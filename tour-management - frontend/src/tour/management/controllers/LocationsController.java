package tour.management.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tour.management.helper.Data;
import tour.management.helper.Utils;
import tour.management.models.Location;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationsController implements Initializable {
    public TableView<Location> table;
    public TableColumn<Location, Integer> colSr;
    public TableColumn<Location, String> colName;
    public TableColumn<Location, String> colX;
    public TableColumn<Location, String> colY;
    public TableColumn<Location, String> colDesc;
    public TableColumn<Location, String> colTime;
    public TextField txtName;
    public TextField txtX;
    public TextField txtY;
    public TextArea txtDesc;
    public TextField txtTime;
    public CheckBox chkActive;
    public Button btnAdd;
    public Button btnDelete;


    private Location selected = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeTable();
        if(Data.current.getType() != 1){
            btnAdd.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    public void addOrUpdate(ActionEvent event) {
        String name = txtName.getText();
        String desc = txtDesc.getText();
        String timeText = txtTime.getText();
        String xText = txtX.getText();
        String yText = txtY.getText();
        boolean status = chkActive.isSelected();

        String errors = "";
        if (name.isEmpty()) {
            errors += "Name is missing\n";
        }
        if (xText.isEmpty()) {
            errors += "X Coordinate is missing\n";
        }

        if (yText.isEmpty()) {
            errors += "Y Coordinate is missing\n";
        }
        if (desc.isEmpty()) {
            errors += "Description is missing\n";
        }
        if (timeText.isEmpty()) {
            errors += "Time is missing\n";
        }
        double x = 0d;
        double y = 0d;
        double time = 0d;
        try {
            x = Double.parseDouble(xText);
        } catch (NumberFormatException ex) {
            errors += "X Coordinate must be a number\n";
        }

        try {
            y = Double.parseDouble(yText);
        } catch (NumberFormatException ex) {
            errors += "Y Coordinate must be a number\n";
        }

        try {
            time = Double.parseDouble(xText);
        } catch (NumberFormatException ex) {
            errors += "time must be a number\n";
        }

        if (!errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please correct following errors.");
            alert.setContentText(errors);
            alert.showAndWait();
            return;
        }

        if (selected == null) {
            selected = new Location(name, x, y, desc, time);
            Data.locations.add(selected);
        } else {
            int index = Data.locations.indexOf(selected);

            selected.setStatus(status);
            selected.setName(name);
            selected.setXco(x);
            selected.setYCo(y);
            selected.setTime(time);
            selected.setDesc(desc);

            Data.locations.set(index, selected);
        }
    }

    public void goBack(ActionEvent event) {
        Utils.openWindow("main", "Tour Management System");
        txtY.getScene().getWindow().hide();
    }

    public void delete(ActionEvent event) {
        Location location = table.getSelectionModel().getSelectedItem();
        if (location != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Do you want to delete location?");
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.equals(ButtonType.OK)) {
                    Data.locations.remove(location);
                }
            });
        }
    }

    private void initializeTable() {
        table.setItems(Data.locations);
        colSr.setCellValueFactory(param -> new SimpleObjectProperty<>(table.getItems().indexOf(param.getValue()) + 1));
        colName.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getName()));
        colDesc.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDesc()));
        colTime.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTime().toString()));
        colX.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getXco().toString()));
        colY.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getYCo().toString()));

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

    private void onTableSelect(Location location) {
        txtName.setText(location.getName());
        txtY.setText(location.getYCo().toString());
        txtX.setText(location.getXco().toString());
        txtTime.setText(location.getTime().toString());
        txtDesc.setText(location.getDesc());
        if (location.getStatus()) {
            chkActive.setSelected(true);
        } else {
            chkActive.setSelected(false);
        }
    }

    @FXML
    private void clear() {
        selected = null;
        txtName.clear();
        txtTime.clear();
        txtX.clear();
        txtY.clear();
        txtDesc.clear();
        chkActive.setSelected(true);
    }


}
