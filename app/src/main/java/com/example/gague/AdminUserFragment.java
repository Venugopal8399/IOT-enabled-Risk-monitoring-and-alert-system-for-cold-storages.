package com.example.gague;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class AdminUserFragment extends Fragment {
    private LinearLayout aboutinfo;
    private ImageView user_img;
    private FirebaseUser user;
    private String user_id;
    private DatabaseReference reference;
    private TextView user_name,user_email,user_phone,storage_number;
    private String sname, sphone, semail,img_url,store_num;
    private Button signout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_user, container, false);
        user= FirebaseAuth.getInstance().getCurrentUser();
        user_img = root.findViewById(R.id.admin_use_img);
        user_name = root.findViewById(R.id.admin_use_name);
        user_email = root.findViewById(R.id.admin_use_email);
        user_phone = root.findViewById(R.id.admin_use_phone);
        signout =root.findViewById(R.id.admin_signout);
        aboutinfo =root.findViewById(R.id.admin_about_info_layout);
        storage_number = root.findViewById(R.id.admin_storage_unit);
        user_id = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Admin");
        reference.child(user_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sname = snapshot.child(user_id).child("name").getValue().toString();
                user_name.setText(sname);
                semail = snapshot.child(user_id).child("email").getValue().toString();
                user_email.setText(semail);
                sphone = snapshot.child(user_id).child("phone").getValue().toString();
                user_phone.setText(sphone);
                img_url = snapshot.child(user_id).child("user_img").getValue().toString();
                Picasso.get().load(img_url).rotate(270f)
                        .transform(new CropCircleTransformation()).into(user_img);
                store_num = snapshot.child(user_id).child("storagenumber").getValue().toString();
                storage_number.setText(store_num);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        aboutinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),About_Section.class));

            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
        return root;
    }
}