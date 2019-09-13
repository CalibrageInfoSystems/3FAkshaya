package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import in.calibrage.akshaya.models.GetEncyclopediaDetails;

public class EncyclopediaDetailsAdapter extends RecyclerView.Adapter<EncyclopediaDetailsAdapter.ViewHolder> {

    private int TYPE;
    private GetEncyclopediaDetails getEncyclopediaDetails;
    private Context mContext;

    public EncyclopediaDetailsAdapter(GetEncyclopediaDetails getEncyclopediaDetails, Context mContext, int TYPE) {
        this.getEncyclopediaDetails = getEncyclopediaDetails;
        this.mContext = mContext;
        this.TYPE = TYPE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
