package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.DiseaseDataModel;

public class DiseaseDataAdapter extends RecyclerView.Adapter<DiseaseDataAdapter.ViewHolder>{

    public Context mContext;
    List<DiseaseDataModel> listdata;

    public DiseaseDataAdapter(Context context, List<DiseaseDataModel> listdata) {
        this.listdata = listdata;
        this.mContext=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.dease_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DiseaseDataModel superHero = listdata.get(position);
        holder.Disease.setText(superHero.getDisease());
        holder.Chemical.setText(superHero.getChemical());
        holder.Dosage.setText(superHero.getDosage());
// holder.txtWeight.setText(String.valueOf(superHero.getWeight()+" "+"KGs"));
        holder.UOMName.setText(superHero.getUOMName());
        holder.Comments.setText(superHero.getComments());
        if (superHero.getDosage().contains("null"))
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.Dosage.setVisibility(View.GONE);
            holder.dosage_label.setVisibility(View.GONE);
            holder.UOMName.setVisibility(View.GONE);

        }
        else {
            holder.Dosage.setVisibility(View.VISIBLE);
            holder.dosage_label.setVisibility(View.VISIBLE);
            holder.UOMName.setVisibility(View.VISIBLE);
        }
        String powers = "";
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Disease;
        public TextView Chemical;
        public TextView RecommendedChemical;
        public TextView Dosage;
        public TextView UOMName,dosage_label,Comments,txtDriverName;

        public ViewHolder(View itemView) {
            super(itemView);
            Disease = itemView.findViewById(R.id.Disease);
            Chemical = itemView.findViewById(R.id.Chemical);
            RecommendedChemical = itemView.findViewById(R.id.RecommendedChemical);
            Dosage = itemView.findViewById(R.id.Dosage);
            UOMName = itemView.findViewById(R.id.UOMName);
            Comments = itemView.findViewById(R.id.Comments);
            dosage_label= itemView.findViewById(R.id.DosageLabel);
        }
    }
}