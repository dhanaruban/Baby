package com.dhanaruban.babycasket;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dhanaruban.babycasket.data.TaskContract;
import com.dhanaruban.babycasket.data.UserSettingContract;

/**
 * Created by thenu on 07-03-2018.
 */

public class Settings extends AppCompatActivity {

    RadioButton r1, r2, r3;
    TextView t1;
    Button connect;
    RelativeLayout visible;
    private int mPriority;
    private SharedPreferences sharedPreferences;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        t1 = (TextView) findViewById(R.id.tv);
        r2=(RadioButton) findViewById(R.id.high);
        r3=(RadioButton) findViewById(R.id.low);
        visible = (RelativeLayout) findViewById(R.id.relative);
        connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Not able to connect your host.Enter a valid detail", Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize to highest mPriority by default (mPriority = 1)
        (r1=(RadioButton) findViewById(R.id.normal)).setChecked(true);
        mPriority = 1;
        r1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                t1.setText("Normal  mode  uses  your  android  device  to  monitor.  " +
                        "  Battery  power  will  be  consumed  more  ");
                visible.setVisibility(View.INVISIBLE);
                onPrioritySelected();



            }
        });
        r2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                t1.setText("High  Accuracy  option  allows  you  to  use  your  " +
                        "  CCTV  or  Tron  in  your  home  ");

                visible.setVisibility(View.VISIBLE);
                onPrioritySelected();

            }
        });
        r3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                t1.setText("Low  accuracy  option  will  process  in  the  server  " +
                        "  It  can  be  used  under  stable  internet  connection  otherwise  there  will  be  a  delay  ");
                visible.setVisibility(View.INVISIBLE);
                onPrioritySelected();

            }
        });
    }


    /**
     * onClickAddTask is called when the "ADD" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
//    public void onClickAddTask() {
//        // Not yet implemented
//        // Check if EditText is empty, if not retrieve input and store it in a ContentValues object
//        // If the EditText input is empty -> don't create an entry
//
//
//        // Insert new task data via a ContentResolver
//        // Create new empty ContentValues object
//        ContentValues contentValues = new ContentValues();
//        // Put the task description and selected mPriority into the ContentValues
//
//        contentValues.put(UserSettingContract.TaskEntry.COLUMN_OPTION, mPriority);
//        // Insert the content values via a ContentResolver
//        Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);
//
//        // Display the URI that's returned with a Toast
//        // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
//        if(uri != null) {
//            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
//        }
//
//        // Finish activity (this returns back to MainActivity)
//        finish();
//
//    }


    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
    public void onPrioritySelected() {
        if (((RadioButton) findViewById(R.id.normal)).isChecked()) {
            mPriority = 1;


        } else if (((RadioButton) findViewById(R.id.high )).isChecked()) {
            mPriority = 2;


        } else if (((RadioButton) findViewById(R.id.low)).isChecked()) {
            mPriority = 3;

        }
        sharedPreferences.edit().putInt("radiobutton", mPriority).apply();
    }

}


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.setting);
//        r2 = (RadioButton) findViewById(R.id.normal).setChecked(true);
//
//        r1 = (RadioButton) findViewById(R.id.high);
//
//        mPriority = 1;
//        r3 = (RadioButton) findViewById(R.id.low);
//        t1 = (TextView) findViewById(R.id.tv);
//        visible = (LinearLayout) findViewById(R.id.linear);
//
//        r1.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                t1.setText("High  Accuracy  option  allows  you  to  use  your  " +
//                        "  CCTV  or  Tron  in  your  home  ");
//                visible.setVisibility(View.VISIBLE);
//                connect = (Button) findViewById(R.id.connect);
//                connect.setOnClickListener(new View.OnClickListener() {
//
//                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(), "Not able to connect your host", Toast.LENGTH_SHORT);
//


