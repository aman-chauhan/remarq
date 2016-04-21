package com.mobile.remarq;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Student auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras=getIntent().getExtras();
        auth=(Student)extras.getSerializable("auth");


        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getBaseContext(),"Hello!",Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton addnote = (FloatingActionButton) findViewById(R.id.addnote);
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getBaseContext(),"Hello!",Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView fullname=(TextView)header.findViewById(R.id.auth_fullname);
        TextView emailid=(TextView)header.findViewById(R.id.auth_emailid);
        String name=auth.getFirst_name()+" "+auth.getLast_name();
        fullname.setText(name);
        emailid.setText(auth.getEmail_id());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle=new Bundle();
        bundle.putSerializable("auth",auth);
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        if (id == R.id.timelineicon)
        {
            ft.replace(R.id.frames, new Stub());
        }
        else if (id == R.id.profileicon)
        {
            ft.replace(R.id.frames, new Stub());
        }
        else if (id == R.id.followingstudentsicon)
        {
            FollowStudents obj=new FollowStudents();
            obj.setArguments(bundle);
            ft.replace(R.id.frames, obj);
        }
        else if (id == R.id.followerstudentsicon)
        {
            FollowingStudents obj=new FollowingStudents();
            obj.setArguments(bundle);
            ft.replace(R.id.frames, obj);
        }
        else if (id == R.id.followingcoursesicon)
        {
            FollowCourse obj=new FollowCourse();
            obj.setArguments(bundle);
            ft.replace(R.id.frames, obj);
        }
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
