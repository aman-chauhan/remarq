package com.mobile.remarq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView check;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras=getIntent().getExtras();
        Student auth=(Student)extras.getSerializable("auth");

        check=(TextView)findViewById(R.id.check);
        StringBuilder build=new StringBuilder();
        build.append(auth.getEmail_id());
        build.append("\n");
        build.append(auth.getFirst_name());
        build.append("\n");
        build.append(auth.getLast_name());
        build.append("\n");
        build.append(auth.getStudent_id());
        check.setText(build.toString());
    }
}