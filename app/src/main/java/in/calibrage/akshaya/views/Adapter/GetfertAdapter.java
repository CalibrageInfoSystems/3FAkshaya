package in.calibrage.akshaya.views.Adapter;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.ResPole;
import in.calibrage.akshaya.models.Resfert;

public class GetfertAdapter extends RecyclerView.Adapter<GetfertAdapter.ViewHolder> {

    List<Resfert.ListResult> list;
    public Context mContext;
    private Animation animationUp, animationDown;
    private final int COUNTDOWN_RUNNING_TIME = 500;
    private GetfertAdapter.GetPoleAdapterListener listener;
    public CardView card_view;
    String datetimevaluereq;
    // RecyclerView recyclerView;


    public GetfertAdapter(List<Resfert.ListResult> list, Context ctx, GetfertAdapter.GetPoleAdapterListener listener) {
        this.mContext = ctx;
        this.listener = listener;
        this.list = list;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        public LinearLayout contentLayout;
        public TextView requestCode;
        public TextView req_date;
        public TextView statusType;
        public TextView paymentMode, amount,cancel;
        public ImageView showMore;


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

            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                  //  listener.onContactSelected(list.get(getAdapterPosition()));

                }
            });
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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oneWayTripDate = input.parse(list.get(position).getReqCreatedDate());

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
        holder.amount.setText(list.get(position).getUsageAmount().toString());

        if (!"Closed".equals(holder.statusType.getText()))
        {
            holder.cancel.setVisibility(View.VISIBLE);

        }
        else {
            holder.cancel.setVisibility(View.GONE);
        }

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


    @Override
    public int getItemCount() {
        if (null != list)
            return list.size();
        else
            return 0;
    }


    public interface GetPoleAdapterListener {
        void onContactSelected(ResPole.ListResult products);
    }

}


