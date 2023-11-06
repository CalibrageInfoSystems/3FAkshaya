package in.calibrage.akshaya.views.actvity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.common.TypeWriter;
import in.calibrage.akshaya.localData.SharedPrefsData;

import static in.calibrage.akshaya.common.CommonUtil.getDeviceDensityString;
import static in.calibrage.akshaya.common.CommonUtil.updateResources;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();
    private ImageView imgLogo;
    private TypeWriter txt_name, txt_desc;
    private Context context;
    private static int SPLASH_TIME_OUT = 6000;

TextView dialogMessage;
    private Button ok_btn, cancel_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        init();
        setViews();
        Log.d(TAG, "------ analysis --------- >> GetDimens SIZE :" + getDeviceDensityString(this));
       // UpdateApp();
    }

    private void init() {
        imgLogo = findViewById(R.id.img_logo);
        txt_name = findViewById(R.id.txt_name);
        txt_desc = findViewById(R.id.txt_desc);
    }

    private void setViews() {
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.logo_spalsh);
        imgLogo.startAnimation(myanim);
        txt_name.setText("");
        txt_name.setCharacterDelay(30);
        txt_name.animateText("3F OIL PALM");
        txt_desc.setText("");
        txt_desc.setCharacterDelay(60);
        txt_desc.animateText("Sowing for a Better Future");


        final boolean is_login = SharedPrefsData.getBool(SplashActivity.this, Constants.IS_LOGIN);
        final boolean welcome = SharedPrefsData.getBool(SplashActivity.this, Constants.WELCOME);
        final int langID = SharedPrefsData.getInstance(SplashActivity.this).getIntFromSharedPrefs("lang");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (langID == 2)
                    updateResources(SplashActivity.this, "te");
                else if (langID == 3)
                    updateResources(SplashActivity.this, "kan");
                else
                    updateResources(SplashActivity.this, "en-US");

                if (is_login) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else {
                    if (welcome) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                }
            }


        }, SPLASH_TIME_OUT);
    }
    public void UpdateApp(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(result -> {

            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {

                final Dialog dialog = new Dialog(this, R.style.DialogSlideAnim);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_update);
                dialogMessage = dialog.findViewById(R.id.dialogMessage);
                dialogMessage.setText( getString(R.string.alert_updateapp));
                cancel_btn = dialog.findViewById(R.id.cancel_btn);
                ok_btn = dialog.findViewById(R.id.ok_btn);


                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+getPackageName())));
                        }
                        catch (ActivityNotFoundException e){
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                        }

                    }
                });

/**
 * @param OnClickListner
 */
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
////                requestUpdate(result);

    }
});

    }

}