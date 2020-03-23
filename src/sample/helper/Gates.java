package sample.helper;


/** Gates contain all the data of each gate
 *
 */
public class Gates {
    //convention that of the above have all the options. When we encounter with an "-" string the type in not supported
     public String gateFlightType[] = {"passengers", "-", "-"};
     public String gateAirplaneType[] = {"-", "turboprop", "jet"};
     public String gateServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int gateMaxTime = 45;
     public int gateMaxCapacity;
     public int gateCurrentCapacity;
     public String gateIdentifier;
     public int gateCost;

     public String freightGateFlightType[] = {"-", "cargo", "-"};
     public String freightGateAirplaneType[] = {"-", "turboprop", "jet"};
     public String freightGateServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int freightGateMaxTime = 90;
     public int freightGateMaxCapacity;
     public int freightGateCurrentCapacity;
     public String freightGateIdentifier;
     public int freightGateCost;

     public String zoneAFlightType[] = {"passengers", "-", "-"};
     public String zoneAAirplaneType[] = {"-", "turboprop", "jet"};
     public String zoneAServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int zoneAMaxTime = 90;
     public int zoneAMaxCapacity;
     public int zoneACurrentCapacity;
     public String zoneAIdentifier;
     public int zoneACost;

     public String zoneBFlightType[] = {"passengers", "cargo", "private"};
     public String zoneBAirplaneType[] = {"-", "turboprop", "jet"};
     public String zoneBServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int zoneBMaxTime = 120;
     public int zoneBMaxCapacity;
     public int zoneBCurrentCapacity;
     public String zoneBIdentifier;
     public int zoneBCost;

     public String zoneCFlightType[] = {"passengers", "cargo", "private"};
     public String zoneCAirplaneType[] = {"monoplane", "-", "-"};
     public String zoneCServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int zoneCMaxTime = 180;
     public int zoneCMaxCapacity;
     public int zoneCCurrentCapacity;
     public String zoneCIdentifier;
     public int zoneCCost;

     public String generalFlightType[] = {"passengers", "cargo", "private"};
     public String generalAirplaneType[] = {"monoplane", "turboprop", "jet"};
     public String generalServices[] = {"refuel", "cleaning", "-", "-"};
     public int generalMaxTime = 240;
     public int generalMaxCapacity;
     public int generalCurrentCapacity;
     public String generalIdentifier;
     public int generalCost;

     public String longTermFlightType[] = {"-", "cargo", "-"};
     public String longTermAirplaneType[] = {"monoplane", "turboprop", "jet"};
     public String longTermServices[] = {"refuel", "cleaning", "transportation", "loading"};
     public int longTermMaxTime = 600;
     public int longTermMaxCapacity;
     public int longTermCurrentCapacity;
     public String longTermIdentifier;
     public int longTermCost;

 /** Resets the current capacity of the gates
  *
  */
 public void reset() {
         this.gateCurrentCapacity = 0;
         this.freightGateCurrentCapacity = 0;
         this.zoneACurrentCapacity = 0;
         this.zoneBCurrentCapacity = 0;
         this.zoneCCurrentCapacity = 0;
         this.generalCurrentCapacity = 0;
         this.longTermCurrentCapacity = 0;
     }
}
