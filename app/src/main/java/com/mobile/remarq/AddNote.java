package com.mobile.remarq;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNote extends Fragment
{
    Student auth;
    private String url="http://remarq-central.890m.com/pull_courses_ifollow.php";
    private static String TAG = AddNote.class.getSimpleName();

    public AddNote() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_add_note, container, false);

        auth=(Student)getArguments().getSerializable("auth");

        return v;
    }

}
