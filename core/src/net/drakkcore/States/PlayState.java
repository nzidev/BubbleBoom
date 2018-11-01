package net.drakkcore.States;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import net.drakkcore.Sprites.Baloon;
import net.drakkcore.Sprites.Matrix;
import net.drakkcore.loader.ConstantLoader;

import net.drakkcore.loader.LevelsLoader;
import net.drakkcore.loader.ResourseLoader;

public class PlayState extends State {


    private int steps;
    private double time,timeHelp;
    private int LvlScore = 0;
    private Sprite background, backgroundplay, bgInfo, bgwin;
    private Sprite progressStart;
    private Sprite playbtn,sprScore, sprHighScore,sprStep, sprTime;
    private Array<Baloon> circles;

    private Matrix matrix;
    private MenuState menuState;
    private boolean firstTouch = false;
    int score, winScore;
    public float screenWidth;
    public float screenHeight;
    private boolean startGame = false;


    private String[] levelSetting;
    private String selectLevelSetting;

    Vector3 touchPos;
    private Timer.Task TimerMode;

    public PlayState(GameStateManager gsm, String selectLevelSetting) {
        super(gsm);

        Gdx.app.log("playstate", " gsm.getGameMode() " + selectLevelSetting);


        this.selectLevelSetting = selectLevelSetting;
        levelSetting = selectLevelSetting.split(";");


        // Gdx.app.log("PlayState", "getGameMode " + gsm.getGameMode() );
        if(gsm.getGameMode() == GameStateManager.Mode.STEP || gsm.getGameMode() == GameStateManager.Mode.TIME) {
            // Gdx.app.log("PlayState", " Mode LEVEL" );

            // [0] number of level;[1] count of balls in row and colomn;[2] count of colors;[3]score to win;[4]mode;[5]count of steps/sec;
            // mode: s - step; t - time
            matrix = new Matrix(this, Integer.valueOf(levelSetting[1]), Integer.valueOf(levelSetting[2]));

            winScore = Integer.valueOf(levelSetting[3]);


            steps = Integer.valueOf(levelSetting[5]);
            time = Integer.valueOf(levelSetting[5]);
            timeHelp = ConstantLoader.HELP_TIME;

        }
        else{

            matrix = new Matrix(this, ConstantLoader.CIRCLE_COUNT, ConstantLoader.CIRCLE_COLOR);
            steps = ConstantLoader.MAX_STEP;
        }



        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        camera.setToOrtho(false, screenWidth, screenHeight);
        touchPos = new Vector3();
        playbtn = new Sprite(ResourseLoader.plybtn);
        sprStep = new Sprite(ResourseLoader.StepImg);
        sprTime = new Sprite(ResourseLoader.TimeImg);
        sprHighScore = new Sprite(ResourseLoader.HighScore);
        sprScore = new Sprite(ResourseLoader.Score);
        progressStart = new Sprite(ResourseLoader.progressStart);
        background = new Sprite(ResourseLoader.bg);
        backgroundplay = new Sprite(ResourseLoader.bgplay);
        bgInfo = new Sprite(ResourseLoader.info);
        bgwin = new Sprite(ResourseLoader.win);


        Gdx.input.setCatchBackKey(true);

    }



    @Override
    protected void handleInput() {

        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);

        matrix.ClickCircle(touchPos.x, touchPos.y);
        //|| touchPos.y < matrix.getlastCircles().getStartY())
        if (Gdx.input.justTouched()) {
            firstTouch = true;
            if (Math.pow(touchPos.x - ((screenWidth / 2) - (playbtn.getWidth() / 2)), 2) + Math.pow(touchPos.y - ((screenHeight / 5) * 4), 2) <= Math.pow((playbtn.getWidth() / 2), 2)) {

                if (score > ResourseLoader.getHighScore()) {
                    ResourseLoader.setHighScore(score);
                }
                gsm.set(new PlayState(gsm, selectLevelSetting));
                score = 0;
            }
            if (touchPos.x > (screenWidth / 5)*3 && touchPos.y > (screenHeight /5)*4 )
            {
                gsm.set(new MenuState(gsm));
            }


            if (gsm.getGameMode() == GameStateManager.Mode.WIN && (touchPos.x > screenWidth / 2))
            {
                Gdx.app.log("playstate", " next lvl ");
                dispose();
                if (LevelsLoader.countOfLvl() != LevelsLoader.getSelectLvl())
                    LevelsLoader.setSelectLvl(LevelsLoader.getSelectLvl() +1);


                //gsm.set(new MenuState(gsm));

                selectLevelSetting =  LevelsLoader.levels[LevelsLoader.getSelectLvl() - 1];
                levelSetting = selectLevelSetting.split(";");
                if(levelSetting[4].toCharArray()[0] == 's'){
                    gsm.setGameMode(GameStateManager.Mode.STEP);
                }
                else if(levelSetting[4].toCharArray()[0] == 't'){
                    gsm.setGameMode(GameStateManager.Mode.TIME);
                }
                else {
                    gsm.setGameMode(GameStateManager.Mode.UNLIM);
                }

                gsm.set(new PlayState(gsm, selectLevelSetting));
                //Gdx.app.log("playstate", " new Game  selectLevelSetting" + selectLevelSetting);

            }


        }

