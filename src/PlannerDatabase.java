import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;

/**
 * Amtrak Travel Planner - PlannerDatabase
 *
 * A PlannerDatabase class for the Amtrak Travel
 * Planner, which connects to the Amtrak API, fetches
 * station and train data, parses it, and stores it
 * appropriately for use throughout the program. This
 * class also provides Route-finding, search, and
 * search file functionality.
 *
 * @author Ayush Shukla Arora, L19
 *
 * @version April 22, 2024
 */

public class PlannerDatabase {
    private static URL stationsURL;
    private static URL trainsURL;
    private static Station[] stations;
    private static String[] stationStrings;
    private static ArrayList<Station> stationsArrayList =  new ArrayList<>();
    private static ArrayList<Train> trainsInDatabase = new ArrayList<>();
    private final static String searchOutputFileString = "Amtrak Travel Planner Search Preset.txt";
    private final static String resultsOutputFileString = "Amtrak Travel Planner Search Results.txt";


    static {
        try {
            stationsURL = new URL("https://mgwalker.github.io/amtrak-api/stations.json");
            trainsURL = new URL("https://mgwalker.github.io/amtrak-api/trains.json");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    public PlannerDatabase() throws MalformedURLException {
    }

    public static Station[] getTrainStations() throws IOException, ParseException {
        HttpURLConnection connStations = (HttpURLConnection) stationsURL.openConnection();
        connStations.setRequestMethod("GET");
        connStations.connect();
        int stationsResponseCode = connStations.getResponseCode();

        if (stationsResponseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + stationsResponseCode);
        } else {
            String inline = "";
            Scanner scanner = new Scanner(stationsURL.openStream());
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();
            JSONParser parse = new JSONParser();
            JSONArray stationArray = (JSONArray) parse.parse(inline);
            for (int i = 0; i < stationArray.size(); i++) {
                JSONObject stationObject = (JSONObject) stationArray.get(i);
                String stationCode = (String) stationObject.get("code");
                String stationName = (String) stationObject.get("name");
                Station station = new Station(stationCode, stationName);
                stationsArrayList.add(station);
            }
        }
        stations = new Station[stationsArrayList.size()];
        for (int i = 0; i < stations.length; i++) {
            stations[i] = stationsArrayList.get(i);
        }
        return stations;
    }

    public static String[] getTrainStationStrings() throws IOException, ParseException {
        getTrainStations();
        ArrayList<String> stringsArrayList = new ArrayList<>();
        for (Station station : stations) {
            stringsArrayList.add(station.toString());
        }
        stationStrings = new String[stringsArrayList.size()];
        for (int i = 0; i < stationStrings.length; i++) {
            stationStrings[i] = stringsArrayList.get(i);
        }
        return stationStrings;
    }

    public static Station getStationfromString(String stationString) throws IOException, ParseException {
        for (Station station : stations) {
            if (station.toString().equals(stationString)) {
                return station;
            }
        }
        return null; //should never happen
    }

    public static Station getStationfromCodeString(String stationCodeString) throws IOException, ParseException {
        for (Station station : stations) {
            if (station.getCode().toString().equals(stationCodeString)) {
                return station;
            }
        }
        return null;
    }

    public static void storeTrains() throws IOException, ParseException {
        HttpURLConnection connTrains = (HttpURLConnection) trainsURL.openConnection();
        connTrains.setRequestMethod("GET");
        connTrains.connect();
        int stationsResponseCode = connTrains.getResponseCode();

        if (stationsResponseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + stationsResponseCode);
        } else {
            String inline = "";
            Scanner scanner = new Scanner(trainsURL.openStream());
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();
            JSONParser parse = new JSONParser();
            JSONArray trains = (JSONArray) parse.parse(inline);
            for (int i = 0; i < trains.size(); i++) {
                JSONObject train = (JSONObject) trains.get(i);
                String trainName = (String) train.get("route"); //line names (e.g., CalZephyr)
                Long trainNumber = (Long) train.get("number");
                JSONArray trainStations = (JSONArray) train.get("stations");
                ArrayList<String> servedStationCodes = new ArrayList<>();
                ArrayList<String> stationStatuses = new ArrayList<>();
                ArrayList<ZonedDateTime> departureTimes = new ArrayList<>();
                ArrayList<ZonedDateTime> arrivalTimes = new ArrayList<>();
                ArrayList<String> timezones = new ArrayList<>();
                ArrayList<String> remarks = new ArrayList<>();
                for (int j = 0; j < trainStations.size(); j++) {
                    JSONObject stationAttributes = (JSONObject) trainStations.get(j);
                    String stationCode = (String) stationAttributes.get("code");
                    servedStationCodes.add(stationCode);
                    String stationStatus = (String) stationAttributes.get("status");
                    stationStatuses.add(stationStatus);
                    String timezone = (String) stationAttributes.get("timezone");
                    timezones.add(timezone);
                    String departureTime = (String) stationAttributes.get("departureActual");
                    if (departureTime == null) {
                        departureTime = (String) stationAttributes.get("departureEstimated");
                    }
                    if (departureTime == null) {
                        departureTime = (String) stationAttributes.get("departureScheduled");
                    }
                    if (departureTime == null) {
                        departureTimes.add(null);
                    } else {
                        departureTimes.add(ZonedDateTime.parse(departureTime));
                    }
                    String arrivalTime = (String) stationAttributes.get("arrivalActual");
                    if (arrivalTime == null) {
                        arrivalTime = (String) stationAttributes.get("arrivalEstimated");
                    }
                    if (arrivalTime == null) {
                        arrivalTime = (String) stationAttributes.get("arrivalScheduled");
                    }
                    if (arrivalTime == null) {
                        arrivalTimes.add(null);
                    } else {
                        arrivalTimes.add(ZonedDateTime.parse(arrivalTime));
                    }
                    JSONObject rawAttributes = (JSONObject) stationAttributes.get("_raw");
                    String remark = (String) rawAttributes.get("postcmnt");
                    if (remark == null) {
                        remark = (String) rawAttributes.get("estarrcmnt");
                    }
                    remarks.add(remark);
                }
                ArrayList<Station> servedStations = new ArrayList<>();
                for (String stationCode : servedStationCodes) {
                    servedStations.add(getStationfromCodeString(stationCode));
                }
                ArrayList<ZonedDateTime> adjustedDepartureTimes = new ArrayList<>();
                ArrayList<ZonedDateTime> adjustedArrivalTimes = new ArrayList<>();
                for (int k = 0; k < departureTimes.size(); k++) {
                    if (departureTimes.get(k) != null) {
                        ZonedDateTime departureTimeAdjusted =
                                departureTimes.get(k).withZoneSameInstant(ZoneId.of(timezones.get(k)));
                        adjustedDepartureTimes.add(departureTimeAdjusted);
                    } else {
                        adjustedDepartureTimes.add(null);
                    }
                    if (arrivalTimes.get(k) != null) {
                        ZonedDateTime arrivalTimeAdjusted =
                                arrivalTimes.get(k).withZoneSameInstant(ZoneId.of(timezones.get(k)));
                        adjustedArrivalTimes.add(arrivalTimeAdjusted);
                    } else {
                        adjustedArrivalTimes.add(null);
                    }
                }
                trainsInDatabase.add(
                        new Train(trainName, Math.toIntExact(trainNumber), servedStations, stationStatuses,
                                adjustedDepartureTimes, adjustedArrivalTimes, remarks));
            }
        }
    }
    public static ArrayList<Route> findRoutesFromStationList(String[] stationStrings) throws IOException,
            ParseException {
        storeTrains();
        ArrayList<Route> routes = new ArrayList<>();
        for (int i = 0; i < stationStrings.length - 1; i++) {
            for (int j = i + 1; j < stationStrings.length; j++) {
                Station originStationSelection = getStationfromString(stationStrings[i]);
                Station destinationStationSelection = getStationfromString(stationStrings[j]);
                for (Train train : trainsInDatabase) {
                    if (train.getStations().contains(originStationSelection) &&
                            train.getStations().contains(destinationStationSelection)) {
                        int originStationIndex = train.getStations().indexOf(originStationSelection);
                        int destinationStationIndex = train.getStations().indexOf(destinationStationSelection);
                        if (originStationIndex < destinationStationIndex) {
                            Route newRoute = null;
                            if (train.getDepartureTimes().get(originStationIndex) != null
                                && train.getArrivalTimes().get(destinationStationIndex) != null) {
                                newRoute = new Route(originStationSelection, destinationStationSelection,
                                        train.getName(),
                                        train.getNumber(), train.getStationStatuses().get(originStationIndex),
                                        train.getStationStatuses().get(destinationStationIndex),
                                        train.getDepartureTimes().get(originStationIndex),
                                        train.getArrivalTimes().get(destinationStationIndex),
                                        (Duration.between(train.getDepartureTimes().get(originStationIndex),
                                                train.getArrivalTimes().get(destinationStationIndex))),
                                        train.getRemarks().get(destinationStationIndex));
                            } else if (train.getDepartureTimes().get(originStationIndex) == null) { //last station
                                newRoute = new Route(originStationSelection, destinationStationSelection,
                                        train.getName(),
                                        train.getNumber(), train.getStationStatuses().get(originStationIndex),
                                        train.getStationStatuses().get(destinationStationIndex),
                                        null,
                                        train.getArrivalTimes().get(destinationStationIndex),
                                        null,
                                        train.getRemarks().get(destinationStationIndex));
                            } else if (train.getArrivalTimes().get(destinationStationIndex) == null) { //first station
                                newRoute = new Route(originStationSelection, destinationStationSelection,
                                        train.getName(),
                                        train.getNumber(), train.getStationStatuses().get(originStationIndex),
                                        train.getStationStatuses().get(destinationStationIndex),
                                        train.getDepartureTimes().get(originStationIndex),
                                        null,
                                        null,
                                        train.getRemarks().get(destinationStationIndex));
                            }
                            if (routes.isEmpty()) {
                                routes.add(newRoute);
                            }
                            boolean repeatRoute = false;
                            for (Route route : routes) {
                                assert newRoute != null;
                                if ((newRoute.getOrigin().equals(route.getOrigin()) &&
                                        newRoute.getDestination().equals(route.getDestination()) &&
                                        newRoute.getDepartureTime().equals(route.getDepartureTime()) &&
                                        newRoute.getArrivalTime().equals(route.getArrivalTime()))) {
                                    repeatRoute = true;
                                    break;
                                }
                            }
                            if (!repeatRoute) {
                                routes.add(newRoute);
                            }
                        }
                    }
                }
            }
        }
        return routes;
    }

