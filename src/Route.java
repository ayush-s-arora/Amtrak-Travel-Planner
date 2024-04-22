import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * Amtrak Travel Planner - Duration
 *
 * A Route class for the Amtrak Travel
 * Planner, which creates a Route
 * object from route data parsed in
 * PlannerDatabase. A Trip consists
 * of as many Routes as are possible
 * based on the user's desired Stations
 * and the current data.
 *
 * @author Ayush Shukla Arora, L19
 *
 * @version April 22, 2024
 */

public class Route {
    private Station origin;
    private Station destination;
    private String trainName;
    private int trainNumber;
    private String originStationStatus;
    private String destinationStationStatus;
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;
    private Duration duration;
    private String remark;

    public Route(Station origin, Station destination, String trainName,
                 int trainNumber, String originStationStatus,
                 String destinationStationStatus, ZonedDateTime departureTime, ZonedDateTime arrivalTime,
                 Duration duration, String remark) {
        this.origin = origin;
        this.destination = destination;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.originStationStatus = originStationStatus;
        this.destinationStationStatus = destinationStationStatus;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
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

    public String getOriginStationStatus() {
        return originStationStatus;
    }

    public void setOriginStationStatus(String originStationStatus) {
        this.originStationStatus = originStationStatus;
    }

    public String getDestinationStationStatus() {
        return destinationStationStatus;
    }

    public void setDestinationStationStatus(String destinationStationStatus) {
        this.destinationStationStatus = destinationStationStatus;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ZonedDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public static String formatTime(ZonedDateTime time) {
        String formattedTime;
        int hour = time.getHour();
        String minute = String.valueOf(time.getMinute());
        if (Integer.parseInt(minute) < 10) {
            minute = "0" + minute;
        }
        formattedTime = hour + ":" + minute;
        return formattedTime;
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

    public static String formatDate(ZonedDateTime dateAndTime) {
        String formattedDate = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        formattedDate = formatter.format(dateAndTime);
        return formattedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        if (arrivalTime != null && departureTime != null) {
            ZonedDateTime localTime = ZonedDateTime.now(ZoneId.systemDefault());
            String startingStringComponent = trainName + " #" + trainNumber + ": " + origin.getCode() + " ("
                    + formatTime(departureTime) + ") --> "
                    + destination.getCode() + " (" + formatTime(arrivalTime) + ")\n";
            String endingStringComponent = " -- " + remark + "\nTrip Duration: " + formatDuration(duration);
            String output = "";
            if (Duration.between(arrivalTime, localTime).toHours() > 24 ||
                    Duration.between(departureTime, localTime).toHours() > 24) {
                output += startingStringComponent + "\n" + formatDate(departureTime) + "–" + formatDate(arrivalTime);
            }
            if (originStationStatus.equalsIgnoreCase("scheduled")) {
                output += startingStringComponent +
                        originStationStatus.toUpperCase() + " to Depart in " +
                        formatDuration(Duration.between(localTime, departureTime)) +
                        "\n" + destinationStationStatus.toUpperCase() + " to Arrive in " +
                        formatDuration(Duration.between(localTime, arrivalTime)) +
                        endingStringComponent;
            } else if (destinationStationStatus.equalsIgnoreCase("arrived")) {
                output += startingStringComponent +
                        destinationStationStatus.toUpperCase() + " " +
                        formatDuration(Duration.between(arrivalTime, localTime)) + " ago" +
                        endingStringComponent;
            } else if (destinationStationStatus.equalsIgnoreCase("enroute")) {
                output += startingStringComponent +
                        originStationStatus.toUpperCase() + " " +
                        formatDuration(Duration.between(departureTime, localTime)) + " ago " +
                        "\n" + destinationStationStatus.toUpperCase() + " and Expected to Arrive in " +
                        formatDuration(Duration.between(localTime, arrivalTime)) +
                        endingStringComponent;
            }
                return output;
            } else {
            return trainName + " #" + trainNumber + ": " + origin.getCode() + " --> " + destination.getCode() + ", " +
                    destinationStationStatus.toUpperCase() + " -- " + remark;
        }
    }
}