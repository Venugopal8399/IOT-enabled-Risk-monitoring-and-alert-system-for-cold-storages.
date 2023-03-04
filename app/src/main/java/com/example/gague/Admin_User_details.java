package com.example.gague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_User_details extends AppCompatActivity {
    private ImageButton close_button;
    private TextView an, ab, al,ac,acd,and;
    private String user_id,aname, abranch, alocation, achecked, achekeddate, anextdate;
    private DatabaseReference reference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_details);
        close_button = findViewById(R.id.userd_back_button);

        user= FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();
        an = findViewById(R.id.admin_name);
        ab = findViewById(R.id.admin_branch);
        al = findViewById(R.id.admin_location);
        ac = findViewById(R.id.admin_check);
        acd = findViewById(R.id.admin_check_date);
        and = findViewById(R.id.admin_next_date);

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference("Admin");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aname = snapshot.child(user_id).child("name").getValue().toString();
                abranch = snapshot.child(user_id).child("branch").getValue().toString();
                alocation = snapshot.child(user_id).child("location").getValue().toString();
                achecked = snapshot.child(user_id).child("Storagecheck").getValue().toString();
                achekeddate =snapshot.child(user_id).child("checkdate").getValue().toString();
                anextdate = snapshot.child(user_id).child("nextcheckeddate").getValue().toString();
                an.setText(aname);
                ab.setText(abranch);
                al.setText(alocation);
                ac.setText(achecked);
                acd.setText(achekeddate);
                and.setText(anextdate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}