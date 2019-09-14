package in.calibrage.akshaya.views.actvity;

import android.content.Intent;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.views.Adapter.PagerAdapter;
import in.calibrage.akshaya.views.fragments.StandardRecommendationsFragment;
import in.calibrage.akshaya.views.fragments.VideoFragment;
import in.calibrage.akshaya.views.fragments.documentFragment;

public class EncyclopediaActivity extends AppCompatActivity {
    private int postTypeId;
    private String titleName;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView label;
    ImageView backImg;
    private OnAboutDataReceivedListener mAboutDataListener;
    PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_encyclopedia);
         backImg = (ImageView) findViewById(R.id.back);
        label = (TextView) findViewById(R.id.text_label);
        if (getIntent() != null) {
            /*
             * to check the intent value
             * @param  postTypeId
             *@param  image
             */
            postTypeId = getIntent().getIntExtra("postTypeId", 0);
            titleName = getIntent().getStringExtra("name");

        }
        label.setText(titleName);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if(postTypeId == 1) {
            Log.d("Id ", "Id======101" + postTypeId);
            pagerAdapter.addFrag(new StandardRecommendationsFragment(), getString(R.string.standard_recommendations));
            pagerAdapter.addFrag(new VideoFragment(),getString(R.string.videos));
            //   pagerAdapter.addFrag(new AudioFragment(), "Audios");
            pagerAdapter.addFrag(new documentFragment(),getString(R.string.doc));
        }
        else {
            pagerAdapter.addFrag(new VideoFragment(), getString(R.string.videos));
            //   pagerAdapter.addFrag(new AudioFragment(), "Audios");
            pagerAdapter.addFrag(new documentFragment(), getString(R.string.doc));
        }
        viewPager.setAdapter(pagerAdapter);

    }


    public interface OnAboutDataReceivedListener {
        void onDataReceived(String model);
    }


    public void setAboutDataListener(OnAboutDataReceivedListener listener) {
        this.mAboutDataListener = listener;
    }

}
