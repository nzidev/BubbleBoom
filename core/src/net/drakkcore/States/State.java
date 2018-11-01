package net.drakkcore.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Banner on 27.03.2016.
 */
public abstract class State {
    protected OrthographicCamera camera;
    protected Vector2 mouse;
    protected GameStateManager gsm;

    public State(GameStateManager gsm){
        this.gsm = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector2();
    }

    protected abstract void handleInput();
    public abstract void update (float dt);
    public abstract void render(SpriteBatch sb, float runTime);
    public abstract void dispose();


}
