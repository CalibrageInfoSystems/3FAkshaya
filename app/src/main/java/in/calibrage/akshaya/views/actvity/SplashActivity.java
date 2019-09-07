package in.calibrage.akshaya.views.actvity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.TypeWriter;

public class SplashActivity extends BaseActivity {
    private ImageView imgLogo;
    private TypeWriter txt_name,txt_desc;
    private Context context;
    private static int SPLASH_TIME_OUT = 6000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        setViews();
    }
    private void init() {
        imgLogo = findViewById(R.id.img_logo);
        txt_name = findViewById(R.id.txt_name);
        txt_desc = findViewById(R.id.txt_desc);
    }
    private void setViews() {
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.logo_spalsh);
        imgLogo.startAnimation(myanim);
        txt_name.setText("");
        txt_name.setCharacterDelay(30);
        txt_name.animateText("3F OIL PALM");

        txt_desc.setText("");
        txt_desc.setCharacterDelay(60);
        txt_desc.animateText("sowing for better future");


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                finish();

            }
        }, SPLASH_TIME_OUT);
    }

}
