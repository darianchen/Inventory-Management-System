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
 * Controller of AddProductForm Class
 * @author Darian Chen
 */
public class AddProductFormController {

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

    private ObservableList<Part> associatedList = FXCollections.observableArrayList();

    /**
     * Unique Product ID set to 1000
     */
    public static int uniqueProductId = 1000;


    /**
     * This method cancels the add product form and returns to the main form.
     * @param actionEvent Cancel button event
     * @throws IOException FXMLLoader IOException
     */
    @FXML
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the Id text field to uneditable and sets text to "Auto Gen- Disabled".
     *
     * Displays data in the cells using setCellValueFactory and PropertyValueFactory
     */
    @FXML
    public void initialize(){
        productIdTxt.setEditable(false);
        productIdTxt.setText("Auto Gen- Disabled");
        partFilterField.setPromptText("Search by Part ID or Name");
        partFilterField.setFocusTraversable(false);

        topPartTableView.setItems(Inventory.getAllParts());
        topPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        topPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        topPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        bottomPartTableView.setItems(associatedList);
        bottomPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        bottomPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bottomPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        bottomPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method utilizes the validator class found in the helper package.
     * It validates all the text fields before saving the product.
     * Lastly, it returns to the main form after saving the product.
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

            Product product = new Product(++uniqueProductId,name,price,inv,min,max);
            for(Part part: associatedList){
                product.addAssociatedPart(part);
            }

            Inventory.addProduct(product);


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
     * This method Searches the part table and displays all parts found.
     * It Searches the inventory by ID first and then name.
     * Lastly, it displays error message if nothing is found.
     * @param actionEvent Search part table event
     */
    public void onActionSearchParts(ActionEvent actionEvent) {
        topPartTableView.getSelectionModel().clearSelection();
        String stringSearch = partFilterField.getText(); //Search data
        ObservableList<Part> parts = FXCollections.observableArrayList();
        try {
            parts.add(Inventory.lookupPart(Integer.parseInt(stringSearch)));
            if (parts.get(0) == null) {
                throw new NumberFormatException();
            } else {
                topPartTableView.setItems(Inventory.getAllParts());
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
     * This method removes an associated part from the product.
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove the selected part?");
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
     * @param actionEvent Add part button event
     */
    public void onActionAddPart(ActionEvent actionEvent) {
        Part part = topPartTableView.getSelectionModel().getSelectedItem();
        if(part == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to add");
            alert.show();
            return;
        }
        associatedList.add(part);
    }
}
