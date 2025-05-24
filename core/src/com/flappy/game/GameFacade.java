package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappy.game.states.GameStateManager;

public class GameFacade {
    private SpriteBatch batch;
    private Texture bg;
    private Texture ground;

    public GameFacade() {
        batch = new SpriteBatch();
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
    }

    public void render(GameStateManager gsm) {
        gsm.update(Gdx.graphics.getDeltaTime());
        batch.begin();
        gsm.render(batch);
        batch.end();
    }

    public void dispose() {
        if (batch != null) batch.dispose();
        if (bg != null) bg.dispose();
        if (ground != null) ground.dispose();
    }
}
