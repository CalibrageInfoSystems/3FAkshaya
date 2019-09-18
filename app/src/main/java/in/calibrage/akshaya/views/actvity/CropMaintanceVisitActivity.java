package in.calibrage.akshaya.views.actvity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.CropResponseModel;
import in.calibrage.akshaya.models.DiseaseDataModel;
import in.calibrage.akshaya.models.Nutrient_Model;
import in.calibrage.akshaya.models.fertilizerRecommendation;
import in.calibrage.akshaya.models.healthPlantation;
import in.calibrage.akshaya.models.pest;
import in.calibrage.akshaya.models.uprootmentData;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.DiseaseDataAdapter;
import in.calibrage.akshaya.views.Adapter.NutrientDataAdapter;
import in.calibrage.akshaya.views.Adapter.fertilizerRecommendation_Adapter;
import in.calibrage.akshaya.views.Adapter.healthPlantation_Adapter;
import in.calibrage.akshaya.views.Adapter.pest_Adapter;
import in.calibrage.akshaya.views.Adapter.uprootment_Adapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CropMaintanceVisitActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private Subscription mSubscription;
    private static final String TAG = CropMaintanceVisitActivity.class.getSimpleName();
    String Farmer_code,plot_id,datevaluereq,timevaluereq;
    RecyclerView recycler_view_health_plant,recycler_view_uprootment ,
            recycler_view_fert_rec_Details,recycler_view_pest,recycler_view_desease,
            recyclerView_nut;
    ImageView thumbnail;
    private healthPlantation_Adapter hAdapter;
    private uprootment_Adapter UAdapter;
    private TextView Age, id_plot, area,landMark;
    //
   private fertilizerRecommendation_Adapter fertadapter;
//
    private pest_Adapter pestadapter;
    //
    private DiseaseDataAdapter disease_adapter;
    //
//
    private NutrientDataAdapter nut_adapter;

    private List<healthPlantation> Plantation_List = new ArrayList<>();
    private List<uprootmentData> uprootment_List = new ArrayList<>();
    //
    private List<fertilizerRecommendation> fert_rec_List = new ArrayList<>();
