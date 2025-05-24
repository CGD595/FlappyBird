package com.flappy.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.flappy.game.FlappyDemo;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Preferences;

public class CreditState extends State {
    private Texture background;
    private BitmapFont font;
    private int score;
    private int highScore;
    private Preferences prefs;
    private Command restartCommand;
    private Command resetHighScoreCommand;

    public CreditState(GameStateManager gsm, int score) {
        super(gsm);
        this.score = score;
        Gdx.app.log("CreditState", "Received score: " + score);
        prefs = Gdx.app.getPreferences("FlappyBirdPrefs");
        new SaveScoreCommand(score).execute();
        highScore = prefs.getInteger("highScore", 0);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        font = new BitmapFont();
        restartCommand = new RestartCommand(gsm);
        resetHighScoreCommand = new ResetHighScoreCommand();
    }

    @Override
    protected void handleinput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            // Restart button
            if (touchPos.x >= cam.position.x - 50 && touchPos.x <= cam.position.x + 50
                    && touchPos.y >= cam.position.y + 15 && touchPos.y <= cam.position.y + 65) {
                Gdx.app.log("CreditState", "Restart button touched at x=" + touchPos.x + ", y=" + touchPos.y);
                restartCommand.execute();
            }
            // Reset High Score button
            if (touchPos.x >= cam.position.x - 100 && touchPos.x <= cam.position.x + 100
                    && touchPos.y >= cam.position.y - 30 && touchPos.y <= cam.position.y + 30) {
                Gdx.app.log("CreditState", "Reset High Score button touched at x=" + touchPos.x + ", y=" + touchPos.y);
                resetHighScoreCommand.execute();
                highScore = prefs.getInteger("highScore", 0); // Refresh high score
                Gdx.app.log("CreditState", "High score updated to: " + highScore);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleinput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        sb.draw(background, cam.position.x - cam.viewportWidth / 2, 0);
        font.draw(sb, String.format("Your Score: %d", score),
                cam.position.x, cam.position.y + 120, 0, Align.center, false);
        font.draw(sb, String.format("High Score: %d", highScore),
                cam.position.x, cam.position.y + 80, 0, Align.center, false);
        font.getData().setScale(1f);
        font.draw(sb, "Restart", cam.position.x, cam.position.y + 40, 0, Align.center, false);
        font.draw(sb, "Reset High Score", cam.position.x, cam.position.y, 0, Align.center, false);
    }


    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        Gdx.app.log("CreditState", "Disposed resources");
    }
}
