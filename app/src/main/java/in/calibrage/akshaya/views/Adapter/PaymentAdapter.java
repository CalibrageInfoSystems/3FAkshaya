package in.calibrage.akshaya.views.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.PaymentResponseModel;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder>{

    public Context mContext;
    private List<PaymentResponseModel.PaymentResponce> payment_Set;
    public PaymentAdapter(  Context context,List<PaymentResponseModel.PaymentResponce> payment_Set) {

        this.mContext=context;
        this.payment_Set=payment_Set;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_history_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(PaymentAdapter.ViewHolder holder, int position) {

        ((ViewHolder) holder).memo_text.setText(""+payment_Set.get(position).getMemo());
        ((ViewHolder) holder).date.setText(payment_Set.get(position).getRefDate());
        ((ViewHolder) holder).quantity_ffb.setText(""+payment_Set.get(position).getQuantity());
        ((ViewHolder) holder).adhoc_value.setText(""+payment_Set.get(position).getAdhocRate());
        ((ViewHolder) holder).txt_invoice.setText(""+payment_Set.get(position).getInvoiceRate());
        ((ViewHolder) holder).txt_gr_rate.setText(""+payment_Set.get(position).getGRAmount());
        ((ViewHolder) holder).adjustTxt.setText(""+payment_Set.get(position).getAdjusted());
        ((ViewHolder) holder).finalAmount.setText(""+payment_Set.get(position).getAmount());
        ((ViewHolder) holder).balance.setText(""+payment_Set.get(position).getBalance());


        if(position%2 == 0){
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white));

        } else {
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white));

        }
        // holder.textViewPowers.setText(powers);*/
        if (payment_Set.get(position).getAdhocRate()==0.0)
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.adhoc_value.setVisibility(View.GONE);
            holder.text_two.setVisibility(View.GONE);
        }
        else {
            holder.adhoc_value.setVisibility(View.VISIBLE);
            holder.text_two.setVisibility(View.VISIBLE);
        }
        if (payment_Set.get(position).getQuantity()==0.0)
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.text_one.setVisibility(View.GONE);
            holder.quantity_ffb.setVisibility(View.GONE);
        }
        else {
            holder.text_one.setVisibility(View.VISIBLE);
            holder.quantity_ffb.setVisibility(View.VISIBLE);
        }
        if (payment_Set.get(position).getInvoiceRate()==0.0)
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.txt_invoice.setVisibility(View.GONE);
            holder.text_thee.setVisibility(View.GONE);

        }
        else {
            holder.txt_invoice.setVisibility(View.VISIBLE);
            holder.text_thee.setVisibility(View.VISIBLE);
        }
        if (payment_Set.get(position).getGRAmount()==0.0)
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.txt_gr_rate.setVisibility(View.GONE);
            holder.text_four.setVisibility(View.GONE);

        }
        else {
            holder.txt_gr_rate.setVisibility(View.VISIBLE);
            holder.text_four.setVisibility(View.VISIBLE);
        }
//
//
        if (payment_Set.get(position).getAdjusted()==0.0)
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.adjustTxt.setVisibility(View.GONE);
            holder.text_five.setVisibility(View.GONE);

        }
        else {
            holder.adjustTxt.setVisibility(View.VISIBLE);
            holder.text_five.setVisibility(View.VISIBLE);
        }
//
//        if (superHero.getBankHolderName()==0.0)
//        {
//            //   Log.e("bbbbb",superHero.getmAmount());
//            holder. txtBankHolderName.setVisibility(View.GONE);
//            holder.text_four.setVisibility(View.GONE);
//
//        }
//        else {
//            holder. txtBankHolderName.setVisibility(View.VISIBLE);
//            holder.text_four.setVisibility(View.VISIBLE);
//        }

//
        if (payment_Set.get(position).getMemo()==null)
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.memo_text.setVisibility(View.GONE);
            holder.text_six.setVisibility(View.GONE);

        }
        else {
            holder.memo_text.setVisibility(View.VISIBLE);
            holder.text_six.setVisibility(View.VISIBLE);
        }
        if (payment_Set.get(position).getAmount()==0.0)
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.finalAmount.setVisibility(View.GONE);
            holder.text_seven.setVisibility(View.GONE);
        }
        else {
            holder.finalAmount.setVisibility(View.VISIBLE);
            holder.text_seven.setVisibility(View.VISIBLE);
        }
        if (payment_Set.get(position).getBalance()==0.0)
        {
            //   Log.e("bbbbb",superHero.getmAmount());
            holder.balance.setVisibility(View.GONE);
            holder.text_eight.setVisibility(View.GONE);
        }
        else {
            holder.balance.setVisibility(View.VISIBLE);
            holder.text_eight.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return payment_Set.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtLedgerItem;
        public TextView quantity_ffb;
        public TextView adhoc_value;
        public TextView txt_invoice;
        public TextView txt_gr_rate;
        public TextView memo_text;
        public TextView showMore;
        public TextView finalAmount;
        protected CardView card_view;
        public TextView  remarks,textViewPowers,date,balance,adjustTxt;
        public TextView  text_one,text_two,text_thee,text_four,text_five,text_six,text_seven,text_eight;
        public ViewHolder(View itemView) {
            super(itemView);

            //   txtLedgerItem = itemView.findViewById(R.id.ledgerItem);
            quantity_ffb = itemView.findViewById(R.id.quantity_ffb);
            adhoc_value = itemView.findViewById(R.id.adhoc_value);
            txt_invoice = itemView.findViewById(R.id.invoice);
            txt_gr_rate= itemView.findViewById(R.id.gr_rate);
            memo_text = itemView.findViewById(R.id.memo_text);
            finalAmount = itemView.findViewById(R.id.amount);
            adjustTxt = itemView.findViewById(R.id.adjusted);

            //contentLayout = (LinearLayout)itemView.findViewById(R.id.content);

            date= (TextView) itemView.findViewById(R.id.date);
            balance= (TextView) itemView.findViewById(R.id.balance);
            textViewPowers= (TextView) itemView.findViewById(R.id.textViewPowers);

            text_one= (TextView) itemView.findViewById(R.id.ffb_quantity);
            text_two= (TextView) itemView.findViewById(R.id.adhocLabel);
            text_thee= (TextView) itemView.findViewById(R.id.invoice_label);
            text_four= (TextView) itemView.findViewById(R.id.gr_amount_label);
            text_five= (TextView) itemView.findViewById(R.id.adjustedLabel);
            text_six= (TextView) itemView.findViewById(R.id.memo_label);
            text_seven= (TextView) itemView.findViewById(R.id.amount_label);
            text_eight= (TextView) itemView.findViewById(R.id.balanceLabel);

            card_view=itemView.findViewById(R.id.card_view);
        }
    }
}
