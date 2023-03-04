package com.example.gague;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class UserFragment extends Fragment {
    private LinearLayout aboutinfo;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String user_id;
    private DatabaseReference reference;
    private ImageView user_img;
    private TextView user_name,user_email,user_phone,storage_number;
    private String sname, sphone, semail,img_url,store_num;
    private Button signout;
    private ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user, container, false);
        progressDialog =  new ProgressDialog(getActivity());
        user= FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();
        user_img = root.findViewById(R.id.use_img);
        user_email = root.findViewById(R.id.use_email);
        user_phone =root.findViewById(R.id.use_phone);
        storage_number=root.findViewById(R.id.storage_unit_no);
        user_name =root.findViewById(R.id.use_name);
        signout =root.findViewById(R.id.signout);
        aboutinfo =root.findViewById(R.id.about_info_layout);
        storage_number = root.findViewById(R.id.storage_unit_no);
        reference = FirebaseDatabase.getInstance().getReference("User");

        reference.child(user_id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.setTitle("Login");
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                if (snapshot != null) {
                    sname = snapshot.child(user_id).child("name").getValue().toString();
                    sphone = snapshot.child(user_id).child("phone").getValue().toString();
                    semail = snapshot.child(user_id).child("email").getValue().toString();
                    img_url = snapshot.child(user_id).child("user_img").getValue().toString();
                    store_num = snapshot.child(user_id).child("storagenumber").getValue().toString();
                    user_name.setText(sname);
                    user_email.setText(semail);
                    user_phone.setText(sphone);
                    storage_number.setText(store_num);
                    Picasso.get().load(img_url).rotate(270f)
                            .transform(new CropCircleTransformation()).into(user_img);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(),"Loading Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        aboutinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intnt = new Intent(getActivity(),About_Section.class);
                startActivity(intnt);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent inent = new Intent(getActivity(), Login.class);
                startActivity(inent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        return root;
    }
}