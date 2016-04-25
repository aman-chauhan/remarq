package com.mobile.remarq;


import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCourse extends Fragment
{

    Student auth;
    private List<Course> courses=new ArrayList<>();
    private CourseAdapter coadapter;

    public SearchCourse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View v=inflater.inflate(R.layout.fragment_search_course, container, false);

        auth=(Student)getArguments().getSerializable("auth");

        SerializedList sl=(SerializedList)getArguments().getSerializable("courselist");


        RecyclerView recyclerview = (RecyclerView) v.findViewById(R.id.coursesIfollowlist);
        coadapter=new CourseAdapter(courses);
        RecyclerView.LayoutManager mlayoutmanager=new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mlayoutmanager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                Course course=courses.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("auth",auth);
                bundle.putSerializable("course",course);
                CourseProfile cp=new CourseProfile();
                cp.setArguments(bundle);
                FragmentTransaction ft=getFragmentManager().beginTransaction();
                ft.replace(R.id.frames,cp);
                ft.commit();
            }

            @Override
            public void onLongClick(View view, int position)
            {
                Toast.makeText(v.getContext(),courses.get(position).getCourse_name(),Toast.LENGTH_SHORT).show();
            }
        }));
        recyclerview.setAdapter(coadapter);

        if(sl!=null)
        {
            courses=sl.getList();
            coadapter.notifyDataSetChanged();
        }
        else
        {
            Log.d("SerializedList","error in serialized object in course");
        }
        return v;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {
        private GestureDetector gestureDetector;
        private SearchCourse.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SearchCourse.ClickListener clickListener)
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
