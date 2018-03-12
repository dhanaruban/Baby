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

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.models.nosql.TasksDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.dhanaruban.babycasket.data.TaskContract;
import com.dhanaruban.babycasket.utility.CircleTransform;
import com.dhanaruban.babycasket.utility.Util;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URISyntaxException;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

public class AddPhotosActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    Intent imageSelection;
    private String filePath;
    private Uri localPath;
    private Bitmap bitmap;
    DynamoDBMapper dynamoDBMapper;
    private int mPriority;
    private static final String TAG = AddPhotosActivity.class.getSimpleName();

    TextView relation;
    ImageButton addPhoto;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);

        relation = (TextView) findViewById(R.id.et_relationship);
        addPhoto = (ImageButton) findViewById(R.id.ib_addPhoto);
        addButton = (Button) findViewById(R.id.b_addAction);

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
                contentValues.put(TaskContract.TaskEntry.COLUMN_RELATIONSHIP, relation.getText().toString());

                contentValues.put(TaskContract.TaskEntry.COLUMN_IMAGE, filePath);
                contentValues.put(TaskContract.TaskEntry.UPLOAD_STATUS,"false");
                contentValues.put(TaskContract.TaskEntry.TASK_LOCAL_PATH, localPath.getEncodedPath() );
                // Insert the content values via a ContentResolver
                Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

                Intent data = new Intent();
                data.putExtra("relationShip", relation.getText().toString());
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
            //filePath = data.getData();

            try {
                filePath = Util.getPath(this, getContentResolver(), data.getData());
                localPath = data.getData();
                Picasso.with(this).load(localPath).fit().into(addPhoto);
                Log.i(TAG,filePath);
                Log.i(TAG,localPath.toString());

            }  catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

}
