package com.myur.cryptcloud.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.myur.cryptcloud.R;

import static com.myur.cryptcloud.util.Constants.fetchUserdata;
import static com.myur.cryptcloud.util.Constants.fromLogin;
import static com.myur.cryptcloud.util.Constants.restoreUserData;

public class MainActivity extends AppCompatActivity {
    TextView uName, uEmail;
    ImageView uImage;
    ImageView file;

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
                Intent intent = new Intent(getApplicationContext(), CloudFileActivity.class);
                startActivity(intent);
            }
        });

        sideNavOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
       /* FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                Toast.makeText(getApplicationContext(),name+email+photoUrl,Toast.LENGTH_LONG).show();*/

        View header = navigationView.getHeaderView(0);
        uName = header.findViewById(R.id.user_name);
        uEmail = header.findViewById(R.id.user_phone_number);
        ImageView editProfile = header.findViewById(R.id.edit_profile);
        uImage = header.findViewById(R.id.imageProfile);
              /*  uName.setText(name);
                uEmail.setText(email);
                uImage.setImageURI(photoUrl);*/
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (fromLogin) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loading_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            fetchUserdata(FirebaseAuth.getInstance(), MainActivity.this, dialog, uName, uEmail, uImage);
        } else {
            String[] data = restoreUserData(MainActivity.this);
            Log.e("FROM: ", data[0]);
            uName.setText(data[0]);
            uEmail.setText(data[1]);
            uImage.setImageResource(Integer.parseInt(data[4]));
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // get id of clicked item
                int id = item.getItemId();

                // set action to selected item
                if (id == R.id.menuSetting) {
                    Toast.makeText(MainActivity.this, "Open Setting Activity", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.menuHelp) {
                    Toast.makeText(MainActivity.this, "Open HElp & Feedback Activity", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void darkModeChecker() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        } else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fromLogin) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loading_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            fetchUserdata(FirebaseAuth.getInstance(), MainActivity.this, dialog, uName, uEmail, uImage);
        } else {
            String[] data = restoreUserData(MainActivity.this);
            Log.e("FROM: ", data[0]);
            uName.setText(data[0]);
            uEmail.setText(data[1]);
            uImage.setImageResource(Integer.parseInt(data[4]));
        }

    }
}