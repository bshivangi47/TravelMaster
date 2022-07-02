import java.util.*;

/**
 *
 * City --- Supporting class City
 * @author : Shivangi Bhatt
 *
 */
public class Graph {

    //initialize class variable
    private Set<City> cities = new HashSet<>();

    /*
     * method name : getCities
     * method purpose : get the cities
     * arguments : none
     * return value : returns Hashset of cities
     */
    public Set<City> getCities() {
        return cities;
    }

    /*
     * method name : addCities
     * method purpose : add the cities to HashSet cities
     * arguments : none
     * return value : void
     */
    public void addCities(City city) {
        cities.add(city);
    }

    /*
     * method name : calculateShortestPathBetweenCities
     * method purpose : calculates the shortest path for each city in the hash set of cities from the given startCity
     * arguments : none
     * return value : returns the updated graph
     */
    Graph calculateShortestPathBetweenCities(Graph graph, City startCity, City destinationCity, boolean isVaccinated, int costImportance, int travelTimeImportance, int travelHopImportance) {
        //set the distance of startCity to 0
        startCity.setDistance(0);

        //settledCities stores the cities whose shortest path from the startCity is calculated
        Set<City> settledCities = new HashSet<>();
        //unsettledCities stores the cities whose shortest path from the startCity are yet to be calculated
        Set<City> unsettledCities = new HashSet<>();

        //add startCity to the unsettled Cities
        unsettledCities.add(startCity);
        
        while (unsettledCities.size() != 0) {
            //get the city with the lowest distance from the unsettledCities
            City currentCity = getCityWithLowestDistance(unsettledCities);
            unsettledCities.remove(currentCity);
            
            //if not vaccinated and testing is available at current city
            if (!isVaccinated && currentCity.getTimeToTest() > 0 ) {
                //isVaccinated is set to true
                isVaccinated = true;
            }
            //Iterate through all the adjacent city of the currentCity
            for (Map.Entry<City, List<Transport>> connectingCity :
                    currentCity.getConnectingCities().entrySet()) {
                boolean covidTestTaken = false;
                
                //get the adjacentCity
                City adjacentCity = connectingCity.getKey();
                List<Transport> possibleTransports = connectingCity.getValue();
                
                //Check if test is required at the adjacentCity and if traveller is not vaccinated and covid test has not been taken 
                if(adjacentCity.isTestRequired() && !isVaccinated && !covidTestTaken){
                    //move to the  next adjacentNode
                    continue;
                }
                int hotelCost = 0;
                
                //Check if covid test is not taken and traveller is notvaccinated and testing is available at the adjacentCity
                if (!covidTestTaken && !isVaccinated && adjacentCity.getTimeToTest() > 0 ) {
                    //calculate the total hotel cost
                    hotelCost = adjacentCity.getNightlyHotelCost() * adjacentCity.getTimeToTest();
                    //The covid test has been taken
                    covidTestTaken = true;
                }

                //Check if test is required at the destination city and traveller has not taken covid test and is not vaccinated
                if ( destinationCity.isTestRequired() && !isVaccinated && !covidTestTaken) {
                    //if true move to the next adjacent Node
                    continue;
                }
                //Loop through all the possible transports between current City and adjacentCity
                for (Transport transport : possibleTransports) {
                    //If adjacent Vertes iz not settled
                    if (!settledCities.contains(adjacentCity)) {
                        
                        //Calculate the weight of travelling from current City to adjacentCity
                        int weight = (transport.getTime() * travelTimeImportance) + (transport.getCost() * costImportance) + travelHopImportance + (hotelCost * costImportance);
                        calculateMinimumDistance(adjacentCity, weight, currentCity, transport);
                        
                        //add the adjacentCity to set of unsettledCities
                        unsettledCities.add(adjacentCity);
                    }
                }

            }
            //add the currentCity to settledCities
            settledCities.add(currentCity);
        }

        return graph;
    }

    /*
     * method name : getCityWithLowestDistance
     * method purpose : Gets the city from the set with lowest distance from the set of unsettledCities
     * arguments : Set<City> unsettledCities
     * return value : returns the City
     */
    private City getCityWithLowestDistance(Set<City> unsettledCities) {
        // set the lowesDistanceCity to null
        City lowestDistanceCity = null;
        //set the lowestDisance to Integer.MAX_VALUE
        int lowestDistance = Integer.MAX_VALUE;
        //Loop through the unsettledCities
        for (City city : unsettledCities) {
            int nodeDistance = city.getDistance();
            //Check if distance of the city is less than the lowestDistance
            if (nodeDistance < lowestDistance) {
                //if true set the lowestDistance to distance of the city
                lowestDistance = nodeDistance;
                //set the lowesDistanceCity to city
                lowestDistanceCity = city;
            }
        }
        // return lowestDistanceCity
        return lowestDistanceCity;
    }

    /*
     * method name : calculateMinimumDistance
     * method purpose : calculates the minimum distance between evaluationCity and SourceCity
     * arguments : City evaluationCity, Integer edgeWeigh, City sourceCity, Transport transport
     * return value : void
     */
    private void calculateMinimumDistance(City evaluationCity, Integer edgeWeigh, City sourceCity, Transport transport) {
        //get the distance of source city
        Integer sourceDistance = sourceCity.getDistance();

        //Check if sourceDistance+edgeWigh id less than the distance of evaluationCity
        if (sourceDistance + edgeWeigh < evaluationCity.getDistance()) {
            //if true, set the distance of evaluationCity to sourceDistance+edgeWeigh
            evaluationCity.setDistance(sourceDistance + edgeWeigh);
            //Initialize a shortestpath LinkedList with shortestPath to the source.
            LinkedList<Map<City, Transport>> shortestPath = new LinkedList<>(sourceCity.getShortestPath());
            Map<City, Transport> shortestPathToTravel = new HashMap<>();
            //Add sourceCity and transport to shortestPath
            shortestPathToTravel.put(sourceCity, transport);
            shortestPath.add(shortestPathToTravel);
            //set the shortestPath of the evaluation city as shortestPath
            evaluationCity.setShortestPath(shortestPath);
        }
    }


}