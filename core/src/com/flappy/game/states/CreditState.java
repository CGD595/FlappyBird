package com.flappy.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.flappy.game.FlappyDemo;
import com.badlogic.gdx.math.Vector3;
public class CreditState extends State {
    private Texture background;
    private BitmapFont font;
    private int score;
    // Define a Preferences object to save and load data
    public CreditState(GameStateManager gsm, int score) {
        super(gsm);
        this.score = score;
        // Initialize preferences with a unique name (can be your game's name)
        // Load the highest score from preferences
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        font = new BitmapFont();
    }
    @Override
    protected void handleinput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            if (touchPos.x >= cam.position.x - 50 && touchPos.x <= cam.position.x + 50
                    && touchPos.y >= cam.position.y - 25 && touchPos.y <= cam.position.y + 25) {
                gsm.set(new PlayState(gsm));
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
        sb.begin();

        sb.draw(background, cam.position.x - cam.viewportWidth / 2, 0);

        font.draw(sb, String.format("Your Score: %d", score), cam.position.x, cam.position.y + 50, 0, Align.center, false);
        font.getData().setScale(1f);
        font.draw(sb, "Restart", cam.position.x, cam.position.y+20, 0, Align.center, false);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
    }
}

