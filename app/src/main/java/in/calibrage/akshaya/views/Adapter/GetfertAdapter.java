package in.calibrage.akshaya.views.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import in.calibrage.akshaya.models.DeleteObject;
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

import static in.calibrage.akshaya.views.Adapter.MyLabour_ReqAdapter.recreateActivityCompat;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class GetfertAdapter extends RecyclerView.Adapter<GetfertAdapter.ViewHolder> {

    List<Resfert.ListResult> list;
    public Context mContext;
    private Animation animationUp, animationDown;
    private final int COUNTDOWN_RUNNING_TIME = 500;
    private GetfertAdapter.GetPoleAdapterListener listener;
    public CardView card_view;
    String datetimevaluereq,currentDate;
    // RecyclerView recyclerView;
    String selectedItemID;
    Button cancel_btn,ok_btn;
    int selectedPO;
    private Subscription mSubscription;
    DecimalFormat df = new DecimalFormat("####0.00");
    public GetfertAdapter(List<Resfert.ListResult> list, Context ctx, GetfertAdapter.GetPoleAdapterListener listener) {
        this.mContext = ctx;
        this.listener = listener;
        this.list = list;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        public LinearLayout contentLayout;
        public TextView requestCode,pin,pinlabel;
        public TextView req_date;
        public TextView statusType,sub_amount;
        public TextView paymentMode, amount,cancel,godown_name,imdpayment_label,imdpayment;
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
            imdpayment = itemView.findViewById(R.id.imdpayment);
            imdpayment_label =itemView.findViewById(R.id.imdpayment_label);

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if( list.get(position).getIsImmediatePayment()!= null)
            holder.imdpayment.setText(list.get(position).getIsImmediatePayment());
        else{
            holder.imdpayment.setVisibility(View.GONE);
            holder.imdpayment_label.setVisibility(View.GONE);
        }
        if(null != list.get(position).getPaubleAmount())
            holder.amount.setText(df.format(Math.round(list.get(position).getPaubleAmount())));

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//        if (!"Closed".equals(holder.statusType.getText()))
//        {
//            holder.cancel.setVisibility(View.VISIBLE);
//
//        }
//        else {
//            holder.cancel.setVisibility(View.GONE);
//        }

        //todo


//        if (!"Closed".equals(holder.statusType.getText()) && !"Cancelled".equals(holder.statusType.getText())) {
//            holder.cancel.setVisibility(View.VISIBLE);
//
//        } else {
//            holder.cancel.setVisibility(View.GONE);
//        }
//        holder.cancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                selectedItemID = list.get(position).getRequestCode();
//                selectedPO = position;
//                showConformationDialog(selectedPO);
//
//            }
//
//        });


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
                listener.onContactSelected(list.get(position));
            }
        });
    }


    private void showConformationDialog(final int selectedPO) {
        TextView dialogMessage;
        final Dialog dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_cancel);
        dialogMessage = dialog.findViewById(R.id.dialogMessage);
        dialogMessage.setText(mContext.getString(R.string.alert_msg));
        cancel_btn = dialog.findViewById(R.id.cancel_btn);
        ok_btn = dialog.findViewById(R.id.ok_btn);
/**
 * @param OnClickListner
 */
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    delete_request();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });

/**
 * @param OnClickListner
 */
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void delete_request()  throws JSONException {

        JsonObject object = Requestobject();
        ApiService service = ServiceFactory.createRetrofitService(mContext, ApiService.class);
        mSubscription = service.postdelete(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Resdelete>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onNext(Resdelete resdelete) {

                        Resfert.ListResult item =list.get(selectedPO);
                        item.setStatus("Cancelled");
                        list.set(selectedPO,item);
                        Toast.makeText(mContext, mContext.getString(R.string.cancel_success), Toast.LENGTH_LONG).show();
                        notifyItemChanged(selectedPO);
//                        list.remove(selectedPO);
//                        Toast.makeText(mContext,mContext.getString(R.string.cancel_success),Toast.LENGTH_LONG).show();
//                        recreateActivityCompat((Activity) mContext);

                    }



                });
    }

    private JsonObject Requestobject() {
        DeleteObject requestModel = new DeleteObject();
        requestModel.setRequestCode(selectedItemID);
        requestModel.setStatusTypeId(32);
        requestModel.setUpdatedByUserId(null);
        requestModel.setUpdatedDate(currentDate);
        return new Gson().toJsonTree(requestModel).getAsJsonObject();


    }


    @Override
    public int getItemCount() {
        if (null != list)
            return list.size();
        else
            return 0;
    }


    public interface GetPoleAdapterListener {
        void onContactSelected(Resfert.ListResult list);
    }

}


