/**
 *
 * City --- Supporting class City
 * @author : Shivangi Bhatt
 *
 */

public class Transport {

    //define the class variables
    private City startCity;
    private City destinationCity;
    private String meansOfTransport;
    private int time;
    private int cost;

    /*
     * Constructor : Transport
     * method purpose : Initialize the variables of object
     * arguments : City startCity, City destinationCity,String meansOfTransport, int time, int cost
     */
    public Transport(City startCity, City destinationCity,String meansOfTransport, int time, int cost ) {
        this.startCity =startCity ;
        this.destinationCity = destinationCity;
        this.meansOfTransport = meansOfTransport;
        this.time = time;
        this.cost = cost;
    }


    /*
     * method name : getDestinationCity
     * method purpose : get the destinationCity
     * arguments : none
     * return value : returns Object of type City of destinationCity
     */
    public City getDestinationCity() {
        return destinationCity;
    }

    /*
     * method name : getTime
     * method purpose : get the time
     * arguments : none
     * return value : returns integer value of time
     */
    public int getTime() {
        return time;
    }

    /*
     * method name : getCost
     * method purpose : get the cost
     * arguments : none
     * return value : returns integer value of cost
     */
    public int getCost() {
        return cost;
    }

    /*
     * method name : getMeansOfTransport
     * method purpose : get the meansOfTransport
     * arguments : none
     * return value : returns string of meansOfTransport
     */
    public String getMeansOfTransport() {
        return meansOfTransport;
    }


}
