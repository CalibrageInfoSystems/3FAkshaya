package in.calibrage.akshaya.views.actvity;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.CircleAnimationUtil;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.ModelFert;
import in.calibrage.akshaya.models.Product_new;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.views.Adapter.ModelFertAdapter;
import in.calibrage.akshaya.views.Adapter.ModelFertAdapterNew;

public class PoleActivity extends BaseActivity implements ModelFertAdapter.OnClickAck, ModelFertAdapterNew.listner {
    private RecyclerView recyclerView;
    private ModelFertAdapterNew adapter;
    static ArrayList<Product_new> myProductsList = new ArrayList<>();
    String dis_price, Farmer_code;
    final Context context = this;
    Button button, btn_next;
    TextView mealTotalText, txt_count;
    private String TAG = "PoleActivity";
    List<ModelFert> product_list = new ArrayList<>();
    private ProgressDialog dialog;
    private ImageView cartButtonIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pole);
        dialog = new ProgressDialog(this);
        txt_count = findViewById(R.id.txt_count);
        btn_next = findViewById(R.id.btn_next);
        cartButtonIV = findViewById(R.id.cartButtonIV);
        ImageView backImg = (ImageView) findViewById(R.id.back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");       // Saving string data of your editext

        recyclerView = (RecyclerView) findViewById(R.id.fer_recycler_view);
        mealTotalText = (TextView) findViewById(R.id.meal_total);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
            if (isOnline())
                Getstate();
        else {
            showDialog(PoleActivity.this,getResources().getString(R.string.Internet));

        }



       cartButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (myProductsList.size() > 0) {


                        Intent i = new Intent(PoleActivity.this, pole_godown_list.class);
                        i.putExtra("Total_amount", mealTotalText.getText());
                        startActivity(i);
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                    }
                    else{
                        showDialog(PoleActivity.this, getResources().getString(R.string.select_product_toast));
                    }

                }

        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (myProductsList.size() > 0) {


                        Intent i = new Intent(PoleActivity.this, pole_godown_list.class);
                        i.putExtra("Total_amount", mealTotalText.getText());
                        startActivity(i);
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                    }
                    else{
                        showDialog(PoleActivity.this, getResources().getString(R.string.select_product_toast));
                    }

                }

        });
//
    }
    private void Getstate() {
        dialog.setMessage("Loading, please wait....");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        String url = APIConstantURL.LOCAL_URL + "Products/GetProductsByCategoryId/2";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "RESPONSE======" + response);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "GetProductsByCategoryId ======" + jsonObject);
                    String success = jsonObject.getString("isSuccess");
                    Log.d(TAG, "success======" + success);
                    JSONArray alsoKnownAsArray = jsonObject.getJSONArray("listResult");
                    parseData(alsoKnownAsArray);


                    String affectedRecords = jsonObject.getString("affectedRecords");
                    Log.d(TAG, "GetProductsByCategoryId======" + affectedRecords);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
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
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {

            ModelFert superHero = new ModelFert();
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);
                superHero.setName(json.getString("name"));
                superHero.setDiscountedPrice(json.getString("actualPrice"));
                superHero.setmAmount(json.getString("discountedPrice"));
                superHero.setPrice(json.getInt("price"));
                superHero.setImageUrl(json.getString("imageUrl"));
                superHero.setDescription(json.getString("description"));
                Double size = json.getDouble("size");
                Log.d(TAG, "--- Size ----" + size);
//                superHero.setSize(json.getString("size"));
                superHero.setSize(size);


                superHero.setId(json.getInt("id"));
                superHero.setUomType(json.getString("uomType"));
                Log.e("uom===", json.getString("uomType"));
                int price_finall = json.getInt("price");
                Log.e("price_final====", String.valueOf(price_finall));
                String final_price = Integer.toString(price_finall);
                Log.e("final_price====", String.valueOf(final_price));
                dis_price = json.getString("discountedPrice");
                Log.e("dis_price====", dis_price);

                superHero.setgst(json.getInt("gstPercentage"));
                ArrayList<String> powers = new ArrayList<String>();



                // superHero.setfarmerCode(labourDetails.getString("farmerCode"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            product_list.add(superHero);
            //   selected_list.add();

                                /*String plotCode = productObject.getString("plotCode");
                                String plotMandalName = productObject.getString("plotMandalName");
                                Log.d(TAG,"RESPONSE plotCode======"+ plotCode);
                                Log.d(TAG,"RESPONSE plotMandalName======"+ plotMandalName);*/

            adapter = new ModelFertAdapterNew(product_list, this, this);
            Log.d(TAG, "listSuperHeroes======" + product_list);
            //Adding adapter to recyclerview
            recyclerView.setAdapter(adapter);
//            adapter.setOnListener(PoleActivity.this);
//            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//                @Override
//                public void onChanged() {
//                    super.onChanged();
//                    setMealTotal();
//                }
//            });

        }
    }
    @Override
    public void setOnClickAckListener(String status, int position, Boolean ischecked, NetworkImageView img) {
    }
    @Override
    public void updated(int po, ArrayList<Product_new> myProducts) {
        SharedPrefsData.saveCartitems(context,myProducts);


        myProductsList = myProducts;
        CommonUtil.Productitems =myProductsList;
        Double allitemscost = 0.0;
        int allproducts = 0;

        for (Product_new product : myProducts) {
            Double oneitem = product.getQuandity() *  Double.parseDouble(product.getWithGSTamount());
            allitemscost = oneitem + allitemscost;
            Log.d("Product", "total Proce :" + allitemscost);
            int onitem = product.getQuandity();
            allproducts = allproducts + onitem;
            Log.d("Product", "totalitems :" + allproducts);


        }
        txt_count.setText(allproducts + "");
        String total_amount = allitemscost.toString();
        mealTotalText.setText(total_amount);
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    private void makeFlyAnimation(ImageView targetView) {

        // RelativeLayout destView = (RelativeLayout) findViewById(R.id.cartRelativeLayout);
        ImageView destView = findViewById(R.id.cartButtonIV);

        new CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setCircleDuration(500).setMoveDuration(800).setDestView(destView).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //  addItemToCart();
                // Toast.makeText(MainActivity.this, "Continue Shopping...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();


    }
    public static ArrayList<Product_new> getProducts(){
        return myProductsList;
    }
}
