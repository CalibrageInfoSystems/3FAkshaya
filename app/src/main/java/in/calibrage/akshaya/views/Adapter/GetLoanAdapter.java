package in.calibrage.akshaya.views.Adapter;




import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import in.calibrage.akshaya.models.ResLoan;
import in.calibrage.akshaya.models.Resdelete;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.views.Adapter.MyLabour_ReqAdapter.recreateActivityCompat;


public class GetLoanAdapter extends RecyclerView.Adapter<GetLoanAdapter.ViewHolder>{

    List<ResLoan.ListResult> list_loan;
    public Context mContext;
    String datetimevaluereq,currentDate;
    String selectedItemID;
    int selectedPO;
    private Subscription mSubscription;
    // RecyclerView recyclerView;
    public GetLoanAdapter(  List<ResLoan.ListResult> list_loan, Context ctx) {
        this.list_loan = list_loan;
        this.mContext=ctx;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.loan_req_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //      holder.imageView.setImageResource(listdata[position].getImgId());

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oneWayTripDate = input.parse(list_loan.get(position).getReqCreatedDate());

            datetimevaluereq = output.format(oneWayTripDate);
            //datetimevalute.setText(output.format(oneWayTripDate));

            Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.requestCode.setText(list_loan.get(position).getRequestCode());
        holder.req_date.setText(datetimevaluereq);
          holder.statusType.setText(list_loan.get(position).getStatusType());
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
                selectedItemID = list_loan.get(position).getRequestCode();
                selectedPO = position;
                try {
                    delete_request();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });


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
                        list_loan.remove(selectedPO);
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

        if (list_loan != null)
            return list_loan.size();
        else
            return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {



        public TextView requestCode;
        public TextView req_date;
        public TextView statusType,cancel;



        public ViewHolder(View itemView) {
            super(itemView);



            requestCode = itemView.findViewById(R.id.requestCode);
            req_date = itemView.findViewById(R.id.reqCreatedDate);
            statusType = itemView.findViewById(R.id.statusType);

              cancel = itemView.findViewById(R.id.cancel);
            //   txtPin = itemView.findViewById(R.id.pin);


        }


    }
}


