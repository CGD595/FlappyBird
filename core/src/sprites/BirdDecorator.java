package sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public abstract class BirdDecorator extends Bird {
    protected Bird decoratedBird;

    public BirdDecorator(Bird decoratedBird) {
        super((int) decoratedBird.getPosition().x, (int) decoratedBird.getPosition().y);
        this.decoratedBird = decoratedBird;
    }

    @Override
    public void update(float dt) {
        decoratedBird.update(dt);
    }

    @Override
    public void jump() {
        decoratedBird.jump();
    }

    @Override
    public Vector3 getPosition() {
        return decoratedBird.getPosition();
    }

    @Override
    public TextureRegion getTexture() {
        return decoratedBird.getTexture();
    }

    @Override
    public Rectangle getBounds() {
        return decoratedBird.getBounds();
    }

    @Override
    public void dispose() {
        // Only dispose the decorated bird, not the textures here
    }

    public abstract boolean isInvincible();
    public abstract boolean isExpired();
}
