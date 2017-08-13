package com.doramram.cruvit;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.doramram.cruvit.Objects.NotificationID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Data Body: " + remoteMessage.getData().get("body"));


//        addNotification(remoteMessage.getNotification().getBody());

//        generateNotification(getApplicationContext(), remoteMessage.getNotification().getBody());

//        sendNotification(remoteMessage.getNotification().getBody());

        sendNotification(remoteMessage.getData().get("body"));




//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.push_notification_layout);
//
//        remoteViews.setImageViewResource(R.id.push_notif_icon,R.mipmap.ic_bird_black);
//
//        Intent intent = new Intent(this,MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
//        notificationBuilder.setContent(remoteViews);
//        notificationBuilder.setContentTitle("Radyo Türkkuşu");
//        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
//
//        notificationBuilder.setAutoCancel(true);
//        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        notificationBuilder.setContentIntent(pendingIntent);
//        remoteViews.setTextViewText(R.id.push_title, "Radyo Türkkuşu");
//        remoteViews.setTextViewText(R.id.push_context, remoteMessage.getNotification().getBody());
//        //notificationBuilder.setLights (ContextCompat.getColor(MainActivity.context, R.color.pushColor), 5000, 5000);
//        notificationManager.notify(0,notificationBuilder.build());


    }

    public void addNotification(String notification){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.cruvit_logo)
                        .setContentTitle("Kruvit")
                        .setContentText(notification);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ChooseProductActivity.class);

        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ChooseProductActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(NotificationID.getID(), mBuilder.build());

    }

    private static void generateNotification(Context context, String message) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, ChooseProductActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent =
                PendingIntent.getActivity(context, (int) System.currentTimeMillis(), notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.cruvit_logo)
                .setContentTitle("Kruvit")
                .setContentText(message)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(999999, notification);

    }

    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, ChooseProductActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Kruvit")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
