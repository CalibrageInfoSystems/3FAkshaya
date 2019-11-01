package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import java.util.ArrayList;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.Product_new;

public class producut_Adapter extends RecyclerView.Adapter<producut_Adapter.MyViewHolder> {
    private Context context;
    private ArrayList<Product_new> product_Listitems = new ArrayList<>();
 double  valueRounded;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name, quantity, amount, gst, item_cost;
        CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            product_name = view.findViewById(R.id.name);
            quantity = view.findViewById(R.id.qun_tity);
            amount = view.findViewById(R.id.Value);
            gst = view.findViewById(R.id.per_gst);
            item_cost = view.findViewById(R.id.item_cost);
            card_view = view.findViewById(R.id.card_view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }


    public producut_Adapter(Context context, ArrayList<Product_new> myProductsList) {
        this.context = context;

        this.product_Listitems = myProductsList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.card_view.setBackgroundResource(R.drawable.button_bg2);
        final Product_new dataa = product_Listitems.get(position);
        holder.product_name.setText(dataa.getProductname());
        holder.quantity.setText(dataa.getQuandity() + "");
Double amountt =(dataa.getWithGSTamount()) * dataa.getQuandity() ;


        valueRounded = Math.round(amountt * 100D) / 100D;
        holder.amount.setText(valueRounded + "");
        holder.gst.setText(String.valueOf(dataa.getGst() + ""));

        holder.item_cost.setText(dataa.getAmount() + "");


    }


    @Override
    public int getItemCount() {
        if (product_Listitems != null)
            return product_Listitems.size();
        else
            return 0;
    }


}

