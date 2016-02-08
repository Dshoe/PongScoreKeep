package com.devinshoemaker.pongscorekeep;

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
