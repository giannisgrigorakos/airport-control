package sample.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.helper.*;

public class MainController extends Main {

    @FXML
    private Label medialabLabel;

    @FXML
    private Label arrivedFlightsLabel;

    @FXML
    private Label totalParkingSpaceLabel;

    @FXML
    private Label departingIn10MinutesLabel;

    @FXML
    private Label revenueLabel;

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
    private Label announcements;

    @FXML
    private StackPane errorStackPane;

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
    private Flights ledger = new Flights();
    Timeline timeline = new Timeline();


    private int minutes = 0;
    private int hours;
    private Gates consts = new Gates();
    private Random random = new Random();
    private int revenue = 0;

    @FXML
    void initialize() {
        //start menu item which reads a txt file for the airports' gates and flights.
        startMenuItem.setOnAction(actionEvent -> {
            //read the files///////////////////////////////////////////////
            try (BufferedReader br = new BufferedReader(new FileReader("airport_1.txt"))) {
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

            try (BufferedReader br = new BufferedReader(new FileReader("setup_1.txt"))) {
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
            clearAllExistingData();
            initializeTimer();
            startTheApp();
        });

        loadMenuItem.setOnAction(actionEvent -> {
            AtomicBoolean error = new AtomicBoolean(false);
            TextField text = new TextField(" Insert id and press enter ");
            JFXDialog dialog = new JFXDialog(errorStackPane, text, JFXDialog.DialogTransition.TOP);
            dialog.show();
            text.setOnAction(actionEvent1 -> {
                dialog.close();
                //read the files///////////////////////////////////////////////
                try (BufferedReader br = new BufferedReader(new FileReader("airport_"+ text.getText() +".txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        startupAirportGates.add(Arrays.asList(values));
                    }
                } catch (FileNotFoundException e) {
                    error.set(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (BufferedReader br = new BufferedReader(new FileReader("setup_"+ text.getText()+".txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        startupAirportFlights.add(Arrays.asList(values));
                    }
                } catch (FileNotFoundException e) {
                    error.set(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(!error.get()) {
                    clearAllExistingData();
                    initializeTimer();
                    startTheApp();
                }
                else {
                    JFXDialog errorDialog = new JFXDialog(errorStackPane, new Label(" No such file "), JFXDialog.DialogTransition.TOP);
                    errorDialog.show();
                }


            });
        });

        exitMenuItem.setOnAction(actionEvent -> {
            Stage stage = (Stage) medialabLabel.getScene().getWindow();
            stage.close();
        });

        gates.setOnAction(actionEvent -> buttonShowGates());

        flights.setOnAction(actionEvent -> buttonShowFlights());

        nextDepartures.setOnAction(actionEvent -> buttonShowNextDepartures());

        holding.setOnAction(actionEvent -> buttonShowHoldingFlights());

        delayed.setOnAction(actionEvent -> buttonShowDelayedFlights());


        //Add a new flight
        addFlight.setOnAction(actionEvent -> {
            String[] text = newFlight.getText().replaceAll("\\s+", "").split(",");
            // step two : convert String array to list of String
            List<String> fixedLengthList = Arrays.asList(text);
            // step three : copy fixed list to an ArrayList
            ArrayList<String> flight = new ArrayList<String>(fixedLengthList);
            if(flight.size() == 5) {
                if(flight.get(2).matches("[0-9]*") && Integer.parseInt(flight.get(2)) >= 1 && Integer.parseInt(flight.get(2)) <= 3) {
                    if(flight.get(3).matches("[0-9]*") && Integer.parseInt(flight.get(3)) >= 1 && Integer.parseInt(flight.get(3)) <= 3) {
                        if(flight.get(4).matches("[0-9]*") && Integer.parseInt(flight.get(4)) >= 0) {

                            insertFlight(flight, false);
                        }
                        else{
                            JFXDialog dialog = new JFXDialog(errorStackPane, new Label("Time must be a positive number"), JFXDialog.DialogTransition.TOP);
                            dialog.show();
                        }
                    }
                    else{
                        JFXDialog dialog = new JFXDialog(errorStackPane, new Label("Plane type must be 1,2 or 3"), JFXDialog.DialogTransition.TOP);
                        dialog.show();
                    }
                }
                else{
                    JFXDialog dialog = new JFXDialog(errorStackPane, new Label("Flight type must be 1,2 or 3"), JFXDialog.DialogTransition.TOP);
                    dialog.show();
                }
            }
            else{
                JFXDialog dialog = new JFXDialog(errorStackPane, new Label("Please provide a valid flight"), JFXDialog.DialogTransition.TOP);
                dialog.show();
            }
        });
    }

    /** Clears all existing data for a fresh new start
     *
     */
    private void clearAllExistingData() {
        this.timeline.stop();
        ledger.flightLedger.clear();
        arrivedFlightsLabel.setText("Arrived Flights: ");
        totalParkingSpaceLabel.setText("Total Parking Space: ");
        revenueLabel.setText("Total Revenue: ");
        departingIn10MinutesLabel.setText("No. of Flights Departing in 10 next minutes: ");
        timerLabel.setText("Total TIme Elapsed: ");
        this.minutes = 0;
        this.hours = 0;
        announcements.setText("");
        this.consts.reset();

    }

    /** Initialize the timer
     *
     */
    private void initializeTimer() {
        this.timeline = new Timeline(new KeyFrame(
                Duration.millis(5000),
                ae -> increaseTimer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    /** Start the appllication
     *
     */
    public void startTheApp() {
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
            insertFlight(strings, true);
        }
    }

    /** In every time pulse increase the timer and do various computations
     *
     */
    public void increaseTimer() {
        this.minutes++;
        this.hours = this.minutes /60;
        timerLabel.setText("Total Time Elapsed  " + (this.hours%24) + " : " + (this.minutes %60));
        checkForPendingFlights();
        checkForStateChanges();
        checkForDepartures();
        totalParkingSpaceLabel.setText("Total Parking Space: " + (consts.gateCurrentCapacity+consts.freightGateCurrentCapacity+consts.zoneACurrentCapacity+consts.zoneBCurrentCapacity+consts.zoneCCurrentCapacity+consts.generalCurrentCapacity+consts.longTermCurrentCapacity) + " / " + (consts.gateMaxCapacity+consts.freightGateMaxCapacity+consts.zoneAMaxCapacity+consts.zoneBMaxCapacity+consts.zoneCMaxCapacity+consts.generalMaxCapacity+consts.longTermMaxCapacity));
        departingIn10MinutesLabel.setText("No. of Flights Departing in 10 next minutes: " + next10MinDepartures());
        arrivedFlightsLabel.setText("Arrived Flights: " + ledger.flightLedger.size());
    }

    /** Check for departures
     *
     */
    public void checkForDepartures() {
        Iterator<List<String>> itr = ledger.flightLedger.iterator();
        while (itr.hasNext()) {
            List<String> flight = itr.next();
            if (flight.get(5).equals("Parked") && Integer.parseInt(flight.get(12)) == this.minutes) {
                if(this.minutes > Integer.parseInt(flight.get(6))){
                    announcements.setText("Flight with id: " + flight.get(0) + " has just departed later than expected.");
                }
                else if(this.minutes < Integer.parseInt(flight.get(6))) {
                    if(Integer.parseInt(flight.get(8))-Integer.parseInt(flight.get(14))>= 25) {
                        flight.set(13, Integer.toString((int) (Integer.parseInt(flight.get(13))*0.8)));
                    }
                    else if(Integer.parseInt(flight.get(8))-Integer.parseInt(flight.get(14))>= 10 && Integer.parseInt(flight.get(8))-Integer.parseInt(flight.get(14)) <=20) {
                        flight.set(13, Integer.toString((int) (Integer.parseInt(flight.get(13))*0.9)));

                    }
                    announcements.setText("Flight with id: " + flight.get(0) + " has just departed earlier than expected.");
                }
                else announcements.setText("Flight with id: " + flight.get(0) +" has just departed.");
                revenue += Integer.parseInt(flight.get(13));
                revenueLabel.setText("Total Revenue : " + revenue);
                itr.remove();
                if(flight.get(9).equals("gate")) {
                    consts.gateCurrentCapacity--;
                    gate.setText("Gate: " + consts.gateCurrentCapacity + " / " + consts.gateMaxCapacity);
                }
                else if (flight.get(9).equals("freightGate")) {
                    consts.freightGateCurrentCapacity--;
                    freightGate.setText("Freight Gate: " + consts.freightGateCurrentCapacity + " / " + consts.freightGateMaxCapacity);
                }
                else if (flight.get(9).equals("zoneA")) {
                    consts.zoneACurrentCapacity--;
                    zoneA.setText("Zone A: " + consts.zoneACurrentCapacity + " / " + consts.zoneAMaxCapacity);
                }
                else if (flight.get(9).equals("zoneB")) {
                    consts.zoneBCurrentCapacity--;
                    zoneB.setText("Zone B: " + consts.zoneBCurrentCapacity + " / " + consts.zoneBMaxCapacity);
                }
                else if (flight.get(9).equals("zoneC")) {
                    consts.zoneCCurrentCapacity--;
                    zoneC.setText("Zone C: " + consts.zoneCCurrentCapacity + " / " + consts.zoneCMaxCapacity);
                }
                else if (flight.get(9).equals("general")) {
                    consts.generalCurrentCapacity--;
                    general.setText("General Parking Space: " + consts.generalCurrentCapacity + " / " + consts.generalMaxCapacity);
                }
                else if (flight.get(9).equals("longTerm")) {
                    consts.longTermCurrentCapacity--;
                    longTerm.setText("Long Term: " + consts.longTermCurrentCapacity + " / " + consts.longTermMaxCapacity);
                }
            }
        }
    }

    /** Try to insert a flight
     *
     * @param flight A flight that is in the form of a list
     * @param firstTime A variable that check if the insertion of a flight happens during a fresh start
     *
     */
    public void insertFlight(List<String> flight, Boolean firstTime) {
        //we check for already registered flight
        for (List<String> list : ledger.flightLedger) {
            if (list.get(0).equals(flight.get(0))) {
                if(firstTime) announcements.setText("");
                else announcements.setText("Flight with id: " + flight.get(0) + " already exists. You can't enter a flight with an existing flight id");
                return;
            }
        }


        //initialize a new flight in order to add it in our ledger
        List<String> newFlight = new ArrayList<>();
        //flightId
        newFlight.add(flight.get(0).trim());
        //city
        newFlight.add(flight.get(1).trim());
        //flightType
        newFlight.add(flight.get(2).trim());
        //planeType
        newFlight.add(flight.get(3).trim());
        //whereParked
        newFlight.add("");
        //currentState
        newFlight.add("Holding");
        //departureTime
        newFlight.add("");
        //firstContact
        newFlight.add(Integer.toString(this.minutes));
        //initialTimeSpendLanded
        newFlight.add(flight.get(4).trim());
        //gateParked
        newFlight.add("");
        //timeToLand
        if(newFlight.get(3).equals("1"))
            newFlight.add("6");
        else if(newFlight.get(3).equals("2"))
            newFlight.add("4");
        else if(newFlight.get(3).equals("3"))
            newFlight.add("2");
        //timeItStartsLanding
        newFlight.add("");
        //actualTimeSpendLanded can have random value
        newFlight.add("");
        //costOfParking
        newFlight.add("");
        //a field to keep the random time for pricing reason
        newFlight.add("");


        int randomNum = random.nextInt(Integer.parseInt(newFlight.get(8))*2);
        Boolean hasParked = false;
        String flightType = flight.get(2).trim();
        String planeType = flight.get(3).trim();
        int landingTime = Integer.parseInt(flight.get(4).trim());

        if(consts.gateCurrentCapacity < consts.gateMaxCapacity && !hasParked) {
            if(!consts.gateFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.gateAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.gateMaxTime >= landingTime + Integer.parseInt(newFlight.get(10))) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.gateCurrentCapacity++;
                        gate.setText("Gate: " + consts.gateCurrentCapacity + " / " + consts.gateMaxCapacity);
                        //this is the parking spot e.g. G1 and we change the default value
                        newFlight.set(4, consts.gateIdentifier+consts.gateCurrentCapacity);
                        //we set the state of the flight and we change the default value
                        if(firstTime) newFlight.set(5, "Parked");
                        else newFlight.set(5, "Landing");
                        //we set the departure time and we change the default value
                        if(firstTime) newFlight.set(6, Integer.toString(this.minutes + landingTime));
                        else newFlight.set(6, Integer.toString(this.minutes + landingTime + Integer.parseInt(newFlight.get(10))));
                        //we set the gate which is parked
                        newFlight.set(9, "gate");
                        //we set the time that it begins to land
                        if(firstTime) newFlight.set(11, "");
                        else newFlight.set(11, Integer.toString(this.minutes));
                        //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                        if(firstTime) newFlight.set(12, Integer.toString(randomNum + this.minutes));
                        else newFlight.set(12 ,Integer.toString(randomNum +Integer.parseInt(newFlight.get(10)) + this.minutes));
                        //we add the cost of each gate
                        newFlight.set(13, Integer.toString(consts.gateCost));
                        //a field to keep the random time for pricing reason
                        newFlight.set(14, Integer.toString(randomNum));
                    }
                }
            }
        }
        if(consts.freightGateCurrentCapacity < consts.freightGateMaxCapacity && !hasParked) {
            if(!consts.freightGateFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.freightGateAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.freightGateMaxTime >= landingTime + Integer.parseInt(newFlight.get(10))) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.freightGateCurrentCapacity++;
                        freightGate.setText("Freight Gate: " + consts.freightGateCurrentCapacity + " / " + consts.freightGateMaxCapacity);
                        //this is the parking spot e.g. G1 and we change the default value
                        newFlight.set(4, consts.freightGateIdentifier+consts.freightGateCurrentCapacity);
                        //we set the state of the flight and we change the default value
                        if(firstTime) newFlight.set(5, "Parked");
                        else newFlight.set(5, "Landing");
                        //we set the departure time and we change the default value
                        if(firstTime) newFlight.set(6, Integer.toString(this.minutes + landingTime));
                        else newFlight.set(6, Integer.toString(this.minutes + landingTime + Integer.parseInt(newFlight.get(10))));
                        //we set the gate which is parked
                        newFlight.set(9, "freightGate");
                        //we set the time that it begins to land
                        if(firstTime) newFlight.set(11, "");
                        else newFlight.set(11, Integer.toString(this.minutes));
                        //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                        if(firstTime) newFlight.set(12, Integer.toString(randomNum + this.minutes));
                        else newFlight.set(12 ,Integer.toString(randomNum +Integer.parseInt(newFlight.get(10)) + this.minutes));
                        //we add the cost of each gate
                        newFlight.set(13, Integer.toString(consts.freightGateCost));
                        //a field to keep the random time for pricing reason
                        newFlight.set(14, Integer.toString(randomNum));
                    }
                }
            }
        }
        if(consts.zoneACurrentCapacity < consts.zoneAMaxCapacity && !hasParked) {
            if(!consts.zoneAFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.zoneAAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.zoneAMaxTime >= landingTime + Integer.parseInt(newFlight.get(10))) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.zoneACurrentCapacity++;
                        zoneA.setText("Zone A: " + consts.zoneACurrentCapacity + " / " + consts.zoneAMaxCapacity);
                        //this is the parking spot e.g. G1 and we change the default value
                        newFlight.set(4, consts.zoneAIdentifier+consts.zoneACurrentCapacity);
                        //we set the state of the flight and we change the default value
                        if(firstTime) newFlight.set(5, "Parked");
                        else newFlight.set(5, "Landing");
                        //we set the departure time and we change the default value
                        if(firstTime) newFlight.set(6, Integer.toString(this.minutes + landingTime));
                        else newFlight.set(6, Integer.toString(this.minutes + landingTime + Integer.parseInt(newFlight.get(10))));
                        //we set the gate which is parked
                        newFlight.set(9, "zoneA");
                        //we set the time that it begins to land
                        if(firstTime) newFlight.set(11, "");
                        else newFlight.set(11, Integer.toString(this.minutes));
                        //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                        if(firstTime) newFlight.set(12, Integer.toString(randomNum + this.minutes));
                        else newFlight.set(12 ,Integer.toString(randomNum +Integer.parseInt(newFlight.get(10)) + this.minutes));
                        //we add the cost of each gate
                        newFlight.set(13, Integer.toString(consts.zoneACost));
                        //a field to keep the random time for pricing reason
                        newFlight.set(14, Integer.toString(randomNum));
                    }
                }
            }
        }
        if(consts.zoneBCurrentCapacity < consts.zoneBMaxCapacity && !hasParked) {
            if(!consts.zoneBFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.zoneBAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.zoneBMaxTime >= landingTime + Integer.parseInt(newFlight.get(10))) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.zoneBCurrentCapacity++;
                        zoneB.setText("Zone B: " + consts.zoneBCurrentCapacity + " / " + consts.zoneBMaxCapacity);
                        //this is the parking spot e.g. G1 and we change the default value
                        newFlight.set(4, consts.zoneBIdentifier+consts.zoneBCurrentCapacity);
                        //we set the state of the flight and we change the default value
                        if(firstTime) newFlight.set(5, "Parked");
                        else newFlight.set(5, "Landing");
                        //we set the departure time and we change the default value
                        if(firstTime) newFlight.set(6, Integer.toString(this.minutes + landingTime));
                        else newFlight.set(6, Integer.toString(this.minutes + landingTime + Integer.parseInt(newFlight.get(10))));
                        //we set the gate which is parked
                        newFlight.set(9, "zoneB");
                        //we set the time that it begins to land
                        if(firstTime) newFlight.set(11, "");
                        else newFlight.set(11, Integer.toString(this.minutes));
                        //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                        if(firstTime) newFlight.set(12, Integer.toString(randomNum + this.minutes));
                        else newFlight.set(12 ,Integer.toString(randomNum +Integer.parseInt(newFlight.get(10)) + this.minutes));
                        //we add the cost of each gate
                        newFlight.set(13, Integer.toString(consts.zoneBCost));
                        //a field to keep the random time for pricing reason
                        newFlight.set(14, Integer.toString(randomNum));
                    }
                }
            }
        }
        if(consts.zoneCCurrentCapacity < consts.zoneCMaxCapacity && !hasParked) {
            if(!consts.zoneCFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.zoneCAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.zoneCMaxTime >= landingTime + Integer.parseInt(newFlight.get(10))) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.zoneCCurrentCapacity++;
                        zoneC.setText("Zone C: " + consts.zoneCCurrentCapacity + " / " + consts.zoneCMaxCapacity);
                        //this is the parking spot e.g. G1 and we change the default value
                        newFlight.set(4, consts.zoneCIdentifier+consts.zoneCCurrentCapacity);
                        //we set the state of the flight and we change the default value
                        if(firstTime) newFlight.set(5, "Parked");
                        else newFlight.set(5, "Landing");
                        //we set the departure time and we change the default value
                        if(firstTime) newFlight.set(6, Integer.toString(this.minutes + landingTime));
                        else newFlight.set(6, Integer.toString(this.minutes + landingTime + Integer.parseInt(newFlight.get(10))));
                        //we set the gate which is parked
                        newFlight.set(9, "zoneC");
                        //we set the time that it begins to land
                        if(firstTime) newFlight.set(11, "");
                        else newFlight.set(11, Integer.toString(this.minutes));
                        //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                        if(firstTime) newFlight.set(12, Integer.toString(randomNum + this.minutes));
                        else newFlight.set(12 ,Integer.toString(randomNum +Integer.parseInt(newFlight.get(10)) + this.minutes));
                        //we add the cost of each gate
                        newFlight.set(13, Integer.toString(consts.zoneCCost));
                        //a field to keep the random time for pricing reason
                        newFlight.set(14, Integer.toString(randomNum));
                    }
                }
            }
        }
        if(consts.generalCurrentCapacity < consts.generalMaxCapacity && !hasParked) {
            if(!consts.generalFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.generalAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.generalMaxTime >= landingTime + Integer.parseInt(newFlight.get(10))) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.generalCurrentCapacity++;
                        general.setText("General Parking Space: " + consts.generalCurrentCapacity + " / " + consts.generalMaxCapacity);
                        //this is the parking spot e.g. G1 and we change the default value
                        newFlight.set(4, consts.generalIdentifier+consts.generalCurrentCapacity);
                        //we set the state of the flight and we change the default value
                        if(firstTime) newFlight.set(5, "Parked");
                        else newFlight.set(5, "Landing");
                        //we set the departure time and we change the default value
                        if(firstTime) newFlight.set(6, Integer.toString(this.minutes + landingTime));
                        else newFlight.set(6, Integer.toString(this.minutes + landingTime + Integer.parseInt(newFlight.get(10))));
                        //we set the gate which is parked
                        newFlight.set(9, "general");
                        //we set the time that it begins to land
                        if(firstTime) newFlight.set(11, "");
                        else newFlight.set(11, Integer.toString(this.minutes));
                        //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                        if(firstTime) newFlight.set(12, Integer.toString(randomNum + this.minutes));
                        else newFlight.set(12 ,Integer.toString(randomNum +Integer.parseInt(newFlight.get(10)) + this.minutes));
                        //we add the cost of each gate
                        newFlight.set(13, Integer.toString(consts.generalCost));
                        //a field to keep the random time for pricing reason
                        newFlight.set(14, Integer.toString(randomNum));
                    }
                }
            }
        }
        if(consts.longTermCurrentCapacity < consts.longTermMaxCapacity && !hasParked) {
            if(!consts.longTermFlightType[Integer.parseInt(flightType)-1].equals("-")) {
                if(!consts.longTermAirplaneType[Integer.parseInt(planeType)-1].equals("-")) {
                    if(consts.longTermMaxTime >= landingTime + Integer.parseInt(newFlight.get(10))) {
                        //finally empty appropriate spot
                        hasParked = true;
                        consts.longTermCurrentCapacity++;
                        longTerm.setText("Long Term: " + consts.longTermCurrentCapacity + " / " + consts.longTermMaxCapacity);
                        //this is the parking spot e.g. G1 and we change the default value
                        newFlight.set(4, consts.longTermIdentifier+consts.longTermCurrentCapacity);
                        //we set the state of the flight and we change the default value
                        if(firstTime) newFlight.set(5, "Parked");
                        else newFlight.set(5, "Landing");
                        //we set the departure time and we change the default value
                        if(firstTime) newFlight.set(6, Integer.toString(this.minutes + landingTime));
                        else newFlight.set(6, Integer.toString(this.minutes + landingTime + Integer.parseInt(newFlight.get(10))));
                        //we set the gate which is parked
                        newFlight.set(9, "longTerm");
                        //we set the time that it begins to land
                        if(firstTime) newFlight.set(11, "");
                        else newFlight.set(11, Integer.toString(this.minutes));
                        //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                        if(firstTime) newFlight.set(12, Integer.toString(randomNum + this.minutes));
                        else newFlight.set(12 ,Integer.toString(randomNum +Integer.parseInt(newFlight.get(10)) + this.minutes));
                        //we add the cost of each gate
                        newFlight.set(13, Integer.toString(consts.longTermCost));
                        //a field to keep the random time for pricing reason
                        newFlight.set(14, Integer.toString(randomNum));
                    }
                }
            }
        }
        if (!hasParked) announcements.setText("All parking spot have been filled! Please wait...");
        else if(!firstTime) announcements.setText("Flight with id: " + newFlight.get(0) +" has just started landing.");
        ledger.flightLedger.add(newFlight);
    }

    /** Check for flights that have a status of "Holding" and try to insert them to the airport
     *
     */
    public void checkForPendingFlights() {
        for (List<String> flight : ledger.flightLedger) {
            if (!flight.get(5).equals("Holding")) continue;

            int randomNum = random.nextInt(Integer.parseInt(flight.get(8))*2);
            Boolean hasParked = false;
            if (consts.gateCurrentCapacity < consts.gateMaxCapacity && !hasParked) {
                if (!consts.gateFlightType[Integer.parseInt(flight.get(2)) - 1].equals("-")) {
                    if (!consts.gateAirplaneType[Integer.parseInt(flight.get(3)) - 1].equals("-")) {
                        if (consts.gateMaxTime >= Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))) {
                            //finally empty appropriate spot
                            hasParked = true;
                            consts.gateCurrentCapacity++;
                            gate.setText("Gate: " + consts.gateCurrentCapacity + " / " + consts.gateMaxCapacity);
                            //this is the parking spot e.g. G1 and we change the default value
                            flight.set(4, consts.gateIdentifier + consts.gateCurrentCapacity);
                            //we set the state of the flight and we change the default value
                            flight.set(5, "Landing");
                            //we set the departure time and we change the default value
                            flight.set(6, Integer.toString(this.minutes + Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))));
                            //we set the gate which is parked
                            flight.set(9, "gate");
                            //we set the time that it begins to land
                            flight.set(11, Integer.toString(this.minutes));
                            //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                            flight.set(12 ,Integer.toString(randomNum + Integer.parseInt(flight.get(10)) + this.minutes));
                            //we add the cost of each gate
                            flight.set(13, Integer.toString(consts.gateCost));
                            //a field to keep the random time for pricing reason
                            flight.set(14, Integer.toString(randomNum));
                        }
                    }
                }
            }
            if (consts.freightGateCurrentCapacity < consts.freightGateMaxCapacity && !hasParked) {
                if (!consts.freightGateFlightType[Integer.parseInt(flight.get(2)) - 1].equals("-")) {
                    if (!consts.freightGateAirplaneType[Integer.parseInt(flight.get(3)) - 1].equals("-")) {
                        if (consts.freightGateMaxTime >= Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))) {
                            //finally empty appropriate spot
                            hasParked = true;
                            consts.freightGateCurrentCapacity++;
                            freightGate.setText("Gate: " + consts.freightGateCurrentCapacity + " / " + consts.freightGateMaxCapacity);
                            //this is the parking spot e.g. G1 and we change the default value
                            flight.set(4, consts.freightGateIdentifier + consts.freightGateCurrentCapacity);
                            //we set the state of the flight and we change the default value
                            flight.set(5, "Landing");
                            //we set the departure time and we change the default value
                            flight.set(6, Integer.toString(this.minutes + Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))));
                            //we set the gate which is parked
                            flight.set(9, "freightGate");
                            //we set the time that it begins to land
                            flight.set(11, Integer.toString(this.minutes));
                            //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                            flight.set(12 ,Integer.toString(randomNum + Integer.parseInt(flight.get(10)) + this.minutes));
                            //we add the cost of each gate
                            flight.set(13, Integer.toString(consts.freightGateCost));
                            //a field to keep the random time for pricing reason
                            flight.set(14, Integer.toString(randomNum));
                        }
                    }
                }
            }
            if (consts.zoneACurrentCapacity < consts.zoneAMaxCapacity && !hasParked) {
                if (!consts.zoneAFlightType[Integer.parseInt(flight.get(2)) - 1].equals("-")) {
                    if (!consts.zoneAAirplaneType[Integer.parseInt(flight.get(3)) - 1].equals("-")) {
                        if (consts.zoneAMaxTime >= Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))) {
                            //finally empty appropriate spot
                            hasParked = true;
                            consts.zoneACurrentCapacity++;
                            zoneA.setText("Gate: " + consts.zoneACurrentCapacity + " / " + consts.zoneAMaxCapacity);
                            //this is the parking spot e.g. G1 and we change the default value
                            flight.set(4, consts.zoneAIdentifier + consts.zoneACurrentCapacity);
                            //we set the state of the flight and we change the default value
                            flight.set(5, "Landing");
                            //we set the departure time and we change the default value
                            flight.set(6, Integer.toString(this.minutes + Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))));
                            //we set the gate which is parked
                            flight.set(9, "zoneA");
                            //we set the time that it begins to land
                            flight.set(11, Integer.toString(this.minutes));
                            //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                            flight.set(12 ,Integer.toString(randomNum + Integer.parseInt(flight.get(10)) + this.minutes));
                            //we add the cost of each gate
                            flight.set(13, Integer.toString(consts.zoneACost));
                            //a field to keep the random time for pricing reason
                            flight.set(14, Integer.toString(randomNum));
                        }
                    }
                }
            }
            if (consts.zoneBCurrentCapacity < consts.zoneBMaxCapacity && !hasParked) {
                if (!consts.zoneBFlightType[Integer.parseInt(flight.get(2)) - 1].equals("-")) {
                    if (!consts.zoneBAirplaneType[Integer.parseInt(flight.get(3)) - 1].equals("-")) {
                        if (consts.zoneBMaxTime >= Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))) {
                            //finally empty appropriate spot
                            hasParked = true;
                            consts.zoneBCurrentCapacity++;
                            zoneB.setText("Gate: " + consts.zoneBCurrentCapacity + " / " + consts.zoneBMaxCapacity);
                            //this is the parking spot e.g. G1 and we change the default value
                            flight.set(4, consts.zoneBIdentifier + consts.zoneBCurrentCapacity);
                            //we set the state of the flight and we change the default value
                            flight.set(5, "Landing");
                            //we set the departure time and we change the default value
                            flight.set(6, Integer.toString(this.minutes + Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))));
                            //we set the gate which is parked
                            flight.set(9, "zoneB");
                            //we set the time that it begins to land
                            flight.set(11, Integer.toString(this.minutes));
                            //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                            flight.set(12 ,Integer.toString(randomNum + Integer.parseInt(flight.get(10)) + this.minutes));
                            //we add the cost of each gate
                            flight.set(13, Integer.toString(consts.zoneBCost));
                            //a field to keep the random time for pricing reason
                            flight.set(14, Integer.toString(randomNum));
                        }
                    }
                }
            }
            if (consts.zoneCCurrentCapacity < consts.zoneCMaxCapacity && !hasParked) {
                if (!consts.zoneCFlightType[Integer.parseInt(flight.get(2)) - 1].equals("-")) {
                    if (!consts.zoneCAirplaneType[Integer.parseInt(flight.get(3)) - 1].equals("-")) {
                        if (consts.zoneCMaxTime >= Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))) {
                            //finally empty appropriate spot
                            hasParked = true;
                            consts.zoneCCurrentCapacity++;
                            zoneC.setText("Gate: " + consts.zoneCCurrentCapacity + " / " + consts.zoneCMaxCapacity);
                            //this is the parking spot e.g. G1 and we change the default value
                            flight.set(4, consts.zoneCIdentifier + consts.zoneCCurrentCapacity);
                            //we set the state of the flight and we change the default value
                            flight.set(5, "Landing");
                            //we set the departure time and we change the default value
                            flight.set(6, Integer.toString(this.minutes + Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))));
                            //we set the gate which is parked
                            flight.set(9, "zoneC");
                            //we set the time that it begins to land
                            flight.set(11, Integer.toString(this.minutes));
                            //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                            flight.set(12 ,Integer.toString(randomNum + Integer.parseInt(flight.get(10)) + this.minutes));
                            //we add the cost of each gate
                            flight.set(13, Integer.toString(consts.zoneCCost));
                            //a field to keep the random time for pricing reason
                            flight.set(14, Integer.toString(randomNum));
                        }
                    }
                }
            }
            if (consts.generalCurrentCapacity < consts.generalMaxCapacity && !hasParked) {
                if (!consts.generalFlightType[Integer.parseInt(flight.get(2)) - 1].equals("-")) {
                    if (!consts.generalAirplaneType[Integer.parseInt(flight.get(3)) - 1].equals("-")) {
                        if (consts.generalMaxTime >= Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))) {
                            //finally empty appropriate spot
                            hasParked = true;
                            consts.generalCurrentCapacity++;
                            general.setText("Gate: " + consts.generalCurrentCapacity + " / " + consts.generalMaxCapacity);
                            //this is the parking spot e.g. G1 and we change the default value
                            flight.set(4, consts.generalIdentifier + consts.generalCurrentCapacity);
                            //we set the state of the flight and we change the default value
                            flight.set(5, "Landing");
                            //we set the departure time and we change the default value
                            flight.set(6, Integer.toString(this.minutes + Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))));
                            //we set the gate which is parked
                            flight.set(9, "general");
                            //we set the time that it begins to land
                            flight.set(11, Integer.toString(this.minutes));
                            //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                            flight.set(12 ,Integer.toString(randomNum + Integer.parseInt(flight.get(10)) + this.minutes));
                            //we add the cost of each gate
                            flight.set(13, Integer.toString(consts.generalCost));
                            //a field to keep the random time for pricing reason
                            flight.set(14, Integer.toString(randomNum));
                        }
                    }
                }
            }
            if (consts.longTermCurrentCapacity < consts.longTermMaxCapacity && !hasParked) {
                if (!consts.longTermFlightType[Integer.parseInt(flight.get(2)) - 1].equals("-")) {
                    if (!consts.longTermAirplaneType[Integer.parseInt(flight.get(3)) - 1].equals("-")) {
                        if (consts.longTermMaxTime >= Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))) {
                            //finally empty appropriate spot
                            hasParked = true;
                            consts.longTermCurrentCapacity++;
                            longTerm.setText("Gate: " + consts.longTermCurrentCapacity + " / " + consts.longTermMaxCapacity);
                            //this is the parking spot e.g. G1 and we change the default value
                            flight.set(4, consts.longTermIdentifier + consts.longTermCurrentCapacity);
                            //we set the state of the flight and we change the default value
                            flight.set(5, "Landing");
                            //we set the departure time and we change the default value
                            flight.set(6, Integer.toString(this.minutes + Integer.parseInt(flight.get(8)) + Integer.parseInt(flight.get(10))));
                            //we set the gate which is parked
                            flight.set(9, "longTerm");
                            //we set the time that it begins to land
                            flight.set(11, Integer.toString(this.minutes));
                            //actualTimeSpendLanded can have random valued in range 0-2*initialTimeSpendLanded + the seconds it does in order to park
                            flight.set(12 ,Integer.toString(randomNum + Integer.parseInt(flight.get(10)) + this.minutes));
                            //we add the cost of each gate
                            flight.set(13, Integer.toString(consts.longTermCost));
                            //a field to keep the random time for pricing reason
                            flight.set(14, Integer.toString(randomNum));
                        }
                    }
                }
            }
            if (!hasParked) announcements.setText("All parking spot have been filled! Please wait...");
            else announcements.setText("Flight with id: " + flight.get(0) +" has just started landing.");
        }
    }

    /** Check for flight that have parked at the airport and change their status to parked
     *
     */
    public void checkForStateChanges() {
        for (List<String> flight : ledger.flightLedger) {
            if(flight.get(5).equals("Landing") && this.minutes == (Integer.parseInt(flight.get(11)) + Integer.parseInt(flight.get(10)))) {
                //we set the state of the flight to parked
                flight.set(5, "Parked");
                announcements.setText("Flight with id: " + flight.get(0) +" has parked safely.");
            }
        }
    }

    /** Show the airport's gates statuses when the Gates button is clicked
     *
     */
    public void buttonShowGates() {
        //we are going to traverse each gates hashmap in order to create strings of flight ids and departure times
        String gateFlightIds = "";
        String gateDepartures = "";
        String freightGateFlightIds = "";
        String freightGateDepartures = "";
        String zoneAFlightIds = "";
        String zoneADepartures = "";
        String zoneBFlightIds = "";
        String zoneBDepartures = "";
        String zoneCFlightIds = "";
        String zoneCDepartures = "";
        String generalFlightIds = "";
        String generalDepartures = "";
        String longTermFlightIds = "";
        String longTermDepartures = "";
        for (List<String> flight : ledger.flightLedger) {
            if(flight.get(9).equals("gate")){
                gateFlightIds += flight.get(0) + ",";
                gateDepartures += flight.get(6) + ",";
            }
            if(flight.get(9).equals("freightGate")){
                freightGateFlightIds += flight.get(0) + ",";
                freightGateDepartures += flight.get(6) + ",";
            }
            if(flight.get(9).equals("zoneA")){
                zoneAFlightIds += flight.get(0) + ",";
                zoneADepartures += flight.get(6) + ",";
            }
            if(flight.get(9).equals("zoneB")){
                zoneBFlightIds += flight.get(0) + ",";
                zoneBDepartures += flight.get(6) + ",";
            }
            if(flight.get(9).equals("zoneC")){
                zoneCFlightIds += flight.get(0) + ",";
                zoneCDepartures += flight.get(6) + ",";
            }
            if(flight.get(9).equals("general")){
                generalFlightIds += flight.get(0) + ",";
                generalDepartures += flight.get(6) + ",";
            }
            if(flight.get(9).equals("longTerm")){
                longTermFlightIds += flight.get(0) + ",";
                longTermDepartures += flight.get(6) + ",";
            }
        }
        String gate[] = {"gate" ,consts.gateIdentifier, consts.gateCurrentCapacity + "/" + consts.gateMaxCapacity, gateFlightIds, gateDepartures};
        String freightGate[] = {"Freight Gate" ,consts.freightGateIdentifier, consts.freightGateCurrentCapacity + "/" + consts.freightGateMaxCapacity, freightGateFlightIds, freightGateDepartures};
        String zoneA[] = {"Zone A" ,consts.zoneAIdentifier, consts.zoneACurrentCapacity + "/" + consts.zoneAMaxCapacity, zoneAFlightIds, zoneADepartures};
        String zoneB[] = {"Zone B" ,consts.zoneBIdentifier, consts.zoneBCurrentCapacity + "/" + consts.zoneBMaxCapacity, zoneBFlightIds, zoneBDepartures};
        String zoneC[] = {"Zone C" ,consts.zoneCIdentifier, consts.zoneCCurrentCapacity + "/" + consts.zoneCMaxCapacity, zoneCFlightIds, zoneCDepartures};
        String general[] = {"General Parking Space" ,consts.generalIdentifier, consts.generalCurrentCapacity + "/" + consts.generalMaxCapacity, generalFlightIds, generalDepartures};
        String longTerm[] = {"Long Term" ,consts.longTermIdentifier, consts.longTermCurrentCapacity + "/" + consts.longTermMaxCapacity, longTermFlightIds, longTermDepartures};

        TableView<PopUpGates> table = PopUpGates.setGatesForPopUp(gate, freightGate, zoneA, zoneB, zoneC, general, longTerm);
        JFXDialog dialog = new JFXDialog(stackPane, table, JFXDialog.DialogTransition.TOP);
        dialog.show();
    }


    /** Show the flights statuses when the Flights button is clicked
     *
     */
    private void buttonShowFlights() {
        TableView<PopUpFlights> table = PopUpFlights.setFlightsForPopUp(ledger.flightLedger);
        JFXDialog dialog = new JFXDialog(stackPane, table, JFXDialog.DialogTransition.TOP);
        dialog.show();
    }

    /** Show the delayed flights when the Flights button is clicked
     *
     */
    private void buttonShowDelayedFlights() {
        //we make a new list with only flights that have been delayed
        List<List<String>> delayedFlights = new ArrayList<>();

        for (List<String> flight : ledger.flightLedger) {
            if (flight.get(5).equals("Parked")) {
                if (this.minutes > Integer.parseInt(flight.get(6))) delayedFlights.add(flight);
            }
        }
        TableView<PopUpDelayedFlights> table = PopUpDelayedFlights.setDelayedFlightsForPopUp(delayedFlights);
        JFXDialog dialog = new JFXDialog(stackPane, table, JFXDialog.DialogTransition.TOP);
        dialog.show();
    }

    /** Show the flights that have a status of "Holding" when the Holding button is clicked
     *
     */
    private void buttonShowHoldingFlights() {
        //we make a new list with only flights to be departed in the next 10 minutes
        List<List<String>> nextDepartureFlights = new ArrayList<>();

        for (List<String> flight : ledger.flightLedger) {
            if(flight.get(5).equals("Holding")) nextDepartureFlights.add(flight);
        }
        TableView<PopUpHoldingFlights> table = PopUpHoldingFlights.setHoldingFlightsForPopUp(nextDepartureFlights);
        JFXDialog dialog = new JFXDialog(stackPane, table, JFXDialog.DialogTransition.TOP);
        dialog.show();
    }

    /** Show the flights that are about to leave in max 10 minutes from now when the Next Departures button is clicked
     *
     */
    private void buttonShowNextDepartures() {
        //we make a new list with only flights to be departed in the next 10 minutes
        List<List<String>> nextDepartureFlights = new ArrayList<>();

        for (List<String> flight : ledger.flightLedger) {
            if (!flight.get(6).equals("")) {
                if (Integer.parseInt(flight.get(6)) <= this.minutes + 10) nextDepartureFlights.add(flight);
            }
        }
        TableView<PopUpNextDepartures> table = PopUpNextDepartures.setNextDeparturesForPopUp(nextDepartureFlights);
        JFXDialog dialog = new JFXDialog(stackPane, table, JFXDialog.DialogTransition.TOP);
        dialog.show();
    }

    /** Returns the number of flights about to depart in max the next 10 minutes
     *
     * @return nextDepartureFlights.size() which return the size of the newly constructed arraylist for this purpose
     */
    private int next10MinDepartures() {
        //we make a new list with only flights to be departed in the next 10 minutes
        List<List<String>> nextDepartureFlights = new ArrayList<>();

        for (List<String> flight : ledger.flightLedger) {
            if (!flight.get(6).equals("")) {
                if (Integer.parseInt(flight.get(6)) <= this.minutes + 10) nextDepartureFlights.add(flight);
            }
        }
        return nextDepartureFlights.size();
    }

}