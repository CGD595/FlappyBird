package com.flappy.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ResetHighScoreCommand implements Command {
    private Preferences prefs;

    public ResetHighScoreCommand() {
        this.prefs = Gdx.app.getPreferences("FlappyBirdPrefs");
        Gdx.app.log("ResetHighScoreCommand", "Initialized command");
    }

    @Override
    public void execute() {
        try {
            prefs.putInteger("highScore", 0);
            prefs.flush();
            Gdx.app.log("ResetHighScoreCommand", "High score successfully reset to 0");
        } catch (Exception e) {
            Gdx.app.log("ResetHighScoreCommand", "Failed to reset high score: " + e.getMessage());
        }
    }
}
