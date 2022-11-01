import java.lang.String;
import java.util.Scanner;
//System.out.println("Username is: " + userName);  // Output user input


class Main 
{

    public static void Parameters(String initialPort, String finalPort, String flightEarliestDate,
            String flightLatestDate, int maxTransfers, int lowCostPref, int layoverTimePref, int flightTimePref) 
    {

    }
    public static int getMaxTransfers() //function for getting the max amount of transfers from plane to plane a customer will want
    {
        Scanner myObj = new Scanner(System.in);
        int max ;
        System.out.println("Please enter your max amount of tranfers");
        max = myObj.nextInt();
        return max;
    }
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
        System.out.println("Please enter how you much you prefer not having layover time 1-5 (1 meaning no preference, 5 meaning preferred to utmost)");
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
    public static void main(String[] args) 
    {  
        Scanner myObj = new Scanner(System.in);
        boolean notComplete = true;
        do 
        {
            Boolean twoWayFlightCheck;
            String twoWayFlight, homePort, destinationPort;

            //takes in the customer's home port and destination port (should be made into function)
            System.out.println("Please enter you depature point. (Dallas/LA)"); 
            homePort = myObj.nextLine();  // Read user input
            System.out.println("Please enter you arrival point. (Dallas/LA)");
            destinationPort = myObj.nextLine();                                 

            //queries the customer for if this is a two-way flight (should be made into function)
            System.out.println("Is this a two-way flight? (Yes/No)");
            twoWayFlight = myObj.nextLine();

            //sets a flag for if two-way flight or not
            if (twoWayFlight.equals("Yes") || twoWayFlight.equals("yes"))
            {
                twoWayFlightCheck = true;
            }
            else
            {
                twoWayFlightCheck = false;
            }

            //does two way flight
            if(twoWayFlightCheck)
            {
                Boolean firstFlightNotComplete = true;
                Boolean secondFlightNotComplete = true;
                do
                {
                    System.out.println("**You are in first flight**");
                    getMaxTransfers();
                    getLowCostPref();
                    getLayoverTimePref();
                    getFlightTimePref();
                    String response = queryRestartProcess();
                    System.out.println(response);
                    if(response.equals("No") || response.equals("no"))
                    {
                        firstFlightNotComplete = false;
                    }
                }while(firstFlightNotComplete);
                do
                {
                    System.out.println("**You are in second flight**");
                    getMaxTransfers();
                    getLowCostPref();
                    getLayoverTimePref();
                    getFlightTimePref();
                    String response = queryRestartProcess();
                    System.out.println(response);
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
                    System.out.println("**You are in one flight**");
                    getMaxTransfers();
                    getLowCostPref();
                    getLayoverTimePref();
                    getFlightTimePref();
                    String response = queryRestartProcess();
                    if(response.equals("No") || response.equals("no"))
                    {
                        flightNotComplete = false;
                    }
                }while(flightNotComplete);
            }
        
        
            ///////////////// Outputting Section /////////////////////////
            System.out.println("Homeport is: " + homePort);
            System.out.println("Destination port is: " + destinationPort);
            if(twoWayFlightCheck)
            {
                System.out.println("This is a two-way flight");
            }
            else
            {
                System.out.println("This is a one-way flight");
            }
            notComplete = false;
        }while(notComplete);
    }

}