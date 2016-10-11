package com.ematos.eggtimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar timeSeeker = (SeekBar) findViewById(R.id.timeSeeker);
        final TextView timer = (TextView) findViewById(R.id.timer);

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
}
