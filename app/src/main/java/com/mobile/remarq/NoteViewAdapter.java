package com.mobile.remarq;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import static java.lang.Math.min;

/**
 * Created by Jenis_000 on 4/24/2016.
 */
public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.View_Holder>
{
    public List<NoteData> notelist;

    public static class View_Holder extends RecyclerView.ViewHolder
    {
        TextView noteTitle;
        TextView coursecode;
        TextView noteContent;
        TextView postedBy;
        TextView dateofnote;

        View_Holder(View itemView)
        {
            super(itemView);
            noteTitle=(TextView)itemView.findViewById(R.id.notetitle_text);
            noteContent=(TextView)itemView.findViewById(R.id.notecontent_text);
            coursecode=(TextView)itemView.findViewById(R.id.coursecode_text);
            postedBy=(TextView)itemView.findViewById(R.id.studentname_text);
            dateofnote=(TextView)itemView.findViewById(R.id.date_text);
        }
    }

    public NoteViewAdapter(List<NoteData> list)
    {
        this.notelist=list;
    }

    public void onBindViewHolder(View_Holder holder, int position)
    {
        NoteData note=notelist.get(position);
        holder.noteTitle.setText(note.getNoteTitle());
        holder.noteContent.setText(notelist.get(position).getNoteContent().substring(0,min(25,notelist.get(position).getNoteContent().length()))+"...");
        holder.postedBy.setText(notelist.get(position).getPostedByName());
        holder.coursecode.setText(notelist.get(position).getCoursecode());
        holder.dateofnote.setText(notelist.get(position).getDateOfNote());

    }

    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_note_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }

}

