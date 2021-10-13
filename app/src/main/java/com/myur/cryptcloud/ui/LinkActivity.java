package com.myur.cryptcloud.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myur.cryptcloud.R;
import com.myur.cryptcloud.adapter.AESUtils;

public class LinkActivity extends AppCompatActivity {

    EditText edLink;
    Button enc, dec;
    TextView Tv;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        edLink=findViewById(R.id.linkString);
        enc=findViewById(R.id.btnenc);
        dec=findViewById(R.id.btndec);
        Tv=findViewById(R.id.textView);
        Back=findViewById(R.id.back);


        enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String encrypted = "";
                String sourceStr = edLink.getText().toString();
                try {
                    encrypted = AESUtils.encrypt(sourceStr);
                    Log.d("TEST", "encrypted:" + encrypted);
                    Tv.setText(encrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encrypted = edLink.getText().toString();
                String decrypted = "";
                try {
                    decrypted = AESUtils.decrypt(encrypted);
                    Log.d("TEST", "decrypted:" + decrypted);
                    Tv.setText(decrypted);
                    Toast.makeText(getApplicationContext(),decrypted,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}