package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.GetRecommendationsByAgeModel;


public class GetRecommendationsByAgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    List<GetRecommendationsByAgeModel> reccom_list;

    public GetRecommendationsByAgeAdapter(List<GetRecommendationsByAgeModel> reccom_list, Context context){
        super();

        this.reccom_list = reccom_list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_recommendations_by_age_list, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
//
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }





    }

    @Override
    public int getItemCount() {
        return reccom_list == null ? 0 : reccom_list.size();
    }

    public int getItemViewType(int position) {
        return reccom_list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView fertilizer;
        public TextView uoM;
        public TextView year;

        public TextView  remarks,textViewPowers;
 CardView cardView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            fertilizer = (TextView) itemView.findViewById(R.id.fertilizer);

            year= (TextView) itemView.findViewById(R.id.year);
            remarks= (TextView) itemView.findViewById(R.id.remarks);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }


    private void showLoadingView(LoadingViewHolder viewHolder, int position) {


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void populateItemRows(ItemViewHolder holder, int position) {
        GetRecommendationsByAgeModel list =  reccom_list.get(position);
        if(list!=null){
            holder.fertilizer.setText(list.getFertilizer());
         //   holder.uoM.setText(superHero.getUoM());
            holder.year.setText(list.getYear());
            holder.remarks.setText(list.getRemarks());
            String powers = "";
        }

        if(position%2 == 0){
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.white));
        } else {
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.white2));

        }
    }
}