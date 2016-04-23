package com.mobile.remarq;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNote extends Fragment
{
    Student auth;
    private String url="http://remarq-central.890m.com/push_note.php";
    private static String TAG = AddNote.class.getSimpleName();
    HashMap<String,String> courseids=new HashMap<>();

    EditText inputcoursecode;
    static EditText inputnotedate;
    EditText inputnotetitle;
    EditText inputnotecontent;
    Button submit;
    private static String notedate;

    public AddNote()
    {
        // Required empty public constructor
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            final Calendar c=Calendar.getInstance();
            int year=c.get(Calendar.YEAR);
            int month=c.get(Calendar.MONTH);
            int day=c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),this,year,month,day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            Calendar cal=Calendar.getInstance();
            cal.set(year,monthOfYear,dayOfMonth);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            notedate=sdf.format(cal.getTime());
            inputnotedate.setText(notedate);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_add_note, container, false);

        auth=(Student)getArguments().getSerializable("auth");
        courseids=(HashMap<String,String>)getArguments().getSerializable("courseids");

        inputcoursecode=(EditText)v.findViewById(R.id.inputcoursecode);
        inputnotedate=(EditText)v.findViewById(R.id.inputnotedate);
        inputnotedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment=new DatePickerFragment();
                newFragment.show(getFragmentManager(),"DatePicker");
            }
        });
        inputnotetitle=(EditText)v.findViewById(R.id.inputnotetitle);
        inputnotecontent=(EditText)v.findViewById(R.id.inputnotecontent);
        submit=(Button)v.findViewById(R.id.submitnote);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String coursecode=inputcoursecode.getText().toString().toUpperCase();
                String notetitle=inputnotetitle.getText().toString();
                String notecontent=inputnotecontent.getText().toString();

                if(!(notetitle==null||notecontent==null||coursecode==null))
                {
                    if(courseids.get(coursecode)==null)
                    {
                        Toast.makeText(getActivity(), "Please enter a valid Course Code.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Map<String,String> params=new HashMap<String, String>();
                        params.put("writerid",Integer.toString(auth.getStudent_id()));
                        params.put("coursecodeid",courseids.get(coursecode));
                        params.put("notedate",notedate);
                        params.put("notetitle",notetitle);
                        params.put("notecontent",notecontent);

                        CustomRequest request = new CustomRequest(Request.Method.POST, url, params,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {
                                        Log.d(TAG, jsonObject.toString());

                                        try {
                                            String success = jsonObject.getString("success");
                                            String message = jsonObject.getString("message");
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                            if (success.equals("yes"))
                                            {
                                                inputcoursecode.setText("");
                                                inputnotedate.setText("");
                                                inputnotetitle.setText("");
                                                inputnotecontent.setText("");
                                            }
                                        }
                                        catch (JSONException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                                        Toast.makeText(getActivity(),
                                                volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AppController.getInstance().addToRequestQueue(request);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Please enter all details.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

}
