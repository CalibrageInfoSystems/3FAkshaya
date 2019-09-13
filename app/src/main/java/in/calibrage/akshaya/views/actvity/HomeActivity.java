package in.calibrage.akshaya.views.actvity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

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

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static in.calibrage.akshaya.common.CommonUtil.updateResources;
import static in.calibrage.akshaya.common.CommonUtil.view;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private android.support.v7.widget.Toolbar toolbar;
    private BottomNavigationView bottom_navigation;
    private AlertDialog alert, alertDialog;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private ImageView img_profile;
    private Subscription mSubscription;
    private FrameLayout content_frame;
    private FragmentManager fragmentManager;
    private TextView txt_name, txt_phone, txt_adrs, dialogMessage;
    private FarmerOtpResponceModel catagoriesList;
    private Button ok_btn, cancel_btn;
    String FragmentTAG ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_home);
        init();
        setViews();

    }


    private void init() {
        nv = (NavigationView) findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.app_name, R.string.app_name);

        dl.addDrawerListener(t);
        t.syncState();

        /*   getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        bottom_navigation = findViewById(R.id.bottom_navigation);
        View headerLayout =
                nv.inflateHeaderView(R.layout.navigation_header);
        img_profile = headerLayout.findViewById(R.id.img_profile);
        txt_name = headerLayout.findViewById(R.id.txt_name);
        txt_phone = headerLayout.findViewById(R.id.txt_phone);
        txt_adrs = headerLayout.findViewById(R.id.txt_adrs);

        fragmentManager = getSupportFragmentManager();
        content_frame = (FrameLayout) findViewById(R.id.content_frame);
       /* HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, homeFragment, "homeTag")
                .commit();*/
       viewFragment(new HomeFragment(),HomeFragment.TAG);
//        FragmentTAG = HomeFragment.TAG;
    }

    private void setViews() {
        BottomNavigationViewHelper bottomNavigationViewHelper = new BottomNavigationViewHelper();
        bottomNavigationViewHelper.disableShiftMode(bottom_navigation);
        initToolBar();

        nv.setNavigationItemSelectedListener(this);
        catagoriesList = SharedPrefsData.getCatagories(HomeActivity.this);

        String name = catagoriesList.getResult().getFarmerDetails().get(0).getFirstName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getMiddleName() + " " + catagoriesList.getResult().getFarmerDetails().get(0).getLastName();
        txt_name.setText(name.replace("null", ""));

        if (catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber() == null)
            txt_phone.setVisibility(View.GONE);
        else
            txt_phone.setText(catagoriesList.getResult().getFarmerDetails().get(0).getMobileNumber().toString());
        txt_adrs.setText(catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine1() + " - " + catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine2());
        if (!TextUtils.isEmpty(catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine1()))
            Picasso.with(HomeActivity.this).load(catagoriesList.getResult().getFarmerDetails().get(0).getAddressLine1()).error(R.drawable.ic_user).transform(new CircleTransform()).into(img_profile);

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.action_home: {

                        /*replaceFragment(HomeActivity.this,R.id.content_frame,new HomeFragment(),FragmentTAG,HomeFragment.TAG);
                        FragmentTAG = HomeFragment.TAG;*/
                        viewFragment( new HomeFragment(),HomeFragment.TAG);
                        break;
                    }
                    case R.id.action_profile: {
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.content_frame, new ProfileFragment(), ProfileFragment.TAG)
//                                .commit();
                        viewFragment( new ProfileFragment(),ProfileFragment.TAG);
                        break;
                    }
                    case R.id.action_3f: {
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, new My3FFragment(), My3FFragment.TAG)
                                .commit();*/
                        viewFragment( new My3FFragment(),My3FFragment.TAG);
                        break;
                    }
                    case R.id.action_requests: {
                        viewFragment( new RequestsFragment(),RequestsFragment.TAG);
                        break;
                    }
                    case R.id.action_logout: {
                        logOutDialog();
                        break;
                    }
                }
                return true;
            }
        });

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
                        if (!dl.isDrawerOpen(Gravity.START)) dl.openDrawer(Gravity.START);
                        else dl.closeDrawer(Gravity.END);
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

            viewFragment( new HomeFragment(),HomeFragment.TAG);
        } else if (id == R.id.nav_logout) {

            //popupdialog to show message to logout the application
            logOutDialog();
        }

        return true;
    }

    private void logOutDialog() {


        final Dialog dialog = new Dialog(HomeActivity.this,R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_logout);
        cancel_btn = dialog.findViewById(R.id.cancel_btn);
        ok_btn = dialog.findViewById(R.id.ok_btn);
/**
 * @param OnClickListner
 */
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit().clear().commit();
                updateResources(getApplicationContext(), "en-US");
                //  SharedPrefsData.putInt(getApplicationContext(), Constants.ISLOGIN, 0, PREF_NAME);
                SharedPrefsData.getInstance(getApplicationContext()).ClearData(getApplicationContext());
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                // startActivity(new Intent(getApplicationContext(), LoginActivity.class));

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
        final Dialog dialog = new Dialog(HomeActivity.this, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_language);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setTitle("");

        // set the custom forgotPasswordDialog components - text, image and button
        final TextView rbEng = dialog.findViewById(R.id.rbEng);
        final TextView rbTelugu = dialog.findViewById(R.id.rbTelugu);


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


        dialog.show();
    }


    public class BottomNavigationViewHelper {

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
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }
    private void viewFragment(Fragment fragment, String name){
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        // 1. Know how many fragments there are in the stack
        final int count = fragmentManager.getBackStackEntryCount();
        // 2. If the fragment is **not** "home type", save it to the stack
        if( name.equals( HomeFragment.TAG) ) {
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
                if( fragmentManager.getBackStackEntryCount() <= count){
                    // pop all the fragment and remove the listener
                    fragmentManager.popBackStack(HomeFragment.TAG, POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    // set the home button selected
                    bottom_navigation.getMenu().getItem(0).setChecked(true);
                }
            }
        });
    }

}
