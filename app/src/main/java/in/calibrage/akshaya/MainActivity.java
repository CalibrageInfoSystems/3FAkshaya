package in.calibrage.akshaya;


import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView checkView;
    private ImageView crossView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkView = (ImageView) findViewById(R.id.check);
        crossView = (ImageView) findViewById(R.id.cross);


        checkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Animatable) checkView.getDrawable()).start();
            }
        });

        crossView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Animatable) crossView.getDrawable()).start();
            }
        });
    }
}
