package net.drakkcore.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Banner on 14.04.2016.
 */
public class ResourseLoader {
    private static TextureAtlas atlas;
    public static Sprite logo, bg, bgplay, info,gameType,exit,win, settings, circBlue, circBlue2,circGreen,circPurple,circRed, circYellow, plybtn, progressStart,bgLvl;
    public static Sprite circBlueGrey, circBlue2Grey,circGreenGrey,circPurpleGrey,circRedGrey, circYellowGrey;
    public static Sprite circBlueStar, circBlue2Star,circGreenStar,circPurpleStar,circRedStar, circYellowStar;
    public static Sprite circBlack;
    public static Sprite soundOn, soundOff, Score, HighScore, StepImg, TimeImg;
    public static Sprite boom1,boom2,boom3;
    public static Sprite soulOfBaloon;
    public static Sprite flash1,flash2,flash3;
    public static Animation boomAnimation, flashBaloonAnimation;
    public static Animation[] flashArray = new Animation[7];
    public static BitmapFont font, shadow, whiteFont;
    public static Sprite[] colorsArray = new Sprite[7];
    public static Sprite[] colorsGrayArray = new Sprite[7];
    public static Sprite[] colorsStarArray = new Sprite[7];
    private static Preferences preferences;

    public static void load(){
        atlas = new TextureAtlas(Gdx.files.internal("Texture/Texture.pack"), true);

        logo = new Sprite(atlas.findRegion("Info"));
        logo.flip(false,true);

        settings = new Sprite(atlas.findRegion("Settings"));
        settings.flip(false,true);

        info = new Sprite(atlas.findRegion("Info"));
        info.flip(false,true);

        gameType =  new Sprite(atlas.findRegion("typeGame"));
        gameType.flip(false,true);

        exit =  new Sprite(atlas.findRegion("exit"));
        exit.flip(false,true);

        win =  new Sprite(atlas.findRegion("win"));
        win.flip(false,true);

        progressStart = new Sprite(atlas.findRegion("progressStart"));
        progressStart.flip(false,true);

        soundOn = new Sprite(atlas.findRegion("OnSound"));
        soundOn.flip(false,true);

        soundOff = new Sprite(atlas.findRegion("OffSound"));
        soundOff.flip(false,true);

        HighScore = new Sprite(atlas.findRegion("HighScore"));
        HighScore.flip(false,true);

        Score = new Sprite(atlas.findRegion("Score"));
        Score.flip(false,true);

        TimeImg = new Sprite(atlas.findRegion("Time"));
        TimeImg.flip(false,true);

        StepImg = new Sprite(atlas.findRegion("Time"));        //////////////// CHANGE image
        StepImg.flip(false,true);

        plybtn = new Sprite(atlas.findRegion("OnSound"));
        plybtn.flip(false,true);
        bg = new Sprite(atlas.findRegion("BackgroundMain"));
        bg.flip(false,true);
        bgplay = new Sprite(atlas.findRegion("BackgroundGame"));
        bgplay.flip(false,true);

        bgLvl = new Sprite(atlas.findRegion("LVL"));
        bgLvl.flip(false,true);


        circBlue = new Sprite(atlas.findRegion("BaloonBlue"));
        circBlue.flip(false,true);
        circBlue2 = new Sprite(atlas.findRegion("BaloonLightBlue"));
        circBlue2.flip(false,true);
        circGreen = new Sprite(atlas.findRegion("BaloonGreen"));
        circGreen.flip(false,true);
        circPurple = new Sprite(atlas.findRegion("BaloonPurple"));
        circPurple.flip(false,true);
        circRed = new Sprite(atlas.findRegion("BaloonRed"));
        circRed.flip(false,true);
        circYellow = new Sprite(atlas.findRegion("BaloonOrange"));
        circYellow.flip(false,true);

        circBlueGrey = new Sprite(atlas.findRegion("BaloonBlueDark"));
        circBlueGrey.flip(false,true);
        circBlue2Grey = new Sprite(atlas.findRegion("BaloonLightBlueDark"));
        circBlue2Grey.flip(false,true);
        circGreenGrey = new Sprite(atlas.findRegion("BaloonGreenDark"));
        circGreenGrey.flip(false,true);
        circPurpleGrey = new Sprite(atlas.findRegion("BaloonPurpleDark"));
        circPurpleGrey.flip(false,true);
        circRedGrey = new Sprite(atlas.findRegion("BaloonRedDark"));
        circRedGrey.flip(false,true);
        circYellowGrey = new Sprite(atlas.findRegion("BaloonOrangeDark"));
        circYellowGrey.flip(false,true);

        circBlueStar = new Sprite(atlas.findRegion("BaloonBlueStar"));
        circBlueStar.flip(false,true);
        circBlue2Star = new Sprite(atlas.findRegion("BaloonLightBlueStar"));
        circBlue2Star.flip(false,true);
        circGreenStar = new Sprite(atlas.findRegion("BaloonGreenStar"));
        circGreenStar.flip(false,true);
        circPurpleStar = new Sprite(atlas.findRegion("BaloonPurpleStar"));
        circPurpleStar.flip(false,true);
        circRedStar = new Sprite(atlas.findRegion("BaloonRedStar"));
        circRedStar.flip(false,true);
        circYellowStar = new Sprite(atlas.findRegion("BaloonOrangeStar"));
        circYellowStar.flip(false,true);


        circBlack = new Sprite(atlas.findRegion("BaloonBlack"));
        circBlack.flip(false,true);




        colorsArray[0] = new Sprite(ResourseLoader.circBlack);
        colorsArray[1] = new Sprite(ResourseLoader.circBlue);
        colorsArray[2] = new Sprite(ResourseLoader.circBlue2);
        colorsArray[3] = new Sprite(ResourseLoader.circGreen);
        colorsArray[4] = new Sprite(ResourseLoader.circPurple);
        colorsArray[5] = new Sprite(ResourseLoader.circRed);
        colorsArray[6] = new Sprite(ResourseLoader.circYellow);

        colorsGrayArray[0] = new Sprite(ResourseLoader.circBlack);
        colorsGrayArray[1] = new Sprite(ResourseLoader.circBlueGrey);
        colorsGrayArray[2] = new Sprite(ResourseLoader.circBlue2Grey);
        colorsGrayArray[3] = new Sprite(ResourseLoader.circGreenGrey);
        colorsGrayArray[4] = new Sprite(ResourseLoader.circPurpleGrey);
        colorsGrayArray[5] = new Sprite(ResourseLoader.circRedGrey);
        colorsGrayArray[6] = new Sprite(ResourseLoader.circYellowGrey);

        colorsStarArray[0] = new Sprite(ResourseLoader.circBlack);
        colorsStarArray[1] = new Sprite(ResourseLoader.circBlueStar);
        colorsStarArray[2] = new Sprite(ResourseLoader.circBlue2Star);
        colorsStarArray[3] = new Sprite(ResourseLoader.circGreenStar);
        colorsStarArray[4] = new Sprite(ResourseLoader.circPurpleStar);
        colorsStarArray[5] = new Sprite(ResourseLoader.circRedStar);
        colorsStarArray[6] = new Sprite(ResourseLoader.circYellowStar);


      //  flash1 = new Sprite(atlas.findRegion("BaloonBlueDark1"));
        flash1 = new Sprite(atlas.findRegion("BaloonBlueDark1"));
        flash1.flip(false,true);
        flash2 = new Sprite(atlas.findRegion("BaloonBlueDark2"));
        flash2.flip(false,true);
        flash3 = new Sprite(atlas.findRegion("BaloonBlueDark3"));
        flash3.flip(false,true);

        soulOfBaloon = new Sprite(atlas.findRegion("boom2"));
        soulOfBaloon.flip(false,true);

        TextureRegion[] flash = {flash1, flash2, flash3};
        flashBaloonAnimation = new Animation(0.06f, flash);
        flashBaloonAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        float dta = 0.06f;
        flashArray[0] = new Animation(0.01f, flash);
        flashArray[1] = new Animation(0.06f, flash);
        flashArray[2] = new Animation(0.1f, flash);
        flashArray[3] = new Animation(0.3f, flash);
        flashArray[4] = new Animation(0.5f, flash);
        flashArray[5] = new Animation(0.7f, flash);
        flashArray[6] = new Animation(1f, flash);

        flashArray[0].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        flashArray[1].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        flashArray[2].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        flashArray[3].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        flashArray[4].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        flashArray[5].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        flashArray[6].setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


// Already have Animation class
//        boom1 = new Sprite(atlas.findRegion("boom1"));
//        boom2 = new Sprite(atlas.findRegion("boom2"));
//        boom3 = new Sprite(atlas.findRegion("boom3"));
//
//        TextureRegion[] boom = {boom3, boom2, boom1};
//        boomAnimation = new Animation(3f, boom);
//        boomAnimation.setPlayMode(Animation.PlayMode.LOOP );

        font = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        font.getData().setScale(0.5f, 0.6f);
        //font.getData().setScale(.7f, .7f);
        whiteFont = new BitmapFont(Gdx.files.internal("fonts/whitetext.fnt"));
        // whiteFont.getData().setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("fonts/shadow.fnt"));
        shadow.getData().setScale(.7f, .7f);

        preferences = Gdx.app.getPreferences("BubbleBoom");

        if(!preferences.contains("highScore")){
            preferences.putInteger("highScore",0);
        }
    }

    public static void setHighScore(int val){
        preferences.putInteger("highScore", val);
        preferences.flush();
    }

    public static int getHighScore(){
        return preferences.getInteger("highScore");
    }

    public static void setMaxLvl(int val) {

        preferences.putInteger("maxLvl", val);
        preferences.flush();

    }

    public static int getMaxLvl() {
        if (preferences.getInteger("maxLvl") < 1) {
       //     Gdx.app.log("ResourseLoader", " maxLvl little " + preferences.getInteger("maxLvl"));
            setMaxLvl(1);
        }
        return preferences.getInteger("maxLvl");
    }


    public static void dispose(){
        atlas.dispose();
    }
}
