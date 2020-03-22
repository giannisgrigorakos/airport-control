package sample.helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PopUpNextDepartures {
    private SimpleStringProperty flightId;
    private SimpleStringProperty flightType;
    private SimpleStringProperty planeType;

    public PopUpNextDepartures(String flightId, String flightType, String planeType) {
        this.flightId = new SimpleStringProperty(flightId);
        this.flightType = new SimpleStringProperty(flightType);
        this.planeType = new SimpleStringProperty(planeType);
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


    public static TableView<PopUpNextDepartures> setNextDeparturesForPopUp(List<List<String>> flightLedger) {

        TableView<PopUpNextDepartures> table = new TableView<>();

        ObservableList<PopUpNextDepartures> data = FXCollections.observableArrayList();

        for (List<String> list : flightLedger) {
            data.add(new PopUpNextDepartures(list.get(0), list.get(2), list.get(3)));
        }

        TableColumn flightIdCol = new TableColumn("Flight Id");
        flightIdCol.setMinWidth(100);
        flightIdCol.setCellValueFactory(
                new PropertyValueFactory<PopUpNextDepartures, String>("flightId"));

        TableColumn flightTypeCol = new TableColumn("Flight Type");
        flightTypeCol.setMinWidth(100);
        flightTypeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpNextDepartures, String>("flightType"));

        TableColumn planeTypeCol = new TableColumn("Plane Type");
        planeTypeCol.setMinWidth(100);
        planeTypeCol.setCellValueFactory(
                new PropertyValueFactory<PopUpNextDepartures, String>("planeType"));

        table.setItems(data);
        table.getColumns().addAll(flightIdCol, flightTypeCol, planeTypeCol);

        return table;
    }

}