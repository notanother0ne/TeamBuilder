import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


public class Display extends JPanel implements Runnable, MouseListener, ActionListener {
    private JPanel friendPanel, teamPanel;
    private JFrame frame;
    private Dimension dim;
    private Color offBlack;
    private static final String FRIENDPANEL = "Friend Request Review";
    private static final String TEAMPANEL = "Teams";

    private League league;
    private Team playerList;

    public JFileChooser fc;
    public File file;

    int numTeams;

    JOptionPane numPrompt;

    private Thread thread;
    @SuppressWarnings("static-access")
    public Display(League league, Team playerList) {
        offBlack = new Color(32,32,32);
        // Creates the league size prompt
        numPrompt = new JOptionPane();
        numTeams = (int)Double.parseDouble(numPrompt.showInputDialog(null,
                                            "Enter number of teams in league (Whole numbers only)",
                                            "League Size",
                                            JOptionPane.QUESTION_MESSAGE));

        // Create the file chooser
        fc = new JFileChooser();
        fc.showOpenDialog(null);
        fc.setPreferredSize(new Dimension(500, 500));
        file = fc.getSelectedFile();

        // Create the friend and built team pages
        this.league = league;
        this.playerList = playerList;

        frame = new JFrame("Team Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dim = new Dimension(1500, 1000);
        frame.setSize(dim);
        frame.setPreferredSize(dim);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.addMouseListener(this);

        // Create the friend page
        friendPanel = new JPanel();
        friendPanel.setBackground(offBlack);
        friendPanel.setLayout(new BoxLayout(friendPanel, BoxLayout.PAGE_AXIS));
        friendPanel.setAlignmentX(LEFT_ALIGNMENT);
        JLabel friendLabel = new JLabel("<html><font color=#FFFFFF>Name Color = Friend Request Status: White = No Request, " +
                                        "<font color=#FF0000>Red</font>" +
                                        "<font color=#FFFFFF> = Request not Reciprocated, </font>" +
                                        "<font color=#009900>Green</font>" +
                                        "<font color=#FFFFFF> = Request Reciprocated</font>");
        friendLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        friendPanel.add(friendLabel);
        friendLabel.setAlignmentX(Component.LEFT_ALIGNMENT);        

        // Create the teams page
        teamPanel = new JPanel();
        teamPanel.setBackground(offBlack);
        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.Y_AXIS));
        teamPanel.setAlignmentX(LEFT_ALIGNMENT);
        JLabel teamLabel = new JLabel("<html><font color=#FFFFFF>Name Color = Skill Level: </font>" +
                                        "<font color=#FF0000>Red</font>" +
                                        "<font color=#FFFFFF> = New, </font>" +
                                        "<font color=#FFD000>Yellow</font>" +
                                        "<font color=#FFFFFF> = Recreational, </font>" +
                                        "<font color=#009900>Green</font>" +
                                        "<font color=#FFFFFF> = Competitive<br>FR Color = Friend Status: White = No Request, </font>" +
                                        "<font color=#FF0000>Red</font>" +
                                        "<font color=#FFFFFF> = Request not Reciprocated, </font>" +
                                        "<font color=#009900>Green</font>" +
                                        "<font color=#FFFFFF> = Request Reciprocated</font>");
        teamLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        teamPanel.add(teamLabel);
        teamLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab(FRIENDPANEL, friendPanel);
        tabs.addTab(TEAMPANEL, teamPanel);
        frame.add(tabs, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);

