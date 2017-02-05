package thachdd.vuighenet.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import thachdd.vuighenet.R;
import thachdd.vuighenet.api_client.ApiClient;
import thachdd.vuighenet.api_client.ApiInterface;
import thachdd.vuighenet.api_client.SeasonsCallback;
import thachdd.vuighenet.model.SeasonsResponse;

public class IntroActivity extends AppCompatActivity {
    private SeasonsCallback seasonsCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        seasonsCallback = new SeasonsCallback(this);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        api.getSeasons().enqueue(seasonsCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1000);
    }

    public void showResult(SeasonsResponse response) {
        int a;

        // do nothing
    }
}
