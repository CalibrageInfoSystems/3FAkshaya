package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.views.actvity.EncyclopediaActivity;

public class KnowledgeZoneBaseAdapter extends RecyclerView.Adapter<KnowledgeZoneBaseAdapter.ViewHolder> {

    public Context mContext;
    private List<FarmerOtpResponceModel.CategoriesDetail> learning_Set;

    public KnowledgeZoneBaseAdapter(Context context, FarmerOtpResponceModel catagoriesList) {

        this.mContext = context;
        this.learning_Set = catagoriesList.getResult().getCategoriesDetails();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_kz_home, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ((ViewHolder) holder).text_title.setText(learning_Set.get(position).getName());
        holder.learn_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EncyclopediaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("postTypeId", learning_Set.get(position).getId());
                intent.putExtra("name", learning_Set.get(position).getName());

                if(learning_Set.get(position).getId() == 1 ){
                    String[] tabnames = {mContext.getString(R.string.str_standard),mContext.getString(R.string.str_videos),mContext.getString(R.string.str_pdf)};


                    intent.putExtra("tabslist", tabnames);
                }else
                {
                    String[] tabnames = {mContext.getString(R.string.str_videos),mContext.getString(R.string.str_pdf)};
                    intent.putExtra("tabslist", tabnames);
                }

                mContext.startActivity(intent);


            }
        });
    }
    @Override
    public int getItemCount() {
        if (learning_Set != null)
            return learning_Set.size();
        else
            return 0;
    }

    public void addCollection(List<FarmerOtpResponceModel.CategoriesDetail> learning_Set)
    {
        learning_Set.addAll(learning_Set);
        notifyDataSetChanged();
    }
    public  void  clearList()
    {
        learning_Set.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text_title;
     private  ImageView img;
     private RelativeLayout learn_relative;

        public ViewHolder(View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            img= itemView.findViewById(R.id.imageView);
            learn_relative =itemView.findViewById(R.id.learn_relative);

        }


    }

}

