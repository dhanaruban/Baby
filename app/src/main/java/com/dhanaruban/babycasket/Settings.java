package com.dhanaruban.babycasket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by thenu on 07-03-2018.
 */

public class Settings extends AppCompatActivity {

   RadioButton r1,r2,r3;
   TextView t1;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        r1 = (RadioButton) findViewById(R.id.high);
        r2 = (RadioButton) findViewById(R.id.normal);
        r3 = (RadioButton) findViewById(R.id.low);
        t1 = (TextView) findViewById(R.id.tv);
        r1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
               t1.setText("High  Accuracy  option  allows  you  to  use  your  " +
                       "  CCTV  or  Tron  in  your  home  ");
            }

        });
                r2.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View v) {
                        t1.setText("Normal  mode  uses  your  android  device  to  monitor.  " +
                                "  Battery  power  will  be  consumed  more  ");
                    }

                });
                        r3.setOnClickListener(new View.OnClickListener(){

                            public void onClick(View v) {
                                t1.setText("Low  accuracy  option  will  process  in  the  server  " +
                                        "  It  can  be  used  under  stable  internet  connection  otherwise  there  will  be  a  delay  ");
                            }

                        });
    }
}