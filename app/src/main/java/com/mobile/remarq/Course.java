package com.mobile.remarq;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Aman Chauhan on 20-04-2016.
 */
public class Course implements Serializable
{
    private int course_id;
    private String course_code;
    private String course_name;

    public Course()
    {

    }

    public Course(int course_id,String course_code, String course_name)
    {
        this.course_id=course_id;
        this.course_code=course_code;
        this.course_name=course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
