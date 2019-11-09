package in.calibrage.akshaya.views.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.AnimationUtil;
import in.calibrage.akshaya.models.DeleteObject;
import in.calibrage.akshaya.models.ReqPole;
import in.calibrage.akshaya.models.ResPole;
import in.calibrage.akshaya.models.Resdelete;
import in.calibrage.akshaya.models.labour_req_response;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.actvity.CropMaintanceVisitActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyLabour_ReqAdapter extends RecyclerView.Adapter<MyLabour_ReqAdapter.ViewHolder> {

    private List<labour_req_response.ListResult> labourlist_Set = new ArrayList<>();
    public Context mContext;
    String request_date, prefferdate, currentDate;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;

    private Reqlister reqlister;
    // RecyclerView recyclerView;
    String selectedItemID;
    int selectedPO;

    public MyLabour_ReqAdapter(List<labour_req_response.ListResult> labourlist_Set, Context ctx) {
        this.labourlist_Set = labourlist_Set;
        this.mContext = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.my_req_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtPlotId.setText(labourlist_Set.get(position).getPlotCode());
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oneWayTripDate = input.parse(labourlist_Set.get(position).getStartDate());
            Date prefferdatee = input.parse(labourlist_Set.get(position).getCreatedDate());
            prefferdate = output.format(oneWayTripDate);
            request_date = output.format(prefferdatee);
            //datetimevalute.setText(output.format(oneWayTripDate));

            Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtDate.setText(request_date);

        //   holder.txtTime.setText(superHero.getTime() 0.6);
   holder.txtDateNTime.setText(labourlist_Set.get(position).getPalmArea() + " " + "Ha");
      //  holder.txtDateNTime.setText("0.6" + " " + "Ha");
        holder.txtReqDate.setText(labourlist_Set.get(position).getPlotVillage());
        holder.txtApproveDate.setText(prefferdate);

        holder.req_code.setText(labourlist_Set.get(position).getRequestCode());
        holder.txtStatus.setText(labourlist_Set.get(position).getStatusType());
        if (!"Closed".equals(holder.txtStatus.getText())) {
            holder.cancel.setVisibility(View.VISIBLE);

        } else {
            holder.cancel.setVisibility(View.GONE);
        }
        if (!"Cancelled".equals(holder.txtStatus.getText())) {
            holder.cancel.setVisibility(View.VISIBLE);

        } else {
            holder.cancel.setVisibility(View.GONE);
        }
        holder.txtname.setText(labourlist_Set.get(position).getServiceTypes());


        if (position % 2 == 0) {
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white));
        } else {
            holder.card_view.setCardBackgroundColor(mContext.getColor(R.color.white2));

        }
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        holder.cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedItemID = labourlist_Set.get(position).getRequestCode();
                selectedPO = position;
                try {
                    delete_request();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
        AnimationUtil.animate(holder, true);
    }

    private void delete_request() throws JSONException {

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
                        labour_req_response.ListResult item =labourlist_Set.get(selectedPO);
                        item.setStatusType("Cancelled");
                        labourlist_Set.set(selectedPO,item);
                        Toast.makeText(mContext, mContext.getString(R.string.cancel_success), Toast.LENGTH_LONG).show();
                        notifyItemChanged(selectedPO);

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
        if (null != labourlist_Set)
            return labourlist_Set.size();
        else
            return 0;
    }
    // return labourlist_Set.size();


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txtPlotId;
        public TextView txtDate;
        public TextView txtTime;
        public TextView txtComments;
        public TextView txtDateNTime;
        public TextView txtReqDate;
        public TextView txtApproveDate;
        public TextView txtStatus;
        public TextView txtname;
        public TextView cancel;
        public TextView req_code;
        public CardView card_view;
//"requestCode":"REQVWGBDAB00010001L127"

        public ViewHolder(View itemView) {
            super(itemView);


            txtPlotId = itemView.findViewById(R.id.plotId);
            txtDate = itemView.findViewById(R.id.req_date);
            req_code = itemView.findViewById(R.id.requestCode);
            cancel = itemView.findViewById(R.id.cancel);
            txtDateNTime = itemView.findViewById(R.id.dateNTime);
            txtReqDate = itemView.findViewById(R.id.village_name);
            txtApproveDate = itemView.findViewById(R.id.status_type);
            txtStatus = itemView.findViewById(R.id.status);
            txtname = itemView.findViewById(R.id.name);
            card_view = itemView.findViewById(R.id.card_view);
            //   txtPin = itemView.findViewById(R.id.pin);*/


        }


    }

    public interface Reqlister {
        void onContactSelected(labour_req_response.ListResult selectedItem);
    }


    @SuppressLint("NewApi")
    public static final void recreateActivityCompat(final Activity a) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        } else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finish();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }
    }
}




