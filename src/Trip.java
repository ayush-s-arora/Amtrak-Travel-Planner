import java.time.Duration;
import java.util.ArrayList;

public class Trip {
    private ArrayList<Route> tripRoutes;

    private Duration totalDuration;

    public Trip(ArrayList<Route> tripRoutes) {
        this.tripRoutes = tripRoutes;
    }

    public ArrayList<Route> getTripRoutes() {
        return tripRoutes;
    }

    public void setTripRoutes(ArrayList<Route> tripRoutes) {
        this.tripRoutes = tripRoutes;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public void calculateTotalDuration() {
        Duration totalDuration = Duration.ZERO;
        for (Route route : tripRoutes) {
            totalDuration = totalDuration.plus(route.getDuration());
        }
        this.totalDuration = totalDuration;
    }

    public String toString() {
        calculateTotalDuration();
        String output = "";
        for (Route route : tripRoutes) {
            output += route.toString();
            output += "\n";
        }
        output += "Total Duration: " + Route.formatDuration(this.totalDuration);
        return output;
    }
}