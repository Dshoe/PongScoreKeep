package com.devinshoemaker.pongscorekeep;

import android.graphics.Color;
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

    private int gameCount = 0;

    private Button btnPlayerLeft;
    private Button btnPlayerRight;
    private TextView tvCurrentState;

    private Player playerLeft, playerRight, playerOne, playerTwo;

    private enum states {
        NEW_GAME,
        SET_SERVER,
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
        if (state == states.SET_SERVER) {
            playerLeft.getBtn().setText("Set server");
            playerRight.getBtn().setText("Set server");
        } else if (state == states.IN_PROGRESS) {
            playerLeft.getBtn().setText("");
            playerRight.getBtn().setText("");
        }
        currentState = state;
        tvCurrentState.setText(currentState.toString());
    }

    private void resetGame() {
        playerOne = new Player();
        playerTwo = new Player();
        playerLeft = playerOne;
        playerRight = playerTwo;

        playerLeft.setEtName((EditText) findViewById(R.id.etPlayerLeftName));
        playerLeft.setTvScore((TextView) findViewById(R.id.tvPlayerLeftScore));
        playerLeft.setBtn((Button) findViewById(R.id.btnPlayerLeft));
        playerLeft.getBtn().setText("");
        playerLeft.getBtn().setBackgroundColor(Color.LTGRAY);
        setScore(playerLeft, 0);

        playerRight.setEtName((EditText) findViewById(R.id.etPlayerRightName));
        playerRight.setTvScore((TextView) findViewById(R.id.tvPlayerRightScore));
        playerRight.setBtn((Button) findViewById(R.id.btnPlayerRight));
        playerRight.getBtn().setText("");
        playerRight.getBtn().setBackgroundColor(Color.LTGRAY);
        setScore(playerRight, 0);

        gameCount = 1;

        setCurrentState(states.SET_SERVER);
    }

    private boolean isAllowed(states requiredState) {
        return (requiredState.equals(currentState));
    }

    private void updateScore(Player scoringPlayer, Player opposingPlayer) {
        if (isAllowed(states.IN_PROGRESS)) {
            setScore(scoringPlayer, scoringPlayer.getScore() + 1);

            if (isMatchPoint(scoringPlayer.getScore(), opposingPlayer.getScore())) {
                scoringPlayer.setWinCount(scoringPlayer.getWinCount() + 1);
                gameCount++;

                if (isGamePoint(scoringPlayer.getWinCount())) {
                    setCurrentState(states.END_GAME);
                } else {
                    switchSides();
                }
            } else if (((scoringPlayer.getScore() + opposingPlayer.getScore()) % 2) == 0) {
                if (scoringPlayer.isServer())
                    setServer(opposingPlayer, scoringPlayer);
                else
                    setServer(scoringPlayer, opposingPlayer);
            }
        } else if (isAllowed(states.SET_SERVER)) {
            setServer(scoringPlayer, opposingPlayer);
            scoringPlayer.setStartingServer(true);
            setCurrentState(states.IN_PROGRESS);
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

    private void switchSides() {
        String playerLeftName = playerLeft.getEtName().getText().toString();
        String playerRightName = playerRight.getEtName().getText().toString();

        playerLeft.setEtName(null);
        playerLeft.setTvScore(null);
        playerLeft.setBtn(null);
        playerRight.setEtName(null);
        playerRight.setTvScore(null);
        playerRight.setBtn(null);

        playerOne = playerLeft;
        playerTwo = playerRight;
        playerLeft = playerTwo;
        playerRight = playerOne;

        playerLeft.setEtName((EditText) findViewById(R.id.etPlayerLeftName));
        playerLeft.setTvScore((TextView) findViewById(R.id.tvPlayerLeftScore));
        playerLeft.setBtn((Button) findViewById(R.id.btnPlayerLeft));

        playerRight.setEtName((EditText) findViewById(R.id.etPlayerRightName));
        playerRight.setTvScore((TextView) findViewById(R.id.tvPlayerRightScore));
        playerRight.setBtn((Button) findViewById(R.id.btnPlayerRight));

        playerLeft.getEtName().setText(playerRightName);
        playerRight.getEtName().setText(playerLeftName);

        setScore(playerLeft, 0);
        setScore(playerRight, 0);

        if (gameCount == 2) {
            if (playerLeft.isStartingServer())
                setServer(playerRight, playerLeft);
            else
                setServer(playerLeft, playerRight);
        } else {
            if (playerLeft.isStartingServer())
                setServer(playerLeft, playerRight);
            else
                setServer(playerRight, playerLeft);
        }
    }

    private void setServer(Player server, Player nonServer) {
        server.setServer(true);
        server.getBtn().setBackgroundColor(Color.GREEN);
        nonServer.getBtn().setBackgroundColor(Color.LTGRAY);
        nonServer.setServer(false);
    }

}
