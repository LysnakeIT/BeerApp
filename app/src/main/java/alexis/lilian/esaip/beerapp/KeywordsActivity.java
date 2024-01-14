package alexis.lilian.esaip.beerapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Key activity which allows the user to extract keywords from a text input using the OpenAI API.
 */
public class KeywordsActivity extends AppCompatActivity {

    private EditText input_keywords_text; // Declaration of the text input field of the form to extract keywords

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keywords); // Set the layout to keywords.xml

        this.input_keywords_text = findViewById(R.id.hint_text_zone_extract); // Get the text input field of the form
        ImageButton button_keywords_extract = findViewById(R.id.button_extract); // Get the extract button of the form

        button_keywords_extract.setOnClickListener(v -> extractKeywords()); // Set the click listener to extract keywords
    }

    /**
     * Method to extract keywords from a text input using the OpenAI API.
     * The extractKeywords method is called when the button is clicked.
     * It creates a JSONObject called requestData containing parameters for the OpenAI request.
     * A JSON array is created with two JSON objects. The first, with the "system" role, indicates an instruction for the user.
     * The second, with the "user" role, uses the contents of the input_keywords_text object as the user's input.
     * JSON objects are added to the request, which is then sent via the sendVolleyRequest method.
     */
    private void extractKeywords() {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("model", "gpt-3.5-turbo");
            requestData.put("temperature", 0.5);
            requestData.put("max_tokens", 64);
            requestData.put("top_p", 1);

            JSONArray message = new JSONArray();
            message.put(new JSONObject().put("role", "system").put("content", "You will be provided with a block of text, and your task is to extract a list of keywords from it."));
            message.put(new JSONObject().put("role", "user").put("content", this.input_keywords_text.getText().toString()));

            requestData.put("messages", message); // The messages array is added to the request data

            sendVolleyRequest(requestData); // The request is sent via the sendVolleyRequest method
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to send a POST request to the OpenAI API with the necessary JSON data and headers (API key).
     * Responses are processed by the showResponse methods on success and showError on error.
     * @param requestData The JSON data to send
     */
    private void sendVolleyRequest(JSONObject requestData) {
        String API_URL = BuildConfig.OPENAI_API_URL; // The API URL is retrieved from the build.gradle file
        String API_KEY = BuildConfig.OPENAI_API_KEY; // The API key is retrieved from the build.gradle file

        RequestQueue queue = Volley.newRequestQueue(this); // A new request queue is created

        // A new POST request is created with the API URL, the JSON data, and the headers (API key)
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_URL, requestData, response -> this.showResponse(response), error -> this.showError(error)) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>(); // A new HashMap is created to store the headers
                headers.put("Authorization", "Bearer " + API_KEY); // The API key is added to the headers
                headers.put("Content-Type", "application/json"); // The content type is added to the headers
                return headers;
            }
        };
        queue.add(request);
    }

    /**
     * Method to display the extracted keywords in a popup window.
     * @param response The JSON response from the OpenAI API containing the extracted keywords
     */
    private void showResponse(JSONObject response) {
        Popup popup = new Popup(this); // A new popup is created
        popup.setTitle("Keywords extracted");
        popup.setImageResource(R.drawable.success_icon);
        try {
            // The keywords are extracted from the JSON response and formatted
            String keywords = response.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
            String formattedKeywords = formatExtractKeywords(keywords);
            popup.setDescription(formattedKeywords); // The formatted keywords are added to the popup
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // The popup is displayed and closed when the user clicks on the button
        popup.getNextButton().setOnClickListener(v -> {
            popup.closePopup();
        });
        popup.showPopup();
    }

    /**
     * Method to format the extracted keywords with a "•" prefix and a line break.
     * @param keywords The extracted keywords to format
     * @return The formatted keywords
     */
    private String formatExtractKeywords(String keywords) {
        keywords = keywords.replace("Keywords: ", ""); // The "keywords: " text is removed from the keywords
        String[] keywordsArray = keywords.split(",\\s*"); // The keywords are split into an array
        StringBuilder formattedText = new StringBuilder(); // A new StringBuilder is created to store the formatted text
        // Each keyword is added to the StringBuilder with a "•" prefix and a line break
        for (String keyword : keywordsArray) {
            formattedText.append("• ").append(keyword).append("\n");
        }
        return formattedText.toString();
    }

    /**
     * Method to display a toast to inform the user if an error occurred.
     * @param error VolleyError containing the error message
     */
    private void showError(VolleyError error) {
        throw new RuntimeException(error);
    }
}