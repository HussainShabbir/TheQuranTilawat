package com.example.hussain.qurantilawat;

import android.media.MediaPlayer;


/**
 * Created by Hussain on 2/4/17.
 */
public class QuranGlobalClass {
    private static QuranGlobalClass ourInstance = new QuranGlobalClass();

    public static QuranGlobalClass getInstance() {
        return ourInstance;
    }

    MediaPlayer mediaPlayer = null;

    boolean isStartAyat = false;

    boolean isPause = false;

    int pausePostion = 0;

    int fileCounter = 0;
    
}
