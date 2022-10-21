package com.example.spacefight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOverActivity extends AppCompatActivity {

    private int level;
    private int enemies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        GlobalVariables.game_overs++;

        //Get variables from level selection
        Bundle extras = this.getIntent().getExtras();

        if(extras != null)
        {
            level = extras.getInt("Level");
            enemies = extras.getInt("Enemies");
        }
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