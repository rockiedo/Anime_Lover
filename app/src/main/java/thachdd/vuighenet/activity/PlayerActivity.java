package thachdd.vuighenet.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.wang.avi.AVLoadingIndicatorView;

import thachdd.vuighenet.R;
import thachdd.vuighenet.api_client.ApiClient;
import thachdd.vuighenet.api_client.ApiInterface;
import thachdd.vuighenet.api_client.PlayerCallback;

public class PlayerActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
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

//        showLoading(true);
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

    public void playerOnClick(View v) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    public void loadVideo() {
        int id = getIntent().getIntExtra("id", 0);
        mPlayerCallback = new PlayerCallback(this);
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        api.getPlayer(id).enqueue(mPlayerCallback);
    }

    public void onPlayerLoadedSuccessfully(String url) {
//        url = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
//        url = "https://redirector.googlevideo.com/videoplayback?id=7732952c93ac3251&itag=59&source=webdrive&requiressl=yes&ttl=transient&mm=30&mn=sn-npoe7n7y&ms=nxu&mv=m&pl=20&mime=video/mp4&lmt=1477820339908422&mt=1486565919&ip=139.59.229.244&ipbits=0&expire=1486580339&sparams=ip%2Cipbits%2Cexpire%2Cid%2Citag%2Csource%2Crequiressl%2Cttl%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cmime%2Clmt&signature=8F971E60538F2DAFF97A346568AEF066976547EA.7D002483A045F10A0D2B75F2923D170E36E759B3&key=ck2&app=explorer&title=[VuiGhe.Net] Vua Hai Tac - Tap 0-(480p)";
        url = "https://redirector.googlevideo.com/videoplayback?id=0efeb7dd135b53ae&itag=18&source=webdrive&requiressl=yes&ttl=transient&mm=30&mn=sn-npoe7n7z&ms=nxu&mv=m&pl=20&mime=video/mp4&lmt=1486278231172797&mt=1486561593&ip=139.59.229.244&ipbits=0&expire=1486576051&sparams=ip%2Cipbits%2Cexpire%2Cid%2Citag%2Csource%2Crequiressl%2Cttl%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cmime%2Clmt&signature=2EFE3C152966DBBFEE3E72543F377C4FEBBE016E.A560656B100E7F6337AE2BEA7BF7D87230282BA1&key=ck2&app=explorer&title=[VuiGhe.Net]";

        if (url == null) {
            Toast.makeText(this, "Khong the tai video", Toast.LENGTH_LONG).show();
            showLoading(false);
            finish();

            return;
        }

        Uri uri = Uri.parse(url);
        mVideoView.setVideoURI(uri);
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

    @Override
    public void onPrepared(MediaPlayer mp) {
        showLoading(false);
//        mMediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        mMediaPlayer = new MediaPlayer();
//        mMediaPlayer.setDisplay(mSurfaceHolder);
//        mMediaPlayer.setOnPreparedListener(this);
//        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//        loadVideo();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
