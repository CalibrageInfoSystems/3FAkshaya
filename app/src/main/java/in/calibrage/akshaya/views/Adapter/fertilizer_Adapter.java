package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.fertilizer;

public class fertilizer_Adapter extends RecyclerView.Adapter<fertilizer_Adapter.ViewHolder>{

    public Context mContext;
    private List<fertilizer> fertilizer_List;
    // RecyclerView recyclerView;
    public fertilizer_Adapter(Context ctx, List<fertilizer> fertilizer_List) {
        this.fertilizer_List = fertilizer_List;
        this.mContext=ctx;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.fertilizer_crop_list, parent, false);
       ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final fertilizer dataa = fertilizer_List.get(position);
      //  holder.txtPlotCode.setText(dataa.getPloteCode());
        holder.fertilizerSource.setText(dataa.getfertilizer_sourse());
        holder.fertilizer_name.setText(dataa.getfertilizer_name());
        holder.dosage.setText(dataa.getDosage());
        holder.lastAppliedDate.setText(dataa.getdate());
        holder.applyFertilizerFrequencyType.setText(dataa.getfrequency());

        //      holder.imageView.setImageResource(listdata[position].getImgId());

    }


    @Override
    public int getItemCount() {
        return fertilizer_List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txtPlotCode;
        public TextView fertilizerSource;

        public TextView dosage;
        public TextView lastAppliedDate;
        public TextView applyFertilizerFrequencyType,fertilizer_name;


        public ViewHolder(View itemView) {
            super(itemView);

         //   txtPlotCode = itemView.findViewById(R.id.plot_code);
            fertilizerSource = itemView.findViewById(R.id.fertilizerSource);
            dosage = itemView.findViewById(R.id.dosage);
            lastAppliedDate = itemView.findViewById(R.id.lastAppliedDate);
            applyFertilizerFrequencyType = itemView.findViewById(R.id.applyFertilizerFrequencyType);
            fertilizer_name= itemView.findViewById(R.id.fertilizer_name);


        }


    }
}


