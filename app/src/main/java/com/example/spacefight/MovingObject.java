package com.example.spacefight;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MovingObject {

    int x_position;
    int y_position;
    int image_size;
    float angle = 0;
    Bitmap image;

    MovingObject(Bitmap i, int i_size,int x, int y)
    {
        image = i;
        image_size = i_size;
        x_position = x;
        y_position = y;
    }

    void draw (Canvas canvas)
    {
        canvas.save();
        canvas.translate(x_position + 100, y_position + 100);
        canvas.rotate(angle);
        canvas.drawBitmap(image, -100, -100, null);
        canvas.restore();
    }

    boolean collidesWith(MovingObject otherObj)
    {
        //Check for collision
        return Math.abs(x_position - otherObj.x_position) < 100 && Math.abs(y_position - otherObj.y_position) < 100;
    }
}
