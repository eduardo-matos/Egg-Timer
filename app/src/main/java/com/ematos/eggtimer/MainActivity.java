package com.ematos.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    protected boolean running = false;
    protected CountDownTimer countDown;

    protected SeekBar timeSeeker;
    protected TextView timer;
    protected MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSeeker = (SeekBar) findViewById(R.id.timeSeeker);
        timer = (TextView) findViewById(R.id.timer);
        player = MediaPlayer.create(this, R.raw.horn);

        timeSeeker.setMax(600);
        timeSeeker.setProgress(30);
        updateTimer(timer, timeSeeker.getProgress());

        timeSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(timer, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    final protected void updateTimer(TextView timer, int progress) {
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);

        timer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public void controlTimer(View view) {
        final Button button = (Button) findViewById(R.id.controller);

        if(running) {
            button.setText("Start");
            running = false;
            countDown.cancel();
            player.stop();
        } else {
            button.setText("Stop");
            running = true;

            countDown = new CountDownTimer(timeSeeker.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int progress = (int)millisUntilFinished/1000;
                    Log.i("Millis", progress + "");
                    updateTimer(timer, progress);
                    timeSeeker.setProgress(progress);
                }

                @Override
                public void onFinish() {
                    updateTimer(timer, 0);
                    timeSeeker.setProgress(0);
                    button.setText("Start");
                    running = false;

                    player.start();
                }
            };
            countDown.start();
        }
    }
}
