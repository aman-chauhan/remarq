package com.mobile.remarq;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Jenis_000 on 4/24/2016.
 */
public class NoteViewAdapter  extends RecyclerView.Adapter{
    List<NoteData> list = Collections.emptyList();
    Context context;
    public NoteViewAdapter(List<NoteData> list,Context context){
        this.list=list;
        this.context=context;
    }


    public void onBindViewHolder(View_Holder holder, int position) {
        holder.noteTitle.setText(list.get(position).noteTitle);
        holder.noteContent.setText(list.get(position).noteContent);
        holder.postedBy.setText(list.get(position).postedBy);
        holder.courseid.setText(list.get(position).courseID);
        holder.dateofnote.setText(list.get(position).dateOfNote);

    }
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_note_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class View_Holder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView noteTitle;
        TextView courseid;
        TextView noteContent;
        TextView postedBy;
        TextView dateofnote;

        View_Holder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            noteTitle=(TextView)itemView.findViewById(R.id.notetitle_text);
            noteContent=(TextView)itemView.findViewById(R.id.notecontent_text);
            courseid=(TextView)itemView.findViewById(R.id.courseid_text);
            postedBy=(TextView)itemView.findViewById(R.id.studentname_text);
            dateofnote=(TextView)itemView.findViewById(R.id.date_text);


        }
    }
}

