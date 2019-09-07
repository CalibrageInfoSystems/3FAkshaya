package in.calibrage.akshaya.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import in.calibrage.akshaya.R;






/*   this frgamenbt usefull for adding and back caling fragemnt*/

public class BaseFragment extends Fragment {
    private ProgressDialog mProgressDialog;
    private int LoginStatus = 0;
    //public static final int MAIN_CONTAINER = R.id.content_frame;


    protected void popUpFromBackStack(FragmentActivity activity) {
        activity.getSupportFragmentManager().popBackStack();
    }

    public static void addFragment(FragmentActivity activity, int container,Fragment fragment,
                                   String cuurentFragmentTag, String newFragmentTag) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(cuurentFragmentTag)
                .add(container, fragment, newFragmentTag)
                .commit();
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

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    public void showDialog(FragmentActivity activity, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage(message);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }





   /* public void ShowImagePopUp(String Img_url, String msg_str, String TAG) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dialog.setContentView(R.layout.alert_image_alert);

        dialog.show();

    }*/

}
