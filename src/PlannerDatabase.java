import java.io.IOException;
import java.lang.reflect.Array;
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
            JSONArray trains = (JSONArray) parse.parse(inline);
            for (int i = 0; i < trains.size(); i++) {
                JSONObject train = (JSONObject) trains.get(i);
                String trainName = (String) train.get("route"); //line names (e.g., CalZephyr)
                Long trainNumber = (Long) train.get("number");
                JSONArray trainStations = (JSONArray) train.get("stations");
                ArrayList<String> servedStations = new ArrayList<>();
                ArrayList<String> stationStatuses = new ArrayList<>();
                ArrayList<String> remarks = new ArrayList<>();
                for (int j = 0; j < trainStations.size(); j++) {
                    JSONObject stationAttributes = (JSONObject) trainStations.get(j);
                    String stationCode = (String) stationAttributes.get("code");
                    servedStations.add(stationCode);
                    String stationStatus = (String) stationAttributes.get("status");
                    stationStatuses.add(stationStatus);
                    JSONObject rawAttributes = (JSONObject) stationAttributes.get("_raw");
                    String remark = (String) rawAttributes.get("postcmnt");
                    if (remark == null) {
                        remark = (String) rawAttributes.get("estarrcmnt");
                    }
                    remarks.add(remark);
                }
                Train trane = new Train(trainName, Math.toIntExact(trainNumber), servedStations, stationStatuses, remarks);
                System.out.println(trane);
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
