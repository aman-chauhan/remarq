package com.mobile.remarq;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SignUpDetails extends Fragment
{
    Button b1;

    public SignUpDetails()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_sign_up_details, container, false);
        b1=(Button)v.findViewById(R.id.register);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction f=getFragmentManager().beginTransaction();
                f.replace(R.id.loginframes, new LoginDetails());
                f.commit();
            }
        });
        return v;
    }

}
