package thachdd.vuighenet.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.wang.avi.AVLoadingIndicatorView;

import thachdd.vuighenet.R;
import thachdd.vuighenet.api_client.ApiClient;
import thachdd.vuighenet.api_client.HerokuInterface;
import thachdd.vuighenet.api_client.PlayerCallback;

public class PlayerActivity extends AppCompatActivity {
    private VideoView mVideoView = null;
    private MediaController mMediaController = null;

    private RelativeLayout mLoadingContainer = null;
    private AVLoadingIndicatorView mLoading = null;
    private PlayerCallback mPlayerCallback = null;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mVideoView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mVideoView = (VideoView) findViewById(R.id.player_videoview);
        mMediaController = new MediaController(this);
        mMediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mMediaController);

        mLoadingContainer = (RelativeLayout) findViewById(R.id.player_loading_container);
        mLoading = (AVLoadingIndicatorView) findViewById(R.id.player_loading);

        loadVideo();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mVideoView.stopPlayback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mVideoView != null) {
            mVideoView = null;
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

        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.start();
        showLoading(false);
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
}
