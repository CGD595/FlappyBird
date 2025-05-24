package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private static SoundManager instance;
    private Music music;
    private Sound flap;
    private Sound scoreSound;
    private Sound powerUpSound;
    private Sound gameOverSound;

    private SoundManager() {
        music = Gdx.audio.newMusic(Gdx.files.internal("phudosa.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("sfx_point.ogg"));
        powerUpSound = Gdx.audio.newSound(Gdx.files.internal("sfx_powerup.ogg"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sfx_hit.ogg"));
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void initialize() {
        music.play();
    }

    public void playFlap() {
        if (flap != null) flap.play(0.5f);
    }

    public void playScore() {
        if (scoreSound != null) scoreSound.play(0.5f);
    }

    public void playPowerUp() {
        if (powerUpSound != null) powerUpSound.play(0.5f);
    }

    public void playGameOver() {
        if (gameOverSound != null) gameOverSound.play(0.5f);
    }

    public void dispose() {
        if (music != null) music.dispose();
        if (flap != null) flap.dispose();
        if (scoreSound != null) scoreSound.dispose();
        if (powerUpSound != null) powerUpSound.dispose();
        if (gameOverSound != null) gameOverSound.dispose();
        instance = null;
    }
}
