public class League {
    public Team[] teams;
    public int numTeams;

    public League() {
        
    }

    public Team getTeam(int index) {return teams[index];}
    public int getNumTeams() {return numTeams;}
    public void setNumTeams(int numTeams) {
        this.numTeams = numTeams;
        teams = new Team[numTeams]; 
    }

    // Gets the team with the specified coaches kid (null if team not found)
    public Team getTeam(String coachKid) {
        for(int i = 0; i < numTeams; i++) {
            if(teams[i].getCoachName().equals(coachKid)) {
                return teams[i];
            }
        }
        return null;
    }

    // Checks if all league spaces are filled
    public boolean spaceForTeam() {
        for(int i = 0; i < numTeams; i++) {
            if(teams[i] == null) {
                return true;
            }
        }
        return false;
    }
    // Adds a team to the league
    public void addTeam(Team team) {
        for(int i = 0; i < teams.length; i++) {
            if(teams[i] == null) {
                teams[i] = team;
                return;
            }
        }
    }

    // Fills remaining teams in the league using staff coaches
    public void fillRemanTeams() {
        int teamNumber = 1;
        for(int i = 0; i < teams.length; i++) {
            if(teams[i] == null) {
                teams[i] = new Team("Indoor Staff " + teamNumber);
                teamNumber++;
            }
        }
    }
    // Puts coach's friend requests on teams
    public void partnerCoachRequests(Team generalRoster) {
        for(Team team : teams) {
            if(team.getCoach() != null) {
                Player coach = team.getCoach();
                //check if reciprocated
                //add friend
                if(generalRoster.checkReciprocate(coach)) {
                    team.addPlayer(generalRoster.findPlayer(coach.getFriend()));
                    generalRoster.removePlayer(generalRoster.findPlayer(coach.getFriend()));
                }
                generalRoster.removePlayer(coach);
            }
        }
    }
    // Fills the teams with the remaining players in such that the average age of all the teams is as even as possible
    public void fillTeams(Team generalRoster) {
        boolean direction = true; // The direction of the sorter with true being down the list and false up the list
        int numPlayers = 0;
        while(generalRoster.teamSize() > 0) {
            if(direction) {
                // Add a player to every team with the lowest number of players
                for(int i = 0; i < numTeams; i++) {
                    if(generalRoster.teamSize() > 0 && teams[i].teamSize() == numPlayers) {
                        Player currentPlayer = generalRoster.getPlayer(0);
                        // Adds the friend request
                        if(generalRoster.checkReciprocate(currentPlayer)) {
                            teams[i].addPlayer(generalRoster.findPlayer(currentPlayer.getFriend()));
                            generalRoster.removePlayer(generalRoster.findPlayer(currentPlayer.getFriend()));
                        }
                        teams[i].addPlayer(currentPlayer);
                        generalRoster.removePlayer(currentPlayer);
                    } else if(generalRoster.teamSize() == 0) {
                        return;
                    }
                }
                direction = false;
            } else if(!direction) {
                // Add a player to every team with the lowest number of players
                for(int i = numTeams; i > 0; i--) {
                    if(generalRoster.teamSize() > 0 && teams[i-1].teamSize() == numPlayers) {
                        Player currentPlayer = generalRoster.getPlayer(0); 
                        // Adds the friend request
                        if(generalRoster.checkReciprocate(currentPlayer)) {
                            teams[i-1].addPlayer(generalRoster.findPlayer(currentPlayer.getFriend()));
                            generalRoster.removePlayer(generalRoster.findPlayer(currentPlayer.getFriend()));
                        }
                        teams[i-1].addPlayer(currentPlayer);
                        generalRoster.removePlayer(currentPlayer);
                    } else if(generalRoster.teamSize() == 0) {
                        return;
                    }
                }
                direction = true;
            }
            // Sets the new lowest number of players
            int smallTeam = teams[0].teamSize();
            for(Team team : teams) {
                if(team.teamSize() < smallTeam) {
                    smallTeam = team.teamSize();
                }
            }
            numPlayers = smallTeam;
        }
    }


    @Override
    public String toString() {
        String str = "";
        for(Team team : teams) {
            double aveAge = 0;
            str += "\n" + team.getCoachName() + "'s team";
            for(int i = 0; i < team.teamSize(); i++) {
                str += "\n" + team.getPlayer(i);
                aveAge += team.getPlayer(i).getAge();
            }
            str += "\n" + (aveAge/team.teamSize()) + "\n";
        }
        return str;
    }
}
