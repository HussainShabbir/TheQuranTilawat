package com.example.hussain.qurantilawat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;


public class QuranPlayList extends AppCompatActivity {

    String mp3FileList = null;

    Boolean isBismillahAyat = false;

    AssetManager assetManager = null;

    final QuranGlobalClass globalClass = QuranGlobalClass.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        configureLayout();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ImageView imageView = (ImageView)findViewById(R.id.AyatImageView);

        imageView.refreshDrawableState();

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

                if (globalClass.mediaPlayer == null) {

                    start();

                }

            }
        });

        //Pause button Set up
        Button pauseBtn = getPauseOrResumeButton();

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

    void loadImagesFromAsset(AssetManager assets, int index) {

        try {

            String imagesFileFormat = String.format("Images/%s",mp3FileList);

            String imageList[] = assets.list(imagesFileFormat);

            if (imageList.length > index) {

                String imagesFilePath = String.format("%s/%s",imagesFileFormat,imageList[index]);

                InputStream inputStream = assets.open(imagesFilePath);

                ImageView imageView = (ImageView) findViewById(R.id.AyatImageView);

                Drawable drawable = Drawable.createFromStream(inputStream, null);

                imageView.setImageDrawable(drawable);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    void playStartAudioFiles() throws IOException {

        globalClass.mediaPlayer = new MediaPlayer();

        if (assetManager == null) {

            assetManager = getAssets();
        }

        String mp3Files[] = assetManager.list("startAyat");

        if (mp3Files.length > 1) {

            String filePath = null;

            if (!QuranGlobalClass.getInstance().isStartAyat) {

                filePath = String.format("%s/%s","startAyat",mp3Files[0]);

                globalClass.isStartAyat = true;

            } else if (!isBismillahAyat) {

                filePath = String.format("%s/%s","startAyat",mp3Files[1]);

                isBismillahAyat = true;
            }

            AssetFileDescriptor afd = getAssets().openFd(filePath);

            globalClass.mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

            globalClass.mediaPlayer.prepare();

            globalClass.mediaPlayer.start();

            globalClass.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    start();
                }
            });
        }
    }

    void playAudioFile() throws IOException {

        globalClass.mediaPlayer = new MediaPlayer();

        updateimageAndAssetsFolderPath();

        String mp3Files[] = assetManager.list(mp3FileList);

        if (mp3Files.length > globalClass.fileCounter) {

            String filename = mp3Files[globalClass.fileCounter];

            //Below condition is useless. Not sure from where it is reading the file s01. Once it is found then remove the below condtion
            if (filename.equals("s01.mp3")) {
                globalClass.mediaPlayer.stop();
                globalClass.fileCounter = 0;
                return;
            }

            String filePath = String.format("%s/%s",mp3FileList,filename);

            AssetFileDescriptor afd = getAssets().openFd(filePath);

            globalClass.mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

            globalClass.mediaPlayer.prepare();

            globalClass.mediaPlayer.start();

            globalClass.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    globalClass.fileCounter += 1;

                    start();

                }
            });

        } else {

            stop();
        }
    }


    void start() {

        try {

            if (!isBismillahAyat) {

                playStartAudioFiles();

            } else {

                playAudioFile();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }
    }


    void stop() {

        if (globalClass.mediaPlayer != null) {

            globalClass.mediaPlayer.stop();

            globalClass.fileCounter = 0;

            globalClass.mediaPlayer = null;

            isBismillahAyat = false;

            ImageView imageView = (ImageView) findViewById(R.id.AyatImageView);

            imageView.setImageDrawable(null);

        }
    }


    void pause() {

        Button pauseorResumeBtn = getPauseOrResumeButton();

        if ((globalClass.mediaPlayer != null) && globalClass.mediaPlayer.isPlaying()) {

            globalClass.pausePostion = globalClass.mediaPlayer.getCurrentPosition();

            globalClass.mediaPlayer.pause();

            pauseorResumeBtn.setText(R.string.Mediplayer_Resume_Button);

        } else if (globalClass.mediaPlayer !=null) {

            globalClass.mediaPlayer.seekTo(globalClass.pausePostion);

            pauseorResumeBtn.setText(R.string.Mediplayer_Pause_Button);

            globalClass.mediaPlayer.start();

        }

    }

    void updateimageAndAssetsFolderPath() {

        if (assetManager == null) {
            assetManager = getAssets();
        }

        loadImagesFromAsset(assetManager,globalClass.fileCounter);
    }

    Button getPauseOrResumeButton() {

        return (Button) findViewById(R.id.PauseButton);
    }


}