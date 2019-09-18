package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.LabourTermsNCondtionsModel;


public class LabourTermsNCondtionsAdapter extends RecyclerView.Adapter<LabourTermsNCondtionsAdapter.ViewHolder> {

    public Context mContext;
    private LabourTermsNCondtionsModel listdata;

    // RecyclerView recyclerView;
    public LabourTermsNCondtionsAdapter(Context ctx, LabourTermsNCondtionsModel listdata) {
        this.listdata = listdata;
        this.mContext = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.recommendation_terms_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.age.setText(listdata.getResult().getPruning().get(position).getMeasurement());
        holder.purning.setText(String.valueOf(listdata.getResult().getPruning().get(position).getPrice()));
        if (listdata.getResult().getHarvesting().size() > 0 && listdata.getResult().getHarvesting().size()< position)
            holder.harv.setText(String.valueOf(listdata.getResult().getHarvesting().get(position).getPrice()));
        else
            holder.harv.setText("NA");

        holder.unknown1.setText("NA");
        holder.unknow2.setText("NA");

        //      holder.imageView.setImageResource(listdata[position].getImgId());

    }


    @Override
    public int getItemCount() {
        if (listdata != null)
            return listdata.getResult().getPruning().size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView harv, purning, unknown1, unknow2, age;

        public CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.harv = (TextView) itemView.findViewById(R.id.harv);
            this.purning = (TextView) itemView.findViewById(R.id.prunning);
            this.unknown1 = (TextView) itemView.findViewById(R.id.un1);
            this.unknow2 = (TextView) itemView.findViewById(R.id.un2);
            this.age = (TextView) itemView.findViewById(R.id.age);

            // this.card_view =  (CardView) itemView.findViewById(R.id.card_view);

        }


    }
}