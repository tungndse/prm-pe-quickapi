package com.example.prmpemobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmpemobile.R;
import com.example.prmpemobile.fragment.BookManagerFragment;
import com.example.prmpemobile.model.Book;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final List<Book> books;
    private final BookManagerFragment bookManagerFragment;

    public BookAdapter(List<Book> books, BookManagerFragment bookManagerFragment) {
        this.books = books;
        this.bookManagerFragment = bookManagerFragment;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book);
        holder.itemView.setOnClickListener(v -> {
            // Handle item click, e.g., show a toast or navigate to details
            Toast.makeText(bookManagerFragment.getContext(), "Clicked on: " + book.getName(), Toast.LENGTH_SHORT).show();
            // Implement other actions such as editing or deleting if required
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewBookName;
        private final TextView textViewDatePublished;
        private final TextView textViewAuthorName;
        private final TextView textViewGenre;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBookName = itemView.findViewById(R.id.textViewBookName);
            textViewDatePublished = itemView.findViewById(R.id.textViewDatePublished);
            textViewAuthorName = itemView.findViewById(R.id.textViewAuthorName);
            textViewGenre = itemView.findViewById(R.id.textViewGenre);
        }

        public void bind(Book book) {
            textViewBookName.setText(book.getName());

            if (book.getDatePublished() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                textViewDatePublished.setText(dateFormat.format(book.getDatePublished()));
            } else {
                textViewDatePublished.setText("N/A");
            }

            textViewAuthorName.setText(book.getAuthorName() != null ? book.getAuthorName() : "Unknown Author");
            textViewGenre.setText(book.getGenre() != null ? book.getGenre() : "Unknown Genre");
        }
    }
}
