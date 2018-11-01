package net.drakkcore.Sprites;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import net.drakkcore.loader.ResourseLoader;

public class Baloon {
    public static final int GRAVITY = 1000;
    private Circle boundsCircle;
    private Vector2 position;
    private Vector2 velocity;
    //private TextureRegion circle;

    private int circle_Height, circle_Width;
    private float circle_radius;
    private float startX, startY, bufy;
    public float screenWidth;
    public float screenHeight;
    public boolean isGrey, isBlack, isBoomed, bufGrey, bufBlack, isCross, bufCross;
    public int color;


    public int score = 0;
    public Sprite  circle;
    public com.badlogic.gdx.graphics.g2d.Animation animation, boomAnimation;
    public int greyColor;
    private int bufColor;
    private BaloonState states, bufstates;

    public enum BaloonState{
        NORMAL, GREY, BLACK, CROSS, BOOM
    }



    public Baloon(int color, float x, float y){

       // circle = new Texture("circle-s.png");
        this.color = color;
        screenWidth = 800; //Gdx.graphics.getWidth();
        screenHeight = 480;//Gdx.graphics.getHeight();
        circle_radius = Matrix.getRADIUS();
        x = x - circle_radius;
        y = y - circle_radius;
        position = new Vector2(x ,y );
        velocity = new Vector2(0,0);
        startX = x;
        startY = y;
        states = BaloonState.NORMAL;
        animation = ResourseLoader.flashArray[color];
        boomAnimation = ResourseLoader.boomAnimation;
        circle = ResourseLoader.colorsArray[color];
        boundsCircle = new Circle(x,y,circle_radius);
    }

    public Sprite getColorSprite() {
        return circle;
    }

    public com.badlogic.gdx.graphics.g2d.Animation getAnimation() {
     //   if (states == BaloonState.BOOM)
      //      return  boomAnimation;
      //      else
        return animation;
    }

    public int getColor() {
        return color;
    }

    public int getGreyColor() {
        return greyColor;
    }

    public void setGrayCircleSprite() {
        this.circle = ResourseLoader.colorsGrayArray[color];
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setCircleSprite(Sprite circleSprite){
        this.circle = circleSprite;
    }
    public void setCircle(int color) {
        this.color = color;
        changeColor();
    }

    public void update(float dt){
            if(position.y > 0)
                velocity.add(0, GRAVITY);

        velocity.scl(dt);

        position.add(0, velocity.y);
        if (position.y > startY)
            position.y = startY;

        changeColor();
    }

    public void changeBaloons(Baloon dragBaloon){

        bufColor = dragBaloon.getColor();

        bufstates = dragBaloon.getStates();


        dragBaloon.setCircle(getColor());
        setCircle(bufColor);

        dragBaloon.setStates(getStates());
        setStates(bufstates);

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void addScore() {
        score++;
    }


    public Vector2 getPosition() {
        return position;
    }


    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
        boundsCircle.setPosition(x,y);
    }



    public void setBoomed(boolean boomed) {
        isBoomed = boomed;

        if(isBoomed){
            setNormal();
        }
    }



    public float getStartX()
    {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStart(float startX, float startY) {
        this.startX = startX;
        this.startY = startY;

    }
    public boolean collides(Circle player){


        return Intersector.overlaps(player, boundsCircle);
    }

    public void setGrey() {

        states = BaloonState.GREY;

        changeColor();
    }

    public void setBoom() {

        states = BaloonState.BOOM;

//        changeColor();
    }


    public void setBlack() {
        states = BaloonState.BLACK;

        changeColor();
    }

    public boolean isCross() {
        return isCross;
    }

    public void setCross(boolean cross) {
        isCross = cross;
        if (cross)
        states = BaloonState.CROSS;
        else
            states = BaloonState.NORMAL;

        changeColor();

    }

    public BaloonState getStates() {
        return states;
    }

    public void setStates(BaloonState states) {
        this.states = states;
        changeColor();
    }

    public void setNormal(){
        this.states = BaloonState.NORMAL;
        changeColor();
    }

    private void changeColor() {

        switch (states) {
            case BLACK:
                this.circle = ResourseLoader.circBlack;
                break;
            case CROSS:
                this.circle = ResourseLoader.colorsStarArray[getColor()];
                break;
            case GREY:
                this.circle = ResourseLoader.colorsGrayArray[getColor()];
                break;
            case NORMAL:
                this.circle = ResourseLoader.colorsArray[getColor()];
                break;
        }
    }

    public void circleMove(float x, float y, int count){
//        Gdx.app.log("Baloon", " move " );

        position.x = x;
        position.y = y;
        if (position.x - startX > circle_radius/2 ) {
            position.y = startY;
         //   if (position.x > startX + circle_radius*2 + (screenHeight / count))
                position.x = startX + circle_radius*2 + (screenHeight / (count*2));
        }
        if (startX - position.x > circle_radius/2 ) {
            position.y = startY;
           // if (position.x < startX - circle_radius*2 - (screenHeight / count))
                position.x = startX - circle_radius*2 - (screenHeight / (count*2));
        }
        if (position.y - startY > circle_radius/2) {
            position.x = startX;
          //  if (position.y > startY + circle_radius*2 + (screenWidth / count))
                position.y = startY + circle_radius*2 + (screenWidth / (count*2));
        }
        if (startY - position.y > circle_radius/2) {
            position.x = startX;
           // if (position.y < startY - circle_radius*2 - (screenWidth / count))
                position.y = startY - circle_radius*2 - (screenWidth / (count*2));
        }

        boundsCircle.setPosition(position.x,position.y);
    }


}