    public static void exportNullResults(String[] selectedStationsArray, String searchDateTime)
            throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(resultsOutputFileString);
        PrintWriter pw = new PrintWriter(fos);
        pw.println("Amtrak Travel Planner Search Results " + "[Search Timestamp: " + searchDateTime + "]");
        pw.println("-----");
        for (String stationString : selectedStationsArray) {
            pw.println(stationString);
        }
        pw.println("-----");
        pw.println("No Results Found");
        pw.close();
        Path filePath = Paths.get(resultsOutputFileString);
        JOptionPane.showMessageDialog(null, "Save successful! File saved to " + filePath + "."
                , "Results Output Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void exportResults(String[] selectedStationsArray, String searchDateTime, Trip trip)
            throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(resultsOutputFileString);
        PrintWriter pw = new PrintWriter(fos);
        pw.println("Amtrak Travel Planner Search Results " + "[Search Timestamp: " + searchDateTime + "]");
        pw.println("-----");
        for (String stationString : selectedStationsArray) {
            pw.println(stationString);
        }
        pw.println("-----");
        pw.println(trip);
        pw.close();
        Path filePath = Paths.get(resultsOutputFileString);
        JOptionPane.showMessageDialog(null, "Save successful! File saved to " + filePath + "."
                , "Results Output Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void exportSearchPreset(String[] selectedStationsArray, String searchDateTime)
            throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(searchOutputFileString);
        PrintWriter pw = new PrintWriter(fos);
        pw.println("Amtrak Travel Planner Search Preset " + "[Search Timestamp: " + searchDateTime + "]");
        pw.println("-----");
        for (String stationString : selectedStationsArray) {
            pw.println(stationString);
        }
        pw.close();
        Path filePath = Paths.get(searchOutputFileString);
        JOptionPane.showMessageDialog(null, "Save successful! File saved to " + filePath + "."
                , "Search Preset File Output Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String[] loadSearch(File searchFile) throws Exception {
        ArrayList<String> searchedStations = new ArrayList<>();
        FileReader fr = new FileReader(searchFile);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        if (!(line.contains("Amtrak Travel Planner Search "))) {
            throw new Exception();
        }
        line = bfr.readLine();
        if (!(line.equals("-----"))) {
            throw new Exception();
        }
        line = bfr.readLine();
        while (line != null) {
            if (stationsArrayList.contains(getStationfromString(line))) {
                searchedStations.add(line);
                line = bfr.readLine();
            } else {
                break;
                //will pick up station strings while omitting results, should the user load a results file
            }
        }
        String[] searchedStationsArray = new String[searchedStations.size()];
        for (int i = 0; i < searchedStations.size(); i++) {
            searchedStationsArray[i] = searchedStations.get(i);
        }
        return searchedStationsArray;
    }
    public static void createTripForGUIDisplay(String[] stations) throws IOException, ParseException {
        Trip userTrip = new Trip(PlannerDatabase.findRoutesFromStationList(stations));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        new TripViewer(userTrip, stations, formatter.format(currentDateTime));
    }

    //for testing
    public static void main(String[] args) {
        try {
            Station[] stations = getTrainStations();
            for (Station station : stations) {
                System.out.println(station);
            }
            storeTrains();
            for (Train train : trainsInDatabase) {
                System.out.println(train);
            }
            Trip testTrip = new Trip(findRoutesFromStationList(new String[]{"ORC--Oregon City, OR", "PDX--Portland, OR",
                    "REN--Rensselaer, IN", "YNY--Yonkers, NY", "RSP--Rouses Point, NY"}));
            System.out.println(testTrip);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}