package com.example.connect3game;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    // 0 - empty, 1 - yellow, 2 - red
    int activePlayer = 1;

    int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    boolean gameActive = true;

    public void dropIn(View view) {

        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        // getting the clicked view
        ImageView coin = (ImageView) view;

        // getting the position of clicked view
        int clickedPosition = Integer.parseInt(coin.getTag().toString());

        // before adding the coin we check if coin exist or not and game is still on.
        if (gameState[clickedPosition] == 0 && gameActive) {
            // stating the game
            gameState[clickedPosition] = activePlayer;

            // animating the coin drop
            coin.setTranslationY(-1500);

            // choosing the coin depended in player
            if (activePlayer == 1) {
                coin.setImageResource(R.drawable.yellow);
                activePlayer = 2;
            } else {
                coin.setImageResource(R.drawable.red);
                activePlayer = 1;
            }

            coin.animate().translationYBy(1500).rotation(180).setDuration(500);

            // finding the winner
            for (int[] winingPosition : winningPositions) {
                if (
                        gameState[winingPosition[0]] == gameState[winingPosition[1]] &&
                                gameState[winingPosition[1]] == gameState[winingPosition[2]] &&
                                gameState[winingPosition[0]] != 0
                ) {
                    // someone won!
                    gameActive = false;

                    String winner = "";

                    if (activePlayer == 2) {
                        winner = "Yellow";
                    } else {
                        winner = "Red";
                    }

                    winnerTextView.setText(winner + " won!");
                    winnerTextView.setVisibility(View.VISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);

                }
            }

            // checking if is draw
            if (gameActive) {
                int steps = 0;
                for (int i=0; i < gameState.length; i++) {
                    if (gameState[i] != 0) {
                        steps++;
                    }
                }
                if (steps == 9) {
                    winnerTextView.setText("It was draw!");
                    winnerTextView.setVisibility(View.VISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);
                    gameActive = false;
                }
            }

        }
    }

    public void playAgain(View view) {
        // setting back to default
        activePlayer = 1;
        gameActive = true;

        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        // removing resource of all imageViews
        GridLayout gridLayout = (GridLayout) findViewById(R.id.girdLayout);

        for (int i=0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
        }

        // resetting gameState
        for (int i=0; i < gameState.length; i++) {
            gameState[i] = 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
