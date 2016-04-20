package com.mobile.remarq;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aman Chauhan on 20-04-2016.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder>
{
    private List<Course> courseList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView codeofcourse,nameofcourse;

        public MyViewHolder(View view)
        {
            super(view);
            codeofcourse=(TextView)view.findViewById(R.id.codeofcourse);
            nameofcourse=(TextView)view.findViewById(R.id.nameofcourse);
        }
    }

    public CourseAdapter(List<Course> courseList)
    {
        this.courseList=courseList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Course course=courseList.get(position);
        holder.codeofcourse.setText(course.getCourse_code());
        holder.nameofcourse.setText(course.getCourse_name());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_course, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}
