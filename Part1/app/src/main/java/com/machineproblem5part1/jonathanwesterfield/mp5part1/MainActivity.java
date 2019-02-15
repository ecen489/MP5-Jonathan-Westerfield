package com.machineproblem5part1.jonathanwesterfield.mp5part1;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.view.View.*;
import android.content.Context.*;
import java.net.URI;
import java.util.*;
import java.io.*;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity
{

    RadioButton dancQueen;
    RadioButton dontStopMe;
    RadioButton bunDem;
    ImageView imgView;
    // int songChoice;
    MediaPlayer songPlayer;
    TextView songTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    public void initializeViews()
    {
        this.imgView = (ImageView) findViewById(R.id.imageView);
        this.songTitle = (TextView) findViewById(R.id.songTitleView);
        this.songPlayer = MediaPlayer.create(this, R.raw.dancing_queen);

        this.dancQueen = (RadioButton) findViewById(R.id.dancQnBtn);
        this.dontStopMe = (RadioButton) findViewById(R.id.dntStopMeNowBtn);
        this.bunDem = (RadioButton) findViewById(R.id.bunDemBtn);

        OnClickListener dancQueenBtnClk = new OnClickListener ()
        {
            public void onClick(View v)
            {
                imgView.setImageResource(R.drawable.abba_gold);
                songTitle.setText("Dancing Queen");
                // songChoice = R.raw.dancing_queen;
                switchSong(R.raw.dancing_queen);
            }
        };
        this.dancQueen.setOnClickListener(dancQueenBtnClk);

        OnClickListener dontStopMeNowBtnClk = new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                imgView.setImageResource(R.drawable.dont_stop_me_now);
                songTitle.setText("Don't Stop Me Now");
                // songChoice = R.raw.dont_stop_me_now;
                switchSong(R.raw.dont_stop_me_now);
            }
        };
        this.dontStopMe.setOnClickListener(dontStopMeNowBtnClk);

        OnClickListener bunDemBtnClk = new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                imgView.setImageResource(R.drawable.make_it_bun_dem);
                songTitle.setText("Make It Bun Dem");
                // songChoice = R.raw.make_it_bun_dem;
                switchSong(R.raw.make_it_bun_dem);
            }
        };
        this.bunDem.setOnClickListener(bunDemBtnClk);
    }


    public void playSong(View view)
    {
        if(this.songPlayer != null && !this.songPlayer.isPlaying())
        {
            this.songPlayer.start();
        }
    }

    public void pauseSong(View view)
    {
        if (this.songPlayer != null && this.songPlayer.isPlaying())
            this.songPlayer.pause();
    }

    public void switchSong(int songChoice)
    {
        if(this.songPlayer != null)
        {
            this.songPlayer.reset();
            // this.songPlayer.release();
        }

        this.songPlayer = MediaPlayer.create(this, songChoice);
    }
}
