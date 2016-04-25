package com.mobile.remarq;


import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginDetails extends Fragment
{
    public Button b1;
    public Button b2;
    public EditText email_input;
    public EditText password_input;
    private static String TAG = LoginDetails.class.getSimpleName();
    private String url="http://remarq-central.890m.com/pull_student.php";
    private HashMap<String, String> courseids=new HashMap<>();

    public LoginDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_login_details, container, false);
        courseids=(HashMap<String, String>)getArguments().getSerializable("courseids");

        b1=(Button)v.findViewById(R.id.signin);
        b2=(Button)v.findViewById(R.id.signup);
        email_input=(EditText)v.findViewById(R.id.email_input);
        password_input=(EditText)v.findViewById(R.id.password_input);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String emailid=email_input.getText().toString();
                final String password=password_input.getText().toString();

                Map<String,String> params=new HashMap<String, String>();
                params.put("emailid",emailid);
                params.put("password",password);

                CustomRequest request=new CustomRequest(Request.Method.POST, url, params,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject jsonObject)
                            {
                                Log.d(TAG, jsonObject.toString());
                                Log.d("username is - ",emailid);
                                Log.d("password is - ",password);

                                try
                                {
                                    String success=jsonObject.getString("success");
                                    String message=jsonObject.getString("message");
                                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                                    if(success.equals("yes"))
                                    {
                                        Student auth=new Student();
                                        auth.setFirst_name(jsonObject.getString("firstname"));
                                        auth.setLast_name(jsonObject.getString("lastname"));
                                        auth.setEmail_id(jsonObject.getString("emailid"));
                                        auth.setStudent_id(jsonObject.getInt("studentid"));
                                        Intent i=new Intent(getActivity(), MainActivity.class);
                                        i.putExtra("auth",auth);
                                        i.putExtra("courseids",courseids);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        email_input.setText(emailid);
                                        password_input.setText("");
                                    }
                                }
                                catch(JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError volleyError)
                            {
                                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                                Toast.makeText(getActivity(),
                                        volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                AppController.getInstance().addToRequestQueue(request);
            }
        });

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction f=getFragmentManager().beginTransaction();
                f.addToBackStack(null);
                f.replace(R.id.loginframes,new SignUpDetails());
                f.commit();
            }
        });
        return v;
    }
}

