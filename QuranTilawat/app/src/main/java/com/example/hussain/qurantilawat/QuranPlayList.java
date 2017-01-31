package com.example.hussain.qurantilawat;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


import java.io.IOException;



public class QuranPlayList extends AppCompatActivity {

    String mp3FileList = null;

    int fileCounter = 0;

    MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        configureLayout();
    }


    void configureLayout() {

        setContentView(R.layout.activity_quran_play_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        int position = intent.getExtras().getInt("s");

        mp3FileList = String.format("s%d",position);

        //Play button Set up
        Button playBtn = (Button) findViewById(R.id.PlayButton);

        playBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mediaPlayer == null) {

                    start();

                }

            }
        });

        //Pause button Set up
        Button pauseBtn = (Button) findViewById(R.id.PauseButton);

        pauseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pause();
            }
        });

        //Stop button Set up
        Button stopBtn = (Button) findViewById(R.id.StopButton);

        stopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                stop();

            }
        });
    }

    void playAudioFile() throws IOException {

        mediaPlayer = new MediaPlayer();

        AssetManager assetManager = getAssets();

        String mp3Files[] = assetManager.list(mp3FileList);

        if (mp3Files.length > fileCounter) {

            String filename = mp3Files[fileCounter];

            //Below condition is useless. Not sure from where it is reading the file s01. Once it is found then remove the below condtion
            if (filename.equals("s01.mp3")) {
                mediaPlayer.stop();
                fileCounter = 0;
                return;
            }

            String filePath = String.format("%s/%s",mp3FileList,filename);

            AssetFileDescriptor afd = getAssets().openFd(filePath);

            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

            mediaPlayer.prepare();

            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    fileCounter += 1;

                    start();

                }
            });

        } else {

            stop();
        }
    }


    void start() {

        try {

            playAudioFile();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }


    void stop() {

        if ((mediaPlayer != null) && mediaPlayer.isPlaying()) {

            fileCounter = 0;

            mediaPlayer = null;

            mediaPlayer.stop();

        }
    }


    void pause() {

        if ((mediaPlayer != null) && mediaPlayer.isPlaying()) {

            mediaPlayer.pause();

        }
    }


}
