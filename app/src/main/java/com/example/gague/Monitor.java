package com.example.gague;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Monitor extends AppCompatActivity {
   private ArcGauge ar1, ar2,ar3;
   private String temp, hum,mq3;
    private double temp_val, hum_val, mq3_val;
   private ImageButton ib;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String user_id;
    private TextView mq_status;
    private DatabaseReference reference;
    private final static String default_notification_channel_id = "default" ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        ar1 = findViewById(R.id.temp_arc);
        ar2 = findViewById(R.id.hum_arc);
        ar3 = findViewById(R.id.mq3_arc);
        ib= findViewById(R.id.back_button);
        ar1.setMinValue(0);
        ar1.setMaxValue(100);
        ar2.setMinValue(0);
        ar2.setMaxValue(100);
        mq_status = findViewById(R.id.mq3_status);
        user= FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("User");
        reference.child(user_id);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                temp = dataSnapshot.child(user_id).child("temp").getValue().toString();
                ar1.setValue(Double.parseDouble(temp));
                hum = dataSnapshot.child(user_id).child("hum").getValue().toString();
                mq3 = dataSnapshot.child(user_id).child("mq3").getValue().toString();
                ar3.setValue(Double.parseDouble(mq3));
                ar2.setValue(Double.parseDouble(hum));
                temp_val = Double.parseDouble(temp);
                hum_val = Double.parseDouble(hum);
                mq3_val = Double.parseDouble(mq3);
                if(mq3_val<20){
                    mq_status.setText("Normal");
                } else if (mq3_val>20) {
                    mq_status.setText("Alert");
                    Toast.makeText(Monitor.this,"Ther is a alert in the Storage", Toast.LENGTH_LONG).show();
                }

                if(temp_val>30 || hum_val>10 || mq3_val>20)
                {
                    Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(Monitor.this,"My Notification")
                                    .setSmallIcon(R.drawable. alert_icon )
                                    .setVibrate( new long []{ 1000 , 1000 , 1000 , 1000 , 1000 })
                                    .setContentTitle( "Alert!" )
                                    .setSound(alarmSound)
                                    .setContentText( "Alert! There is a problem in Cold Storage Cabinate" ) ;
                    NotificationManager mNotificationManager = (NotificationManager)
                            getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(( int ) System. currentTimeMillis (), mBuilder.build());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Monitor.this,"Error in loading data",Toast.LENGTH_LONG);
                ar1.setValue(Double.parseDouble("Error Value"));
                ar2.setValue(Double.parseDouble("Error Value"));

            }
        });
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
    }
}