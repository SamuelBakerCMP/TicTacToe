package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    //New 2d array of 3x3
    private Button[][] buttons = new Button[3][3];
    //when game starts, its player 1's turn
    private boolean player1Turn = true;
    //tally of round number
    private int roundCount;
    //score tallys
    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1= findViewById(R.id.text_view_p1);
        textViewPlayer2= findViewById(R.id.text_view_p2);
        //nested loop to get ID's dynamically
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                //gets ids of the button through loop
                String buttonID= "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                //references all the buttons without having to get them individually
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetGame();
            }

        });
    }

    @Override
    public void onClick(View v)
    {
        //if the button clicked contains an empty string
        //if this is not the case this shows there is already an x or o present
        if (!((Button) v).getText().toString().equals(""))
        {
            return;
        }

        if (player1Turn)
        {
            ((Button) v).setText("X");
        }
        else
        {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin())
        {
            if (player1Turn)
            {
                player1Wins();
            }
            else
            {
                player2Wins();
            }
        }
        else if (roundCount == 9)
        {
            draw();
        }
        else
        {
            //switch turns
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin()
    {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++)
        {
            //compares three rows next to each other, checks if they have a value
            //and makes sure the fields are not empty
            if (field[i][0].equals(field[i][1])
            && field[i][0].equals(field[i][2])
            && !field[i][0].equals(""))
            {
                return true;
            }
        }

        for (int i = 0; i < 3; i++)
        {
            //compares three columns next to each other, checks if they have a value
            //and makes sure the fields are not empty
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals(""))
            {
                return true;
            }
        }
        //checks the diagonals
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals(""))
        {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals(""))
        {
            return true;
        }
        //there is no winner
        return false;
    }

    private void player1Wins()
    {
        player1Points++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins()
    {
        player2Points++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw()
    {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText()
    {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                //set buttons to be empty
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame()
    {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //save info if orientation change
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
