package thachdd.vuighenet.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import retrofit2.Call;
import thachdd.vuighenet.R;
import thachdd.vuighenet.adapter.MainRecyclerAdapter;
import thachdd.vuighenet.api_client.ApiClient;
import thachdd.vuighenet.api_client.ApiInterface;
import thachdd.vuighenet.api_client.SeasonsCallback;
import thachdd.vuighenet.model.SeasonsResponse;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar = null;
    private RecyclerView mRecycler = null;
    private AVLoadingIndicatorView mLoading = null;
    private RelativeLayout mLoadingContainer = null;

    private SeasonsCallback mSeasonsCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        mRecycler = (RecyclerView) findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        MainRecyclerAdapter adapter = new MainRecyclerAdapter();
        mRecycler.setAdapter(adapter);

        mLoadingContainer = (RelativeLayout) findViewById(R.id.main_loading_container);

        mLoading = (AVLoadingIndicatorView) findViewById(R.id.main_loading);
        mLoading.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);

        MenuItem searchItem = menu.findItem(R.id.main_action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        // add prediction to search view

        return super.onCreateOptionsMenu(menu);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

    public void showLoading(boolean mode) {
        if (mode) {
            mLoading.show();
            mLoadingContainer.setVisibility(View.VISIBLE);
        }
        else {
            mLoading.hide();
            mLoadingContainer.setVisibility(View.GONE);
        }
    }

    public void loadSeasons() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        mSeasonsCallback = new SeasonsCallback(this);
        api.getSeasons().enqueue(mSeasonsCallback);
    }

    public void onSeasonsLoaded(SeasonsResponse response) {

    }

    public void loadEpisodes(int seasonId) {

    }
}
