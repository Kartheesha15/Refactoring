package batting.acs560.batting_analyzer;

import java.util.List;
import java.util.Optional;

public class DataAnalyzer {

    public Optional<Player> findTopScorer(List<Player> players) {
        return players.stream().max((p1, p2) -> Integer.compare(p1.getRuns(), p2.getRuns()));
    }

    public Optional<Player> findTopAveragePlayer(List<Player> players) {
        return players.stream().max((p1, p2) -> Double.compare(p1.getAverage(), p2.getAverage()));
    }

    public Optional<Player> findTopStrikeRatePlayer(List<Player> players) {
        return players.stream().max((p1, p2) -> Double.compare(p1.getStrikeRate(), p2.getStrikeRate()));
    }
}
