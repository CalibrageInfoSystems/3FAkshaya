package in.calibrage.akshaya.views.actvity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.LabourRecommendationsModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.KnowledgeZoneBaseAdapter;
import in.calibrage.akshaya.views.Adapter.LabourRecommendationAdapter;
import in.calibrage.akshaya.views.Adapter.PaymentAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.common.CommonUtil.updateResources;
import static java.util.Calendar.YEAR;

public class LabourRecommendationsActivity  extends BaseActivity {
    private static final String TAG = LabourRecommendationsActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private LabourRecommendationAdapter adapter;
    private Subscription mSubscription;
    String Farmer_code;
    LinearLayout noRecords;
    ImageView backImg,home_btn;
    private SpotsDialog mdilogue ;
    private  boolean isstatustype_89 = false;
    Calendar calendar ;
    Date dateObj;
    int diff;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int langID = SharedPrefsData.getInstance(this).getIntFromSharedPrefs("lang");
        if (langID == 2)
            updateResources(this, "te");
        else if (langID == 3)
            updateResources(this, "kan");
        else
            updateResources(this, "en-US");
        setContentView(R.layout.activity_labour_recommendations);
        init();
        setViews();
        settoolbar();
    }

    private void settoolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Plot");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "").trim();
        noRecords = (LinearLayout) findViewById(R.id.text);
       // backImg = (ImageView) findViewById(R.id.back);
         home_btn = (ImageView) findViewById(R.id.home_btn);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
    }

    private void setViews() {

//        backImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
//            }
//        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LabourRecommendationsActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        // listSuperHeroes = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        if (isOnline())
            GetLabourRequestDetails();
        else {
            showDialog(LabourRecommendationsActivity.this, getResources().getString(R.string.Internet));

        }


    }
    // Farmer Active  plots list ==> Farmer/GetActivePlotsByFarmerCode/

    private void GetLabourRequestDetails() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getrecommdetails(APIConstantURL.GetActivePlotsByFarmerCode + Farmer_code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LabourRecommendationsModel>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                Log.d(TAG, "error " + ((HttpException) e).response().errorBody());
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }

                        mdilogue.dismiss();
          showDialog(LabourRecommendationsActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(LabourRecommendationsModel labourRecommendationsModel) {
                        mdilogue.dismiss();
                        boolean isLessthan3years = false;
                        Log.d(TAG, "onNext:lobour " + labourRecommendationsModel);
//

                        if (labourRecommendationsModel.getListResult() != null) {
                            // we have check wether 89 id exist or mot
                            for (LabourRecommendationsModel.ListResult item : labourRecommendationsModel.getListResult()) {

                                Log.d("Mahesh", "--- Analysis ----- ");
                                Log.d("Mahesh", "id :" + item.getStatusTypeId());
                                String date = item.getDateOfPlanting();


                                SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    dateObj = curFormater.parse(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date currentDate = new Date();
                                Calendar a = getCalendar(dateObj);
                                Calendar b = getCalendar(currentDate);
                                diff = b.get(YEAR) - a.get(YEAR);
                                Log.e("diff====", diff + "");


                                if(diff >= 3)
                                    isLessthan3years =true;

                            }


                            if (!isLessthan3years) {
                                showDialogf(LabourRecommendationsActivity.this, getResources().getString(R.string.no_yeild));

                            }else{

                                noRecords.setVisibility(View.GONE);
                                adapter = new LabourRecommendationAdapter(labourRecommendationsModel.getListResult(), LabourRecommendationsActivity.this);
                                recyclerView.setAdapter(adapter);
                        }



                        } else {
                            noRecords.setVisibility(View.VISIBLE);

                        }
                    }


                });
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
    public void showDialogf(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final ImageView img = dialog.findViewById(R.id.img_cross);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Animatable) img.getDrawable()).start();
            }
        }, 500);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
