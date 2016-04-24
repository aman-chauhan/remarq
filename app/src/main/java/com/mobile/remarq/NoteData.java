package com.mobile.remarq;

/**
 * Created by Jenis_000 on 4/24/2016.
 */
public class NoteData
{
    private int noteID;
    private String noteTitle;
    private String noteContent;
    private int postedBy;
    private String postedByName;
    private String coursecode;
    private String dateOfNote;

    public NoteData()
    {

    }

    public String getPostedByName() {
        return postedByName;
    }

    public void setPostedByName(String postedByName) {
        this.postedByName = postedByName;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getDateOfNote() {
        return dateOfNote;
    }

    public void setDateOfNote(String dateOfNote) {
        this.dateOfNote = dateOfNote;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public int getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(int postedBy) {
        this.postedBy = postedBy;
    }
}
