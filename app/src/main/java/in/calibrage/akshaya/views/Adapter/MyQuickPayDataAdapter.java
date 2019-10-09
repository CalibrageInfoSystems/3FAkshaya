package in.calibrage.akshaya.views.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.Resquickpay;


public class MyQuickPayDataAdapter extends RecyclerView.Adapter<MyQuickPayDataAdapter.ViewHolder>{

    List<Resquickpay.ListResult> list;
    public Context mContext;
String datetimevaluereq;
    // RecyclerView recyclerView;
    public MyQuickPayDataAdapter(  List<Resquickpay.ListResult> list, Context ctx) {
        this.list = list;
        this.mContext=ctx;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.quick_req_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oneWayTripDate = input.parse(list.get(position).getReqCreatedDate());

            datetimevaluereq = output.format(oneWayTripDate);
            //datetimevalute.setText(output.format(oneWayTripDate));

            Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.requestCode.setText(list.get(position).getRequestCode());
        holder.req_date.setText(datetimevaluereq);
        holder.statusType.setText(list.get(position).getStatusType());
        holder.amount.setText(list.get(position).getTotalCost()+"");
// holder.txtPin.setText(superHero.getPin());
        String powers = "";

/* for(int i = 0; i<superHero.getPowers().size(); i++){
powers+= superHero.getPowers().get(i);
}*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {



        public TextView requestCode;
        public TextView req_date;
        public TextView statusType;
        public TextView amount;


        public ViewHolder(View itemView) {
            super(itemView);



            requestCode = itemView.findViewById(R.id.requestCode);
            req_date = itemView.findViewById(R.id.reqCreatedDate);
            statusType = itemView.findViewById(R.id.statusType);
            amount = itemView.findViewById(R.id.amount);
// txtMobileNo = itemView.findViewById(R.id.mobileNo);
// txtPin = itemView.findViewById(R.id.pin);


        }


    }
}

