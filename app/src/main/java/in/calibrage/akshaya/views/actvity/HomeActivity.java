package in.calibrage.akshaya.views.actvity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.CircleTransform;
import in.calibrage.akshaya.common.Constants;
import in.calibrage.akshaya.views.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private BottomNavigationView bottom_navigation;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private ImageView img_profile;

    private FrameLayout content_frame;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        setViews();
    }

    private void init() {
        nv = (NavigationView)findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.app_name, R.string.app_name);

        dl.addDrawerListener(t);
        t.syncState();

        /*   getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        bottom_navigation = findViewById(R.id.bottom_navigation);
        View headerLayout =
                nv.inflateHeaderView(R.layout.navigation_header);
        img_profile = headerLayout.findViewById(R.id.img_profile);

        fragmentManager = getSupportFragmentManager();
        content_frame = (FrameLayout) findViewById(R.id.content_frame);
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, homeFragment, "homeTag")
                .commit();


    }

    private void setViews() {
        BottomNavigationViewHelper bottomNavigationViewHelper = new BottomNavigationViewHelper();
        bottomNavigationViewHelper.disableShiftMode(bottom_navigation);
        initToolBar();
        Picasso.with(HomeActivity.this).load(Constants.PROFILE_URL).transform(new CircleTransform()).into(img_profile);

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
                        if(!dl.isDrawerOpen(Gravity.START)) dl.openDrawer(Gravity.START);
                        else dl.closeDrawer(Gravity.END);
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
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
}
