import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TripViewer extends PlannerDriver implements ActionListener {
    private static JFrame jf;
    private JPanel tripViewingPanel;

    public TripViewer() {
        super("Amtrak Travel Planner: Explore Your Trip Options!");
        jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setContentPane(tripViewingPanel);
        jf.setSize(640, 480);
        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}