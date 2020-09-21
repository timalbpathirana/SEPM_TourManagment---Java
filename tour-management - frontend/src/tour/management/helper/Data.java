package tour.management.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tour.management.models.Location;
import tour.management.models.Staff;

public class Data {
    public static ObservableList<Staff> staffList = FXCollections.observableArrayList();
    public static ObservableList<Location> locations = FXCollections.observableArrayList();
    public static Staff current = null;
}
