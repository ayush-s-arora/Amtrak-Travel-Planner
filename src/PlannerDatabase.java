import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PlannerDatabase {
    private static URL stationsURL;
    private static URL trainsURL;
    private static Station[] stations;
    private static String[] stationStrings;
    private static Map<String, ArrayList<String>> multiStationTrains = new HashMap<>();


    static {
        try {
            stationsURL = new URL("https://mgwalker.github.io/amtrak-api/stations.json");
            trainsURL = new URL("https://mgwalker.github.io/amtrak-api/routes.json");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    public PlannerDatabase() throws MalformedURLException {
    }

    public static Station[] getTrainStations() throws IOException, ParseException {
        ArrayList<Station> stationsArrayList = new ArrayList<>();
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

    public static Station getStationfromString(String stationString) {
        for (Station station : stations) {
            if (station.toString().equals(stationString)) {
                return station;
            }
        }
        return null; //should never happen
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
            JSONArray lines = (JSONArray) parse.parse(inline);
            for (int i = 0; i < lines.size(); i++) {
                JSONObject line = (JSONObject) lines.get(i);
                String lineName = (String) line.get("route"); //line names (e.g., CalZephyr)
                JSONArray lineTrainsArray = (JSONArray) line.get("trains"); //multiple trains run one line
                for (int j = 0; j < lineTrainsArray.size(); j++) {
                    System.out.println("***" + lineTrainsArray.size());
                    JSONObject lineTrainObject = (JSONObject) lineTrainsArray.get(j);
                    Long trainNumber = (Long) lineTrainObject.get("number");
                    System.out.println(lineName + " " + trainNumber);
//                    Long lineTrainNumber = (Long) lineTrainObject.get("number");
//                    System.out.println(lineTrainNumber);
                }
//                System.out.println(lineTrainsArray.getFirst());

//                System.out.println(trainName);
//                System.out.println(trainObject);
//                System.out.println(stationsArray);
//                for (int j = 0; j < stationsArray.size() - 1; j++) {
//                    JSONObject stationObject = (JSONObject) stationsArray.get(j);
//                    String stationCode = (String) stationObject.get("code");
//                    multiStationTrains.put(trainName, new ArrayList<>());
//                    multiStationTrains.get(trainName).add(stationCode);
//                }
//                JSONObject stationObject = (JSONObject) stationsArray.get(stationsArray.size() - 1);
//                String stationCode = (String) stationObject.get("code");
//                String remark = (String) stationObject.get("_raw.postcmnt");
//                multiStationTrains.put(trainName, new ArrayList<String>());
//                multiStationTrains.get(trainName).add(stationCode);
//                multiStationTrains.get(trainName).add(remark); //add the remark for timeliness at the END of list
//                System.out.println(multiStationTrains);
            }
        }
    }
    public static Route findRoutesFromStationList(String[] stationStrings) {
        return null;
    }

    public static Trip[] generateTripsFromRoutes(Route[] userRoutes) {
        return null;
    }

    //for testing
    public static void main(String[] args) {
        try {
            Station[] stations = getTrainStations();
            for (Station station : stations) {
                System.out.println(station);
            }
            storeTrains();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
