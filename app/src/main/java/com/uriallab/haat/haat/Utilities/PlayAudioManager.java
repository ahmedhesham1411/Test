package com.uriallab.haat.haat.Utilities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.ObservableInt;

import com.uriallab.haat.haat.R;
import com.warkiz.widget.IndicatorSeekBar;

/**
 * Created by Mahmoud on 02/10/2019.
 */
public class PlayAudioManager {

    public PlayAudioManager() {
    }

    private MediaPlayer mediaPlayer;

    private IndicatorSeekBar indicatorSeekBarAActive;

    private ImageView imageViewActive;

    private ObservableInt mediaStatus;

    public void playAudio(final Activity context, final String url, final IndicatorSeekBar indicatorSeekBar, final ImageView imageView, ObservableInt mediaStatus) {

        indicatorSeekBarAActive = indicatorSeekBar;
        imageViewActive = imageView;
        this.mediaStatus = mediaStatus;

        try {
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(context, Uri.parse(url));
            Log.e("seeking", url + " ");

            mediaPlayer.setOnCompletionListener(mp -> {
                mediaStatus.set(0);
                indicatorSeekBarAActive.setProgress(0);
                killMediaPlayer();
            });
            mediaStatus.set(1);
            mediaPlayer.start();
            imageViewActive.setBackgroundResource(R.drawable.pause_icon);
            Log.e("mCurrentPosition", mediaPlayer.getDuration() + " Duration"); // TODO: 7/20/2020  /1000
            indicatorSeekBarAActive.setMax(mediaPlayer.getDuration());
            indicatorSeekBarAActive.setProgress(0);
            final Handler mHandler = new Handler();
            context.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition();
                        Log.e("mCurrentPosition", mCurrentPosition + "");
                        indicatorSeekBarAActive.setProgress(mCurrentPosition + 1);
                    }
                    mHandler.postDelayed(this, 10);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaStatus.set(2);
                mediaPlayer.pause();
                imageViewActive.setBackgroundResource(R.drawable.play_icon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void resumeMediaPlayer(Activity activity, int startFrom) {
        if (mediaPlayer != null) {
            try {
                Log.e("mCurrentPosition", startFrom + " startFrom");
                mediaPlayer.seekTo(startFrom);
                mediaPlayer.start();
                mediaStatus.set(1);
                final Handler mHandler = new Handler();
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            int mCurrentPosition = mediaPlayer.getCurrentPosition();
                            Log.e("mCurrentPosition", mCurrentPosition + "");
                            indicatorSeekBarAActive.setProgress(mCurrentPosition + 1);
                        }
                        mHandler.postDelayed(this, 10);
                    }
                });
                imageViewActive.setBackgroundResource(R.drawable.pause_icon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaStatus.set(0);
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
                if (imageViewActive != null)
                    imageViewActive.setBackgroundResource(R.drawable.play_icon);
                indicatorSeekBarAActive.setProgress(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}