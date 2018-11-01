package net.drakkcore.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import net.drakkcore.loader.ConstantLoader;
import net.drakkcore.loader.LevelsLoader;
import net.drakkcore.loader.ResourseLoader;

/**
 * Created by Banner on 09.07.2017.
 */

public class LvlState extends State {

    private Sprite bgLvl;
    private float screenWidth, screenHeight;
    private String[] levelSetting;
    private String selectLevelSetting;
    Vector2 touchPos;

    public LvlState(GameStateManager gsm) {
        super(gsm);
        touchPos = new Vector2();
        bgLvl = new Sprite(ResourseLoader.bgLvl);
        screenHeight = ConstantLoader.screenHeight;
        screenWidth = ConstantLoader.screenWidth;

        selectLevelSetting =  LevelsLoader.levels[LevelsLoader.getSelectLvl() - 1];
        levelSetting = selectLevelSetting.split(";");
        String aa = selectLevelSetting.split(";")[0];
    }

    @Override
    protected void handleInput() {

        touchPos.set(Gdx.input.getX(), Gdx.input.getY());

        if (Gdx.input.justTouched()) {


            if ((touchPos.x > screenWidth * 0.1) && (touchPos.x < screenWidth * 0.3) && (touchPos.y < screenHeight * 0.9) && (touchPos.y > screenHeight * 0.7) &&  (LevelsLoader.levels[0].split(";")[6].toString().equals("open"))) {
                LevelsLoader.setSelectLvl(1);
                LevelsLoader.setLevelMode(gsm,LevelsLoader.levels[0]);
                gsm.set(new PlayState(gsm, LevelsLoader.levels[0]));
            }
            if ((touchPos.x > screenWidth * 0.42) && (touchPos.x < screenWidth * 0.7) && (touchPos.y < screenHeight * 0.7) && (touchPos.y > screenHeight * 0.5) &&  (LevelsLoader.levels[1].split(";")[6].toString().equals("open")))
            {
                LevelsLoader.setSelectLvl(2);
                LevelsLoader.setLevelMode(gsm,LevelsLoader.levels[1]);
                gsm.set(new PlayState(gsm, LevelsLoader.levels[1]));
            }
            if ((touchPos.x > screenWidth * 0.1) && (touchPos.x < screenWidth * 0.3) && (touchPos.y < screenHeight * 0.5) && (touchPos.y > screenHeight * 0.3)&&  (LevelsLoader.levels[2].split(";")[6].toString().equals("open")))
            {
               LevelsLoader.setSelectLvl(3);
                LevelsLoader.setLevelMode(gsm,LevelsLoader.levels[2]);
                gsm.set(new PlayState(gsm, LevelsLoader.levels[2]));
            }
            if ((touchPos.x > screenWidth * 0.42) && (touchPos.x < screenWidth * 0.7) && (touchPos.y < screenHeight * 0.3) && (touchPos.y > screenHeight * 0.15) &&  (LevelsLoader.levels[3].split(";")[6].toString().equals("open")))
            {
                LevelsLoader.setSelectLvl(4);
                LevelsLoader.setLevelMode(gsm,LevelsLoader.levels[3]);
                gsm.set(new PlayState(gsm, LevelsLoader.levels[3]));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb, float runTime) {
        sb.begin();

        sb.draw(bgLvl,0,0, screenWidth, screenHeight);

        sb.end();


        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            dispose();
            gsm.set(new MenuState(gsm));
        }
    }

    @Override
    public void dispose() {

    }
}
