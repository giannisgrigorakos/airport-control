package sample.helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PopUpFlights {
    private SimpleStringProperty flightId;
    private SimpleStringProperty cityOfDeparture;
    private SimpleStringProperty flightType;
    private SimpleStringProperty planeType;
    private SimpleStringProperty currentState;
    private SimpleStringProperty parkedWhere;
    private SimpleStringProperty departureTime;

    /** Constructor of this class
     *
     * @param flightId the flight id
     * @param cityOfDeparture the city of departure
     * @param flightType the type of the flight
     * @param planeType the plane type
     * @param parkedWhere the parking spot of the flight e.g. G1
     * @param currentState the current state of the flight
     * @param departureTime the initial departure time
     */
    public PopUpFlights(String flightId, String cityOfDeparture, String flightType, String planeType, String parkedWhere, String currentState, String departureTime) {
        this.flightId = new SimpleStringProperty(flightId);
        this.cityOfDeparture = new SimpleStringProperty(cityOfDeparture);
        this.flightType = new SimpleStringProperty(flightType);
        this.planeType = new SimpleStringProperty(planeType);
        this.parkedWhere = new SimpleStringProperty(parkedWhere);
        this.currentState = new SimpleStringProperty(currentState);
        this.departureTime = new SimpleStringProperty(departureTime);
    }


    public String getFlightId() {
        return flightId.get();
    }
    public void setFlightId(String id) {
        flightId.set(id);
    }

    public String getCityOfDeparture() {
        return cityOfDeparture.get();
    }
    public void setCityOfDeparture(String city) {
        cityOfDeparture.set(city);
    }

    public String getFlightType() {
        return flightType.get();
    }
    public void setFlightType(String type) {
        flightType.set(type);
    }

    public String getPlaneType() {
        return planeType.get();
    }
    public void setPlaneType(String pltype) {
        planeType.set(pltype);
    }

    public String getCurrentState() {
        return currentState.get();
    }
    public void setCurrentState(String state) {
        currentState.set(state);
    }

    public String getParkedWhere() {
        return parkedWhere.get();
    }
    public void setParkedWhere(String where) {
        parkedWhere.set(where);
    }

    public String getDepartureTime() {
        return departureTime.get();
    }
    public void setDepartureTime(String time) {
        departureTime.set(time);
    }

    /** Makes the table that fills the popup with the right data
     *
     * @param flightLedger All our flights are stored here
     * @return a table with the all the flights in the flightLedger
     */
    public static TableView<PopUpFlights> setFlightsForPopUp(List<List<String>> flightLedger) {

        TableView<PopUpFlights> table = new TableView<>();

        ObservableList<PopUpFlights> data = FXCollections.observableArrayList();

        for (List<String> list : flightLedger) {
            data.add(new PopUpFlights(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6)));
        }

        TableColumn flightIdCol = new TableColumn("Flight Id");
        flightIdCol.setMinWidth(100);
        flightIdCol.setCellValueFactory(
                new PropertyValueFactory<PopUpFlights, String>("flightId"));

        TableColumn cityOfDepartureCol = new TableColumn("Departure City");
        cityOfDepartureCol.setMinWidth(100);
        cityOfDepartureCol.setCellValueFactory(
                new PropertyValueFactory<PopUpFlights, String>("cityOfDeparture"));

        TableColumn flightTypeCol = new TableColumn("Flight Type");
        flightTypeCol.setMinWidth(100);
        flightTypeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpFlights, String>("flightType"));

        TableColumn planeTypeCol = new TableColumn("Plane Type");
        planeTypeCol.setMinWidth(100);
        planeTypeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpFlights, String>("planeType"));

        TableColumn parkedWhereCol = new TableColumn("Parking Spot");
        parkedWhereCol.setMinWidth(100);
        parkedWhereCol.setCellValueFactory(
                new PropertyValueFactory<PopUpFlights, String>("parkedWhere"));

        TableColumn currentStateCol = new TableColumn("Current State");
        currentStateCol.setMinWidth(100);
        currentStateCol.setCellValueFactory(
                new PropertyValueFactory<PopUpFlights, String>("currentState"));

        TableColumn departureTimeCol = new TableColumn("Departure Time");
        departureTimeCol.setMinWidth(100);
        departureTimeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpFlights, String>("departureTime"));

        table.setItems(data);
        table.getColumns().addAll(flightIdCol, cityOfDepartureCol, flightTypeCol, planeTypeCol, parkedWhereCol, currentStateCol, departureTimeCol);

        return table;
    }

}