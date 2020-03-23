package sample.helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PopUpGates {
    private SimpleStringProperty gate;
    private SimpleStringProperty gateId;
    private SimpleStringProperty state;
    private SimpleStringProperty flightIds;
    private SimpleStringProperty departures;

    /** Constructor tof this class
     *
     * @param gate the name of the gate
     * @param gateId the gate id
     * @param state the current state of the gate
     * @param flightIds the ids of the flight that this gate hosts
     * @param departures the departure times of the flights this gate hosts
     */
    public PopUpGates(String gate, String gateId, String state, String flightIds, String departures) {
        this.gate = new SimpleStringProperty(gate);
        this.gateId = new SimpleStringProperty(gateId);
        this.state = new SimpleStringProperty(state);
        this.flightIds = new SimpleStringProperty(flightIds);
        this.departures = new SimpleStringProperty(departures);
    }


    public String getGate() {
        return gate.get();
    }
    public void setGate(String currentGate) {
        gate.set(currentGate);
    }

    public String getGateId() {
        return gateId.get();
    }
    public void setGateId(String id) {
        gateId.set(id);
    }

    public String getState() {
        return state.get();
    }
    public void setState(String st) {
        state.set(st);
    }

    public String getFlightIds() {
        return flightIds.get();
    }
    public void setFlightIds(String ids) {
        flightIds.set(ids);
    }
    public String getDepartures() {
        return departures.get();
    }
    public void setDepartures(String dps) {
        departures.set(dps);
    }

    /** Makes the table that fills the popup with the right data
     *
     * @param gate an array of strings for all the data of the gate
     * @param freightGate an array of strings for all the data of the freight gate
     * @param zoneA an array of strings for all the data of zone A
     * @param zoneB an array of strings for all the data of zone B
     * @param zoneC an array of strings for all the data of zone C
     * @param general an array of strings for all the data of the general parking space
     * @param longTerm an array of strings for all the data of the long term gate
     * @return the table with the all the above data
     */
    public static TableView<PopUpGates> setGatesForPopUp(String[] gate, String[] freightGate, String[] zoneA, String[] zoneB, String[] zoneC, String[] general, String[] longTerm) {

        TableView<PopUpGates> table = new TableView<>();

        ObservableList<PopUpGates> data =
                FXCollections.observableArrayList(
                        new PopUpGates(gate[0], gate[1], gate[2], gate[3], gate[4]),
                        new PopUpGates(freightGate[0], freightGate[1], freightGate[2], freightGate[3], freightGate[4]),
                        new PopUpGates(zoneA[0], zoneA[1], zoneA[2], zoneA[3], zoneA[4]),
                        new PopUpGates(zoneB[0],zoneB[1], zoneB[2], zoneB[3], zoneB[4]),
                        new PopUpGates(zoneC[0],zoneC[1], zoneC[2], zoneC[3], zoneC[4]),
                        new PopUpGates(general[0],general[1], general[2], general[3], general[4]),
                        new PopUpGates(longTerm[0],longTerm[1], longTerm[2], longTerm[3], longTerm[4])
                );

        TableColumn gateCol = new TableColumn("Gate");
        gateCol.setMinWidth(100);
        gateCol.setCellValueFactory(
                new PropertyValueFactory<PopUpGates, String>("gate"));

        TableColumn gateIdCol = new TableColumn("Gate Id");
        gateIdCol.setMinWidth(100);
        gateIdCol.setCellValueFactory(
                new PropertyValueFactory<PopUpGates, String>("gateId"));

        TableColumn stateCol = new TableColumn("State");
        stateCol.setMinWidth(100);
        stateCol.setCellValueFactory(
                new PropertyValueFactory<PopUpGates, String>("state"));

        TableColumn flightIdsCol = new TableColumn("Flight Ids");
        flightIdsCol.setMinWidth(100);
        flightIdsCol.setCellValueFactory(
                new PropertyValueFactory<PopUpGates, String>("flightIds"));

        TableColumn departuresCol = new TableColumn("Departure Times");
        departuresCol.setMinWidth(100);
        departuresCol.setCellValueFactory(
                new PropertyValueFactory<PopUpGates, String>("departures"));

        table.setItems(data);
        table.getColumns().addAll(gateCol, gateIdCol, stateCol, flightIdsCol, departuresCol);

        return table;
    }

}