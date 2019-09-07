package in.calibrage.akshaya.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;



public class BaseActivity extends AppCompatActivity {

    public boolean isOnline() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public void replaceFragment(final FragmentActivity activity, final int container, final Fragment
            fragment, final String cuurentFragmentTag, final String newFragmentTag) {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                if (activity !=null )// update the main content by replacing fragments
                {

                    FragmentTransaction fragmentTransaction = activity
                            .getSupportFragmentManager()
                            .beginTransaction();
                    fragmentTransaction
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction
                            .addToBackStack(cuurentFragmentTag)
                            .add(container, fragment, newFragmentTag);
                    fragmentTransaction.commitAllowingStateLoss();
                }


                /*  closeTab(cuurentFragmentTag);*/

            }
        };
        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            new Handler().post(mPendingRunnable);
        }
    }
}
