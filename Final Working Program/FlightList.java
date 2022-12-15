import java.util.*;
import java.io.*;

public class FlightList
{
  private int date;
  private String month;
  private int depart;
  public String origin;
  private int arrival;
  public String destination;
  public String connect;
  private int cost;
  private ArrayList<Flight> list;

  public FlightList()
  {
    list = new ArrayList<Flight>();
    // File path
    String filePath = "flightTest.txt";

    try 
    {
        // Create a scanner to read the file
        Scanner scanner = new Scanner(new File(filePath));
        // Read each line of the file
        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine();

            // Split the line by tabs to get the individual fields
            String[] fields = line.split("\\s+");

            // Store each each individual fields into object properties
            date = Integer.parseInt(fields[0]);
            month = fields[1];
            depart = Integer.parseInt(fields[2]);
            origin = fields[3];
            arrival = Integer.parseInt(fields[4]);
            destination = fields[5];
            connect = fields[6];
            cost = Integer.parseInt(fields[7]);

            // Add new object to ArrayList list
            list.add(new Flight(date, month, depart, origin, arrival, destination, connect, cost));
        }
      scanner.close();
      //System.out.println(list);
      System.out.println();
      /*for (int i = 0; i < 7; i++)
      {
        System.out.println(list.get(i));
      }*/
    } 
    catch (FileNotFoundException e) 
    {
      System.out.println("Error: unable to read file " + filePath);
    }   
  }

  public ArrayList<Flight> getFlightList()
  {
    return list;
  }


  
  public void printList()
  {
    ArrayList<Flight> list = new ArrayList<>();
    // File path
    String filePath = "flightTest.txt";

    try 
    {
        // Create a scanner to read the file
        Scanner scanner = new Scanner(new File(filePath));

        System.out.print("Day   Month   Depart  Ori.  Arriv.  Dest.  Connect.   Cost");
        // Read each line of the file
        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine();

            // Split the line by tabs to get the individual fields
            String[] fields = line.split("\\s+");

            // Store each each individual fields into object properties
            int date = Integer.parseInt(fields[0]);
            String month = fields[1];
            int depart = Integer.parseInt(fields[2]);
            String origin = fields[3];
            int arrival = Integer.parseInt(fields[4]);
            String destination = fields[5];
            String connect = fields[6];
            int cost = Integer.parseInt(fields[7]);

            // Add new object to ArrayList list
            list.add(new Flight(date, month, depart, origin, arrival, destination, connect, cost));
        }
      scanner.close();
      //System.out.println(list);
      System.out.println();
      for (int i = 0; i < 7; i++)
      {
        System.out.println(list.get(i));
      }
    } 
    catch (FileNotFoundException e) 
    {
      System.out.println("Error: unable to read file " + filePath);
    }
  }
}