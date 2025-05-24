package sprites;

import com.badlogic.gdx.graphics.Texture;

public class TubeFactory {
    private static Texture topTubeTexture;
    private static Texture bottomTubeTexture;

    public TubeFactory() {
        if (topTubeTexture == null) {
            topTubeTexture = new Texture("toptube.png");
        }
        if (bottomTubeTexture == null) {
            bottomTubeTexture = new Texture("bottomtube.png");
        }
    }

    public Tube createTube(float x) {
        return new Tube(x, topTubeTexture, bottomTubeTexture);
    }

    public void dispose() {
        // Do not dispose textures here; managed by FlappyDemo
    }
}
