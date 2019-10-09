package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.AnimationUtil;
import in.calibrage.akshaya.models.LabourRecommendationsModel;
import in.calibrage.akshaya.models.res_plotdetails;
import in.calibrage.akshaya.views.actvity.LabourActivity;
import in.calibrage.akshaya.views.actvity.LabourRecommendationsActivity;

public class PlotDetailsAdapter extends RecyclerView.Adapter<PlotDetailsAdapter.ViewHolder>{
    //private RecommendationModel[] listdata;
    List<res_plotdetails.ListResult> recomm_Set;
    public Context mContext;

    // RecyclerView recyclerView;
    public PlotDetailsAdapter( List<res_plotdetails.ListResult> recomm_Set, Context context) {
        this.recomm_Set = recomm_Set;
        this.mContext=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.plot_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //  LabourRecommendationsModel superHero =  superHeroes.get(position);
        holder.textViewplotId.setText(": "+recomm_Set.get(position).getSurveyNumber());
        holder.textViewpalmArea.setText(": "+recomm_Set.get(position).getTotalPalmArea()+" "+"Ha");
        holder.textViewLocation.setText(": "+recomm_Set.get(position).getAddress());

        holder.plotLandmark.setText(": "+recomm_Set.get(position).getLandmark());
        holder.plot_Mandal.setText(": "+recomm_Set.get(position).getMandal());
       holder.plotvillage.setText(": "+recomm_Set.get(position).getVillage());
        holder.plot_District.setText(": "+recomm_Set.get(position).getDistrict());

        holder.date_plantation.setText(": "+recomm_Set.get(position).getDateofPlanting());



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
        public TextView textViewLocation,plotvillage;
        public TextView textViewstatus,date_plantation;
        public CardView card_view;
        public TextView plotLandmark,plot_Mandal,plot_State,plot_District;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewplotId = (TextView) itemView.findViewById(R.id.surveynumber);
            this.textViewpalmArea = (TextView) itemView.findViewById(R.id.plothectrage);
            this.textViewLocation = (TextView) itemView.findViewById(R.id.plotaddress);


            this.plotLandmark = (TextView) itemView.findViewById(R.id.plotLandmark);
            this.plot_Mandal = (TextView) itemView.findViewById(R.id.plotmandal);
            this.plotvillage = (TextView) itemView.findViewById(R.id.plotvillage);
            this.plot_District = (TextView) itemView.findViewById(R.id.plotdistrict);
            this.date_plantation = (TextView) itemView.findViewById(R.id.yop);

            this.card_view =  (CardView) itemView.findViewById(R.id.card_view);

        }


    }
}

