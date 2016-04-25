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


public class SearchStudent extends Fragment
{

    Student auth;
    private List<Student> students=new ArrayList<>();
    private StudentAdapter stadapter;

    public SearchStudent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v=inflater.inflate(R.layout.fragment_search_student, container, false);
        auth=(Student)getArguments().getSerializable("auth");

        SerializedList sl=(SerializedList)getArguments().getSerializable("studentlist");

        RecyclerView recyclerview = (RecyclerView) v.findViewById(R.id.studentsfollowMelist);
        stadapter=new StudentAdapter(students);
        RecyclerView.LayoutManager mlayoutmanager=new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mlayoutmanager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
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
        if(sl!=null)
        {
            students=sl.getList();
            Log.d("SerializedList","reciever sl object");
            stadapter.notifyDataSetChanged();
            Log.d("SerializedList","dataset changed");
        }
        else
        {
            Log.d("SerializedList","error in serialized object in student");
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
        private SearchStudent.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SearchStudent.ClickListener clickListener)
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
