package thachdd.vuighenet.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        mLoadingContainer = (RelativeLayout) findViewById(R.id.player_loading_container);
        mLoading = (AVLoadingIndicatorView) findViewById(R.id.player_loading);

        loadVideo();
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
        mExoPlayer.prepare(videoSource);

        showLoading(false);
    }
}
