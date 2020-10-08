package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.models.GetActiveEncyclopediaCategoryDetails;
import in.calibrage.akshaya.views.actvity.EncyclopediaActivity;

public class KnowledgeZoneBaseAdapter extends RecyclerView.Adapter<KnowledgeZoneBaseAdapter.ViewHolder> {

public Context mContext;
private List<GetActiveEncyclopediaCategoryDetails.ListResult> learning_Set;

public KnowledgeZoneBaseAdapter(Context context, List<GetActiveEncyclopediaCategoryDetails.ListResult>catagoriesList) {

        this.mContext = context;
        this.learning_Set =catagoriesList;

        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_kz_home, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
        }

@Override
public void onBindViewHolder(final ViewHolder holder, final int position) {
final int langID = SharedPrefsData.getInstance(mContext).getIntFromSharedPrefs("lang");

        Log.e("languageid===", langID + "");


        if (langID == 2){
        ((ViewHolder) holder).text_title.setText(learning_Set.get(position).getTeluguName());
        }
        else if (langID == 3){
        ((ViewHolder) holder).text_title.setText(learning_Set.get(position).getKannadaName());
        }
        else{
        ((ViewHolder) holder).text_title.setText(learning_Set.get(position).getName());
        }


        if(learning_Set.get(position).getId() == 1 ){
        Picasso.with(mContext )
        .load(R.drawable.fertilizers)
        .error(R.drawable.ic_applogo )
        .placeholder( R.drawable.progress_animation)
        .into(holder.img);

        }else if(learning_Set.get(position).getId() == 2 )
        {
        Picasso.with(mContext )
        .load(R.drawable.harvesting)
        .error(R.drawable.ic_applogo )
        .placeholder( R.drawable.progress_animation)
        .into(holder.img);

        }
        else if(learning_Set.get(position).getId() == 3 )
        {
        Picasso.with(mContext )
        .load(R.drawable.pest)
        .error(R.drawable.ic_applogo )
        .placeholder( R.drawable.progress_animation)
        .into(holder.img);

        }
        else if(learning_Set.get(position).getId() == 4)
        {
        Picasso.with(mContext )
        .load(R.drawable.oilpalm)
        .error(R.drawable.ic_applogo )
        .placeholder( R.drawable.progress_animation)
        .into(holder.img);

        }
        else if(learning_Set.get(position).getId() == 5)
        {
        Picasso.with(mContext )
        .load(R.drawable.general)
        .error(R.drawable.ic_applogo )
        .placeholder( R.drawable.progress_animation)
        .into(holder.img);

        }
        else
        {
        Picasso.with(mContext )
        .load(R.drawable.ic_lernin)
        .error(R.drawable.ic_applogo )
        .placeholder( R.drawable.progress_animation)
        .into(holder.img);

        }
        holder.learn_relative.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(mContext, EncyclopediaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("postTypeId", learning_Set.get(position).getId());
        intent.putExtra("name", learning_Set.get(position).getName());
        intent.putExtra("teluguname", learning_Set.get(position).getTeluguName());
        intent.putExtra("kannadanames", learning_Set.get(position).getKannadaName());

        if(learning_Set.get(position).getId() == 1 ){

        String[] tabnames = {mContext.getString(R.string.str_standard),mContext.getString(R.string.str_pdf),mContext.getString(R.string.str_videos)};



        intent.putExtra("tabslist", tabnames);
        }else
        {

        String[] tabnames = {mContext.getString(R.string.str_pdf),mContext.getString(R.string.str_videos)};
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

