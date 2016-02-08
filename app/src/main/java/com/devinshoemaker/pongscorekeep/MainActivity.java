package com.devinshoemaker.pongscorekeep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnPlayerLeft;
    private Button btnPlayerRight;
    private TextView tvCurrentState;

    private Player playerLeft, playerRight;

    private enum states {
        NEW_GAME,
        IN_PROGRESS,
        END_GAME
    }

    private states currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        btnPlayerLeft = (Button) findViewById(R.id.btnPlayerLeft);
        btnPlayerLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(playerLeft, playerRight);
            }
        });

        btnPlayerRight = (Button) findViewById(R.id.btnPlayerRight);
        btnPlayerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(playerRight, playerLeft);
            }
        });

        tvCurrentState = (TextView) findViewById(R.id.tvCurrentState);

        setCurrentState(states.NEW_GAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCurrentState(states state) {
        currentState = state;
        tvCurrentState.setText(currentState.toString());
    }

    private void resetGame() {
        playerLeft = new Player();
        playerRight = new Player();

        playerLeft.setEtName((EditText) findViewById(R.id.etPlayerLeftName));
        playerLeft.setTvScore((TextView) findViewById(R.id.tvPlayerLeftScore));
        setScore(playerLeft, 0);

        playerLeft.setEtName((EditText) findViewById(R.id.etPlayerRightName));
        playerRight.setTvScore((TextView) findViewById(R.id.tvPlayerRightScore));
        setScore(playerRight, 0);

        setCurrentState(states.IN_PROGRESS);
    }

    private boolean isAllowed(states requiredState) {
        return (requiredState.equals(currentState));
    }

    private void updateScore(Player scoringPlayer, Player opposingPlayer) {
        if (isAllowed(states.IN_PROGRESS)) {
            setScore(scoringPlayer, scoringPlayer.getScore() + 1);

            if (isMatchPoint(scoringPlayer.getScore(), opposingPlayer.getScore())) {
                scoringPlayer.setWinCount(scoringPlayer.getWinCount() + 1);

                if (isGamePoint(scoringPlayer.getWinCount())) {
                    setCurrentState(states.END_GAME);
                } else {
                    setScore(scoringPlayer, 0);
                    setScore(opposingPlayer, 0);
                }
            }
        }
    }

    private void setScore(Player player, int score) {
        player.setScore(score);
        player.getTvScore().setText(String.valueOf(player.getScore()));
    }

    private boolean isMatchPoint(int scoringPlayerScore, int opposingPlayerScore) {
        return (scoringPlayerScore >= 11 && (scoringPlayerScore - opposingPlayerScore) >= 2);
    }

    private boolean isGamePoint(int scoringPlayerScore) {
        return (scoringPlayerScore == 2);
    }

}
