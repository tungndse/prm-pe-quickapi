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
import com.example.prmpemobile.adapter.AuthorAdapter;
import com.example.prmpemobile.api.AuthorService;
import com.example.prmpemobile.model.Author;
import com.example.prmpemobile.model.ResponseObject;
import com.example.prmpemobile.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorManagerFragment extends Fragment {

    private RecyclerView recyclerViewAuthors;
    private AuthorAdapter authorAdapter;
    private List<Author> authorList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_manager, container, false);

        recyclerViewAuthors = view.findViewById(R.id.recyclerViewAuthors);

        // Set up RecyclerView with AuthorAdapter
        recyclerViewAuthors.setLayoutManager(new LinearLayoutManager(getContext()));
        authorAdapter = new AuthorAdapter(authorList, this);
        recyclerViewAuthors.setAdapter(authorAdapter);

        // Load authors from server or other sources
        loadAuthors();

        // Set up buttons
        view.findViewById(R.id.buttonBackToMainMenu).setOnClickListener(v -> goToMainMenu());

        return view;
    }

    private void loadAuthors() {
        AuthorService authorService = RetrofitClient.getClient().create(AuthorService.class);
        Call<ResponseObject<List<Author>>> call = authorService.getAuthors();

        call.enqueue(new Callback<ResponseObject<List<Author>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Author>>> call, Response<ResponseObject<List<Author>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Author> authors = response.body().getData();
                    if (authors != null) {
                        authorList.clear();
                        authorList.addAll(authors);
                        authorAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No authors found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load authors", Toast.LENGTH_SHORT).show();
                    Log.e("AuthorManagerFragment", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<Author>>> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AuthorManagerFragment", "Request failed", t);
            }
        });
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
