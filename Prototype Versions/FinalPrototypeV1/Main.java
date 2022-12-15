import java.util.*;
import java.io.*;

public class Main
{
  // User Parameters
  public static String initialPort, finalPort, flightEarliestDate, flightLatestDate;
  public static int lowCostPref1, layoverTimePref1, flightTimePref1, lowCostPref2, layoverTimePref2, flightTimePref2; 
  
  public static int getLowCostPref() //function for getting the customer's preference for low prices
  {
      Scanner myObj = new Scanner(System.in);
      int lowCostPref;
      System.out.println("Please enter how you much you prefer cheaper prices 1-5 (1 meaning no preference, 5 meaning preferred to utmost)");
      lowCostPref = myObj.nextInt();
      return lowCostPref;
  }
  public static int getLayoverTimePref()  //function for getting the customer's preference for lower layover time
  {
      Scanner myObj = new Scanner(System.in);
      int layoverTimePref;
      System.out.println("Please enter how you much you prefer not having a layover time 1-5 (1 meaning no preference, 5 meaning preferred to utmost)");
      layoverTimePref = myObj.nextInt();
      return layoverTimePref;
  }
  public static int getFlightTimePref()   //function for getting customer's preference for lower flight time
  {
      Scanner myObj = new Scanner(System.in);
      int flightTimePref;
      System.out.println("Please enter how you much you prefer having a shorter flight time 1-5 (1 meaning no preference, 5 meaning preferred to utmost)");
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
  

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  public static void main(String[] args) 
  {
    FlightList list = new FlightList();
    list.getList();

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
      Scanner myObj = new Scanner(System.in);
      boolean notComplete = true;
      boolean twoWayFlightCheck= false;
      boolean resetFlag;
      String twoWayFlight, homePort, destPort = "";
      resetFlag = false;
      do 
      {
          System.out.println("\nThank you for using the Efficient Flight Itinerary Software! Enter 'Reset' at any query with the option to start from the beginning.");
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
                      System.out.println("Please input the parameters for your first flight.");
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
                      System.out.println("Please input the parameters for your second flight.");
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
                      System.out.println("Please input your parameters for your flight.");
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
              System.out.println("Homeport is: " + homePort);
              System.out.println("Destination port is: " + destPort);
              if(twoWayFlightCheck)                                                       ///////////error says local may not have been initialized
              {
                System.out.println("This is a two-way flight");
                System.out.println("First Flight Details: ");
                System.out.println("Low cost preference is: " + lowCostPref1);
                System.out.println("Layover time preference is: " + layoverTimePref1);
                System.out.println("Flight time preference is: " + flightTimePref1);
                System.out.println("Second Flight Details: ");
                System.out.println("Low cost preference is: " + lowCostPref2);
                System.out.println("Layover time preference is: " + layoverTimePref2);
                System.out.println("Flight time preference is: " + flightTimePref2);
              }
              else
              {
                System.out.println("This is a one-way flight");
                System.out.println("One-way Flight Details: ");
                System.out.println("Low cost preference is: " + lowCostPref1);
                System.out.println("Layover time preference is: " + layoverTimePref1);
                System.out.println("Flight time preference is: " + flightTimePref1);    
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
  
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
  }
}

