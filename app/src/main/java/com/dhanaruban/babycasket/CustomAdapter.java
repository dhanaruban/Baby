package com.dhanaruban.babycasket;

/**
 * Created by thenu on 21-02-2018.
 */
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dhanaruban.babycasket.data.TaskContract;
import com.dhanaruban.babycasket.utility.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;


public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.TaskViewHolder>{
    private Cursor mCursor;
    private Context mContext;
    private static String TAG = CustomActivity.class.getName();
    private ContentResolver mContentresolver;
    public CustomAdapter(Context mContext,ContentResolver mContentresolver) {
        this.mContext = mContext;
        this.mContentresolver = mContentresolver;
    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {


        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(TaskContract.TaskEntry._ID);
        int descriptionIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_RELATIONSHIP);
        int image = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_IMAGE);
        int upload = mCursor.getColumnIndex(TaskContract.TaskEntry.UPLOAD_STATUS);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String description = mCursor.getString(descriptionIndex);
        String url =  mCursor.getString(image); //"content://media" + mCursor.getString(image);
        String isUploaded = mCursor.getString(upload);
        Log.i(TAG,url);


        //Set values
        holder.itemView.setTag(id);
        holder.relationshipView.setText(description);
        Picasso.with(mContext).load(url).transform(new CircleTransform())
                .into(holder.imageView);
        if(getItemCount()!=0 && isUploaded.equals("false")) {
            uploadData(id,url);
        }



        // Programmatically set the text and color for the priority TextView


    }
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public void uploadData(int id,String filename) {


        // Initialize AWSMobileClient if not initialized upon the app startup.
        AWSMobileClient.getInstance().initialize(mContext).execute();

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(mContext)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();
        Log.d(TAG,transferUtility.toString());

        File file = new File(filename);
//        InputStream is=file.getInputStream();
//        s3client.putObject(new PutObjectRequest(bucketName, keyName,is,new ObjectMetadata()));
        TransferObserver uploadObserver =
                transferUtility.upload("uploads/thenu/custom/"+file.getName(), file);

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Log.d(TAG,"upload successfully local");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(TaskContract.TaskEntry.UPLOAD_STATUS,"true");

//                    ContentValues contentValues = new ContentValues();
//                    // Put the task description and selected mPriority into the ContentValues
//                    contentValues.put(TaskContract.TaskEntry.COLUMN_RELATIONSHIP, relation.getText().toString());
//
//                    contentValues.put(TaskContract.TaskEntry.COLUMN_IMAGE, filePath);
//                    contentValues.put(TaskContract.TaskEntry.UPLOAD_STATUS,"false");
//                    // Insert the content values via a ContentResolver
                    String arrayId[] = {Integer.toString(id)};
                    int uri = mContentresolver.update(TaskContract.TaskEntry.CONTENT_URI, contentValues,null,arrayId);

                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d(TAG, "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.d(TAG,"upload fail local" + id);
                ex.printStackTrace();
                // Handle errors
            }

        });

        // If your upload does not trigger the onStateChanged method inside your
        // TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            Log.d(TAG,"upload successfully");

            // Handle a completed upload.
        }
    }
    class TaskViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView relationshipView;
        ImageView imageView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            relationshipView = (TextView) itemView.findViewById(R.id.relationship);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}


