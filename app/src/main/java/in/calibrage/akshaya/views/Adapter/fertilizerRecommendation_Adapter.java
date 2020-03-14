package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.fertilizerRecommendation;

public class fertilizerRecommendation_Adapter extends RecyclerView.Adapter<fertilizerRecommendation_Adapter.ViewHolder>{

private List<fertilizerRecommendation> fert_rec_List;
public Context mContext;
String datetimevaluereq;
public fertilizerRecommendation_Adapter(Context context, List<fertilizerRecommendation> fert_rec_List) {
        this.fert_rec_List = fert_rec_List;
        this.mContext=context;

        }


@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.fert_rec_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
        }

@RequiresApi(api = Build.VERSION_CODES.M)
@Override
public void onBindViewHolder(final ViewHolder holder, int position) {

    fertilizerRecommendation list =  fert_rec_List.get(position);

    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
    try {
        Date oneWayTripDate = input.parse(list.getLastUpdatedDate());

        datetimevaluereq = output.format(oneWayTripDate);


        Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
    } catch (ParseException e) {
        e.printStackTrace();
    }
        holder.LastUpdatedDate.setText(datetimevaluereq);
        holder.comments.setText(list.getComments());
        holder.Dosage.setText(list.getDosage());
    holder.UOMame.setText(list.getUOMame());
        holder.recommended_fertilizer.setText(list.getRecommended_fertilizer());
    if(position%2 == 0){
        holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white));
    } else {
        holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white2));

    }
    if (list.getComments().contains("null"))
    {
        //   Log.e("bbbbb",superHero.getmAmount());
        holder.comments.setVisibility(View.GONE);
        holder.comment_label.setVisibility(View.GONE);

    }
    else {
        holder.comments.setVisibility(View.VISIBLE);
        holder.comment_label.setVisibility(View.VISIBLE);
    }
        }
@Override
public int getItemCount() {
        return fert_rec_List.size();
        }


public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView LastUpdatedDate,comments,Dosage,UOMame,recommended_fertilizer,comment_label;
private CardView card_view;
    public ViewHolder(View itemView) {
        super(itemView);
        this.LastUpdatedDate = (TextView) itemView.findViewById(R.id.LastUpdatedDate);
        this.comments = (TextView) itemView.findViewById(R.id.comments);
        this.Dosage = (TextView) itemView.findViewById(R.id.Dosage);
        this.UOMame = (TextView) itemView.findViewById(R.id.UOMName);
        this.recommended_fertilizer = (TextView) itemView.findViewById(R.id.recommended_fertilizer);
        comment_label=itemView.findViewById(R.id.commentsLabel);
        card_view=itemView.findViewById(R.id.card_view);

    }


}




}
