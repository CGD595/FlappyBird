//Tube
package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tube {
    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 130;
    // Increase this value to widen the gap between tubes
    private static final int TUBE_GAP = 120;

    // Increase this value to raise the bottom tube position
    private static final int LOWEST_OPENING = 120;


    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Rectangle boundsTop, boundsBot;

    // Constructor initializes the Tube with random positions
    public Tube(float x) {
        // Load textures
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        // Set initial positions for the top and bottom tubes
        posTopTube = new Vector2(x + TUBE_GAP, getRandomY());
        posBotTube = new Vector2(x + TUBE_GAP, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        // Create bounding rectangles for collision detection
        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public void reposition(float x) {
        // Increase the TUBE_GAP value to widen the horizontal gap between tubes
        posTopTube.set(x + TUBE_GAP, getRandomY());
        posBotTube.set(x + TUBE_GAP, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        // Update bounding rectangle positions
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }


    // Check if the player collides with either the top or bottom tube
    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }



    // Dispose of resources to free up memory
    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }

    // Getter methods to access tube properties
    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    // Helper method to generate a random Y coordinate within the fluctuation range
    private float getRandomY() {
        return (float) (Math.random() * FLUCTUATION) + TUBE_GAP + LOWEST_OPENING;
    }
}