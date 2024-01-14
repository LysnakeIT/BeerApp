package alexis.lilian.esaip.beerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for the RecyclerView which displays the list of beers.
 */
public class BeerAdapter extends RecyclerView.Adapter<BeerViewHolder> {

    private List<Beer> beerList; // List of beers to display in the RecyclerView

    /**
     * Constructor of the adapter.
     * @param beerList List of beers to display in the RecyclerView
     */
    public BeerAdapter(List<Beer> beerList) {
        this.beerList = beerList;
    }

    /**
     * Method to create a new BeerViewHolder.
     * @param parent The parent of the BeerViewHolder to create (the RecyclerView)
     * @param viewType int
     * @return BeerViewHolder created
     */
    @Override
    public BeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext(); // Get the context of the parent
        LayoutInflater inflater = LayoutInflater.from(context); // Get the inflater of the context
        View view = inflater.inflate(R.layout.beer_item, parent, false); // Inflate the view of the BeerViewHolder with the beer_item.xml layout

        return new BeerViewHolder(view); // Return the BeerViewHolder created
    }

    /**
     * Method to bind a BeerViewHolder to a position in the RecyclerView.
     * @param holder The BeerViewHolder to bind
     * @param position The position in the RecyclerView
     */
    @Override
    public void onBindViewHolder(BeerViewHolder holder, int position) {
        Beer beer = this.beerList.get(position); // Get the beer at the position in the list
        // Set the click listener to go to the beer details activity
        holder.itemView.setOnClickListener(v -> {
            Intent goToDetails = new Intent(v.getContext(), BeerDetailsActivity.class); // Create an intent to go to the beer details activity
            goToDetails.putExtra("beerId", beer.getKey()); // Put the id of the beer in the intent
            v.getContext().startActivity(goToDetails);
        });
        holder.update(this.beerList.get(position)); // Update the BeerViewHolder with the beer at the position in the list
    }

   /**
     * Method to get the number of beers in the list.
     * @return The number of beers in the list
     */
    @Override
    public int getItemCount() {
        return this.beerList.size();
    }

    /**
     * Method to update the adapter.
     */
    public void update() {
        this.notifyDataSetChanged();
    }
}