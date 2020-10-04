package com.tourmanager;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    private static Scanner in = new Scanner(System.in);
    private static List<Staff> staffList = new ArrayList<>();
    private static List<Location> locationList = new ArrayList<>();
    private static List<TourType> tourTypeList = new ArrayList<>();
    private static List<Tour> tourList = new ArrayList<>();
    private static Staff currentUser = null;
    public static void main(String[] args) {


        //Loading dummy data
        dummyStaff();
        dummyLocations();
        dummyTourTypes();
        dummyTours();

        System.out.println("Welcome to Professional Computer Practise Tour Guide Program\n\n");


        do {
            System.out.println("******************* LOGIN **************************");
        } while (!login());

        int choice;

        do {
            System.out.println("*********************** MAIN MENU *****************************");
            System.out.println("Please select any option from the below List to proceed");
            System.out.println("1. Staff Management");
            System.out.println("2. Locations Management");
            System.out.println("3. Tours Management");
            System.out.println("4. Logout");
            System.out.println("5. Quit");
            System.out.print("Choose your option: ");
            choice = getInteger();


            int subchoice;
            switch (choice) {
                case 1:
                    if(currentUser.getType() != 1){
                        showPermissionError();
                    }else{
                        do {
                            subchoice = staffManagement();
                        } while (subchoice != 4);
                    }

                    break;
                case 2:
                    do {
                        subchoice = locationManagement();
                    } while (subchoice != 5);
                    break;
                case 3:
                    do {
                        subchoice = tourManagement();
                    } while (subchoice != 10);
                    break;
                case 4:
                    System.out.println("*************************** LOGOUT ******************************************");
                    do {
                        System.out.println("******************* LOGIN **************************");
                    } while (!login());

                    break;
                case 5:
                    System.out.println("********************************* GOOD BYE *********************************");
                    break;
                default:
            }
        } while (choice != 5);


    }

    private static int staffManagement() {

        System.out.println("*************************** STAFF MENU *********************************");
        System.out.println("1. Add Staff");
        System.out.println("2. Active/Deactivate Staff");
        System.out.println("3. Show All Staff");
        System.out.println("4. Back");
        System.out.print("Choose your option: ");
        int choice = getInteger();

        switch (choice) {
            case 1:
                addStaff();
                break;
            case 2:
                editStaff();
                break;
            case 3:
                if(currentUser.getType() != 1){
                    showPermissionError();
                }else{
                    System.out.println("****************************** STAFF LIST **********************************");
                    for (Staff staff : staffList) {
                        System.out.println(staff);
                    }
                }

                break;
            case 4:
                break;
            default:
                System.out.println("Invalid Choice");

        }
        return choice;
    }

    private static void addStaff() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** ADD NEW STAFF ********************************\n");
        System.out.print("Enter First Name: ");
        String firstName = in.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = in.nextLine();
        System.out.print("Enter Staff type (1: admin, 2: assistant): ");
        int type = getInteger();
        System.out.print("Enter username: ");
        String username = in.nextLine();
        System.out.print("Enter password: ");
        String password = in.nextLine();
        System.out.print("Enter address: ");
        String address = in.nextLine();

        System.out.print("Enter post code: ");
        int postCode = getInteger();
        Staff staff = new Staff(firstName, type, lastName, address, postCode, username, password);
        staffList.add(staff);
        System.out.println("\n******************** STAFF ADDED SUCCESSFULLY ********************************\n");
    }

    private static void editStaff() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** Edit STAFF ********************************\n");
        System.out.print("Enter ID of Staff to change status: ");
        int id = getInteger();

        Staff selected = null;
        for (Staff staff : staffList) {
            if (staff.getId() == id) {
                selected = staff;
            }
        }

        if (selected == null) {
            System.out.println("Staff with ID=" + id + " is not found");
            return;
        }

        int index = staffList.indexOf(selected);

        System.out.print("Enter new status (true, false): ");
        Boolean status = getBoolean();
        selected.setStatus(status);
        staffList.set(index, selected);
        System.out.println("\n******************** STAFF UPDATED SUCCESSFULLY ********************************\n");
    }

    private static int locationManagement() {
        System.out.println("*************************** LOCATION MENU *********************************");
        System.out.println("1. Add new Location");
        System.out.println("2. Edit Location");
        System.out.println("3. Remove Location");
        System.out.println("4. Show All Locations");
        System.out.println("5. Back");
        System.out.print("Choose your option: ");
        int choice = getInteger();
        switch (choice) {
            case 1:
                addLocation();
                break;
            case 2:
                editLocation();
                break;
            case 3:
                removeLocation();
                break;
            case 4:
                for (Location location : locationList) {
                    System.out.println(location);
                }
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid Choice");

        }
        return choice;
    }

    private static void addLocation() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** ADD NEW LOCATION ********************************\n");
        System.out.print("Enter Location Name: ");
        String name = in.nextLine();
        System.out.print("Enter X coordinate: ");
        Double x = getDouble();
        System.out.print("Enter Y coordinate: ");
        Double y = getDouble();
        System.out.print("Enter Description: ");
        String desc = in.nextLine();
        System.out.print("Enter minimum time: ");
        Double time = getDouble();

        Location location = new Location(name, x, y, desc, time);
        locationList.add(location);
        System.out.println("\n******************** LOCATION ADDED SUCCESSFULLY ********************************\n");
    }

    private static void editLocation() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** EDIT LOCATION ********************************\n");

        Location selected = findLocationWithID();

        if (selected == null) {
            return;
        }

        System.out.print("Enter new Location Name(Empty to keep previous): ");
        String name = in.nextLine();
        if (name.isEmpty()) {
            name = selected.getName();
        }
        System.out.print("Enter X coordinate(Empty to keep previous): ");
        Double x;
        String data = in.nextLine();
        if (data.isEmpty()) {
            x = selected.getXco();
        } else {
            x = getDouble();
        }
        System.out.print("Enter Y coordinate(Empty to keep previous): ");
        Double y;
        data = in.nextLine();
        if (data.isEmpty()) {
            y = selected.getYCo();
        } else {
            y = getDouble();
        }
        System.out.print("Enter Description(Empty to keep previous): ");
        String desc = in.nextLine();
        if (desc.isEmpty()) {
            desc = selected.getDesc();
        }
        System.out.print("Enter minimum time(Empty to keep previous): ");
        Double time;
        data = in.nextLine();
        if (data.isEmpty()) {
            time = selected.getTime();
        } else {
            time = getDouble();
        }

        int index = locationList.indexOf(selected);
        selected.setDesc(desc);
        selected.setName(name);
        selected.setTime(time);
        selected.setXco(x);
        selected.setYCo(y);
        locationList.set(index, selected);
        System.out.println("\n******************** LOCATION UPDATED SUCCESSFULLY ********************************\n");
    }

    private static void removeLocation() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** REMOVE LOCATION ********************************\n");


        Location selected = findLocationWithID();

        if (selected == null) {
            return;
        }

        for (Tour tour : tourList) {
            for (Location location : tour.getLocations()) {
                if(location == selected){
                    System.out.println("********************** CANNOT DELETE LOCATION. IT IS USED BY TOUR=" + tour.getName() + "**************************");
                }
            }
        }

        locationList.remove(selected);

        System.out.println("************************* LOCATION REMOVED SUCCESSFULLY ****************************");
    }

    private static Location findLocationWithID(){
        System.out.print("Enter location ID: ");
        int id = getInteger();

        Location selected = null;
        for (Location location : locationList) {
            if (location.getId() == id) {
                selected = location;
            }
        }

        if (selected == null) {
            System.out.println("Location with ID=" + id + " is not found");
        }

        return selected;
    }


    private static int tourManagement() {
        System.out.println("*************************** TOUR MENU *********************************");
        System.out.println("1. Add Tour Type");
        System.out.println("2. Edit Tour Type");
        System.out.println("3. Remove Tour Type");
        System.out.println("4. Show All Tour Types");
        System.out.println("5. Add new Tour");
        System.out.println("6. Edit Tour");
        System.out.println("7. Remove Tour");
        System.out.println("8. Show All Tours");
        System.out.println("9. Select a tour");
        System.out.println("10. Back");
        System.out.print("Choose your option: ");
        int choice = getInteger();
        switch (choice) {
            case 1:
                addTourType();
                break;
            case 2:
                editTourType();
                break;
            case 3:
                removeTourType();
                break;
            case 4:
                for (TourType tourType : tourTypeList) {
                    System.out.println(tourType);
                }
                break;
            case 5:
                addTour();
                break;
            case 6:
                editTour();
                break;
            case 7:
                removeTour();
                break;
            case 8:
                System.out.println("***************************** TOUR LIST *****************************");
                for (Tour tour : tourList) {

                    System.out.println(tour);
                }
                break;
            case 9:
                tourToVoice();
                break;
            case 10:
                break;
            default:
                System.out.println("Invalid Choice");

        }
        return choice;
    }

    private static void addTourType() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** ADD NEW TOUR TYPE ********************************\n");
        System.out.print("Enter Tour Type: ");
        String name = in.nextLine();
        TourType tourType = new TourType(name);
        tourTypeList.add(tourType);
        System.out.println("******************** TOUR TYPE SAVED SUCCESSFULLY ********************************\n");
    }

    private static void editTourType() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** Edit TOUR TYPE ********************************\n");
        TourType selected = findTourTypeWithID();

        if (selected == null) {
            return;
        }
        int index = tourTypeList.indexOf(selected);
        System.out.print("Enter Tour Type name: ");
        String name = in.nextLine();
        selected.setName(name);
        tourTypeList.set(index, selected);
        System.out.println("******************** TOUR TYPE UPDATED SUCCESSFULLY ********************************\n");
    }

    private static void removeTourType() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** REMOVE TOUR TYPE ********************************\n");

        TourType selected = findTourTypeWithID();

        if (selected == null) {
            return;
        }

        for (Tour tour : tourList) {
            if(tour.getType() == selected){
                System.out.println("******************** CANNOT DELETE THIS TOUR TYPE. IT IS USED BY TOUR= "+tour.getName()+" ********************************");
                return;
            }
        }
        tourTypeList.remove(selected);
        System.out.println("******************** TOUR TYPE REMOVED SUCCESSFULLY ********************************\n");
    }


    private static TourType findTourTypeWithID(){
        System.out.print("Enter Tour Type ID: ");
        int id = getInteger();

        TourType selected = null;
        for (TourType tourType : tourTypeList) {
            if (tourType.getId() == id) {
                selected = tourType;
            }
        }

        if (selected == null) {
            System.out.println("Tour Type with ID=" + id + " is not found");

        }
        return selected;
    }

    private static void addTour() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** ADD TOUR ********************************\n");
        System.out.print("Enter Tour Name: ");
        String name = in.nextLine();
        System.out.println("Select Tour Type: ");
        showTourTypes();
        System.out.print("Selected Type: ");
        int type = getInteger();
        while(type > tourTypeList.size()){
            System.out.print("Invalid Tour type. select again: ");
            type = getInteger();
        }
        TourType selected = tourTypeList.get(type - 1);
        System.out.println("Select location ids");
        showLocations();
        System.out.print("Locations: ");
        List<Location> locations = getLocations();

        Tour tour = new Tour(name, selected);
        tour.setLocations(locations);

        tourList.add(tour);
    }

    private static void editTour() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** Edit TOUR ********************************\n");
        Tour selected = findTourWithID();

        if (selected == null) {
            return;
        }
        int index = tourList.indexOf(selected);
        System.out.print("Enter Tour name(Enter to keep old): ");
        String name = in.nextLine();
        if (name.isEmpty()) {
            name = selected.getName();
        }
        System.out.print("Select tour type: ");
        showTourTypes();
        int type = getInteger();
        while(type > tourTypeList.size()){
            System.out.print("Invalid tour type id. Try again: ");
            type = getInteger();
        }
        TourType tourType = tourTypeList.get(type - 1);
        System.out.print("Select Locations: ");
        showLocations();
        List<Location> locations = getLocations();

        selected.setName(name);
        selected.setType(tourType);
        selected.setLocations(locations);
        tourList.set(index, selected);
    }

    private static void removeTour() {
        if(currentUser.getType() != 1){
            showPermissionError();
            return;
        }
        System.out.println("******************** REMOVE TOUR ********************************\n");

        Tour selected = findTourWithID();


        if (selected == null) {
            return;
        }
        tourList.remove(selected);
        System.out.println("******************** TOUR REMOVED SUCCESSFULLY ********************************\n");
    }

    private static Tour findTourWithID(){
        System.out.print("Enter Tour ID: ");
        int id = getInteger();

        Tour selected = null;
        for (Tour tour : tourList) {
            if (tour.getId() == id) {
                selected = tour;
            }
        }

        if (selected == null) {
            System.out.println("Tour with ID=" + id + " is not found");
        }

        return selected;
    }

    private static boolean login() {

        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        for (Staff staff : staffList) {
            if (staff.getUsername().equals(username) && staff.getPassword().equals(password)) {
                System.out.println("Welcome " + staff.getFirstName());
                currentUser = staff;
                return true;
            }
        }
        System.out.println("Invalid username or password. Please try again\n");
        return false;
    }

    private static void dummyStaff() {
        Staff staff = new Staff("Administrator", 1, "Last Name 1", "Address 1", 123, "admin", "admin");
        Staff staff2 = new Staff("First Name 2", 2, "Last Name 2", "Address 2", 321, "user", "user");
        staffList.add(staff);
        staffList.add(staff2);
    }

    private static void dummyLocations() {
        Location location = new Location("Location Name ", 2.3,  2.5, "Address ",  2.0);
        Location location2 = new Location("Location Name2 ", 2.3,  2.5, "Address2 ",  2.0);
        locationList.add(location);
        locationList.add(location2);

    }

    private static void dummyTourTypes() {
        TourType tourtype = new TourType("Tour Type ");
        TourType tourtype2 = new TourType("Tour Type 2" );
        tourTypeList.add(tourtype);
        tourTypeList.add(tourtype2);
    }

    private static void dummyTours() {
        Tour tour = new Tour("Tour 1", tourTypeList.get(0));
        Tour tour2 = new Tour("Tour 2", tourTypeList.get(1));
        tourList.add(tour);
        tourList.add(tour2);
        tour.setLocations(locationList);
    }

    private static void showTourTypes() {
        System.out.println("Available Types are: ");
        int sr = 1;
        for (TourType tourType : tourTypeList) {
            System.out.println(sr + " - " + tourType.getName());
            sr++;
        }
    }

    private static void showTours() {
        System.out.println("Available Tours are: ");
        int sr = 1;
        for (Tour tour : tourList) {
            System.out.println(sr + " - " + tour.getName());
            sr++;
        }
    }

    private static void showLocations() {
        System.out.println("Available locations are: ");
        for (int i = 1; i <= locationList.size(); i++) {
            System.out.println(i + " - " + locationList.get(i - 1).getName());
        }
    }

    private static Integer getInteger(){

        int num;
        do {
            try{
                num = Integer.parseInt(in.nextLine());
            }catch (NumberFormatException ex){
                num = -1;
                System.out.print("Invalid number. Try again: ");
            }
        }while(num <= 0);
        return num;
    }




    private static Double getDouble(){

        double num;
        do {
            try{
                num = Double.parseDouble(in.nextLine());
            }catch (NumberFormatException ex){
                num = -100;
                System.out.print("Invalid number. Try again: ");
            }
        }while(num <= 0);
        return num;
    }

    private static List<Location> getLocations(){
        List<Location> locations = new ArrayList<>();
        String[] ids = in.nextLine().split(" ");
        for (String id : ids) {
            try{
                int locID = Integer.parseInt(id);
                if(locID < 0 || locID > locationList.size()){
                    System.out.println("Location with id: " + locID + " is not found. Hence cannot be added");
                }else{
                    locations.add(locationList.get(locID - 1));
                }
            }catch (NumberFormatException ex){
                System.out.println("Invalid id=" + id);
            }
        }
        return locations;
    }


    private static Boolean getBoolean(){
        String data = in.next();
        Boolean value = Boolean.parseBoolean(data);
        in.nextLine();
        return value;
    }


    private static void tourToVoice(){
        System.out.println("Select a tour: ");
        showTours();
        System.out.print("Your choice: ");
        int id = getInteger();
        while(id > tourList.size()){
            System.out.print("Invalid tour. Try again: ");
            id = getInteger();
        }
        Tour tour = tourList.get(id - 1);

        System.out.println("You have selected");
        System.out.println(tour);
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
        voice.speak("Welcome to Professional Computer Practice Tour Guide");
        voice.speak("You have selected. " + tour.getName() + ", of tour type, " + tour.getType().getName());
        voice.speak("Minimum time required to complete the tour is. " + tour.getDuration() + " minutes.");
        voice.speak("This tour has Locations. ");
        int i = 1;
        if(tour.getLocations().size() > 0){
            for (Location location : tour.getLocations()) {
                voice.speak("Location " + i + ", " + location.getDesc() + ".");
                i++;
            }
        }else{
            voice.speak("No Location exits");
        }

        voice.deallocate();
    }

    private static void showPermissionError(){
        System.out.println("********************************************************************************************");
        System.out.println("**************                                                         *********************");
        System.out.println("**************   YOU DON'T HAVE PERMISSIONS TO PERFORM THIS FUNCTION   *********************");
        System.out.println("**************                                                         *********************");
        System.out.println("*********************************************************************************************");
    }

}
