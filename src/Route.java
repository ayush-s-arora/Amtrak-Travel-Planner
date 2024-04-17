public class Route {
    private Station origin;
    private Station destination;
    private String trainName;
    private int trainNumber;
    private String stationStatus;
    private String remark;

    public Route(Station origin, Station destination, String trainName,
                 int trainNumber, String stationStatus, String remark) {
        this.origin = origin;
        this.destination = destination;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.stationStatus = stationStatus;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        return trainName + " #" + trainNumber + ": " + origin.getCode() + " --> " +  destination.getCode() + ", " +
                stationStatus.toUpperCase() + " " + remark;
    }
}
