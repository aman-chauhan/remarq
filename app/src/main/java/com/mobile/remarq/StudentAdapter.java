package com.mobile.remarq;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.List;

/**
 * Created by Aman Chauhan on 20-04-2016.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder>
{
    private List<Student> studentList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nameofstudent, emailofstudent;

        public MyViewHolder(View view)
        {
            super(view);
            nameofstudent=(TextView)view.findViewById(R.id.nameofstudent);
            emailofstudent=(TextView)view.findViewById(R.id.emailofstudent);
        }
    }

    public StudentAdapter(List<Student> studentList)
    {
        this.studentList=studentList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Student student=studentList.get(position);
        holder.nameofstudent.setText(student.getFirst_name()+" "+student.getLast_name());
        holder.emailofstudent.setText(student.getEmail_id());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_student, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
