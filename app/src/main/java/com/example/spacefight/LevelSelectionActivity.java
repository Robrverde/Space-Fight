package com.example.spacefight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LevelSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
    }

    public void onLevelPressed(View view)
    {
        Intent intent = new Intent(getBaseContext(), GameActivity.class);

        if(view.getId() == R.id.Level1_button)
        {

            intent.putExtra("Level", 1);
            intent.putExtra("Enemies", 8);
        }
        else if(view.getId() == R.id.Level2_button)
        {
            intent.putExtra("Level", 2);
            intent.putExtra("Enemies", 12);
        }

        startActivity(intent);
    }

    public void onBackPressed(View view)
    {
        finish();
    }
}