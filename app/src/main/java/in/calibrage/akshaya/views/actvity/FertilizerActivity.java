package in.calibrage.akshaya.views.actvity;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.BaseActivity;
import in.calibrage.akshaya.common.CircleAnimationUtil;
import in.calibrage.akshaya.models.ModelFert;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.views.Adapter.ModelFertAdapter;

public class FertilizerActivity extends BaseActivity implements ModelFertAdapter.OnClickAck {

    private RecyclerView recyclerView;
    private ModelFertAdapter adapter;
    List<Integer> selectedId_List = new ArrayList<>();
    List<Integer> selectedQty_List = new ArrayList<>();
    List<String> selecteditem_List = new ArrayList<>();
    List<Integer> selectedgst_List = new ArrayList<>();
    List<Integer> amount_List = new ArrayList<>();

    List<String> selectedsize_List = new ArrayList<>();
    String amount;
    String dis_price, Farmer_code;
    final Context context = this;
    Button button, btn_next;
    TextView mealTotalText, txt_recomandations, txt_count;
    private String TAG = "FertilizerActivity";
    private List<ModelFert> product_list = new ArrayList<>();
    private ProgressDialog dialog;
    int SPLASH_DISPLAY_DURATION = 500;

    private ImageView cartButtonIV;
    Integer Id, quantity;
    int price_final;
    int Count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fertilizer);
        dialog = new ProgressDialog(this);
        txt_recomandations = findViewById(R.id.txt_recomandations);
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
        //recyclerView.setAdapter(adapter);
