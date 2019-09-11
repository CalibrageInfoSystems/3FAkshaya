package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.FarmerOtpResponceModel;
import in.calibrage.akshaya.models.LerningsModel;

public class KnowledgeZoneBaseAdapter extends BaseAdapter {

    private Context mContext;

 private List<FarmerOtpResponceModel.CategoriesDetail> learning_Set;
    public KnowledgeZoneBaseAdapter(Context context, List<FarmerOtpResponceModel.CategoriesDetail> learning_Set) {
        this.mContext = context;
        this.learning_Set = learning_Set;
    }

    // 2
    @Override
    public int getCount() {
        return learning_Set.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
//     final LerningsModel.ListResult book = learning_Set.getListResult().get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.adapter_kz_home, null);
        }

        // 3
        final ImageView imageView = convertView.findViewById(R.id.imageView);
        final TextView textView = convertView.findViewById(R.id.text_title);


        // 4
        //  imageView.setImageResource(R.drawable.encylopedia);
        try {


            textView.setText(learning_Set.get(position).getName());
            // imageView.setImageResource(covers[position]);
//            Glide.with(mContext)
//                    .load( R.drawable.ic_myprofile)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

}

