package net.drakkcore.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import net.drakkcore.loader.ResourseLoader;

public class Stone {
    private float circle_radius;
    private Circle boundsCircle;
    private Vector2 position;
    public Sprite circle;
    public Stone(float x, float y){

        circle_radius = Matrix.getRADIUS();
        x = x - circle_radius;
        y = y - circle_radius;
        position = new Vector2(x,y);
        circle = ResourseLoader.circBlack;
        boundsCircle = new Circle(x,y,circle_radius);
    }

    public Vector2 getPosition() {return position;}
    public Sprite getColorSprite() {
        return circle;
    }
}
