package thachdd.vuighenet.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.wang.avi.AVLoadingIndicatorView;

import thachdd.vuighenet.R;
import thachdd.vuighenet.api_client.ApiClient;
import thachdd.vuighenet.api_client.HerokuInterface;
import thachdd.vuighenet.api_client.PlayerCallback;

public class PlayerActivity extends AppCompatActivity {
    private SimpleExoPlayerView mExoView = null;
    private SimpleExoPlayer mExoPlayer = null;

    private RelativeLayout mLoadingContainer = null;
    private AVLoadingIndicatorView mLoading = null;
    private PlayerCallback mPlayerCallback = null;

    private final String URL_TAG = "url";
    private final String CURPOS_TAG = "curpos";
    private final String ISPLAY_TAG = "isplay";

    private String mCachedUrl = null;
    private Long mCachedCusPos = -1l;
    private Boolean mCachedIsPlaying = true;

    private boolean mClearCache = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mExoView = (SimpleExoPlayerView) findViewById(R.id.main_exoview);

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl();

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        mExoView.setPlayer(mExoPlayer);
        mExoView.setFastForwardIncrementMs(10000);
        mExoView.setRewindIncrementMs(10000);

        mLoadingContainer = (RelativeLayout) findViewById(R.id.player_loading_container);
        mLoading = (AVLoadingIndicatorView) findViewById(R.id.player_loading);
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadCache();
        mClearCache = true;

        if (mCachedUrl != null) {
            prepareVideo(mCachedUrl);
        } else {
            loadVideo();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onStop() {
        super.onStop();

        storeCache();

        if (mExoPlayer != null) {
            mExoPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mExoPlayer != null) {
            mExoPlayer.release();
        }

        if (mClearCache) {
            clearCache();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    public void loadVideo() {
        showLoading(true);

        int id = getIntent().getIntExtra("id", 0);

        mPlayerCallback = new PlayerCallback(this);
        HerokuInterface api = ApiClient.getHeroku().create(HerokuInterface.class);
        api.getPlayerDetail(id).enqueue(mPlayerCallback);
    }

    public void onPlayerLoadedSuccessfully(String url) {

        if (url == null) {
            Toast.makeText(this, "Khong the tai video", Toast.LENGTH_LONG).show();
            showLoading(false);
            finish();

            return;
        }

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(URL_TAG, url);
        edit.commit();

        prepareVideo(url);
    }

    public void onPlayerLoadedFailed() {
        Toast.makeText(this, "Khong the tai video", Toast.LENGTH_LONG).show();
        showLoading(false);
        finish();
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

    public void prepareVideo(String url) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "OnePieceLover"),
                bandwidthMeter);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory, extractorsFactory, null, null);

        if (mCachedCusPos != -1) {
            mExoPlayer.seekTo(mCachedCusPos);
        }
        mExoPlayer.setPlayWhenReady(mCachedIsPlaying);
        mExoPlayer.prepare(videoSource);

        showLoading(false);
    }

    public void rotate() {
        mClearCache = false;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void onBtnFullscreenClicked(View v) {
        rotate();
    }

    public void loadCache() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mCachedUrl = sharedPreferences.getString(URL_TAG, null);
        mCachedIsPlaying = sharedPreferences.getBoolean(ISPLAY_TAG, true);
        mCachedCusPos = sharedPreferences.getLong(CURPOS_TAG, -1);
    }

    public void storeCache() {
        long curPos = mExoPlayer.getCurrentPosition();
        boolean isPlaying = mExoPlayer.getPlayWhenReady();

        SharedPreferences.Editor edit = getPreferences(Context.MODE_PRIVATE).edit();
        edit.putLong(CURPOS_TAG, curPos);
        edit.putBoolean(ISPLAY_TAG, isPlaying);
        edit.commit();
    }

    public void clearCache() {
        SharedPreferences.Editor edit = getPreferences(Context.MODE_PRIVATE).edit();
        edit.clear();
        edit.commit();
    }
}
