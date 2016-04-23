package com.mobile.remarq;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class TimelineFragment extends Fragment {

    List<NoteData> data = fill_with_data();
    RecyclerView recyclerView;
    NoteViewAdapter noteViewAdapter;
    public TimelineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_timeline, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        noteViewAdapter=new NoteViewAdapter(data,getContext());
        recyclerView.setAdapter(noteViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
    public List<NoteData> fill_with_data() {
        List<NoteData> data = new ArrayList<>();
        data.add(new NoteData("Three rules of Master Theorem","CE601","24-04-16","Master's Theorem","Aishwarye Omer"));
        data.add(new NoteData("Oveview of KDC and it's complexity","IT652","24-04-16","Key Distribution Center","Pooja Malvi"));
        data.add(new NoteData("Perfection id the key","IT661","25-04-16","How to make a Project in MADT?","Aman Chauhan"));
        return data;
    }
}
