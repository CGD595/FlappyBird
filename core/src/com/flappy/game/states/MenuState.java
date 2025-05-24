package com.flappy.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.flappy.game.FlappyDemo;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Preferences;

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private BitmapFont font;
    private Preferences prefs;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        font = new BitmapFont();
        prefs = Gdx.app.getPreferences("FlappyBirdPrefs");
    }

    @Override
    public void handleinput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            if (touchPos.x >= cam.position.x - playBtn.getWidth() / 2
                    && touchPos.x <= cam.position.x + playBtn.getWidth() / 2
                    && touchPos.y >= cam.position.y - playBtn.getHeight() / 2
                    && touchPos.y <= cam.position.y + playBtn.getHeight() / 2) {
                gsm.set(new PlayState(gsm));
            }
            if (touchPos.x >= cam.position.x - 100 && touchPos.x <= cam.position.x + 100
                    && touchPos.y >= cam.position.y - 80 && touchPos.y <= cam.position.y - 40) {
                gsm.set(new LeaderboardState(gsm));
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
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y - playBtn.getHeight() / 2);

        int highScore = prefs.getInteger("highScore", 0);
        font.draw(sb, String.format("High Score: %d", highScore), cam.position.x, cam.position.y + 80, 0, Align.center, false);
        font.draw(sb, "Leaderboard", cam.position.x, cam.position.y - 60, 0, Align.center, false);
    }



    @Override
    public void dispose() {
        // Defer to GameFacade
    }
}