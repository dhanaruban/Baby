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

import com.dhanaruban.babycasket.data.ObjectContract;
import com.dhanaruban.babycasket.data.TaskContract;
import com.dhanaruban.babycasket.utility.Util;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URISyntaxException;

public class AddObjectActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    Intent imageSelection;
    String filePath;
    private Bitmap bitmap;
    private static final String TAG = AddObjectActivity.class.getSimpleName();

    TextView object;
    ImageButton addPhoto;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);

        object = (TextView) findViewById(R.id.et_object);
        addPhoto = (ImageButton) findViewById(R.id.ib_addObject);
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
                contentValues.put(ObjectContract.TaskEntry.COLUMN_OBJECT_NAME, object.getText().toString());

                contentValues.put(ObjectContract.TaskEntry.COLUMN_OBJECT_IMAGE, filePath);
                contentValues.put(ObjectContract.TaskEntry.UPLOAD_OBJECT_STATUS,"false");
                // Insert the content values via a ContentResolver
                Uri uri = getContentResolver().insert(ObjectContract.TaskEntry.CONTENT_URI, contentValues);

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
            try {
                filePath = Util.getPath(this, getContentResolver(), data.getData());
                Picasso.with(this).load(filePath).fit().into(addPhoto);
                Log.i(TAG, filePath);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

}
