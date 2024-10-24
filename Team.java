import java.util.ArrayList;

public class Team {
    public String coachKid; // name of player who has parent intested in coaching, also team name
    public ArrayList<Player> players;

    public Team(String coachKidName) {
        players = new ArrayList<>();
        this.coachKid = coachKidName;
    }

    public int teamSize() {return players.size();}
    // Returns the player at the requested index
    public Player getPlayer(int index) { return players.get(index);}
    // Returns the coach's player's name
    public String getCoachName() {return coachKid;}
    // Returns the coach's player's object
    public Player getCoach() {
        if(players.size() > 0) {
            return players.get(0);
        } else {
            return null;
        }
    }
    // Returns the average age of the team
    public double getAveAge() {
        double age = 0;
        for(Player player : players) {
            age += player.getAge();
        }
        String str = (age/teamSize()) + "00000";
        return Double.parseDouble(str.substring(0,4));
    }

    // Adds a player to the team
    public void addPlayer(Player player) {
        players.add(player);
    }
    // Removes a player from the team
    public void removePlayer(Player player) {
        players.remove(player);
    }

    // Sorts the players youngest to oldest
    public void sortByAge() {
        
        ArrayList<Player> sortedTeam = new ArrayList<>();
        for(int i = 0; i < teamSize(); i++) {
            if(i == 0) {
                sortedTeam.add(players.get(i));
            } else {
                for(int j = 0; j < teamSize(); j++) {
                    if(j < sortedTeam.size()) {
                        if(players.get(i).getAge() < sortedTeam.get(j).getAge()) {
                            sortedTeam.add(j, players.get(i));
                            j = teamSize();
                        } else if(j == sortedTeam.size() - 1) {
                            sortedTeam.add(players.get(i));
                            j = teamSize();
                        }
                    }
                    
                }
            }
        }
        players = sortedTeam;
    }
    // Finds the youngest player on a team
    public Player findYoungest() {
        if(teamSize() > 0) {
            Player youngest = players.get(0);
            for(Player player : players) {
                if(player.getAge() < youngest.getAge()) {
                    youngest = player;
                }
            }
            return youngest;
        } else {
            return null;
        }
    }
    // Returns the object of a requested player using their name (null if not found)
    public Player findPlayer(String name) {
        for(int i = 0; i < teamSize(); i++) {
            // No friend request
            if(name.equals("")) {
                return null;
            } 
            // Exact names, shortened names, just first name
            else if(name.contains(players.get(i).getName()) || players.get(i).getName().contains(name)) {
                return players.get(i);
            }
            // Contains both the first and last name not in consecutive order
            else if(name.contains(players.get(i).getFirstName()) && name.contains(players.get(i).getLastName())) {
                return players.get(i);
            }
            // Mostly matching for misspelling
            else if(JaroWinklerCalc.jaroWinkler(name, players.get(i).getName()) > 0.85) {
                return players.get(i);
            }
        }
        return null;
    }
    // Checks if a friend request is reciprocated
    public boolean checkReciprocate(Player player) {
        Player friend = findPlayer(player.getFriend());
        if(friend != null) {
            Player friendsFriend = findPlayer(friend.getFriend());
            if(friendsFriend == player) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }    

    @Override
    public String toString() {
        String str = "";
        double aveAge = 0;
        for(Player player : players) {
            str += "\n" + player.getName() + ": " + player.getAge();
            aveAge += player.getAge();
        }
        str += "\n" + (aveAge/teamSize());
        return str;
    }
}
