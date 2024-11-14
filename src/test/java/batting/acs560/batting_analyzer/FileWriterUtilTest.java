package batting.acs560.batting_analyzer;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileWriterUtilTest {

    private static final String OUTPUT_FILE = "test_analysis.txt";
    private FileWriterUtil fileWriterUtil;
    private DataAnalyzer dataAnalyzer;
    private List<Player> players;

    @BeforeEach
    void setup() {
        fileWriterUtil = new FileWriterUtil();
        dataAnalyzer = new DataAnalyzer();
        players = List.of(
                new Player("Player A", "Team X", 10, 8, 2, 400, 50.0, 120.0),
                new Player("Player B", "Team Y", 12, 10, 1, 350, 35.0, 110.0)
        );
    }

    @AfterEach
    void tearDown() {
        new File(OUTPUT_FILE).delete();
    }

    @Test
    void testWriteAnalysisToFile() throws IOException {
        fileWriterUtil.writeAnalysisToFile(players, OUTPUT_FILE, dataAnalyzer);
        File file = new File(OUTPUT_FILE);
        assertTrue(file.exists(), "Output file should be created");

        String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        assertTrue(content.contains("Most Runs"));
        assertTrue(content.contains("Highest Average"));
    }

    @Test
    void testSinglePlayerData() throws IOException {
        List<Player> singlePlayerList = List.of(new Player("Player A", "Team X", 10, 8, 2, 400, 50.0, 120.0));
        fileWriterUtil.writeAnalysisToFile(singlePlayerList, OUTPUT_FILE, dataAnalyzer);

        File file = new File(OUTPUT_FILE);
        assertTrue(file.exists(), "Output file should be created with single player data");
    }

    @Test
    void testEmptyPlayerData() throws IOException {
        fileWriterUtil.writeAnalysisToFile(List.of(), OUTPUT_FILE, dataAnalyzer);
        File file = new File(OUTPUT_FILE);
        assertTrue(file.exists(), "Output file should be created even with empty data");

        String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        assertTrue(content.contains("Player Stats Analysis"));
    }
}
