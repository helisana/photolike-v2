package com.ddm.photolike2.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ddm.photolike2.R;
import com.ddm.photolike2.adapter.FeedAdapter;
import com.ddm.photolike2.model.FeedPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private FirebaseAnalytics mFirebaseAnalytics;

    private RecyclerView feed;
    private FeedAdapter feedAdapter;

    ArrayList<FeedPost> feedPosts = new ArrayList<FeedPost>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        feed = findViewById(R.id.main_feed);

        /*
        FeedPost fp = new FeedPost();
        fp.setNomeUsuario("carlosedba");
        fp.setLegenda("Teste de legendaaaaa");
        feedPosts.add(fp);
        */

        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

        feedPosts.clear();
        fetchPosts();

        Bundle bundle = new Bundle();
        bundle.putString("screen", "feed");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    public void fetchPosts() {
        db.collection("posts")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getString("userId").equals(user.getUid())) {

                                FeedPost feedPost = new FeedPost();
                                feedPost.setDocRef(document.getId());
                                feedPost.setNomeUsuario(user.getDisplayName());
                                feedPost.setLegenda(document.getString("legenda"));
                                feedPosts.add(feedPost);

                                Log.d("MainActivity", document.getId() + " => " + document.getData());
                            }
                        }

                        fetchImages();
                    } else {
                        Log.w("MainActivity", "Error getting documents.", task.getException());
                    }
                }
            });
    }

    public void fetchImages() {
        for (FeedPost feedPost : feedPosts) {
            StorageReference storageRef = storage.getReference();

            try {
                File localFile = File.createTempFile(feedPost.getDocRef(), "jpg");

                storageRef.child("posts/" + feedPost.getDocRef()).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d("MainActivity", "Sucesso ao resgatar imagem!");
                        feedPost.setImagem(localFile);
                        feedAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("MainActivity", "Erro ao resgatar imagem.");
                    }
                });
            } catch (IOException e) {
                Log.d("MainActivity", "Erro ao resgatar imagem.");
            }

        }
    }

    public void initAdapter() {
        feedAdapter = new FeedAdapter(feedPosts);
        feed.setLayoutManager(new LinearLayoutManager(this));
        feed.setAdapter(feedAdapter);
    }

    public void handleBtnNovoPostClick(View view) {
        Intent i = new Intent(this, NovoPostActivity.class);
        startActivity(i);
    }
}