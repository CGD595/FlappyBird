package com.flappy.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveScoreCommand implements Command {
    private int score;
    private Preferences prefs;

    public SaveScoreCommand(int score) {
        this.score = score;
        this.prefs = Gdx.app.getPreferences("FlappyBirdPrefs");
        Gdx.app.log("SaveScoreCommand", "Saving score: " + score);
    }

    @Override
    public void execute() {
        if (score < 0 || score > 1000) {
            Gdx.app.log("SaveScoreCommand", "Invalid score detected: " + score);
            return;
        }
        int highScore = prefs.getInteger("highScore", 0);
        if (score > highScore) {
            prefs.putInteger("highScore", score);
            Gdx.app.log("SaveScoreCommand", "New high score: " + score);
        }
        updateLeaderboard(score);
        prefs.flush();
    }

    private void updateLeaderboard(int newScore) {
        List<Integer> scores = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int s = prefs.getInteger("leaderboard_score_" + i, 0);
            if (s > 0) scores.add(s);
        }
        scores.add(newScore);
        Collections.sort(scores, Collections.reverseOrder());
        for (int i = 0; i < Math.min(scores.size(), 5); i++) {
            prefs.putInteger("leaderboard_score_" + (i + 1), scores.get(i));
        }
    }
}
