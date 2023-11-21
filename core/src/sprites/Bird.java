package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird{
    // Gravity and movement constants
    public static final int GRAVITY = -10;
    private static final int MOVEMENT = 100;

    private Vector3 position; // Bird's position
    private Vector3 velocity; // Bird's velocity (speed)
    private Rectangle bounds; // Bird's bounding box for collision detection
    private Animation birdAnimation; // Animation for bird's appearance
    private Sound flap; // Sound for the bird's flap

    // Constructor to initialize the bird
    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);

        // Load bird texture and create animation
        Texture texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        // Set bounding box dimensions
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());

        // Load flap sound
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    // Update bird's position and animation
    public void update(float dt) {
        birdAnimation.update(dt);

        // Apply gravity if the bird is above the ground
        if (position.y > 0)
            velocity.add(0, GRAVITY, 0);

        velocity.scl(dt);

        // Move the bird horizontally and vertically
        position.add(MOVEMENT * dt, velocity.y, 0);

        // Prevent the bird from going below the ground
        if (position.y < 0)
            position.y = 0;

        velocity.scl(1 / dt);

        // Update bounding box position
        bounds.setPosition(position.x, position.y);
    }

    // Make the bird jump
    public void jump() {
        velocity.y = 250;
        flap.play(0.5f); // Play flap sound
    }

    // Getter method to retrieve bird's position
    public Vector3 getPosition() {
        return position;
    }

    // Getter method to retrieve current bird texture frame
    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    // Getter method to retrieve bounding box for collision detection
    public Rectangle getBounds() {
        return bounds;
    }

    // Dispose of resources
    public void dispose() {
        birdAnimation.dispose();
        flap.dispose();
    }
}