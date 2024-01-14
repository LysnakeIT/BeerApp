package alexis.lilian.esaip.beerapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Key activity which displays the details of a beer item selected by the user.
 */
public class BeerDetailsActivity extends AppCompatActivity {

    private DatabaseReference database; // Declaring a reference to the Firebase database.

    private TextView name, description, taste, type, degree, price; // Declaration of the different fields of the beer selected
    
    private ImageView image; // Declaration of the image of the beer selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_details); // Set the layout to beer_details.xml

        String beerId = getIntent().getStringExtra("beerId"); // Get the id of the beer selected by the user
        this.database = DatabaseManager.getFirebaseDatabase().getReference("beers").child(beerId); // Get the Firebase database reference of the beer selected

        // Add a listener to the database reference to retrieve the data of the beer selected
        this.database.addValueEventListener(new ValueEventListener() {
            /**
             * Method to retrieve beer data from the database and display it in the layout.
             * @param dataSnapshot The snapshot of the data retrieved from the database
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Beer beer = dataSnapshot.getValue(Beer.class); // Get the beer object from the database
                if (beer != null) {
                    name = findViewById(R.id.name_beer);
                    name.setText(beer.getName()); // Set the name of the beer selected in the name field of the layout
                    description = findViewById(R.id.description_beer);
                    description.setText(beer.getDescription()); // Set the description of the beer selected in the description field of the layout
                    image = findViewById(R.id.image_beer);
                    if (beer.getImageUrl() != null && !beer.getImageUrl().isEmpty()) {
                        Picasso.get().load(beer.getImageUrl()).into(image); // Set the image of the beer selected in the image field of the layout
                    }
                    taste = findViewById(R.id.taste_beer);
                    taste.setText(beer.getTaste()); // Set the taste of the beer selected in the taste field of the layout
                    type = findViewById(R.id.type_beer);
                    type.setText(beer.getType()); // Set the type of the beer selected in the type field of the layout
                    degree = findViewById(R.id.degree_beer);
                    degree.setText(String.format("%s °", String.valueOf(beer.getAlcoholDegree()))); // Set the alcohol degree of the beer selected in the alcohol degree field of the layout
                    price = findViewById(R.id.price_beer);
                    price.setText(String.format("%s $/L", String.valueOf(beer.getPrice()))); // Set the price of the beer selected in the price field of the layout
                }
            }

            /**
             * Method to display a toast if an error occurred.
             * @param databaseError The error which occurred when retrieving data from the database
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BeerDetailsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show(); // Display a toast to inform the user if an error occurred
            }
        });
    }
}
