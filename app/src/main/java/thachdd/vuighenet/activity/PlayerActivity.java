package thachdd.vuighenet.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import thachdd.vuighenet.R;

public class PlayerActivity extends AppCompatActivity {
    private final String sampleUrl = "https://redirector.googlevideo.com/videoplayback?id=2ec476660f13d369&itag=59&source=webdrive&requiressl=yes&ttl=transient&mm=30&mn=sn-i3b7kne7&ms=nxu&mv=u&nh=IgpwZjAxLmhrZzA4KhgyMDAxOjQ4NjA6MToxOjA6NDdlMzowOjk&pl=39&mime=video/mp4&lmt=1484800963645929&mt=1486101354&ip=2405:4800:201:dc5e:862b:2bff:fe72:3aab&ipbits=0&expire=1486115957&sparams=ip%2Cipbits%2Cexpire%2Cid%2Citag%2Csource%2Crequiressl%2Cttl%2Cmm%2Cmn%2Cms%2Cmv%2Cnh%2Cpl%2Cmime%2Clmt&signature=904DCF18F033FDE01C29CFDFA18AF069836CD1E4.92F0A8A11C74A53A520B3AFD59F6772D0A4609B7&key=ck2&app=explorer&title=[VuiGhe.Net] Vua Hai Tac - Tap 48-(480p)";

    private VideoView mVideoView = null;
    private MediaController mVideoCtrl = null;
    private View mToolbar = null;

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

        loadVideo();
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

    private void loadVideo() {
        Uri uri = Uri.parse(sampleUrl);
        mVideoView.setVideoURI(uri);
        mVideoView.start();
    }
}
