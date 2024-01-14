package alexis.lilian.esaip.beerapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

/**
 * ViewHolder for the RecyclerView which displays the list of beers.
 */
public class BeerViewHolder extends RecyclerView.ViewHolder {

    /**
     * Constructor of the ViewHolder.
     * @param itemView The view of the ViewHolder
     */
    public BeerViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * Method to update the ViewHolder with a beer item.
     * @param beer The beer item to display in the ViewHolder
     */
    public void update(Beer beer) {
        TextView name = itemView.findViewById(R.id.name_beer_item); // Get the name field of the ViewHolder
        ImageView image = itemView.findViewById(R.id.image_beer_item); // Get the image field of the ViewHolder
        TextView origin = itemView.findViewById(R.id.origin_beer_item); // Get the origin field of the ViewHolder
        TextView price = itemView.findViewById(R.id.price_beer_item); // Get the price field of the ViewHolder

        name.setText(beer.getName()); // Set the name of the beer in the name field of the beer item
        if (beer.getImageUrl() != null && !beer.getImageUrl().isEmpty()) {
            Picasso.get().load(beer.getImageUrl()).into(image); // Set the image of the beer in the image field of the beer item
        }
        origin.setText(String.valueOf(beer.getOrigin())); // Set the origin of the beer in the origin field of the beer item
        price.setText(String.format("%s $/L", String.valueOf(beer.getPrice()))); // Set the price of the beer in the price field of the beer item
    }
}