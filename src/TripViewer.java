import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TripViewer extends PlannerDriver implements ActionListener {
    private static JFrame jf;
    private JPanel tripViewingPanel;
    private JLabel noTripsFound;
    private JButton saveThisSearchButton;
    private JButton saveSearchResultsButton;
    private JButton startANewSearchButton;
    private JSplitPane saveSplitPane;
    private JButton exitButton;
    private String[] results;

    public TripViewer(Trip trip, String[] selectedStationsArray, String searchDateTime) {
        super("Amtrak Travel Planner: Explore Your Trip Options!");
        jf = new JFrame();
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setContentPane(tripViewingPanel);
        jf.setSize(640, 480);
        jf.setVisible(true);
        ArrayList<Route> resultsArrayList = trip.getTripRoutes();
        results = new String[resultsArrayList.size()];
        for (int i = 0; i < results.length; i++) {
            results[i] = resultsArrayList.get(i).toString();
        }
        if (results.length == 0) {
            noTripsFound.setVisible(true);
        } else {
            noTripsFound.setVisible(false);
        }
        saveSplitPane.setDividerLocation(0.5);
        saveThisSearchButton.setMinimumSize(new Dimension(30, 15));
        saveThisSearchButton.setMaximumSize(new Dimension(30, 15));
        saveThisSearchButton.setPreferredSize(new Dimension(30, 1500));
        saveSearchResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (results.length == 0) {
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
                        PlannerDatabase.exportResults(selectedStationsArray, searchDateTime, results);
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
                    PlannerDatabase.exportSearch(selectedStationsArray, searchDateTime);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}