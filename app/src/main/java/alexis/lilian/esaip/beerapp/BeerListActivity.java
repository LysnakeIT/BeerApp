package alexis.lilian.esaip.beerapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/* Key activity which retrieves beer data from a Firebase database and displays it in a RecyclerView.
It also permit navigation to other features via buttons (create and keywords),
(some features are disabled in guest mode -> only the select view).
 */
public class BeerListActivity extends AppCompatActivity {

    private DatabaseReference database; // Declaring a reference to the Firebase database.

    private List<Beer> beers = new ArrayList<>(); // Declaration of a list of beers which will be used to store the data retrieved from the database.

    private BeerAdapter adapter; // Declaration of the adapter which will be used to display the list of beers in the RecyclerView.

    private Button button_create_beer; // Declaration of a button to access functionality related to beer creation.

    private Button button_keywords; // Declaration of a button to access functionality related to keywords.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_list); // Set the layout to beer_list.xml

        String userName = getIntent().getStringExtra("username"); // Get the username of the user connected
        String userPhotoUrl = getIntent().getStringExtra("icon_user"); // Get the photo url of the user connected

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(userName); // Set the username in the toolbar

        Picasso.get().load(userPhotoUrl).into((ImageView) findViewById(R.id.toolbar_profile_image)); // Set the photo url in the toolbar

        this.database = DatabaseManager.getFirebaseDatabase().getReference("beers"); // Get the Firebase database reference

        RecyclerView recyclerView = findViewById(R.id.recyclerViewBeers); // Get the RecyclerView of the layout
        // Set the layout manager of the RecyclerView to a GridLayoutManager with 2 columns in portrait mode and 4 columns in landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        this.adapter = new BeerAdapter(this.beers); // Create a new BeerAdapter with the list of beers
        recyclerView.setAdapter(this.adapter); // Set the adapter of the RecyclerView to the BeerAdapter

        this.goToBeerCreate();
        this.goToKeywords();

        boolean isAnonymous = getIntent().getBooleanExtra("status", false); // Get the status of the user connected
        // If the user is anonymous, disable the buttons to access the functionalities related to beer creation and keywords and display a toast to inform the user
        if (isAnonymous) {
            this.button_create_beer.setEnabled(false);
            this.button_create_beer.setAlpha(0.5f);
            this.button_keywords.setEnabled(false);
            this.button_keywords.setAlpha(0.5f);
            Toast.makeText(this, "Some features are disabled in guest mode.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method onResume to retrieve beer data from the database and display it in the RecyclerView.
     */
    public void onResume() {
        super.onResume();
        this.beers.clear();
        this.getBeers();
        this.adapter.update();
    }

    /**
     * Method to retrieve beer data from the database and display it in the RecyclerView.
     */
    private void getBeers() {
        this.database.addValueEventListener(new ValueEventListener() {
            /**
             * Method to retrieve beer data from the database and display it in the RecyclerView.
             * @param dataSnapshot The snapshot of the data retrieved from the database
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop for to go through each snapshot of beer data in the database.
                for (DataSnapshot beerSnapshot : dataSnapshot.getChildren()) {
                    Beer beer = beerSnapshot.getValue(Beer.class); // Get the beer object from the database
                    beer.setKey(beerSnapshot.getKey()); // Set the key of the beer object to the key of the snapshot
                    beers.add(beer); // Add the beer object to the list of beers
                }
                adapter.update(); // Update the adapter to display the list of beers in the RecyclerView
            }

            /**
             * Method to display a toast to inform the user if an error occurred.
             * @param databaseError The error which occurred during the retrieval of data from the database
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BeerListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This method permit to navigate to the Create Beer activity to add a new beer in the list on the button click
     */
    private void goToBeerCreate() {
        this.button_create_beer = findViewById(R.id.button_beer_list_create); // Get the create button of the form
        this.button_create_beer.setOnClickListener(v -> {
            Intent intent = new Intent(this, BeerCreateActivity.class); // Create a new intent to go to the beer create activity
            startActivity(intent);
        });
    }

    /**
     * This method permit to navigate to the Keywords activity on the button click
     */
    private void goToKeywords() {
        this.button_keywords = findViewById(R.id.button_beer_list_openai); // Get the keywords button of the layout
        this.button_keywords.setOnClickListener(v -> {
            Intent intent = new Intent(this, KeywordsActivity.class); // Create a new intent to go to the keywords activity
            startActivity(intent);
        });
    }
}