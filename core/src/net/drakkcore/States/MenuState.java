package net.drakkcore.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import net.drakkcore.loader.ConstantLoader;

import net.drakkcore.loader.LevelsLoader;
import net.drakkcore.loader.ResourseLoader;

/**
 * Created by Banner on 27.03.2016.
 */
public class MenuState extends State{



    //private ArrayList<String> levels = new ArrayList<String>();
    private String[] levels;


    public int currentLvl;
    private Sprite background;

    public String selectLevelSetting;
    private Sprite playbtn, bgInfo, bgSetting, bgTypeGame, exitGame;
    private Sprite soundOn, soundOff;
    public float screenWidth;
    public float screenHeight;
    Vector2 touchPos;




    boolean infoBoolIsSelect = false;
    boolean settingBoolIsSelect = false;
    boolean typeGameBoolIsSelect = false;
    boolean showExitDialog = false;
    boolean sound = false;
    private Rectangle btnSound;

    public boolean backReleased  = false;
    float btnSoundX;
    float btnSoundY;

    private String[] levelSetting;

    public MenuState(GameStateManager gsm) {

        super(gsm);
        touchPos = new Vector2();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        background = new Sprite(ResourseLoader.bg);
        bgInfo = new Sprite(ResourseLoader.info);
        bgTypeGame = new Sprite(ResourseLoader.gameType);
        bgSetting = new Sprite(ResourseLoader.settings);


        exitGame = new Sprite(ResourseLoader.exit);

        playbtn =  new Sprite(ResourseLoader.plybtn);


        soundOn = new Sprite(ResourseLoader.soundOn);
        soundOff = new Sprite(ResourseLoader.soundOff);

        btnSoundX = (screenWidth/2) + (soundOff.getWidth()/2);
        btnSoundY = (screenHeight/2) - (soundOff.getHeight()/2);

        btnSound = new Rectangle(btnSoundX, btnSoundY, soundOff.getWidth(), soundOff.getHeight());

        Gdx.input.setCatchBackKey(true);
        backReleased = false;


        LevelsLoader.LoadingLevels();

    }


    public int getCurrentLvl() {
        return currentLvl;
    }

    public void setCurrentLvl(int currentLvl) {
        this.currentLvl = currentLvl;
    }



    @Override
    protected void handleInput() {
        touchPos.set(Gdx.input.getX(), Gdx.input.getY());

        if (Gdx.input.justTouched()){

            if(infoBoolIsSelect)
                infoBoolIsSelect = false;
            else if(typeGameBoolIsSelect) {

                if (touchPos.x < screenWidth / 2 && (touchPos.y > (screenHeight / 2 - bgTypeGame.getHeight() / 2) && touchPos.y < (screenHeight / 2 + bgTypeGame.getHeight() / 2)) ) {
                    //select UNLIM
                    Gdx.app.log("menustate", " select UNLIM ");
                    gsm.setGameMode(GameStateManager.Mode.UNLIM);
                    gsm.set(new PlayState(gsm, selectLevelSetting));
                }
                /*
                if (touchPos.x > screenWidth / 2 && (touchPos.y > (screenHeight / 2 - bgTypeGame.getHeight() / 2) && touchPos.y < (screenHeight / 2 + bgTypeGame.getHeight() / 2)) ) {
                    //select Step
                    Gdx.app.log("menustate", " select Step ");
                    gsm.setGameMode(GameStateManager.Mode.STEP);
                    gsm.set(new PlayState(gsm));
                }*/
                if (touchPos.x > screenWidth / 2 && (touchPos.y > (screenHeight / 2 - bgTypeGame.getHeight() / 2) && touchPos.y < (screenHeight / 2 + bgTypeGame.getHeight() / 2)) )
                {
                   gsm.set(new LvlState(gsm));


                    //
                    //if(levelSetting[4] == "t")
                    //    gsm.setGameMode(GameStateManager.Mode.TIME);

                    //Gdx.app.log("menustate", " select Levels " + levelSetting[4]);






                }
                else
                    typeGameBoolIsSelect = false;
            }
            else if(settingBoolIsSelect)
                if (touchPos.y > btnSound.getY()  && touchPos.y < (btnSound.getY()+btnSound.getHeight())  )
                {
                    if(sound)
                    {
                        ResourseLoader.setMaxLvl(1);
                        sound = false;
                    }

                    else
                        sound = true;
                }
                else
                    settingBoolIsSelect = false;
            else if (showExitDialog)
            {
                if ((touchPos.y > (screenHeight / 2 - exitGame.getHeight() / 2) && touchPos.y < (screenHeight / 2 + exitGame.getHeight() / 2)) )
                    Gdx.app.exit();
                else
                    showExitDialog = false;
            }
            else { // which menu select
                if (touchPos.x > screenWidth / 2 && touchPos.y > (screenHeight / 2.5)*1 && !typeGameBoolIsSelect)
                    //select Info
                    infoBoolIsSelect = true;

                else if (touchPos.x < screenWidth / 2 && touchPos.y > (screenHeight / 2.5)*1 && !typeGameBoolIsSelect)
                    //select Settings
                    settingBoolIsSelect = true;

                else {
                    //select Play
                    typeGameBoolIsSelect = true;
                    //gsm.set(new PlayState(gsm));
                }
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
        sb.draw(background, 0,0, screenWidth, screenHeight);
        // sb.draw(playbtn,(screenWidth /2) - (playbtn.getWidth()/2),(screenHeight/3)*2);
        if(infoBoolIsSelect)
            rendInfo(sb);

        if(settingBoolIsSelect)
            rendSetting(sb);

        if(typeGameBoolIsSelect)
            rendTypeGame(sb);
        if(showExitDialog)
            rendExitGame(sb);

        if (backReleased && Gdx.input.isKeyPressed(Input.Keys.BACK ) ){

            backReleased = false;
            Gdx.app.log("menustate", " isKeyPressed(Input.Keys.BACK) ");
            showExitDialog = true;


            // Gdx.app.exit();
        }
        else if (!backReleased && Gdx.input.isKeyPressed(Input.Keys.BACK ) )
        {
            for(int i=0;i<10;i++)
            {
                // i need to busy processor some time
            }
            backReleased = true;

        }


        sb.end();


    }



    public void rendTypeGame(SpriteBatch sb){
        sb.draw(bgTypeGame, 0 ,(screenHeight/2) - (screenWidth/2), screenWidth, screenWidth);
    }

    public void rendExitGame(SpriteBatch sb){
        sb.draw(exitGame, 0, (screenHeight/2)-(screenWidth/2), screenWidth, screenWidth);

    }



    public void rendInfo(SpriteBatch sb){

        //sb.draw(bgInfo, (screenWidth/2) - (bgInfo.getWidth()/2),(screenHeight/2) - (bgInfo.getHeight()/2), bgInfo.getWidth(), bgInfo.getHeight());
        sb.draw(bgInfo, 0 ,(screenHeight/2) - (screenWidth/2), screenWidth, screenWidth);
    }

    public void rendSetting(SpriteBatch sb){
        sb.draw(bgSetting, 0 ,(screenHeight/2) - (screenWidth/2), screenWidth, screenWidth);
        if(sound)
            sb.draw(soundOn, btnSoundX, btnSoundY, soundOn.getWidth(), soundOn.getHeight());
        else
            sb.draw(soundOff,btnSoundX,btnSoundY, soundOff.getWidth(), soundOff.getHeight());



    }

    @Override
    public void dispose() {
        Gdx.app.log("menustate", " dispose ");

    }




}
