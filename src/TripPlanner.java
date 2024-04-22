import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.time.Year;
import java.util.ArrayList;

/**
 * Amtrak Travel Planner - TripPlanner
 *
 * GUI class for the Amtrak Travel Planner's
 * Trip Planner Screen, where the user selects
 * stations.
 *
 * @author Ayush Shukla Arora, L19
 *
 * @version April 22, 2024
 */

public class TripPlanner extends JFrame implements ActionListener {

    private JLabel instructionOne;
    private JLabel instructionTwo;
    private JPanel tripPlanningPanel;
    private JScrollPane stationSelector;
    private JSplitPane jSplitPane;
    private JScrollPane selectedStationsPane;
    private JButton exploreTravelOptionsButton;
    private JButton returnToMainMenuButton;
    private JLabel copyright;
    private String[] selectedStationsArray;
    private static JFrame jf;

    public TripPlanner() {
        jf = new JFrame("Amtrak Travel Planner: Plan your Trip!");
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setContentPane(tripPlanningPanel);
        jf.setSize(640, 480);
        jf.setVisible(true);
        copyright.setText("© " + Year.now().getValue() + " Ayush Shukla Arora. All Rights Reserved. ");
        copyright.setFont(new Font(null, Font.PLAIN, 8));
        JList<String> trainStations = null;
        try {
            trainStations = new JList<>(PlannerDatabase.getTrainStationStrings());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error! Unable to connect to database." +
                    " Returning to welcome screen...", "Database Connection Failure", JOptionPane.ERROR_MESSAGE);
            jf.dispose();
            new WelcomeScreen();
        }
        final JList<String> finalTrainStations = trainStations;
        exploreTravelOptionsButton.setVisible(false);
        jSplitPane.setDividerLocation(0.5);
        stationSelector.setViewportView(trainStations);
        ArrayList<String> selectedStationsArrayList = new ArrayList<>();
        assert finalTrainStations != null;
        final String[] selectedStation = {null};
        finalTrainStations.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (finalTrainStations.getSelectedIndex() == -1) {
                        return;
                    } else {
                        selectedStation[0] = finalTrainStations.getModel().
                                getElementAt(finalTrainStations.getSelectedIndex());
                        if (selectedStationsArrayList.contains(selectedStation[0])) {
                            selectedStationsArrayList.remove(selectedStation[0]);
                        } else {
                            selectedStationsArrayList.add(selectedStation[0]);
                        }
                        selectedStationsArray = new String[selectedStationsArrayList.size()];
                        for (int i = 0; i < selectedStationsArray.length; i++) {
                            selectedStationsArray[i] = selectedStationsArrayList.get(i);
                        }
                        JList<String> selectedStations = new JList<>(selectedStationsArray);
                        selectedStationsPane.setViewportView(selectedStations);
                        if (selectedStationsArray.length >= 2) {
                            exploreTravelOptionsButton.setVisible(true);
                        } else {
                            exploreTravelOptionsButton.setVisible(false);
                        }
                    }
                }
            }
        });
        finalTrainStations.addMouseListener(new MouseAdapter() {
            int lastSelectionIndex;

            @Override
            public void mouseClicked(MouseEvent e) {
                int index = finalTrainStations.locationToIndex(e.getPoint());
                if (index != -1 && index == lastSelectionIndex) {
                    finalTrainStations.clearSelection();
                    selectedStationsArrayList.remove(finalTrainStations.getModel().getElementAt(index));
                    selectedStationsArray = new String[selectedStationsArrayList.size()];
                    for (int i = 0; i < selectedStationsArray.length; i++) {
                        selectedStationsArray[i] = selectedStationsArrayList.get(i);
                    }
                    JList<String> selectedStations = new JList<>(selectedStationsArray);
                    selectedStationsPane.setViewportView(selectedStations);
                    if (selectedStationsArray.length >= 2) {
                        exploreTravelOptionsButton.setVisible(true);
                    } else {
                        exploreTravelOptionsButton.setVisible(false);
                    }
                }
                lastSelectionIndex = finalTrainStations.getSelectedIndex();
            }
        });
        exploreTravelOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PlannerDatabase.createTripForGUIDisplay(selectedStationsArray);
                    jf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error! Unable to connect to database."
                                    + " Please check your internet connection and try submitting again."
                            , "Database Connection Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        returnToMainMenuButton.addActionListener(new ActionListener() {
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