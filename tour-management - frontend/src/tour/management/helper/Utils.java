package tour.management.helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tour.management.Main;

import java.io.IOException;

public class Utils {
    public static void openWindow(String filename, String title){
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("ui/" + filename + ".fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle(title);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
