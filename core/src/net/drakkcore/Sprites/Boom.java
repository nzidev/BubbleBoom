package net.drakkcore.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import net.drakkcore.States.PlayState;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Banner on 03.04.2016.
 */

public class Boom{

    private Animations boomCircleAnimations;
    private ArrayList<Integer> boomArray, blackArray, greyArray;
    private PlayState playState;
    private PowerBoomAnimation pweboomanimation;
    public static final int BlackCount = 5;
    public static final int GreyCount = 4;
    public boolean boomed, boomCol, boomCr;
    private int buf, min,max, maxRow,maxCol, count, eqal;

    public Boom(PlayState playState) {
        this.playState = playState;
        boomArray = new ArrayList<Integer>();
        blackArray = new ArrayList<Integer>();
        greyArray = new ArrayList<Integer>();
        pweboomanimation = new PowerBoomAnimation();

        Texture texture = new Texture("boom.png");

        boomCircleAnimations = new Animations(new TextureRegion(texture),3, 0.7f);


    }

    public void setBoom(int eqal, int max, int count, boolean row, Array<Baloon> circles, int numDragCircle, int dragCircle){
        this.count = count;
        this.eqal = eqal;
        this.max = max;
//            Gdx.app.log("Boom", " eqal " + eqal );
//            Gdx.app.log("Boom", " max " + max );
        if (eqal > 2)
        {
            //  Gdx.app.log("Boom", "add score " + eqal);
            playState.addScore(eqal);


            for(int i = eqal; i >0 ; i--) {
                if (row) {
                    buf = max - i * count + count;
                    if (circles.get(buf).getStates()== Baloon.BaloonState.CROSS)
                    {
                        //   Gdx.app.log("Boom", " boomCross buf " + buf);
                        //   Gdx.app.log("Boom", " boomCross state " + circles.get(buf).getStates() );
                        boomCross(buf,true);
                        i = 1;
                        eqal = count;
                        boomCr = true;
                        //   Gdx.app.log("Boom", " CROSS state - add score " + (count*eqal));
                        playState.addScore(count*eqal);
                    }
                    else if (circles.get(buf).getStates() == Baloon.BaloonState.GREY) {
                        min = max;

                        while (count-1 < min){
                            min = min - count;

                        }

                        max = min + (count * (count-1));
                        eqal = count;
                        i = 1;
                        boomCol = true;
                        //   Gdx.app.log("Boom", " GREY state - add score " + eqal);
                        playState.addScore(eqal);
                    }
                } else {
                    buf = max - i + 1;
                    if (circles.get(buf).getStates()== Baloon.BaloonState.CROSS)
                    {
                        //   Gdx.app.log("Boom", " boomCross buf " + buf);
                        //   Gdx.app.log("Boom", " boomCross state " + circles.get(buf).getStates() );
                        boomCross(buf, false);
                        i = 1;
                        eqal = count;
                        boomCr = true;
                        //   Gdx.app.log("Boom", " CROSS state - add score " + (count*eqal));
                        playState.addScore(count*eqal);
                    }
                    else if (circles.get(buf).getStates() == Baloon.BaloonState.GREY)
                    {
                        min = buf / count;
                        min = min * count -1;
                        max = min + count ;
                        eqal = count;
                        i = 1;
                        boomCol = true;
                        //   Gdx.app.log("Boom", " GREY state - add score " + eqal);
                        playState.addScore(eqal);
                    }
                }
            }

            for(int i = eqal; i >0 ; i--) {
                if(boomCr)
                {

                    buf = maxRow - i * count + count;
                    boomArray.add(buf);
                    // Gdx.app.log("Boom", " boomCross buf " + buf);
                    buf = maxCol - i + 1;
                    boomArray.add(buf);
                    //  Gdx.app.log("Boom", " boomCross buf " + buf);


                }
                else {
                    if (row) {
                        buf = max - i * count + count;
                    } else {
                        buf = max - i + 1;
                    }

                    if (!boomCol && eqal == BlackCount && (buf == numDragCircle || buf == dragCircle)) {
                        circles.get(buf).setBlack();
                        blackArray.add(buf);
                    } else if (!boomCol && eqal == GreyCount && (buf == numDragCircle || buf == dragCircle)) {
                        // Gdx.app.log("Boom", " Grey " + buf );
                        circles.get(buf).setGrey();
                        greyArray.add(buf);

                    } else {
                        circles.get(buf).setNormal();
                        boomArray.add(buf);
                    }
                }
            }

        }

        boomCol = false;
        boomCr = false;
        //Gdx.app.log("Boom", " boomArray " + boomArray );
    }



    private void boomCross(int buf, boolean b) {

        min = buf;
        while (count-1 < min) {
            min = min - count;
        }
        maxRow = min + (count * (count - 1));
        min = buf / count;
        min = min * count -1;
        maxCol = min + count ;


    }

    public void startBoom(Array<Baloon> circles){

        Collections.sort(boomArray);


        pweboomanimation.start(boomArray,circles);
        //Gdx.app.log("Boom", " boomArray " + boomArray);
        for (int i = 0; i < boomArray.size(); i++) {
            for (int g = 0; g < Matrix.powerCircles.size; g++) {
                if (Matrix.powerCircles.get(g).color == circles.get(boomArray.get(i)).color) {
                    Matrix.powerCircles.get(g).addScore();

                }
            }
        }

        // Gdx.app.log("Boom", " boomArray " + boomArray);
        for (int i = 0; i < boomArray.size() - 1; i++) {

            if ((boomArray.get(i) == boomArray.get(i+1)) && (circles.get(boomArray.get(i)).getStates()!= Baloon.BaloonState.CROSS)) {

                circles.get(boomArray.get(i)).setCross(true);
                //     Gdx.app.log("Boom", " NEW CROSS " + boomArray.get(i) );
                greyArray.add(boomArray.get(i));
                boomArray.remove(boomArray.get(i+1));
                boomArray.remove(boomArray.get(i));
            }
            else if((boomArray.get(i) == boomArray.get(i+1)) && (circles.get(boomArray.get(i)).getStates()== Baloon.BaloonState.CROSS)){
                //     Gdx.app.log("Boom", " CROSS deleted " + boomArray.get(i) );
                circles.get(boomArray.get(i)).setNormal();
                boomArray.remove(boomArray.get(i));
                //     Gdx.app.log("Boom", " CROSS state " + (circles.get(boomArray.get(i)).getStates()) );

            }
        }

        boomCircleAnimations.setBoomed(true);

    }

    public boolean isBoomed() {
        boomed = boomCircleAnimations.isBoomed();
        return boomed;
    }

    public void addBoomArray(int value) {
        boomArray.add(value);
    }

    public ArrayList<Integer> getGreyArray() {
        return greyArray;
    }

    public ArrayList<Integer> getBoomArray() {
        return boomArray;
    }

    public void update(Array<Baloon> circles, float dt){
        boomCircleAnimations.update(dt);

        pweboomanimation.update(dt);
        for (int i = 0; i < boomArray.size(); i++) {
            circles.get(boomArray.get(i)).setBoomed(true);
            circles.get(boomArray.get(i)).setCircleSprite(boomCircleAnimations.getFrame());




            // PowerBoomAnimation.start(circles.get(boomArray.get(i)), dt);
        }
    }


    public void clearBoomArray() {
        blackArray.clear();
        boomArray.clear();
        greyArray.clear();
        boomCircleAnimations.clearFrame();
        //  Gdx.app.log("Boom", " boomArray clear" );
    }

    public void drawSoul(SpriteBatch sb) {
        pweboomanimation.draw(sb);
    }
}