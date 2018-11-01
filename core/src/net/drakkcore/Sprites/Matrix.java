package net.drakkcore.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;

import net.drakkcore.States.PlayState;
import net.drakkcore.loader.ConstantLoader;
import net.drakkcore.loader.ResourseLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static net.drakkcore.loader.ConstantLoader.DOWN_PROCENT;
import static net.drakkcore.loader.ConstantLoader.circleRadius;

public class Matrix {
    private PlayState playState;
    private Array<Baloon> circles;
    public static Array<Baloon> powerCircles;
    private PowerBoomAnimation pweboomanimation;

    private float evX, evY, dragX, dragY;

    private float screenWidth;
    private float screenHeight;

    private boolean drag = false;      // Player start to drag on baloon
    private boolean changed = false;   // If two baloons was changed then = true
    private boolean startFall = false; // Baloons start to explosion and fall down
    private boolean tried = false;     // if try to check all baloons then = true
    private boolean exposionStep = false; //after this step will be explosion, if false return baloon to start posotion

    private int numberToHelp = 0;            //what number of array maybeArray will be show and jump
    private int dragCircle, numDragCircle;
    private float x, y, bufx, bufy;
    private Random random;
    private Circle playerCircle;

    private int count;

    private int bufColor;


    private ArrayList<Integer> newCirclesArray;
    private ArrayList<String> maybeArray;
    private Boom boom;
    private int colors, color;

   // private Animation boomAnimation;
    private boolean startJump = false;

    public Matrix(PlayState playState, int count, int colors) {

        this.playState = playState;
        this.count = count;
        this.colors = colors;
        screenWidth =  ConstantLoader.screenWidth;

        screenHeight = ConstantLoader.screenHeight;
        boom = new Boom(playState);
        maybeArray = new ArrayList<String>();
        //maybeArray[2][3][4].add(4);
        random = new Random();

      //  boomAnimation = ResourseLoader.boomAnimation;

        playerCircle = new Circle(0, 0, circleRadius / 2);

        createGameArea();
    }

