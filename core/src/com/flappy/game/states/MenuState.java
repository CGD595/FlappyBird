package com.flappy.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappy.game.FlappyDemo;

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private BitmapFont font; // New font variable for text rendering

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");

        // Initialize the font
        font = new BitmapFont();
        font.setColor(1, 1, 1, 1); // Set text color (white in this case)
    }

    @Override
    public void handleinput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
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
        sb.draw(background, 0, 0);
        sb.draw(playBtn, cam.position.x - 40, cam.position.y, 80, 40);

        // Draw text at the bottom center
        font.getData().setScale(0.5f);
        font.draw(sb, "Developed by  TG-SZ-STY", 10, 20);

        sb.end();
    }
    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        font.dispose(); // Dispose of the font when done
        System.out.println("Menu State Dispose");
    }
}
