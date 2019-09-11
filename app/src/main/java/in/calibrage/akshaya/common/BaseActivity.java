package in.calibrage.akshaya.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import in.calibrage.akshaya.R;


public class BaseActivity extends AppCompatActivity {

    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    private ProgressDialog mProgressDialog;


    // to intialize the Progress Dialog

    private void initProgressDialog() {

        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(this);

        }

        mProgressDialog.setMessage("Please Wait...");

        mProgressDialog.setCancelable(false);

        mProgressDialog.setCanceledOnTouchOutside(false);

    }


    // to start the Progress Dialog

    public void showProgressDialog() {


        runOnUiThread(new Runnable() {

            @Override

            public void run() {

                try {

                    if (mProgressDialog == null)

                        initProgressDialog();

                    if (!mProgressDialog.isShowing())

                        mProgressDialog.show();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        });

    }


    // to hide the Progress Dialog

    public void hideProgressDialog() {

        runOnUiThread(new Runnable() {

            @Override

            public void run() {

                try {

                    if (mProgressDialog != null && mProgressDialog.isShowing())

                        mProgressDialog.dismiss();

                } catch (Exception e) {

                    e.printStackTrace();

                } finally {

                    mProgressDialog = null;

                }

            }

        });

    }


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

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void replaceFragment(final FragmentActivity activity, final int container, final Fragment
            fragment, final String cuurentFragmentTag, final String newFragmentTag) {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                if (activity != null)// update the main content by replacing fragments
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

    public void validationPopShow() {

        LayoutInflater layoutInflater = (LayoutInflater)BaseActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup, null);

        closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popup_window_animation);

        //display the popup window
       // popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }


}





