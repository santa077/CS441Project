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

    //flightList object created to getFlightList db 
    flightList = new FlightList();
    //store db list of flights into ArrayList dblist
    ArrayList<Flight> dblist = flightList.getFlightList();
    //ArrayList list1 generated for holding necessary filtered departure flights
    ArrayList<Flight> list1 = new ArrayList<>();
    //ArrayList list2 generated for holding necessary filtered return flights
    ArrayList<Flight> list2 = new ArrayList<>();
    //ArrayList flightChoiceList for storing user picked flights (departure/return)
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
            // Queries for users inputted info related to departure and arrival point, two or one way, and if
            // they would like to reset their inputs. Wrapped within a do, while with necessary if statements
            // for handling edge error cases
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
                    //Queries related to parameters for users first flight if two way chosen
                    //wrapped in necessary do while loops for repeating queries if incorrect input is
                    //found, also if Mismatch variable type input is caught, try..catch statements
                    //are assigned to handle those possible error user inputs as well

                    //User inputs for preferences are on a ranking basis, only allowing 3, 2, 1 to be given 
                    //per preference, not accepting repeatable ranks
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
                    //Populates and displays recommended departure flights dependent on initial inputted
                    //homePorts (DFW/LAX). Pulls db list and stores related flights to list1 for storing/usage
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
                        //Cost pref is highest weight, so sort (low -> high) based on cost. Date will still populate
                        //but doesn't hold as much weight compared to cost
                        else if(lowCostPref1 == 3 && layoverTimePref1 == 1 && flightTimePref1 == 2)
                        {
                          Collections.sort(list1, new Flight.CostComparator());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        //flightTimePref holds highest weight, so we sort list (low -> high) based on departure dates
                        else if(lowCostPref1 == 2 && layoverTimePref1 == 1 && flightTimePref1 == 3)
                        {
                          Collections.sort(list1, new Flight.DateComparator());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        //layoverTimePref holds highest weight so the logic is to use a listIterator to 
                        //iterate through list1's indexes looking for any flights that contain a layover
                        //Since customer prefers no layovers in this case, we remove all of those indexes
                        //containing a layover from the ArrayList
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
                          //then we sort based on cost since lowCostPref is second highest rank
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
                          //sort based on flight dates since second highest rank
                          Collections.sort(list1, new Flight.DateComparator());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }
                        //flightTimePref is ranked highest so we sort based on dates (low -> high)
                        //since layoverTimePref isn't highly preferred, we still generate flights that 
                        //are both direct and layover however, we reverse sort order to display direct-
                        //flights first to the user for recommendations
                        else if(lowCostPref1 == 1 && layoverTimePref1 == 2 && flightTimePref1 == 3)
                        {
                          Collections.sort(list1, new Flight.DateComparator());
                          Collections.sort(list1, Collections.reverseOrder());
                          for (int i = 0; i < list1.size(); i++)
                          {
                            System.out.println(i+": " +list1.get(i));
                          }
                        }

                      //Takes user's input choice for which flight from the recommended list they would like 
                      //to pick for their initial departure flight. 
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
                      //Users flight choice is added to ArrayList flightChoiceList to be stored for later
                      //Generate a confirmation to user of their chosen flight
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
                //Same process as previously when querying for first flight, but now modified 
                //for handling second flight since it is a two-way flight that the user has chosen
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
                //Same process as previously done (departure flights), this time modified for handling recommendations for return flights
                //Slight modification includes a check for compiled list of departure flight recommendations against
                //Users initial chosen flight date to ensure that only flights after the initial chosen departure date
                //are recomended, as any flights before the users initial flight date would not make sense to be shown
                //Displays nicely populated flights related to users set departure point
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

                      //For recommendations of second flight options, if user happens to choose an edge date for their
                      //initial flight but no available dates into the future are available (list of flights is empty)
                      //User is presented with a message of the unfortunate inconvenience and notifies user
                      //to restart software in order to input new parameters/preferences and terminates.
                      if(list2.isEmpty())
                      {
                        System.out.println("\n    *Unfortunately, there are no available return flights*\n                  *Please run program again*\n");
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        System.out.println("Program terminating...");
                        System.exit(0);
                    
                      }
                      else
                      {
                      //Takes users input for second flight choice to be stored for later
                      //users choice is stored within var flightChoice2 as an int
                      //do..while loops and try,catch statements utilized to handle invalid input cases
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
                        //flightChoice2 from list2 is added to flightChoiceList
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

                    //Same process as handled before for DFW, now handles LAX if users destPort is LAX
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

                      //Similar repeated process for handling no available flights when destPort was set to DFW
                      //now for handlig destPort of LAX
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

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Begining~Of~One~Way~Flight~Queries~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
            //does one-way flight
            else
            {
                //Logic+algorithm implementations are mimic'd from two-way query logic, however,
                //since this is only a one-way flight, logic+algo for checking second flight against
                //first is not necessary anymore
                //Simply queries user for preferences related to their one-way flight
                //then generates a list of recommended flights based on those preferences
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
            //checks for twoWayFlight, if so generate output of users initial inputs for two-way
            //else generate output of users initial inputs for one-way
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
      //Checks if two-way else one-way but this time, generates an confirmation output of users 
      //chosen flights. If two-way, generates both the initial departure flight as well as the return flight 
      //chosen. If one-way, simply generates a confirmation of the users direct one-way flight
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

  