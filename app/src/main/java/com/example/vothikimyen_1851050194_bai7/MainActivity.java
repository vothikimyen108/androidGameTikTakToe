package com.example.vothikimyen_1851050194_bai7;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView scorep1, scorep2, result;
    private Button[]  buttons = new Button[9];
    private  Button resetGame;
    private  int p1_count, p2_count, round_count;
    Boolean activePlayer;
    //p1->0 ,p2-> 1,emty->0
    int[] gameSate = {2,2,2,2,2,2,2,2,2} ;
    //win position
    int[][] winingPosition = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8},{2,4,6}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        scorep1 = (TextView)findViewById( R.id.tv_score1);
        scorep2 = (TextView)findViewById( R.id.tv_score2);
        result = (TextView)findViewById( R.id.tv_result);
        resetGame =(Button) findViewById( R.id.buttonReset);
        for(int i =0;i<buttons.length;i++) {
            String buttonId = "button" + i;
            int resourceID = getResources().getIdentifier( buttonId,"id" ,getPackageName());
            buttons[i] = (Button) findViewById( resourceID );
            buttons[i].setOnClickListener(this);
        }
        p1_count = 0;
        p2_count = 0;
        round_count = 0;
        activePlayer = true;

    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonIDS = v.getResources().getResourceEntryName(v.getId());
        int gameStater = Integer.parseInt(buttonIDS.substring( buttonIDS.length()-1,buttonIDS.length()));
        if(activePlayer){
            ((Button)v).setText("x");
            ((Button)v).setTextColor( Color.parseColor("#990000"));
            gameSate[gameStater] = 0;
        }else {
            ((Button)v).setText("0");
            ((Button)v).setTextColor( Color.parseColor("#cc00cc"));
            gameSate[gameStater] = 1;
        }
        round_count++;
        if(checkWhoWin()){
            if(activePlayer){
                p1_count++;
                updatePlayerScore();
                Toast.makeText( this,"player 1 won",Toast.LENGTH_SHORT).show();
                playAgain();
            }else {
                p2_count++;
                updatePlayerScore();
                Toast.makeText( this,"player 2 won",Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(round_count ==9 ){
            playAgain();
            Toast.makeText( this,"player 2 won",Toast.LENGTH_SHORT).show();

        }else {
            activePlayer = !activePlayer;
        }
        if(p1_count>p2_count){
            result.setText("the winer is p1" );
        }else if(p2_count>p1_count){
            result.setText("the winer is p2" );
        }else {
            result.setText("");
        }

        resetGame.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                p1_count = 0;
                p2_count = 0;
                result.setText("");
                updatePlayerScore();
            }
        } );
    }
    public boolean checkWhoWin() {
        boolean whoWin = false;
        for (int[] winingPositions : winingPosition) {
            if (gameSate[winingPositions[0]] == gameSate[winingPositions[1]] && gameSate[winingPositions[1]] == gameSate[winingPositions[2]] && gameSate[winingPositions[0]] != 2) {
                whoWin = true;
            }

        }
        return whoWin;
    }
    public void  updatePlayerScore() {
        scorep1.setText( Integer.toString( p1_count));
        scorep2.setText( Integer.toString( p2_count));
    }
    public void playAgain(){
        round_count = 0;
        activePlayer = true;
        for(int i =0;i<buttons.length;i++){
            gameSate[i] = 2;
            buttons[i].setText("");
        }
    }
}