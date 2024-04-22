import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.Year;

/**
 * Amtrak Travel Planner - WelcomeScreen
 *
 * GUI class for the Amtrak Travel Planner's
 * Welcome Screen.
 *
 * @author Ayush Shukla Arora, L19
 *
 * @version April 22, 2024
 */

public class WelcomeScreen extends PlannerDriver implements ActionListener {
    private JButton createANewAmtrakButton;
    private JButton loadFromASearchButton;
    private JPanel welcomePanel;
    private JLabel titleLabel;
    private JLabel creditsLabel;
    private JLabel internetReqd;
    private JLabel copyright;
    private static JFrame jf;
    public WelcomeScreen() {
        jf = new JFrame("Amtrak Travel Planner");
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setContentPane(welcomePanel);
        jf.setSize(640, 480);
        jf.setVisible(true);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 25));
        internetReqd.setFont(new Font("Sans Serif", Font.ITALIC, 10));
        copyright.setText("© " + Year.now().getValue() + " Ayush Shukla Arora. All Rights Reserved. ");
        //intentional following space for padding
        copyright.setFont(new Font(null, Font.PLAIN, 8));

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
                        PlannerDatabase.createTripForGUIDisplay(PlannerDatabase.loadSearch(searchFile));
                        jf.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error! Unable to read the search" +
                                        " file."
                                        + " Please ensure the file is formatted correctly and then try again."
                                , "Search File Load Failure", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        creditsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                creditsLabel.setFont(new Font(null, Font.BOLD, 20));
                creditsLabel.setForeground(Color.BLUE);
            }
        });
        creditsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                creditsLabel.setFont(new Font(null, Font.BOLD, 13));
                creditsLabel.setForeground(Color.BLACK);
            }
        });
        creditsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                jf.dispose();
                new CreditsScreen();
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