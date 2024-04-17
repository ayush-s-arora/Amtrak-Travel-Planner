import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;

public class TripPlanner extends PlannerDriver implements ActionListener {

    private JLabel instructionOne;
    private JLabel instructionTwo;
    private JPanel tripPlanningPanel;
    private JScrollPane stationSelector;
    private JSplitPane jSplitPane;
    private JScrollPane selectedStationsPane;
    private static JFrame jf;

    public TripPlanner() {
        super("Amtrak Travel Planner: Plan your Trip!");
        jf = new JFrame();
        jf.setContentPane(tripPlanningPanel);
        jf.setSize(640, 480);
        jf.setVisible(true);
        JList<String> trainStations = null;
        try {
            trainStations = new JList<>(PlannerDatabase.getTrainStationStrings());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error! Unable to connect to database." +
                    " Returning to welcome screen...", "Database Connection Failure", JOptionPane.ERROR_MESSAGE);
            jf.dispose();
            new WelcomeScreen();
        }
        jSplitPane.setDividerLocation(0.5);
        stationSelector.setViewportView(trainStations);
        JList<String> selectedStations = null;
        ArrayList<String> selectedStationsArrayList = new ArrayList();
        assert trainStations != null;
        JList<String> finalTrainStations = trainStations;
        final String[] selectedStation = {null};
        trainStations.addListSelectionListener(new ListSelectionListener() {
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
                        String[] selectedStationsArray = new String[selectedStationsArrayList.size()];
                        for (int i = 0; i < selectedStationsArray.length; i++) {
                            selectedStationsArray[i] = selectedStationsArrayList.get(i);
                        }
                        JList<String> selectedStations = new JList<>(selectedStationsArray);
                        selectedStationsPane.setViewportView(selectedStations);
                    }
                }
            }
        });
        trainStations.addMouseListener(new MouseAdapter() {
            int lastSelectionIndex;
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = finalTrainStations.locationToIndex(e.getPoint());
                if (index != -1 && index == lastSelectionIndex) {
                    finalTrainStations.clearSelection();
                    selectedStationsArrayList.remove(finalTrainStations.getModel().getElementAt(index));
                    String[] selectedStationsArray = new String[selectedStationsArrayList.size()];
                    for (int i = 0; i < selectedStationsArray.length; i++) {
                        selectedStationsArray[i] = selectedStationsArrayList.get(i);
                    }
                    JList<String> selectedStations = new JList<>(selectedStationsArray);
                    selectedStationsPane.setViewportView(selectedStations);
                }
                lastSelectionIndex = finalTrainStations.getSelectedIndex();
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}