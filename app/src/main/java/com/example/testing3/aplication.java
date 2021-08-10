package com.example.testing3;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.onesignal.OSMutableNotification;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class aplication extends Application {
    private static final String ONESIGNAL_APP_ID = "0e9c4603-b86e-4e16-bc34-f4110d382527";

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        OneSignal.setNotificationOpenedHandler(new ExampleNotificationOpenedHandler(this));


    }

    class ExampleNotificationOpenedHandler implements OneSignal.OSNotificationOpenedHandler {
        Context context2;

        ExampleNotificationOpenedHandler(Context context) {
            context2 = context;
        }

        @Override
        public void notificationOpened(OSNotificationOpenedResult result) {
            OSNotificationAction.ActionType actionType = result.getAction().getType();
            JSONObject data = result.getNotification().getAdditionalData();
            String customKey;

            if (data != null) {
                customKey = data.optString("customkey", null);
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
                Intent intent1 = new Intent(context2, sendnotification.class);

                intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);

            } else {
                Log.e("i", "error");
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken) {
                Log.i("OneSignalExample", "Button pressed with id: " + result.getAction().getActionId());
                String string = result.getAction().getActionId();

                if (string != null && string.equals("like-button")) {
                    Intent intent = new Intent(context2, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                }




                // The following can be used to open an Activity of your choice.
                // Replace - getApplicationContext() - with any Android Context.


                // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
                //   if you are calling startActivity above.
     /*
        <application ...>
          <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
        </application>
     */
            }
        }

    }


}
