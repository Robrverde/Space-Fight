package com.example.spacefight;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class GameActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    //Public variables

    //Paints
    Paint white_text;

    //Player
    Bitmap player_image;
    Player player;
    int player_size = 250;
    int pos_x = 0;

    //Projectile
    Bitmap projectile_image;
    int projectile_size = 250;

    // The invaders bullets

    //Enemies
    int totalEnemies;
    Enemy[] enemies;
    Bitmap enemy_image;
    int enemy_size = 200;

    SurfaceHolder holder = null;
    Animator my_animator;
    SoundPlayer sound;

    //Game status
    Point screen_size;
    boolean game_won = false;
    boolean lost = false;
    int level;
    int enemies_defeated = 0;
    int totalShots = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GlobalVariables.games_played++;

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        screen_size = new Point();
        display.getSize(screen_size);

        pos_x = (screen_size.x - player_size)/2;

        //Get variables from level selection
        Bundle extras = this.getIntent().getExtras();

        if(extras != null)
        {
            level = extras.getInt("Level");
            enemies = new Enemy[extras.getInt("Enemies")];
            totalEnemies = extras.getInt("Enemies");
        }

        sound = new SoundPlayer(this);

        white_text = new Paint();
        white_text.setColor(Color.WHITE);
        white_text.setTextSize(90);

        //Scale bitmap image of all movingObjects

        //Bitmap for projectiles
        projectile_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ship_projectile), projectile_size, projectile_size, false);

        //Bitmap for Player
        player_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.player_ship), player_size, player_size, false);
        player = new Player(player_image, player_size,(screen_size.x - player_size)/2, screen_size.y - player_size);
        player.projectile = new Projectile(projectile_image, projectile_size, player.x_position, player.y_position);

        //Bitmap for enemies
        enemy_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.alien_ship), enemy_size, enemy_size, false);

        // Build an army of invaders
        int offset_x = 0;
        int offset_y = 130;
        int row = 0;
        for(int i = 0; i < totalEnemies; i++)
        {
            if(row > 3)
            {
                offset_x = 0;
                offset_y += enemy_size;
                row = 0;
            }
            enemies[i] = new Enemy(enemy_image, enemy_size, offset_x, offset_y);
            enemies[i].projectile = new Projectile(projectile_image, projectile_size, offset_x, 130);
            offset_x += enemy_size;
            row++;
        }

        //Initialize Surface Holder
        SurfaceView my_surface = findViewById(R.id.surfaceView);
        my_surface.getHolder().addCallback(this);

        //Initialize Animator
        my_animator = new Animator(this);
        my_animator.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: //Player is touching the screen

                if(player.projectile.shoot(player.x_position, player.y_position, player.projectile.UP, sound))
                {
                    totalShots++;
                    GlobalVariables.shots_fired++;
                }


                break;

            case MotionEvent.ACTION_MOVE: //Player is moving
                pos_x = (int) event.getX();
                break;
        }

        return true;
    }


    public void update(int width, int height)
    {
        if(game_won)
        {
            //Go to Game Over screen and finish this activity
            Intent intent = new Intent(getBaseContext(), VictoryActivity.class);
            intent.putExtra("Level", level);
            intent.putExtra("Enemies", totalEnemies);
            intent.putExtra("Health", player.getLives());
            intent.putExtra("Shots", totalShots);
            startActivity(intent);

            //GlobalVariables.SaveData(this);
            my_animator.finish();
            finish();
        }
        else if(lost)
        {
            //Go to Game Over screen and finish this activity
            Intent intent = new Intent(getBaseContext(), GameOverActivity.class);
            intent.putExtra("Level", level);
            intent.putExtra("Enemies", totalEnemies);
            startActivity(intent);

            my_animator.finish();
            finish();
        }

        //Boolean to check if the enemies touches on side of the screen
        boolean bumped = false;

        //PUMPKIN MOVEMENT
        player.updateWithTouchEvent(width, pos_x);

        // Update the players bullet
        if(player.projectile.getStatus()){
            player.projectile.update(height);
        }

        // Update all the invaders if visible
        for(int i = 0; i < totalEnemies; i++)
        {

            if(enemies[i].getVisibility())
            {
                // Move the next invader
                enemies[i].update();

                // Does he wants to take a shot?
                if(enemies[i].takeAim(player.x_position, player.image_size))
                {
                    enemies[i].projectile.shoot(enemies[i].x_position + enemies[i].image_size / 2,
                            enemies[i].y_position, enemies[i].projectile.DOWN, sound);
                }

                // If that move caused them to bump the screen change bumped to true
                if (enemies[i].x_position > width - enemies[i].image_size || enemies[i].x_position < 0)
                {
                    bumped = true;
                }
            }
        }

        // Update all the invaders bullets if active
        for(int i = 0; i < totalEnemies; i++){
            if(enemies[i].projectile.getStatus()) {
                enemies[i].projectile.update(height);
            }
        }

        // Did an invader bump into the edge of the screen
        if(bumped){

            // Move all the invaders down and change direction
            for(int i = 0; i < totalEnemies; i++){
                enemies[i].dropDownAndReverse();

                // Have the invaders landed
                if(enemies[i].y_position > height - player.image_size || player.getLives() == 0){
                    lost = true;
                }
            }
        }

        // Has the player's bullet hit an invader
        if(player.projectile.getStatus()) {
            for (int i = 0; i < totalEnemies; i++) {
                if (enemies[i].getVisibility()) {
                    if (player.projectile.collidesWith(enemies[i]) || enemies[i].collidesWith(player.projectile))
                    {
                        enemies[i].setInvisible();
                        enemies_defeated++;
                        player.projectile.setInactive();

                        GlobalVariables.aliens_defeated++;
                    }
                }
            }
        }

        // Has an invader bullet hit the player ship
        for(int i = 0; i < totalEnemies; i++){
            if(enemies[i].projectile.getStatus()){
                if(enemies[i].projectile.collidesWith(player) || player.collidesWith(enemies[i].projectile))
                {
                    enemies[i].projectile.setInactive();

                    if(player.getLives() > 0)
                        player.lostLife();

                    //soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);
                }
            }
        }

        if(enemies_defeated == totalEnemies)
            game_won = true;
    }


    public void draw()
    {
        if(holder == null)
            return;

        Canvas my_canvas = holder.lockCanvas();

        update(my_canvas.getWidth(), my_canvas.getHeight());

        //Violet Background
        //my_canvas.drawColor(Color.rgb(75, 0, 130));
        my_canvas.drawColor(Color.BLACK);

        my_canvas.drawText("Lives: " + player.getLives(), 30, 120, white_text);
        my_canvas.drawText("Level " + level, 750, 120, white_text);

        player.draw(my_canvas);

        // Draw the players bullet if active
        if(player.projectile.getStatus()){
            player.projectile.draw(my_canvas);
        }

        if(!lost)
        {
            // Update all the invader's bullets if active
            for(int i = 0; i < totalEnemies; i++){
                if(enemies[i].projectile.getStatus()) {
                    enemies[i].projectile.draw(my_canvas);
                }
            }

            // Draw the invaders
            for(int i = 0; i < totalEnemies; i++)
            {
                if(enemies[i].getVisibility())
                {
                    enemies[i].draw(my_canvas);
                }
            }

        }

        holder.unlockCanvasAndPost(my_canvas);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        holder = surfaceHolder;
        draw();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        holder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        holder = null;
    }

    @Override
    public void onDestroy()
    {
        my_animator.finish();
        super.onDestroy();
    }


}