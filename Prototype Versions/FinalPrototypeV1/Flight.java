public class Flight 
{
  private String month, origin, destination, connect;
  private int date, depart, arrival, cost;

  public Flight(int date, String month, int depart, String origin, int arrival, String destination, String connect, int cost)
  {
    //super();
    this.date = date;
    this.month = month;
    this.depart = depart;
    this.origin = origin;
    this.arrival = arrival;
    this.destination = destination;
    this.connect = connect;
    this.cost = cost;
  }
  
  public int getDate()
  {
    return date;
  }

  public void setDate(int date)
  {
    this.date = date;
  }
  
  public String getMonth()
  {
    return month;
  }

  public void setMonth(String month)
  {
    this.month = month;
  }

  public int getDepart()
  {
    return depart;
  }

  public void setDepart(int depart)
  {
    this.depart = depart;
  }

  public String getOrigin()
  {
    return origin;
  }

  public void setOrigin(String origin)
  {
    this.origin = origin;
  }

  public int getArrival()
  {
    return arrival;
  }

  public void setArrival(int arrival)
  {
    this.arrival = arrival;
  }

  public String getDestination()
  {
    return destination;
  }

  public void setDestination(String destination)
  {
    this.destination = destination;
  }

  public String getConnect()
  {
    return connect;
  }

  public void setConnect(String connect)
  {
    this.connect = connect;
  }

  public int getCost()
  {
    return cost;
  }

  public void setCost(int cost)
  {
    this.cost = cost;
  }

  public String toString() 
  {
    /*return "Flight\n{" +
            "Date='" + date + '\'' +
            ", Month='" + month + '\'' +
            ", Depart='" + depart + '\'' +
            ", Origin='" + origin + '\'' +
            ", Arrival='" + arrival + '\'' +
            ", Destination='" + destination + '\'' +
            ", Connection='" + connect + '\'' +
            ", Cost= $" + cost +
            '}';*/
    return date + "    " + month + "     " + depart + "    " + origin + "    " + arrival + "    " + destination + "    " + connect + "    " + cost;
  }
}