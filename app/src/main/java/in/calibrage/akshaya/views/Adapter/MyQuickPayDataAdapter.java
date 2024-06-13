package in.calibrage.akshaya.views.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.models.DeleteObject;
import in.calibrage.akshaya.models.GetQuickpayDocumentRes;
import in.calibrage.akshaya.models.MSGmodel;
import in.calibrage.akshaya.models.Resdelete;
import in.calibrage.akshaya.models.Resquickpay;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.actvity.Quickpay_SummaryActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static in.calibrage.akshaya.views.Adapter.MyLabour_ReqAdapter.recreateActivityCompat;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


public class MyQuickPayDataAdapter extends RecyclerView.Adapter<MyQuickPayDataAdapter.ViewHolder> {

    List<Resquickpay.ListResult> list;
    public Context mContext;
    String datetimevaluereq, currentDate;
    // RecyclerView recyclerView;
    String selectedItemID , pdf_url;
    int selectedPO;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;


    DecimalFormat df = new DecimalFormat("####0.00");
    public MyQuickPayDataAdapter(List<Resquickpay.ListResult> list, Context ctx) {
        this.list = list;
        this.mContext = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.quick_req_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oneWayTripDate = input.parse(list.get(position).getReqCreatedDate());

            datetimevaluereq = output.format(oneWayTripDate);


            Log.e("===============", "======currentData======" + output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(mContext)
                .setTheme(R.style.Custom)
                .build();
        holder.requestCode.setText(list.get(position).getRequestCode());
        holder.req_date.setText(datetimevaluereq);
        holder.statusType.setText(list.get(position).getStatusType());
        holder.amount.setText(df.format(list.get(position).getTotalCost()));
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//        if (!"Closed".equals(holder.statusType.getText())) {
//            holder.cancel.setVisibility(View.VISIBLE);
//
//        } else {
//            holder.cancel.setVisibility(View.GONE);
//        }
//        if (!"Cancelled".equals(holder.statusType.getText())) {
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
//                try {
//                    delete_request();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        });

        holder.details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedItemID = list.get(position).getRequestCode();
                selectedPO = position;
                GetQuickpayDocument(selectedPO,selectedItemID);





            }

        });
    }
    // Get Quickpay Document
    private void GetQuickpayDocument(final int selectedPO, String selectedItemID) {
        ApiService service = ServiceFactory.createRetrofitService(mContext, ApiService.class);
        mSubscription = service.getPdfurl(APIConstantURL.GetQuickpayDocument + selectedItemID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetQuickpayDocumentRes>() {
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
                        // mdilogue.dismiss();
                        //showDialog(LabourActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetQuickpayDocumentRes getQuickpayDocumentRes) {
                        if(getQuickpayDocumentRes.getResult() != null){
                            pdf_url= getQuickpayDocumentRes.getResult();
                            Log.e("===>pdf_url",pdf_url+"");
                            showCondetailsDialog(selectedPO,pdf_url);
                        }




                    }



                });}

//    private void showCondetailsDialog(int selectedPO, final String pdf_url) {
//
//        mdilogue.show();
//        final Dialog dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.pdf_dialog);
//        final WebView webView = dialog.findViewById(R.id.webViewpdf);
//        Button btn_dialog = dialog.findViewById(R.id.btn_dialog);
//
//        String doc = "https://docs.google.com/gview?embedded=true&url=" + pdf_url;
//        //  String doc =  result;
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.loadUrl(doc);
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                mdilogue.dismiss();
//            }
//        });
//
//        btn_dialog.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//            }
//        });
//
//        dialog.show();
//
//
//    }

    private void showCondetailsDialog(int selectedPO, final String pdf_url) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pdf_dialog);
        final WebView webView = dialog.findViewById(R.id.webViewpdf);
        final ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        Button btn_dialog = dialog.findViewById(R.id.btn_dialog);

        progressBar.setVisibility(View.VISIBLE); // Show the progress bar initially
       // String pdfurl = "https://3FAkshaya.com/3FAkshayaRepo/FileRepository/2023/07/28/QuickpayPdf/20230728111114410.pdf";
        //TODO
         String doc = "https://docs.google.com/gview?embedded=true&url=" + pdf_url;

      //  String doc = "https://docs.google.com/gview?embedded=true&url=" + pdfurl;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl(doc);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE); // Hide the progress bar when page is loaded
            }
        });

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }





    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView requestCode;
        public TextView req_date;
        public TextView statusType;
        public TextView amount, cancel;

        LinearLayout details;
        private SpotsDialog mdilogue;
        public ViewHolder(View itemView) {
            super(itemView);


            requestCode = itemView.findViewById(R.id.requestCode);
            req_date = itemView.findViewById(R.id.reqCreatedDate);
            statusType = itemView.findViewById(R.id.statusType);
            amount = itemView.findViewById(R.id.amount);
            cancel = itemView.findViewById(R.id.cancel);
            details =itemView.findViewById(R.id.details);



        }


    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }
}

