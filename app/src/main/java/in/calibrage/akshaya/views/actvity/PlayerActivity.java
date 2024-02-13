package in.calibrage.akshaya.views.actvity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.Config;

public class PlayerActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener{
    private YouTubePlayerFragment playerFragment;
    private YouTubePlayer mPlayer;
    private String YouTubeKey = Config.DEVELOPER_KEY;
    private String videoid;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        if (getIntent() != null) {
           String name = getIntent().getStringExtra("name");
            videoid = getIntent().getStringExtra("videoid");
            TextView txt_name = findViewById(R.id.txt_name);
            txt_name.setText(name);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        playerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player_fragment);

        playerFragment.initialize(YouTubeKey, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
        mPlayer = player;

        //Enables automatic control of orientation
        mPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        //Show full screen in landscape mode always
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        //System controls will appear automatically
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        if (!b) {
            //player.cueVideo("9rLZYyMbJic");
            mPlayer.loadVideo(videoid);
        }
        else
        {
            mPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        mPlayer = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
