package com.mobile.remarq;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;


import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Profile extends Fragment
{
    ImageView profileImage;
    TextView studentName;
    TextView studentEmail;
    ViewPager viewPager;
    TabLayout tabLayout;

    Student student;
    HashMap<String,String> courseids;
    Bundle bundle=new Bundle();
    ProfileCourses courses=new ProfileCourses();
    ProfileFollowing following=new ProfileFollowing();
    ProfileFollowers followers=new ProfileFollowers();

    OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    private void setupViewPager(ViewPager viewPager)
    {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new Stub(), "Notes");
        adapter.addFragment(courses, "Courses");
        adapter.addFragment(following, "Following");
        adapter.addFragment(followers, "Followers");
        viewPager.setAdapter(adapter);
    }


    public Profile()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);

        student=(Student)getArguments().getSerializable("student");
        courseids=(HashMap<String, String>)getArguments().getSerializable("courseids");
        bundle.putSerializable("student",student);
        courses.setArguments(bundle);
        following.setArguments(bundle);
        followers.setArguments(bundle);

        profileImage=(ImageView)v.findViewById(R.id.profile_image);
        studentEmail=(TextView)v.findViewById(R.id.studentEmail);
        studentEmail.setText(student.getEmail_id());
        studentName=(TextView)v.findViewById(R.id.studentName);
        studentName.setText(student.getFirst_name()+" "+student.getLast_name());

        viewPager =(ViewPager)v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

        tabLayout =(TabLayout)v.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }
}
