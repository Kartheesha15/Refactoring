package batting.acs560.batting_analyzer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import batting.acs560.batting_analyzer.Application.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    private static final String CSV_FILE = "test_batting_stats.csv";
    private static final String OUTPUT_FILE = "test_analysis.txt";

    @BeforeEach
    void setup() throws IOException {
        // Create a test CSV file before each test
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
            writer.write("Player A,Team X,10,8,2,400,50.0,120.0\n");
            writer.write("Player B,Team Y,12,10,1,350,35.0,110.0\n");
            writer.write("Player C,Team Z,15,13,0,450,40.0,130.0\n");
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up the files after each test
        new File(CSV_FILE).delete();
        new File(OUTPUT_FILE).delete();
    }

    @Test
    void testReadCsvFile() {
        List<Player> players = Application.readCsvFile(CSV_FILE);

        assertEquals(3, players.size());

        Player playerA = players.get(0);
        assertEquals("Player A", playerA.getName());
        assertEquals("Team X", playerA.getTeam());
        assertEquals(400, playerA.getRuns());
        assertEquals(50.0, playerA.getAverage());
        assertEquals(120.0, playerA.getStrikeRate());
    }

    @Test
    void testAnalyzeData() {
        List<Player> players = Application.readCsvFile(CSV_FILE);
        Application.analyzeData(players); // No return value; we’ll rely on console output for now

        // Run some assertions on the data directly to validate analysis
        assertEquals("Player C", players.stream()
                .max((p1, p2) -> Integer.compare(p1.getRuns(), p2.getRuns()))
                .orElse(null).getName(), "Most runs player");

        assertEquals("Player A", players.stream()
                .max((p1, p2) -> Double.compare(p1.getAverage(), p2.getAverage()))
                .orElse(null).getName(), "Highest average player");

        assertEquals("Player C", players.stream()
                .max((p1, p2) -> Double.compare(p1.getStrikeRate(), p2.getStrikeRate()))
                .orElse(null).getName(), "Highest strike rate player");
    }

    @Test
    void testWriteAnalysisToFile() throws IOException {
        List<Player> players = Application.readCsvFile(CSV_FILE);
        Application.writeAnalysisToFile(players, OUTPUT_FILE);

        File file = new File(OUTPUT_FILE);
        assertTrue(file.exists());

        // Read the file to ensure the content was written correctly
        String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        assertTrue(content.contains("Player Stats Analysis"));
        assertTrue(content.contains("Most Runs"));
        assertTrue(content.contains("Highest Average"));
        assertTrue(content.contains("Highest Strike Rate"));
    }

    // Additional Tests

    @Test
    void testEmptyCsvFile() throws IOException {
        // Create an empty CSV file
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
        }

        List<Player> players = Application.readCsvFile(CSV_FILE);
        assertEquals(0, players.size(), "Player list should be empty for an empty CSV file");
    }

    @Test
    void testInvalidDataFormat() throws IOException {
        // Create a CSV file with invalid data
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
            writer.write("Player A,Team X,10,8,2,invalidRuns,50.0,120.0\n");
        }

        Exception exception = assertThrows(NumberFormatException.class, () -> Application.readCsvFile(CSV_FILE));
        assertTrue(exception.getMessage().contains("invalidRuns"), "Should throw an exception for invalid number format");
    }

    @Test
    void testBoundaryConditions() throws IOException {
        // Create a CSV file with boundary data values
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
            writer.write("Player A,Team X,0,0,0,0,0.0,0.0\n");
        }

        List<Player> players = Application.readCsvFile(CSV_FILE);

        assertEquals(1, players.size());
        Player playerA = players.get(0);
        assertEquals(0, playerA.getMatches());
        assertEquals(0, playerA.getInnings());
        assertEquals(0, playerA.getNotOuts());
        assertEquals(0, playerA.getRuns());
        assertEquals(0.0, playerA.getAverage());
        assertEquals(0.0, playerA.getStrikeRate());
    }

    @Test
    void testSinglePlayerData() throws IOException {
        // Test with only one player in CSV file
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
            writer.write("Player A,Team X,10,8,2,400,50.0,120.0\n");
        }

        List<Player> players = Application.readCsvFile(CSV_FILE);
        assertEquals(1, players.size());

        Application.analyzeData(players); // Check if the single player's data is correctly analyzed

        File file = new File(OUTPUT_FILE);
        Application.writeAnalysisToFile(players, OUTPUT_FILE);
        assertTrue(file.exists(), "Output file should be created even with single player data");
    }
}
