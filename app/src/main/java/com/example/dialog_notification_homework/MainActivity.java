package com.example.dialog_notification_homework;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import static android.app.Notification.*;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send = findViewById(R.id.send);
        Button remove = findViewById(R.id.remove);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("send");
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("remove");
            }
        });
    }

    private void showDialog(String operation) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        if(operation.equals("send")) {
            alertDialog.setTitle("SEND");
            alertDialog.setMessage("Send the notification");
            alertDialog.setIcon(R.drawable.add_notif);
        } else if(operation.equals("remove")){
            alertDialog.setTitle("REMOVE");
            alertDialog.setMessage("Remove your message");
            alertDialog.setIcon(R.drawable.remove_notif);
        }
        LinearLayout lp = new LinearLayout(this);
        lp.setOrientation(LinearLayout.VERTICAL);

        final EditText input1 = new EditText(this);
        input1.setHint("your@gmail.com");
        lp.addView(input1);
        final EditText input2 = new EditText(this);
        input2.setHint("12-digit listing key");
        lp.addView(input2);


        alertDialog.setView(lp);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendNotification(input1.getText().toString(), input2.getText().toString());
                        dialog.dismiss();
                    }
                }
        );
        alertDialog.show();
    }

    public void sendNotification(String sTitle, String sText) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder
                = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(true)
                .setDefaults(DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker("Hearty365")
                .setContentTitle(sTitle)
                .setContentText(sText);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

    }

}
