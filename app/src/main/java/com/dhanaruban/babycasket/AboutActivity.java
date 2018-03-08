package com.dhanaruban.babycasket;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by thenu on 21-02-2018.
 */

public class AboutActivity extends AppCompatActivity {

    Button bt;

    @Override
        public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        bt = (Button) findViewById(R.id.alarm);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.danger);
        bt.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                if(! mp.isPlaying()){
                    mp.start();
                    bt.setText("Stop Alarm");
                }else {
                    mp.stop();
                    bt.setText("Start Alarm");
                }

            }
        });


        }
    }

