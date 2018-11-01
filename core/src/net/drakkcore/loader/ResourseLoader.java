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
    public static Sprite logo, bg, circBlue, circBlue2,circGreen,circPurple,circRed, circYellow, plybtn;
    public static Sprite circBlueGrey, circBlue2Grey,circGreenGrey,circPurpleGrey,circRedGrey, circYellowGrey;
    public static Sprite circBlack;
    public static Sprite boom1,boom2,boom3;
    public static Animation boomAnimation;
    public static BitmapFont font, shadow, whiteFont;
    public static Sprite[] colorsArray = new Sprite[7];
    public static Sprite[] colorsGrayArray = new Sprite[7];
    private static Preferences preferences;

    public static void load(){
        atlas = new TextureAtlas(Gdx.files.internal("Texture/texture.pack"), true);

        logo = new Sprite(atlas.findRegion("logo"));
        logo.flip(false,true);

        plybtn = new Sprite(atlas.findRegion("playbtn"));
        plybtn.flip(false,true);
        bg = new Sprite(atlas.findRegion("bg"));
        bg.flip(false,true);
        circBlue = new Sprite(atlas.findRegion("1blue"));
        circBlue2 = new Sprite(atlas.findRegion("1blue2"));
        circGreen = new Sprite(atlas.findRegion("1green"));
        circPurple = new Sprite(atlas.findRegion("1purple"));
        circRed = new Sprite(atlas.findRegion("1red"));
        circYellow = new Sprite(atlas.findRegion("1yellow"));

        circBlueGrey = new Sprite(atlas.findRegion("1blue_grey"));
        circBlue2Grey = new Sprite(atlas.findRegion("1blue2_grey"));
        circGreenGrey = new Sprite(atlas.findRegion("1green_grey"));
        circPurpleGrey = new Sprite(atlas.findRegion("1purple_grey"));
        circRedGrey = new Sprite(atlas.findRegion("1red_grey"));
        circYellowGrey = new Sprite(atlas.findRegion("1yellow_grey"));

        circBlack = new Sprite(atlas.findRegion("black"));

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

// Already have Animation class
        boom1 = new Sprite(atlas.findRegion("boom1"));
        boom2 = new Sprite(atlas.findRegion("boom2"));
        boom3 = new Sprite(atlas.findRegion("boom3"));

        TextureRegion[] boom = {boom1, boom2, boom3};
        boomAnimation = new Animation(0.06f, boom);


                font = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        //font.getData().setScale(.25f, -.25f);
        whiteFont = new BitmapFont(Gdx.files.internal("fonts/whitetext.fnt"));
       // whiteFont.getData().setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("fonts/shadow.fnt"));
       // shadow.getData().setScale(.25f, -.25f);

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

    public static void dispose(){
        atlas.dispose();
    }
}
