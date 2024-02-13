package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.AnimationUtil;
import in.calibrage.akshaya.models.LabourRecommendationsModel;
import in.calibrage.akshaya.models.res_plotdetails;
import in.calibrage.akshaya.views.actvity.LabourActivity;
import in.calibrage.akshaya.views.actvity.LabourRecommendationsActivity;

public class PlotDetailsAdapter extends RecyclerView.Adapter<PlotDetailsAdapter.ViewHolder> {

    List<LabourRecommendationsModel.ListResult> recomm_Set;
    public Context mContext;
    DecimalFormat df = new DecimalFormat("####0.00");


    public PlotDetailsAdapter(List<LabourRecommendationsModel.ListResult> recomm_Set, Context context) {
        this.recomm_Set = recomm_Set;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.plot_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.textViewplotId.setText(": " + recomm_Set.get(position).getSurveyNumber());
        holder.textViewpalmArea.setText(": " + df.format(recomm_Set.get(position).getPalmArea()) + " " + "Ha ("+ df.format(recomm_Set.get(position).getPalmArea()*2.5) + " Acre)");
        holder.textViewLocation.setText(": " + recomm_Set.get(position).getVillageName());
       if( recomm_Set.get(position).getLandMark()!=null)
        holder.plotLandmark.setText(": " + recomm_Set.get(position).getLandMark());
       else
           holder.landmark_layout.setVisibility(View.GONE);
        holder.plot_Mandal.setText(": " + recomm_Set.get(position).getMandalName());
        holder.plotvillage.setText(": " + recomm_Set.get(position).getVillageName());
        holder.plot_District.setText(": " + recomm_Set.get(position).getDistrictName());

        holder.date_plantation.setText(": " + recomm_Set.get(position).getDateOfPlanting());
        holder.plot_code.setText(": " + recomm_Set.get(position).getPlotcode());
        holder.cluster_offcer.setText(": " + recomm_Set.get(position).getClusterName());


        if (position % 2 == 0) {
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white));
        } else {
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white2));

        }


        AnimationUtil.animate(holder, true);
    }

    @Override
    public int getItemCount() {
        return recomm_Set.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewplotId;
        public TextView textViewpalmArea;
        public TextView textViewLocation, plotvillage;
        public TextView plot_code, date_plantation,cluster_offcer;
        public CardView card_view;
        public TextView plotLandmark, plot_Mandal, plot_State, plot_District;
LinearLayout landmark_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewplotId = (TextView) itemView.findViewById(R.id.surveynumber);
            this.textViewpalmArea = (TextView) itemView.findViewById(R.id.plothectrage);
            this.textViewLocation = (TextView) itemView.findViewById(R.id.plotaddress);
            this.cluster_offcer = (TextView) itemView.findViewById(R.id.cluster_offcer);
this.landmark_layout =(LinearLayout)itemView.findViewById(R.id.landmark_layout) ;

            this.plotLandmark = (TextView) itemView.findViewById(R.id.plotLandmark);
            this.plot_Mandal = (TextView) itemView.findViewById(R.id.plotmandal);
            this.plotvillage = (TextView) itemView.findViewById(R.id.plotvillage);
            this.plot_District = (TextView) itemView.findViewById(R.id.plotdistrict);
            this.date_plantation = (TextView) itemView.findViewById(R.id.yop);
            this.plot_code = (TextView) itemView.findViewById(R.id.plot_code);
            this.card_view = (CardView) itemView.findViewById(R.id.card_view);


        }


    }
}

