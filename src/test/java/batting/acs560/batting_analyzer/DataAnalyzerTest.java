package batting.acs560.batting_analyzer;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataAnalyzerTest {

    private DataAnalyzer dataAnalyzer;
    private List<Player> players;

    @BeforeEach
    void setup() {
        dataAnalyzer = new DataAnalyzer();
        players = List.of(
                new Player("Player A", "Team X", 10, 8, 2, 400, 50.0, 120.0),
                new Player("Player B", "Team Y", 12, 10, 1, 350, 35.0, 110.0),
                new Player("Player C", "Team Z", 15, 13, 0, 450, 40.0, 130.0)
        );
    }

    @Test
    void testFindTopScorer() {
        assertEquals("Player C", dataAnalyzer.findTopScorer(players).get().getName());
    }

    @Test
    void testFindTopAveragePlayer() {
        assertEquals("Player A", dataAnalyzer.findTopAveragePlayer(players).get().getName());
    }

    @Test
    void testFindTopStrikeRatePlayer() {
        assertEquals("Player C", dataAnalyzer.findTopStrikeRatePlayer(players).get().getName());
    }
}
