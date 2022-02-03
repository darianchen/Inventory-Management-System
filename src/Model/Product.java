package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model of Product Class.
 * @author Darian Chen
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product parametrized constructor
     * @param id Product ID
     * @param name Product Name
     * @param price Product Price
     * @param stock Product Stock
     * @param min Minimum Inventory
     * @param max Maximum Inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets Id.
     * @param id The Id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets price.
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets stock.
     * @param stock The stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets minimum.
     * @param min The minimum to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets maximum.
     * @param max The maximum to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets Id.
     * @return Returns the Id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     * @return Returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets price.
     * @return Returns the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets stock.
     * @return Returns the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets minimum.
     * @return Returns the minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets maximum.
     * @return Returns the maximum
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to the associated parts list of a product.
     * @param part The part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes associated Part from a product.
     * @param selectedAssociatedPart The associated part to delete.
     * @return Returns boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
      return false;
    } //This method was not used in the program.

    /**
     * Gets a list of all associated parts of a product.
     * @return Returns associated list of parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
