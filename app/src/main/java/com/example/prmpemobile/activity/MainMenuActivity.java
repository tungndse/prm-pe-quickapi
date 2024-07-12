package com.example.prmpemobile.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prmpemobile.R;
import com.example.prmpemobile.fragment.ItemManagerFragment;
import com.example.prmpemobile.model.AccountGetDto;
import com.google.gson.Gson;

public class MainMenuActivity extends AppCompatActivity {

    private TextView textViewWelcome;
    private AccountGetDto account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        textViewWelcome = findViewById(R.id.textViewWelcomeMessage);

        // Retrieve AccountGetDto from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("account", null);
        if (accountJson != null) {
            Gson gson = new Gson();
            account = gson.fromJson(accountJson, AccountGetDto.class); // Deserialize the JSON to AccountGetDto
        }

        // Display welcome message with the user's name
        if (account != null) {
            textViewWelcome.setText("Welcome, " + account.getName());
        } else {
            textViewWelcome.setText("Welcome, User");
        }

        Button buttonItemManager = findViewById(R.id.buttonItemManager);
        buttonItemManager.setOnClickListener(v -> openItemManagerPage());
    }

    // Method to open the ItemManagerFragment
    public void openItemManagerPage() {
        // Create a new instance of ItemManagerFragment
        ItemManagerFragment itemManagerFragment = new ItemManagerFragment();

        // Begin a FragmentTransaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with ItemManagerFragment
        fragmentTransaction.replace(R.id.fragment_container, itemManagerFragment);
        fragmentTransaction.addToBackStack(null); // Optional: to allow navigation back
        fragmentTransaction.commit();
    }
}
