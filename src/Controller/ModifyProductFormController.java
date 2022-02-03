package Controller;

import Helper.Validator;
import Model.*;
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
 * Controller of ModifyProductForm Class
 * @author Darian Chen
 */
public class ModifyProductFormController {

    @FXML
    private TableColumn<Part, Integer> topPartIdCol;

    @FXML
    private TableColumn<Part, Integer> topPartInvCol;

    @FXML
    private TableColumn<Part, String> topPartNameCol;

    @FXML
    private TableColumn<Part, Double> topPartPriceCol;

    @FXML
    private TableView<Part> topPartTableView;

    @FXML
    private TableColumn<Part,Integer> bottomPartIdCol;

    @FXML
    private TableColumn<Part, Integer> bottomPartInvCol;

    @FXML
    private TableColumn<Part, String> bottomPartNameCol;

    @FXML
    private TableColumn<Part, Integer> bottomPartPriceCol;

    @FXML
    private TableView<Part> bottomPartTableView;

    @FXML
    private TextField partFilterField;

    @FXML
    private TextField productIdTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productPriceTxt;

    private ObservableList<Part> associatedList = FXCollections.observableArrayList();

    private int productIndex;

    private int productId;

    /**
     * This method initializes the modify product form and sets up the top table view.
     */
    @FXML
    public void initialize(){
        productIdTxt.setEditable(false);

        partFilterField.setPromptText("Search by Part ID or Name");
        partFilterField.setFocusTraversable(false);

        topPartTableView.setItems(Inventory.getAllParts());
        topPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method cancels the modify product form and returns to the main form.
     * @param actionEvent Cancel button event
     * @throws IOException FXMLLoader IOException
     */
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method utilizes the validator class found in the helper package.
     * It validates all the text fields before saving the product.
     * An appropriate message will display if an error is found.
     * @param actionEvent Save button event
     */
    public void onActionSaveProduct(ActionEvent actionEvent) {
        try {
            String name = Validator.validateName(productNameTxt.getText());
            int inv = Validator.validateInv(productInvTxt.getText());
            double price = Validator.validatePrice(productPriceTxt.getText());
            int max = Validator.validateMax(productMaxTxt.getText());
            int min = Validator.validateMin(productMinTxt.getText());

            if(min < 0 || max < 0) {
                throw new NumberFormatException("Min and max cannot be less than 0.");
            }
            if(inv > max || inv < min) {
                throw new NumberFormatException("Inventory cannot be greater than max or less than min.");
            }
            Inventory.getAllProducts().get(productIndex).setName(name);
            Inventory.getAllProducts().get(productIndex).setStock(inv);
            Inventory.getAllProducts().get(productIndex).setPrice(price);
            Inventory.getAllProducts().get(productIndex).setMax(max);
            Inventory.getAllProducts().get(productIndex).setMin(min);

            Inventory.getAllProducts().get(productIndex).getAllAssociatedParts().clear();
            Inventory.getAllProducts().get(productIndex).getAllAssociatedParts().addAll(associatedList);

            Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch (NumberFormatException | IOException error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(error.getMessage());
            alert.show();
        }
    }

    /**
     * This method removes an associated part from product.
     * It displays a confirmation message before removal.
     * @param actionEvent Remove part button event
     */
    public void onActionRemoveAssociatedPart(ActionEvent actionEvent) {
        Part selectedItem = bottomPartTableView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to remove");
            alert.show();
            return;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");
            alert.setTitle("Delete");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                associatedList.remove(selectedItem);
            }
        }
    }

    /**
     * This method adds an associated part to a product.
     * It displays an alert window if no part is selected.
     * @param actionEvent Add part form button event
     */
    public void onActionAddPart(ActionEvent actionEvent) {
        Part part = topPartTableView.getSelectionModel().getSelectedItem();
        if(part == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to add.");
            alert.show();
            return;
        }
        associatedList.add(part);
    }

    /**
     * This validates all the text fields using the validator class and then saves the product.
     * It also checks for errors and displays an appropriate message if an error is found.
     * @param actionEvent Save button event
     */
    public void onActionSearchParts(ActionEvent actionEvent) {
        topPartTableView.getSelectionModel().clearSelection();
        String stringSearch = partFilterField.getText();
        ObservableList<Part> parts = FXCollections.observableArrayList();
        try {
            parts.add(Inventory.lookupPart(Integer.parseInt(stringSearch)));
            if (parts.get(0) == null) {
                throw new NumberFormatException();
            } else { topPartTableView.setItems(Inventory.getAllParts());
                for (Part part : Inventory.getAllParts()) {
                    if (Integer.toString(part.getId()).equals(stringSearch)) {
                        topPartTableView.getSelectionModel().select(part);
                    }
                }
            }
        } catch (NumberFormatException e) {
            parts = Inventory.lookupPart(stringSearch);
            if (parts.size() != 0) {
                topPartTableView.setItems(parts);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing Found");
                alert.setTitle("Nothing Found");
                alert.show();
            }
        }
    }

    /**
     * This method retrieves all the data from the product object and places them into the appropriate text fields and variables.
     * It also sets up bottom table view.
     * @param product The part to modify
     */
    public void sendProduct(Product product){
        productIndex = Inventory.getAllProducts().indexOf(product); //Get index of product in product list
        productId = product.getId();

        productIdTxt.setText(Integer.toString(product.getId()));
        productNameTxt.setText(product.getName());
        productInvTxt.setText(Integer.toString(product.getStock()));
        productPriceTxt.setText(Double.toString(product.getPrice()));
        productMaxTxt.setText(Integer.toString(product.getMax()));
        productMinTxt.setText(Integer.toString(product.getMin()));

        associatedList.addAll(product.getAllAssociatedParts());

        bottomPartTableView.setItems(associatedList);
        bottomPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bottomPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bottomPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        bottomPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
