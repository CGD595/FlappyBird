package sprites;

import com.badlogic.gdx.Gdx;

public class ScoreMultiplierDecorator extends BirdDecorator {
    private float duration;
    private float timeElapsed;
    private static final float DURATION = 5f;

    public ScoreMultiplierDecorator(Bird decoratedBird) {
        super(decoratedBird);
        this.duration = DURATION;
        this.timeElapsed = 0;
        Gdx.app.log("ScoreMultiplierDecorator", "Applied score multiplier");
    }

    @Override
    public void update(float dt) {
        timeElapsed += dt;
        decoratedBird.update(dt);
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
