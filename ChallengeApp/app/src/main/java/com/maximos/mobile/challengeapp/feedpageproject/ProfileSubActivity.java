package com.maximos.mobile.challengeapp.feedpageproject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.maximos.mobile.challengeapp.R;

import java.util.concurrent.TimeUnit;

/**
 * Created by Henry on 11/9/2014.
 */
public class ProfileSubActivity extends Activity {
    public TextView songName, startTimeField, endTimeField;
//    private MediaPlayer mediaPlayer;
    private VideoView videoView;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private SeekBar seekbar;
    private ImageButton playButton, pauseButton;
    public static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_sub);
        startTimeField = (TextView) findViewById(R.id.textView1);
        seekbar = (SeekBar) findViewById(R.id.seekBar1);
        playButton = (ImageButton) findViewById(R.id.imageButton1);
        pauseButton = (ImageButton) findViewById(R.id.imageButton2);
        videoView =(VideoView)findViewById(R.id.videoView);
        //mediaPlayer = MediaPlayer.create(this, R.raw.one);
        seekbar.setClickable(false);
        pauseButton.setEnabled(false);

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        //Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.one);
        Uri uri=Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // mediaPlayer.stop();
        videoView.stopPlayback();
    }

    public void play(View view) {
        Toast.makeText(getApplicationContext(), "Playing sound",
                Toast.LENGTH_SHORT).show();
        //mediaPlayer.start();
        videoView.start();
        finalTime = videoView.getDuration();
        //finalTime = mediaPlayer.getDuration();
        //startTime = mediaPlayer.getCurrentPosition();
        startTime = videoView.getCurrentPosition();
        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

       /* endTimeField.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) finalTime)))
        );*/
        startTimeField.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime)))
        );
        seekbar.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);
        pauseButton.setEnabled(true);
        playButton.setEnabled(false);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = videoView.getCurrentPosition();
            startTimeField.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) startTime)))
            );
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    public void pause(View view) {
        Toast.makeText(getApplicationContext(), "Pausing sound",
                Toast.LENGTH_SHORT).show();

        videoView.pause();
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
