package com.mobile.remarq;

import java.io.Serializable;

/**
 * Created by Aman Chauhan on 16-04-2016.
 */
public class Student implements Serializable
{
    private int student_id;
    private String first_name;
    private String last_name;
    private String email_id;

    public Student()
    {

    }

    public Student(int student_id, String first_name,String last_name,String email_id)
    {
        this.student_id=student_id;
        this.first_name=first_name;
        this.last_name=last_name;
        this.email_id=email_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}
