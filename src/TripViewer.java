import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.time.Year;

public class TripViewer extends JFrame implements ActionListener {
    private static JFrame jf;
    private JPanel tripViewingPanel;
    private JLabel noTripsFound;
    private JButton saveThisSearchButton;
    private JButton saveSearchResultsButton;
    private JButton startANewSearchButton;
    private JSplitPane saveSplitPane;
    private JButton exitButton;
    private JTextArea resultsTextArea;
    private JScrollPane resultsScrollPane;
    private JButton viewSelectedStationsButton;
    private JSplitPane bottomSplitPane;
    private JButton mainMenuButton;
    private JSplitPane operationsSplitPane;
    private JLabel copyright;

    public TripViewer(Trip trip, String[] selectedStationsArray, String searchDateTime) {
        jf = new JFrame("Amtrak Travel Planner: Explore Your Trip Options!");
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setContentPane(tripViewingPanel);
        jf.setSize(640, 480);
        jf.setVisible(true);
        bottomSplitPane.setDividerLocation(0.5);
        operationsSplitPane.setDividerLocation(0.5);
        saveSplitPane.setDividerLocation(0.5);
        copyright.setText("© " + Year.now().getValue() + " Ayush Shukla Arora. All Rights Reserved. ");
        copyright.setFont(new Font(null, Font.PLAIN, 8));
        if (trip.getTripRoutes().isEmpty()) {
            noTripsFound.setVisible(true);
            noTripsFound.setPreferredSize(new Dimension(300, 300));
            noTripsFound.setHorizontalAlignment(SwingConstants.CENTER);
            resultsTextArea.setVisible(false);
            resultsScrollPane.setVisible(false);
        } else {
            noTripsFound.setVisible(false);
            resultsTextArea.setVisible(true);
            resultsTextArea.setText("Search Timestamp: " + searchDateTime + "\n\n" + trip);
            resultsTextArea.setCaretPosition(0);
            String noRouteStations = "Unable to find routes for: \n";
            boolean noRouteStationsExist = false;
            for (String station : selectedStationsArray) {
                if (!trip.toString().contains(station.substring(0, 3))) {
                    noRouteStations += station + "\n";
                    noRouteStationsExist = true;
                }
            }
            noRouteStations = noRouteStations.substring(0, noRouteStations.length());
            if (noRouteStationsExist) {
                resultsTextArea.setText("Search Timestamp: " + searchDateTime + "\n\n" + trip + "\n\n\n" +
                        noRouteStations);
            }
            resultsScrollPane.setViewportView(resultsTextArea);
            resultsScrollPane.setPreferredSize(new Dimension(300, 300));
            resultsScrollPane.setVisible(true);
        }
        saveSearchResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (trip.getTripRoutes().isEmpty()) {
                    try {
                        PlannerDatabase.exportNullResults(selectedStationsArray, searchDateTime);
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error! Unable to write to the " +
                                        "save file. Please ensure the Amtrak Travel Planner has write privileges and " +
                                        "then try again."
                                , "Results Output Failure", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    try {
                        PlannerDatabase.exportResults(selectedStationsArray, searchDateTime, trip);
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error! Unable to write to the " +
                                        "save file. Please ensure the Amtrak Travel Planner has write privileges and " +
                                        "then try again."
                                , "Results Output Failure", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        saveThisSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PlannerDatabase.exportSearchPreset(selectedStationsArray, searchDateTime);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Error! Unable to write to the " +
                                    "save file. Please ensure the Amtrak Travel Planner has write privileges and " +
                                    "then try again."
                            , "Results Output Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        startANewSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new TripPlanner();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            final Object[] options = {"You're welcome"};
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showOptionDialog(null, "Thank you for using the Amtrak Travel" +
                                " Planner!"
                        , "Exit", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                        options, options[0]);
                jf.dispose();
            }
        });
        viewSelectedStationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jfSelectedStations = new JFrame();
                jfSelectedStations.setSize(200, 400);
                JScrollPane selectedStationsPanel = new JScrollPane();
                JList<String> selectedStations = new JList<>(selectedStationsArray);
                selectedStationsPanel.setViewportView(selectedStations);
                jfSelectedStations.add(selectedStationsPanel);
                jfSelectedStations.setVisible(true);
            }
        });
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new WelcomeScreen();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}