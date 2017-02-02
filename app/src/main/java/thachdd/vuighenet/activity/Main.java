package thachdd.vuighenet.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import thachdd.vuighenet.R;

public class Main extends AppCompatActivity {

    private Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
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
}
