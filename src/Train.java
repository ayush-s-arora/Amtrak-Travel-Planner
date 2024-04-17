import java.util.ArrayList;

public class Train {
    private String route;
    private int number;
    private ArrayList<String> stations;
    private ArrayList<String> stationStatuses;
    private ArrayList<String> remarks;
    private String status;

    public Train(String route, int number, ArrayList<String> stations, ArrayList<String> stationStatuses,
                 ArrayList<String> remarks) {
        this.route = route;
        this.number = number;
        this.stations = stations;
        this.stationStatuses = stationStatuses;
        this.remarks = remarks;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<String> getStations() {
        return stations;
    }

    public void setStations(ArrayList<String> stations) {
        this.stations = stations;
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
                "route='" + route + '\'' +
                ", number=" + number +
                ", stations=" + stations +
                ", stationStatuses=" + stationStatuses +
                ", remarks=" + remarks;
    }
}