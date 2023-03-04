package com.example.gague;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class AdminHomeFragment extends Fragment implements View.OnClickListener{
    public CardView card1, card2, card3, card4;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_admin_home, container, false);
        card1 = home.findViewById(R.id.admin_monitor_click);
        card2 = home.findViewById(R.id.admin_client_detail);
        card3 = home.findViewById(R.id.admin_control_click);
        card4= home.findViewById(R.id.admin_users_details_click);

        card1.setOnClickListener(this::onClick);
        card2.setOnClickListener(this::onClick);
        card3.setOnClickListener(this::onClick);
        card4.setOnClickListener(this::onClick);
        return home;
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId())
        {
            case R.id.admin_monitor_click:
                i=new Intent(getActivity(),AdminMonitor.class);
                startActivity(i);
                break;
            case R.id.admin_client_detail:
                i = new Intent(getActivity(),AdminClientDetials.class);
                startActivity(i);
                break;
            case R.id.admin_control_click:
                i = new Intent(getActivity(),Admin_control.class);
                startActivity(i);
                break;
            case R.id.admin_users_details_click:
                i = new Intent(getActivity(),Admin_User_details.class);
                startActivity(i);
                break;
        }

    }
}