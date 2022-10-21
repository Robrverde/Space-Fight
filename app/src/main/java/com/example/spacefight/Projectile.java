package com.example.spacefight;

import android.graphics.Bitmap;

/*
Most of the functions related to projectile behavior were inspired by example provided in this website:
 http://gamecodeschool.com/android/coding-a-space-invaders-game/
*/

public class Projectile extends MovingObject{


    // Which way is it shooting
    public final int UP = 0;
    public final int DOWN = 1;

    //Going nowhere
    float speed = 70;
    int heading = -1;

    boolean isActive;


    Projectile(Bitmap i, int i_size, int x, int y) {
        super(i, i_size, x, y);
        isActive = false;
    }

    public boolean getStatus(){
        return isActive;
    }

    public void setInactive(){
        isActive = false;
    }

    public boolean shoot(int x, int y, int direction, SoundPlayer sound) {

        //if there is no projectile on screen
        if (!isActive) {
            x_position = x;
            y_position = y;
            heading = direction;
            isActive = true;
            sound.playProjectileSound();

            return true;
        }

        // There is a projectile on screen
        return false;
    }

    void update(int height)
    {
        angle += 2;

        if(heading == UP){
            y_position -= speed ;
        }else{
            y_position += speed;
        }

        //Prevent object to go out of screen
        if(y_position < -image_size || y_position > height + image_size)
        {
            setInactive();
        }

    }
}
