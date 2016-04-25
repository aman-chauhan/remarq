package com.mobile.remarq;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseProfile extends Fragment
{
    TextView courseCode;
    TextView courseName;

    Student auth;
    Course course;
    private List<NoteData> notes = new ArrayList<>();
    private NoteViewAdapter noteViewAdapter;
    private String url="http://remarq-central.890m.com/pull_notes_from_course.php";
    private static String TAG = CourseProfile.class.getSimpleName();

    public CourseProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.fragment_course_profile, container, false);

        auth=(Student)getArguments().getSerializable("auth");
        course=(Course)getArguments().getSerializable("course");

        courseCode=(TextView)v.findViewById(R.id.courseCode);
        courseName=(TextView)v.findViewById(R.id.courseName);
        courseCode.setText(course.getCourse_code());
        courseName.setText(course.getCourse_name());

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        noteViewAdapter = new NoteViewAdapter(notes);
        RecyclerView.LayoutManager mlayoutmanager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(v.getContext(), notes.get(position).getNoteTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(v.getContext(), notes.get(position).getNoteTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
        recyclerView.setAdapter(noteViewAdapter);

        prepareNotesList();

        return v;
    }

    void prepareNotesList()
    {
        Map<String,String> params=new HashMap<String, String>();
        params.put("courseid",Integer.toString(course.getCourse_id()));

        CustomRequest request=new CustomRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try{
                            Log.d(TAG,jsonObject.toString());

                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            if (success.equals("yes"))
                            {
                                Toast.makeText(getActivity(), "yes", Toast.LENGTH_LONG).show();
                                JSONArray array = jsonObject.getJSONArray("notes");
                                NoteData note;
                                for (int i = 0; i < array.length(); ++i)
                                {
                                    JSONObject obj = (JSONObject) array.get(i);
                                    note=new NoteData();
                                    note.setNoteID(obj.getInt("noteid"));
                                    note.setPostedBy(obj.getInt("writerid"));
                                    note.setPostedByName(obj.getString("firstname")+" "+obj.get("lastname"));
                                    note.setCoursecode(obj.getString("coursecode"));
                                    note.setDateOfNote(obj.getString("notedate"));
                                    note.setNoteTitle(obj.getString("notetitle"));
                                    note.setNoteContent(obj.getString("notecontent"));
                                    notes.add(note);
                                    Log.d(TAG,note.getNoteTitle());
                                }
                                noteViewAdapter.notifyDataSetChanged();
                            }
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        Toast.makeText(getActivity(),
                                volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {
        private GestureDetector gestureDetector;
        private CourseProfile.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CourseProfile.ClickListener clickListener)
        {
            this.clickListener=clickListener;
            gestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
            {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clickListener!=null)
                    {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e))
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
