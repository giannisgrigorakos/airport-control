package sample.helper;

import java.util.*;

public class Flights {
    //here we store all our flights. The convention is [flightId, cityOfDeparture, flightType, planeType, whereParked, currentState, departureTime, firstContact, initialTimeLanded, gateParked, timeToLand, timeItStartsLanding, realTimeLanded]
    public List<List<String>> flightLedger = new ArrayList<>();


    Comparator<List<String>> compareById = (List<String> o1, List<String> o2) ->
            o1.get(0).compareTo( o2.get(0));

    public void sortHoldingFlights() {
        Collections.sort(flightLedger, compareById);
    }

}

