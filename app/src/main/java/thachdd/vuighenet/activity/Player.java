package thachdd.vuighenet.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.MediaController;
import android.widget.VideoView;

import thachdd.vuighenet.R;

public class Player extends AppCompatActivity {
    private final String sampleUrl = "https://redirector.googlevideo.com/videoplayback?id=0287cccb7dda22a0&itag=59&source=webdrive&requiressl=yes&ttl=transient&mm=30&mn=sn-i3b7kn7d&ms=nxu&mv=u&nh=IgpwZjAxLmhrZzA4KhgyMDAxOjQ4NjA6MToxOjA6NDdlMzowOjk&pl=39&mime=video/mp4&lmt=1484798868122171&mt=1486017973&ip=2405:4800:201:dc5e:d6ae:52ff:fe6c:d59d&ipbits=0&expire=1486032444&sparams=ip%2Cipbits%2Cexpire%2Cid%2Citag%2Csource%2Crequiressl%2Cttl%2Cmm%2Cmn%2Cms%2Cmv%2Cnh%2Cpl%2Cmime%2Clmt&signature=2EAF45E3AE3D4F37674E7984ED78722E3D37A356.9AC88DDBF24C2A47412887431323FF06572ED3B1&key=ck2&app=explorer&title=[VuiGhe.Net] Vua Hai Tac - Tap 35-(480p)";
    private final int INTERNET_PERMISSION_CODE = 1111;

    private boolean mHavePermission = false;

    private VideoView mVideoView = null;
    private MediaController mVideoCtrl = null;
    private Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mVideoView = (VideoView) findViewById(R.id.myVideo);
        mVideoCtrl = new MediaController(this);

        mVideoCtrl.setAnchorView(mVideoView);
        mVideoView.setMediaController(mVideoCtrl);

        checkPermission();
    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        else {
            mHavePermission = true;
            loadVideo();
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        INTERNET_PERMISSION_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void loadVideo() {
        Uri uri = Uri.parse(sampleUrl);
        mVideoView.setVideoURI(uri);
        mVideoView.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case INTERNET_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    mHavePermission = true;
                    loadVideo();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                break;
            }

            default:
                break;
        }
    }
}
