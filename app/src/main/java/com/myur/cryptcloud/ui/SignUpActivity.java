package com.myur.cryptcloud.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.myur.cryptcloud.model.UserModel;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myur.cryptcloud.R;

public class SignUpActivity extends AppCompatActivity {


    EditText setName;
    EditText setPassword;
    EditText reEnterPassword;
    EditText setPhone;
    Button btnSignUp;
    TextView txt_login_page;
    TextView headerSignUp;
    CheckBox isUserAgreed;
    EditText setEmail;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private ProgressDialog loadingBar;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make activity full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        // ini values

        setName = findViewById(R.id.set_name);
        setPassword = findViewById(R.id.set_password);
        setPhone = findViewById(R.id.set_phone);
        btnSignUp = findViewById(R.id.btn_sign_up);
        txt_login_page = findViewById(R.id.txt_login_page);
        headerSignUp = findViewById(R.id.head_sign_up_page);
        loadingBar = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        isUserAgreed = findViewById(R.id.agreeMent);
        setEmail = findViewById(R.id.set_email);


        txt_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount();

            }
        });
    }

    private void createAccount() {

        String name = setName.getText().toString();
        String password = setPassword.getText().toString();
        String phoneNumber = setPhone.getText().toString();
        String email = setEmail.getText().toString();
        boolean agreedDetails = isUserAgreed.isChecked();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            if (password.length() < 8) {
                Toast.makeText(this, "Password length should be 8 characters", Toast.LENGTH_SHORT).show();
            }
        } else if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            if (phoneNumber.length() != 10) {
                Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
            }
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            if (setEmail.getText().toString().matches(emailPattern)) {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            }
        } else if (!(agreedDetails)) {
            Toast.makeText(this, "Please mark the box", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait while we are checking your details");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatePhoneNumber(name, phoneNumber, email, password);
        }

    }

    private void validatePhoneNumber(final String name, final String phoneNumber, final String email, final String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference root = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getUid());
                            String key = root.push().getKey();
                            UserModel upload = new UserModel(name, email, phoneNumber, key, R.drawable.image_1);
                            root.child("USER_DATA").child(key).setValue(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(SignUpActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                }
                            });
                            loadingBar.dismiss();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(SignUpActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                        loadingBar.dismiss();
                    }
                });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}