        if (!firstTouch && getScore() > 0) {

            gsm.set(new PlayState(gsm, selectLevelSetting));
            score = 0;

            startGame = false;

            //Gdx.app.log("playstate", " new Game ");
        }
        else{
            startGame = true;
        }


    }


    @Override
    public void update(float dt) {

        matrix.update(dt);

        handleInput();
        if(gsm.getGameMode() == GameStateManager.Mode.TIME) {

            Timer.schedule(TimerMode = new Timer.Task() {
                @Override
                public void run() {
                    //Gdx.app.log("playstate", " Start timer " + time);
                    time = time - 0.01;
                    if (time <= 0) {
                        gsm.setGameMode(GameStateManager.Mode.GAMEOVER);
                        time = 0;
                    }
                }
            },1);
        }
    }



    @Override
    public void render(SpriteBatch sb, float runTime) {

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.disableBlending();
        sb.draw(backgroundplay, 0,0, screenWidth, screenHeight);
        sb.enableBlending();
        sb.draw(playbtn, screenWidth - playbtn.getWidth()-5, screenHeight - playbtn.getHeight() - 5);
        matrix.drawCircles(sb, runTime);
        matrix.drawPowerCircles(sb,runTime);
        matrix.drawSoul(sb);
        drawScore(sb);
        drawHighScore(sb);
        drawMode(sb);
        drawProgressStart(sb);

        if (gsm.getGameMode() == GameStateManager.Mode.WIN) {
            rendWIN(sb);
        }

        sb.end();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            //  menuState.setBackButton(true);


    dispose();
            gsm.set(new MenuState(gsm));

        }
    }


    private void drawProgressStart(SpriteBatch sb) {
        if(!startGame) {
            sb.draw(progressStart, 0,0, screenWidth, screenHeight);
        }
    }


    public int getScore() {
        return score;
    }

    public int getSteps() {
        return steps;
    }
    public boolean isGameOver() {
        if (gsm.getGameMode() == GameStateManager.Mode.GAMEOVER)
            return true;
        else
            return false;
    }

    public boolean isWin()
    {
        if (gsm.getGameMode() == GameStateManager.Mode.WIN)
            return true;
        else
            return false;
    }


    public void addScore(int increment) {
        score += increment;
    }

    public void minusStep(){
        steps = steps -1;
    }

    private void drawScore(SpriteBatch sb) {


        //     ResourseLoader.shadow.draw(sb, "" + getScore(), (float)(screenWidth * 0.0625),(float)(screenHeight * 0.84));
        sb.draw(sprScore,(float)(screenWidth * 0.0625), (float)(screenHeight * 0.9 - sprScore.getHeight()));
        ResourseLoader.font.draw(sb, "" + getScore(), (float)(screenWidth * 0.0625 + sprHighScore.getWidth() + screenWidth * 0.05), (float)(screenHeight * 0.9));
    }

    private void drawHighScore(SpriteBatch sb) {

        sb.draw(sprHighScore,(float)(screenWidth * 0.0625), (float)(screenHeight * 0.999 - sprHighScore.getHeight()));
        ResourseLoader.font.draw(sb, "" + ResourseLoader.getHighScore(), (float)(screenWidth * 0.0625 + sprHighScore.getWidth() + screenWidth * 0.05),(float)(screenHeight * 0.999));
    }




    public void timeHelpReset()
    {
        timeHelp = ConstantLoader.HELP_TIME;

    }

    public void rendWIN(SpriteBatch sb){

        //sb.draw(bgInfo, (screenWidth/2) - (bgInfo.getWidth()/2),(screenHeight/2) - (bgInfo.getHeight()/2), bgInfo.getWidth(), bgInfo.getHeight());
        sb.draw(bgwin, 0 ,(screenHeight/2) - (screenWidth/2), screenWidth, screenWidth);
    }
    private void drawMode(SpriteBatch sb) {
        // Gdx.app.log("playstate", " getGameMode " + gsm.getGameMode());

        if(gsm.getGameMode() == GameStateManager.Mode.STEP) {
            if(getSteps() == 0)
                gsm.setGameMode(GameStateManager.Mode.GAMEOVER);

            if(getScore() >= winScore) {
                gsm.setGameMode(GameStateManager.Mode.WIN);


                if(ResourseLoader.getMaxLvl() < LevelsLoader.countOfLvl())
                {
                    ResourseLoader.setMaxLvl(ResourseLoader.getMaxLvl()+1);
                }

                Gdx.app.log("playstate", " getGameMode WIN" );
                Gdx.app.log("playstate", " ResourseLoader.getMaxLvl() " + ResourseLoader.getMaxLvl());
                Gdx.app.log("playstate", " LevelsLoader.countOfLvl() " + LevelsLoader.countOfLvl() );
                //Gdx.app.log("playstate", " getGameMode WIN" );
            }

            sb.draw(sprStep, (float) (screenWidth * 0.0625), (float) (screenHeight * 0.02));
            ResourseLoader.font.draw(sb, "" + getSteps(), (float) (screenWidth * 0.0625 + sprStep.getWidth() + screenWidth * 0.05), (float) (screenHeight * 0.02 + sprStep.getHeight()));
        }

        if(gsm.getGameMode() == GameStateManager.Mode.TIME) {
            if(getScore() >= winScore) {
                gsm.setGameMode(GameStateManager.Mode.WIN);
                if(ResourseLoader.getMaxLvl() < LevelsLoader.countOfLvl())
                {
                    ResourseLoader.setMaxLvl(ResourseLoader.getMaxLvl()+1);
                }

                Gdx.app.log("playstate", " getGameMode WIN" );
                Gdx.app.log("playstate", " ResourseLoader.getMaxLvl() " + ResourseLoader.getMaxLvl());
                Gdx.app.log("playstate", " LevelsLoader.countOfLvl() " + LevelsLoader.countOfLvl() );

            }

            sb.draw(sprTime, (float) (screenWidth * 0.0625), (float) (screenHeight * 0.02));
            ResourseLoader.font.draw(sb, "" + String.format("%,.0f",time), (float) (screenWidth * 0.0625 + sprStep.getWidth() + screenWidth * 0.05), (float) (screenHeight * 0.02 + sprTime.getHeight()));
        }

        if(gsm.getGameMode() == GameStateManager.Mode.GAMEOVER) {
            // ResourseLoader.font.getData().setScale(2f, 2f);
            ResourseLoader.font.draw(sb, "GAME OVER",  (float) (screenWidth * 0.0625 + sprStep.getWidth() + screenWidth * 0.05), (float) (screenHeight * 0.02 + sprTime.getHeight()));
        }

        if(gsm.getGameMode() == GameStateManager.Mode.WIN) {

            ResourseLoader.font.draw(sb, "WIN" , (float) (screenWidth * 0.0625 + sprStep.getWidth() + screenWidth * 0.05), (float) (screenHeight * 0.02 + sprTime.getHeight()));
        }

        Timer.schedule(new Timer.Task() {  // Every timeHelp check can player to move baloon or not, and show it.
            @Override
            public void run() {

                timeHelp = timeHelp - 0.01;
                if (timeHelp <= 0) {
                    matrix.showMaybe();
                }
            }
        }, 2);


    }

    @Override
    public void dispose() {

        Gdx.app.log("playstate", " dispose ");
        matrix.dispose();
        if (firstTouch) {

            if (score > ResourseLoader.getHighScore()) {
                ResourseLoader.setHighScore(score);
            }
        }
    }
}