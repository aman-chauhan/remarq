package com.mobile.remarq;

import android.app.FragmentTransaction;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Login extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction f=getFragmentManager().beginTransaction();
        f.replace(R.id.loginframes, new Splash());
        f.commit();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                FragmentTransaction ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.loginframes, new LoginDetails());
                ft.commit();
            }
        },3000);
    }
}

