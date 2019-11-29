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

import java.text.DecimalFormat;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.AnimationUtil;
import in.calibrage.akshaya.models.RecomPlotcodes;
import in.calibrage.akshaya.models.VisitplotDetailsModel;
import in.calibrage.akshaya.views.actvity.CropMaintanceVisitActivity;
import in.calibrage.akshaya.views.actvity.Visit_request_Activity;

public class ReqVisitAdapter extends RecyclerView.Adapter<ReqVisitAdapter.ViewHolder> {

    public Context mContext;
    private List<RecomPlotcodes.ListResult> plot_Set;


    public ReqVisitAdapter(Context context, List<RecomPlotcodes.ListResult> plot_Set) {

        this.mContext = context;
        this.plot_Set = plot_Set;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recommendation_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        DecimalFormat df = new DecimalFormat("#,###,##0.00");

        holder.textViewpalmArea.setText(df.format(plot_Set.get(position).getPalmArea())+" "+"Ha");
        ((ViewHolder) holder).textViewplotId.setText(plot_Set.get(position).getPlotcode());
     //   ((ViewHolder) holder).textViewpalmArea.setText(plot_Set.get(position).getPalmArea() + " " + "Ha");
        ((ViewHolder) holder).textViewLocation.setText(plot_Set.get(position).getVillageName());
        ((ViewHolder) holder).textViewstatus.setText(plot_Set.get(position).getLandMark());
        holder.yop.setText(plot_Set.get(position).getDateOfPlanting() );
        final double selected_plot =plot_Set.get(position).getPalmArea();
        Log.e("selected_plot===",selected_plot+"");
        ((ViewHolder) holder).card_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Visit_request_Activity.class);
                intent.putExtra("plotid", holder.textViewplotId.getText());
                intent.putExtra("plotAge", selected_plot);
                intent.putExtra("plotVillage", holder.textViewLocation.getText());
                intent.putExtra("landMark", holder.textViewstatus.getText());
                intent.putExtra("date_of_plandation",   plot_Set.get(position).getDateOfPlanting());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);


            }

        });
        if (position % 2 == 0) {
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white));
        } else {
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white2));

        }

        AnimationUtil.animate(holder, true);
    }

    @Override
    public int getItemCount() {

        if (plot_Set != null)
            return plot_Set.size();
        else
            return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewplotId;
        public TextView textViewpalmArea;
        public TextView textViewLocation;
        public TextView textViewstatus,yop;
        public CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewplotId = (TextView) itemView.findViewById(R.id.plotId);
            this.textViewpalmArea = (TextView) itemView.findViewById(R.id.palmArea);
            this.textViewLocation = (TextView) itemView.findViewById(R.id.location);
            this.textViewstatus = (TextView) itemView.findViewById(R.id.landmark);
            this.yop =(TextView) itemView.findViewById(R.id.yop);
            this.card_view = (CardView) itemView.findViewById(R.id.card_view);

        }


    }
}
