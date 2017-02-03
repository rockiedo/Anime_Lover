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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import thachdd.vuighenet.R;

public class Main extends AppCompatActivity {
    private final int INTERNET_PERMISSION_CODE = 1111;

    private Toolbar mToolbar = null;
    private boolean mHavePermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        checkPermission();
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, Player.class);
        startActivity(intent);
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

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        else {
            mHavePermission = true;
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

                } else {
                    Toast.makeText(this, "Cannot access the internet", Toast.LENGTH_LONG).show();
                    finish();
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
