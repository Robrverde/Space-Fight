package com.example.spacefight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        if(GlobalVariables.shots_fired != 0)
            GlobalVariables.overall_accuracy = ((double) GlobalVariables.aliens_defeated)/GlobalVariables.shots_fired;

        GlobalVariables.overall_accuracy = GlobalVariables.overall_accuracy * 100;

        if(GlobalVariables.games_played != 0)
            GlobalVariables.win_ratio = ((double) GlobalVariables.games_won)/GlobalVariables.games_played;

        GlobalVariables.win_ratio = GlobalVariables.win_ratio * 100;

        //Display on Stats activity
        TextView aliens_defeated = findViewById(R.id.stats_aliensDefeated_textView);
        TextView shots_fired = findViewById(R.id.stats_shotsFired_textView);
        TextView accuracy = findViewById(R.id.stats_accuracy_textView);
        TextView games_played = findViewById(R.id.stats_gamesPlayed_textView);
        TextView win_ratio = findViewById(R.id.stats_winRatio_textView);
        TextView games_lost = findViewById(R.id.stats_games_lost_textView);

        aliens_defeated.append(" " + GlobalVariables.aliens_defeated);
        shots_fired.append(" " + GlobalVariables.shots_fired);
        accuracy.append(" " + new BigDecimal(GlobalVariables.overall_accuracy).setScale(1, RoundingMode.HALF_UP) + "%");
        games_played.append(" " + GlobalVariables.games_played);
        win_ratio.append(" " + new BigDecimal(GlobalVariables.win_ratio).setScale(1, RoundingMode.HALF_UP) + "%");
        games_lost.append(" " + GlobalVariables.game_overs);
    }

    public void onBackPressed(View view)
    {
        finish();
    }
}