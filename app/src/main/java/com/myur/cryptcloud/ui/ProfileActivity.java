package com.myur.cryptcloud.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myur.cryptcloud.R;
import com.myur.cryptcloud.model.UserModel;

import static com.myur.cryptcloud.util.Constants.restoreUserData;
import static com.myur.cryptcloud.util.Constants.saveUserData;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profile1, profile2, profile3, profile4, profile5, profile6, profile7, profile8, selectedProfile, backButton, logoutButton;
    private EditText profileName;
    private TextView profilePhone, profileEmail;
    private Button saveButton, forgetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // initialize components
        profile1 = findViewById(R.id.profile_1);
        profile2 = findViewById(R.id.profile_2);
        profile3 = findViewById(R.id.profile_3);
        profile4 = findViewById(R.id.profile_4);
        profile5 = findViewById(R.id.profile_5);
        profile6 = findViewById(R.id.profile_6);
        profile7 = findViewById(R.id.profile_7);
        profile8 = findViewById(R.id.profile_8);
        selectedProfile = findViewById(R.id.profile_image);
        profileName = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_Mail);
        profilePhone = findViewById(R.id.profile_Phone);
        saveButton = findViewById(R.id.save_btn);
        backButton = findViewById(R.id.imageBack);
        forgetButton = findViewById(R.id.forget_btn);
        logoutButton = findViewById(R.id.imageLogout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences pref = ProfileActivity.this.getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isUserLogin", false);
                editor.putBoolean("isDarkModeOn", false);
                editor.apply();
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().clear().apply();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfile.setImageResource(R.drawable.img0);
                selectedProfile.setTag(R.drawable.img0);
            }
        });

        profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfile.setImageResource(R.drawable.img1);
                selectedProfile.setTag(R.drawable.img1);
            }
        });

        profile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfile.setImageResource(R.drawable.img8);
                selectedProfile.setTag(R.drawable.img8);
            }
        });

        profile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfile.setImageResource(R.drawable.img9);
                selectedProfile.setTag(R.drawable.img9);
            }
        });

        profile5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfile.setImageResource(R.drawable.img9);
                selectedProfile.setTag(R.drawable.img9);
            }
        });

        profile6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfile.setImageResource(R.drawable.img6);
                selectedProfile.setTag(R.drawable.img6);
            }
        });

        profile7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfile.setImageResource(R.drawable.img7);
                selectedProfile.setTag(R.drawable.img7);
            }
        });

        profile8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProfile.setImageResource(R.drawable.img3);
                selectedProfile.setTag(R.drawable.img3);
            }
        });

        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        String[] userData = restoreUserData(ProfileActivity.this);
        Toast.makeText(getApplicationContext(), userData[1] + userData[2] + userData[3], Toast.LENGTH_LONG).show();
        String Email = userData[1];
        profileEmail.setText(Email);
        profilePhone.setText(userData[2]);
        profileName.setText(userData[0]);
        selectedProfile.setImageResource(Integer.parseInt(userData[4]));
        selectedProfile.setTag(Integer.parseInt(userData[4]));

        Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager) ProfileActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (profileName.length() != 0) {
                    if (isConnected) {
                        dialog.show();
                        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid());
                        UserModel upload = new UserModel(profileName.getText().toString(), userData[1], userData[2], userData[3], (int) selectedProfile.getTag());
                        /*String key = root.push().getKey();*/
                        root.child("USER_DATA").child(userData[3]).setValue(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                saveUserData(ProfileActivity.this, profileName.getText().toString(), userData[1], userData[2], userData[3], (int) selectedProfile.getTag());
                                dialog.dismiss();
                                //finish();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(ProfileActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        backButton.callOnClick();
        super.onBackPressed();
    }
}