//
//        Button buttonBarCodeScan = findViewById(R.id.confirm);

        Getstate();
        cartButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (validations()) {
                            Intent i = new Intent(FertilizerActivity.this, Fert_godown_list.class);

                            i.putExtra("Ids", (Serializable) selectedId_List);
                            i.putExtra("quantity", (Serializable) selectedQty_List);
                            i.putExtra("item_names", (Serializable) selecteditem_List);
                            i.putExtra("item_amount", (Serializable) amount_List);
                            i.putExtra("gst_per", (Serializable) selectedgst_List);
                            i.putExtra("procuct_size", (Serializable) selectedsize_List);
                            i.putExtra("amount", amount);

                            startActivity(i);

                            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

                        }
                    }
                }, SPLASH_DISPLAY_DURATION);

                //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //  startActivity(i);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validations()) {
                    Intent i = new Intent(FertilizerActivity.this, Fert_godown_list.class);
                    i.putExtra("Ids", (Serializable) selectedId_List);
                    i.putExtra("quantity", (Serializable) selectedQty_List);
                    i.putExtra("item_names", (Serializable) selecteditem_List);
                    i.putExtra("item_amount", (Serializable) amount_List);
                    i.putExtra("gst_per", (Serializable) selectedgst_List);
                    i.putExtra("procuct_size", (Serializable) selectedsize_List);
                    i.putExtra("amount", amount);
                    startActivity(i);

                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

                }
            }
        });
        txt_recomandations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FertilizerActivity.this, RecommendationActivity.class));
            }
        });
    }

    private boolean validations() {
        if (selectedQty_List.isEmpty()) {
            showDialog(FertilizerActivity.this, getResources().getString(R.string.select_product_toast));
            return false;
        }
        return true;
    }

    private void Getstate() {
        dialog.setMessage("Loading, please wait....");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        String url = APIConstantURL.LOCAL_URL + "Products/GetProductsByCategoryId/1";

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

                superHero.setgst(json.getInt("gstPercentage"));
                superHero.setSize(json.getString("size"));
                superHero.setId(json.getInt("id"));
                superHero.setUomType(json.getString("uomType"));
                Log.e("uom===", json.getString("uomType"));
                int price_finall = json.getInt("price");
                Log.e("price_final====", String.valueOf(price_finall));
                String final_price = Integer.toString(price_finall);
                Log.e("final_price====", String.valueOf(final_price));
                dis_price = json.getString("discountedPrice");
                Log.e("dis_price====", dis_price);


                ArrayList<String> powers = new ArrayList<String>();


                superHero.setPowers(powers);
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

            adapter = new ModelFertAdapter(product_list, this);
            Log.d(TAG, "listSuperHeroes======" + product_list);
            //Adding adapter to recyclerview
            recyclerView.setAdapter(adapter);
            adapter.setOnListener(FertilizerActivity.this);
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    setMealTotal();
                }
            });
        }
    }


    public long calculateMealTotal() {
        try {
            long mealTotal = 0;
            for (ModelFert order : product_list) {

                mealTotal = mealTotal + order.getmQuantity() * order.getPrice();


                quantity = order.getmQuantity();
                Id = order.getId();
                Log.e("quantity kk===", quantity.toString());
                Log.e("Id-=== kk", Id.toString());
//            selectedId_List = new ArrayList<>();
//            selectedQty_List = new ArrayList<>();

            /*if (order.getmQuantity() > 0 ){

                int pos = -1;

                for (int i = 0; i< selectedId_List.size(); i++){
                    if (selectedId_List.get(i) == Id){
                        pos = i;
                    }else{
                        pos = i++;
                    }

                }

                if (pos != -1){
                    selectedId_List.set(pos, Id);
                    selectedQty_List.set(pos, quantity);
                }else {
                    selectedId_List.add(Id);
                    selectedQty_List.add(quantity);
                }


                Log.e("product===357","id===  " + selectedId_List + " quantity ===" +  selectedQty_List);
                Log.e("product===339","id===  " + Id + " quantity ===" +  quantity);

            }*/
            }

//

            return mealTotal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void setMealTotal() {
        mealTotalText.setText(" " + calculateMealTotal());
        // mealTotalText.setText("Rs"+" "+ calculateMealTotal());
        amount = mealTotalText.getText().toString();
        Log.e("amount-===", mealTotalText.getText().toString());
    }

    @Override
    public void setOnClickAckListener(String status, int position, Boolean ischecked, NetworkImageView img) {
        if (ischecked) {
            ImageView cartButtonIV = findViewById(R.id.cartButtonIV);

            makeFlyAnimation(img);
            if (selectedId_List.size() > 0) {

                if (selectedId_List.contains(product_list.get(position).getId())) {
                    selectedId_List.set(selectedId_List.indexOf(product_list.get(position).getId()), product_list.get(position).getId());
                    selectedQty_List.set(selectedId_List.indexOf(product_list.get(position).getId()), product_list.get(position).getmQuantity());
                    selecteditem_List.set(selectedId_List.indexOf(product_list.get(position).getId()), (product_list.get(position).getName()));
                    amount_List.set(selectedId_List.indexOf(product_list.get(position).getId()), (product_list.get(position).getPrice()));
                    selectedgst_List.set(selectedId_List.indexOf(product_list.get(position).getId()), (product_list.get(position).getgst()));
                    selectedsize_List.set(selectedId_List.indexOf(product_list.get(position).getId()), (product_list.get(position).getSize()));
                    Count= Count+1;
                    txt_count.setText(Count+"");

                } else {
                    selectedId_List.add(product_list.get(position).getId());
                    selectedQty_List.add(product_list.get(position).getmQuantity());
                    selecteditem_List.add(product_list.get(position).getName());
                    amount_List.add(product_list.get(position).getPrice());
                    selectedgst_List.add(product_list.get(position).getgst());
                    selectedsize_List.add(product_list.get(position).getSize());
                    Count= Count+1;
                    txt_count.setText(Count+"");

                }
            } else {

                selectedId_List.add(product_list.get(position).getId());
                selectedQty_List.add(product_list.get(position).getmQuantity());
                selecteditem_List.add(product_list.get(position).getName());
                amount_List.add(product_list.get(position).getPrice());
                selectedgst_List.add(product_list.get(position).getgst());
                selectedsize_List.add(product_list.get(position).getSize());

                Count= Count+1;
                txt_count.setText(Count+"");
            }

            Log.e(" add selectedId_List-==", selectedId_List.toString());
            Log.e("add selectedQty_List-==", selectedQty_List.toString());
            Log.e("add selectedQty_List-==", selecteditem_List.toString());
            Log.e("amount_List_List-==", amount_List.toString());
            //    Toast.makeText(FertilizerActivity.this,selectedId_List.toString(),Toast.LENGTH_SHORT).show();

        } else {
            if (selectedId_List.size() > 0) {



                if (product_list.get(position).getmQuantity() >= 1) {
                    selectedQty_List.set(selectedId_List.indexOf(product_list.get(position).getId()), product_list.get(position).getmQuantity());
                    if(Count >0)
                    {
                        Count= Count-1;
                        txt_count.setText(Count+"");
                    }
                } else {

                    int a = selectedId_List.indexOf(product_list.get(position).getId());
                    if (a < 0) {
                        Log.e(" =====", "negative");

                        return;
                    }
                    Log.e(TAG, "test " + a);
                    Log.e(TAG, "test " + selectedQty_List.size());
                    Log.e(TAG, "test " + selectedQty_List.toString());

                    selectedQty_List.remove(a);

                    selectedId_List.remove(selectedId_List.indexOf(product_list.get(position).getId()));
                    Log.d(TAG, "--------- analysis ---->>(< 0) Item Count"+selectedId_List.size() );
                    if(Count >0)
                    {
                        Count= Count-1;
                        txt_count.setText(Count+"");
                    }

                }


//                Log.e(" remove selectedId_List", selectedId_List.toString());
//                Log.e("remove selectedQty_List", selectedQty_List.toString());
            }


        }
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

}
