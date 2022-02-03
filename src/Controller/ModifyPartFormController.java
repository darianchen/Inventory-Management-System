package Controller;

import Helper.Validator;
import Model.InHouse;
import Model.Part;
import Model.Inventory;
import Model.OutSourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller of ModifyPartForm Class
 * @author Darian Chen
 */
public class ModifyPartFormController {

    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private Label label;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partMachineIdTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partPriceTxt;

    private int partId;

    private int partIndex;

    /**
     * This method cancels add part form and returns to main form.
     * @param event Cancel button event
     * @throws IOException FXMLLoader IOException
     */
    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method saves a part object and returns to the main form.
     * It also creates an InHouse or Outsourced object depending on the radio button selected.
     * The text fields are validated using the Validator class found in the Helper Package.
     * @param event Save button event
     * @throws IOException FXMLLoader IOException
     */
    @FXML
    public void onActionSave(ActionEvent event) throws IOException {
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
                InHouse inhouse = new InHouse(partId,name,price,inv,min,max,machineId); //Create new InHouse object
                Inventory.updatePart(partIndex,inhouse); //Store the InHouse Object in the parts list

            } else if (outsourcedRBtn.isSelected()) {
                String companyName = Validator.validateCompanyName(partMachineIdTxt.getText());
                OutSourced outSourced = new OutSourced(partId,name,price,inv,min,max,companyName); //Create a new OutSourced object
                Inventory.updatePart(partIndex,outSourced); // Store the Outsourced object in the parts list
            }
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
     * This method retrieves all the data from the part object and places them into the appropriate text fields and variables.
     * @param part The part to modify
     */
    public void sendPart(Part part){
        partId = part.getId();
        partIndex = Inventory.getAllParts().indexOf(part);
        partIdTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        partInvTxt.setText(String.valueOf(part.getStock()));
        partPriceTxt.setText(String.valueOf(part.getPrice()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
        partMinTxt.setText(String.valueOf(part.getMin()));


        if(part instanceof InHouse){
            inhouseRBtn.setSelected(true);
            label.setText("Machine ID");
            partMachineIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));

        }
        else if(part instanceof OutSourced){
            outsourcedRBtn.setSelected(true);
            label.setText("Company Name");
            partMachineIdTxt.setText(String.valueOf(((OutSourced) part).getCompany()));
        }
    }

    /**
     * Sets part text field to uneditable
     */
    @FXML
    public void initialize(){

        partIdTxt.setEditable(false);
    }

    /**
     * Changes label based on radio button click.
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
}
