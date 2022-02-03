package Main;

import Controller.AddPartFormController;
import Controller.AddProductFormController;
import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * @author Darian Chen
 */
public class Main extends Application{
        public static void main(String[] args) {
                InHouse inhouse1 = new InHouse(++AddPartFormController.uniquePartId,"Wheel",19.99,65,0,5000,111);
                OutSourced outSourced2 = new OutSourced(++AddPartFormController.uniquePartId,"Charger",5.99,50,2,100,"Apple");

                Product product1 = new Product(++AddProductFormController.uniqueProductId,"IPhone 12",999.99,777,100,1000);
                Product product2 = new Product(++AddProductFormController.uniqueProductId,"Tesla Model S",90000.99,888,42,10000);

                Inventory.getAllParts().add(inhouse1);
                Inventory.getAllParts().add(outSourced2);
                Inventory.getAllProducts().add(product1);
                Inventory.getAllProducts().add(product2);

                launch(args);
        }
        /**
         * @param primaryStage The primary stage object
         * @throws Exception The exception
         */
        @Override
        public void start(Stage primaryStage) throws Exception {
                Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                Scene scene = new Scene(root);
                primaryStage.setTitle("Inventory Management System");
                primaryStage.setScene(scene);
                primaryStage.show();
        }
}