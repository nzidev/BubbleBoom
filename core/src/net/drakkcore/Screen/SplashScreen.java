package net.drakkcore.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.drakkcore.BubbleBoom;
import net.drakkcore.Tools.SpriteAccessor;
import net.drakkcore.loader.ConstantLoader;
import net.drakkcore.loader.ResourseLoader;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;





/*
 Show screenslash with logo from resourseLoader

 */

public class SplashScreen implements Screen{

    private TweenManager manager;
    private SpriteBatch batch;
    private Sprite sprite;
    private BubbleBoom game;
    private GameScreen gamescreen;

    public SplashScreen(BubbleBoom game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.app.log("splashscreen", "show splashscreen!!!");
        gamescreen = new GameScreen();
        sprite = new Sprite(ResourseLoader.logo);
        sprite.setColor(1,1,1,0);

        float desiredWidth = ConstantLoader.screenWidth * 0.7f;
        float scale = desiredWidth/sprite.getWidth();
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);

        sprite.setPosition((ConstantLoader.screenWidth/2) - (sprite.getWidth()/2),(ConstantLoader.screenHeight/2) - (sprite.getHeight()/2));

        setupTween();
        batch = new SpriteBatch();

    }

    private void setupTween(){
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        TweenCallback callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game
                        .setScreen(gamescreen);
            }
        };

        Tween.to(sprite,SpriteAccessor.ALPHA, ConstantLoader.SPLASH_TIME/2).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1,ConstantLoader.LOGO_TIME)
                .setCallback(callback).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gamescreen.dispose();
        Gdx.app.log("splashstate", " dispose ");
    }
}
