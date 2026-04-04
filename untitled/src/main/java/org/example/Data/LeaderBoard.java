package org.example.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Comparator;

public class LeaderBoard {
    private ArrayList<Score> scoreList;

    @JsonCreator
    public LeaderBoard(@JsonProperty("scoreList") ArrayList<Score> scoreList) {
        this.scoreList = (scoreList != null) ? scoreList : new ArrayList<>();
    }
    public ArrayList<Score> getScoreList() {
        if (scoreList == null) {
            scoreList = new ArrayList<>();
        }
        return scoreList;
    }

    public void addToScoreList(Score newScore){
        for (int i = 0; i < scoreList.size(); i++) {
            if (scoreList.get(i).getID().equals(newScore.getID())) {
                scoreList.set(i, newScore);
                sortAndTrim();
                return;
            }
        }
        scoreList.add(newScore);
        sortAndTrim();
    }

    private void sortAndTrim(){
        scoreList.sort(Comparator.comparingInt(Score::getScore).reversed());
        while (scoreList.size() > 5) {
            scoreList.remove(scoreList.size() - 1);
        }
    }

    public void removeFromScoreList(Score score){
        this.scoreList.remove(score);
    }

    public int count(){
        return this.scoreList.size();
    }

}
