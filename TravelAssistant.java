import java.util.*;

/**
 * TravelAssistant --- program to optimize travel paths from one city to another
 *
 * @author : Shivangi Bhatt
 */
public class TravelAssistant {

    //Create a new instance of object graph
    Graph cities = new Graph();


    /*
     * method name : findCity
     * method purpose : finds the City with given cityName from the graph.
     * arguments : Graph of cities, string of city name
     * return value : Returns city object if it exists and null otherwise.
     */
    private City findCity(Graph cities, String cityName) {
        return cities.getCities().stream().filter(city -> cityName.equalsIgnoreCase(city.getCityName())).findFirst().orElse(null);
    }

    /*
     * method name : addCity
     * method purpose : Creates the object of city and adds it to the graph.
     * arguments : string of city name, boolean testRequired, integer timeToTest, integer nightlyHotelCost
     * return value : Returns true if the object city is added to the graph successfully, throws IllegalArgumentException if the arguments are not acceptable and false otherwise.
     */
    boolean addCity(String cityName, boolean testRequired, int timeToTest, int nightlyHotelCost) throws IllegalArgumentException {

        //Check if the arguments are valid
        if (cityName == null || cityName.equalsIgnoreCase("") || nightlyHotelCost < 1) {
            //if invalid throw IllegalArgumentException
            throw new IllegalArgumentException("Invalid input data");
        }
        City foundCity = findCity(cities, cityName);
        //Check if the city does not exist in the graph already
        if (foundCity == null) {
            // If it does not exist in the graph, add it
            City city = new City(cityName, testRequired, timeToTest, nightlyHotelCost);
            cities.addCities(city);
            return true;
        }
        //Check if the city exists with same other data.
        else if (foundCity != null && foundCity.isTestRequired() == testRequired && foundCity.getTimeToTest() == timeToTest && foundCity.getNightlyHotelCost() == nightlyHotelCost) {
            return true;
        } else {
            //return false otherwise
            return false;
        }
    }


