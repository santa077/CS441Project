import java.util.*;
import java.io.*;



public class UI
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  // User Parameters
  public static String initialPort, finalPort, flightEarliestDate, flightLatestDate;
  public static int lowCostPref1, layoverTimePref1, flightTimePref1, lowCostPref2, layoverTimePref2, flightTimePref2; 
  
  public static int getLowCostPref() //function for getting the customer's preference for low prices
  {
      Scanner myObj = new Scanner(System.in);
      int lowCostPref;
      System.out.println("How you much do you prefer cheaper prices? \n1-3 (1 = no preference, 3 = preferred to utmost): ");
      lowCostPref = myObj.nextInt();
      return lowCostPref;
  }
  public static int getLayoverTimePref()  //function for getting the customer's preference for lower layover time
  {
      Scanner myObj = new Scanner(System.in);
      int layoverTimePref;
      System.out.println("How you much you prefer not having a layover? \n1-3 (1 = no preference, 3 = preferred to utmost): ");
      layoverTimePref = myObj.nextInt();
      return layoverTimePref;
  }
  public static int getFlightTimePref()   //function for getting customer's preference for lower flight time
  {
      Scanner myObj = new Scanner(System.in);
      int flightTimePref;
      System.out.println("How you much do you prefer having the earliest flight available? \n1-3 (1 = no preference, 3 = preferred to utmost): ");
      flightTimePref = myObj.nextInt();
      return flightTimePref;
  }
  public static String queryRestartProcess()  //function for asking the customer if they want to reinput preferences
  {
      Scanner myObj = new Scanner(System.in);
      String response;
      System.out.println("Would you like to redo your inputs for this flight? (Yes/No)");
      response = myObj.nextLine();
      return response;
  }

  public void getUserParameters()
  {
    Scanner myObj = new Scanner(System.in);
    boolean notComplete = true;
    boolean twoWayFlightCheck= false;
    boolean resetFlag;
    String twoWayFlight, homePort, destPort = "";
    resetFlag = false;
    do 
    {
        System.out.println("\nThank you for using the Efficient Flight Itinerary Software! \nEnter 'Reset' at any query to start from the beginning.");      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //takes in the customer's home port and destination port (should be made into function)
        /*  Ver 1
        System.out.println("Please enter you depature point. (Dallas/LA)"); 
        homePort = myObj.nextLine();  // Read user input
        System.out.println("Please enter you arrival point. (Dallas/LA)");
        destinationPort = myObj.nextLine();*/
        Boolean homePortCheckNotComplete = true;
        do
        {
            System.out.println("Please enter you depature point. (Dallas/LA/Reset)");
            homePort = myObj.nextLine();
            if(homePort.equals("Dallas") || homePort.equals("dallas") || homePort.equals("LA") || homePort.equals("la") || 
            homePort.equals("Los Angeles") || homePort.equals("los angeles"))
            {
                homePortCheckNotComplete = false;
            }
            else if(homePort.equals("Reset") || homePort.equals("reset"))
            {
                homePortCheckNotComplete = false;
                resetFlag=true;
            }
            else
            {
                System.out.println("Please enter a valid input (Dallas/LA/Reset)");
            }
        }while (homePortCheckNotComplete);
        if(!resetFlag) 
        {
            Boolean destPortCheckNotComplete = true;
            do
            {
                System.out.println("Please enter you arrival point. (Dallas/LA/Reset)");
                destPort = myObj.nextLine();
                if(destPort.equals("Dallas") || destPort.equals("dallas") || destPort.equals("LA") || destPort.equals("la") || 
                destPort.equals("Los Angeles") || destPort.equals("los angeles"))
                {
                    destPortCheckNotComplete = false;
                }
                else if(destPort.equals("Reset") || destPort.equals("reset"))
                {
                    destPortCheckNotComplete = false;
                    resetFlag=true;
                }
                else
                {
                    System.out.println("Please enter a valid input (Dallas/LA/Reset)");
                }
            }while(destPortCheckNotComplete);
        }                                        

        //queries the customer for if this is a two-way flight
        
        if(!resetFlag) 
        {
            Boolean twoWayFlightNotComplete = true;//sets a flag for if two-way flight or not
            do
            {
                System.out.println("Is this a two-way flight? (Yes/No/Reset)");
                twoWayFlight = myObj.nextLine();
                if (twoWayFlight.equals("Yes") || twoWayFlight.equals("yes"))
                {
                    twoWayFlightCheck = true;
                    twoWayFlightNotComplete = false;
                }
                else if(twoWayFlight.equals("No") || twoWayFlight.equals("no")) 
                {
                    twoWayFlightCheck = false;
                    twoWayFlightNotComplete = false;
                }
                else if(twoWayFlight.equals("Reset") || twoWayFlight.equals("reset"))
                {
                    resetFlag=true;
                    twoWayFlightNotComplete = false;
                }
                else 
                {
                    System.out.println("Please enter a valid input (Yes/No/Reset)");
                }
            }while(twoWayFlightNotComplete);
        }
        //does two way flight
        if(!resetFlag)
        {
            if(twoWayFlightCheck)                                             ///////////error says local may not have been initialized
            {
                Boolean firstFlightNotComplete = true;
                Boolean secondFlightNotComplete = true;
                do
                {
                    System.out.println("\nPlease input the parameters for your first flight.");
                    lowCostPref1 = getLowCostPref();
                    layoverTimePref1 = getLayoverTimePref();
                    flightTimePref1 = getFlightTimePref();
                    String response = queryRestartProcess();
                    System.out.println();
                    if(response.equals("No") || response.equals("no"))
                    {
                        firstFlightNotComplete = false;
                    }
                }while(firstFlightNotComplete);
                do
                {
                    System.out.println("\nPlease input the parameters for your second flight.");
                    lowCostPref2 = getLowCostPref();
                    layoverTimePref2 = getLayoverTimePref();
                    flightTimePref2 = getFlightTimePref();
                    String response = queryRestartProcess();
                    System.out.println();
                    if(response.equals("No") || response.equals("no"))
                    {
                        secondFlightNotComplete = false;
                    }
                }while(secondFlightNotComplete);
            }
        
            //does one-way flight
            else
            {
                Boolean flightNotComplete = true;
                do
                {
                    System.out.println("\nPlease input your parameters for your flight.");
                    lowCostPref1 = getLowCostPref();
                    layoverTimePref1 = getLayoverTimePref();
                    flightTimePref1 = getFlightTimePref();
                    String response = queryRestartProcess();
                    if(response.equals("No") || response.equals("no"))
                    {
                        flightNotComplete = false;
                    }
                }while(flightNotComplete);
            }
        }
    
    
        ///////////////// Outputting Section /////////////////////////
        if(!resetFlag)
        {
            System.out.println("\nHomeport is: " + homePort);
            System.out.println("Destination port is: " + destPort);
            if(twoWayFlightCheck)                                                       ///////////error says local may not have been initialized
            {
              System.out.println("This is a two-way flight");
              System.out.println("*First Flight Details*: ");
              System.out.println("Low cost preference is: " + lowCostPref1);
              System.out.println("Layover time preference is: " + layoverTimePref1);
              System.out.println("Earliest flight preference is: " + flightTimePref1);
              System.out.println("*Second Flight Details*: ");
              System.out.println("Low cost preference is: " + lowCostPref2);
              System.out.println("Layover time preference is: " + layoverTimePref2);
              System.out.println("Earliest flight preference is: " + flightTimePref2);
            }
            else
            {
              System.out.println("This is a one-way flight");
              System.out.println("*One-way Flight Details*: ");
              System.out.println("Low cost preference is: " + lowCostPref1);
              System.out.println("Layover time preference is: " + layoverTimePref1);
              System.out.println("Earliest flight preference is: " + flightTimePref1);    
            }
           ///////////////similar localized variable error here for all preference inputs
            /*System.out.println("First Flight Details: \n");
            System.out.println("Low cost preference is: " + lowCostPref1);
            System.out.println("Layover time preference is: " + layoverTimePref1);
            System.out.println("Flight time preference is: " + flightTimePref1);*/
            notComplete = false;
        }
        
        resetFlag = false;
    }while(notComplete);

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      ArrayList<Flight> list = new ArrayList<>();
      // File path
      String filePath = "flightTest.txt";
  
      try 
      {
          // Create a scanner to read the file
          Scanner scanner = new Scanner(new File(filePath));
  
          //System.out.print("Day   Month   Depart  Ori.  Arriv.  Dest.  Connect.   Cost");
          // Read each line of the file
          while (scanner.hasNextLine()) 
          {
              String line = scanner.nextLine();
  
              // Split the line by tabs to get the individual fields
              String[] fields = line.split("\\s+");
  
              int date = Integer.parseInt(fields[0]);
              String month = fields[1];
              int depart = Integer.parseInt(fields[2]);
              String origin = fields[3];
              int arrival = Integer.parseInt(fields[4]);
              String destination = fields[5];
              String connect = fields[6];
              int cost = Integer.parseInt(fields[7]);
  
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

      //ALGORITHM~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      if(lowCostPref1 == 3 && layoverTimePref1 == 1 && flightTimePref1 == 1)
      {
        System.out.println("Test1");
        Collections.sort(list, new Flight.CostComparator());
        for (int i = 0; i < 7; i++)
          {
            System.out.println(list.get(i));
          }
      }
      else if(lowCostPref1 == 3 && layoverTimePref1 == 2 && flightTimePref1 == 1)
      {
        System.out.println("Test1.1");
        Collections.sort(list, new Flight.CostComparator());
        Collections.sort(list);
        for (int i = 0; i < 7; i++)
          {
            System.out.println(list.get(i));
          }
      }
      else if(lowCostPref1 == 2 && layoverTimePref1 == 3 && flightTimePref1 == 1)
      {
        System.out.println("Test1.2");
        Collections.sort(list, Collections.reverseOrder());
        //split list into layover-direct
        Collections.sort(list, new Flight.CostComparator());
        //repeat on other list
        for (int i = 0; i < 7; i++)
          {
            System.out.println(list.get(i));
          }
      }
      else if(lowCostPref1 == 1 && layoverTimePref1 == 3 && flightTimePref1 == 1)
      {
        System.out.println("Test2");
        Collections.sort(list);
        for (int i = 0; i < 7; i++)
          {
            System.out.println(list.get(i));
          }
      }
      else if(lowCostPref1 == 1 && layoverTimePref1 == 1 && flightTimePref1 == 3)
      {
        System.out.println("Test3");
        Collections.sort(list, new Flight.DateComparator());
        for (int i = 0; i < 7; i++)
          {
            System.out.println(list.get(i));
          }
      }
  } 
}

  