package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.product;

public class producut_AdapterNew extends RecyclerView.Adapter<producut_AdapterNew.MyViewHolder> {
    private Context context;
    private List<product> product_List;

    String holiday_id, name;
    String Enduser, IsSuccess;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name, quantity, amount, gst, item_cost;
        CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            product_name = view.findViewById(R.id.name);
            quantity = view.findViewById(R.id.qun_tity);
            amount = view.findViewById(R.id.Value);
          //  gst = view.findViewById(R.id.per_gst);
            item_cost = view.findViewById(R.id.item_cost);
            card_view = view.findViewById(R.id.card_view);
        }
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
        final product dataa = product_List.get(position);
        holder.product_name.setText(dataa.getname());
        holder.quantity.setText(dataa.getquantity() + "");
        holder.amount.setText(dataa.getamount() + "");
       // holder.gst.setText(String.valueOf(dataa.getgst()));
        int quantity = dataa.getquantity();
        int amount = dataa.getamount();
        int value = amount / quantity;
        Log.e("value===", String.valueOf(value));
        holder.item_cost.setText(value + "");
        holiday_id = dataa.getid();


    }


    @Override
    public int getItemCount() {

        if (product_List != null)
            return product_List.size();
        else
            return 0;
    }


}

