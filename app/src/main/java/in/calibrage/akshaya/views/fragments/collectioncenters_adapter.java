package in.calibrage.akshaya.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import in.calibrage.akshaya.views.Adapter.GodownListAdapter;
import in.calibrage.akshaya.views.Adapter.Godown_adapter;
import in.calibrage.akshaya.views.actvity.MapActivity;
import in.calibrage.akshaya.views.actvity.MapsActivity;

class collectioncenters_adapter extends RecyclerView.Adapter<collectioncenters_adapter.viewHolder> {
    private Context ctx;
    private List<resGet3FInfo.CollectionCenter> centerslistResults = new ArrayList<>();
    private LayoutInflater inflater;
    GodownListAdapter.OnItemClickListener listener;

    public collectioncenters_adapter(List<resGet3FInfo.CollectionCenter> centerslistResults, Context ctx) {
        this.centerslistResults = centerslistResults;
        inflater = (LayoutInflater.from(ctx));
        this.listener = listener;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_collection_centres, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {


        holder.txt_name.setText(centerslistResults.get(position).getCollectionCenter());
        holder.collectioncentervillage.setText(": " + centerslistResults.get(position).getVillageName());
        holder.collectioncentermandal.setText(": " + centerslistResults.get(position).getMandalName());
        holder.collectioncenterdistrict.setText(": " + centerslistResults.get(position).getDistrictName());
        holder.mapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != centerslistResults.get(position).getLatitude())      {
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("https")
                            .authority("www.google.com")
                            .appendPath("maps")
                            .appendPath("dir")
                            .appendPath("")
                            .appendQueryParameter("api", "1")
                            .appendQueryParameter("destination", centerslistResults.get(position).getLatitude() + "," +  centerslistResults.get(position).getLongitude());
                    String url = builder.build().toString();
                    Log.d("Directions", url);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    ctx.startActivity(i);

                } else {
                    Toast.makeText(ctx, "Location not available", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if (centerslistResults != null)
            return centerslistResults.size();
        else
            return 0;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, collectioncentervillage, collectioncentermandal, collectioncenterdistrict, map;
        CardView cardView;
        ImageView mapview;

        public viewHolder(View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
            collectioncentervillage = itemView.findViewById(R.id.collectioncentervillage);
            collectioncentermandal = itemView.findViewById(R.id.collectioncentermandal);
            collectioncenterdistrict = itemView.findViewById(R.id.collectioncenterdistrict);
            cardView = itemView.findViewById(R.id.cardView);
            mapview = itemView.findViewById(R.id.map);
        }

    }


}



