package sample.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.helper.Constants;

public class MenuController extends Main {

    @FXML
    private Label medialabLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private Label gate;

    @FXML
    private Label freightGate;

    @FXML
    private Label general;

    @FXML
    private Label zoneA;

    @FXML
    private Label zoneC;

    @FXML
    private Label longTerm;

    @FXML
    private Label zoneB;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField newFlight;

    @FXML
    private JFXButton addFlight;

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

    @FXML
    private Menu detailsMenu;

    @FXML
    private MenuItem gates;

    @FXML
    private MenuItem flights;

    @FXML
    private MenuItem delayed;

    @FXML
    private MenuItem holding;

    @FXML
    private MenuItem nextDepartures;

    private List<List<String>> startupAirportGates = new ArrayList<>();
    private List<List<String>> startupAirportFlights = new ArrayList<>();

    private int seconds = 0;
    private int hours;
    private Constants consts;

    @FXML
    void initialize() {
        //start menu item which reads a txt file for the airports' gates and flights.
        startMenuItem.setOnAction(actionEvent -> {
            //initialize consts
             consts = new Constants();
            //initialize timer/////////////////////////////////////////////
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(100),
                    ae -> increaseTimer()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            //read the files///////////////////////////////////////////////
            try (BufferedReader br = new BufferedReader(new FileReader("airport_default.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    startupAirportGates.add(Arrays.asList(values));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedReader br = new BufferedReader(new FileReader("setup_default.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    startupAirportFlights.add(Arrays.asList(values));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }





            ////////////////////////////////////////////////////////////


            //time to initialize our parking spots
            for (List<String> strings : startupAirportGates) {
                if (strings.get(0).equals("1")) {
                    gate.setText("Gate:" + "- / " + strings.get(1));
                    consts.gateMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.gateCurrentCapacity = 0;
                    consts.gateCost = Integer.parseInt(strings.get(2));
                    consts.gateIdentifier = strings.get(3);
                } else if (strings.get(0).equals("2")) {
                    freightGate.setText("Freight Gate:" + "- / " + strings.get(1));
                    consts.freightGateMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.freightGateCurrentCapacity = 0;
                    consts.freightGateCost = Integer.parseInt(strings.get(2));
                    consts.freightGateIdentifier = strings.get(3);
                } else if (strings.get(0).equals("3")) {
                    zoneA.setText("Zone A:" + "- / " + strings.get(1));
                    consts.zoneAMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.zoneACurrentCapacity = 0;
                    consts.zoneACost = Integer.parseInt(strings.get(2));
                    consts.zoneAIdentifier = strings.get(3);
                } else if (strings.get(0).equals("4")) {
                    zoneB.setText("Zone B:" + "- / " + strings.get(1));
                    consts.zoneBMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.zoneBCurrentCapacity = 0;
                    consts.zoneBCost = Integer.parseInt(strings.get(2));
                    consts.zoneBIdentifier = strings.get(3);
                } else if (strings.get(0).equals("5")) {
                    zoneC.setText("Zone C:" + "- / " + strings.get(1));
                    consts.zoneCMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.zoneCCurrentCapacity = 0;
                    consts.zoneCCost = Integer.parseInt(strings.get(2));
                    consts.zoneCIdentifier = strings.get(3);
                } else if (strings.get(0).equals("6")) {
                    general.setText("General Parking Space:" + "- / " + strings.get(1));
                    consts.generalMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.generalCurrentCapacity = 0;
                    consts.generalCost = Integer.parseInt(strings.get(2));
                    consts.generalIdentifier = strings.get(3);
                } else if (strings.get(0).equals("7")) {
                    longTerm.setText("Long Term:" + "- / " + strings.get(1));
                    consts.longTermMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.longTermCurrentCapacity = 0;
                    consts.longTermCost = Integer.parseInt(strings.get(2));
                    consts.longTermIdentifier = strings.get(3);
                }
            }

            //lets initialize our parking spots with flights already on the ground
            for (List<String> strings : startupAirportFlights) {
                insertFlight(strings);
            }

        });

        loadMenuItem.setOnAction(actionEvent -> {
            System.out.println("Hello load");
            showNow();
        });

        exitMenuItem.setOnAction(actionEvent -> {
            Stage stage = (Stage) medialabLabel.getScene().getWindow();
            stage.close();
        });

        gates.setOnAction(actionEvent -> {
            showNow();
        });

        //Add a new flight
        addFlight.setOnAction(actionEvent -> {
            String[] text = newFlight.getText().split(",");
            // step two : convert String array to list of String
            List<String> fixedLengthList = Arrays.asList(text);
            // step three : copy fixed list to an ArrayList
            ArrayList<String> flight = new ArrayList<String>(fixedLengthList);
            System.out.println(flight);
            insertFlight(flight);
        });
    }

    public void increaseTimer() {
        System.out.println(this.seconds);
        this.seconds++;
        this.hours = this.seconds/3600;
        timerLabel.setText("Total TIme Ellapsed  " + this.hours + " : " + (this.seconds-(3600*this.hours))/60);
        checkForDepartures();
    }

    public void checkForDepartures() {

        // Getting an individual iterator foe every gate
        Iterator hmIterator;
        hmIterator = consts.gateFlights.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            if(mapElement.getValue().equals(this.seconds)) {
                consts.gateFlights.remove(mapElement.getKey());
                consts.gateCurrentCapacity--;
                gate.setText("Gate: " + consts.gateCurrentCapacity + " / " + consts.gateMaxCapacity);
            }
        }

        hmIterator = consts.freightGateFlights.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            if(mapElement.getValue().equals(this.seconds)) {
                consts.freightGateFlights.remove(mapElement.getKey());
                consts.freightGateCurrentCapacity--;
                freightGate.setText("Freight Gate: " + consts.freightGateCurrentCapacity + " / " + consts.freightGateMaxCapacity);
            }
        }

        hmIterator = consts.zoneAFlights.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            if(mapElement.getValue().equals(this.seconds)) {
                consts.zoneAFlights.remove(mapElement.getKey());
                consts.zoneACurrentCapacity--;
                zoneA.setText("Zone A: " + consts.zoneACurrentCapacity + " / " + consts.zoneAMaxCapacity);
            }
        }

