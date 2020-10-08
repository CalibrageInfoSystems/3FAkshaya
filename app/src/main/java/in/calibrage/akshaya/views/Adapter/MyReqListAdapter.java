package in.calibrage.akshaya.views.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.CircleTransform;
import in.calibrage.akshaya.models.GetServicesByStateCode;
import in.calibrage.akshaya.models.Request_settings;
import in.calibrage.akshaya.models.Resfert;


public class MyReqListAdapter extends RecyclerView.Adapter<MyReqListAdapter.MyViewHolder> {


    //private Context context;
  //  private List<Request_settings> request_List;
    public Context mContext;
    private List<GetServicesByStateCode.ListResult> Service_Set;
    private RequestAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.textView);
            thumbnail = view.findViewById(R.id.imageView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onContactSelected(Service_Set.get(getAdapterPosition()));
                }
            });
        }
    }


    public MyReqListAdapter(Context context, List<GetServicesByStateCode.ListResult> request_List, RequestAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.Service_Set = request_List;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_req_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
      //  final Request_settings settings = Service_Set.get(position);


        if(Service_Set.get(position).getServiceTypeId() == 11 ){
            Picasso.with(mContext )
                    .load(R.drawable.labour)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.thumbnail);
           holder.name.setText(mContext.getString(R.string.labour));


        } else if(Service_Set.get(position).getServiceTypeId() == 12 )
        {
            Picasso.with(mContext )
                    .load(R.drawable.fertilizers)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.thumbnail);
          holder.name.setText(mContext.getString(R.string.fertilizer));
        }
        else if(Service_Set.get(position).getServiceTypeId() == 10 )
        {
            Picasso.with(mContext )
                    .load(R.drawable.equipment)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.thumbnail);
         holder.name.setText(mContext.getString(R.string.pole));
        }
        else if(Service_Set.get(position).getServiceTypeId() == 13)
        {
            Picasso.with(mContext )
                    .load(R.drawable.quick_pay)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.thumbnail);
           holder.name.setText(mContext.getString(R.string.quick));
        }
        else if(Service_Set.get(position).getServiceTypeId() == 14)
        {
            Picasso.with(mContext )
                    .load(R.drawable.visit)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.thumbnail);
          holder.name.setText(mContext.getString(R.string.visit));
        }
        else if(Service_Set.get(position).getServiceTypeId() == 28)
        {
            Picasso.with(mContext )
                    .load(R.drawable.loan)
                    .error(R.drawable.ic_applogo )
                    .placeholder( R.drawable.progress_animation)
                    .into(holder.thumbnail);
         holder.name.setText(mContext.getString(R.string.loan));
        }



   //     Picasso.with(context).load(settings.getImage()).error(R.drawable.ic_user).transform(new CircleTransform()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return Service_Set.size();
    }




    public interface RequestAdapterListener {
        void onContactSelected(GetServicesByStateCode.ListResult list);
    }
}