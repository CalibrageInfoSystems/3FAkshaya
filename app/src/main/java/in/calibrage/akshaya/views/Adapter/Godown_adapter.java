package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.ActiveGodownsModel;
import in.calibrage.akshaya.models.resGet3FInfo;
import in.calibrage.akshaya.views.actvity.MapActivity;

public class Godown_adapter extends RecyclerView.Adapter<Godown_adapter.viewHolder> {

    private Context ctx;
    private List<resGet3FInfo.Godown> godownlistResults = new ArrayList<>();
    private LayoutInflater inflater;
    GodownListAdapter.OnItemClickListener listener;
    public Godown_adapter(List<resGet3FInfo.Godown> godownlistResults, Context ctx) {
        this.godownlistResults = godownlistResults;
        inflater = (LayoutInflater.from(ctx));
        this.listener =listener;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_item_godown2, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {



        holder.txt_name.setText(""+godownlistResults.get(position).getGodown());
        holder.txt_address.setText(""+godownlistResults.get(position).getAddress());
        holder.txt_Location.setText(""+godownlistResults.get(position).getLocation());
        holder.mapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != godownlistResults.get(position).getLatitude())
                {
                    Intent intent = new Intent(ctx, MapActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name", godownlistResults.get(position).getGodown());
                    intent.putExtra("lat", godownlistResults.get(position).getLatitude());
                    intent.putExtra("log", godownlistResults.get(position).getLongitude());
                    ctx.startActivity(intent);
                }else {
                    Toast.makeText(ctx, "Location not available", Toast.LENGTH_SHORT).show();
                }

            }
        });


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
        ImageView mapview;
        public viewHolder(View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_Location = itemView.findViewById(R.id.txt_Location);
            txt_address = itemView.findViewById(R.id.txt_address);
            cardView = itemView.findViewById(R.id.cardView);
            mapview = itemView.findViewById(R.id.map);
        }

    }


    public interface OnItemClickListener {
        void onItemClick(ActiveGodownsModel.ListResult item);
    }
}