        thread = new Thread(this);
        thread.start();
    }

    // Gets the desired number of teams in the league
    public int getNumTeams() {return numTeams;}
    // Gets the currently selected file
    public File getFile() {return file;}

    // Generate the friend page content
    public void generateFriendPage() {
        JPanel panel = new JPanel();
        panel.setBackground(offBlack);
        panel.setPreferredSize(dim);
        panel.setLayout(new GridLayout(15, 0));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        for(int i = 0; i < playerList.teamSize(); i++) {
            Player currentPlayer = playerList.getPlayer(i);
            JLabel label = new JLabel(currentPlayer.getName() + " friend: " + currentPlayer.getFriend());
            if(playerList.getPlayer(i).getFriend().equals("")) {
                label.setForeground(Color.WHITE);
            } else if(!playerList.checkReciprocate(playerList.findPlayer(currentPlayer.getName()))) {
                label.setForeground(Color.RED);
            } else if(playerList.checkReciprocate(playerList.findPlayer(currentPlayer.getName()))) {
                label.setForeground(new Color(0, 153, 0));
            }
            panel.add(label);
        }
        friendPanel.add(panel);   
    }
    // Generate the teams page content
    public void generateTeamsPage() {
        JPanel panel = new JPanel();
        panel.setBackground(offBlack);
        panel.setPreferredSize(dim);
        panel.setAlignmentX(LEFT_ALIGNMENT);
        int rows = (playerList.teamSize()/numTeams >= 18) ? 1
            : (playerList.teamSize()/numTeams >= 10) ? 2
            : (playerList.teamSize()/numTeams >= 7) ? 3
            : (playerList.teamSize()/numTeams >= 5) ? 4
            : 5;
        panel.setLayout(new GridLayout(rows, 0));
        for(int i = 0; i < league.getNumTeams(); i++) {
            JPanel teamSection = new JPanel();
            teamSection.setBackground(offBlack);
            teamSection.setLayout(new BoxLayout(teamSection, BoxLayout.Y_AXIS));
            teamSection.setAlignmentX(LEFT_ALIGNMENT);
            Team currentTeam = league.getTeam(i);
            teamSection.add(new JLabel("<html><font color=#FFFFFF font size=+1>" + currentTeam.getCoachName() + "'s team</font><font color=#FFFFFF> (age)</font>"));
            for(int player = 0; player < currentTeam.teamSize(); player++) {
                Player currentPlayer = currentTeam.getPlayer(player);
                String label = "";
                if(currentPlayer.getFriend().equals("")) {
                    label += "<html><font color=#FFFFFF font size=+1>FR  </font>";
                } else if(!playerList.checkReciprocate(playerList.findPlayer(currentPlayer.getName()))) {
                    label += "<html><font color=#FF0000 font size=+1>FR  </font>";
                } else if(playerList.checkReciprocate(playerList.findPlayer(currentPlayer.getName()))) {
                    label += "<html><font color=#009900 font size=+1>FR  </font>";
                }

                if(currentPlayer.getLevel().equals("None / New to Sports") || currentPlayer.getLevel().equals("Some Sports / New to Soccer")) {
                    label += "<font color=#FF0000 font size=+1>" + currentPlayer.getName() + "</font>  ";
                } else if(currentPlayer.getLevel().equals("Some Soccer - Recreational") || currentPlayer.getLevel().equals("Lots of Soccer - Recreational")) {
                    label += "<font color=#FFD000 font size=+1>" + currentPlayer.getName() + "</font>  ";
                } else if(currentPlayer.getLevel().equals("Competitive Soccer and Training")) {
                    label += "<font color=#009900 font size=+1>" + currentPlayer.getName() + "</font>  ";
                }
                label += "<font color=#FFFFFF>" + currentPlayer.getAge();
                teamSection.add(new JLabel(label));
            }
            teamSection.add(new JLabel("<html><font color=#FFFFFF>Average Age: " + currentTeam.getAveAge()));
            panel.add(teamSection);
        }
        teamPanel.add(panel);
    }
        
    public void actionPerformed(ActionEvent action) {
        if (action.getActionCommand().equals("CancelSelection")) {
            // Cancel action
        } 
        if (action.getActionCommand().equals("ApproveSelection")) {
            file = fc.getSelectedFile();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
    }
}
