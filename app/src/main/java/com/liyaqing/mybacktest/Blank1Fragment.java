package com.liyaqing.mybacktest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Blank1Fragment extends Fragment {

    public Blank1Fragment() {
        // Required empty public constructor
    }

    public static Blank1Fragment newInstance() {
        Blank1Fragment fragment = new Blank1Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_blank, container, false);

        TextView textView= view.findViewById(R.id.text);
        textView.setText("1");
        Button btn= view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Main1Activity.class));
            }
        });
        return view;
    }
}
