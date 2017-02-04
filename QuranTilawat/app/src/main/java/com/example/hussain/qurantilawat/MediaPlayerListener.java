package com.example.hussain.qurantilawat;


import android.content.res.AssetManager;

/**
 * Created by Hussain on 2/4/17.
 */

public interface MediaPlayerListener {

    void updateImage(AssetManager assets, int index);

    void resetImage();

    void updatePauseOrResumeButtonTitle();
}
