package com.mobile.remarq;

import android.app.AlertDialog;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Student auth;
    HashMap<String, String> courseids=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras=getIntent().getExtras();
        auth=(Student)extras.getSerializable("auth");
        courseids=(HashMap<String, String>)extras.getSerializable("courseids");

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search);


        FloatingActionButton addnote = (FloatingActionButton) findViewById(R.id.addnote);
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Bundle bundle=new Bundle();
                bundle.putSerializable("auth",auth);
                bundle.putSerializable("courseids",courseids);
                AddNote obj=new AddNote();
                obj.setArguments(bundle);
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frames,obj);
                ft.commit();
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

    public void onSearchClicked(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_search_dialog, null);
        builder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.searchText);

        builder.setMessage("Search").setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton){
                String searchquery = edt.getText().toString();
                Bundle bundle=new Bundle();
                bundle.putString("searchterm",searchquery);
                bundle.putSerializable("auth",auth);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction();
                SearchFragment s=new SearchFragment();
                s.setArguments(bundle);
                ft.replace(R.id.frames,s,"Search");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });
            builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //pass
                }
            });
        AlertDialog b =builder.create();
        b.show();
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
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        if (id == R.id.timelineicon)
        {
            bundle.putSerializable("courseids",courseids);
            TimelineFragment obj=new TimelineFragment();
            obj.setArguments(bundle);
            ft.replace(R.id.frames, obj);
        }
        else if (id == R.id.profileicon)
        {
            Bundle profilebundle=new Bundle();
            profilebundle.putSerializable("student",auth);
            profilebundle.putSerializable("auth",auth);
            Profile obj=new Profile();
            obj.setArguments(profilebundle);
            ft.replace(R.id.frames, obj);
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
