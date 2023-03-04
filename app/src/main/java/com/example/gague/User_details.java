package com.example.gague;


import com.google.firebase.database.DatabaseReference;

public class User_details {
    DatabaseReference myRef;
    public String email, name,phone;
    public User_details(){
    }
    public User_details(String email,String name,String phone){

        this.email=email;
        this.name=name;
        this.phone=phone;
    }
}
