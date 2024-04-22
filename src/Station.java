/**
 * Amtrak Travel Planner - Station
 *
 * A Station class for the Amtrak Travel
 * Planner, which creates an Amtrak
 * Station object from station data parsed
 * in PlannerDatabase.
 *
 * @author Ayush Shukla Arora, L19
 *
 * @version April 22, 2024
 */

public class Station {
   private String code;
   private String name;

    public Station(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return code + "--" + name;
    }
}
