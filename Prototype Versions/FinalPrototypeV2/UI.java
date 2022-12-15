import java.util.*;
import java.io.*;

public class UI
{
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  private FlightList flightList;
  String dfw = "DFW";
  String lax = "LAX";
  String layover = "Layover";
  
  // User Parameters
  public static String initialPort, finalPort, flightEarliestDate, flightLatestDate;
  public static int lowCostPref1, layoverTimePref1, flightTimePref1, lowCostPref2, layoverTimePref2, flightTimePref2, flightChoice, firstFlightChoice, secondFlightChoice; 

  public static int getPrefs()  //function for getting the customer's preference for lower layover time
  {
      Scanner myObj = new Scanner(System.in);
      int lowCostPref = 0, layoverTimePref = 0, flightTimePref = 0;
      boolean check1 = true, checkEarliest = true, checkLayOver = true; 
    
      System.out.println("Please rank these preferences from highest to lowest \n1-3 (1 = no preference,2 = slight preference, 3 = preferred to utmost): ");
      //System.out.println("Please rank these preferences from highest to lowest");
      //System.out.println("1-3 (1 = no preference, 2 = slight preference, 3 = preferred to utmost): ");
      System.out.println("Cheaper Prices: ");
      lowCostPref = myObj.nextInt();
      if(lowCostPref == 3)
      {
        do{
          System.out.println("Not having a layover: ");
          layoverTimePref = myObj.nextInt();
          if(layoverTimePref == 2)
          {
            do{
              System.out.println("Earliest departure date: ");
              flightTimePref = myObj.nextInt();
              if(flightTimePref == 1)
              {
                checkEarliest = false;
              }
            }while(checkEarliest);
            checkLayOver = false;
          }
          else if (layoverTimePref == 1)
          {
            do{
              System.out.println("Earliest departure date: ");
              flightTimePref = myObj.nextInt();
              if(flightTimePref == 2)
              {
                checkEarliest = false;
              }
            }while(checkEarliest);
            checkLayOver = false;
          }
          else
          {
            System.out.println("Please enter a valid input, 2 OR 1");
          }
        }while(checkLayOver); 
      }
      return lowCostPref;
      //return layoverTimePref;
      //return flightTimePref;
  }
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

