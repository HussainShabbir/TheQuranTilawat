package com.example.hussain.qurantilawat;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;


/**
 * Created by Hussain on 2/4/17.
 */
public class MediaPlayerGlobal {

    private static MediaPlayerGlobal ourInstance = new MediaPlayerGlobal();

    private MediaPlayerListener mediaPlayerListener;

    public static MediaPlayerGlobal getInstance() {
        return ourInstance;
    }

    MediaPlayer mediaPlayer = null;

    boolean isStartAyat = false;

    boolean isBismillahAyat = false;

    boolean isPause = false;

    int pausePostion = 0;

    int fileCounter = 0;

    String mp3FilesFolderName = null;

    AssetManager assetManager = null;



    void start() throws IOException {

        try {
            if (!isBismillahAyat) {

                String mp3Files[] = assetManager.list("startAyat");

                if (mp3Files.length > 1) {

                    String filePath = null;

                    if (!isStartAyat) {

                        filePath = String.format("%s/%s", "startAyat", mp3Files[0]);

                        isStartAyat = true;

                    } else if (!isBismillahAyat) {

                        filePath = String.format("%s/%s", "startAyat", mp3Files[1]);

                        isBismillahAyat = true;
                    }

                    AssetFileDescriptor afd = assetManager.openFd(filePath);

                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

                    mediaPlayer.prepare();

                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {

                            play();
                        }
                    });
                }

            } else {

                mediaPlayerListener.updateImage(assetManager,fileCounter);

                String mp3Files[] = assetManager.list(mp3FilesFolderName);

                if (mp3Files.length > fileCounter) {

                    String filename = mp3Files[fileCounter];

                    //Below condition is useless. Not sure from where it is reading the file s01. Once it is found then remove the below condtion
                    if (filename.equals("s01.mp3")) {
                        mediaPlayer.stop();
                        fileCounter = 0;
                        return;
                    }

                    String filePath = String.format("%s/%s",mp3FilesFolderName,filename);

                    AssetFileDescriptor afd = assetManager.openFd(filePath);

                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

                    mediaPlayer.prepare();

                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {

                            fileCounter += 1;

                            play();
                        }
                    });

                } else {

                    stop();
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    void play() {

        try {
            if (assetManager != null && mp3FilesFolderName != null) {

                mediaPlayer = new MediaPlayer();

                start();
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    void pause() {

        boolean isMediaplayerPauseOrResume = false;

        if ((mediaPlayer != null) && mediaPlayer.isPlaying()) {

            pausePostion = mediaPlayer.getCurrentPosition();

            mediaPlayer.pause();

            isPause = true;

            isMediaplayerPauseOrResume = true;

        } else if (mediaPlayer !=null) {

            mediaPlayer.seekTo(pausePostion);

            isPause = false;

            mediaPlayer.start();

            isMediaplayerPauseOrResume = true;
        }

        if (isMediaplayerPauseOrResume) {

            mediaPlayerListener.updatePauseOrResumeButtonTitle();
        }

    }


    void stop() {

        if (mediaPlayer != null) {

            mediaPlayer.stop();

            fileCounter = 0;

            mediaPlayer = null;

            isBismillahAyat = false;

            mediaPlayerListener.resetImage();

        }
    }


    void updateAssest(AssetManager assetManager) {

        this.assetManager = assetManager;
    }

    void updateMp3FolderName(String folderName) {

        mp3FilesFolderName = folderName;
    }

    void updateMediaPlayerListener(QuranPlayList listener) {

        mediaPlayerListener = listener;
    }
}
