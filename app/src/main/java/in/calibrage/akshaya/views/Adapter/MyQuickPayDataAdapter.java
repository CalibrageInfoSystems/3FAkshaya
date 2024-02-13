package in.calibrage.akshaya.views.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.v7.widget.RecyclerView;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
import in.calibrage.akshaya.views.actvity.HomeActivity;
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
                        if(getQuickpayDocumentRes.getResult()!=null){
                            pdf_url= getQuickpayDocumentRes.getResult();
//
//                            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//                            browserIntent.setData(Uri.parse(pdf_url));
//                            mContext.startActivity(browserIntent);

                            new OpenBrowserTask().execute(pdf_url);

                            Intent homeIntent = new Intent(mContext, HomeActivity.class);
                                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    mContext.startActivity(homeIntent);

                            // Open the browser after a delay
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//                                    browserIntent.setData(Uri.parse(pdf_url));
//                                    mContext.startActivity(browserIntent);
//                                }
//                            }, 1000); // Delay in milliseconds (adjust as needed)
//
//                            // Navigate to Home Activity after the delay
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent homeIntent = new Intent(mContext, HomeActivity.class);
//                                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    mContext.startActivity(homeIntent);
//                                    //mContext.finish();  // Finish the current activity if needed
//                                }
//                            }, 10000); // Delay in milliseconds (adjust as needed)


                            //showCondetailsDialog(selectedPO,pdf_url);
                        }




                    }



    });}

    private class OpenBrowserTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(browserIntent);
            return null;
        }
    }

//    private void showCondetailsDialog(int selectedPO, final String pdf_url) {
//        final Dialog dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.pdf_dialog);
//
//        final WebView webview = dialog.findViewById(R.id.webViewpdf);
//
//        if (webview != null) {
//            WebSettings webSettings = webview.getSettings();
//            if (webSettings != null) {
//                webSettings.setJavaScriptEnabled(true);
//                webSettings.setDomStorageEnabled(true);
//                webSettings.setAllowFileAccess(true);  // Enable file access
//                webSettings.setAllowContentAccess(true);
//
//                webview.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                        super.onPageStarted(view, url, favicon);
//                        Log.d("WebView", "onPageStarted: " + url);
//                    }
//
//                    @Override
//                    public void onPageFinished(WebView view, String url) {
//                        super.onPageFinished(view, url);
//                        Log.d("WebView", "onPageFinished: " + url);
//                    }
//
//                    @Override
//                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                        Log.e("WebView Error", "Error: " + description);
//                        super.onReceivedError(view, errorCode, description, failingUrl);
//                    }
//                });
//
//               // webview.loadUrl("https://www.example.com/sample.pdf");
////                try {
////                    String encodedUrl = URLEncoder.encode(pdf_url, "UTF-8");
////                    webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + encodedUrl);
////                } catch (UnsupportedEncodingException e) {
////                    e.printStackTrace();
////                    // Handle the exception as needed
////                }
//
////                try {
////                    String encodedUrl = URLEncoder.encode(pdf_url, "UTF-8");
////                    encodedUrl = encodedUrl.replace("%2F", "/");  // Replace %2F with /
////                    encodedUrl = encodedUrl.replace("%5C", "/");  // Replace %5C with /
////
////                    webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + encodedUrl);
////                } catch (UnsupportedEncodingException e) {
////                    e.printStackTrace();
////                    // Handle the exception as needed
////                }
//
//
//                webview.loadUrl("https://docs.google.com/viewerng/viewer?embedded=true&url=" + pdf_url.trim());
//
//                //webview.loadUrl("https://mozilla.github.io/pdf.js/web/viewer.html?file=" + pdf_url);
//
//
//                // Continue with dialog setup...
//                Button btn_dialog = dialog.findViewById(R.id.btn_dialog);
//                if (btn_dialog != null) {
//                    btn_dialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    // Show the dialog
//                    dialog.show();
//                } else {
//                    Log.e("Dialog Error", "Button not found in the layout.");
//                }
//            } else {
//                Log.e("WebView Error", "WebSettings is null.");
//            }
//        } else {
//            Log.e("WebView Error", "WebView is null.");
//        }
//    }

    private void showCondetailsDialog(int selectedPO, final String result) {
        mdilogue.show();
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pdf_dialog);
        final ImageView webView = dialog.findViewById(R.id.webViewpdf);
        Button btn_dialog = dialog.findViewById(R.id.btn_dialog);

        // Load PDF directly without Google Docs Viewer
        String doc = result;
      //  File fileToDownLoad;



        Bitmap[] images = convertPdfToImages(mContext, pdf_url);

        if (images != null && images.length > 0) {
            webView.setImageBitmap(images[0]);
        }

       // new DownloadpdfFileFromURL().execute(result);


       // fileToDownLoad = new File(get3FFileRootPath() + "3F_AkshayaFiles/" + result);


//        webView.fromFile(fileToDownLoad)
//                .load();

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setAllowContentAccess(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setDisplayZoomControls(false);
//
//        // Enable plugins (if needed)
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//
//        // Set a WebViewClient to handle onPageFinished and dismiss the dialog
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                mdilogue.dismiss();
//            }
//        });
//
//        // Load the PDF URL
//        webView.loadUrl("http://182.18.157.215/3FAkshaya/3FAkshaya_Repo/FileRepository/2024/02/09/QuickpayPdf/20240209042255049.pdf");

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //List<MSGmodel> displayList = new ArrayList<>();
               // showSuccessDialog(displayList, getResources().getString(R.string.qucick_success));
            }
        });

        dialog.show();
    }



