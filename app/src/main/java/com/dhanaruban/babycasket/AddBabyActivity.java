package com.dhanaruban.babycasket;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dhanaruban.babycasket.data.BabyContract;
import com.dhanaruban.babycasket.data.ObjectContract;
import com.dhanaruban.babycasket.data.TaskContract;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AddBabyActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    Intent imageSelection;
    private Uri filePath;
    private Bitmap bitmap;
    private static final String TAG = AddBabyActivity.class.getSimpleName();

    TextView object;
    ImageButton addPhoto;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);

        object = (TextView) findViewById(R.id.et_baby);
        addPhoto = (ImageButton) findViewById(R.id.ib_addBaby);
        addButton = (Button) findViewById(R.id.b_addobject);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                imageSelection = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageSelection, RESULT_LOAD_IMAGE);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                // Put the task description and selected mPriority into the ContentValues
                contentValues.put(BabyContract.TaskEntry.COLUMN_NAME, object.getText().toString());
                contentValues.put(BabyContract.TaskEntry.COLUMN__BABY_IMAGE, filePath.getEncodedPath());
                // Insert the content values via a ContentResolver
                Uri uri = getContentResolver().insert(BabyContract.TaskEntry.CONTENT_URI, contentValues);


                Intent data = new Intent();
                data.putExtra("relationShip", object.getText().toString());
                data.putExtra("filePath", filePath);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Log.i(TAG,filePath.toString());

                Picasso
                        .with(this)
                        .load(filePath)
                        .fit() // resizes the image to these dimensions (in pixel). does not respect aspect ratio
                        .into(addPhoto);


        }
    }

}
