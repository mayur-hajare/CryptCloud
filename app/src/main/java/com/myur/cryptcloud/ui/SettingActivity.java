package com.myur.cryptcloud.ui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.myur.cryptcloud.R;

public class SettingActivity extends AppCompatActivity {

    private ImageView backBtn;
    private View about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Action Bar Back button
        about = findViewById(R.id.about);
        backBtn = findViewById(R.id.backSetting);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Action Bar Back button

        SwitchCompat darkModeSwitch = (SwitchCompat) findViewById(R.id.dark_mode_switch);
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "myPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        if (isDarkModeOn) {
            darkModeSwitch.setChecked(true);
        } else {
            darkModeSwitch.setChecked(false);
        }

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // if dark mode is off
                    // it will turn it on
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_YES);

                    // it will set isDarkModeOn
                    // boolean to true
                    editor.putBoolean(
                            "isDarkModeOn", true);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Dark Mode On ", Toast.LENGTH_SHORT).show();
                } else {
                    // if dark mode is on
                    // will turn it off
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_NO);
                    // it will set isDarkModeOn
                    // boolean to false
                    editor.putBoolean(
                            "isDarkModeOn", false);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Dark Mode Off", Toast.LENGTH_SHORT).show();

                }
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("Crypt Cloud")
                        .setMessage("Encrypt File Free can encrypt and protect photos, videos, audios, pictures, doc, ppt, xls, pdf and other files using a password.\n" +
                                "\n" +
                                "This app can encrypt and lock all file types such as private photos and videos, confidential office documents (Word, Excel, PowerPoint, etc) and any other files for they can not be opened or viewed by others. The encrypted file can only be opened with the correct password.\n" +
                                "\n" +
                                "You must enter a password to encrypt / decrypt each file.\n" +
                                "\n" +
                                "Encrypted and Decrypted files available in CryptCloud in file manager or on available on Cloud Storage if you upload it.\n" +

                                "\n" +
                                "Encrypt your files and not just hide them. This solution is better and safer than simply hiding files.\n" +
                                "\n")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                finish();

                            }
                        }).show();
            }
        });
    }
}