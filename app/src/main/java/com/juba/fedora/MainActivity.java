package com.juba.fedora;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private ViewPager viewPager;
    private TabLayout tablayout;
    private FirebaseAuth mAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = findViewById(R.id.appbarlayout);
        setSupportActionBar(myToolbar);
        viewPager = findViewById(R.id.view_pager);
        FragmentsAdapter adapter = new FragmentsAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tablayout = findViewById(R.id.tab_layout);
        tablayout.setupWithViewPager(viewPager);
        currentUserId = mAuth.getCurrentUser().getUid();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUserId == null) {
            moveToLoginactivity();

        }

        if (currentUserId != null) {
            moveToSettingsactivity();
        }


    }

    private void moveToLoginactivity() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);

    }


    private void moveToSettingsactivity() {

        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.log_out) {
            mAuth.signOut();
            moveToLoginactivity();
        }

        if (item.getItemId() == R.id.find_friends)
        {

        }

        if (item.getItemId() == R.id.settings) {
            moveToSettingsactivity();
        }

        return true;

    }

}
