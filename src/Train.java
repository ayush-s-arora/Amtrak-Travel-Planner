import java.util.ArrayList;

public class Train {
    private String name;
    private int number;
    private ArrayList<Station> stations;
    private ArrayList<String> stationStatuses;
    private ArrayList<String> remarks;
    private String status;

    public Train(String route, int number, ArrayList<Station> stations, ArrayList<String> stationStatuses,
                 ArrayList<String> remarks) {
        this.name = route;
        this.number = number;
        this.stations = stations;
        this.stationStatuses = stationStatuses;
        this.remarks = remarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public ArrayList<String> getStationStatuses() {
        return stationStatuses;
    }

    public void setStationStatuses(ArrayList<String> stationStatuses) {
        this.stationStatuses = stationStatuses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getRemarks() {
        return remarks;
    }

    public void setRemarks(ArrayList<String> remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Train{" +
                "route='" + name + '\'' +
                ", number=" + number +
                ", stations=" + stations +
                ", stationStatuses=" + stationStatuses +
                ", remarks=" + remarks;
    }
}