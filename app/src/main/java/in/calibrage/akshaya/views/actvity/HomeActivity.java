package in.calibrage.akshaya.views.actvity;

//import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static in.calibrage.akshaya.common.CommonUtil.updateResources;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import in.calibrage.akshaya.BuildConfig;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.CircleTransform;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.views.fragments.HomeFragment;
import in.calibrage.akshaya.views.fragments.My3FFragment;
import in.calibrage.akshaya.views.fragments.ProfileFragment;
import in.calibrage.akshaya.views.fragments.RequestsFragment;
import rx.Subscription;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom_navigation;
    private AlertDialog alert, alertDialog;
    private DrawerLayout dl;
    private Toolbar toolbar;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private ImageView img_profile;
    private Subscription mSubscription;
    private FrameLayout content_frame;
    private FragmentManager fragmentManager;
    private TextView txt_name, txt_phone, txt_adrs, dialogMessage;
    FarmerOtpResponceModel catagoriesList;
    private Button ok_btn, cancel_btn;
    String FragmentTAG;
    FloatingActionButton myFab;
    Integer mSelectedItem;
    AppCompatTextView app_version;
     int langID;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      langID = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(this, "te");
        else if (langID == 3)
            updateResources(this, "kan");
        else
            updateResources(this, "en-US");
        setContentView(R.layout.activity_home);

        init();
        setViews();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        app_version = findViewById(R.id.app_version);
        nv = (NavigationView) findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.app_name, R.string.app_name);
        myFab = (FloatingActionButton) findViewById(R.id.call_fb);
        dl.addDrawerListener(t);
        t.syncState();
        bottom_navigation = findViewById(R.id.bottom_navigation);
        View headerLayout = nv.inflateHeaderView(R.layout.navigation_header);
        img_profile = headerLayout.findViewById(R.id.img_profile);
        txt_name = headerLayout.findViewById(R.id.txt_name);
        txt_phone = headerLayout.findViewById(R.id.txt_phone);
        txt_adrs = headerLayout.findViewById(R.id.txt_adrs);

        fragmentManager = getSupportFragmentManager();
        content_frame = (FrameLayout) findViewById(R.id.content_frame);
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, homeFragment, "homeTag")
                .commit();
    }

    private void setViews() {

        String versionName = BuildConfig.VERSION_NAME;
        app_version.setText(getResources().getString(R.string.App_version)+ " "+versionName);
        BottomNavigationViewHelper bottomNavigationViewHelper = new BottomNavigationViewHelper();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            bottomNavigationViewHelper.disableShiftMode(bottom_navigation);
        }
        initToolBar();

        nv.setNavigationItemSelectedListener(this);

        catagoriesList = SharedPrefsData.getCatagories(HomeActivity.this);

        String name = catagoriesList.getResult().getFarmerDetails().get(0).getFirstName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getMiddleName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getLastName();
        txt_name.setText(name.replace("null", ""));

        if (catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber() == null)
            txt_phone.setVisibility(View.GONE);
        else
            txt_phone.setText(catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber().toString());
        if (catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine1() == null)
        {
            txt_adrs.setText( catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine2()+"");

        }else{
            txt_adrs.setText(catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine1() + " - " + catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine2());

        }


        if (!TextUtils.isEmpty(catagoriesList.getResult().getFarmerDetails().get(0).getFarmerPictureLocation()))
            Picasso.with(HomeActivity.this).load(catagoriesList.getResult().getFarmerDetails().get(0).getFarmerPictureLocation()).error(R.drawable.ic_user).transform(new CircleTransform()).into(img_profile);

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.action_home: {
                        mSelectedItem = item.getItemId();
                        viewFragment(new HomeFragment(), HomeFragment.TAG);
                        break;
                    }
                    case R.id.action_profile: {
                        mSelectedItem = item.getItemId();
                        viewFragment(new ProfileFragment(), ProfileFragment.TAG);
                        break;
                    }
                    case R.id.action_3f: {
                        mSelectedItem = item.getItemId();
                        viewFragment(new My3FFragment(), My3FFragment.TAG);
                        break;
                    }
                    case R.id.action_requests: {
                        mSelectedItem = item.getItemId();
                        viewFragment(new RequestsFragment(), RequestsFragment.TAG);
                        break;
                    }
                    case R.id.action_care: {
                        mSelectedItem = item.getItemId();
                        //  bottom_navigation.setSelectedItemId(R.id.action_requests);
                        caredial();
                        break;
                    }
                }
                return true;
            }
        });
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + "123456789");
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(HomeActivity.this, "SecurityException", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void caredial() {

        Uri u = Uri.parse("tel:" + " 040 23324733");
        Intent i = new Intent(Intent.ACTION_DIAL, u);
        try {
            startActivity(i);
        } catch (SecurityException s) {
            Toast.makeText(HomeActivity.this, "SecurityException", Toast.LENGTH_LONG)
                    .show();
        }
    }


    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!dl.isDrawerOpen(GravityCompat.START)) dl.openDrawer(GravityCompat.START);
                        else dl.closeDrawer(GravityCompat.END);
                    }
                }
        );
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        Log.e("id===", String.valueOf(id));
        if (id == R.id.languageTitle) {

            selectLanguage();
            // Handle the camera action
        } else if (id == R.id.action_home) {

            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, homeFragment, null)
                    .commit();
            // Select home item
            bottom_navigation.setSelectedItemId(id);
            // finish();
            bottom_navigation.setSelectedItemId(R.id.action_home);
            if (this.dl.isDrawerOpen(GravityCompat.START))
                this.dl.closeDrawer(GravityCompat.START);

        } else if (id == R.id.action_profile) {
            mSelectedItem = item.getItemId();
            viewFragment(new RequestsFragment(), ProfileFragment.TAG);
            bottom_navigation.setSelectedItemId(R.id.action_profile);
            if (this.dl.isDrawerOpen(GravityCompat.START))
                this.dl.closeDrawer(GravityCompat.START);

        } else if (id == R.id.action_3f) {

            mSelectedItem = item.getItemId();
            viewFragment(new RequestsFragment(), My3FFragment.TAG);
            bottom_navigation.setSelectedItemId(R.id.action_3f);
            if (this.dl.isDrawerOpen(GravityCompat.START))
                this.dl.closeDrawer(GravityCompat.START);
        } else if (id == R.id.My_request) {
            mSelectedItem = item.getItemId();
            viewFragment(new RequestsFragment(), RequestsFragment.TAG);
            bottom_navigation.setSelectedItemId(R.id.action_requests);
            if (this.dl.isDrawerOpen(GravityCompat.START))
                this.dl.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_logout) {
            //  bottom_navigation.setSelectedItemId(R.id.action_requests);
            //popupdialog to show message to logout the application
            logOutDialog();
        }
        return true;
    }

    private void logOutDialog() {
        final Dialog dialog = new Dialog(HomeActivity.this, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_logout);
        dialogMessage = dialog.findViewById(R.id.dialogMessage);
        dialogMessage.setText(getString(R.string.alert_logout));
        cancel_btn = dialog.findViewById(R.id.cancel_btn);
        ok_btn = dialog.findViewById(R.id.ok_btn);
/**
 * @param OnClickListner
 */
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPrefsData.putBool(HomeActivity.this, Constants.IS_LOGIN, false);
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

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
    }


    private void selectLanguage() {
        String statecode = SharedPrefsData.getInstance(this).getStringFromSharedPrefs("statecode");
        Log.e("state===",statecode);
        final Dialog dialog = new Dialog(HomeActivity.this, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_language);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle("");

        // set the custom forgotPasswordDialog components - text, image and button
        final TextView rbEng = dialog.findViewById(R.id.rbEng);
        final TextView rbTelugu = dialog.findViewById(R.id.rbTelugu);
        final TextView rbKannada = dialog.findViewById(R.id.rbkannada);
        View view =dialog.findViewById(R.id.view);
        View view2 =dialog.findViewById(R.id.view2);

//        if (statecode.equalsIgnoreCase("AP")){
//
//            rbTelugu.setVisibility(View.VISIBLE);
//            rbKannada.setVisibility(View.GONE);
//            view2.setVisibility(View.GONE);
//        }
//        else{
//            rbTelugu.setVisibility(View.GONE);
//            rbKannada.setVisibility(View.VISIBLE);
//            view2.setVisibility(View.GONE);
//        }

/**
 * @param OnClickListner
 */
        rbEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * "en" is the localization code for our default English language.
                 */


                updateResources(HomeActivity.this, "en-US");
                SharedPrefsData.getInstance(HomeActivity.this).updateIntValue(HomeActivity.this, "lang", 1);
                Intent refresh = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(refresh);
                finish();
                dialog.dismiss();
            }
        });

