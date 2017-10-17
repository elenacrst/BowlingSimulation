package com.example.elena.bowlingsimulation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elena.bowlingsimulation.adapters.ScoreAdapter;
import com.example.elena.bowlingsimulation.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    private RecyclerView mFrameNumberRecyclerView;
    private RecyclerView mPlayerScoreRecyclerView, mComputerScoreRecyclerView;
    private TextView mScoreTextView;

    private boolean isPlayerTurn, isFrameComplete=true, hasGameEnded;
    private Button mButtonTurn;
    private int shots;
    private Player player, computer;
    private ImageView pinsImageView;
    private List<Integer> mFrameNumber = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        findViews();
        decideTurn();
        initializeFrameNumbers();
        initializeRecyclerViews();

        player = new Player();
        computer = new Player();

        pinsImageView.setImageResource(R.drawable.pins10);
    }

    private void initializeRecyclerViews(){

        LinearLayoutManager layoutManagerPlayer = new LinearLayoutManager(this);
        mPlayerScoreRecyclerView.setLayoutManager(layoutManagerPlayer);
        LinearLayoutManager layoutManagerComputer = new LinearLayoutManager(this);
        mComputerScoreRecyclerView.setLayoutManager(layoutManagerComputer);
        LinearLayoutManager layoutManagerFrame = new LinearLayoutManager(this);
        mFrameNumberRecyclerView.setLayoutManager(layoutManagerFrame);
    }

    private void findViews(){
        mFrameNumberRecyclerView = (RecyclerView)findViewById(R.id.frame_recycler_view);
        mPlayerScoreRecyclerView = (RecyclerView)findViewById(R.id.player_recycler_view);
        mComputerScoreRecyclerView = (RecyclerView)findViewById(R.id.computer_recycler_view);
        mScoreTextView = (TextView)findViewById(R.id.result_text_view);
        mButtonTurn = (Button) findViewById(R.id.turn_button);
        pinsImageView = (ImageView) findViewById(R.id.pins_left_image_view);
    }

    public void initializeFrameNumbers(){
        ScoreAdapter mFrameNumberAdapter = new ScoreAdapter();
        for(int i=1; i<=10; i++){
            mFrameNumber.add(i);
        }
        mFrameNumberAdapter.setData(mFrameNumber);
        mFrameNumberRecyclerView.setAdapter(mFrameNumberAdapter);
    }

    private void prepareRecyclerViews(){


        ScoreAdapter mPlayerScoreAdapter = new ScoreAdapter();
        mPlayerScoreAdapter.setData(player.scores);
        mPlayerScoreRecyclerView.setAdapter(mPlayerScoreAdapter);
        ScoreAdapter mComputerScoreAdapter = new ScoreAdapter();
        mComputerScoreAdapter.setData(computer.scores);
        mComputerScoreRecyclerView.setAdapter(mComputerScoreAdapter);
    }

    private void decideTurn(){
        int value = (int)(Math.random()*10);
        if(value<5){
            //player turn
            isPlayerTurn=true;
            mButtonTurn.setText(getString(R.string.your_turn_text));

        }else{
            //computer turn
            isPlayerTurn = false;
            mButtonTurn.setText(getString(R.string.computer_turn_text));
        }
    }

    public void onTurnClick(View view) {

        if(hasGameEnded){//restart game
            decideTurn();
            prepareRecyclerViews();
            mScoreTextView.setText("");
            player.resetData();
            computer.resetData();
            hasGameEnded=false;
            return;
        }

        //generate random number of knocked down pins
        int pins;
        if(isFrameComplete){
            pins = (int) (Math.random()* 10);
        }else{
            if (isPlayerTurn)
                pins = (int) (Math.random()* (10-player.points.get(player.shotsCount-1)));
            else
                pins = (int) (Math.random()* (10- computer.points.get(computer.shotsCount-1) ));
        }

        //show image with the corresponding number of pins
        decidePinsImage(pins);

        if(isPlayerTurn)
            player.points.add(player.shotsCount, pins);
        else computer.points.add(computer.shotsCount, pins);

        //display result
        if(pins == 10){
            mScoreTextView.setText("Strike\n(10 pins)");
        }else if(isPlayerTurn && player.shotsCount>0 && player.points.get(player.shotsCount-1)+pins==10
                && !isFrameComplete || !isPlayerTurn && computer.shotsCount>0 &&
                computer.points.get(computer.shotsCount-1)+pins== 10 && !isFrameComplete){
            mScoreTextView.setText("Spare\n("+player.points.get(player.shotsCount-1)+"+"+pins+" pins)");
        }else
            mScoreTextView.setText(getString(R.string.text_pins,pins));

        //recalculate points
        if(isPlayerTurn){
            recalculatePoints(player,pins);

        }else {
                recalculatePoints(computer,pins);
        }

        if(isPlayerTurn)
            player.shotsCount++;
        else
            computer.shotsCount++;

        //did any player finish
       if(hasPlayerEndedTurns(player) && hasPlayerEndedTurns(computer)){
           hasGameEnded = true;
           mButtonTurn.setText(getString(R.string.reset));
           displayWinner();
       }else if(hasPlayerEndedTurns(player)){
           isPlayerTurn = false;
       }else if(hasPlayerEndedTurns(computer)){
           isPlayerTurn = true;
       }else if(isFrameComplete)
            decideTurn();
        prepareRecyclerViews();

    }

    private void displayWinner() {
        int playerTotal=0, computerTotal=0;
        for(int i=0; i<10; i++){
            playerTotal+= player.scores.get(i);
            computerTotal += computer.scores.get(i);
        }
        if (playerTotal>computerTotal){
            mScoreTextView.setText("You won!\nYour total: "+playerTotal+"\nComputer's score: "+computerTotal);
        }else if(computerTotal>playerTotal){
            mScoreTextView.setText("Computer won!\nYour score: "+playerTotal+"\nComputer's score: "+computerTotal);
        }else
            mScoreTextView.setText(getString(R.string.text_score,playerTotal));
    }

    private boolean hasPlayerEndedTurns(Player mPlayer){
        int aux;
        try{
            aux = mPlayer.scores.get(mPlayer.frameCount);
        }catch (Exception e){
            aux = 0;
            e.printStackTrace();
        }
        return mPlayer.shotsCount == 20 && aux < 10 || mPlayer.shotsCount == 21;
    }

    private void decidePinsImage(int pins) {
        switch (pins){
            case 0:
                pinsImageView.setImageResource(R.drawable.pins10);
                break;
            case 1:
                pinsImageView.setImageResource(R.drawable.pins9);
                break;
            case 2:
                pinsImageView.setImageResource(R.drawable.pins8);
                break;
            case 3:
                pinsImageView.setImageResource(R.drawable.pins7);
                break;
            case 4:
                pinsImageView.setImageResource(R.drawable.pins6);
                break;
            case 5:
                pinsImageView.setImageResource(R.drawable.pins5);
                break;
            case 6:
                pinsImageView.setImageResource(R.drawable.pins4);
                break;
            case 7:
                pinsImageView.setImageResource(R.drawable.pins3);
                break;
            case 8:
                pinsImageView.setImageResource(R.drawable.pins2);
                break;
            case 9:
                pinsImageView.setImageResource(R.drawable.pins1);
                break;
            default:
                pinsImageView.setImageResource(R.drawable.pins0);
                break;

        }
    }

    private void recalculatePoints(Player mplayer,int pins) {
        int aux;
        //strikes
        if (mplayer.shotsCount > 1 && mplayer.points.get(mplayer.shotsCount - 2) == 10) {
            aux = mplayer.scores.get(mplayer.frameCount - 1);
            mplayer.scores.set(mplayer.frameCount - 1, aux + pins);
        }
        if (mplayer.shotsCount > 0 && mplayer.points.get(mplayer.shotsCount - 1) == 10) {
            aux = mplayer.scores.get(mplayer.frameCount - 1);
            mplayer.scores.set(mplayer.frameCount - 1, aux + pins);
        }

        //spare
        if (mplayer.shotsCount > 1 &&
                mplayer.points.get(mplayer.shotsCount - 2) + mplayer.points.get(mplayer.shotsCount - 1) == 10
                && isFrameComplete) {
            aux = mplayer.scores.get(mplayer.frameCount - 1);
            mplayer.scores.set(mplayer.frameCount - 1, aux + pins);
        }

        //setup current frame score
        if (isFrameComplete) {
            mplayer.scores.add(mplayer.frameCount, pins);
        } else{
            aux = mplayer.scores.get(mplayer.frameCount);
            mplayer.scores.set(mplayer.frameCount, aux + pins);
        }

        shots++;

        //end frame if it's the case
        if(pins==10 || shots==2&& mplayer.frameCount<20){
            mplayer.frameCount++;
            shots= 0;
            isFrameComplete = true;

        }else isFrameComplete = false;
    }
}
