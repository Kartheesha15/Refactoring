package batting.acs560.batting_analyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<Player> readPlayers(String csvFile) {
        List<Player> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip header
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
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return players;
    }
}
