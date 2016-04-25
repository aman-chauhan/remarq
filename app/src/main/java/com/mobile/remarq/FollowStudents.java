package com.mobile.remarq;


import android.content.Context;
//import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
//import com.android.volley.VolleyLog;

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
public class FollowStudents extends Fragment
{
    Student auth;
    private List<Student> students=new ArrayList<>();
    private StudentAdapter stadapter;
    private String url="http://remarq-central.890m.com/pull_students_ifollow.php";
    private static String TAG = FollowStudents.class.getSimpleName();

    public FollowStudents()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        final View v=inflater.inflate(R.layout.fragment_follow_students, container, false);

        auth=(Student)getArguments().getSerializable("auth");

        RecyclerView recyclerview = (RecyclerView) v.findViewById(R.id.studentsIfollowlist);
        stadapter=new StudentAdapter(students);
        RecyclerView.LayoutManager mlayoutmanager=new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mlayoutmanager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                Student student=students.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("auth",auth);
                bundle.putSerializable("student",student);
                Profile pf=new Profile();
                pf.setArguments(bundle);
                FragmentTransaction ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.frames,pf);
                ft.commit();
            }

            @Override
            public void onLongClick(View view, int position)
            {
                Toast.makeText(v.getContext(),students.get(position).getFirst_name(),Toast.LENGTH_SHORT).show();
            }
        }));
        recyclerview.setAdapter(stadapter);

        prepareStudentList();

        return v;
    }

    void prepareStudentList()
    {
        Map<String,String> params=new HashMap<String, String>();
        params.put("studentid",Integer.toString(auth.getStudent_id()));

        CustomRequest request=new CustomRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Log.d(TAG, jsonObject.toString());

                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            if (success.equals("yes")) {
                                JSONArray array = jsonObject.getJSONArray("students");
                                Student student;
                                for (int i = 0; i < array.length(); ++i) {
                                    JSONObject obj = (JSONObject) array.get(i);
                                    student = new Student();
                                    student.setFirst_name(obj.getString("firstname"));
                                    student.setLast_name(obj.getString("lastname"));
                                    student.setEmail_id(obj.getString("emailid"));
                                    student.setStudent_id(obj.getInt("studentid"));
                                    students.add(student);
                                    Log.d(TAG,student.getFirst_name());
                                }
                                stadapter.notifyDataSetChanged();
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

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {
        private GestureDetector gestureDetector;
        private FollowStudents.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FollowStudents.ClickListener clickListener)
        {
            this.clickListener=clickListener;
            gestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
            {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
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
