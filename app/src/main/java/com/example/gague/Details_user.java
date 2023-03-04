package com.example.gague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class Details_user extends AppCompatActivity {
    private ImageButton ib;
    private TextView sn, un,st,cn,sd,ed,s;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String user_id, cname,uname,sdate,edate,stype,sub,snum;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_user);
        user= FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();
        ib = findViewById(R.id.userd_back_button);
        sn=findViewById(R.id.user_storage_number);
        cn = findViewById(R.id.user_crop);
        un = findViewById(R.id.user_name);
        s = findViewById(R.id.user_sub);
        sd=findViewById(R.id.user_start);
        ed=findViewById(R.id.user_end);
        st = findViewById(R.id.user_type);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    uname = snapshot.child(user_id).child("name").getValue().toString();
                     cname= snapshot.child(user_id).child("cropname").getValue().toString();
                    sdate = snapshot.child(user_id).child("startdate").getValue().toString();
                    edate = snapshot.child(user_id).child("endstart").getValue().toString();
                    sub = snapshot.child(user_id).child("subscription").getValue().toString();
                    snum = snapshot.child(user_id).child("storagenumber").getValue().toString();
                    stype = snapshot.child(user_id).child("storagetype").getValue().toString();
                    un.setText(uname);
                    cn.setText(cname);
                    sn.setText(snum);
                    s.setText(sub);
                    ed.setText(edate);
                    sd.setText(sdate);
                    st.setText(stype);
                } else {
                    Toast.makeText(Details_user.this,"Loading Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}