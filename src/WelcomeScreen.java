import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WelcomeScreen extends PlannerDriver implements ActionListener {
    private JButton createANewAmtrakButton;
    private JButton loadFromASearchButton;
    private JPanel welcomePanel;
    private JLabel titleLabel;
    private static JFrame jf;
    public WelcomeScreen() {
        super("Amtrak Travel Planner");
        jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setContentPane(welcomePanel);
        jf.setSize(640, 480);
        jf.setVisible(true);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 25));

        createANewAmtrakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new TripPlanner();
            }
        });
        loadFromASearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser searchFileChooser = new JFileChooser();
                searchFileChooser.setCurrentDirectory(new File("."));
                int searchFileResponse = searchFileChooser.showOpenDialog(null);
                if (searchFileResponse == JFileChooser.APPROVE_OPTION) {
                    File searchFile = new File(searchFileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        try {
                            PlannerDatabase.getTrainStations();
                            PlannerDatabase.storeTrains();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Error! Unable to connect to database."
                                            + " Please check your internet connection and then try submitting again."
                                    , "Database Connection Failure", JOptionPane.ERROR_MESSAGE);
                        }
                        TripPlanner.tripGUISearch(PlannerDatabase.loadSearch(searchFile));
                        jf.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error! Unable to read the search" +
                                        " file."
                                        + " Please ensure the file is formatted correctly and then try again." +
                                        " Make sure to use a Search Preset file and not a Search Results file!"
                                , "Search File Load Failure", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
