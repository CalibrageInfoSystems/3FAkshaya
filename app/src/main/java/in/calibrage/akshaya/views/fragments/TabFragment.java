package in.calibrage.akshaya.views.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.FileProvider;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.BuildConfig;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.common.FileDownloader;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.GetEncyclopediaDetails;
import in.calibrage.akshaya.models.GetRecommendationsByAgeModel;
import in.calibrage.akshaya.models.SpinnerModel;
import in.calibrage.akshaya.models.Stand_recom_model;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.GetRecommendationsByAgeAdapter;
import in.calibrage.akshaya.views.actvity.OtpActivity;
import in.calibrage.akshaya.views.actvity.PDFActivity;
import in.calibrage.akshaya.views.actvity.Pdf_Activity;
import in.calibrage.akshaya.views.actvity.PlayerActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TabFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    public static final String TAG = TabFragment.class.getSimpleName();
    int position;
    private TextView textView, txt_no;
    private RecyclerView rcv_video, rcv_pdf, rcv_recom;
    private LinearLayoutManager llmanagerVideo, llmanagerPDF, layoutManagerrecom;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;
    private String name, url;
    private int count;
    private String text_year;
    Spinner spinner;
    boolean isLoading = false;
    private GetRecommendationsByAgeAdapter adapter;
    private List<GetRecommendationsByAgeModel> recom_list = new ArrayList<>();
    private LinearLayout lyt_firstTab;
    LinearLayout noRecords, noVedios, no_data;

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

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }
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
        if (isOnline(getContext())) {
            GetEncyclopediaDetails();


            if (SharedPrefsData.getInstance(getContext()).getIntFromSharedPrefs("count") == 3)
                GetRecommendation();
        }
        else {
            showDialog(getActivity(), getResources().getString(R.string.Internet));

        }

    }


    private void init(View view) {
        textView = (TextView) view.findViewById(R.id.textView);

        noRecords = (LinearLayout) view.findViewById(R.id.nodata);
        noVedios = (LinearLayout) view.findViewById(R.id.no_videos);
        lyt_firstTab = view.findViewById(R.id.lyt_firstTab);
        textView.setText("Fragment " + (position + 1));
        textView.setVisibility(View.GONE);
        rcv_pdf = view.findViewById(R.id.rcv_pdf);
        rcv_video = view.findViewById(R.id.rcv_video);
        no_data = view.findViewById(R.id.no_data);
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

            lyt_firstTab.setVisibility(View.VISIBLE);
            rcv_video.setVisibility(View.GONE);
            rcv_pdf.setVisibility(View.GONE);
        } else if (count == 3 && position == 1) {
            lyt_firstTab.setVisibility(View.GONE);
            rcv_pdf.setVisibility(View.VISIBLE);
            rcv_video.setVisibility(View.GONE);

        } else if (count == 3 && position == 2) {
            rcv_pdf.setVisibility(View.GONE);
            rcv_video.setVisibility(View.VISIBLE);
            lyt_firstTab.setVisibility(View.GONE);
        } else if (count == 2 && position == 0) {
            lyt_firstTab.setVisibility(View.GONE);
            rcv_pdf.setVisibility(View.VISIBLE);
            rcv_video.setVisibility(View.GONE);
        } else if (count == 2 && position == 1) {
            lyt_firstTab.setVisibility(View.GONE);
            rcv_pdf.setVisibility(View.GONE);
            rcv_video.setVisibility(View.VISIBLE);
        }

    }

    private void setViews() {
        rcv_video.setLayoutManager(llmanagerVideo);
        rcv_pdf.setLayoutManager(llmanagerPDF);
        rcv_recom.setLayoutManager(layoutManagerrecom);
        spinner.setOnItemSelectedListener(this);
    }
