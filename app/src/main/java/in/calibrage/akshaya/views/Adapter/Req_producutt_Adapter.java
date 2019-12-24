package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.Resproduct;

public class Req_producutt_Adapter extends RecyclerView.Adapter<Req_producutt_Adapter.MyViewHolder> {
private Context context;
private List<Resproduct.ListResult> product_Listitems = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("####0.00");
        String holiday_id, name;
        String Enduser, IsSuccess;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView product_name, quantity, amount, gst, item_cost,cgst,sgst;
    CardView card_view;

    public MyViewHolder(View view) {
        super(view);
        product_name = view.findViewById(R.id.name);
        quantity = view.findViewById(R.id.qun_tity);
        amount = view.findViewById(R.id.Value);
        // gst = view.findViewById(R.id.per_gst);
        item_cost = view.findViewById(R.id.item_cost);
        card_view = view.findViewById(R.id.card_view);
        cgst =view.findViewById(R.id.cgst);
        sgst =view.findViewById(R.id.sgst);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}


    public Req_producutt_Adapter(Context context, List<Resproduct.ListResult> product_Listitems) {
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
        holder.amount.setText(df.format(product_Listitems.get(position).getTotalAmount()));
        // holder.gst.setText(product_Listitems.get(position).getGstPercentage()+"");
        holder.cgst.setText(product_Listitems.get(position).getCgstPercentage()+"");
        holder.sgst.setText(product_Listitems.get(position).getSgstPercentage()+"");
        //  holder.item_cost.setText(df.format(product_Listitems.get(position).getAmount()));
        double amount = Double.valueOf(product_Listitems.get(position).getAmount());
        Log.e("amount===",amount+"");
        Integer quantity = Integer.parseInt(holder.quantity.getText().toString());
        double value = amount / quantity;
        holder.item_cost.setText(df.format(value));
    }


    @Override
    public int getItemCount() {
        if (product_Listitems != null)
            return product_Listitems.size();
        else
            return 0;
    }


}

