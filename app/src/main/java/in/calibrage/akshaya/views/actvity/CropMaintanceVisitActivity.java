package in.calibrage.akshaya.views.actvity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import in.calibrage.akshaya.R;
import in.calibrage.akshaya.views.Adapter.CropMaintenanceVisitAdapter;

public class CropMaintanceVisitActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CropMaintenanceVisitAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_maintance_visit);
    }
}
