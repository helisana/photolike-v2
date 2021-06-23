package com.ddm.photolike2.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.ddm.photolike2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.Map;

public class NovoPostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private FirebaseAnalytics mFirebaseAnalytics;

    private ImageView imagem;
    private EditText legenda;

    private Uri uriImagemSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_post);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        imagem = findViewById(R.id.novo_post_imagem);
        legenda = findViewById(R.id.novo_post_legnda);

        Bundle bundle = new Bundle();
        bundle.putString("screen", "novo_post");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            uriImagemSelecionada = data.getData();
            imagem.setImageURI(uriImagemSelecionada);
        }
    }

    public void uploadImage(String docRef) {
        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child("posts/" + docRef);
        UploadTask uploadTask = riversRef.putFile(uriImagemSelecionada);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("NovoPostActivity", "Erro ao fazer o upload da imagem.");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("NovoPostActivity", "Sucesso no upload da imagem!");

                finish();
            }
        });
    }

    public void handleImagemClick(View view) {
        openGallery();
    }

    public void handleBtnVoltarClick(View view) {
        finish();
    }

    public void handleBtnPublicarClick(View view) {
        Map<String, Object> post = new HashMap<>();
        post.put("userId", user.getUid());
        post.put("legenda", legenda.getText().toString());

        db.collection("posts")
            .add(post)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("NovoPostActivity", "DocumentSnapshot added with ID: " + documentReference.getId());

                    String docRef = documentReference.getId();
                    uploadImage(docRef);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, docRef);
                    mFirebaseAnalytics.logEvent("imagem_publicada", bundle);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("NovoPostActivity", "Error adding document", e);
                }
            });
    }
}