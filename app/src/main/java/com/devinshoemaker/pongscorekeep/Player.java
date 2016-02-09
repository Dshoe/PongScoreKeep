package com.devinshoemaker.pongscorekeep;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Devin Shoemaker on 1/15/16.
 *
 * Used to managed each player and their score
 */
public class Player {
    private int score = 0;
    private int winCount = 0;
    private boolean server = false;
    private boolean startingServer = false;
    private Button btn;
    private EditText etName;
    private TextView tvScore;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public boolean isStartingServer() {
        return startingServer;
    }

    public void setStartingServer(boolean startingServer) {
        this.startingServer = startingServer;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public EditText getEtName() {
        return etName;
    }

    public void setEtName(EditText etName) {
        this.etName = etName;
    }

    public TextView getTvScore() {
        return tvScore;
    }

    public void setTvScore(TextView tvScore) {
        this.tvScore = tvScore;
    }
}
