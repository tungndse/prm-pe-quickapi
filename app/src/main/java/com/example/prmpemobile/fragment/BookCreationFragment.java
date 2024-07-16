package com.example.prmpemobile.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prmpemobile.R;
import com.example.prmpemobile.api.BookService;
import com.example.prmpemobile.model.BookPostDto;
import com.example.prmpemobile.model.ResponseObject;
import com.example.prmpemobile.network.RetrofitClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookCreationFragment extends Fragment {

    private EditText editTextBookName, editTextBookAuthorId, editTextBookDatePublished;
    private Spinner spinnerBookGenre;
    private Button buttonSubmit, buttonCancel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_creation, container, false);

        editTextBookName = view.findViewById(R.id.editTextBookName);
        editTextBookAuthorId = view.findViewById(R.id.editTextBookAuthorId);
        editTextBookDatePublished = view.findViewById(R.id.editTextBookDatePublished);
        spinnerBookGenre = view.findViewById(R.id.spinnerBookGenre);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        // Set up the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.book_genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBookGenre.setAdapter(adapter);

        buttonSubmit.setOnClickListener(v -> submitBook());
        buttonCancel.setOnClickListener(v -> getFragmentManager().popBackStack());

        return view;
    }

    private void submitBook() {
        String name = editTextBookName.getText().toString().trim();
        String authorIdStr = editTextBookAuthorId.getText().toString().trim();
        String datePublishedStr = editTextBookDatePublished.getText().toString().trim();
        String genre = spinnerBookGenre.getSelectedItem().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(authorIdStr) || TextUtils.isEmpty(genre) || TextUtils.isEmpty(datePublishedStr)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Long authorId;
        try {
            authorId = Long.parseLong(authorIdStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid Author ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Date datePublished;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            datePublished = inputFormat.parse(datePublishedStr);
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Format the date to the expected format
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String formattedDate = outputFormat.format(datePublished);

        BookPostDto bookPostDto = new BookPostDto(name, formattedDate, genre, authorId);
        BookService bookService = RetrofitClient.getClient().create(BookService.class);
        Call<ResponseObject<BookPostDto>> call = bookService.createBook(bookPostDto);

        call.enqueue(new Callback<ResponseObject<BookPostDto>>() {
            @Override
            public void onResponse(Call<ResponseObject<BookPostDto>> call, Response<ResponseObject<BookPostDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Book created successfully", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Failed to create book", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<BookPostDto>> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
