import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

public class algorithm {
  private ArrayList<Flight> flightList = new ArrayList<Flight>();
  private ArrayList<Flight> topList = new ArrayList<Flight>();

  void readfile() { // reads the file and puts each flight into a flight object which is then stores it in an array
    File file = new File("flightTest.txt");
    Scanner s = new Scanner(file);
    ArrayList<String> list = new ArrayList<String>();

    while (s.hasNextLine()) { //gets all lines from the input file and stores them into a list to be processed 
      list.add(s.nextLine());
    } 
    
    s.close(); //close the input file
    for (int i = 0; i < list.size(); i++) 
    { // creates a flight object with each line of the input file
      Flight fl = new Flight();
      String[] split = list.get(i).split(" ");
      fl.fill(split, i); // filling out the flight information to the object and then giving it an ID which is denoted by i
      flightList.add(fl);
    }
  }
  
  public ArrayList<Flight> findPref(int cost, int lay, int time) {
    
    //dealing points with cost
    //ArrayList<Flight> costSort = flightList; //needed to sort 
    //Collections.sort(costSort, Flight.costComparator); //sort method if needed later

    for(int i = 0; i < flightList.size(); i++) {
      int flightCost = Integer.parseInt((flightList.get(i)).getCost());
      int points = flightCost/100;

    switch(cost) {
      case 1: points = points * 5; break;
      case 2: points = points * 4; break;
      case 3: points = points * 3; break;
      case 4: points = points * 2; break;
      case 5: points = points * 1; break;
      default: break;
    }
    Flight fl = flightList.get(i);
    fl.addPoints(points);
    
    }
      
    //dealing points with time
    //ArrayList<Flight> timeSort = flightList;
    //Collections.sort(timeSort, Flight.costComparator);

    for(int i = 0; i < flightList.size(); i++) {
      int flightTime = Integer.parseInt((flightList.get(i)).getFlightTime());
      int timepoints = flightTime/30;
      
      switch(cost) {
        case 1: timepoints = timepoints * 5; break;
        case 2: timepoints = timepoints * 4; break;
        case 3: timepoints = timepoints * 3; break;
        case 4: timepoints = timepoints * 2; break;
        case 5: timepoints = timepoints * 1; break;
        default: break;
      }
      Flight fl = flightList.get(i);
      fl.addPoints(timepoints);
    }

    topList = flightList;

    Collections.sort(topList, Flight.pointComparator);

    return topList;
  }
    
}

