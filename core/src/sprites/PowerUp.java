package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PowerUp {
    public enum Type { SPEED_BOOST, INVINCIBILITY, SCORE_MULTIPLIER }
    private Texture texture;
    private Vector2 position;
    private Rectangle bounds;
    private Type type;
    private static final float RENDER_SIZE = 20;

    public PowerUp(float x, float y, Type type) {
        this.texture = new Texture("powerup.png");
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, RENDER_SIZE, RENDER_SIZE);
        this.type = type;
        Gdx.app.log("PowerUp", "Created power-up: " + type);
    }

    public void update(float dt, float cameraX) {
        position.x -= 100 * dt;
        bounds.setPosition(position.x, position.y);
    }

    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch sb) {
        if (texture != null) {
            sb.draw(texture, position.x, position.y, RENDER_SIZE, RENDER_SIZE);
            Gdx.app.log("PowerUp", "Rendered power-up at x=" + position.x + ", y=" + position.y);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Type getType() {
        return type;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
            Gdx.app.log("PowerUp", "Disposed power-up");
        }
    }
}
