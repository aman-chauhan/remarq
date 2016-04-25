package com.mobile.remarq;


import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment
{
    ViewPager viewPager;
    TabLayout tabLayout;
    String searchterm;
    Student auth;


    private List<Course> courses=new ArrayList<>();
    private List<Student> students=new ArrayList<>();
    private String url="http://remarq-central.890m.com/pull_search.php";
    private static String TAG = SearchFragment.class.getSimpleName();


    public SearchFragment() {

    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        Bundle bundle=new Bundle();
        bundle.putSerializable("auth",auth);

        SearchCourse sc=new SearchCourse();
        SerializedList obj=new SerializedList();
        obj.setList(courses);
        bundle.putSerializable("courselist",obj);
        sc.setArguments(bundle);
        adapter.addFragment(sc, "Courses");


        bundle.remove("courselist");
        SearchStudent ss=new SearchStudent();
        SerializedList obj2=new SerializedList();
        obj2.setList(students);
        bundle.putSerializable("studentlist",obj2);
        ss.setArguments(bundle);
        adapter.addFragment(ss, "Students");

        viewPager.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_search, container, false);

        searchterm=getArguments().getString("searchterm");
        auth=(Student)getArguments().getSerializable("auth");
        fillList();


        viewPager =(ViewPager)v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout =(TabLayout)v.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);



        return v;
    }

    public void fillList()
    {
        Map<String,String> params=new HashMap<String, String>();
        params.put("searchterm",searchterm);

        CustomRequest request=new CustomRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Log.d(TAG, jsonObject.toString());

                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            String status = jsonObject.getString("status");
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            if (success.equals("Yes"))
                            {
                                if(status.equals("B")||status.equals("D"))
                                {
                                    JSONArray array = jsonObject.getJSONArray("courses");
                                    Course course;
                                    for (int i = 0; i < array.length(); ++i)
                                    {
                                        JSONObject obj = (JSONObject) array.get(i);
                                        course=new Course();
                                        course.setCourse_id(obj.getInt("courseid"));
                                        course.setCourse_code(obj.getString("coursecode"));
                                        course.setCourse_name(obj.getString("coursename"));
                                        courses.add(course);
                                        Log.d(TAG,course.getCourse_name());
                                    }
                                }

                                if(status.equals("C")||status.equals("D"))
                                {
                                    JSONArray array2 = jsonObject.getJSONArray("students");
                                    Student student;
                                    for (int i = 0; i < array2.length(); ++i) {
                                        JSONObject obj = (JSONObject) array2.get(i);
                                        student = new Student();
                                        student.setFirst_name(obj.getString("firstname"));
                                        student.setLast_name(obj.getString("lastname"));
                                        student.setEmail_id(obj.getString("emailid"));
                                        student.setStudent_id(obj.getInt("studentid"));
                                        students.add(student);
                                        Log.d(TAG,student.getFirst_name());
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        Toast.makeText(getActivity(),
                                volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
