public class Player {
    public String fullName /*3+4*/, firstName/*3*/, lastName/*4*/, phoneNumber/*6*/, level/*7*/, friend/*8*/, coach/*9*/;
    public double age/*10+11*/;

    public Player(String firstName, String lastName, String phone, String level, String friend, String coach, String year, String months) {
        this.firstName = firstName.toUpperCase();
        this.lastName = lastName.toUpperCase();
        this.fullName = firstName.toUpperCase() + " " + lastName.toUpperCase();
        this.phoneNumber = phone;
        this.level = level;
        this.friend = friend.toUpperCase();
        this.coach = coach;
        String str = ((Double.parseDouble(months)/12) + "0000").substring(1,4);
        this.age = Double.parseDouble(year) + Double.parseDouble(str);
    }

    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getName() {return fullName;}
    public String getNumber() {return phoneNumber;}
    public String getLevel() {return level;}
    public String getFriend() {return friend;}
    public String getCoach() {return coach;}
    public double getAge() {return age;}


    @Override
    public String toString() {
        return fullName + " " + friend;
    }
}