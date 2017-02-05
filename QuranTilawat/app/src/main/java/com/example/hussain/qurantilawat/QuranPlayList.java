package com.example.hussain.qurantilawat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;


public class QuranPlayList extends AppCompatActivity implements MediaPlayerListener {

    AssetManager assetManager = null;

    ImageView imageView = null;

    Button pauseOrResumeBtn = null;

    Intent intent = null;

    final MediaPlayerGlobal mediaPlayer = MediaPlayerGlobal.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        configureLayout();


    }


    void configureLayout() {

        setContentView(R.layout.activity_quran_play_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (intent == null) {

            intent = getIntent();
        }

        mediaPlayer.updateAssest(getAssets());

        mediaPlayer.updateMediaPlayerListener(this);

        configureMediaPlayerButton();

        if (mediaPlayer.assetManager != null && mediaPlayer.mediaPlayer != null) {

            updateImage(mediaPlayer.assetManager, mediaPlayer.fileCounter);

        }

        displayTitle((mediaPlayer.selectedSurat != null && mediaPlayer.mediaPlayer != null) ? mediaPlayer.selectedSurat : "Surat");

        showBackButtonArrow();

    }


    void showBackButtonArrow() {

        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    void configureMediaPlayerButton() {

        imageView = (ImageView) findViewById(R.id.AyatImageView);

        //Play button Set up
        Button playBtn = (Button) findViewById(R.id.PlayButton);

        playBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mediaPlayer.mediaPlayer == null) {

                    int position = intent.getExtras().getInt("s");

                    mediaPlayer.updateMp3FolderName(String.format("s%d",position));

                    mediaPlayer.play();

                   mediaPlayer.updateTitle(intent.getExtras().getString("suratName"));

                    displayTitle(mediaPlayer.selectedSurat);

                }
            }
        });

        //Pause button Set up
        pauseOrResumeBtn = (Button) findViewById(R.id.PauseButton);

        pauseOrResumeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mediaPlayer.pause();

            }
        });

        //Stop button Set up
        Button stopBtn = (Button) findViewById(R.id.StopButton);

        stopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mediaPlayer.stop();
            }
        });
    }


    @Override
    public void updateImage(AssetManager assets, int index) {

        try {

            if (imageView == null) {

                imageView = (ImageView) findViewById(R.id.AyatImageView);
            }

            imageView.setImageDrawable(null);

            String imagesFileFormat = String.format("Images/%s",mediaPlayer.mp3FilesFolderName);

            String imageList[] = assets.list(imagesFileFormat);

            if (imageList.length > index) {

                String imagesFilePath = String.format("%s/%s",imagesFileFormat,imageList[index]);

                InputStream inputStream = assets.open(imagesFilePath);

                Drawable drawable = Drawable.createFromStream(inputStream, null);

                imageView.setImageDrawable(drawable);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void resetImage() {

        imageView.setImageDrawable(null);

    }

    @Override
    public void updatePauseOrResumeButtonTitle() {

        if (mediaPlayer.isPause) {

            pauseOrResumeBtn.setText(R.string.Mediplayer_Resume_Button);

        } else {

            pauseOrResumeBtn.setText(R.string.Mediplayer_Pause_Button);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    void displayTitle(String suratName) {

        setTitle(suratName);

    }
}