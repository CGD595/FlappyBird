package sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
    private TextureRegion[] frames; // Array to store individual frames of the animation
    private float maxFrameTime; // Maximum time for each frame to be displayed
    private float currentFrameTime; // Time elapsed since the current frame started
    private int frameCount; // Total number of frames in the animation
    private int frame; // Index of the current frame

    // Constructor to create an animation with a given texture region, frame count, and cycle time
    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new TextureRegion[frameCount]; // Initialize the array to hold the frames
        int frameWidth = region.getRegionWidth() / frameCount; // Calculate the width of each frame

        // Create individual frames by specifying the region for each frame
        for (int i = 0; i < frameCount; i++) {
            frames[i] = new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight());
        }

        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount; // Calculate the maximum time for each frame
        frame = 0; // Start with the first frame
    }

    // Update method to progress the animation based on the elapsed time
    public void update(float dt) {
        currentFrameTime += dt; // Add the elapsed time to the current frame time

        // If the current frame time exceeds the maximum frame time, move to the next frame
        if (currentFrameTime > maxFrameTime) {
            frame++; // Move to the next frame
            currentFrameTime = 0; // Reset the current frame time
        }

        // If the last frame is reached, reset to the first frame
        if (frame >= frameCount)
            frame = 0;
    }

    // Getter method to retrieve the current frame of the animation
    public TextureRegion getFrame() {
        return frames[frame];
    }

    // Dispose method to free up resources
    public void dispose() {
        // Iterate through each TextureRegion in the frames array and dispose of its underlying texture
        for (TextureRegion frame : frames) {
            frame.getTexture().dispose();
        }
    }
}