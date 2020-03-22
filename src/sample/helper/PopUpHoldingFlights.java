package sample.helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PopUpHoldingFlights {
    private SimpleStringProperty flightId;
    private SimpleStringProperty flightType;
    private SimpleStringProperty planeType;
    private SimpleStringProperty firstContact;

    public PopUpHoldingFlights(String flightId, String flightType, String planeType, String firstContact) {
        this.flightId = new SimpleStringProperty(flightId);
        this.flightType = new SimpleStringProperty(flightType);
        this.planeType = new SimpleStringProperty(planeType);
        this.firstContact = new SimpleStringProperty(firstContact);
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

    public String getFirstContact() {
        return firstContact.get();
    }
    public void setFirstContact(String time) {
        firstContact.set(time);
    }


    public static TableView<PopUpHoldingFlights> setHoldingFlightsForPopUp(List<List<String>> flightLedger) {

        TableView<PopUpHoldingFlights> table = new TableView<>();

        ObservableList<PopUpHoldingFlights> data = FXCollections.observableArrayList();

        for (List<String> list : flightLedger) {
            data.add(new PopUpHoldingFlights(list.get(0), list.get(2), list.get(3), list.get(7)));
        }

        TableColumn flightIdCol = new TableColumn("Flight Id");
        flightIdCol.setMinWidth(100);
        flightIdCol.setCellValueFactory(
                new PropertyValueFactory<PopUpHoldingFlights, String>("flightId"));

        TableColumn flightTypeCol = new TableColumn("Flight Type");
        flightTypeCol.setMinWidth(100);
        flightTypeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpHoldingFlights, String>("flightType"));

        TableColumn planeTypeCol = new TableColumn("Plane Type");
        planeTypeCol.setMinWidth(100);
        planeTypeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpHoldingFlights, String>("planeType"));

        TableColumn firstContactCol = new TableColumn("First Contact Time");
        firstContactCol.setMinWidth(100);
        firstContactCol.setCellValueFactory(
                new PropertyValueFactory<PopUpHoldingFlights, String>("firstContact"));

        table.setItems(data);
        table.getColumns().addAll(flightIdCol, flightTypeCol, planeTypeCol, firstContactCol);

        return table;
    }

}