package com.example.aisparkdevspring2021;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;

public class BaseApp extends Application {
    public static final String CHANNEL_1_ID = "Messages";
    public static final String  CHANNEL_2_ID = "Matches";

    @Override
    public void onCreate()
    {
        super.onCreate();

        creaNotificationChannels();
    }

    private void creaNotificationChannels() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Messages Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setLightColor(Color.rgb(128, 0, 128));
            channel1.setDescription("You got a message!");


            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Matches",
                    NotificationManager.IMPORTANCE_LOW
            );

            channel2.enableLights(true);
            channel2.enableVibration(true);
            channel2.setLightColor(Color.RED);
            channel2.setDescription("You matched with someone!");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
