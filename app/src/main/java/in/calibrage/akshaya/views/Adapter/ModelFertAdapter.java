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

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.ModelFert;


public class ModelFertAdapter extends RecyclerView.Adapter<ModelFertAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    WindowManager.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
    //List of superHeroes
    List<ModelFert> list_products;
    LayoutInflater mInflater;
    private OnClickAck onClickAck1 ;
    String Description,ProductName;
    public ModelFertAdapter(List<ModelFert> list_products, Context context){
        super();
        //Getting all the superheroes
        this.list_products = list_products;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fert_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ModelFert superHero =  list_products.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.imageView2.setImageUrl(superHero.getImageUrl(), imageLoader);
        holder.imageView.setImageUrl(superHero.getImageUrl(), imageLoader);
        holder.currentFoodName.setText(superHero.getName());
        holder.currentCost.setText(context.getString(R.string.Rs)+ (superHero.getPrice()));
//        holder.currentCost.setText(superHero.getDiscountedPrice());
        //   holder.actual_amt.setText(superHero.getDiscountedPrice());
        //  holder.disc.setText(superHero.getDescription());
        holder.disc.setText(superHero.getDescription());
        if(!TextUtils.isEmpty(superHero.getSize()))
        {
            holder.size.setText(superHero.getSize()+" "+superHero.getUomType());
        }else {
            holder.size.setText("N/A");
        }

        holder.quantityText.setText(""+ superHero.getmQuantity());

        //holder.remove_text.setText(superHero.getId());
        if (superHero.getDescription().equals("null"))
        {

            holder.disc.setVisibility(View.INVISIBLE);

        }
        else {
            holder.disc.setVisibility(View.VISIBLE);

            holder.disc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Description  = superHero.getDescription();
//                    Toast toast=Toast.makeText(context,superHero.getDescription(),Toast.LENGTH_SHORT);
//                    toast.setMargin(50,50);
//                    toast.show();
                    displayPopupWindow(view);
                }
            });
            holder.currentFoodName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductName = superHero.getName();
//
                    displayPopupWindow2(view);
                }
            });
        }


        //  holder.textViewCreatedBy.setText(superHero.getCreatedBy());
        //  holder.textViewFirstAppearance.setText(superHero.getFirstAppearance());
        holder.actual_amt.setPaintFlags(holder.actual_amt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.actual_amt.setText(context.getString(R.string.Rs) + superHero.getDiscountedPrice());

        Log.e("aaaaaaaaaaaa",superHero.getmAmount());

        if (superHero.getmAmount().equals("null"))
        {

            holder.actual_amt.setVisibility(View.INVISIBLE);

        }
        else {
            holder.actual_amt.setVisibility(View.VISIBLE);
        }


        String powers = "";

//        for(int i = 0; i<superHero.getPowers().size(); i++){
//            powers+= superHero.getPowers().get(i);
//        }
        holder.addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                superHero.addToQuantity();
                holder.quantityText.setText("x "+ superHero.getmQuantity());
                onClickAck1.setOnClickAckListener("add",holder.getAdapterPosition(),Boolean.TRUE,holder.imageView);
                //     holder.currentCost.setText("GH"+ (superHero.getPrice() * superHero.getmQuantity()));
                Log.e("product===1","" + superHero.getId() + superHero.getmQuantity());
                notifyDataSetChanged();
            }
        });

        holder.subtractMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                superHero.removeFromQuantity();
                holder.quantityText.setText("x "+superHero.getmQuantity());
                onClickAck1.setOnClickAckListener("remove",holder.getAdapterPosition(),Boolean.FALSE,holder.imageView);

                Log.e("product===2","id" + superHero.getId()+ " quantity " +  superHero.getmQuantity());
                //     holder.currentCost.setText("GH"+ (superHero.getPrice() * superHero.getmQuantity()));
                notifyDataSetChanged();
            }
        });
        //  holder.textViewPowers.setText(powers);
    }
    private void displayPopupWindow(View anchorView) {
        PopupWindow popup = new PopupWindow(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View layout = inflater.inflate( R.layout.popup_content, null );
        TextView text =layout.findViewById(R.id.tvCaption);
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View layout = inflater.inflate( R.layout.popup_content, null );
        TextView text =layout.findViewById(R.id.tvCaption);
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

    class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView imageView,imageView2;
        public  TextView currentFoodName,
                currentCost,
                quantityText,
                actual_amt,

        remove_text;
        public  ImageView addMeal,subtractMeal;
        public ImageView thumbnail;
        public TextView disc,size;

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
        void setOnClickAckListener(String status, int position, Boolean ischecked,NetworkImageView img);
    }

}