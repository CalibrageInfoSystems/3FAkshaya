package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.CollectionResponceModel;


public class Collection_Adapter extends RecyclerView.Adapter<Collection_Adapter.ViewHolder>{

    public Context mContext;
    private List<CollectionResponceModel.CollectioDatum> collection_Set;
    public Collection_Adapter(  Context context,List<CollectionResponceModel.CollectioDatum> collection_Set) {

        this.mContext=context;
        this.collection_Set=collection_Set;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.collection_data_xml, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ((ViewHolder) holder).txtCollectionId.setText(collection_Set.get(position).getUColnid());
        ((ViewHolder) holder).txtDate.setText(collection_Set.get(position).getDocDate());
        ((ViewHolder) holder).txtWeight.setText(""+collection_Set.get(position).getQuantity());
        ((ViewHolder) holder).txtCc.setText(collection_Set.get(position).getWhsCode());
        ((ViewHolder) holder).txtStatus.setText(collection_Set.get(position).getUApaystat());
    }

    @Override
    public int getItemCount() {
        return collection_Set.size();
    }


    /*  @Override
      public int getItemCount() {
          return listdata.length;
      }
  */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCollectionId;
        public TextView txtDate;
        public TextView txtWeight;
        public TextView txtCc;
        public TextView txtStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCollectionId = itemView.findViewById(R.id.collection_id);
            txtDate = itemView.findViewById(R.id.date);
            txtWeight = itemView.findViewById(R.id.weight);
            txtCc = itemView.findViewById(R.id.cc);
            txtStatus = itemView.findViewById(R.id.status);
//
        }


    }

}
