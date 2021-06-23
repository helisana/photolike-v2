package com.ddm.photolike2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ddm.photolike2.model.FeedPost;
import com.ddm.photolike2.view.FeedPostView;
import com.ddm.photolike2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    static private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<FeedPost> localDataset;

    public FeedAdapter(ArrayList<FeedPost> localDataset) {
        this.localDataset = localDataset;
    }

    // Create new views (invoked by the layout manager)
    // Carrega o layout dos itens
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        // Create a new view, which defines the UI of the list item

        FeedPostView feedPostView = new FeedPostView(parent.getContext());

        feedPostView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        return new ViewHolder(feedPostView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindData(localDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataset.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FeedPostView feedPostView;

        public ViewHolder(View view) {
            super(view);

            feedPostView = (FeedPostView) view;
        }

        public void bindData(final FeedPost feedPost) {
            //feedPostView.setFotoPerfil(feedPost.getFotoPerfil());
            feedPostView.setNomeUsuario(feedPost.getNomeUsuario());
            feedPostView.setImagem(feedPost.getImagem());
            feedPostView.setLegenda(feedPost.getLegenda());

            /*
            feedPostView.getBtnDeletar().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.collection("posts").document(feedPost.getDocRef())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("FeedAdapter", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("FeedAdapter", "Error deleting document", e);
                            }
                        });
                }
            });
            */
        }
    }
}
