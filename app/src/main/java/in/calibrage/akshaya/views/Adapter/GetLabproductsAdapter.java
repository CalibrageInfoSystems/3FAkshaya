package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.AnimationUtil;
import in.calibrage.akshaya.models.DeleteObject;
import in.calibrage.akshaya.models.LabproductsRequest;
import in.calibrage.akshaya.models.ResPole;
import in.calibrage.akshaya.models.Resdelete;
import in.calibrage.akshaya.models.Resfert;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetLabproductsAdapter extends RecyclerView.Adapter<GetLabproductsAdapter.ViewHolder>{


    List<LabproductsRequest.ListResult> list;
    public Context mContext;
    private Animation animationUp, animationDown;
    private final int COUNTDOWN_RUNNING_TIME = 500;
    private GetLabproductAdapterListener1 listener;
    public CardView card_view;
    String datetimevaluereq,currentDate;
    // RecyclerView recyclerView;
    String selectedItemID;
    Button cancel_btn,ok_btn;
    int selectedPO;
    private Subscription mSubscription;
    DecimalFormat df = new DecimalFormat("####0.00");

    public GetLabproductsAdapter(List<LabproductsRequest.ListResult> list, Context ctx, GetLabproductAdapterListener1 listener) {
        this.mContext = ctx;
        this.listener = listener;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.fert_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oneWayTripDate = input.parse(list.get(position).getReqCreatedDate());

            datetimevaluereq = output.format(oneWayTripDate);


            Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.requestCode.setText(list.get(position).getRequestCode());
        holder.req_date.setText(datetimevaluereq);
        holder.godown_name.setText(list.get(position).getGoDownName());
        holder.statusType.setText(list.get(position).getStatus());
        holder.paymentMode.setText(list.get(position).getPaymentMode());
        holder.sub_amount.setText(df.format(Math.round(list.get(position).getSubsidyAmount())));
        if( list.get(position).getPin()!= null)
            holder.pin.setText(list.get(position).getPin());
        else{
            holder.pin.setVisibility(View.GONE);
            holder.pinlabel.setVisibility(View.GONE);
        }
        if(null != list.get(position).getPaubleAmount())
            holder.amount.setText(df.format(Math.round(list.get(position).getPaubleAmount())));

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        if (position % 2 == 0) {
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white));
            holder.details.setBackgroundColor(mContext.getColor(R.color.white2));
        } else {
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white2));
            holder.details.setBackgroundColor(mContext.getColor(R.color.light_gray2));

        }
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onContactSelected2(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null != list)
            return list.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout contentLayout;
        public TextView requestCode,pin,pinlabel;
        public TextView req_date;
        public TextView statusType,sub_amount;
        public TextView paymentMode, amount,cancel,godown_name;
        public ImageView showMore;
        LinearLayout details;
        public CardView card_view;
        public ViewHolder(View itemView) {
            super(itemView);
            showMore = (ImageView)itemView.findViewById(R.id.show_more);
            contentLayout = (LinearLayout)itemView.findViewById(R.id.linear1);
            requestCode = itemView.findViewById(R.id.requestCode);
            req_date = itemView.findViewById(R.id.reqCreatedDate);
            statusType = itemView.findViewById(R.id.statusType);
            sub_amount = itemView.findViewById(R.id.sub_amount);
            paymentMode = itemView.findViewById(R.id.paymentMode);
            card_view =   itemView.findViewById(R.id.card_view);
            amount=itemView.findViewById(R.id.amount);
            godown_name=itemView.findViewById(R.id.godown_name);
            cancel = itemView.findViewById(R.id.cancel);
            details = itemView.findViewById(R.id.details);
            card_view = itemView.findViewById(R.id.card_view);
            pin = itemView.findViewById(R.id.pin);
            pinlabel =itemView.findViewById(R.id.pin_label);
        }
    }


    public interface GetLabproductAdapterListener1 {
        void onContactSelected2(LabproductsRequest.ListResult list);
    }
}
