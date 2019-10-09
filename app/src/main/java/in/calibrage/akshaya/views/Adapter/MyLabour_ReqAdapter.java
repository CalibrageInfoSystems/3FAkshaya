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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.labour_req_response;

public class MyLabour_ReqAdapter extends RecyclerView.Adapter<MyLabour_ReqAdapter.ViewHolder> {

    private List<labour_req_response.ListResult> labourlist_Set = new ArrayList<>();
    public Context mContext;
String request_date,prefferdate;
    // RecyclerView recyclerView;
    public MyLabour_ReqAdapter(List<labour_req_response.ListResult> labourlist_Set, Context ctx) {
        this.labourlist_Set = labourlist_Set;
        this.mContext = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.my_req_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtPlotId.setText(labourlist_Set.get(position).getPlotDetails().getPlotCode());
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oneWayTripDate = input.parse(labourlist_Set.get(position).getLabourDetails().getStartDate());
            Date prefferdatee = input.parse(labourlist_Set.get(position).getLabourDetails().getUpdatedDate());
            request_date = output.format(oneWayTripDate);
            prefferdate=output.format(prefferdatee);
            //datetimevalute.setText(output.format(oneWayTripDate));

            Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtDate.setText(request_date);
        //   holder.txtTime.setText(superHero.getTime());
        holder.txtDateNTime.setText(labourlist_Set.get(position).getPlotDetails().getPlotSize()+" "+"Ha");
        holder.txtReqDate.setText(labourlist_Set.get(position).getPlotDetails().getPlotVillageName());
        holder.txtApproveDate.setText(prefferdate);
        holder.txtStatus.setText(labourlist_Set.get(position).getLabourDetails().getStatusType());
        holder.txtname.setText(labourlist_Set.get(position).getLabourDetails().getServiceTypes());
    }

    @Override
    public int getItemCount() {
        return labourlist_Set.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txtPlotId;
        public TextView txtDate;
        public TextView txtTime;
        public TextView txtComments;
        public TextView txtDateNTime;
        public TextView txtReqDate;
        public TextView txtApproveDate;
        public TextView txtStatus;
        public TextView txtname;
        public TextView txtMobileNo;
        public TextView txtPin;


        public ViewHolder(View itemView) {
            super(itemView);


            txtPlotId = itemView.findViewById(R.id.plotId);
            txtDate = itemView.findViewById(R.id.req_date);
            //  txtTime = itemView.findViewById(R.id.time);
            //  txtComments = itemView.findViewById(R.id.comments);
            txtDateNTime = itemView.findViewById(R.id.dateNTime);
            txtReqDate = itemView.findViewById(R.id.village_name);
            txtApproveDate = itemView.findViewById(R.id.status_type);
            txtStatus = itemView.findViewById(R.id.status);
            txtname = itemView.findViewById(R.id.name);
            //  txtMobileNo = itemView.findViewById(R.id.mobileNo);
            //   txtPin = itemView.findViewById(R.id.pin);*/


        }


    }
}



