package com.flappy.game.states;

import com.flappy.game.FlappyDemo;

public class RestartCommand implements Command {
    private GameStateManager gsm;

    public RestartCommand(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void execute() {
        gsm.set(new PlayState(gsm));
    }
}