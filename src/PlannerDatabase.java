import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PlannerDatabase {
    private static URL stationsURL;
    private static URL trainStatusesURL;
    private static Station[] stations;
    private static String[] stationStrings;

    static {
        try {
            stationsURL = new URL("https://mgwalker.github.io/amtrak-api/stations.json");
            trainStatusesURL = new URL("https://mgwalker.github.io/amtrak-api/routes.json");
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

    //for testing
    public static void main(String[] args) {
        try {
            Station[] stations = getTrainStations();
            for (Station station : stations) {
                System.out.println(station);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
