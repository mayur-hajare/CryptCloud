package com.myur.cryptcloud.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.myur.cryptcloud.R;

public class MainActivity extends AppCompatActivity {
    TextView uName, uEmail;
    ImageView uImage;
    ImageButton file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        NavigationView navigationView = findViewById(R.id.navigationView);
        // to remove default black tint of navigation drawer icons
        navigationView.setItemIconTintList(null);

        // Navigation Drawer
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        ImageView sideNavOpener = findViewById(R.id.imageMenu);
         file = findViewById(R.id.fileView);

         file.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getApplicationContext(),CloudFileActivity.class);
                 startActivity(intent);
             }
         });

        sideNavOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        View header = navigationView.getHeaderView(0);
        uName = header.findViewById(R.id.user_name);
        uEmail = header.findViewById(R.id.user_phone_number);
        ImageView editProfile = header.findViewById(R.id.edit_profile);
        uImage = header.findViewById(R.id.imageProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}