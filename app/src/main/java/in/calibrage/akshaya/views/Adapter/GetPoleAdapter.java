package in.calibrage.akshaya.views.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.IOException;
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
import in.calibrage.akshaya.views.actvity.RequestListctivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.views.Adapter.MyLabour_ReqAdapter.recreateActivityCompat;


public class GetPoleAdapter extends RecyclerView.Adapter<GetPoleAdapter.ViewHolder> {

    List<ResPole.ListResult> list;
    public Context mContext;
    private Animation animationUp, animationDown;
    private final int COUNTDOWN_RUNNING_TIME = 500;
    private GetPoleAdapterListener1 listener;
    public CardView card_view;
    String datetimevaluereq,currentDate;
    // RecyclerView recyclerView;
    String selectedItemID;
    int selectedPO;
    private Subscription mSubscription;


    public GetPoleAdapter(List<ResPole.ListResult> list, Context ctx, GetPoleAdapterListener1 listener) {
        this.mContext = ctx;
        this.listener = listener;
        this.list = list;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        public LinearLayout contentLayout;
        public TextView requestCode;
        public TextView req_date;
        public TextView statusType;
        public TextView paymentMode, amount;
        public ImageView showMore;
        public TextView cancel;

        public ViewHolder(View itemView) {
            super(itemView);

            showMore = (ImageView)itemView.findViewById(R.id.show_more);
            contentLayout = (LinearLayout)itemView.findViewById(R.id.linear1);
            requestCode = itemView.findViewById(R.id.requestCode);
            req_date = itemView.findViewById(R.id.reqCreatedDate);
            statusType = itemView.findViewById(R.id.statusType);
            paymentMode = itemView.findViewById(R.id.paymentMode);
          card_view =   itemView.findViewById(R.id.card_view);
          amount=itemView.findViewById(R.id.amount);
            cancel = itemView.findViewById(R.id.cancel);
            //   txtPin = itemView.findViewById(R.id.pin);


        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.pole_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oneWayTripDate = input.parse(list.get(position).getUpdatedDate());

            datetimevaluereq = output.format(oneWayTripDate);
            //datetimevalute.setText(output.format(oneWayTripDate));

            Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.requestCode.setText(list.get(position).getRequestCode());
        holder.req_date.setText(datetimevaluereq);
        holder.statusType.setText(list.get(position).getStatus());
        holder.paymentMode.setText(list.get(position).getPaymentMode());
        if(null !=list.get(position).getUsageAmount() )
        {
            holder.amount.setText(list.get(position).getUsageAmount().toString());
        }

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (!"Closed".equals(holder.statusType.getText()))
        {
            holder.cancel.setVisibility(View.VISIBLE);

        }
        else {
            holder.cancel.setVisibility(View.GONE);
        }
        if (!"Cancelled".equals(holder.statusType.getText())) {
            holder.cancel.setVisibility(View.VISIBLE);

        } else {
            holder.cancel.setVisibility(View.GONE);
        }

        holder.cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedItemID = list.get(position).getRequestCode();
                selectedPO = position;
                try {
                    delete_request();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onContactSelected1(list.get(position).getRequestCode().toString());
            }
        });
        //      holder.imageView.setImageResource(listdata[position].getImgId());


      /*  PoleModel superHero =  superHeroes.get(position);

        holder.requestCode.setText(superHero.getRequestCode());
        holder.req_date.setText(superHero.getReqCreatedDate());
        holder.statusType.setText(superHero.getStatusType());
        holder.paymentMode.setText(superHero.getPaymentMode());
      holder.amount.setText(superHero.getamount());*/
        String powers = "";

       /* for(int i = 0; i<superHero.getPowers().size(); i++){
            powers+= superHero.getPowers().get(i);
        }*/
//        holder.showMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (holder.contentLayout.isShown()) {
//                    holder.contentLayout.setAnimation(animationUp);
//
//                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            holder.contentLayout.setVisibility(View.GONE);
//                        }
//                    };
//                    countDownTimerStatic.start();
//
//                    //  rowViewHolder.showMore.setText(context.getString(R.string.show));
//                    holder.showMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_dow, 0);
//                } else {
//                    GetProductDetailsByRequestCode();
//                    holder.contentLayout.setVisibility(View.VISIBLE);
//
//                    holder.contentLayout.setAnimation(animationDown);
//
//                    //   rowViewHolder.showMore.setText(context.getString(R.string.hide));
//                    holder.showMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
//                }
//            }
//        });
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
                        list.remove(selectedPO);
                        Toast.makeText(mContext,mContext.getString(R.string.cancel_success),Toast.LENGTH_LONG).show();
                        recreateActivityCompat((Activity) mContext);

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


    public interface GetPoleAdapterListener1 {
        void onContactSelected1(String products);
    }

}