//Get Encyclopedia Details
    private void GetEncyclopediaDetails() {
        int typeid = SharedPrefsData.getInstance(getContext()).getIntFromSharedPrefs("postTypeId");
        String statecode = SharedPrefsData.getInstance(getContext()).getStringFromSharedPrefs("statecode");
        String finalUrl = APIConstantURL.GetEncyclopediaDetails + typeid + "/" + statecode + "/"+true;
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
                        showDialog(getActivity(), getString(R.string.server_error));

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
                            else {
                                listResultVideo.add(listResult);
                            }

                        }

                        Log.e(TAG, "--- analysis ---- >> :Result GetEncyclopediaDetails() --->  VideoSize :" + listResultVideo.size());
                        Log.e(TAG, "--- analysis ---- >> :Result GetEncyclopediaDetails() --->  PDFSize :" + listResultPDF.size());


                        rcv_video.setAdapter(new VideoAdapter(listResultVideo));
                        rcv_pdf.setAdapter(new PdfAdapter(listResultPDF));


                        if (count == 3 && position == 0) {

                            lyt_firstTab.setVisibility(View.VISIBLE);
                            rcv_pdf.setVisibility(View.GONE);
                            no_data.setVisibility(View.VISIBLE);

                            rcv_video.setVisibility(View.GONE);
                        } else if (count == 3 && position == 1 && listResultPDF.size() == 0) {
                            lyt_firstTab.setVisibility(View.GONE);
                            noRecords.setVisibility(View.VISIBLE);
                            rcv_pdf.setVisibility(View.GONE);
                            no_data.setVisibility(View.GONE);

                        } else if (count == 3 && position == 2 && listResultVideo.size() == 0) {
                            rcv_video.setVisibility(View.GONE);
                            noVedios.setVisibility(View.VISIBLE);
                            lyt_firstTab.setVisibility(View.GONE);
                            no_data.setVisibility(View.GONE);
                        } else if (count == 2 && position == 0 && listResultPDF.size() == 0) {
                            lyt_firstTab.setVisibility(View.GONE);
                            noRecords.setVisibility(View.VISIBLE);
                            rcv_pdf.setVisibility(View.GONE);
                            no_data.setVisibility(View.GONE);
                        } else if (count == 2 && position == 1 && listResultVideo.size() == 0) {
                            lyt_firstTab.setVisibility(View.GONE);
                            rcv_video.setVisibility(View.GONE);
                            no_data.setVisibility(View.GONE);
                            noVedios.setVisibility(View.VISIBLE);
                        }


//

//                        if (count == 3 && position == 1 && listResultVideo.size() == 0) {
//                            noVedios.setVisibility(View.VISIBLE);
//                            rcv_video.setVisibility(View.GONE);
//                            rcv_pdf.setVisibility(View.GONE);
//                            noRecords.setVisibility(View.GONE);
//                            lyt_firstTab.setVisibility(View.GONE);
//
//                        } else {
//                            noVedios.setVisibility(View.GONE);
//                            rcv_video.setVisibility(View.VISIBLE);
//                            rcv_pdf.setVisibility(View.GONE);
//                            noRecords.setVisibility(View.GONE);
//                            noVedios.setVisibility(View.GONE);
//                            rcv_pdf.setVisibility(View.GONE);
//                            lyt_firstTab.setVisibility(View.GONE);
//                        }

//
//                        if (listResultVideo.size() == 0) {
//                            noVedios.setVisibility(View.VISIBLE);
//                            rcv_video.setVisibility(View.GONE);
//                        } else {
//                            noVedios.setVisibility(View.GONE);
//                            rcv_video.setVisibility(View.VISIBLE);
//                        }
                    }
                });
    }
