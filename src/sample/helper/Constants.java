package sample.helper;

import java.util.ArrayList;

public class Constants {
     public String gate = "Gate";
     public String freight_gate = "Freight Gate";
     public String zone_a = "Zone A";
     public String zone_b = "Zone B";
     public String zone_c = "Zone C";
     public String general = "General Parking Space";
     public String long_stay = "Long Stay";

    //convention that of the above have all the options. When we encounter with an "-" string the type in not supported
     public String gateFlightType[] = {"passengers", "-", "-"};
     public String gateAirplaneType[] = {"-", "turboprop", "jet"};
     public String gateServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int gateMaxTime = 45;
     public int gateMaxCapacity;
     public int gateCurrentCapacity;
     public static ArrayList<Integer> gateDockTimes = new ArrayList<>();

     public String freightGateFlightType[] = {"-", "cargo", "-"};
     public String freightGateAirplaneType[] = {"-", "turboprop", "jet"};
     public String freightGateServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int freightGateMaxTime = 90;
     public int freightGateMaxCapacity;
     public int freightGateCurrentCapacity;
     public static ArrayList<Integer> freightGateDockTimes = new ArrayList<>();

     public String zoneAFlightType[] = {"passengers", "-", "-"};
     public String zoneAAirplaneType[] = {"-", "turboprop", "jet"};
     public String zoneAServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int zoneAMaxTime = 90;
     public int zoneAMaxCapacity;
     public int zoneACurrentCapacity;
     public static ArrayList<Integer> zoneADockTimes = new ArrayList<>();

     public String zoneBFlightType[] = {"passengers", "cargo", "private"};
     public String zoneBAirplaneType[] = {"-", "turboprop", "jet"};
     public String zoneBServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int zoneBMaxTime = 120;
     public int zoneBMaxCapacity;
     public int zoneBCurrentCapacity;
     public static ArrayList<Integer> zoneBDockTimes = new ArrayList<>();

     public String zoneCFlightType[] = {"passengers", "cargo", "private"};
     public String zoneCAirplaneType[] = {"monoplane", "-", "-"};
     public String zoneCServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int zoneCMaxTime = 180;
     public int zoneCMaxCapacity;
     public int zoneCCurrentCapacity;
     public static ArrayList<Integer> zoneCDockTimes = new ArrayList<>();

     public String generalFlightType[] = {"passengers", "cargo", "private"};
     public String generalAirplaneType[] = {"monoplane", "turboprop", "jet"};
     public String generalServices[] = {"refuel", "cleaning", "-", "-"};
     public int generalMaxTime = 240;
     public int generalMaxCapacity;
     public int generalCurrentCapacity;
     public static ArrayList<Integer> generalDockTimes = new ArrayList<>();

     public String longTermFlightType[] = {"-", "cargo", "-"};
     public String longTermAirplaneType[] = {"monoplane", "turboprop", "jet"};
     public String longTermServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int longTermMaxTime = 600;
     public int longTermMaxCapacity;
     public int longTermCurrentCapacity;
     public static ArrayList<Integer> longTermDockTimes = new ArrayList<>();
}
