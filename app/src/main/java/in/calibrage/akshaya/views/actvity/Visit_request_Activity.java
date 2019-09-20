package in.calibrage.akshaya.views.actvity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;
import in.calibrage.akshaya.common.CommonUtil;
import in.calibrage.akshaya.models.GetIssueModel;
import in.calibrage.akshaya.service.APIConstantURL;
import in.calibrage.akshaya.service.ApiService;
import in.calibrage.akshaya.service.ServiceFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Visit_request_Activity extends AppCompatActivity {
    String plot_Age, location, landmarkCode, plot_id, Farmer_code;
    private Subscription mSubscription;
    private SpotsDialog mdilogue;
    ImageView backImg, home_btn;
    private TextView Age, id_plot, area, landMark;
    List<String> Issue_type = new ArrayList<String>();
    private static final String TAG = Visit_request_Activity.class.getSimpleName();
    List<Integer> Issue_Id = new ArrayList<Integer>();
    Spinner Select_Issue;
    String selected_issue;
    ImageButton btn_addIMG;
    private Button btn;
    private ImageView imageview, imageview2, imageview3;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;

    private List<Bitmap> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_request_);
        requestMultiplePermissions();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            plot_id = extras.getString("plotid");
            plot_Age = extras.getString("plotAge");
            location = extras.getString("plotVillage");
            landmarkCode = extras.getString("landMark");

        }
        intview();
        setViews();


    }


    private void intview() {
        requestMultiplePermissions();
        backImg = (ImageView) findViewById(R.id.back);
        home_btn = (ImageView) findViewById(R.id.home_btn);
        btn_addIMG = findViewById(R.id.btn_addIMG);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();

        SharedPreferences pref = getSharedPreferences("FARMER", MODE_PRIVATE);
        Farmer_code = pref.getString("farmerid", "");       // Saving string data of your editext
        Age = findViewById(R.id.age_plot);
        id_plot = findViewById(R.id.plot);
        area = findViewById(R.id.palmArea);
        landMark = findViewById(R.id.landmark);
        Select_Issue = findViewById(R.id.issue_type);

        imageview = (ImageView) findViewById(R.id.iv);
        imageview2 = (ImageView) findViewById(R.id.iv2);
        imageview3 = (ImageView) findViewById(R.id.iv3);

        imageview.setVisibility(View.GONE);
        imageview2.setVisibility(View.GONE);
        imageview2.setVisibility(View.GONE);
        btn_addIMG.setVisibility(View.VISIBLE);

    }


    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void setViews() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(Visit_request_Activity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        Age.setText(plot_Age);
        area.setText(location);
        id_plot.setText(plot_id);
        landMark.setText(landmarkCode);

        GetIssue_type();
        Select_Issue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_issue = Select_Issue.getItemAtPosition(Select_Issue.getSelectedItemPosition()).toString();

                //  Log.e("seleced_period===", seleced_Duration);
//                durationId = period_id.get(labourSpinner.getSelectedItemPosition());
                //Log.e("duration======", String.valueOf(durationId));
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


      /*  imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                add_text.setVisibility(View.VISIBLE);
            }

        });
         imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                add_text.setVisibility(View.VISIBLE);
            }
        });
        imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        */
        btn_addIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images.size() > 2) {
                    Toast.makeText(Visit_request_Activity.this, "max Images 3", Toast.LENGTH_SHORT).show();
                } else {
                    showPictureDialog();
                }


            }
        });

    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Log.e("path===", path);
                    //   Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    //imageview.setImageBitmap(bitmap);
                    images.add(bitmap);
                    displayImages();
                } catch (IOException e) {
                    e.printStackTrace();
                    //  Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            images.add(thumbnail);
            saveImage(thumbnail);
            displayImages();
            //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayImages() {

        if (images.size() > 0) {
            if (images.size() > 0 && images.get(0) != null) {
                imageview.setImageBitmap(images.get(0));
                imageview.setVisibility(View.VISIBLE);

            }
            if (images.size() > 1 && images.get(1) != null) {
                imageview2.setImageBitmap(images.get(1));
                imageview2.setVisibility(View.VISIBLE);
            }
            if (images.size() > 2 && images.get(2) != null) {
                imageview3.setImageBitmap(images.get(2));
                imageview3.setVisibility(View.VISIBLE);
                btn_addIMG.setVisibility(View.GONE);
            }
        }

    }

    public void onclcik() {
        StringBuilder builder = null;
        for (int i = 0; i < images.size(); i++) {
            String base64 = CommonUtil.bitMaptoBase64(images.get(i));
            if (i == 0) {
                builder.append(base64);
            } else
                builder.append("," + base64);
        }
        String base64 = builder.toString();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void GetIssue_type() {


        ApiService service = ServiceFactory.createRetrofitService(this, ApiService.class);
        mSubscription = service.getIssuestypes(APIConstantURL.GetIssue)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetIssueModel>() {
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
                        mdilogue.cancel();
                    }

                    @Override
                    public void onNext(GetIssueModel getIssueModel) {


                        if (getIssueModel.getListResult() != null) {
                            Issue_type.add("Select");
                            for (GetIssueModel.ListResult data : getIssueModel.getListResult()
                            ) {
                                Issue_type.add(data.getDesc());
                                Issue_Id.add(data.getTypeCdId());
                            }
                            Log.d(TAG, "RESPONSE======" + Issue_type);

//

                            ArrayAdapter aa = new ArrayAdapter(Visit_request_Activity.this, R.layout.spinner_item, Issue_type);
                            aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                            Select_Issue.setAdapter(aa);
                        } else {
                            Log.e("nodada====", "nodata===custom2");

                        }

                    }

                });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
