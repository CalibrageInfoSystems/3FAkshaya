package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.ActiveGodownsModel;
import in.calibrage.akshaya.models.resGet3FInfo;

public class Godown_adapter extends RecyclerView.Adapter<Godown_adapter.viewHolder> {
    private int selectedpo= -1;
    private List<resGet3FInfo.Godown> godownlistResults = new ArrayList<>();
    private LayoutInflater inflater;
    GodownListAdapter.OnItemClickListener listener;
    public Godown_adapter(List<resGet3FInfo.Godown> godownlistResults, Context ctx) {
        this.godownlistResults = godownlistResults;
        inflater = (LayoutInflater.from(ctx));
        this.listener =listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_item_godown, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        if(position == selectedpo)
            holder.cardView.setBackgroundResource(R.drawable.button_bg3);
        else
            holder.cardView.setBackgroundResource(R.drawable.button_bg2);


        holder.txt_name.setText(godownlistResults.get(position).getGodown());
        holder.txt_address.setText(godownlistResults.get(position).getAddress());
        holder.txt_Location.setText(godownlistResults.get(position).getLocation());



    }

    @Override
    public int getItemCount() {
        if (godownlistResults != null)
            return godownlistResults.size();
        else
            return 0;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_Location, txt_address;
        CardView cardView;

        public viewHolder(View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_Location = itemView.findViewById(R.id.txt_Location);
            txt_address = itemView.findViewById(R.id.txt_address);
            cardView = itemView.findViewById(R.id.cardView);
        }

    }


    public interface OnItemClickListener {
        void onItemClick(ActiveGodownsModel.ListResult item);
    }
}

