import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;

public class PlannerDriver extends JFrame {

    public PlannerDriver(String frameName) {
        super(frameName);
    }

    public static void main(String[] args) {
        new WelcomeScreen();
    }
}