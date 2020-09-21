package tour.management.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tour.management.helper.Data;
import tour.management.helper.Utils;
import tour.management.models.Staff;

import java.net.URL;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

public class StaffController implements Initializable {
    public TableView<Staff> table;
    public TableColumn<Staff, Integer> colSr;
    public TableColumn<Staff, String> colFirstName;
    public TableColumn<Staff, String> colLastName;
    public TableColumn<Staff, String> colType;
    public TableColumn<Staff, String> colUsername;
    public TableColumn<Staff, String> colAddress;
    public TextField txtFirstName;
    public TextField txtLastName;
    public RadioButton rdAdmin;
    public RadioButton rdAssistant;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public TextArea txtAddress;
    public TextField txtPostcode;
    public CheckBox chkActive;


    private Staff selected = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
    }

    public void addOrUpdate(ActionEvent event) {
        String firstname = txtFirstName.getText();
        String lastname = txtLastName.getText();
        String password = txtPassword.getText();
        String address = txtAddress.getText();
        String username = txtUsername.getText();
        String postCodeString = txtPostcode.getText();
        int type = rdAdmin.isSelected() ? 1 : 2;
        boolean status = chkActive.isSelected();

        String errors = "";
        if (firstname.isEmpty()) {
            errors += "First name is missing\n";
        }
        if (lastname.isEmpty()) {
            errors += "Last name is missing\n";
        }

        if (username.isEmpty()) {
            errors += "User name is missing\n";
        }

        if (password.isEmpty()) {
            errors += "Password is missing\n";
        }

        if (address.isEmpty()) {
            errors += "Address is missing\n";
        }

        if (postCodeString.isEmpty()) {
            errors += "Post code is missing\n";
        }
        int postCode = 0;
        try {
            postCode = Integer.parseInt(postCodeString);
        } catch (NumberFormatException ex) {
            errors += "Post code must be a number\n";
        }

        if (!errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please correct following errors.");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }

        if (selected == null) {
            selected = new Staff(firstname, type, lastname, address, postCode, username, password);
            Data.staffList.add(selected);
        } else {
            int index = Data.staffList.indexOf(selected);

            selected.setStatus(status);
            selected.setAddress(address);
            selected.setFirstName(firstname);
            selected.setLastName(lastname);
            selected.setPassword(password);
            selected.setPostCode(postCode);
            selected.setType(type);
            selected.setUsername(username);


            Data.staffList.set(index, selected);
        }
    }

    public void goBack(ActionEvent event) {
        Utils.openWindow("main", "Tour Management System");
        txtAddress.getScene().getWindow().hide();
    }

    public void delete(ActionEvent event) {
        Staff staff = table.getSelectionModel().getSelectedItem();
        if (staff != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to delete staff?");
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.equals(ButtonType.OK)) {
                    Data.staffList.remove(staff);
                }
            });
        }
    }

    private void initializeTable() {
        table.setItems(Data.staffList);
        colSr.setCellValueFactory(param -> new SimpleObjectProperty<>(table.getItems().indexOf(param.getValue()) + 1));
        colFirstName.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getFirstName()));
        colLastName.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getLastName()));
        colAddress.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getAddress()));
        colUsername.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getUsername()));
        colType.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getType() == 1 ? "Admin" : "Assistant"));

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selected = newValue;
                onTableSelect(newValue);
            } else {
                selected = null;
                clear();
            }
        });
    }

    private void onTableSelect(Staff staff) {
        txtAddress.setText(staff.getAddress());
        txtFirstName.setText(staff.getFirstName());
        txtLastName.setText(staff.getLastName());
        txtPostcode.setText(staff.getPostCode() + "");
        txtUsername.setText(staff.getUsername());
        txtPassword.setText(staff.getPassword());
        if (staff.getStatus()) {
            chkActive.setSelected(true);
        } else {
            chkActive.setSelected(false);
        }

        if (staff.getType() == 1) {
            rdAdmin.setSelected(true);
        } else {
            rdAssistant.setSelected(true);
        }
    }

    @FXML
    private void clear() {
        selected = null;
        txtAddress.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtPostcode.clear();
        txtUsername.clear();
        txtPassword.clear();
        chkActive.setSelected(true);
        rdAssistant.setSelected(true);

    }


}
