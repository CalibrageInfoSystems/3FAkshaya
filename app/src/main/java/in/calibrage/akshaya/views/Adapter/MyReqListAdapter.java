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
import in.calibrage.akshaya.models.Request_settings;
import in.calibrage.akshaya.views.actvity.HomeActivity;


public class MyReqListAdapter extends RecyclerView.Adapter<MyReqListAdapter.MyViewHolder>{


        private Context context;
        private List<Request_settings> request_List;
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
                        // send selected contact in callback
                        listener.onContactSelected(request_List.get(getAdapterPosition()));
                    }
                });
            }
        }


    public MyReqListAdapter(Context context, List<Request_settings> request_List, RequestAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.request_List = request_List;

    }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_req_list_item, parent, false);

        return new MyViewHolder(itemView);
    }




    @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Request_settings settings = request_List.get(position);
        holder.name.setText(settings.getName());




        Picasso.with(context).load(settings.getImage()).error(R.drawable.ic_user).transform(new CircleTransform()).into(holder.thumbnail);

    }

        @Override
        public int getItemCount() {
        return request_List.size();
    }


//  ;
//    Context context;
//    private RequestA  private MyReqListData[] listdatadapterListener vendorlistener;
//    // RecyclerView recyclerView;
//    public MyReqListAdapter( Context context,MyReqListData[] listdata) {
//        this.listdata = listdata;
//        this.context=context;
//    }
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View listItem= layoutInflater.inflate(R.layout.my_req_list_item, parent, false);
//        ViewHolder viewHolder = new ViewHolder(listItem);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        final MyReqListData myListData = listdata[position];
//        holder.textView.setText(listdata[position].getDescription());
//        holder.imageView.setImageResource(listdata[position].getImgId());
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, MyReqDeatilsActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return listdata.length;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView imageView;
//        public TextView textView;
//        public RelativeLayout relativeLayout;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            this.textView = (TextView) itemView.findViewById(R.id.textView);
//            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
//        }
//    }


    public interface RequestAdapterListener {
        void onContactSelected(Request_settings request);
    }
}