package alexis.lilian.esaip.beerapp;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * Key activity which allows the user to create a beer item.
 */
public class BeerCreateActivity extends AppCompatActivity {

    private DatabaseReference database; // Declaring a reference to the Firebase database.

    private EditText name, description, image_url, taste, country, degree, price; // Declaration of the different fields of the form

    private String flag_url; // Declaration of the flag url of the form
    
    private RadioGroup type; // Declaration of the radio group for the type of beer of the form

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_beer_item); // Set the layout to create_beer_item.xml

        this.database = DatabaseManager.getFirebaseDatabase().getReference("beers"); // Get the Firebase database reference

        ImageButton button = findViewById(R.id.button_create_beer); // Get the create button of the form
        this.name = findViewById(R.id.name_input); // Get the name field of the form
        this.description = findViewById(R.id.description_input); // Get the description field of the form
        this.image_url = findViewById(R.id.editTextImageUrl); // Get the image url field of the form
        this.taste = findViewById(R.id.taste_input); // Get the taste field of the form
        this.type = findViewById(R.id.type_input); // Get the type field of the form
        this.country = findViewById(R.id.origin_input); // Get the country field of the form
        this.degree = findViewById(R.id.alcohol_degree_input); // Get the alcohol degree field of the form
        this.price = findViewById(R.id.price_input); // Get the price field of the form

        // Add a listener to the country field to load the flag of the country entered by the user in the form
        this.country.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                loadCountryFlag(s.toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        button.setOnClickListener(v -> this.createBeer()); // Set the click listener to create a beer item
    }

    /**
     * Method to create a beer item and add it to the database.
     */
    private void createBeer() {
        int selectedTypeId = this.type.getCheckedRadioButtonId(); // Get the selected type of beer
        RadioButton radioButtonType = findViewById(selectedTypeId);

        // Create a new beer item with the data entered by the user in the form
        Beer beer = new Beer(this.name.getText().toString().trim(), this.description.getText().toString().trim(), this.image_url.getText().toString().trim(), this.taste.getText().toString().trim(), radioButtonType.getText().toString().trim(), this.country.getText().toString().trim(), Double.parseDouble(this.degree.getText().toString().trim()), Double.parseDouble(this.price.getText().toString().trim()));
        this.addToDatabase(beer); // Add the beer item to the database
    }

    /**
     * Method to add a beer item to the database.
     * @param beer The beer item to add to the database
     */
    private void addToDatabase(Beer beer) {
        this.database.push().setValue(beer); // Add the beer item to the database
        Toast.makeText(this, "Beer added successfully!", Toast.LENGTH_SHORT).show(); // Display a toast to inform the user that the beer item was added successfully
        this.goToBeerList(); // Go to the beer list activity
    }

    /**
     * Method to go to the beer list activity.
     */
    private void goToBeerList() {
        finish(); // Finish the current activity
    }

    /**
     * Method to load the flag of a country from the name of the country.
     * @param countryName The name of the country
     */
    private void loadCountryFlag(String countryName) {
        String url = "https://restcountries.com/v2/name/" + countryName + "?fields=flags,alpha2Code"; // Create the API url to get the flag of the country
        RequestQueue queue = Volley.newRequestQueue(this); // Create a new request queue to send the request to the API
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.length() > 0) {
                            JSONObject country = response.getJSONObject(0); // Get the first country of the response (the API returns an array of countries)
                            this.flag_url = country.getJSONObject("flags").getString("png"); // Get the flag url of the country from the response
                            Picasso.get().load(this.flag_url).into((ImageView) findViewById(R.id.origin_flag)); // Load the flag of the country in the form
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );
        queue.add(request);
    }
}