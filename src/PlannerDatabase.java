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

    public static String[] getTrainStations() throws IOException, ParseException {
        ArrayList<String> stationsArrayList = new ArrayList<>();
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
                stationsArrayList.add(stationCode + " -- " + stationName);
            }
        }
        String[] stations = new String[stationsArrayList.size()];
        for (int i = 0; i < stations.length; i++) {
            stations[i] = stationsArrayList.get(i);
        }
        return stations;
    }

    public static void main(String[] args) {
        try {
            String[] stations = getTrainStations();
            for (String station : stations) {
                System.out.println(station);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
