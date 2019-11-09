package in.calibrage.akshaya.views.actvity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import in.calibrage.akshaya.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String name;
    private double lat,log;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    private void init() {
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
        if (getIntent() != null) {
            name = getIntent().getStringExtra("name");
            lat = getIntent().getDoubleExtra("lat",0.0);
            log = getIntent().getDoubleExtra("log",0.0);
Log.e("lat===",lat+"===="+log +"," +name );
            TextView txt_name = findViewById(R.id.txt_name);
            txt_name.setText(name);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        LatLng sydney = new LatLng(lat, log);
//        googleMap.addMarker(new MarkerOptions().position(sydney).title(name));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        googleMap.setMinZoomPreference(11);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, log))
                .title(name));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, log), 3));

//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
