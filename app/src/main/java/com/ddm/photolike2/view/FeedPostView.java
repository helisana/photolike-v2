package com.ddm.photolike2.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ddm.photolike2.R;

import java.io.File;

public class FeedPostView extends FrameLayout {
    ImageView fotoPerfil;
    TextView nomeUsuario;
    ImageView imagem;
    TextView legenda;
    ImageButton btnDeletar;

    public FeedPostView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public FeedPostView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FeedPostView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public FeedPostView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attributeSet) {
        inflate(context, R.layout.feed_post_view, this);

        //fotoPerfil = findViewById(R.id.feed_post_foto_perfil);
        nomeUsuario = findViewById(R.id.feed_post_nome_usuario);
        imagem = findViewById(R.id.feed_post_imagem);
        legenda = findViewById(R.id.feed_post_legenda);
        btnDeletar = findViewById(R.id.feed_post_btn_deletar);
    }

    /*public void setFotoPerfil(Uri uri) {
        fotoPerfil.setImageURI(uri);
    }*/

    public void setNomeUsuario(String str) {
        nomeUsuario.setText(str);
    }

    public void setImagem(File file) {
        if (file != null && file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imagem.setImageBitmap(bitmap);
        }
    }

    public void setLegenda(String str) {
        legenda.setText(str);
    }

    public ImageButton getBtnDeletar() {
        return btnDeletar;
    }
}
