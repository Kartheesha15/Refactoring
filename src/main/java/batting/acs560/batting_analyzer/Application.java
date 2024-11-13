package batting.acs560.batting_analyzer;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        String csvFile = "batting stats.csv";
        String outputFile = "analysis.txt";

        List<Player> players = readCsvFile(csvFile);
        analyzeData(players);
        writeAnalysisToFile(players, outputFile);
    }

    public static List<Player> readCsvFile(String csvFile) {
        List<Player> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Player player = new Player(
                        values[0],
                        values[1],
                        Integer.parseInt(values[2]),
                        Integer.parseInt(values[3]),
                        Integer.parseInt(values[4]),
                        Integer.parseInt(values[5]),
                        Double.parseDouble(values[6]),
                        Double.parseDouble(values[7])
                );
                players.add(player);
            }
        } catch (IOException e) {
            System.out.println("Unable to read csv file");
        }

        return players;
    }

    public static void analyzeData(List<Player> players) {
        int topScorerRuns = 0;
        double topAverage = 0;
        double topStrikeRate = 0;
        String topScorer = "";
        String topAveragePlayer = "";
        String topStrikeRatePlayer = "";

        for (Player player : players) {
            if (player.getRuns() > topScorerRuns) {
                topScorerRuns = player.getRuns();
                topScorer = player.getName();
            }
            if (player.getAverage() > topAverage) {
                topAverage = player.getAverage();
                topAveragePlayer = player.getName();
            }
            if (player.getStrikeRate() > topStrikeRate) {
                topStrikeRate = player.getStrikeRate();
               topStrikeRatePlayer = player.getName();
            }
        }

        System.out.println("Most Runs: " + topScorerRuns + " by " + topScorer);
        System.out.println("Highest Average: " + topAverage + " by " + topAveragePlayer);
        System.out.println("Highest Strike Rate: " + topStrikeRate + " by " + topStrikeRatePlayer);
    }

    public static void writeAnalysisToFile(List<Player> players, String outputFile) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("Player Stats Analysis\n");
            writer.write("------------------------\n");

            for (Player player : players) {
                writer.write(player.toString() + "\n");
            }

            int topScorerRuns = 0;
            double topAverage = 0;
            double topStrikeRate = 0;
            String topScorer = "";
            String topAveragePlayer = "";
            String topStrikeRatePlayer = "";

            for (Player player : players) {
                if (player.getRuns() > topScorerRuns) {
                    topScorerRuns = player.getRuns();
                    topScorer = player.getName();
                }
                if (player.getAverage() > topAverage) {
                    topAverage = player.getAverage();
                    topAveragePlayer = player.getName();
                }
                if (player.getStrikeRate() > topStrikeRate) {
                    topStrikeRate = player.getStrikeRate();
                    topStrikeRatePlayer = player.getName();
                }
            }

            writer.write("\nHighest Stats\n");
            writer.write("-------------\n");
            writer.write("Most Runs: " + topScorerRuns + " by " + topScorer + "\n");
            writer.write("Highest Average: " + topAverage + " by " + topAveragePlayer + "\n");
            writer.write("Highest Strike Rate: " + topStrikeRate + " by " +topStrikeRatePlayer + "\n");
        } catch (IOException e) {
            System.out.println("output file not found");
        }
    }

    public static class Player {
        private String name;
        private String team;
        private int matches;
        private int innings;
        private int notouts;
        private int runs;
        private double average;
        private double strikeRate;

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

        public String getName() {
            return name;
        }

        public String getTeam() {
            return team;
        }

        public int getMatches() {
            return matches;
        }

        public int getInnings() {
                return innings;
            }

            public int getNotOuts() {
                return notouts;
            }

            public int getRuns() {
                return runs;
            }

            public double getAverage() {
                return average;
            }

            public double getStrikeRate() {
                return strikeRate;
            }

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
    }