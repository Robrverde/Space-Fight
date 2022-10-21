package com.example.spacefight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class VictoryActivity extends AppCompatActivity {

    private int level;
    private int enemies;
    private int health;
    private int totalShots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        GlobalVariables.games_won++;

        //Get variables from level selection
        Bundle extras = this.getIntent().getExtras();

        if(extras != null)
        {
            level = extras.getInt("Level");
            enemies = extras.getInt("Enemies");
            health = extras.getInt("Health");
            totalShots = extras.getInt("Shots");
        }

        RatingBar totalScore = findViewById(R.id.totalScore_ratingBar);

        double accuracy =((double)enemies)/totalShots;
        accuracy = accuracy * 100;

        TextView enemies_textView = findViewById(R.id.totalEnemies_textView);
        enemies_textView.append(" " + enemies + "/" + enemies);

        TextView health_textView = findViewById(R.id.healthRemaining_textView);
        health_textView.append(" " + health + "/3");


        TextView accuracy_textView = findViewById(R.id.accuracy_textView);
        accuracy_textView.append(" " + new BigDecimal(accuracy).setScale(1, RoundingMode.HALF_UP) + "%");

        if(accuracy > 75 && health == 3)
        {
            totalScore.setRating(3);
        }
        else if(accuracy > 75 || health == 3)
        {
            totalScore.setRating(2);
        }
        else
            totalScore.setRating(1);
    }

    public void onAgainPressed(View view)
    {
        Intent intent = new Intent(getBaseContext(), GameActivity.class);
        intent.putExtra("Level", level);
        intent.putExtra("Enemies", enemies);

        startActivity(intent);
        finish();
    }

    public void onQuitPressed(View view)
    {
        finish();
    }
}