import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Year;

/**
 * Amtrak Travel Planner - CreditsScreen
 *
 * GUI class for the Amtrak Travel Planner's
 * Credits Screen.
 *
 * @author Ayush Shukla Arora, L19
 *
 * @version April 22, 2024
 */

public class CreditsScreen extends PlannerDriver {
    private JPanel creditsContentPanel;
    private JLabel title;
    private JLabel info;
    private JLabel author;
    private JLabel dates;
    private JEditorPane dataSource;
    private JLabel specialThanks;
    private JLabel institution;
    private JEditorPane jsonParseSource;
    private JEditorPane github;
    private JEditorPane linkedIn;
    private JSplitPane socialsSplitPane;
    private JButton mainMenu;
    private JLabel copyright;
    private JLabel specialThanksContd;
    private JSplitPane sourcesSplitPane;
    private static JFrame jf;

    public CreditsScreen() {
        jf = new JFrame("Amtrak Travel Planner: Credits");
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setContentPane(creditsContentPanel);
        creditsContentPanel.setPreferredSize(new Dimension(640, 480));
        jf.setSize(640, 480);
        jf.setVisible(true);
        copyright.setText("© " + Year.now().getValue() + " Ayush Shukla Arora. All Rights Reserved. ");
        copyright.setFont(new Font(null, Font.PLAIN, 8));
        dataSource.setContentType("text/html");
        dataSource.setText("<html><body style='font-family: sans-serif; font-weight: bold; font-size: 9px; " +
                "text-align: center'>" +
                "Data from <a href='https://mgwalker.github.io/amtrak-api/'>Greg Walker's Amtrak API" +
                "</a></body>" +
                "</html>");
        dataSource.setOpaque(false);
        dataSource.setEditable(false);
        socialsSplitPane.setDividerLocation(0.5);
        sourcesSplitPane.setDividerLocation(0.5);
        dataSource.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        JOptionPane.showMessageDialog(null, "Unable to open link!\n" +
                                "https://mgwalker.github.io/amtrak-api/");
                    }
                }
            }
        });
        jsonParseSource.setContentType("text/html");
        jsonParseSource.setText("<html><body style='font-family: sans-serif; font-weight: bold; font-size: 9px; " +
                "text-align: center'>" +
                "<a href='https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751/'>" +
                "JSON Data Collection Tutorial" +
                "</a></body>" +
                "</html>");
        jsonParseSource.setOpaque(false);
        jsonParseSource.setEditable(false);
        jsonParseSource.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        JOptionPane.showMessageDialog(null, "Unable to open link!\n" +
                                "https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751/"
                        );
                    }
                }
            }
        });
        socialsSplitPane.setDividerLocation(0.5);
        github.setContentType("text/html");
        github.setText("<html><body style='font-family: sans-serif; font-weight: bold; font-size: 9px; " +
                "text-align: center'>" +
                "<a href='https://github.com/ayush-s-arora'>GitHub" +
                "</a></body>" +
                "</html>");
        github.setOpaque(false);
        github.setEditable(false);
        github.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        JOptionPane.showMessageDialog(null, "Unable to open link!\n" +
                                "https://github.com/ayush-s-arora"
                        );
                    }
                }
            }
        });
        linkedIn.setContentType("text/html");
        linkedIn.setText("<html><body style='font-family: sans-serif; font-weight: bold; font-size: 9px; " +
                "text-align: center'>" +
                "<a href='https://linkedin.com/in/ayush-s-arora'>LinkedIn" +
                "</a></body>" +
                "</html>");
        linkedIn.setOpaque(false);
        linkedIn.setEditable(false);
        linkedIn.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        JOptionPane.showMessageDialog(null, "Unable to open link!\n" +
                                "https://linkedin.com/in/ayush-s-arora"
                        );
                    }
                }
            }
        });

        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new WelcomeScreen();
            }
        });
    }
}