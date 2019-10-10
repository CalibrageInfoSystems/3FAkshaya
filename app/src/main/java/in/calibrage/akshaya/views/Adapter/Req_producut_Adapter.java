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
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.Resproduct;
import in.calibrage.akshaya.views.actvity.Product_new;
import in.calibrage.akshaya.views.actvity.product_list;

public class Req_producut_Adapter extends RecyclerView.Adapter<Req_producut_Adapter.MyViewHolder> {
private Context context;
private List<Resproduct.ListResult> product_Listitems = new ArrayList<>();

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
        gst = view.findViewById(R.id.per_gst);
        item_cost = view.findViewById(R.id.item_cost);
        card_view = view.findViewById(R.id.card_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send selected contact in callback
                //  holidaylistener.onContactSelected(product_List.get(getAdapterPosition()));
            }
        });
    }
}



    public Req_producut_Adapter(Context context, List<Resproduct.ListResult> product_Listitems ) {
        this.context = context;

        this.product_Listitems = product_Listitems;

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

        holder.product_name.setText(product_Listitems.get(position).getName());
        holder.quantity.setText(product_Listitems.get(position).getQuantity() + "");
        holder.amount.setText(product_Listitems.get(position).getTotalAmount()+ "");
        holder.gst.setText(product_Listitems.get(position).getGstPercentage() + "");

        holder.item_cost.setText(product_Listitems.get(position).getAmount() + "");
double amount= Double.valueOf(holder.amount.getText().toString());
Integer quantity = Integer.parseInt(holder.quantity.getText().toString());
        double value =amount / quantity;
        holder.item_cost.setText(value + "");
    }


    @Override
    public int getItemCount() {
        if (product_Listitems != null)
            return product_Listitems.size();
        else
            return 0;
    }


}


