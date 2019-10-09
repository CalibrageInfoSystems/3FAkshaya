package in.calibrage.akshaya.views.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.ModelFert;
import in.calibrage.akshaya.views.actvity.Product_new;


public class ModelFertAdapterNew extends RecyclerView.Adapter<ModelFertAdapterNew.ViewHolder> {

    private ArrayList<Product_new> myProducts = new ArrayList<>();
    private ImageLoader imageLoader;
    private Context context;
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    Double itemcostt,itemcost;
    WindowManager.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
    //List of superHeroes
    List<ModelFert> list_products = new ArrayList<>();
    LayoutInflater mInflater;
    private OnClickAck onClickAck1;
    String Description, ProductName;
    private listner listner;

    public ModelFertAdapterNew(List<ModelFert> list_products, Context context,listner listner) {
        super();
        //Getting all the superheroes
        this.list_products = list_products;
        this.context = context;
        this.listner= listner;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fert_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ModelFert superHero = list_products.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.imageView2.setImageUrl(superHero.getImageUrl(), imageLoader);
        holder.imageView.setImageUrl(superHero.getImageUrl(), imageLoader);
        holder.currentFoodName.setText(superHero.getName());
        holder.currentCost.setText(context.getString(R.string.Rs) + (superHero.getPrice()));

        holder.disc.setText(superHero.getDescription());
        if (!TextUtils.isEmpty(superHero.getSize().toString())) {
            holder.size.setText(superHero.getSize() + "" + superHero.getUomType());
        } else {
            holder.size.setText("N/A");
        }
        holder.quantityText.setText("" + superHero.getmQuantity());

        if (superHero.getDescription().equals("null")) {

            holder.disc.setVisibility(View.INVISIBLE);

        } else {
            holder.disc.setVisibility(View.VISIBLE);

            holder.disc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Description = superHero.getDescription();
                    displayPopupWindow(view);
                }
            });
            holder.currentFoodName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductName = superHero.getName();
                    displayPopupWindow2(view);
                }
            });
        }
        holder.actual_amt.setPaintFlags(holder.actual_amt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.actual_amt.setText(context.getString(R.string.Rs) + superHero.getDiscountedPrice());
        Log.e("aaaaaaaaaaaa", superHero.getmAmount());
        if (superHero.getmAmount().equals("null")) {
            holder.actual_amt.setVisibility(View.INVISIBLE);
        } else {
            holder.actual_amt.setVisibility(View.VISIBLE);
        }


        String powers = "";

        holder.addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 *
                 * user Need to add items to Cart
                 *
                 * */


                if (contains(myProducts, superHero.getId())) {
                    for (int i = 0; i < myProducts.size(); i++) {
                        if (myProducts.get(i).getProductID() == (superHero.getId())) {
                            Product_new product_new = myProducts.get(i);
                            Integer currentQTY = product_new.getQuandity();
                            product_new.setQuandity(currentQTY + 1);
                            myProducts.set(i, product_new);
                            Log.d("PRODUCT ", "---- analysis -----(Update new)  " + product_new.getQuandity());
                            superHero.setmQuantity(product_new.getQuandity());
                           // holder.quantityText.setText("x " + product_new.getQuandity());
                            notifyItemChanged(position);

                        }
                    }

                } else {
                    if (superHero.getmAmount().equals("null")) {
                         itemcost= Double.valueOf(superHero.getPrice());
                    }
                    else {
                         itemcost = Double.valueOf(superHero.getmAmount());
                    }

                    Log.d("PRODUCT ", "---- analysis -----(itemcost)  :" + itemcost);
                    Double gst = Double.valueOf(superHero.getgst());
                    Log.d("PRODUCT ", "---- analysis -----(gst)  :" + gst);
                    //Double onlygst = (gst / itemcost) * 100;
                    double onlygst = (itemcost / 100.0f) * gst;
                    Log.d("PRODUCT ", "---- analysis -----(withgstitemcost)  :" + onlygst);
                    Double finalwithGST = itemcost + onlygst;

                    DecimalFormat df = new DecimalFormat("####0.00");
           //   String itemcost= df.format(itemcostt);

                    String total_amount =   df.format(finalwithGST);
                    Log.d("PRODUCT ", "---- analysis -----  " + total_amount);
                    myProducts.add(new Product_new(1, superHero.getName(), itemcost, total_amount, superHero.getgst(), itemcost, superHero.getId(),superHero.getSize()));
                    Log.d("PRODUCT ", "---- analysis -----(Add new)  ");
                    superHero.setmQuantity(1);
                    // holder.quantityText.setText("x " + product_new.getQuandity());
                    notifyItemChanged(position);
                }
                caliculateTotalAmount();
                listner.updated(position,myProducts);
            }
        });

        holder.subtractMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Roja====,", "clicked----");
                if (contains(myProducts, superHero.getId())) {
                    for (int i = 0; i < myProducts.size(); i++) {
                        if (myProducts.get(i).getProductID() == (superHero.getId())) {
                            Product_new product_new = myProducts.get(i);
                            if(product_new.getQuandity() >1)
                            {
                                Integer currentQTY = product_new.getQuandity();
                                product_new.setQuandity(currentQTY - 1);
                                myProducts.set(i,product_new);
                                superHero.setmQuantity(product_new.getQuandity());
                                // holder.quantityText.setText("x " + product_new.getQuandity());
                                notifyItemChanged(position);
                            }
                            else {
                                myProducts.remove(i);
                                superHero.setmQuantity(0);
                                notifyItemChanged(position);
                            }

                            Log.d("PRODUCT ", "---- analysis -----(Remove)  " + product_new.getQuandity());
                        }
                    }

                }


                caliculateTotalAmount();
                listner.updated(position,myProducts);

            }
        });

    }

    private void displayPopupWindow(View anchorView) {
        PopupWindow popup = new PopupWindow(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_content, null);
        TextView text = layout.findViewById(R.id.tvCaption);
        text.setText(Description);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
    }

    private void displayPopupWindow2(View anchorView) {
        PopupWindow popup = new PopupWindow(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_content, null);
        TextView text = layout.findViewById(R.id.tvCaption);
        text.setText(ProductName);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
    }

    @Override
    public int getItemCount() {
        return list_products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView imageView, imageView2;
        public TextView currentFoodName,
                currentCost,
                quantityText,
                actual_amt,

        remove_text;
        public ImageView addMeal, subtractMeal;
        public ImageView thumbnail;
        public TextView disc, size;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            imageView2 = (NetworkImageView) itemView.findViewById(R.id.thumbnail2);
            currentFoodName = (TextView) itemView.findViewById(R.id.selected_food_name);
            currentCost = (TextView) itemView.findViewById(R.id.selected_food_amount);
            subtractMeal = (ImageView) itemView.findViewById(R.id.minus_meal);
            quantityText = (TextView) itemView.findViewById(R.id.quantity);
            addMeal = (ImageView) itemView.findViewById(R.id.plus_meal);
            //  remove_text = (TextView) itemView.findViewById(R.id.ggd);
            actual_amt = (TextView) itemView.findViewById(R.id.actual_amt);
            disc = (TextView) itemView.findViewById(
                    R.id.desc);
            size = (TextView) itemView.findViewById(
                    R.id.size);
        }
    }

    public void setOnListener(OnClickAck OListener) {
        this.onClickAck1 = OListener;
    }

    public interface OnClickAck {
        void setOnClickAckListener(String status, int position, Boolean ischecked, NetworkImageView img);
    }


    public void caliculateTotalAmount() {
        Double allitemscost= 0.0;
        int allproducts =0;
        for (Product_new product : myProducts) {
           Double oneitem = product.getQuandity()* Double.parseDouble(product.getWithGSTamount());
           allitemscost = oneitem+allitemscost;
           Log.d("Product","total Proce :"+allitemscost);
           int onitem = product.getQuandity();
           allproducts= allproducts+onitem;
            Log.d("Product","totalitems :"+allproducts);
        }


        SharedPrefsData.getInstance(context).updateStringValue(context,"amount",allitemscost+"");
    }

    boolean contains(ArrayList<Product_new> list, int name) {
        for (Product_new item : list) {
            if (item.getProductID() == name) {
                return true;
            }
        }
        return false;
    }

    public interface  listner
    {
        void updated(int po,ArrayList<Product_new> myProducts);
    }

}