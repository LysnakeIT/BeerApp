package alexis.lilian.esaip.beerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

/**
 * Key activity which allows the user to login with a GitHub account or anonymously.
 */
public class AuthenticationActivity extends AppCompatActivity {
    
    private FirebaseAuth authentication; // Firebase authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication); // Set the layout to authentication.xml

        ImageButton githubButton = findViewById(R.id.button_auth_github); // Get the GitHub button
        githubButton.setOnClickListener(v -> loginWithGithub()); // Set the click listener to login with GitHub account

        TextView anonymousButton = findViewById(R.id.button_auth_anonymous); // Get the anonymous button (no account)
        anonymousButton.setOnClickListener(v ->  loginWithoutAccount()); // Set the click listener to login anonymously
    }

    /**
     * Method to login with a GitHub account using Firebase authentication.
     */
    private void loginWithGithub() {
        authentication = FirebaseAuth.getInstance(); // Get the Firebase authentication instance
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com"); // Create a new OAuthProvider for GitHub authentication

        authentication.startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener(result -> loginSuccess(result, 0)) // If the authentication is successful, go to the beer list
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show(); // Else, display a toast to inform the user
                });
    }

    /**
     * Method to login anonymously using Firebase authentication.
     */
    private void loginWithoutAccount() {
        authentication = FirebaseAuth.getInstance(); // Get the Firebase authentication instance
        authentication.signInAnonymously()
                .addOnSuccessListener(result -> loginSuccess(result, 1)) // If the authentication is successful, go to the beer list
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show(); // Else, display a toast to inform the user
                });
    }

    /**
     * Method to go to the beer list activity.
     * It also displays a popup to inform the user that the authentication was successful.
     * The popup is different depending on the choice of authentication.
     * @param result The authentication result
     * @param choice The choice of authentication (0 = GitHub, 1 = Anonymous)
     */
    private void loginSuccess(AuthResult result, int choice) {
        Popup popup = new Popup(this); // Create a new popup
        switch (choice) {
            case 0:
                popup.setTitle("GitHub Session Started");
                popup.setDescription("Hello " + result.getAdditionalUserInfo().getUsername() + ", you're now successfully logged in with GitHub.");
                popup.setImageResource(R.drawable.success_icon);
                break;
            case 1:
                popup.setTitle("Guest Session Started");
                popup.setDescription("You are logged in anonymously. Note that some features may be unavailable until you sign in with a full account.");
                popup.setImageResource(R.drawable.advertisement_icon);
                break;
            default:
                break;
        }
        // Set the click listener to go to the beer list activity when the user clicks on the popup button
        popup.getNextButton().setOnClickListener(v -> {
            popup.closePopup();
            this.goToBeerList(result);
        });
        popup.showPopup();
    }

    /**
     * Method to go to the beer list activity.
     * Also passes the username, icon and status to the activity.
     * @param result The authentication result
     */
    private void goToBeerList(AuthResult result) {
        if (result.getUser() != null) {
            Intent intent = new Intent(this, BeerListActivity.class); // Create a new intent to go to the beer list activity
            if (result.getUser().isAnonymous()) {
                intent.putExtra("username", "Anonymous");
                intent.putExtra("icon_user", "https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png");
                intent.putExtra("status", true); // If the user is anonymous, set the status to true
            } else {
                intent.putExtra("username", result.getAdditionalUserInfo().getUsername()); // Pass the username to the activity
                intent.putExtra("icon_user", result.getAdditionalUserInfo().getProfile().get("avatar_url").toString()); // Pass the icon to the activity
                intent.putExtra("status", false); // If the user is not anonymous, set the status to false
            }
            startActivity(intent); // Start the activity beer list
            finish(); // Finish the current activity
        }
    }
}