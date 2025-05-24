package sprites;

import com.badlogic.gdx.Gdx;

public class SpeedBoostDecorator extends BirdDecorator {
    private float duration;
    private float timeElapsed;
    private static final float SPEED_MULTIPLIER = 2f;
    private static final float DURATION = 5f;
    private static final float BASE_MOVEMENT = 100; // Hardcoded to match Bird's MOVEMENT

    public SpeedBoostDecorator(Bird decoratedBird) {
        super(decoratedBird);
        this.duration = DURATION;
        this.timeElapsed = 0;
        Gdx.app.log("SpeedBoostDecorator", "Applied speed boost");
    }

    @Override
    public void update(float dt) {
        timeElapsed += dt;
        decoratedBird.update(dt);
        if (timeElapsed < duration) {
            // Apply extra speed using the new method
            decoratedBird.applyHorizontalSpeed(BASE_MOVEMENT * (SPEED_MULTIPLIER - 1), dt);
        }
    }

    @Override
    public boolean isInvincible() {
        return decoratedBird.isInvincible();
    }

    @Override
    public boolean isExpired() {
        return timeElapsed >= duration;
    }
}
