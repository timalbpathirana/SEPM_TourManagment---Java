package tour.management.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tour.management.helper.Data;
import tour.management.helper.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button btnStaff;
    public Label lblUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Data.current != null){
            lblUser.setText(Data.current.getUsername());
        }

        if(Data.current.getType() != 1){
            btnStaff.setDisable(true);
        }
    }

    public void openStaffManagement(ActionEvent event) {
        Utils.openWindow("staff", "Staff Management");
        btnStaff.getScene().getWindow().hide();
    }

    public void logout(ActionEvent event) {
        Data.current = null;
        Utils.openWindow("login", "Login");
        btnStaff.getScene().getWindow().hide();
    }

    public void openLocationManagement(ActionEvent event) {
        Utils.openWindow("locations", "Location Management");
        btnStaff.getScene().getWindow().hide();
    }


}
