package com.devinshoemaker.pongscorekeep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnPlayerLeft;
    private Button btnPlayerRight;

    private Player playerLeft, playerRight;

    private enum states {
        NEW_GAME,
        IN_PROGRESS
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
                updateScore(playerLeft);
            }
        });

        btnPlayerRight = (Button) findViewById(R.id.btnPlayerRight);
        btnPlayerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore(playerRight);
            }
        });

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
    }

    private void resetGame() {
        playerLeft = new Player();
        playerRight = new Player();
        playerLeft.setTvScore((TextView) findViewById(R.id.tvPlayerLeftScore));
        playerRight.setTvScore((TextView) findViewById(R.id.tvPlayerRightScore));

        setCurrentState(states.IN_PROGRESS);
    }

    private boolean isAllowed(states requiredState) {
        return (requiredState.equals(currentState));
    }

    private void updateScore(Player scoringPlayer) {
        if (isAllowed(states.IN_PROGRESS)) {
            scoringPlayer.setScore(scoringPlayer.getScore() + 1);
            scoringPlayer.getTvScore().setText(String.valueOf(scoringPlayer.getScore()));
        }
    }

}
