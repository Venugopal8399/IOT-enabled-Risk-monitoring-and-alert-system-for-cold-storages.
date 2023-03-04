package com.example.gague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gague.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_Login extends AppCompatActivity {

    EditText admin_pass, admin_email;
    FirebaseAuth admin_mAuth;
    Button admin_signin, bck;
    ProgressDialog admin_progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        admin_signin=findViewById(R.id.admin_sign_in_button);
        admin_email  = findViewById(R.id.admin_email_id);
        admin_pass = findViewById(R.id.admin_password);
        admin_mAuth = FirebaseAuth.getInstance();
        admin_progressDialog =new ProgressDialog(this);
        bck = findViewById(R.id.back_sign);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        admin_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = admin_email.getText().toString();
                final String password = admin_pass.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)){
                    Toast.makeText(Admin_Login.this,"Please enter",Toast.LENGTH_LONG).show();
                }else{
                    userLogin(name,password);
                }
            }
        });

    }

    private void userLogin(String name, String password) {admin_progressDialog.setTitle("Login");
        admin_progressDialog.setMessage("Please wait...");
        admin_progressDialog.show();
        admin_mAuth.signInWithEmailAndPassword(name,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            admin_progressDialog.dismiss();
                            Toast.makeText(Admin_Login.this,"Login Successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Admin_Login.this,admin_main.class));
                            finish();
                        }else{
                            admin_progressDialog.hide();
                            Toast.makeText(Admin_Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                        }
                    }

                });

    }
}