package Controller;

import Helper.Validator;
import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller of AddPartForm Class
 * @author Darian Chen
 */
public class AddPartFormController {

    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private Label label;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partMachineIdTxt;

    /**
     * Unique static ID for part objects.
     */
    public static int uniquePartId = 0;

    /**
     * This method cancels the add part form and returns to the main form.
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
     * This method changes the bottom label based on which radio button is selected.
     * If InHouse is selected the bottom label will change to Machine ID.
     * If OutSourced is selected the bottom label will change to Company Name.
     * @param actionEvent Change label event
     */
    @FXML
    public void changeLabel(ActionEvent actionEvent){
        if (inhouseRBtn.isSelected()) {
            label.setText("Machine ID");
        } else if(outsourcedRBtn.isSelected()) {
            label.setText("Company Name");
        }
    }

    /**
     * This method saves a part object and returns to the main form.
     * It also creates an InHouse or Outsourced object depending on which radio button is selected.
     * The Text fields are validated using Validator class found in the Helper Package.
     * @param actionEvent Save button event
     * @throws IOException FXMLLoader IOException
     */
    @FXML
    public void onActionSavePart(ActionEvent actionEvent) throws IOException {
        try {
            String name = Validator.validateName(partNameTxt.getText());
            int inv = Validator.validateInv(partInvTxt.getText());
            double price = Validator.validatePrice(partPriceTxt.getText());
            int max = Validator.validateMax(partMaxTxt.getText());
            int min = Validator.validateMin(partMinTxt.getText());

            if(min < 0 || max < 0) {
                throw new NumberFormatException("Min and max cannot be less than 0.");
            }
            if(inv > max || inv < min) {
                throw new NumberFormatException("Inventory cannot be greater than max or less than min.");
            }
            if (inhouseRBtn.isSelected()) {
                int machineId = Validator.validateMachineId(partMachineIdTxt.getText());
                Inventory.addPart(new InHouse(++uniquePartId, name, price, inv, min, max, machineId));
            } else if (outsourcedRBtn.isSelected()) {
                String companyName = Validator.validateCompanyName(partMachineIdTxt.getText());
                Inventory.addPart(new OutSourced(++uniquePartId, name, price, inv, min, max, companyName));
            }
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch (NumberFormatException error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(error.getMessage());
            alert.show();
        }
    }

    /**
     * This method initializes the controller.
     * It also sets the part text field to uneditable and sets the text to "Auto Gen- Disabled".
     */
    @FXML
    public void initialize(){
        partIdTxt.setEditable(false);
        partIdTxt.setFont(Font.font(8.5));
        partIdTxt.setText("Auto Gen- Disabled");
    }
}