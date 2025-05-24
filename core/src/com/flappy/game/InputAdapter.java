package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.flappy.game.states.Command;

public class InputAdapter {
    private Command jumpCommand;

    public InputAdapter(Command jumpCommand) {
        this.jumpCommand = jumpCommand;
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Gdx.app.log("InputAdapter", "Spacebar pressed");
            jumpCommand.execute();
        }
    }
}
