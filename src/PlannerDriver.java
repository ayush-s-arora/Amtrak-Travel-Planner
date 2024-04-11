import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class PlannerDriver extends JComponent {
    private final static String APPLICATION_TITLE = "Amtrak Travel Planner";
    public static void main(String[] args) {
        JFrame jf = new JFrame(APPLICATION_TITLE);
        jf.setSize(640, 480);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel welcomePanel = new JPanel(new GridLayout(6, 1));
        JTextArea welcome = new JTextArea("Welcome to the " + APPLICATION_TITLE + "!");
        JTextArea functionSelection = new JTextArea("Please select a function from the options below.");
        JButton createNewTrip = new JButton("Create a New Amtrak Trip");
        JButton searchLoader = new JButton("Load a Search File");
        JTextArea credits = new JTextArea("Credits");
        welcomePanel.add(welcome);
        welcomePanel.add(functionSelection);
        welcomePanel.add(createNewTrip);
        welcomePanel.add(searchLoader);
        welcomePanel.add(credits);
        jf.add(welcomePanel);
        jf.setVisible(true);
        createNewTrip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.remove(welcomePanel);
                JPanel tripPanelInitial = new JPanel();
                JTextArea tripPanelInstructions = new JTextArea("Please select cities you'd like to visit, " +
                        "in order");
                JList<String> trainStations;
                try {
                    trainStations = new JList<>(PlannerDatabase.getTrainStations());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                JScrollPane jsp = new JScrollPane(trainStations);
                tripPanelInitial.add(jsp, BorderLayout.NORTH);
                tripPanelInitial.add(trainStations);
                jf.add(tripPanelInitial);
                jf.setVisible(true);
            }
        });
    }
    public static void welcomeScreen(JFrame jf) {

    }
}
