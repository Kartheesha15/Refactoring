package batting.acs560.batting_analyzer;

import java.util.List;

public class Application {

    private static final String CSV_FILE = "batting stats.csv";
    private static final String OUTPUT_FILE = "analysis.txt";

    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader();
        DataAnalyzer dataAnalyzer = new DataAnalyzer();
        FileWriterUtil fileWriterUtil = new FileWriterUtil();

        List<Player> players = csvReader.readPlayers(CSV_FILE);

        dataAnalyzer.findTopScorer(players).ifPresent(player -> 
            System.out.println("Most Runs: " + player.getRuns() + " by " + player.getName()));

        dataAnalyzer.findTopAveragePlayer(players).ifPresent(player ->
            System.out.println("Highest Average: " + player.getAverage() + " by " + player.getName()));

        dataAnalyzer.findTopStrikeRatePlayer(players).ifPresent(player ->
            System.out.println("Highest Strike Rate: " + player.getStrikeRate() + " by " + player.getName()));

        fileWriterUtil.writeAnalysisToFile(players, OUTPUT_FILE, dataAnalyzer);
    }
}
