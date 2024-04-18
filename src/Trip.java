import java.time.Instant;

public class Trip {
    private Route[] tripRoutes;
    private Instant duration;

    public Trip(Route[] tripRoutes) {
        this.tripRoutes = tripRoutes;
    }

    public Route[] getTripRoutes() {
        return tripRoutes;
    }

    public void setTripRoutes(Route[] tripRoutes) {
        this.tripRoutes = tripRoutes;
    }

    public void calculateTotalDuration() {
        for (Route route : tripRoutes) {

        }
    }
}
