import java.lang.String;
import java.util.Comparator;

public class Flight { 
  private String day;
  private String month;
  private String departTime;
  private String departPort;
  private String arriveTime;
  private String arrivePort;
  private String flightType;
  private String cost;
  private int id;
  private int prefPoint;

  public void fill(String[] s, int i) {
    this.day = s[0];
    this.month = s[1];
    this.departTime = s[2];
    this.departPort = s[3];
    this.arriveTime = s[4];
    this.arrivePort = s[5];
    this.flightType = s[6];
    this.cost = s[7];
    this.id = i;
  }

  public String getCost() {
    return this.cost;
  }

  //still need to finish this function
  public String getFlightTime() { 
    String totalTime = "0";
    return totalTime;
  }

  public void addPoints(int p) {
    this.prefPoint = this.prefPoint + p;
  } 

  public int getPoints() {
    return prefPoint;
  }

  /*
  public static Comparator<Flight> costComparator = new Comparator<Flight>() {
	  public int compare(Flight s1, Flight s2) {
	     String flight1 = s1.getCost();
	     String flight2 = s2.getCost();

	     return flight2.compareTo(flight1);
    }
  };
*/
  public static Comparator<Flight> pointComparator = new Comparator<Flight>() {
	  
    public int compare(Flight s1, Flight s2) {
	     Integer flight1 = s1.getPoints();
	     Integer flight2 = s2.getPoints();

	     return flight1.compareTo(flight2);
    }
  };
  /*
  public static Comparator<Flight> timeComparator = new Comparator<Flight>() {
	  public int compare(Flight s1, Flight s2) {
	     String flight1 = s1.getFlightTime();
	     String flight2 = s2.getFlightTime();

	     return flight2.compareTo(flight1);
    }
  };
  */
}

//  123 
//  213
//  231
//  321
//  312
//  132
//  123