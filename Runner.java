import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;

public class Runner {
    public static void main(String args[]) throws Exception{
        // Create league
        League league = new League();

        // Transfer roster to code, create teams if interest expressed (fill remaining teams with staff )
        String line = "";
        String splitBy = ",";
        Team generalRoster =  new Team("General Roster");
        Team playerList = new Team("Player List");

        // Create the display
        Display display = new Display(league, playerList);
        league.setNumTeams(display.getNumTeams());

        try {
            BufferedReader bR = new BufferedReader(new FileReader(display.getFile()));  
            while ((line = bR.readLine()) != null) {
                if(!line.startsWith("Team")){
                    String[] player = line.split(splitBy);
                    Player newPlayer = new Player(player[3], player[4], player[6], player[7], player[8], player[9], player[10], player[11]);
                    if(newPlayer.getCoach().equals("yes")) {
                        Team newTeam = new Team(newPlayer.getName());
                        newTeam.addPlayer(newPlayer);
                        generalRoster.addPlayer(newPlayer);
                        playerList.addPlayer(newPlayer);
                        league.addTeam(newTeam);
                    } else {
                        generalRoster.addPlayer(newPlayer);
                        playerList.addPlayer(newPlayer);
                    }
                }
                
            }
            bR.close();
            league.fillRemanTeams();
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        // Sort the general roster
        generalRoster.sortByAge();
        // Buddy the coach friend requests
        league.partnerCoachRequests(generalRoster);
        // Fill the rest of teams
        league.fillTeams(generalRoster);
        display.generateFriendPage();
        display.generateTeamsPage();
        // (Possibly) Generate a gui to display the teams including, the coaches kid, the skill of the players (color),
            // the age of the players, and if the friend request was fulfilled for review 
            // ^ secondary UI that displays only friend request statuses for review before building
    }    
}
