import java.time.Duration;
import java.util.ArrayList;

/**
 * Amtrak Travel Planner - Trip
 *
 * A Trip class for the Amtrak Travel
 * Planner, which creates a Trip object
 * consisting of all the user's possible
 * routes and their attributes (duration etc.,),
 * and any stations not accounted for. Outputted
 * to TripViewer.
 *
 * @author Ayush Shukla Arora, L19
 *
 * @version April 22, 2024
 */

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
            if (route.getDuration() == null) {
                break;
            } else {
                totalDuration = totalDuration.plus(route.getDuration());
            }
        }
        this.totalDuration = totalDuration;
    }

    public String toString() {
        calculateTotalDuration();
        String output = "";
        for (Route route : tripRoutes) {
            output += route.toString();
            output += "\n\n";
        }
        output += "\nTotal Duration: " + Route.formatDuration(this.totalDuration);
        return output;
    }
}