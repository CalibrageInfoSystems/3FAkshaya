package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.uprootmentData;


public class uprootment_Adapter extends RecyclerView.Adapter<uprootment_Adapter.ViewHolder>{

    public Context mContext;
    private List<uprootmentData> uprootment_List;
    // RecyclerView recyclerView;
    public uprootment_Adapter(Context ctx, List<uprootmentData> uprootment_List ) {
        this.uprootment_List = uprootment_List;
        this.mContext=ctx;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.uprootment_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final uprootmentData dataa = uprootment_List.get(position);
        //  holder.txtPlotCode.setText(dataa.getPloteCode());
        holder.seedsPlanted.setText(dataa.getSeedsPlanted());
        holder.prevPalmsCount.setText(dataa.getPrevPalmsCount());
        holder.plamsCount.setText(dataa.getPlamsCount());
        holder.isTreesMissing.setText(dataa.getIsTreesMissing());
        holder.missingTreesCount.setText(dataa.getMissingTreesCount());
        holder.reasonType.setText(dataa.getReasonType());
        holder.expectedPlamsCount.setText(dataa.getExpectedPlamsCount());
        holder.comments.setText(dataa.getComments());

        if (dataa.getReasonType().contains("null"))
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.reasonType.setVisibility(View.GONE);
            holder.reason_label.setVisibility(View.GONE);

        }
        else {
            holder.reasonType.setVisibility(View.VISIBLE);
            holder.reason_label.setVisibility(View.VISIBLE);
        }
        if (dataa.getComments().contains("null"))
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.comments.setVisibility(View.GONE);
            holder.comment_label.setVisibility(View.GONE);

        }
        else {
            holder.comments.setVisibility(View.VISIBLE);
            holder.comment_label.setVisibility(View.VISIBLE);
        }

        //      holder.imageView.setImageResource(listdata[position].getImgId());

    }


    @Override
    public int getItemCount() {

        if (uprootment_List != null)
            return uprootment_List.size();
        else
            return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView seedsPlanted,prevPalmsCount,plamsCount,isTreesMissing,missingTreesCount,reasonType,expectedPlamsCount,comments;
       // ImageView thumbnail;
        public TextView comment_label,reason_label;
        public ViewHolder(View itemView) {
            super(itemView);

            //   txtPlotCode = itemView.findViewById(R.id.plot_code);
            seedsPlanted = itemView.findViewById(R.id.seedsPlanted);
            prevPalmsCount = itemView.findViewById(R.id.prevPalmsCount);
            plamsCount = itemView.findViewById(R.id.plamsCount);
            isTreesMissing = itemView.findViewById(R.id.isTreesMissing);
            missingTreesCount= itemView.findViewById(R.id.missingTreesCount);

            reasonType = itemView.findViewById(R.id.reasonType);
            expectedPlamsCount= itemView.findViewById(R.id.expectedPlamsCount);
            comments =itemView.findViewById(R.id.comments);
            comment_label=itemView.findViewById(R.id.commentsLabel);
            reason_label=itemView.findViewById(R.id.reasonTypeLabel);

        }


    }
}


