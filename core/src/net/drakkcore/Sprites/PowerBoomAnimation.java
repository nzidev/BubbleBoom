package net.drakkcore.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import net.drakkcore.loader.ResourseLoader;
import net.drakkcore.Sprites.Baloon;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;


/**
 * Created by n7701-00-057 on 05-Jul-17.
 */

public class PowerBoomAnimation {
    public static final int GRAVITY = 1000;
    public Sprite SoulOfBaloon;
    public Baloon baloon;
    public Array<Baloon> soulCircles;

    public static float x,y,startX,startY,endX,endY,deltaX, deltaY;
    private Vector2 velocity, position;

    public PowerBoomAnimation() {
        SoulOfBaloon = ResourseLoader.soulOfBaloon;

        //x = startX = circle.getStartX();
        // y = startY = circle.getStartY();

        velocity = new Vector2(0,0);
        //position = new Vector2(x, y);
    }

    public void start(ArrayList<Integer> boomArray, Array<Baloon> circles) {

        soulCircles = new Array<Baloon>();
        if (boomArray != null && boomArray.size() != 0) {
            for (int j = 0; j < Matrix.powerCircles.size; j++) {

                if (Matrix.powerCircles.get(j).color == circles.get(boomArray.get(0)).color)
                {
                    endX = Matrix.powerCircles.get(j).getPosition().x;
                    endY = Matrix.powerCircles.get(j).getPosition().y;
                }
            }
        }
        for (int i=0;i<boomArray.size();i++)
        {
            soulCircles.add(new Baloon(circles.get(boomArray.get(i)).color,circles.get(boomArray.get(i)).getStartX(),circles.get(boomArray.get(i)).getStartY()));
        }
    }

    public void update(float dt)
    {
        velocity.add(0, GRAVITY);

        velocity.scl(dt);


        if (soulCircles != null) {
            for (int i = 0; i < soulCircles.size; i++) {

                deltaX = (endX - soulCircles.get(i).getPosition().x)*dt*7;
               // deltaY = (endY - soulCircles.get(i).getPosition().y)*dt*7;

                soulCircles.get(i).setPosition(soulCircles.get(i).getPosition().x + deltaX , soulCircles.get(i).getPosition().y+velocity.y);
                if (soulCircles.get(i).getPosition().y >= Matrix.powerCircles.get(0).getPosition().y)
                {
                    soulCircles.get(i).setPosition(endX, Matrix.powerCircles.get(0).getPosition().y);
                }
            }
        }


        //   Gdx.app.log("PowerBoomAnimation", " position " + position );
        //  Gdx.app.log("PowerBoomAnimation", " velocity.scl(dt) " + velocity.scl(dt) );
        //   Gdx.app.log("PowerBoomAnimation", " drawPowerBoomAnimation " +position.y );
        //    Gdx.app.log("PowerBoomAnimation", " startY " +startY );
    }


    public void draw(SpriteBatch sb) {
        if (soulCircles != null) {
            for (int i = 0; i < soulCircles.size; i++) {
                //  sb.draw(SoulOfBaloon, soulCircles.get(i).getPosition().x,soulCircles.get(i).getPosition().y);
                sb.draw(SoulOfBaloon, soulCircles.get(i).getPosition().x, soulCircles.get(i).getPosition().y);
            }
        }
    }

}