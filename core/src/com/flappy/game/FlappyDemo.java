package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.flappy.game.states.GameStateManager;
import com.flappy.game.states.MenuState;
import sprites.TubeFactory;
import com.flappy.game.SoundManager;

public class FlappyDemo extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Flappy Bird";
	private GameStateManager gsm;
	private GameFacade facade;
	private TubeFactory tubeFactory;

	@Override
	public void create() {
		facade = new GameFacade();
		gsm = new GameStateManager();
		tubeFactory = new TubeFactory();
		SoundManager.getInstance().initialize();
		ScreenUtils.clear(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render() {
		facade.render(gsm);
	}

	@Override
	public void dispose() {
		facade.dispose();
		tubeFactory.dispose();
		SoundManager.getInstance().dispose();
		Gdx.app.log("FlappyDemo", "Disposed application resources");
	}
}
