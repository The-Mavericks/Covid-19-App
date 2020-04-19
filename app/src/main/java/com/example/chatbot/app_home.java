package com.example.chatbot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class app_home extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2500;
    Context mContext;


    private CardView chatbot1;
    private CardView tracker;
    private  CardView news;
    private  CardView hotspot;
    private CardView updates;
    private CardView impact;
    // private Toolbar toolbar;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_home);

        chatbot1 = findViewById(R.id.chatbot1);
        tracker = findViewById(R.id.tracker);
        news = findViewById(R.id.news);
        updates = findViewById(R.id.updates);
        hotspot = findViewById(R.id.hotspot);
        impact = findViewById(R.id.impact);


        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("COVID CARE");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );
        Log.i("hn", "hn");
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                int id = item.getItemId();

                if (id == R.id.about)
                {
                    i=new Intent(app_home.this,about.class);
                    startActivity(i);
                }
                else if (id == R.id.feedback)
                {
                    i=new Intent(app_home.this,feedback.class);
                    startActivity(i);
                }
                return true;
            }
        });


        //navigationView.setNavigationItemSelectedListener(this);
        chatbot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatbot1.getContext().startActivity(new Intent(chatbot1.getContext(), chatbot_home.class));

            }
        });
        tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tracker.getContext().startActivity(new Intent(tracker.getContext(), tracker_home.class));

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news.getContext().startActivity(new Intent(news.getContext(), news.class));
            }
        });
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updates.getContext().startActivity(new Intent(updates.getContext(), who.class));
            }
        });
        hotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotspot.getContext().startActivity(new Intent(hotspot.getContext(), hotspot_home.class));
            }
        });
        impact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impact.getContext().startActivity(new Intent(impact.getContext(), other.class));
            }
        });
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item)
//    {
//
//    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
