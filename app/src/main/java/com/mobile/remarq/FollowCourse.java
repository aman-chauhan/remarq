package com.mobile.remarq;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowCourse extends Fragment
{
    Student auth;
    private List<Course> courses=new ArrayList<>();
    private CourseAdapter coadapter;
    private String url="http://remarq-central.890m.com/pull_courses_ifollow.php";
    private static String TAG = FollowCourse.class.getSimpleName();

    public FollowCourse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View v=inflater.inflate(R.layout.fragment_follow_course, container, false);

        auth=(Student)getArguments().getSerializable("auth");

        RecyclerView recyclerview = (RecyclerView) v.findViewById(R.id.coursesIfollowlist);
        coadapter=new CourseAdapter(courses);
        RecyclerView.LayoutManager mlayoutmanager=new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mlayoutmanager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                Toast.makeText(v.getContext(),courses.get(position).getCourse_name(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position)
            {
                Toast.makeText(v.getContext(),courses.get(position).getCourse_name(),Toast.LENGTH_SHORT).show();
            }
        }));
        recyclerview.setAdapter(coadapter);

        prepareCourseList();

        return v;
    }

    void prepareCourseList()
    {
        Map<String,String> params=new HashMap<String, String>();
        params.put("studentid",Integer.toString(auth.getStudent_id()));

        CustomRequest request=new CustomRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try{
                            Log.d(TAG,jsonObject.toString());

                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            if (success.equals("yes"))
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
                                coadapter.notifyDataSetChanged();
                            }
                        }
                        catch(JSONException e) {
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

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {
        private GestureDetector gestureDetector;
        private FollowCourse.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FollowCourse.ClickListener clickListener)
        {
            this.clickListener=clickListener;
            gestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
            {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clickListener!=null)
                    {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e))
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
