package com.flappy.game.states;

import sprites.Bird;

public class JumpCommand implements Command {
    private Bird bird;

    public JumpCommand(Bird bird) {
        this.bird = bird;
    }

    @Override
    public void execute() {
        bird.jump();
    }
}