//    private List<wood>wood_List = new ArrayList<>();
    private List<pest> pestt_List = new ArrayList<>();
    //
    private List<DiseaseDataModel> disease_List = new ArrayList<>();
    //
    private List<Nutrient_Model>nut_List = new ArrayList<>();


    private SpotsDialog mdilogue ;
    String datetimevalute;
    TextView health_text,uprootment_text,fert_rec_text,pest_text,diase_text,nut_text;
    public TextView treesAppearance,treeGirth,treeHeight,fruitColor,fruitSize,fruitHyegiene,plantationType;
    public TextView seedsPlanted,prevPalmsCount,plamsCount,isTreesMissing,missingTreesCount,reasonType,expectedPlamsCount,comments;
    // ImageView thumbnail;
    public TextView comment_label,reason_label;
    String plot_Age,location,landmarkCode;
    ImageView backImg,home_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crop_maintance_visit);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            plot_id = extras.getString("plotid");
            plot_Age = extras.getString("plotAge");
            location = extras.getString("plotVillage");
            landmarkCode = extras.getString("landMark");

        }
        intview();
        setViews();

        GetCropMaintenanceHistoryDetailsByCode();

    }

    private void intview() {

         backImg = (ImageView) findViewById(R.id.back);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        recycler_view_pest =(RecyclerView)findViewById(R.id.recyclerView_pest);
        pest_text =(TextView) findViewById(R.id.pest_text);

        recycler_view_desease =(RecyclerView)findViewById(R.id.recyclerView_dease);
        diase_text =(TextView) findViewById(R.id.dease_text);

        recyclerView_nut =(RecyclerView)findViewById(R.id.recyclerView_nut);
        nut_text =(TextView) findViewById(R.id.nut_text);

        recycler_view_fert_rec_Details =(RecyclerView)findViewById(R.id.recyclerView_fert_rec);
        fert_rec_text =(TextView) findViewById(R.id.fert_rec_text);

        treesAppearance = findViewById(R.id.treesAppearance);
        treeGirth = findViewById(R.id.treeGirth);
        treeHeight = findViewById(R.id.treeHeight);
        fruitColor = findViewById(R.id.fruitColor);
        fruitSize= findViewById(R.id.fruitSize);

        fruitHyegiene = findViewById(R.id.fruitHyegiene);
        plantationType= findViewById(R.id.plantationType);
        thumbnail =findViewById(R.id.imageViewHero);


        seedsPlanted = findViewById(R.id.seedsPlanted);
        prevPalmsCount = findViewById(R.id.prevPalmsCount);
        plamsCount = findViewById(R.id.plamsCount);
        isTreesMissing = findViewById(R.id.isTreesMissing);
        missingTreesCount= findViewById(R.id.missingTreesCount);

        reasonType = findViewById(R.id.reasonType);
        expectedPlamsCount= findViewById(R.id.expectedPlamsCount);
        comments =findViewById(R.id.comments);
        comment_label=findViewById(R.id.commentsLabel);
        reason_label=findViewById(R.id.reasonTypeLabel);

        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();

        Age = findViewById(R.id.age_plot);
        id_plot = findViewById(R.id.plot);
        area = findViewById(R.id.palmArea);
        landMark = findViewById(R.id.landmark);
    }

    private void  setViews() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        RecyclerView.LayoutManager mLayoutManager_pest = new LinearLayoutManager(getApplicationContext());
        recycler_view_pest.setLayoutManager(mLayoutManager_pest);
        recycler_view_pest.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManager_dease = new LinearLayoutManager(getApplicationContext());
        recycler_view_desease.setLayoutManager(mLayoutManager_dease);
        recycler_view_desease.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManager_nut = new LinearLayoutManager(getApplicationContext());
        recyclerView_nut.setLayoutManager(mLayoutManager_nut);
        recyclerView_nut.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManager_fert_rec = new LinearLayoutManager(getApplicationContext());
        recycler_view_fert_rec_Details.setLayoutManager(mLayoutManager_fert_rec);
        recycler_view_fert_rec_Details.setItemAnimator(new DefaultItemAnimator());
        Age.setText(plot_Age );
        area.setText(location);
        id_plot.setText(plot_id);
        landMark.setText(landmarkCode);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }



    private void GetCropMaintenanceHistoryDetailsByCode() {
        mdilogue.show();
        String url = APIConstantURL.LOCAL_URL + "GetCropMaintenanceHistoryDetailsByPlotCode/" + plot_id;
        Log.e("url====",url);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mdilogue.dismiss();
                Log.d(TAG, "RESPONSE======" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "GetCrop ======" + jsonObject);




                    JSONObject healthPlantation_Data = jsonObject.getJSONObject("healthPlantationData");

                    Log.e("data===",healthPlantation_Data.getString("treesAppearance"));
                    treesAppearance.setText(healthPlantation_Data.getString("treesAppearance"));
                    treeGirth.setText(healthPlantation_Data.getString("treeGirth"));
                    treeHeight.setText(healthPlantation_Data.getString("treeHeight"));
                    fruitColor.setText(healthPlantation_Data.getString("fruitColor"));
                    fruitSize.setText(healthPlantation_Data.getString("fruitSize"));
                    fruitHyegiene.setText(healthPlantation_Data.getString("fruitHyegiene"));
                    plantationType.setText(healthPlantation_Data.getString("plantationType"));
                    Log.d(TAG, "healthPlantation_Data ======" + healthPlantation_Data);

                    Picasso.with(CropMaintanceVisitActivity.this).load(healthPlantation_Data.getString("plantationPictureLocation")).fit().centerCrop()
                            .placeholder(R.drawable.encylopedia)
                            .error(R.drawable.pole)
                            .into(thumbnail);
                    JSONArray nutrient_Data = jsonObject.getJSONArray("nutrientData");
                    if(nutrient_Data.length()==0){
                        recyclerView_nut.setVisibility(View.GONE);
                        nut_text.setVisibility(View.GONE);

                    }
                    else {
                        recyclerView_nut.setVisibility(View.VISIBLE);
                        nut_text.setVisibility(View.VISIBLE);

                    }
                    for (int i = 0; i < nutrient_Data.length(); i++) {

                        Nutrient_Model nutrient_List = new Nutrient_Model("","","",
                                "","","","");
                        JSONObject json = null;

                        try {
                            json = nutrient_Data.getJSONObject(i);

                            nutrient_List.setNutrientDeficiencyName(json.getString("nutrient"));
                            nutrient_List.setNameofchemicalapplied(json.getString("chemical"));
                            nutrient_List.setRecommendedFertilizer(json.getString("recommendedFertilizer"));

                            nutrient_List.setUOMName(json.getString("uomName"));
                            nutrient_List.setDosage(json.getString("dosage"));
                            //   nutrient_List.setRegisteredDate(json.getString("registeredDate"));

                            String date=json.getString("registeredDate");
                            //  String amount_total=json.getString("totalCost");
                            String  datee = date.substring(0, 10);
                            Log.e("datee===",datee);
                            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                Date oneWayTripDate = input.parse(datee);

                                datetimevalute=output.format(oneWayTripDate);
                                //datetimevalute.setText(output.format(oneWayTripDate));

                                Log.e("===============","======currentData======"+output.format(oneWayTripDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            nutrient_List.setRegisteredDate(datetimevalute);

                            nutrient_List.setComments(json.getString("comments"));

                            Log.e("ourput===6",json.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        nut_List.add(nutrient_List);


                    }

                    nut_adapter = new NutrientDataAdapter( CropMaintanceVisitActivity.this,nut_List);

                    //Adding adapter to recyclerview
                    recyclerView_nut.setAdapter(nut_adapter);



                    JSONObject uprootment_Data = jsonObject.getJSONObject("uprootmentData");
                  Log.e("uprootment_Data======", String.valueOf(uprootment_Data));
                    seedsPlanted.setText(uprootment_Data.getString("seedsPlanted"));
                    prevPalmsCount.setText(uprootment_Data.getString("prevPalmsCount"));
                    plamsCount.setText(uprootment_Data.getString("plamsCount"));
                    isTreesMissing.setText(uprootment_Data.getString("isTreesMissing"));
                    missingTreesCount.setText(uprootment_Data.getString("missingTreesCount"));
                    reasonType.setText(uprootment_Data.getString("reasonType"));
                    expectedPlamsCount.setText(uprootment_Data.getString("expectedPlamsCount"));
                    comments.setText(uprootment_Data.getString("comments"));

                    if (uprootment_Data.getString("reasonType").contains("null"))
                    {
                        //   Log.e("bbbbb",superHero.getmAmount());
                        reasonType.setVisibility(View.GONE);
                        reason_label.setVisibility(View.GONE);

                    }
                    else {
                        reasonType.setVisibility(View.VISIBLE);
                        reason_label.setVisibility(View.VISIBLE);
                    }
                    if (uprootment_Data.getString("comments").contains("null"))
                    {
                        //   Log.e("bbbbb",superHero.getmAmount());
                        comments.setVisibility(View.GONE);
                        comment_label.setVisibility(View.GONE);

                    }
                    else {
                        comments.setVisibility(View.VISIBLE);
                        comment_label.setVisibility(View.VISIBLE);
                    }


                    JSONArray pest_Data = jsonObject.getJSONArray("pestData");
                    if(pest_Data.length()==0){
                        recycler_view_pest.setVisibility(View.GONE);
                        pest_text.setVisibility(View.GONE);

                    }
                    else {
                        recycler_view_pest.setVisibility(View.VISIBLE);
                        pest_text.setVisibility(View.VISIBLE);

                    }
                    for (int i = 0; i < pest_Data.length(); i++) {

                        pest pest_List = new pest("","","","","","");
                        JSONObject json = null;

                        try {
                            json = pest_Data.getJSONObject(i);

                            pest_List.setPest(json.getString("pest"));
                            pest_List.setPestChemicals(json.getString("pestChemicals"));
                            pest_List.setRecommendedChemical(json.getString("recommendedChemical"));

                            pest_List.setDosage(json.getString("dosage"));
                            pest_List.setUOMName(json.getString("uomName"));
                            pest_List.setComments(json.getString("comments"));




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pestt_List.add(pest_List);


                    }
                    pestadapter = new pest_Adapter( CropMaintanceVisitActivity.this,pestt_List);

                    //Adding adapter to recyclerview
                    recycler_view_pest.setAdapter(pestadapter);




                    JSONArray disease_Data = jsonObject.getJSONArray("diseaseData");

                    if(disease_Data.length()==0){
                        recycler_view_desease.setVisibility(View.GONE);
                        diase_text.setVisibility(View.GONE);

                    }
                    else {
                        recycler_view_desease.setVisibility(View.VISIBLE);
                        diase_text.setVisibility(View.VISIBLE);

                    }

                    for (int i = 0; i < disease_Data.length(); i++) {


                        DiseaseDataModel dis_List = new DiseaseDataModel();
                        JSONObject json = null;

                        try {
                            json = disease_Data.getJSONObject(i);

                            dis_List.setDisease(json.getString("disease"));
                            dis_List.setDosage(json.getString("dosage"));
                            dis_List.setUOMName(json.getString("uomName"));
                            dis_List.setChemical(json.getString("chemical"));
                            dis_List.setComments(json.getString("comments"));





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        disease_List.add(dis_List);


                    }
                    disease_adapter = new DiseaseDataAdapter( CropMaintanceVisitActivity.this,disease_List);
                    recycler_view_desease.setAdapter(disease_adapter);



                    JSONArray fertilizerRecommendation_Details = jsonObject.getJSONArray("fertilizerRecommendationDetails");

                    Log.e("fert_rec_Data===", String.valueOf(fertilizerRecommendation_Details));
                    Log.e("fert_rec_Data===", String.valueOf(fertilizerRecommendation_Details.length()));
                    if(fertilizerRecommendation_Details.length()==0){
                        recycler_view_fert_rec_Details.setVisibility(View.GONE);
                        fert_rec_text.setVisibility(View.GONE);

                    }
                    else {
                        recycler_view_fert_rec_Details.setVisibility(View.VISIBLE);
                        fert_rec_text.setVisibility(View.VISIBLE);

                    }
                    for (int i = 0; i < fertilizerRecommendation_Details.length(); i++) {

                        fertilizerRecommendation fert_List = new fertilizerRecommendation();
                        JSONObject json = null;

                        try {
                            json = fertilizerRecommendation_Details.getJSONObject(i);

                            fert_List.setLastUpdatedDate(json.getString("updatedDate"));
                            fert_List.setComments(json.getString("comments"));
                            fert_List.setDosage(json.getString("dosage"));

                            fert_List.setUOMame(json.getString("uomName"));
                            fert_List.setRecommended_fertilizer(json.getString("recommendedFertilizerName"));




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        fert_rec_List.add(fert_List);


                    }
                    fertadapter = new fertilizerRecommendation_Adapter( CropMaintanceVisitActivity.this,fert_rec_List);

                    //Adding adapter to recyclerview
                    recycler_view_fert_rec_Details.setAdapter(fertadapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mdilogue.dismiss();
                if (error instanceof NetworkError) {
                    Log.i("one:" + TAG, error.toString());

                } else if (error instanceof ServerError) {
                    Log.i("two:" + TAG, error.toString());

                } else if (error instanceof AuthFailureError) {
                    Log.i("three:" + TAG, error.toString());

                } else if (error instanceof ParseError) {
                    Log.i("four::" + TAG, error.toString());

                } else if (error instanceof NoConnectionError) {
                    Log.i("five::" + TAG, error.toString());

                } else if (error instanceof TimeoutError) {
                    Log.i("six::" + TAG, error.toString());

                } else {
                    System.out.println("Checking error in else");
                }
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
