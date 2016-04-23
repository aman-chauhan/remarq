package com.mobile.remarq;

/**
 * Created by Jenis_000 on 4/24/2016.
 */
public class NoteData {
    public String noteTitle;
    public String noteContent;
    public String postedBy;
    public String courseID;
    public String dateOfNote;
    NoteData(String noteContent,String courseID, String dateOfNote, String noteTitle,String postedBy  ) {
        this.noteContent = noteContent;
        this.courseID = courseID;
        this.dateOfNote = dateOfNote;
        this.noteTitle = noteTitle;
        this.postedBy = postedBy;
    }
}
