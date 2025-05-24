package com.flappy.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.flappy.game.FlappyDemo;
import com.flappy.game.SoundManager;
import com.flappy.game.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import java.util.Locale;
import java.util.Random;
import sprites.Bird;
import sprites.Tube;
import sprites.TubeFactory;
import sprites.PowerUp;
import sprites.SpeedBoostDecorator;
import sprites.InvincibilityDecorator;
import sprites.ScoreMultiplierDecorator;
import sprites.BirdDecorator;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    private static final float CAMERA_OFFSET = 80;
    private static final float POWER_UP_SPAWN_INTERVAL = 10f;
    private static final float DIFFICULTY_SCORE_THRESHOLD = 10;
    private static final float TUBE_SPEED_INCREMENT = 10;
    private Bird bird;
    private Bird baseBird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private float groundWidth;
    private SimpleLinkedList tubes;
    private BitmapFont font;
    private Command jumpCommand;
    private InputAdapter inputAdapter;
    private TubeFactory tubeFactory;
    private PowerUp powerUp;
    private float powerUpTimer;
    private Random random;
    private boolean isPaused;
    private float lastTouchTime;
    private static final float DOUBLE_TAP_THRESHOLD = 0.3f;
    private float tubeSpeed;

    private class SimpleLinkedList {
        private Node head;
        private int size;

        public void add(Tube tube) {
            Node newNode = new Node(tube);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            size++;
        }

        public Tube get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.data;
        }

        public int size() {
            return size;
        }

        private class Node {
            private Tube data;
            private Node next;

            public Node(Tube data) {
                this.data = data;
                this.next = null;
            }
        }
    }

    public PlayState(GameStateManager gsm) {
        super(gsm);
        baseBird = new Bird(50, 300);
        bird = baseBird;
        jumpCommand = new JumpCommand(bird);
        inputAdapter = new InputAdapter(jumpCommand);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundWidth = ground.getWidth();
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + groundWidth, GROUND_Y_OFFSET);
        tubeFactory = new TubeFactory();
        tubes = new SimpleLinkedList();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            Tube tube = tubeFactory.createTube(i * (TUBE_SPACING + Tube.TUBE_WIDTH));
            tubes.add(tube);
            Gdx.app.log("PlayState", "Created tube " + i + " at x=" + tube.getPosTopTube().x);
        }
        score = 0;
        font = new BitmapFont();
        powerUpTimer = 0;
        random = new Random();
        isPaused = false;
        lastTouchTime = 0;
        tubeSpeed = 100; // Base speed, matches Bird.MOVEMENT
        Gdx.app.log("PlayState", "Initialized with score: " + score);
    }

    @Override
    protected void handleinput() {
        inputAdapter.handleInput(); // Handle keyboard input
        float currentTime = Gdx.graphics.getRawDeltaTime();
        if (Gdx.input.justTouched()) {
            if (currentTime - lastTouchTime < DOUBLE_TAP_THRESHOLD) {
                isPaused = !isPaused;
                Gdx.app.log("PlayState", "Game " + (isPaused ? "paused" : "resumed"));
            } else {
                jumpCommand.execute();
                SoundManager.getInstance().playFlap();
            }
            lastTouchTime = currentTime;
        }
    }

    @Override
    public void update(float dt) {
        if (isPaused) return;

        handleinput();
        updateBird(dt);
        updatePowerUps(dt);
        updateTubes(dt);
        updateGround();
        updateCamera();
        updateDifficulty();
        Gdx.app.log("PlayState", "Ground positions: pos1.x=" + groundPos1.x + ", pos2.x=" + groundPos2.x + ", cam.x=" + cam.position.x);
    }

    private void updateBird(float dt) {
        bird.update(dt);
        if (!bird.isInvincible() && bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            SoundManager.getInstance().playGameOver();
            Gdx.app.log("PlayState", "Ground collision, transitioning to CreditState with score: " + score);
            gsm.set(new CreditState(gsm, score));
        }
    }

    private void updatePowerUps(float dt) {
        powerUpTimer += dt;
        if (powerUpTimer >= POWER_UP_SPAWN_INTERVAL && powerUp == null) {
            float x = cam.position.x + cam.viewportWidth / 2 + 100;
            float y = random.nextFloat() * (FlappyDemo.HEIGHT - 100) + 50;
            PowerUp.Type type;
            float rand = random.nextFloat();
            if (rand < 0.33) {
                type = PowerUp.Type.SPEED_BOOST;
            } else if (rand < 0.66) {
                type = PowerUp.Type.INVINCIBILITY;
            } else {
                type = PowerUp.Type.SCORE_MULTIPLIER;
            }
            powerUp = new PowerUp(x, y, type);
            powerUpTimer = 0;
            Gdx.app.log("PlayState", "Spawned power-up: " + type);
        }

        if (powerUp != null) {
            powerUp.update(dt, cam.position.x);
            if (powerUp.getBounds().overlaps(bird.getBounds())) {
                if (powerUp.getType() == PowerUp.Type.SPEED_BOOST) {
                    bird = new SpeedBoostDecorator(baseBird);
                } else if (powerUp.getType() == PowerUp.Type.INVINCIBILITY) {
                    bird = new InvincibilityDecorator(baseBird);
                } else if (powerUp.getType() == PowerUp.Type.SCORE_MULTIPLIER) {
                    bird = new ScoreMultiplierDecorator(baseBird);
                }
                jumpCommand = new JumpCommand(bird);
                inputAdapter = new InputAdapter(jumpCommand); // Update InputAdapter
                SoundManager.getInstance().playPowerUp();
                powerUp.dispose();
                powerUp = null;
                Gdx.app.log("PlayState", "Collected power-up, bird updated");
            } else if (powerUp.getBounds().x < cam.position.x - cam.viewportWidth / 2) {
                powerUp.dispose();
                powerUp = null;
                Gdx.app.log("PlayState", "Power-up despawned");
            }
        }

        if (bird instanceof BirdDecorator) {
            BirdDecorator decorator = (BirdDecorator) bird;
            if (decorator.isExpired()) {
                bird = baseBird;
                jumpCommand = new JumpCommand(bird);
                inputAdapter = new InputAdapter(jumpCommand); // Update InputAdapter
                Gdx.app.log("PlayState", "Reverted to base bird");
            }
        }
    }

    private void updateTubes(float dt) {
        for (int i = 0; i < tubes.size(); i++) {
            Tube tube = tubes.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                int scoreIncrement = (bird instanceof ScoreMultiplierDecorator) ? 2 : 1;
                score += scoreIncrement;
                SoundManager.getInstance().playScore();
                Gdx.app.log("PlayState", "Score incremented by " + scoreIncrement + " to: " + score);
            }
            if (!bird.isInvincible() && tube.collides(bird.getBounds())) {
                SoundManager.getInstance().playGameOver();
                Gdx.app.log("PlayState", "Collision detected, transitioning to CreditState with score: " + score);
                gsm.set(new CreditState(gsm, score));
                return;
            }
        }
    }

    private void updateCamera() {
        cam.position.x = bird.getPosition().x + CAMERA_OFFSET;
        cam.update();
    }

    private void updateDifficulty() {
        tubeSpeed = 100 + (score / DIFFICULTY_SCORE_THRESHOLD) * TUBE_SPEED_INCREMENT;
    }

    private void updateGround() {
        float camLeft = cam.position.x - cam.viewportWidth / 2;
        if (groundPos1.x + groundWidth < camLeft) {
            groundPos1.x = groundPos2.x + groundWidth;
        }
        if (groundPos2.x + groundWidth < camLeft) {
            groundPos2.x = groundPos1.x + groundWidth;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        // DO NOT CALL sb.begin();  ← removed

        if (bg != null) sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        if (bird != null) sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y, 30, 20);

        for (int i = 0; i < tubes.size(); i++) {
            Tube tube = tubes.get(i);
            if (tube.getTopTube() != null) sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            if (tube.getBottomTube() != null) sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        if (powerUp != null) {
            powerUp.render(sb); // Ensure this method doesn’t call sb.begin() either!
        }

        if (ground != null) {
            sb.draw(ground, groundPos1.x, groundPos1.y);
            sb.draw(ground, groundPos2.x, groundPos2.y);
        }

        if (font != null) {
            font.draw(sb, String.format(Locale.getDefault(), "Score: %d", score),
                    cam.position.x, cam.viewportHeight - 10, 0, Align.center, false);
            if (isPaused) {
                font.draw(sb, "Paused", cam.position.x, cam.viewportHeight / 2, 0, Align.center, false);
            }
        }

        // DO NOT CALL sb.end();  ← removed
    }


    @Override
    public void dispose() {
        // Defer disposal to GameFacade or FlappyDemo
        Gdx.app.log("PlayState", "Dispose called, deferring to GameFacade");
    }
}
