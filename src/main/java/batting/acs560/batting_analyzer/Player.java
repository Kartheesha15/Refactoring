package batting.acs560.batting_analyzer;

public class Player {
    private String name;
    private String team;
    private int matches;
    private int innings;
    private int notouts;
    private int runs;
    private double average;
    private double strikeRate;

    // Constructor, getters, and toString method
    public Player(String name, String team, int matches, int innings, int notouts, int runs, double average, double strikeRate) {
        this.name = name;
        this.team = team;
        this.matches = matches;
        this.innings = innings;
        this.notouts = notouts;
        this.runs = runs;
        this.average = average;
        this.strikeRate = strikeRate;
    }

    public String getName() { return name; }
    public String getTeam() { return team; }
    public int getMatches() { return matches; }
    public int getRuns() { return runs; }
    public double getAverage() { return average; }
    public double getStrikeRate() { return strikeRate; }

    @Override
    public String toString() {
        return "Player{" +
               "name='" + name + '\'' +
               ", team='" + team + '\'' +
               ", matches=" + matches +
               ", innings=" + innings +
               ", notOuts=" + notouts +
               ", runs=" + runs +
               ", average=" + average +
               ", strikeRate=" + strikeRate +
               '}';
    }
}