//Get Recommendation based on plot Ages
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
                        mdilogue.dismiss();
                        showDialog(getActivity(), getString(R.string.server_error));
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

    private void parseData( String text_yearr, final boolean lazyloading) {
        recom_list.clear();
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
                            get_list.setYear(json.getString("quantity"));
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
                final String videoname = listResultVideo.get(position).getName();
                Log.e("VideoId is->", "" + videoId);
                String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg"; // this is link which will give u thumnail image of that video
                Picasso.with(getContext())
                        .load(img_url)
                        .placeholder(R.drawable.banner_logo)
                        .into(holder.iv_youtube_thumnail);
                holder.txt_name.setText(listResultVideo.get(position).getName());
                holder.txt_desc.setText(listResultVideo.get(position).getDescription());
                if (listResultVideo.size() == 0) {
                    Log.e("no====", "videos==");
                } else {
                    Log.e("==========", "videos==");
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), PlayerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("videoid", videoId);
                        intent.putExtra("name", videoname);
                        getContext().startActivity(intent);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (listResultVideo.size() == 0) {
                txt_no.setText("Nodataa....");
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

                    url =listResultPDF.get(position).getFileUrl();
                    name=listResultPDF.get(position).getName();
                    if (isOnline(getContext())) {
                        new DownloadFilee().execute(url, name + ".pdf");
                        Log.e("url==========", url);
                    }
                    else {
                        showDialog(getActivity(), getResources().getString(R.string.Internet));
                    }


//                    File pdfFile = new File(Environment.getExternalStorageDirectory() + "/3FOIL/" + name+".pdf");
//
//                    Log.e("pdfFile===========",pdfFile+"");// -> filename = maven.pdf
//                    //Uri path = Uri.fromFile(pdfFile);
//                    Uri path = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", pdfFile);
//                    Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//                    //
//                    pdfIntent.setDataAndType(path, "application/pdf");
//                    pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                    try {
//                        startActivity(pdfIntent);
//                    } catch (ActivityNotFoundException e) {
//                        Toast.makeText(getActivity(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
//                    }
//
                  //  Toast.makeText(getActivity(), "Download Completed See in Views ", Toast.LENGTH_SHORT).show();
                   //Intent intent = new Intent(getContext(), Pdf_Activity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("url", listResultPDF.get(position).getFileUrl());
//                    intent.putExtra("name", listResultPDF.get(position).getName());
//                    getContext().startActivity(intent);
                }
            });
            holder.txt_desc.setText(listResultPDF.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return listResultPDF.size();
        }

        class PdfViewHolder extends RecyclerView.ViewHolder {
            ImageView iv_youtube_thumnail;
            TextView txt_pdfname, txt_desc;

            public PdfViewHolder(View itemView) {
                super(itemView);
                iv_youtube_thumnail = itemView.findViewById(R.id.img_thumnail);
                txt_pdfname = itemView.findViewById(R.id.txt_pdfname);
                txt_desc = itemView.findViewById(R.id.txt_desc);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult ( int requestCode, String[] permissions,
                                             int[] grantResults){
        switch (requestCode) {
            case 0:
                // Re-attempt file write
        }
    }

    private class DownloadFilee extends AsyncTask<String, String, String> {

        private String resp;
     //   ProgressDialog progressDialog;

        SpotsDialog mdiloguee = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();
        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "3FOIL");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }



        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            mdiloguee.dismiss();


            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/3FOIL/" + name+".pdf");

            Log.e("pdfFile===========",pdfFile+"");// -> filename = maven.pdf
            //Uri path = Uri.fromFile(pdfFile);
            Uri path = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", pdfFile);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            //
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(pdfIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getActivity(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }
            //finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            mdiloguee.show();
//            mdiloguee = ProgressDialog.show(getActivity(),
//                    "ProgressDialog",
//                    "Wait for "+ " seconds");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            //finalResult.setText(text[0]);

        }
    }
}


//     class DownloadFilee extends AsyncTask<String, Void, Void> {
//
//
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
//            String fileName = strings[1];  // -> maven.pdf
//            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//            File folder = new File(extStorageDirectory, "3FOIL");
//            folder.mkdir();
//
//            File pdfFile = new File(folder, fileName);
//
//            try {
//                pdfFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            FileDownloader.downloadFile(fileUrl, pdfFile);
//            return null;
//        }
//    }


