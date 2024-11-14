package batting.acs560.batting_analyzer;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    private static final String CSV_FILE = "test_batting_stats.csv";
    private CsvReader csvReader;

    @BeforeEach
    void setup() throws IOException {
        csvReader = new CsvReader();
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
            writer.write("Player A,Team X,10,8,2,400,50.0,120.0\n");
            writer.write("Player B,Team Y,12,10,1,350,35.0,110.0\n");
        }
    }

    @AfterEach
    void tearDown() {
        new File(CSV_FILE).delete();
    }

    @Test
    void testReadPlayers() {
        List<Player> players = csvReader.readPlayers(CSV_FILE);
        assertEquals(2, players.size());
        assertEquals("Player A", players.get(0).getName());
    }

    @Test
    void testEmptyFile() throws IOException {
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
        }
        List<Player> players = csvReader.readPlayers(CSV_FILE);
        assertTrue(players.isEmpty(), "Player list should be empty for an empty CSV file");
    }

    @Test
    void testInvalidDataFormat() throws IOException {
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.write("name,team,matches,innings,notouts,runs,average,strikeRate\n");
            writer.write("Player A,Team X,10,8,2,invalidRuns,50.0,120.0\n");
        }
        assertThrows(NumberFormatException.class, () -> csvReader.readPlayers(CSV_FILE));
    }
}
