package com.example.tictac;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playeronescore, playertwoscore, playerstatus;
    private Button[] buttons = new Button[9];
    private Button resetgame;

    private int playeronescorecount, playertwoscorecount, rountcount;
    boolean activeplayer;

    //p1 => 0
    //p2 => 1
    //empty =>2

    int [] gamestate = {2,2,2,2,2,2,2,2,2,};
    int [] [] winningpositions = {
            {0,1,2},{3,4,5},{6,7,8},//rows
            {0,3,6},{1,4,7},{2,5,8}, //col
            {0,4,8},{2,4,6}//cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playeronescore =(TextView) findViewById(R.id.playeronescore);
        playertwoscore =(TextView) findViewById(R.id.playertwoscore);
        playerstatus = (TextView) findViewById(R.id.playerstatus);

        resetgame =(Button) findViewById(R.id.resetgame);

        for(int i=0; i < buttons.length; i++){
              String buttonID = "btn" + i;
              int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
              buttons[i] =(Button) findViewById(resourceID);
              buttons[i].setOnClickListener(this);

        }
         rountcount = 0;
        playeronescorecount = 0;
        playertwoscorecount =0;
        activeplayer = true;



    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gamestatepointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activeplayer){
            ((Button)v).setText("x");
            ((Button)v).setTextColor(Color.parseColor("#FFC107"));
            gamestate[gamestatepointer] = 0;

        } else {
            ((Button)v).setText("o");
            ((Button)v).setTextColor(Color.parseColor("#FF6200EE"));
            gamestate[gamestatepointer] = 1;
        }
        rountcount ++;

        if(checkwinner()){
            if(activeplayer){
                playeronescorecount++;
                updateplayerscore();
                /*Toast.makeText(this, "player one is winner", Toast.LENGTH_SHORT).show();*/
                playagain();

            }else{
                playertwoscorecount++;
                updateplayerscore();
                /*Toast.makeText(this, "player two is winner", Toast.LENGTH_SHORT).show();*/
                playagain();
            }
        }else if(rountcount ==9){
            playagain();
            Toast.makeText(this, " no winner", Toast.LENGTH_SHORT).show();

        }else{
            activeplayer = !activeplayer;
        }
        if(playeronescorecount > playertwoscorecount){
            playerstatus.setText("player one is winning");

        }else if(playertwoscorecount > playeronescorecount){
            playerstatus.setText("player two is winning");

        }else{
            playerstatus.setText("");
        }

        resetgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playagain();
                playeronescorecount = 0;
                playertwoscorecount = 0;
                playerstatus.setText("");
                updateplayerscore();
            }
        });
    }
    public boolean checkwinner(){
        boolean winnerresult = false;
        for(int[] winningposition : winningpositions){
            if(gamestate[winningposition[0]] == gamestate[winningposition[1]] && gamestate[winningposition[1]]
                    ==gamestate[winningposition[2]] && gamestate[winningposition[0]] !=2){
                winnerresult = true;
            }
        }
        return winnerresult;
    }

    public void updateplayerscore(){
        playeronescore.setText(Integer.toString(playeronescorecount));
        playertwoscore.setText(Integer.toString(playertwoscorecount));
    }

    public void playagain(){
        rountcount = 0;
        activeplayer = true;
        for(int i=0; i<buttons.length; i++){
            gamestate[1] = 2;
            buttons[i].setText("");

        }
   }
}