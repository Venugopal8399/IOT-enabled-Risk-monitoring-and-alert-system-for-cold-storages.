package com.example.gague;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener{
    public CardView card1, card2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        card1 = home.findViewById(R.id.monitor_click);
        card2 = home.findViewById(R.id.details_click);

        card1.setOnClickListener(this::onClick);
        card2.setOnClickListener(this);
        return home;
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId())
        {
            case R.id.monitor_click:
                i=new Intent(getActivity(),Monitor.class);
                startActivity(i);
                break;
            case R.id.details_click:
                i = new Intent(getActivity(),Details_user.class);
                startActivity(i);
                break;
        }

    }
}