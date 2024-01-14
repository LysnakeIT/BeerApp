package alexis.lilian.esaip.beerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class for the dialog box
 */
public class Popup {

    private final AlertDialog popup; // AlertDialog instance

    private final TextView title; // Declaration of the title variable

    private final TextView description; // Declaration of the description variable

    private final ImageView image; // Declaration of the image variable
    
    private final ImageButton nextButton; // Declaration of the nextButton variable

    public Popup(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context); // Get the inflater of the context
        View layout = inflater.inflate(R.layout.popup, null); // Inflate the view of the AlertDialog with the popup.xml layout

        this.title = layout.findViewById(R.id.title_popup); // Get the title field of the layout
        this.description = layout.findViewById(R.id.description_popup); // Get the description field of the layout
        this.image = layout.findViewById(R.id.image_popup_status); // Get the image field of the layout
        this.nextButton = layout.findViewById(R.id.button_popup); // Get the next button of the layout

        this.popup = new AlertDialog.Builder(context).setView(layout).create(); // Create an AlertDialog instance
        this.popup.setCanceledOnTouchOutside(false); // Set the dialog box to not be closed when the user clicks outside the dialog box
        this.popup.getWindow().setBackgroundDrawableResource(R.drawable.background_gradient_popup); // Set the background color of the AlertDialog
    }

    /**
     * Setter methods to set the title
     * @param titleText the title of the dialog box
     */
    public void setTitle(String titleText) {
        this.title.setText(titleText);
    }

    /**
     * Setter methods to set the description
     * @param descriptionText the description of the dialog box
     */
    public void setDescription(String descriptionText) {
        this.description.setText(descriptionText);
    }

    /**
     * Setter methods to set the image
     * @param imageResId the image of the dialog box
     */
    public void setImageResource(int imageResId) {
        this.image.setImageResource(imageResId);
    }

    /**
     * Getter methods to get the next button
     * @return the next button of the dialog box
     */
    public ImageButton getNextButton() {
        return this.nextButton;
    }

    /**
     * Method to show the dialog box
     */
    public void showPopup() {
        this.popup.show();
    }

    /**
     * Method to close the dialog box
     */
    public void closePopup() {
        if (popup != null && popup.isShowing()) {
            popup.dismiss();
        }
    }
}