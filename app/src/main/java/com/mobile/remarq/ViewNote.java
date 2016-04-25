package com.mobile.remarq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewNote extends Fragment
{
    Student auth;
    NoteData note;

    TextView coursecode, notedate, notetitle, notecontent, writerid;

    public ViewNote()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View v=inflater.inflate(R.layout.fragment_view_note, container, false);

        auth=(Student)getArguments().getSerializable("auth");
        note=(NoteData)getArguments().getSerializable("note");

        coursecode=(TextView)v.findViewById(R.id.coursecode);
        notedate=(TextView)v.findViewById(R.id.notedate);
        notetitle=(TextView)v.findViewById(R.id.notetitle);
        notecontent=(TextView)v.findViewById(R.id.notecontent);
        writerid=(TextView)v.findViewById(R.id.writerid);

        coursecode.setText(note.getCoursecode());
        notedate.setText(note.getDateOfNote());
        notetitle.setText(note.getNoteTitle());
        notecontent.setText(note.getNoteContent());
        writerid.setText(note.getPostedByName());

        return v;
    }

}
