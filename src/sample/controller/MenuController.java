package sample.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import sample.Main;

public class MenuController extends Main {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label medialabLabel;

    @FXML
    private Label gate;

    @FXML
    private Label freight_gate;

    @FXML
    private Label general;

    @FXML
    private Label zone_a;

    @FXML
    private Label zone_c;

    @FXML
    private Label long_term;

    @FXML
    private Label zone_b;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu applicationMenu;

    @FXML
    private MenuItem startMenuItem;

    @FXML
    private MenuItem loadMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    private List<List<String>> startupAirport = new ArrayList<>();

    @FXML
    void initialize() {
        //start menu item which reads a txt file.
        startMenuItem.setOnAction(actionEvent -> {
            //read the file///////////////////////////////////////////////
            try (BufferedReader br = new BufferedReader(new FileReader("airport_default.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    startupAirport.add(Arrays.asList(values));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ////////////////////////////////////////////////////////////


            //time to initialize our parking spots
            for (List<String> strings : startupAirport) {
                if (strings.get(0).equals("1")) {
                    gate.setText("Gate:" + "- / " + strings.get(1));
                } else if (strings.get(0).equals("2")) {
                    freight_gate.setText("Freight Gate:" + "- / " + strings.get(1));
                } else if (strings.get(0).equals("3")) {
                    zone_a.setText("Zone A:" + "- / " + strings.get(1));
                } else if (strings.get(0).equals("4")) {
                    zone_b.setText("Zone B:" + "- / " + strings.get(1));
                } else if (strings.get(0).equals("5")) {
                    zone_c.setText("Zone C:" + "- / " + strings.get(1));
                } else if (strings.get(0).equals("6")) {
                    general.setText("General Parking Space:" + "- / " + strings.get(1));
                } else if (strings.get(0).equals("7")) {
                    long_term.setText("Long Term:" + "- / " + strings.get(1));
                }
            }

        });

        loadMenuItem.setOnAction(actionEvent -> {
            System.out.println("Hello load");
        });

        exitMenuItem.setOnAction(actionEvent -> {
            Stage stage = (Stage) medialabLabel.getScene().getWindow();
            stage.close();
        });
    }
}