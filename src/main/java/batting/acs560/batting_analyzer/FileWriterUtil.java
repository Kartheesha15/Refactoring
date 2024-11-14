package batting.acs560.batting_analyzer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterUtil {
    
    public void writeAnalysisToFile(List<Player> players, String outputFile, DataAnalyzer analyzer) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("Player Stats Analysis\n");
            writer.write("------------------------\n");

            for (Player player : players) {
                writer.write(player.toString() + "\n");
            }

            analyzer.findTopScorer(players).ifPresent(player ->
                    writeHighestStat(writer, "Most Runs", player.getRuns(), player.getName()));

            analyzer.findTopAveragePlayer(players).ifPresent(player ->
                    writeHighestStat(writer, "Highest Average", player.getAverage(), player.getName()));

            analyzer.findTopStrikeRatePlayer(players).ifPresent(player ->
                    writeHighestStat(writer, "Highest Strike Rate", player.getStrikeRate(), player.getName()));
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void writeHighestStat(FileWriter writer, String statName, Object statValue, String playerName) {
        try {
            writer.write(String.format("%s: %s by %s%n", statName, statValue, playerName));
        } catch (IOException e) {
            System.out.println("Error writing stat to file: " + e.getMessage());
        }
    }
}
