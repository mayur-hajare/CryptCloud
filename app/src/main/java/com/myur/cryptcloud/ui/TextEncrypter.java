package com.myur.cryptcloud.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.myur.cryptcloud.R;
import com.myur.cryptcloud.adapter.AESUtils;

public class TextEncrypter extends AppCompatActivity {

    EditText value;
    Button enc, dec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_encrypter);

        value.findViewById(R.id.textValue);
        enc.findViewById(R.id.encrypt);
        dec.findViewById(R.id.decrypt);

        enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String encrypted = "";
                String sourceStr = value.getText().toString();
                try {
                    encrypted = AESUtils.encrypt(sourceStr);
                    Log.d("TEST", "encrypted:" + encrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encrypted = value.getText().toString();
                String decrypted = "";
                try {
                    decrypted = AESUtils.decrypt(encrypted);
                    Log.d("TEST", "decrypted:" + decrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}