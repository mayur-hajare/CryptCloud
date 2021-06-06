package com.myur.cryptcloud.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
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

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UploadModel model = dataSnapshot.getValue(UploadModel.class);
                    uploadModels.add(model);
                }
                if(uploadModels.size() != 0){
                    animationView.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}