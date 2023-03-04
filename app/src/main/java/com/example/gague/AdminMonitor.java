package com.example.gague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.example.gague.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminMonitor extends AppCompatActivity {
    private ImageButton back;
    private ArcGauge ar1, ar2, ar3, ar4;
    private String temp, hum, temp1, hum1;
    private double temp_val, hum_val, temp_val1, hum_val1;
    private String username1, username2;
    private TextView usern1, usern2;
    private ImageButton ib;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String user_id, user1, user2;
    private DatabaseReference reference, reference1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_monitor);
        usern1 = findViewById(R.id.user_name_1);
        usern2 =findViewById(R.id.user_name_2);
        back=findViewById(R.id.back_img);
        ar1 = findViewById(R.id.temp_arc);
        ar2 = findViewById(R.id.hum_arc);
        ar3 = findViewById(R.id.temp_arc1);
        ar4 = findViewById(R.id.hum_arc1);
        ib= findViewById(R.id.back_button);
        ar1.setMinValue(0);
        ar1.setMaxValue(100);
        ar2.setMinValue(0);
        ar2.setMaxValue(100);
        ar3.setMinValue(0);
        ar3.setMaxValue(100);
        ar4.setMinValue(0);
        ar4.setMaxValue(100);
        user= FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Admin");
        reference.child(user_id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user1 = snapshot.child(user_id).child("user1").getValue().toString();
                user2 = snapshot.child(user_id).child("user2").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference1 =FirebaseDatabase.getInstance().getReference("User");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username1=snapshot.child(user1).child("name").getValue().toString();
                temp = snapshot.child(user1).child("temp").getValue().toString();
                ar1.setValue(Double.parseDouble(temp));
                hum = snapshot.child(user1).child("hum").getValue().toString();
                ar2.setValue(Double.parseDouble(hum));
                temp_val = Double.parseDouble(temp);
                hum_val = Double.parseDouble(hum);
                username2=snapshot.child(user2).child("name").getValue().toString();
                temp1 = snapshot.child(user2).child("temp").getValue().toString();
                ar3.setValue(Double.parseDouble(temp1));
                hum1 = snapshot.child(user2).child("hum").getValue().toString();
                ar4.setValue(Double.parseDouble(hum1));
                temp_val1 = Double.parseDouble(temp1);
                hum_val1 = Double.parseDouble(hum1);
                if(temp_val>30 || hum_val>10)
                {
                    Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(AdminMonitor.this,"My Notification")
                                    .setSmallIcon(R.drawable. alert_icon )
                                    .setVibrate( new long []{ 1000 , 1000 , 1000 , 1000 , 1000 })
                                    .setContentTitle( "Alert!" )
                                    .setSound(alarmSound)
                                    .setContentText( "Alert! There is a problem in Cold Storage Cabinate" ) ;
                    NotificationManager mNotificationManager = (NotificationManager)
                            getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(( int ) System. currentTimeMillis (), mBuilder.build());
                }
                if(temp_val1>30 || hum_val1>10)
                {
                    Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(AdminMonitor.this,"My Notification")
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

            }
        });
    }
}