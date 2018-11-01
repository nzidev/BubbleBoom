package net.drakkcore.loader;

import com.badlogic.gdx.Gdx;

/**
 * Created by ZlatIgor on 25.11.2016.
 */

public class ConstantLoader {

    public static final float SPLASH_TIME = 0.1f;      //logo show and hide time in sec.
    public static final float LOGO_TIME = 0.1f;      //logo time in sec.

    public static final boolean ENABLE_POWERCIRCLE = false; // enable or disable powercircles (baloons for collecting score)

    public static final double HELP_TIME = 1;         //after this time(sec) show help baloons
    public static final double MAX_TIME = 5;          //time in seconds
    public static final int MAX_STEP = 100;

    public static final int CIRCLE_COUNT = 8;         //count of circles. 6 for desktop, 8 for mobile
    public static final int CIRCLE_COLOR = 5;         //number of colors of start circles
    public static final double LEFT_PROCENT = 6.25;   //margin left in procent
    public static final double RIGHT_PROCENT = 6.25;  //margin left in procent
    public static final float DOWN_PROCENT = 8;       //margin bottom in procent
    public static final double PLAY_AREA_PROCENT = 70.5; //area of game in procent
    public static final double PLAY_AREA_HORIZONTAL_PROCENT = 100 - LEFT_PROCENT - RIGHT_PROCENT; //horizontal area in procent
    public static final int BONUS_SCORE = 3;          //add bonus score

    public static float screenWidth = Gdx.graphics.getWidth();
    public static float screenHeight = Gdx.graphics.getHeight();

    public static float circleRadius = ((screenWidth * ((float)PLAY_AREA_HORIZONTAL_PROCENT)/100)/(CIRCLE_COUNT*2));       //40 for mobile, 25 for desktop


}