    flightList = new FlightList();
    ArrayList<Flight> dblist = flightList.getFlightList();
    ArrayList<Flight> list1 = new ArrayList<>();
    ArrayList<Flight> list2 = new ArrayList<>();
    ArrayList<Flight> list3 = new ArrayList<>();
    ArrayList<Flight> flightChoiceList = new ArrayList<>();
    
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
              if(homePort.equals("Dallas") || homePort.equals("dallas"))
              {
                homePort = "DFW";
                homePortCheckNotComplete = false;
                System.out.println(homePort);
              }
              if(homePort.equals("Los Angeles") || homePort.equals("LA") || homePort.equals("la"))
              {
                homePort = "LAX";
                homePortCheckNotComplete = false;
                System.out.println(homePort);
              }
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
                  if(destPort.equals("Dallas") || destPort.equals("dallas"))
                  {
                    destPort = "DFW";
                    destPortCheckNotComplete = false;
                    System.out.println(destPort);
                  }
                  if(destPort.equals("Los Angeles") || destPort.equals("LA") || destPort.equals("la"))
                  {
                    destPort = "LAX";
                    destPortCheckNotComplete = false;
                    System.out.println(destPort);
                  }
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
                Boolean check1 = true, checkEarliest = true, checkEarliest2 = true,  checkLayOver = true; 
                do
                {
                    System.out.println("\nPlease input the parameters for your first flight.");
                    //getPrefs();
                  
                    //Scanner myObj = new Scanner(System.in);
                    //Boolean check1 = true, checkEarliest = true, checkEarliest2 = true,  checkLayOver = true; 
    
                    System.out.println("Please rank preferences from a scale of 1-3 \n(1 = no preference, 2 = slight preference, 3 = preferred to utmost): ");
                  
                    System.out.println("Cheaper Prices: ");
                    lowCostPref1 = myObj.nextInt();
                    if(lowCostPref1 == 3)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        layoverTimePref1 = myObj.nextInt();
                        if(layoverTimePref1 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 1)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref1 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 2)
                            {
                              checkEarliest2 = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (2 OR 1)");
                        }
                      }while(checkLayOver); 
                    }
                    else if(lowCostPref1 == 2)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        layoverTimePref1 = myObj.nextInt();
                        if(layoverTimePref1 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 1)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref1 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 3)
                            {
                              checkEarliest2 = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 1)");
                        }
                      }while(checkLayOver); 
                    }
                    else if(lowCostPref1 == 1)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        layoverTimePref1 = myObj.nextInt();
                        if(layoverTimePref1 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 2)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref1 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 3)
                            {
                              checkEarliest2 = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 2)");
                        }
                      }while(checkLayOver); 
                    }
                  
                    /*lowCostPref1 = getLowCostPref();
                    layoverTimePref1 = getLayoverTimePref();
                    flightTimePref1 = getFlightTimePref();*/
                    String response = queryRestartProcess();
                    if(response.equals("No") || response.equals("no"))
                    {
                        firstFlightNotComplete = false;
                    }
                    System.out.println(); 
                }while(firstFlightNotComplete);
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~              
                    if(homePort.equals("DFW"))
                     {
                       System.out.println("List of recommended Departure Flights, based on preference: ");
                       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                       System.out.println("   Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
                        for(int i = 0; i < dblist.size(); i++)
                        {
                          if(dblist.get(i).origin.contains(dfw))
                          {
                            list1.add(dblist.get(i));
                          }
                        }
                       
                        if(lowCostPref1 == 3 && layoverTimePref1 == 2 && flightTimePref1 == 1)
                        {
                          Collections.sort(list1, new Flight.CostComparator());
                          Collections.sort(list1);
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 3 && layoverTimePref1 == 1 && flightTimePref1 == 2)
                        {
                          Collections.sort(list1, new Flight.CostComparator());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 2 && layoverTimePref1 == 1 && flightTimePref1 == 3)
                        {
                          Collections.sort(list1, new Flight.DateComparator());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 2 && layoverTimePref1 == 3 && flightTimePref1 == 1)
                        { 
                          ListIterator<Flight> itr = list1.listIterator();
                          while (itr.hasNext())
                          {
                            if(itr.next().connect.contains(layover))
                            {
                              itr.remove();
                            }
                          }
                          Collections.sort(list1, new Flight.CostComparator());  
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 1 && layoverTimePref1 == 3 && flightTimePref1 == 2)
                        {
                          ListIterator<Flight> itr = list1.listIterator();
                          while (itr.hasNext())
                          {
                            if(itr.next().connect.contains(layover))
                            {
                              itr.remove();
                            }
                          }
                          Collections.sort(list1, new Flight.DateComparator());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 1 && layoverTimePref1 == 2 && flightTimePref1 == 3)
                        {
                          Collections.sort(list1, new Flight.DateComparator());
                          Collections.sort(list1, Collections.reverseOrder());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }

                       Boolean validChoice = true; 
                       System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                     do{
                        flightChoice = myObj.nextInt();
                        if(flightChoice >= list1.size())
                        {
                          System.out.println("Please enter a valid choice");
                        }
                        else
                        {
                          validChoice = false;
                        }   
                      }while(validChoice);
                      flightChoiceList.add(list1.get(flightChoice));
                      System.out.println("\nYou have chosen: ");
                      System.out.println("Option " + flightChoice + ": " + list1.get(flightChoice));
                     }

                    if(homePort.equals("LAX"))
                     {
                       System.out.println("List of recommended Departure Flights, based on preference: ");
                       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                       System.out.println("   Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
                        for(int i = 0; i < dblist.size(); i++)
                        {
                          if(dblist.get(i).origin.contains(lax))
                          {
                            list1.add(dblist.get(i));
                          }
                        }
                       
                        if(lowCostPref1 == 3 && layoverTimePref1 == 2 && flightTimePref1 == 1)
                        {
                          Collections.sort(list1, new Flight.CostComparator());
                          Collections.sort(list1);
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 3 && layoverTimePref1 == 1 && flightTimePref1 == 2)
                        {
                          Collections.sort(list1, new Flight.CostComparator());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 2 && layoverTimePref1 == 1 && flightTimePref1 == 3)
                        {
                          Collections.sort(list1, new Flight.DateComparator());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 2 && layoverTimePref1 == 3 && flightTimePref1 == 1)
                        { 
                          ListIterator<Flight> itr = list1.listIterator();
                          while (itr.hasNext())
                          {
                            if(itr.next().connect.contains(layover))
                            {
                              itr.remove();
                            }
                          }
                          Collections.sort(list1, new Flight.CostComparator());  
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 1 && layoverTimePref1 == 3 && flightTimePref1 == 2)
                        {
                          ListIterator<Flight> itr = list1.listIterator();
                          while (itr.hasNext())
                          {
                            if(itr.next().connect.contains(layover))
                            {
                              itr.remove();
                            }
                          }
                          Collections.sort(list1, new Flight.DateComparator());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        else if(lowCostPref1 == 1 && layoverTimePref1 == 2 && flightTimePref1 == 3)
                        {
                          Collections.sort(list1, new Flight.DateComparator());
                          Collections.sort(list1, Collections.reverseOrder());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }

                      Boolean validChoice = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                     do{
                        flightChoice = myObj.nextInt();
                        if(flightChoice >= list1.size())
                        {
                          System.out.println("Please enter a valid choice");
                        }
                        else
                        {
                          validChoice = false;
                        }   
                      }while(validChoice);
                      flightChoiceList.add(list1.get(flightChoice));
                      System.out.println("\nYou have chosen: ");
                      System.out.println("Option " + flightChoice + ": " + list1.get(flightChoice));
                     }
                    
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
              
                do
                {
                    System.out.println("\nPlease input the parameters for your second flight.");

                    System.out.println("Please rank preferences from a scale of 1-3 \n(1 = no preference, 2 = slight preference, 3 = preferred to utmost): ");
                  
                    System.out.println("Cheaper Prices: ");
                    lowCostPref2 = myObj.nextInt();
                    if(lowCostPref2 == 3)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        layoverTimePref2 = myObj.nextInt();
                        if(layoverTimePref2 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 1)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref2 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 2)
                            {
                              checkEarliest2 = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (2 OR 1)");
                        }
                      }while(checkLayOver); 
                    }
                    else if(lowCostPref2 == 2)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        layoverTimePref2 = myObj.nextInt();
                        if(layoverTimePref2 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 1)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref2 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 3)
                            {
                              checkEarliest2 = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 1)");
                        }
                      }while(checkLayOver); 
                    }
                    else if(lowCostPref2 == 1)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        layoverTimePref2 = myObj.nextInt();
                        if(layoverTimePref2 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 2)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref2 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 3)
                            {
                              checkEarliest2 = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 2)");
                        }
                      }while(checkLayOver); 
                    }
                    /*lowCostPref2 = getLowCostPref();
                    layoverTimePref2 = getLayoverTimePref();
                    flightTimePref2 = getFlightTimePref();*/
                    String response = queryRestartProcess();
                    if(response.equals("No") || response.equals("no"))
                    {
                        secondFlightNotComplete = false;
                    }
                    System.out.println();
                }while(secondFlightNotComplete);

                if(destPort.equals("DFW"))
                     {
                       System.out.println("List of recommended Return Flights, based on preference: ");
                       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                       System.out.println("   Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
                        for(int i = 0; i < dblist.size(); i++)
                        {
                          if(dblist.get(i).origin.contains(dfw))
                          {
                            list2.add(dblist.get(i));
                          }
                        }
                       
                        if(lowCostPref2 == 3 && layoverTimePref2 == 2 && flightTimePref2 == 1)
                        {
                          Collections.sort(list2, new Flight.CostComparator());
                          Collections.sort(list2);
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 3 && layoverTimePref2 == 1 && flightTimePref2 == 2)
                        {
                          Collections.sort(list2, new Flight.CostComparator());
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 2 && layoverTimePref2 == 1 && flightTimePref2 == 3)
                        {
                          Collections.sort(list2, new Flight.DateComparator());
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 2 && layoverTimePref2 == 3 && flightTimePref2 == 1)
                        { 
                          ListIterator<Flight> itr = list2.listIterator();
                          while (itr.hasNext())
                          {
                            if(itr.next().connect.contains(layover))
                            {
                              itr.remove();
                            }
                          }
                          Collections.sort(list2, new Flight.CostComparator());  
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 1 && layoverTimePref2 == 3 && flightTimePref2 == 2)
                        {
                          ListIterator<Flight> itr = list2.listIterator();
                          while (itr.hasNext())
                          {
                            if(itr.next().connect.contains(layover))
                            {
                              itr.remove();
                            }
                          }
                          Collections.sort(list2, new Flight.DateComparator());
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 1 && layoverTimePref2 == 2 && flightTimePref2 == 3)
                        {
                          Collections.sort(list2, new Flight.DateComparator());
                          Collections.sort(list2, Collections.reverseOrder());
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }

                      Boolean validChoice = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                     do{
                        flightChoice = myObj.nextInt();
                        if(flightChoice >= list2.size())
                        {
                          System.out.println("Please enter a valid choice");
                        }
                        else
                        {
                          validChoice = false;
                        }   
                      }while(validChoice);
                      flightChoiceList.add(list2.get(flightChoice));
                      System.out.println("\nYou have chosen: ");
                      System.out.println("Option " + flightChoice + ": " + list2.get(flightChoice));
                     }

                    if(destPort.equals("LAX"))
                     {
                       System.out.println("List of recommended Return Flights, based on preference: ");
                       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                       System.out.println("   Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
                        for(int i = 0; i < dblist.size(); i++)
                        {
                          if(dblist.get(i).origin.contains(lax))
                          {
                            list2.add(dblist.get(i));
                          }
                        }
                       
                        if(lowCostPref2 == 3 && layoverTimePref2 == 2 && flightTimePref2 == 1)
                        {
                          Collections.sort(list2, new Flight.CostComparator());
                          Collections.sort(list2);
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 3 && layoverTimePref2 == 1 && flightTimePref2 == 2)
                        {
                          Collections.sort(list2, new Flight.CostComparator());
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 2 && layoverTimePref2 == 1 && flightTimePref2 == 3)
                        {
                          Collections.sort(list2, new Flight.DateComparator());
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 2 && layoverTimePref2 == 3 && flightTimePref2 == 1)
                        { 
                          ListIterator<Flight> itr = list2.listIterator();
                          while (itr.hasNext())
                          {
                            if(itr.next().connect.contains(layover))
                            {
                              itr.remove();
                            }
                          }
                          Collections.sort(list2, new Flight.CostComparator());  
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 1 && layoverTimePref2 == 3 && flightTimePref2 == 2)
                        {
                          ListIterator<Flight> itr = list2.listIterator();
                          while (itr.hasNext())
                          {
                            if(itr.next().connect.contains(layover))
                            {
                              itr.remove();
                            }
                          }
                          Collections.sort(list2, new Flight.DateComparator());
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                        else if(lowCostPref2 == 1 && layoverTimePref2 == 2 && flightTimePref2 == 3)
                        {
                          Collections.sort(list2, new Flight.DateComparator());
                          Collections.sort(list2, Collections.reverseOrder());
                          for (int i = 0; i < list2.size(); i++)
                          {
                            System.out.println(i+": " +list2.get(i));
                          }
                        }
                       
                      Boolean validChoice = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                     do{
                        flightChoice = myObj.nextInt();
                        if(flightChoice >= list2.size())
                        {
                          System.out.println("Please enter a valid choice");
                        }
                        else
                        {
                          validChoice = false;
                        }   
                      }while(validChoice);
                      flightChoiceList.add(list2.get(flightChoice));
                      System.out.println("\nYou have chosen: ");
                      System.out.println("Option " + flightChoice + ": " + list2.get(flightChoice));
                     }
              
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
      //System.out.println(flightChoiceList);
      System.out.println("\n\n*Flight Confirmation*");
      System.out.println("Departure Flight: ");
      System.out.println("Day   Month   Depart Ori.   Arriv.  Dest.  Connect.   Cost");
      System.out.println(flightChoiceList.get(0));
      System.out.println("Return Flight: ");
      System.out.println("Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
      System.out.println(flightChoiceList.get(1));

      int amountDue;
      //flightChoiceList.get(0)
      //System.out.println()
      /*Collections.sort(list);
      Collections.sort(list, new Flight.CostComparator());
      Collections.sort(list);
      Collections.sort(list, Collections.reverseOrder());
      Collections.sort(list, new Flight.DateComparator());
      for (int i = 0; i < 7; i++)
          {
            System.out.println(list.get(i));
          }*/
  } 
}

  