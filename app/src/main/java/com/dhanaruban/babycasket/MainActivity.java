package com.dhanaruban.babycasket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

public class MainActivity extends AppCompatActivity {

    public static PinpointManager pinpointManager;
//    private OnFragmentInteractionListener mListener;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
//    public static String ALARM_TO_SET = "ALRMTOSEND";
    Button mIsmonitorOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AWSMobileClient.getInstance().initialize(this).execute();
        if (pinpointManager == null) {
        PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                getApplicationContext(),
                AWSMobileClient.getInstance().getCredentialsProvider(),
                AWSMobileClient.getInstance().getConfiguration());

        pinpointManager = new PinpointManager(pinpointConfig);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String deviceToken =
                                InstanceID.getInstance(MainActivity.this).getToken(
                                        "837289399750",
                                        GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                        Log.e("NotError", deviceToken);
                        pinpointManager.getNotificationClient()
                                .registerGCMDeviceToken(deviceToken);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        // Start a session with Pinpoint
        pinpointManager.getSessionClient().startSession();

        // Stop the session and submit the default app started event
        pinpointManager.getSessionClient().stopSession();
        pinpointManager.getAnalyticsClient().submitEvents();

        setContentView(R.layout.activity_main);

        mIsmonitorOn = (Button) findViewById(R.id.monitorButton);

        mIsmonitorOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPhoto = new Intent(getApplicationContext(),DetectorActivity.class);
                startActivity(addPhoto);
            }
        } );



        logEvent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * Interface with the Activity.
//     */
//    public interface OnFragmentInteractionListener {
//        void onButtonPress(boolean signIn);
//        void showPopup(String title, String content);
//    }
//    public void onButtonPressed() {
//        if (mListener != null) {
//            mListener.onButtonPress(SIGN_OUT);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), CustomActivity.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_logout) {
            //this.finish();
            SignInUI signin = (SignInUI) AWSMobileClient.getInstance()
                    .getClient(this, SignInUI.class);
            signin.login(this, AuthenticatorActivity.class).execute();

//           Intent loginscreen = new Intent(this, AuthenticatorActivity.class);
//            loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(loginscreen);
            finish();
        } else if (id == R.id.action_harmful) {
            Intent intent = new Intent(getApplicationContext(), Harmful.class);
            startActivity(intent);
       }else if (id == R.id.action_baby) {
            Intent intent = new Intent(getApplicationContext(), BabyActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_camera) {
            Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
            startActivity(intent);
        } else if (id == R.id.set) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void logEvent() {
        pinpointManager.getSessionClient().startSession();
        final AnalyticsEvent event =
                pinpointManager.getAnalyticsClient().createEvent("AppStart")
                        .withAttribute("DemoAttribute1", "DemoAttributeValue1")
                        .withMetric("DemoMetric1", Math.random());

        pinpointManager.getAnalyticsClient().recordEvent(event);
        pinpointManager.getSessionClient().stopSession();
        pinpointManager.getAnalyticsClient().submitEvents();
    }
    @Override
    protected void onPause() {
        super.onPause();

        // unregister notification receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register notification receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver,
                new IntentFilter(PushMessage.ACTION_PUSH_NOTIFICATION));
    }

    private final BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Received notification from local broadcast. Display it in a dialog.");

            Bundle data = intent.getBundleExtra(PushMessage.INTENT_SNS_NOTIFICATION_DATA);
            String message = PushMessage.getMessage(data);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Push notification")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    };


}
