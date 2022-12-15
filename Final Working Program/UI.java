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
  public static int lowCostPref1, layoverTimePref1, flightTimePref1, lowCostPref2, layoverTimePref2, flightTimePref2, flightChoice1, flightChoice2, firstFlightChoice, secondFlightChoice; 
  public static Boolean oneWay = false, twoWay = false;

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
    ArrayList<Flight> flightChoiceList = new ArrayList<>();
    
    do 
    {
        System.out.println("Thank you for using the Efficient Flight Itinerary Software! \nEnter 'Reset' at any query to start from the beginning.");      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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
                    twoWay = true;
                    twoWayFlightCheck = true;
                    twoWayFlightNotComplete = false;
                }
                else if(twoWayFlight.equals("No") || twoWayFlight.equals("no")) 
                {
                    oneWay = true;
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
                Boolean checkEarliest = true, checkEarliest2 = true,  checkLayOver = true, checkLayOver2 = true; 
                do
                {
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Query~Parameters~For~First~Flight~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    System.out.println("\nPlease input the parameters for your first flight.");          
                    //Scanner myObj = new Scanner(System.in);
                    //Boolean check1 = true, checkEarliest = true, checkEarliest2 = true,  checkLayOver = true; 
    
                    System.out.println("Please rank preferences from a scale of 1-3 \n(1 = no preference, 2 = slight preference, 3 = preferred to utmost): ");
                  
                    Boolean notInt = true;
                    System.out.println("Cheaper Prices: ");
                    do {
                      try{
                        lowCostPref1 = myObj.nextInt();
                        if (lowCostPref1 > 3 || lowCostPref1 < 0)
                        {
                          System.out.println("ERROR: Inputs can only be 3, 2, or 1");
                          System.out.println("\nCheaper Prices: "); 
                        }
                        else
                        {
                          notInt = false;
                        }
                      }catch(InputMismatchException e)
                        {
                          System.out.println("ERROR: Invalid input type (must be an int value)");
                          System.out.println("\nCheaper Prices: "); 
                          myObj.next();
                        }
                    }while(notInt);
                  
                    //System.out.println("Cheaper Prices: ");
                    //lowCostPref1 = myObj.nextInt();
                    if(lowCostPref1 == 3)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        try{
                          layoverTimePref1 = myObj.nextInt();
                        }catch(InputMismatchException e)
                        {
                          System.out.println("\nERROR: Invalid input type (must be an int value)");
                          myObj.next();
                        }
                        //layoverTimePref1 = myObj.nextInt();
                        if(layoverTimePref1 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 1)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref1 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                                flightTimePref1 = myObj.nextInt();
                              }catch(InputMismatchException e)
                              {
                                System.out.println("\nERROR: Invalid input type (must be an int value)");
                                myObj.next();
                              }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 2)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (2 OR 1)\n");
                        }
                      }while(checkLayOver); 
                    }
                    else if(lowCostPref1 == 2)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        try{
                            layoverTimePref1 = myObj.nextInt();
                          }catch(InputMismatchException e)
                          {
                            System.out.println("\nERROR: Invalid input type (must be an int value)");
                            myObj.next();
                          }
                        //layoverTimePref1 = myObj.nextInt();
                        if(layoverTimePref1 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                                flightTimePref1 = myObj.nextInt();
                              }catch(InputMismatchException e)
                              {
                                System.out.println("\nERROR: Invalid input type (must be an int value)");
                                myObj.next();
                              }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 1)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref1 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 3)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 1)\n");
                        }
                      }while(checkLayOver); 
                    }
                    else if(lowCostPref1 == 1)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        try{
                            layoverTimePref1 = myObj.nextInt();
                          }catch(InputMismatchException e)
                          {
                            System.out.println("\nERROR: Invalid input type (must be an int value)");
                            myObj.next();
                          }
                        //layoverTimePref1 = myObj.nextInt();
                        if(layoverTimePref1 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 2)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref1 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 3)
                            {
                              checkEarliest2 = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)\n");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 2)\n");
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
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Display~Recommendations~Departure~Flights~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~              
                    if(homePort.equals("DFW"))
                     {
                       System.out.println("[DFW]->[LAX] Recommended Departure Flights, based on preference: ");
                       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                       System.out.println("   Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
                       
                       // Check database list of flights, if any indexes contain an origin property
                       // of DFW, add index to list1 to compile all departures from DFW
                        for(int i = 0; i < dblist.size(); i++)
                        {
                          if(dblist.get(i).origin.contains(dfw))
                          {
                            list1.add(dblist.get(i));
                          }
                        }

                        // if... else algorithm for each possible user pref order
                        // depending on weighted ranking for each pref, sort based on 
                        // weighted importance, also removes any flights that are layovers
                        // if user highly prefers no layovers 
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
                          ListIterator<Flight> itr1 = list1.listIterator();
                          while (itr1.hasNext())
                          {
                            if(itr1.next().connect.contains(layover))
                            {
                              itr1.remove();
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
                          // Utilized a listIterator to iterate through list for removal
                          // of any flights not related to initial homePort since if we
                          // were to utilize a standard loop (if i=0...) we run into an issue
                          // of each increment being a mismatch eventually as the total list size
                          // is 1 less per remove per loop, listIterator avoids that problem
                          ListIterator<Flight> itr1 = list1.listIterator();
                          while (itr1.hasNext())
                          {
                            if(itr1.next().connect.contains(layover))
                            {
                              itr1.remove();
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
                       
                      Boolean validChoice = true, notInt = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                       do{
                         do{
                             try{
                               flightChoice1 = myObj.nextInt();
                               notInt = false;
                             }catch(InputMismatchException e)
                               {
                                 System.out.println("ERROR: Invalid input type (must be an int value)");
                                 System.out.println("\nPlease input the number corresponding to the flight you would like:"); 
                                 myObj.next();
                               }
                           }while(notInt);
                          if(flightChoice1 >= list1.size())
                          {
                            System.out.println("ERROR: Please enter a valid choice (must be an int)");
                            System.out.println("\nPlease input the number corresponding to the flight you would like:");
                          }
                          else
                          {
                            validChoice = false;
                          }   
                        }while(validChoice);
                       
                      /*Boolean validChoice = true; 
                       System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                       do{
                          flightChoice1 = myObj.nextInt();
                          if(flightChoice1 >= list1.size())
                          {
                            System.out.println("Please enter a valid choice");
                          }
                          else
                          {
                            validChoice = false;
                          }   
                        }while(validChoice);*/
                      flightChoiceList.add(list1.get(flightChoice1));
                      System.out.println("\nYou have chosen: ");
                      System.out.println("Option " + flightChoice1 + ": " + list1.get(flightChoice1));
                     }

                    // Same repeating process as done above for DFW homePort but 
                    // slightly modified to account for LAX instead, same algorithm
                    if(homePort.equals("LAX"))
                     {
                       System.out.println("[LAX]->[DFW] Recommended Departure Flights, based on preference: ");
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
                          ListIterator<Flight> itr1 = list1.listIterator();
                          while (itr1.hasNext())
                          {
                            if(itr1.next().connect.contains(layover))
                            {
                              itr1.remove();
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
                          ListIterator<Flight> itr1 = list1.listIterator();
                          while (itr1.hasNext())
                          {
                            if(itr1.next().connect.contains(layover))
                            {
                              itr1.remove();
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

                      Boolean validChoice = true, notInt = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                       do{
                         do{
                             try{
                               flightChoice1 = myObj.nextInt();
                               notInt = false;
                             }catch(InputMismatchException e)
                               {
                                 System.out.println("ERROR: Invalid input type (must be an int value)");
                                 System.out.println("\nPlease input the number corresponding to the flight you would like:"); 
                                 myObj.next();
                               }
                           }while(notInt);
                          if(flightChoice1 >= list1.size())
                          {
                            System.out.println("ERROR: Please enter a valid choice (must be an int)");
                            System.out.println("\nPlease input the number corresponding to the flight you would like:");
                          }
                          else
                          {
                            validChoice = false;
                          }   
                        }while(validChoice);
                       
                      /*Boolean validChoice = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                     do{
                        flightChoice1 = myObj.nextInt();
                        if(flightChoice1 >= list1.size())
                        {
                          System.out.println("Please enter a valid choice");
                        }
                        else
                        {
                          validChoice = false;
                        }   
                      }while(validChoice);*/
                      flightChoiceList.add(list1.get(flightChoice1));
                      System.out.println("\nYou have chosen: ");
                      System.out.println("Option " + flightChoice1 + ": " + list1.get(flightChoice1));
                     }
                    
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Query~Parameters~For~Second~Flight~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
              
                do
                {
                    System.out.println("\nPlease input the parameters for your second flight.");

                    System.out.println("Please rank preferences from a scale of 1-3 \n(1 = no preference, 2 = slight preference, 3 = preferred to utmost): ");

                    Boolean notInt = true;
                    System.out.println("Cheaper Prices: ");
                    do {
                      try{
                        lowCostPref2 = myObj.nextInt();
                        if (lowCostPref2 > 3 || lowCostPref2 < 0)
                        {
                          System.out.println("ERROR: Inputs can only be 3, 2, or 1");
                          System.out.println("\nCheaper Prices: "); 
                        }
                        else
                        {
                          notInt = false;
                        }
                      }catch(InputMismatchException e)
                        {
                          System.out.println("ERROR: Invalid input type (must be an int value)");
                          System.out.println("\nCheaper Prices: "); 
                          myObj.next();
                        }
                    }while(notInt);                    
                  
                    //System.out.println("Cheaper Prices: ");
                    //lowCostPref2 = myObj.nextInt();
                    if(lowCostPref2 == 3)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        try{
                              layoverTimePref2 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                        //layoverTimePref2 = myObj.nextInt();
                        if(layoverTimePref2 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref2 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 1)
                            {
                              checkEarliest2 = false;
                              checkLayOver2 = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)\n");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref2 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref2 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 2)
                            {
                              checkEarliest2 = false;
                              checkLayOver2 = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)\n");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (2 OR 1)\n");
                        }
                      }while(checkLayOver2); 
                    }
                    else if(lowCostPref2 == 2)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        try{
                              layoverTimePref2 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                        //layoverTimePref2 = myObj.nextInt();
                        if(layoverTimePref2 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref2 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 1)
                            {
                              checkEarliest2 = false;
                              checkLayOver2 = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)\n");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref2 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref2 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 3)
                            {
                              checkEarliest2 = false;
                              checkLayOver2 = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)\n");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 1)\n");
                        }
                      }while(checkLayOver2); 
                    }
                    else if(lowCostPref2 == 1)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        try{
                            layoverTimePref2 = myObj.nextInt();
                          }catch(InputMismatchException e)
                          {
                            System.out.println("\nERROR: Invalid input type (must be an int value)");
                            myObj.next();
                          }
                        //layoverTimePref2 = myObj.nextInt();
                        if(layoverTimePref2 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref2 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 2)
                            {
                              checkEarliest2 = false;
                              checkLayOver2 = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)\n");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref2 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref2 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref2 = myObj.nextInt();
                            if(flightTimePref2 == 3)
                            {
                              checkEarliest2 = false;
                              checkLayOver2 = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)\n");
                            }
                          }while(checkEarliest2);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 2)\n");
                        }
                      }while(checkLayOver2); 
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

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Display~Recommendations~Return~Flights~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
              
                if(destPort.equals("DFW"))
                     {
                       System.out.println("[DFW]->[LAX] Recommended Return Flights, based on preference: ");
                       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                       System.out.println("   Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
                        for(int i = 0; i < dblist.size(); i++)
                        {
                          if(dblist.get(i).origin.contains(dfw))
                          {
                            list2.add(dblist.get(i));
                          }
                        }

                       ListIterator<Flight> itr2 = list2.listIterator();
                      while (itr2.hasNext())
                      {
                        if(itr2.next().getDate() < flightChoiceList.get(0).getDate())
                        {
                          itr2.remove();
                        }
                      }
                      //System.out.println("TESTTTTTTTTTTTTTTTTTTT2");
                     
                      /*int tempInitDate = flightChoiceList.get(0).getDate();
                      //int templist2Date = list2.get(i).getDate());
                      ListIterator<Flight> itr2 = list2.listIterator();
                      while (itr2.hasNext())
                      {
                        int templist2Date = itr2.next().getDate();
                        if(templist2Date < tempInitDate)
                        {
                          itr2.remove();
                        }
                      }
                      System.out.println("TESTTTTTTTTTTTTTTTTTTT2");*/
                       
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
                          ListIterator<Flight> itr1 = list2.listIterator();
                          while (itr1.hasNext())
                          {
                            if(itr1.next().connect.contains(layover))
                            {
                              itr1.remove();
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
                          ListIterator<Flight> itr1 = list2.listIterator();
                          while (itr1.hasNext())
                          {
                            if(itr1.next().connect.contains(layover))
                            {
                              itr1.remove();
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

                      if(list2.isEmpty())
                      {
                        System.out.println("\n    *Unfortunately, there are no available return flights*\n                  *Please run program again*\n");
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        System.out.println("Program terminating...");
                        System.exit(0);
                    
                      }
                      else
                      {
                        Boolean validChoice = true, notInt = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                       do{
                         do{
                             try{
                               flightChoice2 = myObj.nextInt();
                               notInt = false;
                             }catch(InputMismatchException e)
                               {
                                 System.out.println("ERROR: Invalid input type (must be an int value)");
                                 System.out.println("\nPlease input the number corresponding to the flight you would like:"); 
                                 myObj.next();
                               }
                           }while(notInt);
                          if(flightChoice2 >= list2.size())
                          {
                            System.out.println("ERROR: Please enter a valid choice (must be an int)");
                            System.out.println("\nPlease input the number corresponding to the flight you would like:");
                          }
                          else
                          {
                            validChoice = false;
                          }   
                        }while(validChoice);
                        
                        
                        /*Boolean validChoice = true; 
                        System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                         do{
                            flightChoice2 = myObj.nextInt();
                            if(flightChoice2 >= list2.size())
                            {
                              System.out.println("Please enter a valid choice");
                            }
                            else
                            {
                              validChoice = false;
                            }   
                          }while(validChoice);*/
                        flightChoiceList.add(list2.get(flightChoice2));
                        System.out.println("\nYou have chosen: ");
                        System.out.println("Option " + flightChoice2 + ": " + list2.get(flightChoice2));
                      }
                      /*Boolean validChoice = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                       do{
                          flightChoice2 = myObj.nextInt();
                          if(flightChoice2 >= list2.size())
                          {
                            System.out.println("Please enter a valid choice");
                          }
                          else
                          {
                            validChoice = false;
                          }   
                        }while(validChoice);
                        flightChoiceList.add(list2.get(flightChoice2));
                        System.out.println("\nYou have chosen: ");
                        System.out.println("Option " + flightChoice2 + ": " + list2.get(flightChoice2));*/
                     }

                    if(destPort.equals("LAX"))
                     {
                       System.out.println("[LAX]->[DFW] Recommended Return Flights, based on preference: ");
                       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                       System.out.println("   Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
                        for(int i = 0; i < dblist.size(); i++)
                        {
                          if(dblist.get(i).origin.contains(lax))
                          {
                            list2.add(dblist.get(i));
                          }
                        }

                      ListIterator<Flight> itr2 = list2.listIterator();
                      while (itr2.hasNext())
                      {
                        if(itr2.next().getDate() < flightChoiceList.get(0).getDate())
                        {
                          itr2.remove();
                        }
                      }
                      //System.out.println("TESTTTTTTTTTTTTTTTTTTT2");
                     
                      /*int tempInitDate = flightChoiceList.get(0).getDate();
                      //int templist2Date = list2.get(i).getDate());
                      ListIterator<Flight> itr2 = list2.listIterator();
                      while (itr2.hasNext())
                      {
                        int templist2Date = itr2.next().getDate();
                        if(templist2Date < tempInitDate)
                        {
                          itr2.remove();
                        }
                      }
                      System.out.println("TESTTTTTTTTTTTTTTTTTTT2");*/
                            
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

                      if(list2.isEmpty())
                      {
                        System.out.println("\n    *Unfortunately, there are no available return flights*\n                  *Please run program again*\n");
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        System.out.println("Program terminating...");
                        System.exit(0);
                        //getUserParameters();
                      }
                      else
                      {

                        Boolean validChoice = true, notInt = true; 
                        System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                         do{
                           do{
                               try{
                                 flightChoice2 = myObj.nextInt();
                                 notInt = false;
                               }catch(InputMismatchException e)
                                 {
                                   System.out.println("ERROR: Invalid input type (must be an int value)");
                                   System.out.println("\nPlease input the number corresponding to the flight you would like:"); 
                                   myObj.next();
                                 }
                             }while(notInt);
                            if(flightChoice2 >= list2.size())
                            {
                              System.out.println("ERROR: Please enter a valid choice (must be an int)");
                              System.out.println("\nPlease input the number corresponding to the flight you would like:");
                            }
                            else
                            {
                              validChoice = false;
                            }   
                          }while(validChoice);
                        
                        /*Boolean validChoice = true; 
                        System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                         do{
                            flightChoice2 = myObj.nextInt();
                            if(flightChoice2 >= list2.size())
                            {
                              System.out.println("Please enter a valid choice");
                            }
                            else
                            {
                              validChoice = false;
                            }   
                          }while(validChoice);*/
                        flightChoiceList.add(list2.get(flightChoice2));
                        System.out.println("\nYou have chosen: ");
                        System.out.println("Option " + flightChoice2 + ": " + list2.get(flightChoice2));
                      }
                      /*Boolean validChoice = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                       do{
                          flightChoice2 = myObj.nextInt();
                          if(flightChoice2 >= list2.size())
                          {
                            System.out.println("Please enter a valid choice");
                          }
                          else
                          {
                            validChoice = false;
                          }   
                        }while(validChoice);
                      flightChoiceList.add(list2.get(flightChoice2));
                      System.out.println("\nYou have chosen: ");
                      System.out.println("Option " + flightChoice2 + ": " + list2.get(flightChoice2));*/
                     }
              
            }

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Begining~Of~One-Way~Flight~Queries~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
            //does one-way flight
            else
            {
                Boolean flightNotComplete = true;
                Boolean checkEarliest = true, checkEarliest2 = true,  checkLayOver = true, checkLayOver2 = true; 
                do
                {
                    System.out.println("\nPlease input your parameters for your flight.");
                    System.out.println("Please rank preferences from a scale of 1-3 \n(1 = no preference, 2 = slight preference, 3 = preferred to utmost): ");

                    Boolean notInt = true;
                    System.out.println("Cheaper Prices: ");
                    do {
                      try{
                        lowCostPref1 = myObj.nextInt();
                        if (lowCostPref1 > 3 || lowCostPref1 < 0)
                        {
                          System.out.println("ERROR: Inputs can only be 3, 2, or 1");
                          System.out.println("\nCheaper Prices: "); 
                        }
                        else
                        {
                          notInt = false;
                        }
                      }catch(InputMismatchException e)
                        {
                          System.out.println("ERROR: Invalid input type (must be an int value)");
                          System.out.println("\nCheaper Prices: "); 
                          myObj.next();
                        }
                    }while(notInt);
                  
                    //System.out.println("Cheaper Prices: ");
                    //lowCostPref1 = myObj.nextInt();
                    if(lowCostPref1 == 3)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        try{
                          layoverTimePref1 = myObj.nextInt();
                        }catch(InputMismatchException e)
                        {
                          System.out.println("\nERROR: Invalid input type (must be an int value)");
                          myObj.next();
                        }
                        //layoverTimePref1 = myObj.nextInt();
                        if(layoverTimePref1 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 1)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref1 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 2)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (2 OR 1)\n");
                        }
                      }while(checkLayOver); 
                    }
                    else if(lowCostPref1 == 2)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        try{
                          layoverTimePref1 = myObj.nextInt();
                        }catch(InputMismatchException e)
                        {
                          System.out.println("\nERROR: Invalid input type (must be an int value)");
                          myObj.next();
                        }
                        //layoverTimePref1 = myObj.nextInt();
                        if(layoverTimePref1 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 1)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (1)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref1 == 1)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 3)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 1)\n");
                        }
                      }while(checkLayOver); 
                    }
                    else if(lowCostPref1 == 1)
                    {
                      do{
                        System.out.println("Not having a layover: ");
                        try{
                          layoverTimePref1 = myObj.nextInt();
                        }catch(InputMismatchException e)
                        {
                          System.out.println("\nERROR: Invalid input type (must be an int value)");
                          myObj.next();
                        }
                        //layoverTimePref1 = myObj.nextInt();
                        if(layoverTimePref1 == 3)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 2)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (2)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else if (layoverTimePref1 == 2)
                        {
                          do{
                            System.out.println("Earliest departure date: ");
                            try{
                              flightTimePref1 = myObj.nextInt();
                            }catch(InputMismatchException e)
                            {
                              System.out.println("\nERROR: Invalid input type (must be an int value)");
                              myObj.next();
                            }
                            //flightTimePref1 = myObj.nextInt();
                            if(flightTimePref1 == 3)
                            {
                              checkEarliest = false;
                              checkLayOver = false;
                            }
                            else
                            {
                              System.out.println("Please enter a valid remaining input, (3)\n");
                            }
                          }while(checkEarliest);
                          //checkLayOver = false;
                        }
                        else
                        {
                          System.out.println("Please enter a valid remaining input, (3 OR 2)\n");
                        }
                      }while(checkLayOver); 
                    }
                    /*lowCostPref1 = getLowCostPref();
                    layoverTimePref1 = getLayoverTimePref();
                    flightTimePref1 = getFlightTimePref();*/
                    String response = queryRestartProcess();
                    if(response.equals("No") || response.equals("no"))
                    {
                        flightNotComplete = false;
                    }
                }while(flightNotComplete);

                if(homePort.equals("DFW"))
                     {
                       System.out.println("[DFW]->[LAX] Recommended Departure Flights, based on preference: ");
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

                       Boolean validChoice = true, notInt = true; 
                        System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                         do{
                           do{
                               try{
                                 flightChoice1 = myObj.nextInt();
                                 notInt = false;
                               }catch(InputMismatchException e)
                                 {
                                   System.out.println("ERROR: Invalid input type (must be an int value)");
                                   System.out.println("\nPlease input the number corresponding to the flight you would like:"); 
                                   myObj.next();
                                 }
                             }while(notInt);
                            if(flightChoice1 >= list1.size())
                            {
                              System.out.println("ERROR: Please enter a valid choice (must be an int)");
                              System.out.println("\nPlease input the number corresponding to the flight you would like:");
                            }
                            else
                            {
                              validChoice = false;
                            }   
                          }while(validChoice);
                       
                       /*Boolean validChoice = true; 
                       System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                       do{
                          flightChoice1 = myObj.nextInt();
                          if(flightChoice1 >= list1.size())
                          {
                            System.out.println("Please enter a valid choice");
                          }
                          else
                          {
                            validChoice = false;
                          }   
                        }while(validChoice);*/
                      flightChoiceList.add(list1.get(flightChoice1));
                      System.out.println("\nYou have chosen: ");
                      System.out.println("Option " + flightChoice1 + ": " + list1.get(flightChoice1));
                     }

                    if(homePort.equals("LAX"))
                     {
                       System.out.println("[LAX]->[DFW] Recommended Departure Flights, based on preference: ");
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

                      Boolean validChoice = true, notInt = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                       do{
                         do{
                             try{
                               flightChoice1 = myObj.nextInt();
                               notInt = false;
                             }catch(InputMismatchException e)
                               {
                                 System.out.println("ERROR: Invalid input type (must be an int value)");
                                 System.out.println("\nPlease input the number corresponding to the flight you would like:"); 
                                 myObj.next();
                               }
                           }while(notInt);
                          if(flightChoice1 >= list1.size())
                          {
                            System.out.println("ERROR: Please enter a valid choice (must be an int)");
                            System.out.println("\nPlease input the number corresponding to the flight you would like:");
                          }
                          else
                          {
                            validChoice = false;
                          }   
                        }while(validChoice);
                       
                      /*Boolean validChoice = true; 
                      System.out.println("\nPlease input the number corresponding to the flight you would like:");    
                       do{
                          flightChoice1 = myObj.nextInt();
                          if(flightChoice1 >= list1.size())
                          {
                            System.out.println("Please enter a valid choice");
                          }
                          else
                          {
                            validChoice = false;
                          }   
                        }while(validChoice);*/
                      flightChoiceList.add(list1.get(flightChoice1));
                      System.out.println("\nYou have chosen: ");
                      System.out.println("Option " + flightChoice1 + ": " + list1.get(flightChoice1));
                     }
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

      if (twoWay)
      {
        System.out.println("\n\n*Flight Confirmation*");
        System.out.println("Departure Flight: ");
        System.out.println("Day   Month   Depart Ori.   Arriv.  Dest.  Connect.   Cost");
        System.out.println(flightChoiceList.get(0));
        System.out.println("Return Flight: ");
        System.out.println("Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
        System.out.println(flightChoiceList.get(1));
      }
      else if(oneWay) 
      {
        System.out.println("\n\n*Flight Confirmation*");
        System.out.println("Departure Flight: ");
        System.out.println("Day   Month   Depart Ori.   Arriv.  Dest.  Connect.   Cost");
        System.out.println(flightChoiceList.get(0));
      }
      /*
      System.out.println("\n\n*Flight Confirmation*");
      System.out.println("Departure Flight: ");
      System.out.println("Day   Month   Depart Ori.   Arriv.  Dest.  Connect.   Cost");
      System.out.println(flightChoiceList.get(0));
      System.out.println("Return Flight: ");
      System.out.println("Day   Month   Depart  Ori.   Arriv.  Dest.  Connect.   Cost");
      System.out.println(flightChoiceList.get(1));
      */

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

  