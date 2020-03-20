package sample.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.helper.Constants;

public class MenuController extends Main {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton addFlight;

    @FXML
    private JFXTextField newFlight;

    @FXML
    private Label timerLabel;

    @FXML
    private Label medialabLabel;

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
    private MenuBar menuBar;

    @FXML
    private Menu applicationMenu;

    @FXML
    private MenuItem startMenuItem;

    @FXML
    private MenuItem loadMenuItem;

    @FXML
    private MenuItem exitMenuItem;

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
            //System.out.println(startupAirportFlights.get(0));


            ////////////////////////////////////////////////////////////


            //time to initialize our parking spots
            for (List<String> strings : startupAirportGates) {
                if (strings.get(0).equals("1")) {
                    gate.setText("Gate:" + "- / " + strings.get(1));
                    consts.gateMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.gateCurrentCapacity = 0;
                } else if (strings.get(0).equals("2")) {
                    freightGate.setText("Freight Gate:" + "- / " + strings.get(1));
                    consts.freightGateMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.freightGateCurrentCapacity = 0;
                } else if (strings.get(0).equals("3")) {
                    zoneA.setText("Zone A:" + "- / " + strings.get(1));
                    consts.zoneAMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.zoneACurrentCapacity = 0;
                } else if (strings.get(0).equals("4")) {
                    zoneB.setText("Zone B:" + "- / " + strings.get(1));
                    consts.zoneBMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.zoneBCurrentCapacity = 0;
                } else if (strings.get(0).equals("5")) {
                    zoneC.setText("Zone C:" + "- / " + strings.get(1));
                    consts.zoneCMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.zoneCCurrentCapacity = 0;
                } else if (strings.get(0).equals("6")) {
                    general.setText("General Parking Space:" + "- / " + strings.get(1));
                    consts.generalMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.generalCurrentCapacity = 0;
                } else if (strings.get(0).equals("7")) {
                    longTerm.setText("Long Term:" + "- / " + strings.get(1));
                    consts.longTermMaxCapacity = Integer.parseInt(strings.get(1));
                    consts.longTermCurrentCapacity = 0;
                }
            }

            //lets initialize our parking spots with flights already on the ground
            for (List<String> strings : startupAirportFlights) {
                insertFlight(strings);
            }

        });

        loadMenuItem.setOnAction(actionEvent -> {
            System.out.println("Hello load");
        });

        exitMenuItem.setOnAction(actionEvent -> {
            Stage stage = (Stage) medialabLabel.getScene().getWindow();
            stage.close();
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
        for (Integer temp : consts.gateDockTimes) {
            if (this.seconds == temp) {
                System.out.println("You need to leave");
            }
        }
        for (Integer temp : consts.freightGateDockTimes) {
            if (this.seconds == temp) {
                System.out.println("You need to leave");
            }        }
        for (Integer temp : consts.zoneADockTimes) {
            if (this.seconds == temp) {
                System.out.println("You need to leave");
            }        }
        for (Integer temp : consts.zoneBDockTimes) {
            if (this.seconds == temp) {
                System.out.println("You need to leave");
            }        }
        for (Integer temp : consts.zoneCDockTimes) {
            if (this.seconds == temp) {
                System.out.println("You need to leave");
            }        }
        for (Integer temp : consts.generalDockTimes) {
            if (this.seconds == temp) {
                System.out.println("You need to leave");
            }        }
        for (Integer temp : consts.longTermDockTimes) {
            if (this.seconds == temp) {
                System.out.println("You need to leave");
            }        }
    }

    public void insertFlight(List<String> flight) {
        Boolean hasParked = false;
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
                    }
                }
            }
        }
        if (!hasParked) System.out.println("Sorry mate no parking spot!");



    }
}