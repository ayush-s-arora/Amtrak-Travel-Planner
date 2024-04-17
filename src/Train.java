import java.util.Arrays;

public class Train {
    private String route;
    private int number;
    private String[] stations;
    private String remark;

    public Train(String route, int number, String[] stations, String remark) {
        this.route = route;
        this.number = number;
        this.stations = stations;
        this.remark = remark;
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

    public String[] getStations() {
        return stations;
    }

    public void setStations(String[] stations) {
        this.stations = stations;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Train{" +
                "route='" + route + '\'' +
                ", number=" + number +
                ", stations=" + Arrays.toString(stations) +
                ", remark='" + remark + '\'' +
                '}';
    }
}