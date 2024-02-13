package in.calibrage.akshaya.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.localData.SharedPrefsData;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;

import in.calibrage.akshaya.models.GetServicesByStateCode;
import in.calibrage.akshaya.views.actvity.EncyclopediaActivity;
import in.calibrage.akshaya.views.actvity.Godown_list;
import in.calibrage.akshaya.views.actvity.LabourRecommendationsActivity;
import in.calibrage.akshaya.views.actvity.LoanActivity;
import in.calibrage.akshaya.views.actvity.QuickPayActivity;
import in.calibrage.akshaya.views.actvity.RequestVisitActivity;

class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    public Context mContext;
    private List<GetServicesByStateCode.ListResult> Service_Set;

    public ServiceAdapter(Context context, List<GetServicesByStateCode.ListResult>catagoriesList) {

        this.mContext = context;
        this.Service_Set =catagoriesList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_kz_home, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final int langID = SharedPrefsData.getInstance(mContext).getIntFromSharedPrefs("lang");

        Log.e("languageid===", langID + "");




        if(Service_Set.get(position).getServiceTypeId() == 12 )
        {
            Picasso.with(mContext )
                    .load(R.drawable.fertilizers)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.img);
            ((ViewHolder) holder).text_title.setText(mContext.getString(R.string.fertilizer));
        }
        else if(Service_Set.get(position).getServiceTypeId() == 10 )
        {
            Picasso.with(mContext )
                    .load(R.drawable.equipment)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.img);
            ((ViewHolder) holder).text_title.setText(mContext.getString(R.string.pole));
        }
        else if(Service_Set.get(position).getServiceTypeId() == 107)
        {
            Picasso.with(mContext )
                    .load(R.drawable.fertilizers)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.img);
            ((ViewHolder) holder).text_title.setText(mContext.getString(R.string.labproducts));
        }
        else  if(Service_Set.get(position).getServiceTypeId() == 11 ){
            Picasso.with(mContext )
                    .load(R.drawable.labour)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.img);
            ((ViewHolder) holder).text_title.setText(mContext.getString(R.string.labour_lable));


        }
        else if(Service_Set.get(position).getServiceTypeId() == 13)
        {
            Picasso.with(mContext )
                    .load(R.drawable.quick_pay)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.img);
            ((ViewHolder) holder).text_title.setText(mContext.getString(R.string.quick));
        }
        else if(Service_Set.get(position).getServiceTypeId() == 14)
        {
            Picasso.with(mContext )
                    .load(R.drawable.visit)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.img);
            ((ViewHolder) holder).text_title.setText(mContext.getString(R.string.visit));
        }
        else if(Service_Set.get(position).getServiceTypeId() == 28)
        {
            Picasso.with(mContext )
                    .load(R.drawable.loan)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.img);
            ((ViewHolder) holder).text_title.setText(mContext.getString(R.string.loan));
        }

        holder.learn_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Service_Set.get(position).getServiceTypeId() == 11 ){
                    Intent intent = new Intent(mContext, LabourRecommendationsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
                else if(Service_Set.get(position).getServiceTypeId() == 10 ){
                    Intent intent = new Intent(mContext, Godown_list.class);
                    intent.putExtra("godown", "pole");
                    mContext.startActivity(intent);
                }
                else if(Service_Set.get(position).getServiceTypeId() == 12 ){
                    Intent intent = new Intent(mContext, Godown_list.class);
                    intent.putExtra("godown", "fert");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
                else if(Service_Set.get(position).getServiceTypeId() == 13 ){
                    Intent intent = new Intent(mContext, QuickPayActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
                else if(Service_Set.get(position).getServiceTypeId() == 14 ){
                    Intent intent = new Intent(mContext, RequestVisitActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
                else if(Service_Set.get(position).getServiceTypeId() == 28 ){
                    Intent intent = new Intent(mContext, LoanActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
                else if(Service_Set.get(position).getServiceTypeId() == 107 ){
                    Intent intent = new Intent(mContext, Godown_list.class);
                    intent.putExtra("godown", "chemical");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }


            }
        });
    }
    @Override
    public int getItemCount() {
        if (Service_Set != null)
            return Service_Set.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text_title;
        private ImageView img;
        private RelativeLayout learn_relative;

        public ViewHolder(View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            img= itemView.findViewById(R.id.imageView);
            learn_relative =itemView.findViewById(R.id.learn_relative);

        }


    }

}

