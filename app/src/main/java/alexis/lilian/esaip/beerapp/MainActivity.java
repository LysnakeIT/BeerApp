package alexis.lilian.esaip.beerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // Set the layout to home.xml

        ImageButton nextButton = findViewById(R.id.button_home); // Get the next button of the layout
        nextButton.setOnClickListener(v -> goToAuthentication()); // Set the click listener to go to the authentication page
    }

    /**
     * Method to go to the authentication page.
     */
    private void goToAuthentication() {
        Intent intent = new Intent(this, AuthenticationActivity.class); // Create an intent to go to the authentication page
        startActivity(intent);
        finish(); // End the current activity
    }
}