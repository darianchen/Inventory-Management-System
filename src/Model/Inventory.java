package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Model of Inventory Class
 * @author Darian Chen
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a part to the inventory.
     * @param newPart New Part input
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a product to the inventory.
     * @param newProduct New Product input
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up part by Id.
     * @param partId Part Id
     * @return Returns a part object if found, otherwise returns null
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Looks up product by Id.
     * @param productId The product Id
     * @return Returns a product object if found, otherwise returns null
     */
    public static Product lookupProduct(int productId) {
        for(Product product: allProducts){
            if(product.getId() == productId){
                return product;
            }
        }
    return null;
    }

    /**
     * Looks up a part by name.
     * @param partName The part name
     * @return Returns a list of parts containing the input name
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partList = FXCollections.observableArrayList();
        for(Part part : allParts){
            if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                partList.add(part);
            }
        }
    return partList;
    }

    /**
     * Searches the inventory for products that contain the input string, case ignored.
     * @param productName The Product name
     * @return Returns a list of products containing the input name
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        for(Product product : allProducts){
            if(product.getName().toLowerCase().contains(productName.toLowerCase())){
                productList.add(product);
            }
        }
        return productList;
    }

    /**
     * Updates a part in the inventory.
     * @param index The index
     * @param selectedPart The selected part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index,selectedPart);
    }

    /**
     * Updates a product in the inventory.
     * @param index The index
     * @param newProduct The new product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index,newProduct);
    }

    /**
     * Deletes a part from the inventory.
     * @param selectedPart The part to be removed
     * @return Returns a boolean showing the result of the removal
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a product from the inventory list.
     * @param selectedProduct The selected product
     * @return Returns a boolean showing the result
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets a list of all parts in the inventory.
     * @return Returns a list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets a list of all products in the inventory.
     * @return Returns a list of part objects.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}