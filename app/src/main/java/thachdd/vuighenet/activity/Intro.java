package thachdd.vuighenet.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import thachdd.vuighenet.R;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intro.this, Main.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
