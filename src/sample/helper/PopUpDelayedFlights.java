package sample.helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PopUpDelayedFlights {
    private  SimpleStringProperty whereParked;
    private SimpleStringProperty flightId;
    private SimpleStringProperty flightType;
    private SimpleStringProperty planeType;
    private SimpleStringProperty initialDepartureTime;

    /**
     *
     * @param whereParked the spot that the flight has parked e.g. G1
     * @param flightId the id of the flight
     * @param flightType the type of the flight
     * @param planeType the type of the plane
     * @param initialDepartureTime the initial departure time
     */
    public PopUpDelayedFlights(String whereParked, String flightId, String flightType, String planeType, String initialDepartureTime) {
        this.whereParked = new SimpleStringProperty(whereParked);
        this.flightId = new SimpleStringProperty(flightId);
        this.flightType = new SimpleStringProperty(flightType);
        this.planeType = new SimpleStringProperty(planeType);
        this.initialDepartureTime = new SimpleStringProperty(initialDepartureTime);
    }


    public String getWhereParked() {
        return whereParked.get();
    }
    public void setWhereParked(String where) {
        whereParked.set(where);
    }

    public String getFlightId() {
        return flightId.get();
    }
    public void setFlightId(String id) {
        flightId.set(id);
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

    public String getInitialDepartureTime() {
        return initialDepartureTime.get();
    }
    public void setInitialDepartureTime(String time) {
        initialDepartureTime.set(time);
    }

    /** Makes the table that fills the popup with the right data
     *
     * @param flightLedger all our flights are stored here
     * @return the table with the delayed flights
     */
    public static TableView<PopUpDelayedFlights> setDelayedFlightsForPopUp(List<List<String>> flightLedger) {

        TableView<PopUpDelayedFlights> table = new TableView<>();

        ObservableList<PopUpDelayedFlights> data = FXCollections.observableArrayList();

        for (List<String> list : flightLedger) {
            data.add(new PopUpDelayedFlights(list.get(4), list.get(0), list.get(2), list.get(3), list.get(6)));
        }

        TableColumn whereParkedCol = new TableColumn("Where Parked");
        whereParkedCol.setMinWidth(100);
        whereParkedCol.setCellValueFactory(
                new PropertyValueFactory<PopUpDelayedFlights, String>("whereParked"));

        TableColumn flightIdCol = new TableColumn("Flight Id");
        flightIdCol.setMinWidth(100);
        flightIdCol.setCellValueFactory(
                new PropertyValueFactory<PopUpDelayedFlights, String>("flightId"));

        TableColumn flightTypeCol = new TableColumn("Flight Type");
        flightTypeCol.setMinWidth(100);
        flightTypeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpDelayedFlights, String>("flightType"));

        TableColumn planeTypeCol = new TableColumn("Plane Type");
        planeTypeCol.setMinWidth(100);
        planeTypeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpDelayedFlights, String>("planeType"));

        TableColumn initialDepartureTimeCol = new TableColumn("Initial Departure Time");
        initialDepartureTimeCol.setMinWidth(100);
        initialDepartureTimeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpDelayedFlights, String>("initialDepartureTime"));

        table.setItems(data);
        table.getColumns().addAll(whereParkedCol, flightIdCol, flightTypeCol, planeTypeCol, initialDepartureTimeCol);

        return table;
    }

}