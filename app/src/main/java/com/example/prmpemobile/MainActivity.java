package com.example.prmpemobile;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prmpemobile.activity.LoginActivity;
import com.example.prmpemobile.activity.MainMenuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if user is logged in (replace with your authentication logic)
        if (!isLoggedIn()) {
            // User is not logged in, navigate to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();  // Finish MainActivity so it's not in the back stack
        } else {
            // User is logged in, navigate to MainMenuActivity
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
            finish();  // Finish MainActivity so it's not in the back stack
        }
    }

    // Replace this with your own authentication logic
    private boolean isLoggedIn() {
        // Example logic: check if user session exists or token is valid
        // Return true if authenticated, false otherwise
        // Replace with your actual authentication logic
        return false;  // Placeholder
    }
}

