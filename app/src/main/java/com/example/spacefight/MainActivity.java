package com.example.spacefight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load stored information
        GlobalVariables.LoadData(this);


    }

    public void onPlayPressed(View view)
    {
        Intent intent = new Intent(getBaseContext(), LevelSelectionActivity.class);
        startActivity(intent);
    }

    public void onStatsPressed(View view)
    {
        Intent intent = new Intent(getBaseContext(), StatsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {

        GlobalVariables.SaveData(this);
        super.onPause();
    }

}