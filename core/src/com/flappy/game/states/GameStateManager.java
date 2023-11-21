package com.flappy.game.states;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {
    private static class Node {
        State data;
        Node next;

        Node(State data) {
            this.data = data;
            this.next = null;
        }
    }
    private Node top;

    public GameStateManager() {
        top = null;
    }

    public void push(State state) {
        Node newNode = new Node(state);
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
    }

    public void pop() {
        if (top != null) {
            top.data.dispose();
            top = top.next;
        }
    }

    public void set(State state) {
        if (top != null) {
            top.data.dispose();
            top.data = state;
        } else {
            push(state);
        }
    }

    public void update(float dt) {
        if (top != null) {
            top.data.update(dt);
        }
    }

    public void render(SpriteBatch sb) {
        if (top != null) {
            top.data.render(sb);
        }
    }
}