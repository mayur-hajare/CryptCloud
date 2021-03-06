package com.myur.cryptcloud.ui;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.myur.cryptcloud.R;
import com.myur.cryptcloud.adapter.UploadFilesAdapter;
import com.myur.cryptcloud.model.UploadModel;

import java.util.ArrayList;

public class CloudFileActivity extends AppCompatActivity {

    public static UploadFilesAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<UploadModel> uploadModels;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getUid()).child("USER_FILES");
    public static LottieAnimationView animationView;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_file);


        animationView = findViewById(R.id.animation_view);
        recyclerView = findViewById(R.id.encrypted_files_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploadModels = new ArrayList<>();
        adapter = new UploadFilesAdapter(uploadModels, this);
        recyclerView.setAdapter(adapter);
        Back=findViewById(R.id.back);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UploadModel model = dataSnapshot.getValue(UploadModel.class);
                    uploadModels.add(model);
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CloudFileActivity.this, R.style.BottomSheetDialogTheme);

                }
                if (uploadModels.size() != 0) {
                    animationView.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });


    }
}