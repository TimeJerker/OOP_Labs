//package ui;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//
//public class Controller {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private Button button;
//
//    @FXML
//    private TextField count;
//
//    @FXML
//    void initialize() {
//        count.textProperty().addListener((observable, oldValue, newValue) -> {
//            validateCountInput();
//        });
//
//        button.setOnAction(_ ->{
//            button.getScene().getWindow().hide();
//
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("Table.fxml"));
//
//            try {
//                loader.load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Parent root = loader.getRoot();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.showAndWait();
//        });
//    }
//
//    private boolean validateCountInput() {
//        String countText = count.getText();
//        if (countText == null || countText.trim().isEmpty()) {
//            ExceptionHandler.handle(new IllegalArgumentException("The number of points field is not filled in"));
//            return false;
//        }
//
//        try {
//            int count = Integer.parseInt(countText);
//            if (count < 2) {
//                ExceptionHandler.handle(new IllegalArgumentException("The number of points must be greater than or equal to 2"));
//                return false;
//            }
//            return true;
//        } catch (NumberFormatException e) {
//            ExceptionHandler.handle(new NumberFormatException("Incorrect input format. Please enter an integer"));
//            return false;
//        }
//    }
//}