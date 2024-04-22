import javax.swing.*;

/**
 * Amtrak Travel Planner - PlannerDriver
 *
 * A PlannerDriver class for the Amtrak Travel
 * Planner, which starts the program on the
 * Welcome Screen.
 *
 * @author Ayush Shukla Arora, L19
 *
 * @version April 22, 2024
 */

public class PlannerDriver extends JFrame {

    public PlannerDriver() {
        super();
    }

    public static void main(String[] args) {
        new WelcomeScreen();
    }
}