package in.calibrage.akshaya.views.actvity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import dmax.dialog.SpotsDialog;
import in.calibrage.akshaya.R;

public class FertlizerRecomandationsActivity extends AppCompatActivity {

    private static final String TAG = FertlizerRecomandationsActivity.class.getSimpleName();

    private Context ctx;
    private LinearLayoutManager lytmanager;
    private RecyclerView lst_reco;
    private TextView txt_nodata;
    private SpotsDialog mdilogue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertlizer_recomandations);
        init();
        setviews();
        setToolbar();
    }

    private void init() {
        ctx = this;
        lytmanager =new LinearLayoutManager(ctx);
        lst_reco = findViewById(R.id.rcv_recom);
        txt_nodata = findViewById(R.id.txt_nodata);
        mdilogue = (SpotsDialog) new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.Custom)
                .build();
    }
    private void setviews() {
    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
