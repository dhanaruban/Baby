package com.dhanaruban.babycasket;

/**
 * Created by thenu on 21-02-2018.
 */
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhanaruban.babycasket.data.ObjectContract;
import com.dhanaruban.babycasket.utility.CircleTransform;
import com.squareup.picasso.Picasso;


public class HarmfulAdapter  extends RecyclerView.Adapter<HarmfulAdapter.TaskViewHolder>{
    private Cursor mCursor;
    private Context mContext;
    private static String TAG = Harmful.class.getName();

    public HarmfulAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.object_layout, parent, false);

        return new TaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(ObjectContract.TaskEntry._ID);
        int descriptionIndex = mCursor.getColumnIndex(ObjectContract.TaskEntry.COLUMN_OBJECT_NAME);
        int image = mCursor.getColumnIndex(ObjectContract.TaskEntry.COLUMN_OBJECT_IMAGE);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String description = mCursor.getString(descriptionIndex);
        String url =  "content://media" + mCursor.getString(image);

        Log.i(TAG,url);


        //Set values
        holder.itemView.setTag(id);
        holder.objectname.setText(description);
        Picasso.with(mContext).load(url).transform(new CircleTransform())
                .into(holder.objectimageView);


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
    class TaskViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView objectname;
        ImageView objectimageView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            objectname = (TextView) itemView.findViewById(R.id.objectName);
            objectimageView = (ImageView) itemView.findViewById(R.id.objectView);
        }
    }
}


