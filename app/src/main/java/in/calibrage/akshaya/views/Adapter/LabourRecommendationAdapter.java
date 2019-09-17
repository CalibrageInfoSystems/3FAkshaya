package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.LabourRecommendationsModel;
import in.calibrage.akshaya.views.actvity.LabourActivity;


public class LabourRecommendationAdapter extends RecyclerView.Adapter<LabourRecommendationAdapter.ViewHolder>{
    //private RecommendationModel[] listdata;
    List<LabourRecommendationsModel.ListResult> recomm_Set;
    public Context mContext;

    // RecyclerView recyclerView;
    public LabourRecommendationAdapter(    List<LabourRecommendationsModel.ListResult> recomm_Set, Context context) {
        this.recomm_Set = recomm_Set;
        this.mContext=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.recommendation_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

      //  LabourRecommendationsModel superHero =  superHeroes.get(position);
        holder.textViewplotId.setText(recomm_Set.get(position).getFPlotcode());
        holder.textViewpalmArea.setText(recomm_Set.get(position).getPalmArea()+" "+"Ha");
        holder.textViewLocation.setText(recomm_Set.get(position).getVillageName());
        holder.textViewstatus.setText(recomm_Set.get(position).getStatusType());
        holder.farmer_Code.setText(recomm_Set.get(position).getFarmerCode());
        holder.plot_Mandal.setText(recomm_Set.get(position).getMandalName());
        holder.plot_State.setText(recomm_Set.get(position).getStateName());
        holder.plot_District.setText(recomm_Set.get(position).getDistrictName());



        holder.card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LabourActivity.class);
                intent.putExtra("plotid",    holder.textViewplotId.getText());
                intent.putExtra("plotAge",    holder.textViewpalmArea.getText());
                intent.putExtra("plotVillage",    holder.textViewLocation.getText());
                intent.putExtra("farmerCode",    holder.farmer_Code.getText());
                intent.putExtra("landMark",    holder.textViewstatus.getText());
                intent.putExtra("plotMandal",    holder.plot_Mandal.getText());
                intent.putExtra("plotState",    holder.plot_State.getText());
                intent.putExtra("plotDistrict",    holder.plot_District.getText());


                Log.e("plotDistrict====", (String) holder.plot_Mandal.getText());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);



            }

        });


    }
    @Override
    public int getItemCount() {
        return recomm_Set.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewplotId;
        public TextView textViewpalmArea;
        public TextView textViewLocation;
        public TextView textViewstatus;
        public CardView card_view;
        public TextView farmer_Code,plot_Mandal,plot_State,plot_District;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewplotId = (TextView) itemView.findViewById(R.id.plotId);
            this.textViewpalmArea = (TextView) itemView.findViewById(R.id.palmArea);
            this.textViewLocation = (TextView) itemView.findViewById(R.id.location);
            this.textViewstatus = (TextView) itemView.findViewById(R.id.status);
            this.farmer_Code = (TextView) itemView.findViewById(R.id.farmer_Code);
            this.plot_Mandal = (TextView) itemView.findViewById(R.id.plot_Mandal);
            this.plot_State = (TextView) itemView.findViewById(R.id.plot_State);
            this.plot_District = (TextView) itemView.findViewById(R.id.plot_District);


            this.card_view =  (CardView) itemView.findViewById(R.id.card_view);

        }


    }
}