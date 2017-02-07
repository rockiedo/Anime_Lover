package thachdd.vuighenet.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

import thachdd.vuighenet.R;
import thachdd.vuighenet.api_client.ApiClient;
import thachdd.vuighenet.api_client.ApiInterface;
import thachdd.vuighenet.api_client.PlayerCallback;
import thachdd.vuighenet.model.PlayerResponse;

public class PlayerActivity extends AppCompatActivity {
    private VideoView mVideoView = null;
    private MediaController mVideoCtrl = null;
    private View mToolbar = null;

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

        mVideoView = (VideoView) findViewById(R.id.player_video);
        mVideoCtrl = new MediaController(this);

        mVideoCtrl.setAnchorView(mVideoView);
        mVideoView.setMediaController(mVideoCtrl);

        mToolbar = findViewById(R.id.player_toolbar);

        mLoadingContainer = (RelativeLayout) findViewById(R.id.player_loading_container);
        mLoading = (AVLoadingIndicatorView) findViewById(R.id.player_loading);

        showLoading(true);
        int id = getIntent().getIntExtra("playerId", 0);
        loadVideo(id);
    }

    public void playerOnClick(View v) {
        switch (v.getId()) {
            case R.id.player_btn_back: {
                finish();
                break;
            }

            case R.id.player_btn_download: {
                Toast.makeText(this, "This functionality is coming soon!", Toast.LENGTH_SHORT).show();
                break;
            }

            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mToolbar.getVisibility() == View.VISIBLE) {
                mToolbar.setVisibility(View.GONE);
            }
            else {
                mToolbar.setVisibility(View.VISIBLE);
            }
        }

        return super.onTouchEvent(event);
    }

    public void loadVideo(int id) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        mPlayerCallback = new PlayerCallback(this);
        api.getPlayer(id).enqueue(mPlayerCallback);
    }

    public void onPlayerLoadedSuccessfully(PlayerResponse response) {
        String url = response.getSources().getData().get(1).getLink();
        Uri uri = Uri.parse(url);

        mVideoView.setVideoURI(uri, fakeHeader());

        showLoading(false);

        mVideoView.start();
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

    public Map<String, String> fakeHeader() {
        Map<String, String> header = new HashMap<>();

        header.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:50.0) Gecko/20100101 Firefox/50.0");
        header.put("Host", "r5---sn-i3b7kn7z.googlevideo.com");
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        header.put("Accept-Language", "en-US,en;q=0.5");
        header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("Connection", "keep-alive");
        header.put("Upgrade-Insecure-Request", "1");

        return header;
    }
}
