package in.calibrage.akshaya.views.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.AnimationUtil;
import in.calibrage.akshaya.models.LabourRecommendationsModel;
import in.calibrage.akshaya.views.actvity.HomeActivity;
import in.calibrage.akshaya.views.actvity.LabourActivity;
import in.calibrage.akshaya.views.actvity.LoanActivity;


public class LabourRecommendationAdapter extends RecyclerView.Adapter<LabourRecommendationAdapter.ViewHolder>{

    List<LabourRecommendationsModel.ListResult> recomm_Set  = new ArrayList<LabourRecommendationsModel.ListResult>();
    public Context mContext;
int statusTypeId ,statusType_Id;
    DecimalFormat dec = new DecimalFormat("####0.00");
    public LabourRecommendationAdapter(List<LabourRecommendationsModel.ListResult> recomm_Set, Context context) {
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        statusTypeId =recomm_Set.get(position).getStatusTypeId();


            holder.textViewplotId.setText(recomm_Set.get(position).getPlotcode());
        DecimalFormat df = new DecimalFormat("#,###,##0.00");

        holder.textViewpalmArea.setText(df.format(recomm_Set.get(position).getPalmArea())+" Ha ("+ dec.format(recomm_Set.get(position).getPalmAreainAcres() ) + " Acre)");
        holder.textViewLocation.setText(recomm_Set.get(position).getVillageName());
        holder.landmark.setText(recomm_Set.get(position).getLandMark() );
        holder.textViewstatus.setText(recomm_Set.get(position).getStatusType() );
        holder.farmer_Code.setText(recomm_Set.get(position).getFarmerCode());
        holder.plot_Mandal.setText(recomm_Set.get(position).getMandalName());
        holder.plot_State.setText(recomm_Set.get(position).getStateName());
        holder.plot_District.setText(recomm_Set.get(position).getDistrictName());
        holder.cluster_name.setText(recomm_Set.get(position).getClusterName());
        holder.yop.setText(recomm_Set.get(position).getDateOfPlanting() );
       // Log.e("date",recomm_Set.get(position).getDateOfPlanting() );
        statusTypeId =recomm_Set.get(position).getStatusTypeId();
final double selected_plot =recomm_Set.get(position).getPalmArea() ;
        final double selected_palm =recomm_Set.get(position).getPalmAreainAcres() ;

         final String interCrops =recomm_Set.get(position).getInterCrops() ;



        holder.card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LabourActivity.class);
                intent.putExtra("plotid",    holder.textViewplotId.getText());
                intent.putExtra("plotAge",    selected_plot);
                intent.putExtra("plotarea",    selected_palm);
                intent.putExtra("plotVillage",    holder.textViewLocation.getText());
                intent.putExtra("farmerCode",    holder.farmer_Code.getText());
                intent.putExtra("landMark",    holder.landmark.getText());
                intent.putExtra("status",    holder.textViewstatus.getText());
                intent.putExtra("plotMandal",    holder.plot_Mandal.getText());
                intent.putExtra("plotState",    holder.plot_State.getText());
                intent.putExtra("plotDistrict",    holder.plot_District.getText());
                intent.putExtra("cluster_name",    holder.cluster_name.getText());
                intent.putExtra("cluster_Id",    recomm_Set.get(position).getClusterId());
                intent.putExtra("interCrop",   interCrops);
                intent.putExtra("statusTypeId",   recomm_Set.get(position).getStatusTypeId());
                intent.putExtra("statecode",   recomm_Set.get(position).getStateCode());
                intent.putExtra("statename",   recomm_Set.get(position).getStateName());

                intent.putExtra("date_of_plandation",   recomm_Set.get(position).getDateOfPlanting());



                Log.e("plotDistrict====", (String) holder.plot_Mandal.getText());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);



            }

        });

        AnimationUtil.animate(holder, true);
    }

    public void showDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final ImageView img = dialog.findViewById(R.id.img_cross);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);


            }
        });
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Animatable) img.getDrawable()).start();
            }
        }, 500);
    }

    @Override
    public int getItemCount() {
        return recomm_Set.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewplotId;
        public TextView textViewpalmArea;
        public TextView textViewLocation;
        public TextView textViewstatus,date_plantation,landmark,yop,cluster_name;
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
            this.date_plantation = (TextView) itemView.findViewById(R.id.date_plantation);
            this.landmark = (TextView) itemView.findViewById(R.id.landmark);
            this.yop = (TextView) itemView.findViewById(R.id.yop);
            this.card_view =  (CardView) itemView.findViewById(R.id.card_view);
            this.cluster_name=(TextView)itemView.findViewById(R.id.status);

        }


    }
}