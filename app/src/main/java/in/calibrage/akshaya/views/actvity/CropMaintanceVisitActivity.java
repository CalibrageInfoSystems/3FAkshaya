package in.calibrage.akshaya.views.actvity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.common.Config;
import in.calibrage.akshaya.models.CropResponseModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.CropMaintenanceVisitAdapter;
import in.calibrage.akshaya.views.Adapter.ExpandableVisitAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CropMaintanceVisitActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener {

    private Context mContext;
    private ExpandableListView lst_crop;
    private ExpandableVisitAdapter lst_visitAdapter;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    // YouTube player view
    private YouTubePlayerView youTubeView;
    ImageView iv_youtube_thumnail,iv_play;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_maintance_visit);
        init();
        setViews();

    }
    private void init() {
        mContext = CropMaintanceVisitActivity.this;
        iv_youtube_thumnail=(ImageView)findViewById(R.id.img_thumnail);
        iv_play=(ImageView)findViewById(R.id.iv_play_pause);

    }

    private void setViews() {
        try
        {
            videoId= CommonUtil.extractYoutubeId("https://www.youtube.com/watch?v=tHy12F1PxFU");

            Log.e("VideoId is->","" + videoId);

            String img_url="http://img.youtube.com/vi/"+videoId+"/0.jpg"; // this is link which will give u thumnail image of that video

            // picasso jar file download image for u and set image in imagview

            Picasso.with(CropMaintanceVisitActivity.this)
                    .load(img_url)
                    .placeholder(R.drawable.ic_bank)
                    .into(iv_youtube_thumnail);

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
        if (!b) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(Config.YOUTUBE_VIDEO_CODE);

            // Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            /*String errorMessage = String.format(
                    getString(R.string.app_name), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();*/

            Toast.makeText(mContext, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}
