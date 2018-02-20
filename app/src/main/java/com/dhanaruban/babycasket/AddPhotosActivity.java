package com.dhanaruban.babycasket;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AddPhotosActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    Intent imageSelection;
    private Uri filePath;
    private Bitmap bitmap;

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
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                addPhoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
