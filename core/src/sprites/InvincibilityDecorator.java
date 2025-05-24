package sprites;

import com.badlogic.gdx.Gdx;

public class InvincibilityDecorator extends BirdDecorator {
    private float duration;
    private float timeElapsed;
    private static final float DURATION = 5f;

    public InvincibilityDecorator(Bird decoratedBird) {
        super(decoratedBird);
        this.duration = DURATION;
        this.timeElapsed = 0;
        Gdx.app.log("InvincibilityDecorator", "Applied invincibility");
    }

    @Override
    public void update(float dt) {
        timeElapsed += dt;
        decoratedBird.update(dt);
    }

    @Override
    public boolean isInvincible() {
        return timeElapsed < duration;
    }

    @Override
    public boolean isExpired() {
        return timeElapsed >= duration;
    }
}
