public class Route {
    private Station origin;
    private Station destination;
    private String trainName;
    public Route(Station origin, Station destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public String toString() {
        return trainName + ": " + origin.getCode() + " --> " +  destination.getCode();
    }
}
