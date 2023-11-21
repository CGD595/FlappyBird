package com.flappy.game.states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flappy.game.FlappyDemo;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import java.util.Locale;
import sprites.Bird;
import sprites.Tube;
public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    // Custom linked list implementation for tubes
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

    private SimpleLinkedList tubes;
    BitmapFont font;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        bg = new Texture("bg.png");

        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x-cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x-cam.viewportWidth/2)+ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new SimpleLinkedList();
        for(int i = 1; i<= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        score = 0;
//        font = new BitmapFont(Gdx.files.internal("FlappyBirdRegular-9Pq0.ttf"));
        font = new BitmapFont();
    }

    @Override
    protected void handleinput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleinput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for (int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);

            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                score++; // Increment the score when the bird passes through a tube
            }
            if (tube.collides(bird.getBounds())) {
                gsm.set(new CreditState(gsm, score));
            }
        }
        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gsm.set(new CreditState(gsm, score));
        }
        cam.update();
    }
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y, 30, 20);

        for (int i = 0; i < tubes.size(); i++) {
            Tube tube = tubes.get(i);
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        font.draw(sb, String.format(Locale.getDefault(), "Score: %d", score),
                cam.position.x, cam.viewportHeight - 10,
                0, Align.center, false);

        sb.end();
    }
    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for (int i = 0; i < tubes.size(); i++) {
            tubes.get(i).dispose();
        }
        System.out.println("Play State Dispose");
    }
    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth/2)>groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(cam.position.x - (cam.viewportWidth/2)>groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }
}

// OpenAI. (2023). ChatGPT (Feb 13 version) [Large language model]. https://chat.openai.com