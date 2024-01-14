package alexis.lilian.esaip.beerapp;

/**
 * Class for the beer object
 */
public class Beer {

    private String name; // Declaration of the name variable of the beer

    private String description; // Declaration of the description variable of the beer

    private String imageUrl; // Declaration of the image variable of the beer

    private String taste; // Declaration of the taste variable of the beer

    private String type; // Declaration of the type variable of the beer

    private String origin; // Declaration of the origin variable of the beer

    private double alcoholDegree; // Declaration of the alcohol degree variable of the beer

    private double price; // Declaration of the price variable of the beer
    
    private String key; // Declaration of the key to identify the beer in the database

    /**
     * Constructor of the beer object
     */
    public Beer() {}

    /**
     * Constructor of the beer object with parameters to set the different fields of the beer
     * @param name the name of the beer
     * @param description the description of the beer
     * @param imageUrl the image url of the beer
     * @param taste the taste of the beer
     * @param type the type of the beer
     * @param origin the origin of the beer
     * @param alcoholDegree the alcohol degree of the beer
     * @param price the price of the beer
     */
    public Beer(String name, String description, String imageUrl, String taste, String type, String origin, double alcoholDegree, double price) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.taste = taste;
        this.type = type;
        this.origin = origin;
        this.alcoholDegree = alcoholDegree;
        this.price = price;
    }

    /**
     * Getter method to get the key which identifies the beer in the database
     * @return the key of the beer in the database
     */
    public String getKey() {
        return key;
    }

    /**
     * Setter method to set the key which identifies the beer in the database
     * @param key the key of the beer in the database
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Getter method to get the name
     * @return the name of the beer
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method to get the description
     * @return the description of the beer
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter method to get the image url
     * @return the image url of the beer
     */
    public String getImageUrl() {
        return this.imageUrl;
    }

    /**
     * Getter method to get the taste
     * @return the taste of the beer
     */
    public String getTaste() {
        return taste;
    }

    /**
     * Getter method to get the type
     * @return the type of the beer
     */
    public String getType() {
        return this.type;
    }

    /**
     * Getter method to get the origin
     * @return the origin of the beer
     */
    public String getOrigin() {
        return this.origin;
    }

    /**
     * Getter method to get the alcohol degree
     * @return the alcohol degree of the beer
     */
    public double getAlcoholDegree() {
        return this.alcoholDegree;
    }

    /**
     * Getter method to get the price
     * @return the price of the beer
     */
    public double getPrice() {
        return this.price;
    }
}