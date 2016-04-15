package com.mobile.remarq;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginDetails extends Fragment
{
    public Button b1;
    public Button b2;

    public LoginDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_login_details, container, false);
        b1=(Button)v.findViewById(R.id.signin);
        b2=(Button)v.findViewById(R.id.signup);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction f=getFragmentManager().beginTransaction();
                f.replace(R.id.loginframes,new SignUpDetails());
                f.commit();
            }
        });
        return v;
    }
}

