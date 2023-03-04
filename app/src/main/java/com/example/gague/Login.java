package com.example.gague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private static final String FILE_EMAIL = "remember_email";
    EditText pass, email;
    FirebaseAuth mAuth;
    Button signin, admin_login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin=findViewById(R.id.sign_in_button);
        email  = findViewById(R.id.email_id);
        pass = findViewById(R.id.password);
        admin_login= findViewById(R.id.admin_login_button);
        mAuth = FirebaseAuth.getInstance();
        progressDialog =new ProgressDialog(this);


        CheckBox checkBox = (CheckBox) findViewById(R.id.remember_me);
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_EMAIL, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String name =sharedPreferences.getString("svEmail", "");
        String password = sharedPreferences.getString("svPassword","");
        if(sharedPreferences.contains("checked") && sharedPreferences.getBoolean("checked", false) == true){
            checkBox.setChecked(true);
        } else
        {
            checkBox.setChecked(false);
        }
        email.setText(name);
        pass.setText(password);



        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Admin_Login.class));
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = email.getText().toString();
                final String password = pass.getText().toString();
                if(checkBox.isChecked()){
                    editor.putBoolean("checked",true);
                    editor.apply();
                    StoreDataUsingSharedPref(name,password);
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password))
                    {
                        Toast.makeText(Login.this,"Please enter",Toast.LENGTH_LONG).show();
                    }
                    else{
                        userLogin(name,password);
                    }
                }
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this,"Please enter",Toast.LENGTH_LONG).show();
                }else{
                    getSharedPreferences(FILE_EMAIL,MODE_PRIVATE).edit().clear().commit();
                    userLogin(name,password);
                }
            }
        });

    }

    private void StoreDataUsingSharedPref(String name, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(FILE_EMAIL,MODE_PRIVATE).edit();
        editor.putString("svEmail",name);
        editor.putString("svPassword",password);
        editor.apply();

    }

    private void userLogin(String name, String password) {

        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(name,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(Login.this,MainActivity.class));
                            finish();
                        }else{
                            progressDialog.hide();
                            Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}