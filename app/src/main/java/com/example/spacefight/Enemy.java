package com.example.spacefight;

import android.graphics.Bitmap;
import java.util.Random;

/*
Most of the functions related to enemy behavior were inspired by example provided in this website:
 http://gamecodeschool.com/android/coding-a-space-invaders-game/
*/

public class Enemy extends MovingObject{

    // This will hold the pixels per second speed that the invader will move
    float speed = 10;

    public final int LEFT = 1;
    public final int RIGHT = 2;

    Projectile projectile;

    // Is the ship moving and in which direction
    int shipMoving = RIGHT;

    boolean isVisible;


    Enemy(Bitmap i, int i_size, int x, int y)
    {
        super(i, i_size, x, y);

        isVisible = true;
    }

    public void update()
    {

        if(shipMoving == LEFT){
            x_position -= speed;
        }

        if(shipMoving == RIGHT){
            x_position += speed;
        }
    }

    public void dropDownAndReverse()
    {
        if(shipMoving == LEFT)
        {
            shipMoving = RIGHT;
        }
        else
        {
            shipMoving = LEFT;
        }

        y_position += image_size/2;

        //Cap speed at 40~
        if(speed < 40)
            speed *= 1.18f;
    }

    public boolean takeAim(float player_x_position, float player_size){

        int randomNumber;

        // If near the player
        if((player_x_position + player_size > x_position &&
                player_x_position + player_size < x_position + image_size) ||
                (player_x_position > x_position && player_x_position < x_position + image_size))
        {

            // A 1 in 500 chance to shoot
            //150
            randomNumber = new Random().nextInt(100);
            return randomNumber == 0;
        }

        // If firing randomly (not near the player) a 1 in 5000 chance
        //2000
        randomNumber = new Random().nextInt(500);
        return randomNumber == 0;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }
}
