package net.drakkcore.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Banner on 08.04.2016.
 */
public class Animations {
    private Array<Sprite> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    public boolean boomed = false;

    public Animations(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<Sprite>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new Sprite(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt) {
        if (boomed) {
            currentFrameTime += dt;
            if (currentFrameTime > maxFrameTime) {
                frame++;
                currentFrameTime = 0;
            }
            if (frame >= frameCount) {
                frame = frameCount - 1;
                boomed = false;
            }
        }
        //Gdx.app.log("Animation", " currentFrameTime " + frame );
    }

    public boolean isBoomed() {
        return boomed;
    }

    public void setBoomed(boolean boomed) {
        this.boomed = boomed;
    }

    public Sprite getFrame(){

        return frames.get(frame);
    }

    public void clearFrame(){
        frame = 0;
    }
}
