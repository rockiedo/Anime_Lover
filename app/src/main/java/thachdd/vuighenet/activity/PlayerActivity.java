package thachdd.vuighenet.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
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

        Log.d("mylog", "load episode: " + id);

        mPlayerCallback = new PlayerCallback(this);
        HerokuInterface api = ApiClient.getHeroku().create(HerokuInterface.class);
        api.getPlayerDetail(id).enqueue(mPlayerCallback);
    }

    public void onPlayerLoadedSuccessfully(String url) {
//        url = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
//        url = "https://redirector.googlevideo.com/videoplayback?id=7732952c93ac3251&itag=59&source=webdrive&requiressl=yes&ttl=transient&mm=30&mn=sn-npoe7n7y&ms=nxu&mv=m&pl=20&mime=video/mp4&lmt=1477820339908422&mt=1486565919&ip=139.59.229.244&ipbits=0&expire=1486580339&sparams=ip%2Cipbits%2Cexpire%2Cid%2Citag%2Csource%2Crequiressl%2Cttl%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cmime%2Clmt&signature=8F971E60538F2DAFF97A346568AEF066976547EA.7D002483A045F10A0D2B75F2923D170E36E759B3&key=ck2&app=explorer&title=[VuiGhe.Net] Vua Hai Tac - Tap 0-(480p)";
//        url = "https://redirector.googlevideo.com/videoplayback?id=c4056a6e82eda499&itag=18&source=webdrive&requiressl=yes&ttl=transient&mm=30&mn=sn-i3b7kn7k&ms=nxu&mv=u&pl=20&mime=video/mp4&lmt=1484973049472625&mt=1486903971&ip=125.212.241.142&ipbits=0&expire=1486918431&ste=m1&sparams=ip%2Cipbits%2Cexpire%2Cid%2Citag%2Csource%2Crequiressl%2Cttl%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cmime%2Clmt&signature=03EF96054CE11D111ADD4BD2D9C18612C792539F.2A5B74780663C872517AA3CFCEA886A64E2AF01F&key=ck2&app=explorer&sck=U2FsdGVkX1%2BpM%2BkBjzSalf%2BbDN0xIVolE89gJ6H%2Bsqo%3D";

        Log.d("mylog", "onPlayerLoadedSuccessfully(): " + url);

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
        Log.d("mylog", "onPlayerLoadedFailed()");

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
