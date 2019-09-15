package in.calibrage.akshaya.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.GetEncyclopediaDetails;
import in.calibrage.akshaya.models.GetRecommendationsByAgeModel;
import in.calibrage.akshaya.models.SpinnerModel;
import in.calibrage.akshaya.models.Stand_recom_model;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.GetRecommendationsByAgeAdapter;
import in.calibrage.akshaya.views.actvity.PDFActivity;
import in.calibrage.akshaya.views.actvity.PlayerActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TabFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG = TabFragment.class.getSimpleName();
    int position;
    private TextView textView;
    private RecyclerView rcv_video, rcv_pdf, rcv_recom;
    private LinearLayoutManager llmanagerVideo, llmanagerPDF, layoutManagerrecom;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;
    private int count;
    private String text_year;
    Spinner spinner;
    boolean isLoading = false;
    private GetRecommendationsByAgeAdapter adapter;
    private List<GetRecommendationsByAgeModel> recom_list = new ArrayList<>();
    private LinearLayout lyt_firstTab;
    LinearLayout noRecords;

    public static Fragment getInstance(int position, int count) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        bundle.putInt("title", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        count = count;

        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setViews();
        GetEncyclopediaDetails();
        if (SharedPrefsData.getInstance(getContext()).getIntFromSharedPrefs("count") == 3)
            GetRecommendation();
    }


    private void init(View view) {
        textView = (TextView) view.findViewById(R.id.textView);
        noRecords = (LinearLayout) view.findViewById(R.id.text);
        lyt_firstTab = view.findViewById(R.id.lyt_firstTab);
        textView.setText("Fragment " + (position + 1));
        textView.setVisibility(View.GONE);
        rcv_pdf = view.findViewById(R.id.rcv_pdf);
        rcv_video = view.findViewById(R.id.rcv_video);
        rcv_recom = view.findViewById(R.id.rcv_recom);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        llmanagerPDF = new GridLayoutManager(getContext(), 2);
        llmanagerVideo = new LinearLayoutManager(getContext());
        layoutManagerrecom = new LinearLayoutManager(getContext());
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();
        count = SharedPrefsData.getInstance(getContext()).getIntFromSharedPrefs("count");
        Log.e(TAG, " --- analysis ----- getTabsCount --->> count :" + count);

        if (count == 3 && position == 0) {
            Log.e("recon==","recom_visible");
            lyt_firstTab.setVisibility(View.VISIBLE);
            rcv_video.setVisibility(View.GONE);
            rcv_pdf.setVisibility(View.GONE);
        } else if (count == 3 && position == 1) {
            lyt_firstTab.setVisibility(View.GONE);
            rcv_video.setVisibility(View.VISIBLE);
            rcv_pdf.setVisibility(View.GONE);

        } else if (count == 3 && position == 2) {
            rcv_video.setVisibility(View.GONE);
            rcv_pdf.setVisibility(View.VISIBLE);
            lyt_firstTab.setVisibility(View.GONE);
        } else if (count == 2 && position == 0) {
            lyt_firstTab.setVisibility(View.GONE);
            rcv_video.setVisibility(View.VISIBLE);
            rcv_pdf.setVisibility(View.GONE);
        } else if (count == 2 && position == 1) {
            lyt_firstTab.setVisibility(View.GONE);
            rcv_video.setVisibility(View.GONE);
            rcv_pdf.setVisibility(View.VISIBLE);
        }

    }

    private void setViews() {
        rcv_video.setLayoutManager(llmanagerVideo);
        rcv_pdf.setLayoutManager(llmanagerPDF);
        rcv_recom.setLayoutManager(layoutManagerrecom);
        spinner.setOnItemSelectedListener(this);
    }

    private void GetEncyclopediaDetails() {
        int typeid = SharedPrefsData.getInstance(getContext()).getIntFromSharedPrefs("postTypeId");
        String statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        String finalUrl = APIConstantURL.GetEncyclopediaDetails + typeid + "/" + statecode;
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.getEncyclopediaDetails(finalUrl)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetEncyclopediaDetails>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                        mdilogue.cancel();
                    }

                    @Override
                    public void onNext(final GetEncyclopediaDetails mGetEncyclopediaDetails) {
                        mdilogue.dismiss();

                        List<GetEncyclopediaDetails.ListResult> listResultVideo = new ArrayList<>();
                        List<GetEncyclopediaDetails.ListResult> listResultPDF = new ArrayList<>();

                        for (GetEncyclopediaDetails.ListResult listResult :
                                mGetEncyclopediaDetails.getListResult()) {
                            if (listResult.getFileType().equalsIgnoreCase("PDF"))
                                listResultPDF.add(listResult);
                            else
                                listResultVideo.add(listResult);
                        }

                        Log.e(TAG, "--- analysis ---- >> :Result GetEncyclopediaDetails() --->  VideoSize :" + listResultVideo.size());
                        Log.e(TAG, "--- analysis ---- >> :Result GetEncyclopediaDetails() --->  PDFSize :" + listResultPDF.size());


                        rcv_video.setAdapter(new VideoAdapter(listResultVideo));
                        rcv_pdf.setAdapter(new PdfAdapter(listResultPDF));
                    }


                });
    }

    private void GetRecommendation() {
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.getSpinnerDetails(APIConstantURL.GetRecommendationAges)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<SpinnerModel>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                        mdilogue.cancel();
                    }

                    @Override
                    public void onNext(SpinnerModel spinnerModel) {

                        ArrayList<String> listdata = new ArrayList<>();
                        if (spinnerModel.getListResult() != null) {

                            for (SpinnerModel.ListResult data : spinnerModel.getListResult()
                            ) {
                                listdata.add(data.getDisplayName());
                            }
                            Log.d(TAG, "RESPONSE GetRecommendationAges listdata======" + listdata);

//                            ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listdata);
//                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            spinner.setAdapter(aa);


                            ArrayAdapter aa = new ArrayAdapter(getContext(), R.layout.spinner_item, listdata);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(aa);
                        } else {
                            Log.e("nodada====", "nodata===custom2");

                        }

                    }

                });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        text_year = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        Log.e("text_year===", text_year);

        parseData(text_year, false);
    }

    private void parseData(String text_year, final boolean lazyloading) {

        mdilogue.show();
        String URL_TOKEN = APIConstantURL.LOCAL_URL + "GetRecommendationsByAge/" + text_year;


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Doctor_Json" + response);
                mdilogue.dismiss();
                try {

                    if (lazyloading) {
                        recom_list.remove(recom_list.size() - 1);
                        int scrollPosition = recom_list.size();
                        adapter.notifyItemRemoved(scrollPosition);
                    }
                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("jsonArray==", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {

                        GetRecommendationsByAgeModel get_list = new GetRecommendationsByAgeModel();
                        JSONObject json = null;
                        try {
                            json = jsonArray.getJSONObject(i);
                            get_list.setFertilizer(json.getString("fertilizer"));
                            get_list.setYear(json.getString("quantity") + " " + "Kgs");
                            get_list.setRemarks(json.getString("remarks"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recom_list.add(get_list);
                    }
                    adapter = new GetRecommendationsByAgeAdapter(recom_list, getContext());


                    rcv_recom.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (lazyloading) {
                    adapter.notifyDataSetChanged();
                    isLoading = false;

                } else {

                    adapter = new GetRecommendationsByAgeAdapter(recom_list, getContext());
                    rcv_recom.setAdapter(adapter);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mdilogue.dismiss();
                error.printStackTrace();
            }

            ;
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.videoViewHolder> {
        List<GetEncyclopediaDetails.ListResult> listResultVideo = new ArrayList<>();

        public VideoAdapter(List<GetEncyclopediaDetails.ListResult> listResultVideo) {
            this.listResultVideo = listResultVideo;
        }

        @Override
        public videoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rcv_video_item, parent, false);
            VideoAdapter.videoViewHolder viewHolder = new videoViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(videoViewHolder holder, int position) {


            try {
                final String videoId = CommonUtil.extractYoutubeId(listResultVideo.get(position).getEmbedUrl());
                Log.e("VideoId is->", "" + videoId);
                String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg"; // this is link which will give u thumnail image of that video
                Picasso.with(getContext())
                        .load(img_url)
                        .placeholder(R.drawable.banner_logo)
                        .into(holder.iv_youtube_thumnail);
                holder.txt_name.setText(listResultVideo.get(position).getName());
                holder.txt_desc.setText(listResultVideo.get(position).getDescription());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), PlayerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("videoid", videoId);
                        getContext().startActivity(intent);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return listResultVideo.size();
        }

        class videoViewHolder extends RecyclerView.ViewHolder {
            ImageView iv_youtube_thumnail;
            TextView txt_name, txt_desc;

            public videoViewHolder(View itemView) {
                super(itemView);
                iv_youtube_thumnail = itemView.findViewById(R.id.img_thumnail);
                txt_name = itemView.findViewById(R.id.txt_name);
                txt_desc = itemView.findViewById(R.id.txt_desc);
            }
        }
    }

    class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

        private List<GetEncyclopediaDetails.ListResult> listResultPDF = new ArrayList<>();

        public PdfAdapter(List<GetEncyclopediaDetails.ListResult> listResultPDF) {
            this.listResultPDF = listResultPDF;

        }

        @Override
        public PdfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rcv_pdf_item, parent, false);
            PdfAdapter.PdfViewHolder viewHolder = new PdfAdapter.PdfViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PdfViewHolder holder, final int position) {

            holder.txt_pdfname.setText(listResultPDF.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), PDFActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url", listResultPDF.get(position).getFileUrl());
                    intent.putExtra("name", listResultPDF.get(position).getName());
                    getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listResultPDF.size();
        }

        class PdfViewHolder extends RecyclerView.ViewHolder {
            ImageView iv_youtube_thumnail;
            TextView txt_pdfname;

            public PdfViewHolder(View itemView) {
                super(itemView);
                iv_youtube_thumnail = itemView.findViewById(R.id.img_thumnail);
                txt_pdfname = itemView.findViewById(R.id.txt_pdfname);
            }
        }
    }

}