package com.example.spacefight;

import android.graphics.Bitmap;

public class Player extends MovingObject{

    private int lives = 3;
    Projectile projectile;

    Player(Bitmap i, int i_size, int x, int y) {
        super(i, i_size, x, y);
    }

    void updateWithTouchEvent(int width, int pos_x)
    {
        //Change object position
        x_position = pos_x;

        //Prevent object to go out of screen
        if(x_position < 0)
            x_position = 0;
        else if(x_position > width - image_size)
            x_position = width - image_size;
    }

    public void lostLife()
    {
        lives--;
    }

    public int getLives()
    {
        return lives;
    }

}
