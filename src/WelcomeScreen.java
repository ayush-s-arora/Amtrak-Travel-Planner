import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen extends PlannerDriver implements ActionListener {
    private JButton createANewAmtrakButton;
    private JButton loadFromASearchButton;
    private JPanel welcomePanel;
    private JPanel tripPlannerPanel;
    private static JFrame jf;
    public WelcomeScreen() {
        super("Amtrak Travel Planner");
        jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setContentPane(welcomePanel);
        jf.setSize(640, 480);
        jf.setVisible(true);
        createANewAmtrakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new TripPlanner();
            }
        });
    }
    public static void main(String[] args) {
        jf = new WelcomeScreen();
        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
