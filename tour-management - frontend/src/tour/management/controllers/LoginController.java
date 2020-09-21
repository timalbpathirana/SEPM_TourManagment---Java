package tour.management.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tour.management.helper.Data;
import tour.management.helper.Utils;
import tour.management.models.Staff;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField txtUsername;
    public PasswordField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Data.staffList.add(new Staff("Admin", 1, "Admin", "Adress details", 1234, "admin", "admin"));
    }


    public void login(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please correct following errors.");
            alert.setContentText("Username and password are required");
            alert.showAndWait();
            return;
        }

        Data.staffList.forEach(staff -> {
            if (staff.getUsername().equals(username) && staff.getPassword().equals(password)) {
                Data.current = staff;
            }
        });

        if(Data.current != null){
            Utils.openWindow("main", "Tour management System");
            txtPassword.getScene().getWindow().hide();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cannot login");
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
        }


    }


}
