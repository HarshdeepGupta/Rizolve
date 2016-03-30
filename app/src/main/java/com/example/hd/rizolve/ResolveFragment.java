package com.example.hd.rizolve;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ResolveFragment extends DialogFragment {

    boolean proceed;


    public ResolveFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ResolveFragment newInstance() {
        ResolveFragment fragment = new ResolveFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proceed = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_resolve, container, false);
        getDialog().setTitle("Simple Dialog");
        proceed = false;
        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                proceed = false;
                dismiss();
            }
        });

        Button accept = (Button) rootView.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                proceed = true;
                dismiss();
            }
        });
        return rootView;

    }


}
