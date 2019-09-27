package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.Irrigation_Model;

public class IrrigationAdapter extends RecyclerView.Adapter<IrrigationAdapter.ViewHolder> {

    public Context mContext;
    List<Irrigation_Model> Irr_List;

    public IrrigationAdapter(Context context, List<Irrigation_Model> Irr_List) {
        this.Irr_List = Irr_List;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.irrigation_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Irrigation_Model data = Irr_List.get(position);
        holder.name.setText(data.getName());
        holder.updatedBy.setText(data.getUpdatedBy());
        holder.updatedbyDate.setText(data.getUpdatedbyDate());

//        if (position % 2 == 0) {
//            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white));
//        } else {
//            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white2));
//
//        }
    }

    @Override
    public int getItemCount() {
        return Irr_List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView  plotCode,name,updatedBy,updatedbyDate ;
        public TextView UOMName, dosage_label, Comments, txtDriverName;
        protected CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
           // plotCode = itemView.findViewById(R.id.Disease);
            name = itemView.findViewById(R.id.irrigation_name);
            updatedBy = itemView.findViewById(R.id.updated_by);
            updatedbyDate = itemView.findViewById(R.id.updated_date);

        }
    }
}
