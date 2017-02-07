package thachdd.vuighenet.activity;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import thachdd.vuighenet.R;
import thachdd.vuighenet.adapter.MainRecyclerAdapter;
import thachdd.vuighenet.api_client.ApiClient;
import thachdd.vuighenet.api_client.ApiInterface;
import thachdd.vuighenet.api_client.EpisodesCallback;
import thachdd.vuighenet.api_client.SeasonsCallback;
import thachdd.vuighenet.model.EpisodeDetail;
import thachdd.vuighenet.model.EpisodesResponse;
import thachdd.vuighenet.model.SeasonDetail;
import thachdd.vuighenet.model.SeasonsResponse;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar = null;
    private RecyclerView mRecycler = null;
    private AVLoadingIndicatorView mLoading = null;
    private RelativeLayout mLoadingContainer = null;
    private Spinner mSpinner = null;

    private SeasonsCallback mSeasonsCallback = null;
    private List<SeasonDetail> mSeasons =  new ArrayList<>();
    private EpisodesCallback mEpisodesCallback = null;
    private List<EpisodeDetail> mEpisodes = new ArrayList<>();

    private MainRecyclerAdapter mAdapter = null;

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

        mAdapter = new MainRecyclerAdapter();
        mRecycler.setAdapter(mAdapter);

        mSpinner = (Spinner) findViewById(R.id.main_spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadEpisodes(mSeasons.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(mSpinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        mLoadingContainer = (RelativeLayout) findViewById(R.id.main_loading_container);
        mLoading = (AVLoadingIndicatorView) findViewById(R.id.main_loading);

        loadSeasons();
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
        showLoading(true);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        mSeasonsCallback = new SeasonsCallback(this);
        api.getSeasons().enqueue(mSeasonsCallback);
    }

    public void onSeasonsLoadedSuccessfully(SeasonsResponse response) {
        mSeasons = response.getSeasonsDetail();

        ArrayList<String> spinnerData = new ArrayList<>();
        for (SeasonDetail season : mSeasons) {
            spinnerData.add(season.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_item, spinnerData);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        loadEpisodes(mSeasons.get(0).getId());
    }

    public void onSeasonsLoadedFailed() {
        mLoading.hide();
        Toast.makeText(this, "Khong the tai duoc du lieu", Toast.LENGTH_LONG).show();
        finish();
    }

    public void loadEpisodes(int seasonId) {
        showLoading(true);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        mEpisodesCallback = new EpisodesCallback(this);
        api.getEpisodes(seasonId).enqueue(mEpisodesCallback);
    }

    public void onEpisodesLoadedSuccessfully(EpisodesResponse response) {
        mAdapter.setEpisodes(response.getEpisodesDetail());
        mAdapter.notifyDataSetChanged();

        showLoading(false);
    }

    public void onEpisodesLoadedFailed() {
        mLoading.hide();
        Toast.makeText(this, "Khong the tai duoc du lieu", Toast.LENGTH_LONG).show();
        finish();
    }
}
