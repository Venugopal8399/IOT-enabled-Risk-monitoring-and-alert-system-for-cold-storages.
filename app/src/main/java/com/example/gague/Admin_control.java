package com.example.gague;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_control extends AppCompatActivity {
    private ImageButton imgb;
    private SeekBar seekbar;
    private ArcGauge arc1;
    private ToggleButton tg1, tg2;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String admin_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);
        user= FirebaseAuth.getInstance().getCurrentUser();
        admin_user_id = user.getUid();
        imgb=findViewById(R.id.back_img);
        seekbar = findViewById(R.id.seek_bar);
        arc1 = findViewById(R.id.fanspeed);
        tg1 = findViewById(R.id.fan);
        tg2 = findViewById(R.id.bulb);
        seekbar.setMax(250);
        arc1.setMaxValue(250);
        reference = FirebaseDatabase.getInstance().getReference("Admin");
        imgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                arc1.setValue(progress);
                reference.child(admin_user_id).child("fanstatus").setValue(progress*2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tg1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    setfanhigh();
                } else
                {
                    setfanoff();
                }
            }
            private void setfanoff() {
                reference.child(admin_user_id).child("fan").setValue("0");
            }

            private void setfanhigh() {
                reference.child(admin_user_id).child("fan").setValue("1");
            }
        });
        tg2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    setbulbhigh();
                } else
                {
                    setbulboff();
                }
            }
            private void setbulboff() {
                reference.child(admin_user_id).child("bulb").setValue("0");
            }

            private void setbulbhigh() {
                reference.child(admin_user_id).child("bulb").setValue("1");
            }
        });
    }
}