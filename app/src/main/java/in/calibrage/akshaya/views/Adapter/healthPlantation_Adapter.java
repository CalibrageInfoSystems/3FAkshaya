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
import in.calibrage.akshaya.models.healthPlantation;


public class healthPlantation_Adapter extends RecyclerView.Adapter<healthPlantation_Adapter.ViewHolder>{

    public Context mContext;
    private List<healthPlantation> Plantation_List;
    // RecyclerView recyclerView;
    public healthPlantation_Adapter(Context ctx, List<healthPlantation> Plantation_List ) {
        this.Plantation_List = Plantation_List;
        this.mContext=ctx;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.healthplantation_list, parent, false);
       ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final healthPlantation dataa = Plantation_List.get(position);
        //  holder.txtPlotCode.setText(dataa.getPloteCode());
        holder.treesAppearance.setText(dataa.getTreesAppearance());
        holder.treeGirth.setText(dataa.getTreeGirth());
        holder.treeHeight.setText(dataa.getTreeHeight());
        holder.fruitColor.setText(dataa.getFruitColor());
        holder.fruitSize.setText(dataa.getFruitSize());
        holder.fruitHyegiene.setText(dataa.getFruitHyegiene());
        holder.plantationType.setText(dataa.getPlantationType());


        //      holder.imageView.setImageResource(listdata[position].getImgId());
        Picasso.with(mContext).load(dataa.getImage()).fit().centerCrop()
                .placeholder(R.drawable.encylopedia)
                .error(R.drawable.pole)
                .into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {


        if (Plantation_List != null)
            return Plantation_List.size();
        else
            return 0;

}

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView treesAppearance,treeGirth,treeHeight,fruitColor,fruitSize,fruitHyegiene,plantationType;
ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);

            //   txtPlotCode = itemView.findViewById(R.id.plot_code);
            treesAppearance = itemView.findViewById(R.id.treesAppearance);
            treeGirth = itemView.findViewById(R.id.treeGirth);
            treeHeight = itemView.findViewById(R.id.treeHeight);
            fruitColor = itemView.findViewById(R.id.fruitColor);
            fruitSize= itemView.findViewById(R.id.fruitSize);

            fruitHyegiene = itemView.findViewById(R.id.fruitHyegiene);
            plantationType= itemView.findViewById(R.id.plantationType);
            thumbnail =itemView.findViewById(R.id.imageViewHero);

        }


    }
}


