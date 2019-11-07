package in.calibrage.akshaya.views.actvity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.CircleTransform;
import in.calibrage.akshaya.models.GetCollectionInfoById;
import in.calibrage.akshaya.models.LabourTermsNCondtionsModel;
import in.calibrage.akshaya.models.ServiceType;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import in.calibrage.akshaya.views.Adapter.LabourTermsNCondtionsAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectionInfo extends AppCompatActivity {
String collection_id;
    ImageView img_profile;
    private TextView driverName, vehicleNumber, collectionCenter;
    private SpotsDialog mdilogue;
    private Subscription mSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_info);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            collection_id = extras.getString("collectionid");
            Log.e("collection_id===",collection_id);
        }
        init();

        setviews();
    }

    private void init() {
        driverName=findViewById(R.id.driverName);
        vehicleNumber=findViewById(R.id.vehicle_number);
        collectionCenter=findViewById(R.id.collection_center);
        img_profile=findViewById(R.id.img_profile);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
    }
    private void setviews() {
        CollectionInfoById();

    }

    private void CollectionInfoById() {
        mdilogue.show();
        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getinfo(APIConstantURL.CollectionInfoById +collection_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCollectionInfoById>() {
                    @Override
                    public void onCompleted() {
                        mdilogue.dismiss();
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
                        mdilogue.dismiss();
                        //showDialog(LabourActivity.this, getString(R.string.server_error));
                    }

                    @Override
                    public void onNext(GetCollectionInfoById getCollectionInfoById) {

                        if (getCollectionInfoById.getResult() != null) {
                            Picasso.with(CollectionInfo.this).load(getCollectionInfoById.getResult().getReceiptImg()).error(R.drawable.ic_user).transform(new CircleTransform()).into(img_profile);

                            driverName.setText(getCollectionInfoById.getResult().getDriverName());
                            vehicleNumber.setText(getCollectionInfoById.getResult().getVehicleNumber());
                            collectionCenter.setText(getCollectionInfoById.getResult().getCollectionCenter());
                        }

                    }


                });
    }
    }