/**
 * @param OnClickListner
 */
        rbTelugu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * "te" is the localization code for our default Telugu language.
                 */
                updateResources(HomeActivity.this, "te");
                SharedPrefsData.getInstance(HomeActivity.this).updateIntValue(HomeActivity.this, "lang", 2);
                Intent refresh = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(refresh);
                finish();
                dialog.dismiss();
            }
        });
        rbKannada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * "te" is the localization code for our default Telugu language.
                 */
                updateResources(HomeActivity.this, "kan");
                SharedPrefsData.getInstance(HomeActivity.this).updateIntValue(HomeActivity.this, "lang", 3);
                Intent refresh = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(refresh);
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


//    public class BottomNavigationViewHelper {
//
//        @SuppressLint("RestrictedApi")
//        public void disableShiftMode(BottomNavigationView view) {
//            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
//            try {
//                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//                shiftingMode.setAccessible(true);
//                shiftingMode.setBoolean(menuView, false);
//                shiftingMode.setAccessible(false);
//                for (int i = 0; i < menuView.getChildCount(); i++) {
//                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                    //noinspection RestrictedApi
//                    item.setShiftingMode(false);
//                    // set once again checked value, so view will be updated
//                    //noinspection RestrictedApi
//                    item.setChecked(item.getItemData().isChecked());
//                }
//            } catch (NoSuchFieldException e) {
//                Log.e("BNVHelper", "Unable to get shift mode field", e);
//            } catch (IllegalAccessException e) {
//                Log.e("BNVHelper", "Unable to change value of shift mode", e);
//            }
//        }
//    }

    public class BottomNavigationViewHelper {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("RestrictedApi")
        public void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);

                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);

                    // Use reflection to access setShiftingMode method
                    Method setShiftingMode = item.getClass().getDeclaredMethod("setShiftingMode", boolean.class);
                    setShiftingMode.setAccessible(true);
                    setShiftingMode.invoke(item, false);
                    setShiftingMode.setAccessible(false);

                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException | NoSuchMethodException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }


    private void viewFragment(Fragment fragment, String name) {
          fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        // 1. Know how many fragments there are in the stack
        final int count = fragmentManager.getBackStackEntryCount();
        // 2. If the fragment is **not** "home type", save it to the stack
        if (name.equals(HomeFragment.TAG)) {
            fragmentTransaction.addToBackStack(name);
        }
        // Commit !
        fragmentTransaction.commit();
        // 3. After the commit, if the fragment is not an "home type" the back stack is changed, triggering the
        // OnBackStackChanged callback
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // If the stack decreases it means I clicked the back button
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    // pop all the fragment and remove the listener
                    fragmentManager.popBackStack(HomeFragment.TAG, POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    // set the home button selected
                    bottom_navigation.getMenu().getItem(0).setChecked(true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = bottom_navigation.getMenu().getItem(0);
        Log.e("===========>",homeItem.getItemId()+"");

        if (mSelectedItem !=null && mSelectedItem != homeItem.getItemId()) {

            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, homeFragment, null)
                    .commit();
            // Select home item
            bottom_navigation.setSelectedItemId(homeItem.getItemId());
        } else {
            super.onBackPressed();
        }
    }
}
