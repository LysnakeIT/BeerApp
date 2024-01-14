package alexis.lilian.esaip.beerapp;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Class that manages the Firebase database instance of the application.
 */
public class DatabaseManager extends Application {

    // DATABASE_URL is a constant that represents the Firebase Realtime database URL. This is the place where the application data is stored (beers infos).
    private static final String DATABASE_URL = "https://beerapp-6d6c9-default-rtdb.europe-west1.firebasedatabase.app";

    private static FirebaseDatabase firebaseDatabase; // FirebaseDatabase is a class that represents the Firebase database. It is used to get the database instance.

    /**
     * Method to initialize the Firebase database instance and to set the data persistence.
     * This method is called when the application is created.
     * It ensures that there is only one instance of the database in the entire application.
     * It also provides data persistence, which allows the application to store a copy of the database locally for offline use.
     * Finally, it ensures that the "beers" reference is always up to date locally.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance(DATABASE_URL); // Get the Firebase database instance
            firebaseDatabase.setPersistenceEnabled(true); // Set the data persistence to true (local copy of the database)
            firebaseDatabase.getReference("beers").keepSynced(true); // Set the "beers" reference to always be up to date locally
        }
    }

    /**
     * Method to get the Firebase database instance of the application and to create it if it does not exist.
     * @return the Firebase database instance.
     */
    public static FirebaseDatabase getFirebaseDatabase() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance(DATABASE_URL); // Get the Firebase database instance
        }
        return firebaseDatabase; // Return the Firebase database instance
    }
}