//    private void showCondetailsDialog(int selectedPO, final String pdf_url) {
//
//        final Dialog dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.pdf_dialog);
//        Button btn_dialog = dialog.findViewById(R.id.btn_dialog);
//
//            final WebView webview = dialog.findViewById(R.id.webViewpdf);
////            webview.getSettings().setJavaScriptEnabled(true);
////            webview.getSettings().setSupportZoom(true);
////            webview.getSettings().setBuiltInZoomControls(true);
////            //   String doc = "https://docs.google.com/gview?embedded=true&url=" + result;
////
////            webview.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf_url);
//
//        //String doc = "https://docs.google.com/gview?embedded=true&url=" + result;
//        String doc =  pdf_url;
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setAllowFileAccess(true);
//        webview.getSettings().setLoadsImagesAutomatically(true);
//        webview.getSettings().setDomStorageEnabled(true);
//        webview.loadUrl(doc);
//
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                mdilogue.dismiss();
//
//
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//
//
//
////        webView.setWebViewClient(new WebViewClient() {
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                view.loadUrl(url);
////                return false;
////            }
////        });
//
////            webview.setWebChromeClient(new WebChromeClient() {
////                @Override
////                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
////                    Log.d("WebView Console", consoleMessage.message());
////                    return true;
////                }
////            });
////
////            webview.setWebViewClient(new WebViewClient() {
////                @Override
////                public void onPageStarted(WebView view, String url, Bitmap favicon) {
////                    super.onPageStarted(view, url, favicon);
////
////                    webview.loadUrl("javascript:(function() { " +
////                            "document.querySelector('[role=\"toolbar\"]').remove();})()");
////                    mdilogue.show();
////                }
////                @Override
////                public void onPageFinished(final WebView view, String url) {
////                    super.onPageFinished(view, url);
////                    mdilogue.dismiss();
////                    if (view.getContentHeight() == 0) {
////                        view.reload();
////                        view.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf_url);
////                    }
//////
////
////                    webview.loadUrl("javascript:(function() { " +
////                            "document.querySelector('[role=\"toolbar\"]').remove();})()");
////
////
////                }
////            });
//
//        btn_dialog.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//            }
//        });
//        dialog.show();
//
//    }


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
                        Resquickpay.ListResult item =list.get(selectedPO);
                        item.setStatusType("Cancelled");
                        list.set(selectedPO,item);
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


    public static String get3FFileRootPath() {
        String root = Environment.getExternalStorageDirectory().toString();
        File rootDirectory = new File(root + "/3F_AkshayaFiles");
        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }
        return rootDirectory.getAbsolutePath() + File.separator;
    }

    public class DownloadpdfFileFromURL extends AsyncTask<String, Void, String> {

        private boolean downloadSuccess = false;
        private String filename;
        private String fileExtension;

        public DownloadpdfFileFromURL() {
            this.filename = filename;
            this.fileExtension = fileExtension;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String rootDirectory = get3FFileRootPath() + "3F_QuickPay/";
                File directory = new File(rootDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                OutputStream output = new FileOutputStream(rootDirectory + filename + fileExtension);

                byte data[] = new byte[1024];

                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                downloadSuccess = true;
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                downloadSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if (downloadSuccess) {
                File fileToDownload = new File(get3FFileRootPath() + "3F_DigitalContract/" + "Arun" + ".pdf");
                Log.d("File Path:", fileToDownload.getAbsolutePath());
                if (fileToDownload.exists()) {
                    Log.d("File Path:", fileToDownload.getAbsolutePath());
                } else {
                    Toast.makeText(mContext, "File does not exist", Toast.LENGTH_SHORT).show();
                   // UiUtils.showCustomToastMessage("File does not exist", SplashScreen.this, 1);
                }
            }
        }
    }

        public static Bitmap[] convertPdfToImages(Context context, String pdfFileName) {
            try {
                // Use AssetManager to open the PDF file
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open(pdfFileName); // Assuming the pdf is in the 'pdf' subfolder

                // Write InputStream to a temporary file
                File tempFile = createTempFile(context, inputStream);

                // Open a ParcelFileDescriptor from the temporary file
                ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY);

                // Open the PDF file using PdfRenderer
                PdfRenderer pdfRenderer = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    pdfRenderer = new PdfRenderer(parcelFileDescriptor);
                }

                // Get the number of pages in the PDF
                int pageCount = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    pageCount = pdfRenderer.getPageCount();
                }

                // Create an array to store the images
                Bitmap[] images = new Bitmap[pageCount];

                // Iterate through each page and render it as a bitmap
                for (int i = 0; i < pageCount; i++) {
                    PdfRenderer.Page page = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        page = pdfRenderer.openPage(i);
                    }

                    // Create a bitmap with the same size as the page
                    Bitmap bitmap = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
                    }

                    // Render the page content to the bitmap
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                    }

                    // Store the bitmap in the array
                    images[i] = bitmap;

                    // Close the page
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        page.close();
                    }
                }

                // Close the PDF renderer
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    pdfRenderer.close();
                }

                // Clean up the temporary file
                tempFile.delete();

                return images;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private static File createTempFile(Context context, InputStream inputStream) {
            try {
                File tempFile = File.createTempFile("temp", ".pdf", context.getCacheDir());
                FileOutputStream fos = new FileOutputStream(tempFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                fos.close();
                inputStream.close();

                return tempFile;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

}

