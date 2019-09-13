package in.calibrage.akshaya.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseFragment;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.models.GetEncyclopediaDetails;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TabFragment extends BaseFragment {
    public static final String TAG = TabFragment.class.getSimpleName();
    int position;
    private TextView textView;
    private RecyclerView rcv_video, rcv_pdf;
    private LinearLayoutManager llmanagerVideo, llmanagerPDF;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;


    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
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

    }

    private void init(View view) {
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("Fragment " + (position + 1));
        rcv_pdf = view.findViewById(R.id.rcv_pdf);
        rcv_video = view.findViewById(R.id.rcv_video);
        llmanagerPDF = new LinearLayoutManager(getContext());
        llmanagerVideo = new LinearLayoutManager(getContext());
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.Custom)
                .build();
    }

    private void setViews() {
        rcv_video.setLayoutManager(llmanagerVideo);
        rcv_pdf.setLayoutManager(llmanagerPDF);

    }

    private void GetEncyclopediaDetails() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(getContext(), ApiService.class);
        mSubscription = service.getEncyclopediaDetails(APIConstantURL.GetEncyclopediaDetails)
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
                String videoId = CommonUtil.extractYoutubeId(listResultVideo.get(position).getEmbedUrl());
                Log.e("VideoId is->", "" + videoId);
                String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg"; // this is link which will give u thumnail image of that video
                Picasso.with(getContext())
                        .load(img_url)
                        .placeholder(R.drawable.banner_logo)
                        .into(holder.iv_youtube_thumnail);
                holder.txt_name.setText(listResultVideo.get(position).getName());
                holder.txt_desc.setText(listResultVideo.get(position).getDescription());
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
            TextView txt_name,txt_desc;

            public videoViewHolder(View itemView) {
                super(itemView);
                iv_youtube_thumnail = itemView.findViewById(R.id.img_thumnail);
                txt_name = itemView.findViewById(R.id.txt_name);
                txt_desc = itemView.findViewById(R.id.txt_desc);
            }
        }
    }

    class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

        List<GetEncyclopediaDetails.ListResult> listResultPDF = new ArrayList<>();

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
        public void onBindViewHolder(PdfViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return listResultPDF.size();
        }

        class PdfViewHolder extends RecyclerView.ViewHolder {

            public PdfViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}