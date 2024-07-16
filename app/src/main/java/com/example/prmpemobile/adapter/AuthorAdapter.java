package com.example.prmpemobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmpemobile.R;
import com.example.prmpemobile.model.Author;
import com.example.prmpemobile.model.AuthorGetDto;

import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    private final List<Author> authors;
    private final Fragment fragment;

    public AuthorAdapter(List<Author> authors, Fragment fragment) {
        this.authors = authors;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_author, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Author author = authors.get(position);
        holder.textViewAuthorName.setText(author.getName());
        holder.textViewAuthorAddress.setText(author.getAddress());
        holder.textViewAuthorEmail.setText(author.getEmail());
        holder.textViewAuthorPhone.setText(author.getPhone());
        holder.itemView.setOnClickListener(v -> {
            // Handle item click
            Toast.makeText(fragment.getContext(), "Clicked on: " + author.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    static class AuthorViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewAuthorName;
        private final TextView textViewAuthorAddress;
        private final TextView textViewAuthorEmail;
        private final TextView textViewAuthorPhone;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthorName = itemView.findViewById(R.id.textViewAuthorName);
            textViewAuthorAddress = itemView.findViewById(R.id.textViewAuthorAddress);
            textViewAuthorEmail = itemView.findViewById(R.id.textViewAuthorEmail);
            textViewAuthorPhone = itemView.findViewById(R.id.textViewAuthorPhone);
        }
    }
}
