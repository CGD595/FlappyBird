package com.flappy.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.flappy.game.FlappyDemo;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardState extends State {
    private Texture background;
    private BitmapFont font;
    private Preferences prefs;
    private List<ScoreEntry> leaderboard;

    public LeaderboardState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        font = new BitmapFont();
        prefs = Gdx.app.getPreferences("FlappyBirdPrefs");
        leaderboard = loadLeaderboard();
    }

    private List<ScoreEntry> loadLeaderboard() {
        List<ScoreEntry> entries = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int score = prefs.getInteger("leaderboard_score_" + i, 0);
            if (score > 0) {
                entries.add(new ScoreEntry(score));
            }
        }
        return entries;
    }

    @Override
    protected void handleinput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new MenuState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleinput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - cam.viewportWidth / 2, 0);
        font.draw(sb, "Leaderboard", cam.position.x, cam.position.y + 120, 0, Align.center, false);
        for (int i = 0; i < leaderboard.size(); i++) {
            font.draw(sb, String.format("%d. %d", i + 1, leaderboard.get(i).getScore()),
                    cam.position.x, cam.position.y + 80 - (i * 40), 0, Align.center, false);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        // Defer to GameFacade
    }

    private class ScoreEntry {
        private int score;

        public ScoreEntry(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }
}
