package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.content.Intent;
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
import in.calibrage.akshaya.models.resGet3FInfo;
import in.calibrage.akshaya.views.actvity.MapActivity;

public class Nursery_adapter extends RecyclerView.Adapter<Nursery_adapter.viewHolder> {

private List<resGet3FInfo.Nurseries> nurseriesResults = new ArrayList<>();
private LayoutInflater inflater;
private Context ctx;
        GodownListAdapter.OnItemClickListener listener;
public Nursery_adapter(List<resGet3FInfo.Nurseries> nurtResults, Context ctx) {
        this.nurseriesResults = nurtResults;
        inflater = (LayoutInflater.from(ctx));
        this.ctx= ctx;

        }

@NonNull
@Override
public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lst_nurcery, parent, false);
        return new viewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

Log.e("nurseriesResults======",nurseriesResults+"");
        holder.txt_name.setText(nurseriesResults.get(position).getNurseryName());
        holder.collectioncentervillage.setText(": "+nurseriesResults.get(position).getVillage());
        holder.collectioncentermandal.setText(": "+nurseriesResults.get(position).getMandal());
        holder.collectioncenterdistrict.setText(": "+nurseriesResults.get(position).getDistrict());
//
        holder.mapview.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
//        if(null != nurseriesResults.get(position).getLatitude())
//        Uri.Builder builder = new Uri.Builder();
//                    builder.scheme("https")
//                            .authority("www.google.com")
//                            .appendPath("maps")
//                            .appendPath("dir")
//                            .appendPath("")
//                            .appendQueryParameter("api", "1")
//                            .appendQueryParameter("destination", milllistResults.get(position).getLatitude() + "," +  milllistResults.get(position).getLongitude());
//                    String url = builder.build().toString();
//                    Log.d("Directions", url);
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    ctx.startActivity(i);
        Toast.makeText(ctx, ctx.getResources().getString(R.string.location_notfount), Toast.LENGTH_SHORT).show();
       // }

        }
        });


        }

@Override
public int getItemCount() {
        if (nurseriesResults != null)
        return nurseriesResults.size();
        else
        return 0;
        }

class viewHolder extends RecyclerView.ViewHolder {
    TextView txt_name, collectioncentervillage, collectioncentermandal,collectioncenterdistrict,map;
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

