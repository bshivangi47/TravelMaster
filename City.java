import java.util.*;

/**
 *
 * City --- Supporting class City
 * @author : Shivangi Bhatt
 *
 */
public class City {

    //define the class variables
    private String cityName;
    private boolean testRequired;
    private int timeToTest;
    private int nightlyHotelCost;

    private Map<City, List<Transport>> connectingCities = new HashMap<>();

    private List<Map<City, Transport>> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;


    /*
     * Constructor : City
     * method purpose : Initialize the variables of object
     * arguments : String cityName, boolean testRequired, int timeToTest, int nightlyHotelCost
     */
    public City(String cityName, boolean testRequired, int timeToTest, int nightlyHotelCost) {
        this.cityName = cityName;
        this.testRequired = testRequired;
        this.timeToTest = timeToTest;
        this.nightlyHotelCost = nightlyHotelCost;
    }

    /*
     * method name : addConnectingCity
     * method purpose : adds the connected city to the hashmap connectingCities
     * arguments : City destination, Transport transport
     * return value : returns true if the destination city and transport are successfully added to the hashmap and false if there already exists transport between the cities
     */
    public boolean addConnectingCity(City destination, Transport transport)  throws IllegalArgumentException{
        List<Transport> availableTransportsList;
        //Check if the destination already exists a key in connectingCities
        if (connectingCities.containsKey(destination)) {
            availableTransportsList = connectingCities.get(destination);
            //return false if there already exists the same mode of transport between the cities
            if (transportExists(availableTransportsList, transport.getMeansOfTransport())) {
                throw new IllegalArgumentException("A path already exist with same means of transport between two cities");
            }

        } else {
            //If destination does not exist as a key initialize the availableTransportsList
            availableTransportsList = new ArrayList<>();
        }
        //add the transport to availableTransportsList
        availableTransportsList.add(transport);
        //add the key-value pair to connectingCities.
        connectingCities.put(destination, availableTransportsList);
        return true;
    }

    /*
     * method name : getCityName
     * method purpose : get the cityName
     * arguments : none
     * return value : returns string of cityName
     */
    public String getCityName() {
        return cityName;
    }

    /*
     * method name : isTestRequired
     * method purpose : get the testRequired
     * arguments : none
     * return value : returns boolean value of testRequired
     */
    public boolean isTestRequired() {
        return testRequired;
    }

    /*
     * method name : getTimeToTest
     * method purpose : get the timeToTest
     * arguments : none
     * return value : returns integer value of timeToTest
     */
    public int getTimeToTest() {
        return timeToTest;
    }

    /*
     * method name : getNightlyHotelCost
     * method purpose : get the nightlyHotelCost
     * arguments : none
     * return value : returns integer value of nightlyHotelCost
     */
    public int getNightlyHotelCost() {
        return nightlyHotelCost;
    }

    /*
     * method name : transportExists
     * method purpose : To check if transport with same means of transport exist
     * arguments : none
     * return value : returns true if transport with same means of transport exist and false otherwise
     */
    private boolean transportExists(List<Transport> availableTransportsList, String meansOfTransport) {
        return availableTransportsList.stream().anyMatch(transport -> meansOfTransport.equalsIgnoreCase(transport.getMeansOfTransport()));
    }

    /*
     * method name : getConnectingCities
     * method purpose : get the connectingCities
     * arguments : none
     * return value : returns map of connectingCities
     */
    public Map<City, List<Transport>> getConnectingCities() {
        return connectingCities;
    }

    /*
     * method name : getDistance
     * method purpose : get the distance
     * arguments : none
     * return value : returns integer value of distance
     */
    public Integer getDistance() {
        return distance;
    }

    /*
     * method name : setDistance
     * method purpose : set the value of distance
     * arguments : Integer distance
     * return value : void
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /*
     * method name : getShortestPath
     * method purpose : get the getShortestPath
     * arguments : none
     * return value : returns List of getShortestPath
     */
    public List<Map<City, Transport>> getShortestPath() {
        return shortestPath;
    }

    /*
     * method name : setShortestPath
     * method purpose : set the shortest path
     * arguments : List<Map<City,Transport>> shortestPath
     * return value : void
     */
    public void setShortestPath(List<Map<City, Transport>> shortestPath) {
        this.shortestPath = shortestPath;
    }
}
