package batting.acs560.batting_analyzer;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    private static final String CSV_FILE = "batting stats.csv";
    private static final String OUTPUT_FILE = "analysis.txt";
    private CsvReader csvReader;
    private DataAnalyzer dataAnalyzer;
    private FileWriterUtil fileWriterUtil;

    @BeforeEach
    void setup() throws IOException {
        csvReader = new CsvReader();
        dataAnalyzer = new DataAnalyzer();
        fileWriterUtil = new FileWriterUtil();

        // Create a test CSV file
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
            writer.write("Player A,Team X,10,8,2,400,50.0,120.0\n");
            writer.write("Player B,Team Y,12,10,1,350,35.0,110.0\n");
            writer.write("Player C,Team Z,15,13,0,450,40.0,130.0\n");
        }
    }

    @AfterEach
    void tearDown() {
        new File(CSV_FILE).delete();
        new File(OUTPUT_FILE).delete();
    }

    @Test
    void testMainApplicationIntegration() throws IOException {
        // Simulate the main application workflow
        List<Player> players = csvReader.readPlayers(CSV_FILE);

        // Run analysis directly on the list of players
        dataAnalyzer.findTopScorer(players).ifPresent(player -> 
            assertEquals("Player C", player.getName(), "Player C should have the most runs"));

        dataAnalyzer.findTopAveragePlayer(players).ifPresent(player -> 
            assertEquals("Player A", player.getName(), "Player A should have the highest average"));

        dataAnalyzer.findTopStrikeRatePlayer(players).ifPresent(player -> 
            assertEquals("Player C", player.getName(), "Player C should have the highest strike rate"));

        // Write analysis results to a file and verify
        fileWriterUtil.writeAnalysisToFile(players, OUTPUT_FILE, dataAnalyzer);
        File outputFile = new File(OUTPUT_FILE);
        assertTrue(outputFile.exists(), "Output file should be created");

        // Verify that the output file contains expected analysis
        String content = Files.readString(outputFile.toPath());
        assertTrue(content.contains("Most Runs: 450 by Player C"));
        assertTrue(content.contains("Highest Average: 50.0 by Player A"));
        assertTrue(content.contains("Highest Strike Rate: 130.0 by Player C"));
    }

    @Test
    void testEmptyCsvFileHandling() throws IOException {
        // Create an empty CSV file with headers only
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
        }

        List<Player> players = csvReader.readPlayers(CSV_FILE);
        assertTrue(players.isEmpty(), "No players should be read from an empty CSV file");

        // Write analysis to a file and ensure it's properly handled
        fileWriterUtil.writeAnalysisToFile(players, OUTPUT_FILE, dataAnalyzer);
        File outputFile = new File(OUTPUT_FILE);
        assertTrue(outputFile.exists(), "Output file should still be created for empty player data");

        String content = Files.readString(outputFile.toPath());
        assertTrue(content.contains("Player Stats Analysis"), "Output file should include the basic analysis title even if no data is present");
    }
}
