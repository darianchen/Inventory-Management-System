package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

/**
 * Controller of MainForm Class
 * @author Darian Chen
 */
public class MainFormController {

    @FXML
    private TextField partFilterField;

    @FXML
    private TextField productFilterField;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableView<Product> productTableView;

    /**
     * This method displays the add part form.
     * It displays an error if no part is selected.
     * @param actionEvent Add part form button event
     * @throws IOException FXMLLoader IOException
     */
    @FXML
    public void onActionAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddPartForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The initialize method sets up part and product tables.
     * It also creates prompt text for the part and product text fields.
     */
    @FXML
    public void initialize() {
        partFilterField.setPromptText("Search by Part ID or Name");
        partFilterField.setFocusTraversable(false);

        productFilterField.setPromptText("Search by Product ID or Name");
        productFilterField.setFocusTraversable(false);

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method exits the program.
     * @param actionEvent Exit button event
     */
    @FXML
    public void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Displays the modify part menu.
     *
     * Displays error if no part is selected.
     *
     * @param actionEvent Modify part form button event
     * @throws IOException FXMLLoader IOException
     */
    @FXML
    public void onActionModifyPart(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartFormController modifyPartFormController = loader.getController();
            modifyPartFormController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
            stage.setTitle("Modify Part Form");
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException nullPointerException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to modify");
            alert.show();

        }
    }

    /**
     * This method deletes a selected part from inventory.
     * It also displays confirmation message before deletion.
     * @param actionEvent Delete part button event
     */
    @FXML
    public void onActionDeletePart(ActionEvent actionEvent){
        Part selectedItem = partTableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to delete.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");
            alert.setTitle("Delete");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                partTableView.getItems().remove(selectedItem);
                Inventory.deletePart(selectedItem);

            }
        }
    }

    /**
     * This method displays the add product form.
     * @param actionEvent Add product form button event
     * @throws IOException FXMLLoader IOException
     */
    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProductForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method displays the modify product form.
     * It also displays an error if no product is selected.
     * @param actionEvent Modify product button event
     * @throws IOException FXMLLoader IOException
     */
    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProductForm.fxml"));
            loader.load();

            ModifyProductFormController modifyProductFormController = loader.getController();
            modifyProductFormController.sendProduct(productTableView.getSelectionModel().getSelectedItem()); //Sends the product to modify

            Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
            stage.setTitle("Modify Product Form");
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException nullPointerException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product to modify.");
            alert.show();
        }
    }

    /**
     * This method deletes a product from the inventory.
     * It also displays a confirmation message before deletion.
     * @param actionEvent Delete product button event
     */
    public void onActionDeleteProduct(ActionEvent actionEvent) {
        Product selectedItem = productTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(selectedItem == null){
            alert.setContentText("Please select a product to delete.");
            alert.show();
        }
        else if(selectedItem.getAllAssociatedParts().size() != 0){
            alert.setContentText("Product has associated parts, please remove associated parts before deleting.");
            alert.show();
        }
        else{
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product?");
            alert.setTitle("Delete");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                productTableView.getItems().remove(selectedItem);
                Inventory.deleteProduct(selectedItem);
            }
        }
    }

    /**
     * This method searches the part table and displays or highlights the searched item.
     * If nothing is found a window will display saying nothing found.
     * @param actionEvent Search parts button event
     */
    public void onActionSearchParts(ActionEvent actionEvent) {
        partTableView.getSelectionModel().clearSelection();
        String stringSearch = partFilterField.getText(); //Search data
        ObservableList<Part> parts = FXCollections.observableArrayList();
        try {
            parts.add(Inventory.lookupPart(Integer.parseInt(stringSearch)));
            if (parts.get(0) == null) {
                throw new NumberFormatException();
            } else {
                partTableView.setItems(Inventory.getAllParts());
                for (Part part : Inventory.getAllParts()) {
                    if (Integer.toString(part.getId()).equals(stringSearch)) {
                        partTableView.getSelectionModel().select(part);
                    }
                }
            }
        } catch (NumberFormatException e) {
            parts = Inventory.lookupPart(stringSearch);
            if (parts.size() != 0) {
                partTableView.setItems(parts);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing Found");
                alert.setTitle("Nothing Found");
                alert.show();
            }
        }
    }

    /**
     * This method searches the product table and displays or highlights the searched item.
     *
     * RUNTIME ERROR: When I used the lookupProduct method with parameter ID, I kept getting a nullPointerException.
     * I fixed this issue when I realized a null object was getting added to my products lists. Instead of checking if
     * products.size() == 0, I changed it to products.get(0) == null
     *
     * @param actionEvent Search products button event
     */
    public void onActionSearchProducts(ActionEvent actionEvent) {
        productTableView.getSelectionModel().clearSelection();
        String stringSearch = productFilterField.getText();
        ObservableList<Product> products = FXCollections.observableArrayList();
        try {
            products.add(Inventory.lookupProduct(Integer.parseInt(stringSearch)));
            if (products.get(0) == null) {
                throw new NumberFormatException();
            } else {
                productTableView.setItems(Inventory.getAllProducts());
                for (Product product : Inventory.getAllProducts()) {
                    if (Integer.toString(product.getId()).equals(stringSearch)) {
                        productTableView.getSelectionModel().select(product);
                    }
                }
            }
        } catch (NumberFormatException e) {
            products = Inventory.lookupProduct(stringSearch);
            if (products.size() != 0) {
                productTableView.setItems(products);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing Found");
                alert.setTitle("Nothing Found");
                alert.show();
            }
        }
    }
}