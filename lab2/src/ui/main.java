package ui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        stage.setTitle("Graphic interface");
        stage.setScene(new Scene(root,700,400));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}