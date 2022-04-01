package sgf.model.game;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Map;

import sgf.utilities.Pair;

/**
 * Class that managed the leaderboard.
 */
public class LeaderboardManagerImpl implements LeaderboardManager {

    private final Leaderboard leaderboard;

    public LeaderboardManagerImpl() {
        this.leaderboard = new LeaderboardImpl();
        this.readScore();
    }
    
    @Override
    public void writeScore() {
        try {
            this.leaderboard.getP().toFile().delete();
            this.leaderboard.getP().toFile().createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        this.leaderboard.getMapScore().entrySet().stream()
            .sorted((x, y) -> y.getValue().getY() - x.getValue().getY())
            .forEach(x -> {
            try {
                Files.writeString(this.leaderboard.getP(), x.getKey() + "|" + x.getValue().getX() + "|" + x.getValue().getY() + "\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void readScore() {
        try {
            Files.lines(this.leaderboard.getP()).forEach(x -> mapScore.put(x.split(":")[0], x.split(":")[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