    public void createGameArea() // Create new baloons on play area.
    {

        circles = new Array<Baloon>();
        powerCircles = new Array<Baloon>(); //baloons for collecting score

        float miny, stepY;
        float minx, stepX;


        stepX = (float) (circleRadius*2);
        stepY = (float) (circleRadius * 2.5);

        //find first circle to start printing on workplace
        if ((count & 1) == 0){ // count even
            minx = (float) (screenWidth/2) - ((count/2) * stepX)  + circleRadius;
            miny = (float) (((screenHeight * (ConstantLoader.PLAY_AREA_PROCENT / 100))/2) - ((count/2) * stepY)) + circleRadius + (screenHeight * (ConstantLoader.DOWN_PROCENT / 100)) ;
        }
        else {                  //count odd
            minx = (float) (screenWidth / 2) - ((count / 2) * stepX);
            miny = (float) (((screenHeight * (ConstantLoader.PLAY_AREA_PROCENT / 100)) / 2) - ((count / 2) * stepY)) + (screenHeight * (ConstantLoader.DOWN_PROCENT / 100));
        }

        //position of baloons
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                x = minx + i * stepX;
                y = miny + j * stepY;


                color = random.nextInt(colors) + 1;
                this.circles.add(new Baloon(color, x, y));
            }
        }

        //position of baloons for collecting score
        for (int i=1;i<=colors;i++)
        {

            x = minx + i * stepX;
            y = miny + count * stepY;
            color = i;
            this.powerCircles.add(new Baloon(color,x,y));

        }

    }

    public void ClickCircle(float x, float y) {  //if player click on baloon

        evX = x;
        evY = y;
        changed = false;

        //*............Click baloon.............*//*
        if (Gdx.input.justTouched() && !startFall && !boom.isBoomed() && !playState.isGameOver() && !playState.isWin()) {
            startJump = false;
            playState.timeHelpReset(); //reset time to start jump baloons
            boom.clearBoomArray();
            if(maybeArray.size() > 0) {
                stopJump(numberToHelp);
            }
            //   Gdx.app.log("Matrix", " playState.isGameOver " + playState.isGameOver());
            int j = 0;
            for (int i = 0; i < circles.size; i++) {
                j++;

                if (Math.pow(evX - (circles.get(i).getPosition().x + circleRadius), 2) + Math.pow(evY - (circles.get(i).getPosition().y + circleRadius), 2) <= Math.pow(circleRadius, 2)) {
                    drag = true;
                    dragCircle = i;
                    dragX = evX - circles.get(i).getPosition().x;
                    dragY = evY - circles.get(i).getPosition().y;
                }
            }
        }
        //*............Move Clicked baloon.............*/
        if (Gdx.input.isTouched() && drag) {
            startJump = false;
            circles.get(dragCircle).circleMove(evX - dragX, evY - dragY, circles.size);
            playerCircle.setPosition(circles.get(dragCircle).getPosition().x, circles.get(dragCircle).getPosition().y);
        }
        //*...........Drop off.........*/
        if (!(Gdx.input.isTouched()) && drag) {


            for (int i = 0; i < circles.size; i++) {
                if (i != dragCircle && circles.get(i).collides(playerCircle)) {
                    //* .........Change only texture (color)................ *//
                    if (circles.get(dragCircle).getStates() == Baloon.BaloonState.BLACK) //if change black, then all chosen color of baloon are explosion
                    {
                        playState.addScore(ConstantLoader.BONUS_SCORE);
                        boom.addBoomArray(dragCircle);
                        bufColor = circles.get(i).getColor();
                        boomColor(bufColor);
                        changed = true;
                    } else {
                        circles.get(dragCircle).setPosition(circles.get(dragCircle).getStartX(), circles.get(dragCircle).getStartY());
                        playerCircle.setPosition(circles.get(dragCircle).getStartX(), circles.get(dragCircle).getStartY());
                        circles.get(i).changeBaloons(circles.get(dragCircle));
                        numDragCircle = i;

                        changed = true;

                        // Gdx.app.log("Matrix", " boom " + boom.isBoomed());
                    }


                }
            }

            drag = false;

            //*.......... Return baloon to start position.............*//
            if (!changed) {
                circles.get(dragCircle).setPosition(circles.get(dragCircle).getStartX(), circles.get(dragCircle).getStartY());
                playerCircle.setPosition(circles.get(dragCircle).getStartX(), circles.get(dragCircle).getStartY());

            }
        }

    }

    private void boomColor(int bufColor) {
        for (int i = 0; i < count * count; i++) {
            if (circles.get(i).getColor() == bufColor) {
                boom.addBoomArray(i);
            }
        }
        boom.startBoom(circles);
    }

    //*..........Check equal color............*/
    public void checkEqual() {
        exposionStep = false;
        maybeArray.clear();
        if (!startFall) {
            for (int i = 0; i < count * count; i++) {
                if (circles.get(i).getStates() == Baloon.BaloonState.GREY) {
                    // Gdx.app.log("Matrix", " GREY " + i);
                }
                if (circles.get(i).getStates() == Baloon.BaloonState.BLACK) {
                    // Gdx.app.log("Matrix", " BLACK " + i);
                }
                if (circles.get(i).getStates() == Baloon.BaloonState.CROSS) {
                    // Gdx.app.log("Matrix", " CROSS " + i);
                }
            }
            int eqal = 1;
            int nextrowbaloon;
            for (int col = 0; col < count; col++) {
                //   Gdx.app.log("Matrix", " check col " + col);
                for (int i = 0 + count * col; i < count - 1 + count * col; i++) {   //col
                    //      Gdx.app.log("Matrix", " check i " + i);

                    if ((i + 1) == (count - 1 + count * col) && (circles.get(i).getColor() == circles.get(i + 1).getColor())) {
                        eqal++;
                        if(eqal>2) {
                            boom.setBoom(eqal, i + 1, count, false, circles, numDragCircle, dragCircle);
                        }
                        eqal = 1;

                    }
                    if (circles.get(i).getColor() == circles.get(i + 1).getColor()) {
                        eqal++;
                    } else if(eqal>2) {
                        boom.setBoom(eqal, i, count, false, circles, numDragCircle, dragCircle);
                        eqal = 1;
                    }
                    if (eqal >= 3) {
                        exposionStep = true;
                    }
                    checkColMaybe(i, col, count);
                    //Gdx.app.log("Matrix", " check col " + i);
                }

                eqal = 1;
                for (int j = 0 + col; j < count * count - count + col; j = j + count) {   //row
                    //  Gdx.app.log("Matrix", " check j " + j);
                    nextrowbaloon = j + count;
                    if ((j == ((count * (count - 1) + col)) - count) && (circles.get(j).getColor() == circles.get(nextrowbaloon).getColor())) { //last slot
                        eqal++;
                        boom.setBoom(eqal, nextrowbaloon, count, true, circles, numDragCircle, dragCircle);
                        eqal = 1;
                    } else if (circles.get(j).getColor() == circles.get(nextrowbaloon).getColor()) {
                        eqal++;
                    } else {
                        boom.setBoom(eqal, j, count, true, circles, numDragCircle, dragCircle);
                        eqal = 1;
                    }
                    if (eqal >= 3) {
                        exposionStep = true;
                    }
                    checkRowMaybe(j, col, count);
                }
            }
            if (!exposionStep && changed) {
                circles.get(dragCircle).setPosition(circles.get(dragCircle).getStartX(), circles.get(dragCircle).getStartY());
                playerCircle.setPosition(circles.get(dragCircle).getStartX(), circles.get(dragCircle).getStartY());
                circles.get(numDragCircle).changeBaloons(circles.get(dragCircle));

                Gdx.app.log("Matrix", " exposionStep  " + exposionStep);
            } else if (exposionStep && changed) {
                playState.minusStep();
            }
            tried = true;


        }
    }


    private void checkRowMaybe(int j, int row, int count) {
        if (((j + (count * 3)) <= ((count * count + row)) - count) //.. .
                && (circles.get(j).getColor() == circles.get(j + count).getColor()) && (circles.get(j).getColor() == circles.get(j + (count * 3)).getColor())) {
            // Gdx.app.log("Matrix", " checkRowMaybe  " + j);
            // Gdx.app.log("Matrix", " row  " + row);
            maybeArray.add(j + "," + (j + count) + "," + (j + (count * 3)));


        }
        if (((j + (count * 3)) <= ((count * count + row)) - count) //. ..
                && (circles.get(j).getColor() == circles.get(j + (count * 2)).getColor()) && (circles.get(j).getColor() == circles.get(j + (count * 3)).getColor())) {
            maybeArray.add(j + "," + (j + (count * 2)) + "," + (j + (count * 3)));
            //Gdx.app.log("Matrix", " checkRowMaybe  " + j);
            //Gdx.app.log("Matrix", " row  " + row);
        }

        if ((row < count - 1) && (j < (count * (count - 2)) //..'
                && (circles.get(j).getColor() == circles.get(j + count).getColor()) && (circles.get(j).getColor() == circles.get(j + (count * 2) + 1).getColor()))) {
            maybeArray.add(j + "," + (j + count) + "," + (j + (count * 2) + 1));
        }
        if ((row > 0) && (j < (count * (count - 2)) //..,
                && (circles.get(j).getColor() == circles.get(j + count).getColor()) && (circles.get(j).getColor() == circles.get(j + (count * 2) - 1).getColor()))) {
            maybeArray.add(j + "," + (j + count) + "," + (j + (count * 2) - 1));
        }
        if ((row < count - 1) && (j < (count * (count - 2))  //.'.
                && (circles.get(j).getColor() == circles.get(j + (count * 2)).getColor()) && (circles.get(j).getColor() == circles.get(j + count + 1).getColor()))) {
            maybeArray.add(j + "," + (j + (count * 2)) + "," + (j + count + 1));
        }
        if ((row > 0) && (j < (count * (count - 2))  //.,.
                && (circles.get(j).getColor() == circles.get(j + (count * 2)).getColor()) && (circles.get(j).getColor() == circles.get(j + count - 1).getColor()))) {
            maybeArray.add(j + "," + (j + (count * 2)) + "," + (j + count - 1));
        }

        if ((row > 0) && (j < (count * (count - 2))  //'..
                && (circles.get(j).getColor() == circles.get(j + (count * 2) - 1).getColor()) && (circles.get(j).getColor() == circles.get(j + count - 1).getColor()))) {
            maybeArray.add(j + "," + (j + count - 1) + "," + (j + (count * 2) - 1));
        }
        if ((row < count - 1) && (j < (count * (count - 2))  //,..
                && (circles.get(j).getColor() == circles.get(j + count + 1).getColor()) && (circles.get(j).getColor() == circles.get(j + (count * 2) + 1).getColor()))) {
            maybeArray.add(j + "," + (j + count + 1) + "," + (j + (count * 2) + 1));
        }
    }

    private void checkColMaybe(int i, int col, int count) {
        if ((i + 3) <= (count - 1 + count * col)
                && (circles.get(i).getColor() == circles.get(i + 1).getColor()) && (circles.get(i).getColor() == circles.get(i + 3).getColor())) {
            maybeArray.add(i + "," + (i + 1) + "," + (i + 3));
            // Gdx.app.log("Matrix", " checkColMaybe  " + i);
            //Gdx.app.log("Matrix", " col  " + col);
        }
        if ((i + 3) <= (count - 1 + count * col)
                && (circles.get(i).getColor() == circles.get(i + 2).getColor()) && (circles.get(i).getColor() == circles.get(i + 3).getColor())) {
            maybeArray.add(i + "," + (i + 2) + "," + (i + 3));
            // Gdx.app.log("Matrix", " checkColMaybe  " + i);
            // Gdx.app.log("Matrix", " col  " + col);
        }
        if ((col < count - 1) && (i <= (count - 1 + count * (col) - 2))
                && (circles.get(i).getColor() == circles.get(i + 1).getColor()) && (circles.get(i).getColor() == circles.get(i + 2 + count).getColor())) {
            maybeArray.add(i + "," + (i + 1) + "," + (i + 2 + count));
        }
        if ((col < count - 1) && (i <= (count - 1 + count * (col) - 2))
                && (circles.get(i).getColor() == circles.get(i + 2).getColor()) && (circles.get(i).getColor() == circles.get(i + 1 + count).getColor())) {
            maybeArray.add(i + "," + (i + 2) + "," + (i + 1 + count));
        }
        if ((col < count - 1) && (i <= (count - 1 + count * (col) - 2))
                && (circles.get(i).getColor() == circles.get(i + 2 + count).getColor()) && (circles.get(i).getColor() == circles.get(i + 1 + count).getColor())) {
            maybeArray.add(i + "," + (i + 1 + count) + "," + (i + 2 + count));
        }
        if ((col > 0) && (i <= (count + (count * col) - 3))
                && (circles.get(i).getColor() == circles.get(i - count + 1).getColor()) && (circles.get(i).getColor() == circles.get(i - count + 2).getColor())) {
            maybeArray.add(i + "," + (i - count + 1) + "," + (i - count + 2));
        }
        if ((col > 0) && (i <= (count + (count * col) - 3))
                && (circles.get(i).getColor() == circles.get(i - count + 1).getColor()) && (circles.get(i).getColor() == circles.get(i + 2).getColor())) {
            maybeArray.add(i + "," + (i - count + 1) + "," + (i + 2));
        }
        if ((col > 0) && (i <= (count + (count * col) - 3))
                && (circles.get(i).getColor() == circles.get(i + 1).getColor()) && (circles.get(i).getColor() == circles.get(i - count + 2).getColor())) {
            maybeArray.add(i + "," + (i + 1) + "," + (i - count + 2));
        }

    }

    public void showMaybe() {
        if (maybeArray.size() > 0) {

            startJump = true;

            numberToHelp = 0;// random.nextInt(maybeArray.size());

        } else {


        }
        playState.timeHelpReset();
    }

    //create new baloons
    private void newCircles() {
        float[] intArray = new float[count];
        float[] startArray = new float[count];

        //................LEVELS................
        int score = playState.getScore();
       /* if (score >= ConstantLoader.SCORE_LEVEL_ONE && score < ConstantLoader.SCORE_LEVEL_TWO) {
            colors = ConstantLoader.COLOR_LEVEL_ONE;
        }
        if (score > ConstantLoader.SCORE_LEVEL_TWO) {
            colors = ConstantLoader.COLOR_LEVEL_TWO;
        }*/

        //....................................

        newCirclesArray = boom.getBoomArray();
        for (int j = 0; j < newCirclesArray.size(); j++) {
            circles.get(newCirclesArray.get(j)).setPosition(circles.get(newCirclesArray.get(j)).getStartX(), 1);
        }

        for (int col = 0; col < count; col++) {
            int indx = 0;
            // Gdx.app.log("Matrix", " col  " +col);
            for (int i = 0 + (col * count); i < count + (col * count); i++) {
                startArray[indx] = circles.get(i).getStartY();
                intArray[indx] = circles.get(i).getPosition().y;
                indx++;
            }

            Arrays.sort(intArray);
            Arrays.sort(startArray);
            int uniq = 0;
            for (int i = (intArray.length - 1) + (col * count); i >= 0 + (col * count); i--) {
                // Gdx.app.log("Matrix", " i  " +i);

                for (int j = startArray.length - 1; j >= 0; j--) {

                    if (circles.get(i).getPosition().y == intArray[j]) {
                        circles.get(i).setStart(circles.get(i).getStartX(), startArray[j]);
                    }
                }
            }

            for (int i = 0 + (col * count); i < count + (col * count); i++) {

                if (circles.get(i).getPosition().y == 1) {
                    circles.get(i).setStart(circles.get(i).getStartX(), startArray[uniq]);

                    color = random.nextInt(colors) + 1;
                    circles.get(i).setCircle(color);
                    //   Gdx.app.log("Matrix", " set color " + i);
                    uniq++;
                }
            }
        }
        boom.clearBoomArray();
        startFall = true;
    }

    public void renew() {

        for (int col = 0; col < count; col++) {
            for (int i = 0 + (col * count); i < count + (col * count); i++) {
                for (int j = 0 + (col * count); j < count + (col * count); j++) {
                    if (circles.get(i).getStartY() < circles.get(j).getStartY()) {
                        circles.get(i).changeBaloons(circles.get(j));
                        bufy = circles.get(i).getStartY();
                        circles.get(i).setStart(circles.get(i).getStartX(), circles.get(j).getStartY());
                        circles.get(j).setStart(circles.get(i).getStartX(), bufy);
                    }

                }
            }

            for (int i = 0 + (col * count); i < count + (col * count); i++) {
                circles.get(i).setPosition(circles.get(i).getStartX(), circles.get(i).getStartY());

            }
        }

    }

    public void update(float dt) {
        if (!tried || changed) {
            checkEqual();
            boom.startBoom(circles);

        }


        boom.update(circles, dt);
        if (!boom.isBoomed() && boom.getBoomArray().size() > 0) {
            newCircles();
        }

        if (startFall) {
            playState.timeHelpReset();
            int fallcircle = 0;
            if(maybeArray.size()>0) {
                stopJump(numberToHelp);
            }
            for (int i = 0; i < circles.size; i++) {
                if (circles.get(i).getPosition().y < circles.get(i).getStartY()) {
                    circles.get(i).update(dt);
                }
                if (circles.get(i).getPosition().y == circles.get(i).getStartY()) {
                    fallcircle++;
                }
            }
            //  Gdx.app.log("Matrix", " fallcircle " + fallcircle);
            if (fallcircle == circles.size) {

                renew();
                startFall = false;
                tried = false;
//                Gdx.app.log("Matrix", " fall  ");
            }
        }

        // maybeArray.get(random.nextInt(maybeArray.size()));
        if (playState.isGameOver() || playState.isWin())
        {

            stopJump(numberToHelp);
        }
        if (startJump && !startFall) {
            jumpMayBe(numberToHelp);
            // jumpMayBe(0);
        }
        // Gdx.app.log("Matrix", " startFall  " + startFall);

    }

    float yspeed = 1.0f;
    float gravity = 0.01f;
    float jump = circleRadius / 2;
    float maxJump = jump + jump / 8;

    private void jumpMayBe(int j) {

        String[] jumpString = maybeArray.get(j).split(",");

        yspeed += gravity;
        for (int i = 0; i < 3; i++) {
            circles.get(Integer.valueOf(jumpString[i])).setPosition(circles.get(Integer.valueOf(jumpString[i])).getPosition().x, circles.get(Integer.valueOf(jumpString[i])).getPosition().y + yspeed);
        }
        if (circles.get(Integer.valueOf(jumpString[0])).getPosition().y < circles.get(Integer.valueOf(jumpString[0])).getStartY()) {
            yspeed = 1;
        }
        if (circles.get(Integer.valueOf(jumpString[0])).getPosition().y > circles.get(Integer.valueOf(jumpString[0])).getStartY() + jump && yspeed > 0) {
            yspeed *= -1f;
        }
        if (circles.get(Integer.valueOf(jumpString[0])).getPosition().y > circles.get(Integer.valueOf(jumpString[0])).getStartY() + jump && yspeed < 0) {
            yspeed *= 1;
        }
        if (circles.get(Integer.valueOf(jumpString[0])).getPosition().y > circles.get(Integer.valueOf(jumpString[0])).getStartY() + maxJump) {
            yspeed = 0;
            circles.get(Integer.valueOf(jumpString[0])).setPosition(circles.get(Integer.valueOf(jumpString[0])).getPosition().x, circles.get(Integer.valueOf(jumpString[0])).getStartY());
            circles.get(Integer.valueOf(jumpString[1])).setPosition(circles.get(Integer.valueOf(jumpString[1])).getPosition().x, circles.get(Integer.valueOf(jumpString[1])).getStartY());
            circles.get(Integer.valueOf(jumpString[2])).setPosition(circles.get(Integer.valueOf(jumpString[2])).getPosition().x, circles.get(Integer.valueOf(jumpString[2])).getStartY());
        }
    }

    public void stopJump(int j)
    {
        // Gdx.app.log("Matrix", " stopJump  " + j);
        startJump = false;
        if (j>=0) {
            String[] jumpString = maybeArray.get(j).split(",");

            circles.get(Integer.valueOf(jumpString[0])).setPosition(circles.get(Integer.valueOf(jumpString[0])).getPosition().x, circles.get(Integer.valueOf(jumpString[0])).getStartY());
            circles.get(Integer.valueOf(jumpString[1])).setPosition(circles.get(Integer.valueOf(jumpString[1])).getPosition().x, circles.get(Integer.valueOf(jumpString[1])).getStartY());
            circles.get(Integer.valueOf(jumpString[2])).setPosition(circles.get(Integer.valueOf(jumpString[2])).getPosition().x, circles.get(Integer.valueOf(jumpString[2])).getStartY());
        }
    }

    public void drawCircles(SpriteBatch sb, float runTime) {

        for (int i = 0; i < circles.size; i++) {
            if (circles.get(i).isBoomed) {
                //Gdx.app.log("Matrix", " isBoomed  " + i);

                // pweboomanimation.start(circles.get(i), runTime);
                // pweboomanimation = new PowerBoomAnimation(circles.get(i));

                //  pweboomanimation.drawPowerBoomAnimation(powerCircles.get(i).getStartY(), sb, runTime);

                circles.get(i).setBoomed(false);
            }
            sb.draw(circles.get(i).getColorSprite(), circles.get(i).getPosition().x, circles.get(i).getPosition().y, circleRadius * 2, circleRadius * 2);
        }
    }

    public void drawPowerCircles(SpriteBatch sb, float runTime)
    {
        for (int i = 0; i < powerCircles.size; i++) {
            sb.draw(powerCircles.get(i).getColorSprite(), powerCircles.get(i).getPosition().x, powerCircles.get(i).getPosition().y, circleRadius * 2, circleRadius * 2);
            ResourseLoader.font.draw(sb, "" + powerCircles.get(i).getScore(), powerCircles.get(i).getPosition().x + circleRadius /2,powerCircles.get(i).getPosition().y + circleRadius * 2);
        }
    }

    public void drawSoul(SpriteBatch sb)
    {
        boom.drawSoul(sb);
    }


    public static float getRADIUS() {
        return circleRadius;
    }

    public void dispose() {
    }
}