        hmIterator = consts.zoneBFlights.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            if(mapElement.getValue().equals(this.seconds)) {
                consts.zoneBFlights.remove(mapElement.getKey());
                consts.zoneBCurrentCapacity--;
                zoneB.setText("Zone B: " + consts.zoneBCurrentCapacity + " / " + consts.zoneBMaxCapacity);
            }
        }

        hmIterator = consts.zoneCFlights.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            if(mapElement.getValue().equals(this.seconds)) {
                consts.zoneCFlights.remove(mapElement.getKey());
                consts.zoneCCurrentCapacity--;
                zoneC.setText("Zone C: " + consts.zoneCCurrentCapacity + " / " + consts.zoneCMaxCapacity);
            }
        }

        hmIterator = consts.generalFlights.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            if(mapElement.getValue().equals(this.seconds)) {
                consts.generalFlights.remove(mapElement.getKey());
                consts.generalCurrentCapacity--;
                general.setText("General Parking Space: " + consts.generalCurrentCapacity + " / " + consts.generalMaxCapacity);
            }
        }

        hmIterator = consts.longTermFlights.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            if(mapElement.getValue().equals(this.seconds)) {
                consts.longTermFlights.remove(mapElement.getKey());
                consts.longTermCurrentCapacity--;
                longTerm.setText("Long Term: " + consts.longTermCurrentCapacity + " / " + consts.longTermMaxCapacity);
            }
        }
    }

    public void insertFlight(List<String> flight) {
        Boolean hasParked = false;
        String flight_id = flight.get(0).trim();
        String flightType = flight.get(2).trim();
        String planeType = flight.get(3).trim();
        int landingTime = Integer.parseInt(flight.get(4).trim());

        if(consts.gateCurrentCapacity < consts.gateMaxCapacity && !hasParked) {
            if(!consts.gateFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.gateAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.gateMaxTime >= landingTime) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.gateCurrentCapacity++;
                        consts.gateDockTimes.add(this.seconds+landingTime);
                        gate.setText("Gate: " + consts.gateCurrentCapacity + " / " + consts.gateMaxCapacity);
                        consts.gateFlights.put(flight_id, this.seconds+landingTime);
                        System.out.println(consts.gateFlights);
                    }
                }
            }
        }
        if(consts.freightGateCurrentCapacity < consts.freightGateMaxCapacity && !hasParked) {
            if(!consts.freightGateFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.freightGateAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.freightGateMaxTime >= landingTime) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.freightGateCurrentCapacity++;
                        consts.freightGateDockTimes.add(this.seconds+landingTime);
                        freightGate.setText("Freight Gate: " + consts.freightGateCurrentCapacity + " / " + consts.freightGateMaxCapacity);
                        consts.freightGateFlights.put(flight_id, this.seconds+landingTime);
                    }
                }
            }
        }
        if(consts.zoneACurrentCapacity < consts.zoneAMaxCapacity && !hasParked) {
            if(!consts.zoneAFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.zoneAAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.zoneAMaxTime >= landingTime) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.zoneACurrentCapacity++;
                        consts.zoneADockTimes.add(this.seconds+landingTime);
                        zoneA.setText("Zone A: " + consts.zoneACurrentCapacity + " / " + consts.zoneAMaxCapacity);
                        consts.zoneAFlights.put(flight_id, this.seconds+landingTime);
                    }
                }
            }
        }
        if(consts.zoneBCurrentCapacity < consts.zoneBMaxCapacity && !hasParked) {
            if(!consts.zoneBFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.zoneBAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.zoneBMaxTime >= landingTime) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.zoneBCurrentCapacity++;
                        consts.zoneBDockTimes.add(this.seconds+landingTime);
                        zoneB.setText("Zone B: " + consts.zoneBCurrentCapacity + " / " + consts.zoneBMaxCapacity);
                        consts.zoneBFlights.put(flight_id, this.seconds+landingTime);
                    }
                }
            }
        }
        if(consts.zoneCCurrentCapacity < consts.zoneCMaxCapacity && !hasParked) {
            if(!consts.zoneCFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.zoneCAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.zoneCMaxTime >= landingTime) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.zoneCCurrentCapacity++;
                        consts.zoneCDockTimes.add(this.seconds+landingTime);
                        zoneC.setText("Zone C: " + consts.zoneCCurrentCapacity + " / " + consts.zoneCMaxCapacity);
                        consts.zoneCFlights.put(flight_id, this.seconds+landingTime);
                    }
                }
            }
        }
        if(consts.generalCurrentCapacity < consts.generalMaxCapacity && !hasParked) {
            if(!consts.generalFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.generalAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.generalMaxTime >= landingTime) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.generalCurrentCapacity++;
                        consts.generalDockTimes.add(this.seconds+landingTime);
                        general.setText("General Parking Space: " + consts.generalCurrentCapacity + " / " + consts.generalMaxCapacity);
                        consts.generalFlights.put(flight_id, this.seconds+landingTime);
                    }
                }
            }
        }
        if(consts.longTermCurrentCapacity < consts.longTermMaxCapacity && !hasParked) {
            if(!consts.longTermFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.longTermAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.longTermMaxTime >= landingTime) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.longTermCurrentCapacity++;
                        consts.longTermDockTimes.add(this.seconds+landingTime);
                        longTerm.setText("Long Term: " + consts.longTermCurrentCapacity + " / " + consts.longTermMaxCapacity);
                        consts.longTermFlights.put(flight_id, this.seconds+landingTime);
                    }
                }
            }
        }
        if (!hasParked) System.out.println("Sorry mate no parking spot!");



    }


    public void showNow() {

        TableView table = new TableView<>();
        JFXDialog dialog = new JFXDialog(stackPane, table, JFXDialog.DialogTransition.TOP);
        dialog.show();
    }


}