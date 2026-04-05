package org.example.Data;
import org.example.Game.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;

public class DataClass {
    private static final String SAVEPATH = "data/save.json";
    private static final String LEADERBOARDPATH = "data/leaderboard.json";
    private static ObjectMapper mapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    public static void saveGame(Game game) throws Exception {
        System.out.println(
                mapper.writeValueAsString(game)
        );
        File file = new File(SAVEPATH);
        file.getParentFile().mkdirs();
        mapper.writeValue(file, game);
    }

    public static Game loadGame() throws Exception {
        File file = new File(SAVEPATH);
        if (!file.exists() || file.length() == 0) {
            return null;
        }
        try {
            Game game = mapper.readValue(file, Game.class);
            return game;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean isSaveExist(){
        File file = new File(SAVEPATH);
        System.out.println(file.exists() && file.length()>0);
        return file.exists() && file.length()>0;
    }

    public static void clearSave() throws IOException {
        File file = new File(SAVEPATH);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void saveLeaderBoard(LeaderBoard lb) throws IOException {
        File file = new File(LEADERBOARDPATH);
        file.getParentFile().mkdirs();
        mapper.writeValue(file, lb);
    }

    public static LeaderBoard loadLeaderBoard() {
        File file = new File(LEADERBOARDPATH);
        if (!file.exists() || file.length() == 0) {
            return new LeaderBoard(null);
        }
        try {
            return mapper.readValue(file, LeaderBoard.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new LeaderBoard(null);
        }
    }


}