    /*
     * method name : addFlight
     * method purpose : Creates the object of Transport and with mode of transport fly and add the connecting destination city to startCity object.
     * arguments : String startCity, String destinationCity, int flightTime, int flightCost
     * return value : Returns true if the object transport is added to the graph successfully, throws IllegalArgumentException if the arguments are not acceptable and false otherwise.
     */
    boolean addFlight(String startCity, String destinationCity, int flightTime, int flightCost) throws IllegalArgumentException {
        //Check if the arguments are valid
        if (startCity == null || startCity.equalsIgnoreCase("") || destinationCity == null || destinationCity.equalsIgnoreCase("") || flightTime < 1 || flightCost < 1) {
            throw new IllegalArgumentException("Invalid input data");
        }
        //Find the object for startCity and destinationCity from graph
        City startCityObject = findCity(cities, startCity);
        City destinationCityObject = findCity(cities, destinationCity);

        boolean output;
        //check if the startCity object or destinationCity object is null
        if (startCityObject == null || destinationCityObject == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        // check if both start city and destination city are same
        else if (startCityObject == destinationCityObject) {
            throw new IllegalArgumentException("startCity and destinationCity are same");

        } else {
            //create the object transport and add the connecting destination city to startCity object
            Transport flight = new Transport(startCityObject, destinationCityObject, "fly", flightTime, flightCost);

            output = startCityObject.addConnectingCity(destinationCityObject, flight);
        }

        return output;

    }

    /*
     * method name : addTrain
     * method purpose : Creates the object of Transport and with mode of transport fly and adds the connecting destination city to startCity object.
     * arguments : String startCity, String destinationCity, int flightTime, int flightCost
     * return value : Returns true if the object transport is added to the graph successfully, throws IllegalArgumentException if the arguments are not acceptable and false otherwise.
     */
    boolean addTrain(String startCity, String destinationCity, int trainTime, int trainCost) throws IllegalArgumentException {
        //Check if the arguments are valid
        if (startCity == null || startCity.equalsIgnoreCase("") || destinationCity == null || destinationCity.equalsIgnoreCase("") || trainTime < 1 || trainCost < 1) {
            throw new IllegalArgumentException("Invalid input data");
        }

        //Find the object for startCity and destinationCity from graph
        City startCityObject = findCity(cities, startCity);
        City destinationCityObject = findCity(cities, destinationCity);

        boolean output;

        //check if the startCity object or destinationCity object is null
        if (startCityObject == null || destinationCityObject == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        // check if both start city and destination city are same
        else if (startCityObject == destinationCityObject) {
            throw new IllegalArgumentException("startCity and destinationCity are same");

        } else {
            //create the object transport and add the connecting destination city to startCity object
            Transport train = new Transport(startCityObject, destinationCityObject, "train", trainTime, trainCost);
            output = startCityObject.addConnectingCity(destinationCityObject, train);
        }
        return output;

    }

    /*
     * method name : planTrip
     * method purpose : Returns the optimized list of paths for the traveller to travel from start city to destination city
     * arguments : String startCity, String destinationCity, boolean isVaccinated, int costImportance, int travelTimeImportance, int travelHopImportance
     * return value : Returns true if the object transport is added to the graph successfully, throws IllegalArgumentException if the arguments are not acceptable and false otherwise.
     */
    List<String> planTrip(String startCity, String destinationCity, boolean isVaccinated, int costImportance, int travelTimeImportance, int travelHopImportance) throws IllegalArgumentException {
        //Check if the arguments are valid
        if (startCity == null || startCity.equalsIgnoreCase("") || destinationCity == null || destinationCity.equalsIgnoreCase("") || costImportance < 0 || travelTimeImportance < 0 || travelHopImportance < 0) {
            throw new IllegalArgumentException("Invalid input data");
        }

        //Find the object for startCity and destinationCity from graph
        City startCityObject = findCity(cities, startCity);
        City destinationCityObject = findCity(cities, destinationCity);

        //iterate through each city in the  graph
        for (City city : cities.getCities()) {
            //clear shortest path for the city object
            city.getShortestPath().clear();
            //set the distance of the city to Integer.MAX_VALUE
            city.setDistance(Integer.MAX_VALUE);
        }

        //check if the startCity object or destinationCity object is null
        if (startCityObject == null || destinationCityObject == null) {
            throw new IllegalArgumentException("Cities does not exist");

        }
        //Check if the object for startCity and destinationCity are not null and not equal
        else if (startCityObject != null && destinationCityObject != null && startCityObject != destinationCityObject) {
            //calculate the shortest path for each node in the graph
            Graph updatedGraph = cities.calculateShortestPathBetweenCities(cities, startCityObject, destinationCityObject, isVaccinated, costImportance, travelTimeImportance, travelHopImportance);

            //find the city in the new updated graph
            City updatedDestinationCityObject = findCity(updatedGraph, destinationCity);
            //Initialize output list
            List<String> outputList = new ArrayList<>();
            //Check if the size of the shortest path is not 0 for updated DestinationCity Object
            if (updatedDestinationCityObject.getShortestPath().size() != 0) {
                //Iterate through each element in the list
                for (Map<City, Transport> city : updatedDestinationCityObject.getShortestPath()) {
                    //Iterate through each element int the map
                    for (Map.Entry<City, Transport> adjacencyPair : city.entrySet()) {
                        //Add the string to the output list
                        int currentIndex = updatedDestinationCityObject.getShortestPath().indexOf(city);
                        if (currentIndex == 0) {
                            outputList.add("start " + adjacencyPair.getKey().getCityName());
                        }
                        outputList.add(adjacencyPair.getValue().getMeansOfTransport() + " " + adjacencyPair.getValue().getDestinationCity().getCityName());

                    }
                }

                return outputList;
            }
        }
        //Check if the object for startCity and destinationCity are not null and are equal
        else if (startCityObject != null && destinationCityObject != null && startCityObject == destinationCityObject) {
            // initialize the arrayList and add the string of path to the list
            List<String> outputList = new ArrayList<>();
            outputList.add("start " + startCityObject.getCityName());
            return outputList;

        }
        return null;


    }


}
