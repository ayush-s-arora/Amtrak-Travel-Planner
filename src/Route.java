import java.time.Duration;
import java.time.Instant;

public class Route {
    private Station origin;
    private Station destination;
    private String trainName;
    private int trainNumber;
    private String stationStatus;
    private Duration duration;
    private String remark;

    public Route(Station origin, Station destination, String trainName,
                 int trainNumber, String stationStatus, Duration duration, String remark) {
        this.origin = origin;
        this.destination = destination;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.stationStatus = stationStatus;
        this.duration = duration;
        this.remark = remark;
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

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(String stationStatus) {
        this.stationStatus = stationStatus;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public static String formatDuration(Duration duration) {
        String formattedDuration = "";
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        if (hours == 0) {
            formattedDuration += minutes + " Minutes";
        } else {
            formattedDuration += hours + " Hours " + minutes + " Minutes";
        }
        return formattedDuration;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        if (stationStatus.equalsIgnoreCase("Scheduled") ||
                stationStatus.equalsIgnoreCase("EnRoute")) {
            return trainName + " #" + trainNumber + ": " + origin.getCode() + " --> " +  destination.getCode() + ", " +
                    stationStatus.toUpperCase() + " -- " + remark + "\nTrip Duration: " + formatDuration(duration);
        } else {
            return trainName + " #" + trainNumber + ": " + origin.getCode() + " --> " +  destination.getCode() + ", " +
                    stationStatus.toUpperCase() + " " + remark + "\nTrip Duration: " + formatDuration(duration);
        }
    }
}
