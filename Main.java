import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TravelAssistant travelAssistant = new TravelAssistant();

        Scanner userInput = new Scanner(System.in);
        String userCommand = "";

        String addCityCommand = "addCity";
        String addFlightCommand = "addFlight";
        String addTrainCommand = "addTrain";
        String planTripCommand = "planTrip";
        String quitCommand = "quit";

        System.out.println("Commands available:");
        System.out.println("  " + addCityCommand + " <cityName> <testRequired> <timeToTest> <nightlyHotelCost>");
        System.out.println("  " + addFlightCommand + " <startCity> <destinationCity> <flightTime> <flightCost>");
        System.out.println("  " + addTrainCommand + " <startCity> <destinationCity> <trainTime> <trainCost>");
        System.out.println("  " + planTripCommand + " <startCity> <destinationCity> <isVaccinated> <costImportance> <travelTimeImportance> <travelHopImportance>");
        System.out.println("  " + quitCommand);

        do {
            // Find out what the user wants to do
            userCommand = userInput.next();

                        /* Do what the user asked for.  If condition for each command.  Since each command
                           has a different number of parameters, we do separate handling of each command. */

            if (userCommand.equalsIgnoreCase(addCityCommand)) {
                String cityName = userInput.next();
                Boolean testRequired = userInput.nextBoolean();
                int timeToTest = userInput.nextInt();
                int nightlyHotelCost = userInput.nextInt();
                boolean output = travelAssistant.addCity(cityName, testRequired, timeToTest, nightlyHotelCost);
                System.out.println(output);

            } else if (userCommand.equalsIgnoreCase(addFlightCommand)) {
                String startCity = userInput.next();
                String destinationCity = userInput.next();
                int flightTime = userInput.nextInt();
                int flightCost = userInput.nextInt();
                boolean output = travelAssistant.addFlight(startCity, destinationCity, flightTime, flightCost);
                System.out.println(output);

            } else if (userCommand.equalsIgnoreCase(addTrainCommand)) {
                String startCity = userInput.next();
                String destinationCity = userInput.next();
                int trainTime = userInput.nextInt();
                int trainCost = userInput.nextInt();
                boolean output = travelAssistant.addTrain(startCity, destinationCity, trainTime, trainCost);
                System.out.println(output);

            } else if (userCommand.equalsIgnoreCase(planTripCommand)) {
                String startCity = userInput.next();
                String destinationCity = userInput.next();
                Boolean isVaccinated = userInput.nextBoolean();
                int costImportance = userInput.nextInt();
                int travelTimeImportance = userInput.nextInt();
                int travelHopImportance = userInput.nextInt();
                List<String> planTrip = travelAssistant.planTrip(startCity, destinationCity, isVaccinated, costImportance, travelTimeImportance, travelHopImportance);
                if (planTrip==null) {
                    System.out.println("Trip is empty");
                } else {
                    System.out.println(Arrays.toString(planTrip.toArray()));

                }

            } else if (userCommand.equalsIgnoreCase(quitCommand)) {
                System.out.println(userCommand);
            } else {
                System.out.println("Bad command: " + userCommand);
            }
        } while (!userCommand.equalsIgnoreCase("quit"));


    }

}
