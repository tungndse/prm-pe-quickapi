package com.example.prmpemobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmpemobile.R;
import com.example.prmpemobile.activity.MainMenuActivity;
import com.example.prmpemobile.adapter.BookAdapter;
import com.example.prmpemobile.api.BookService;
import com.example.prmpemobile.model.Book;
import com.example.prmpemobile.model.ResponseObject;
import com.example.prmpemobile.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookManagerFragment extends Fragment {

    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;
    private List<Book> bookList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_manager, container, false);

        recyclerViewBooks = view.findViewById(R.id.recyclerViewBooks);

        // Set up RecyclerView with BookAdapter
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        bookAdapter = new BookAdapter(bookList, this);
        recyclerViewBooks.setAdapter(bookAdapter);

        // Load books from server or other sources
        loadBooks();

        // Set up buttons
        view.findViewById(R.id.buttonAddBook).setOnClickListener(v -> goToBookCreation());
        view.findViewById(R.id.buttonOtherAction).setOnClickListener(v -> goToMainMenu());

        return view;
    }

    private void loadBooks() {
        BookService bookService = RetrofitClient.getClient().create(BookService.class);
        Call<ResponseObject<List<Book>>> call = bookService.getBooks();

        call.enqueue(new Callback<ResponseObject<List<Book>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Book>>> call, Response<ResponseObject<List<Book>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body().getData();
                    if (books != null) {
                        bookList.clear();
                        bookList.addAll(books);
                        bookAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No books found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load books", Toast.LENGTH_SHORT).show();
                    Log.e("BookManagerFragment", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<Book>>> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("BookManagerFragment", "Request failed", t);
            }
        });
    }

    private void goToBookCreation() {
        // Navigate to BookCreationFragment

    }

    private void goToMainMenu() {
        // Navigate back to MainMenuActivity
        Intent intent = new Intent(getActivity(), MainMenuActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
