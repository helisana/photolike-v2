package com.ddm.photolike2.model;

import android.net.Uri;

import java.io.File;

public class FeedPost {
    private String docRef;
    private Uri fotoPerfil;
    private String nomeUsuario;
    private File imagem;
    private String legenda;

    public FeedPost() {}

    public FeedPost(String docRef, Uri fotoPerfil, String nomeUsuario, File imagem, String legenda) {
        this.docRef = docRef;
        this.fotoPerfil = fotoPerfil;
        this.nomeUsuario = nomeUsuario;
        this.imagem = imagem;
        this.legenda = legenda;
    }

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

    public Uri getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Uri fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public File getImagem() {
        return imagem;
    }

    public void setImagem(File imagem) {
        this.imagem = imagem;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }
}
