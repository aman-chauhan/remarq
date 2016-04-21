package com.mobile.remarq;


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


public class SignUpDetails extends Fragment
{
    Button b1;
    public EditText input_email;
    public EditText input_password;
    public EditText confirm_password;
    public EditText input_firstname;
    public EditText input_lastname;
    private static String TAG = SignUpDetails.class.getSimpleName();
    private String url="http://remarq-central.890m.com/push_student.php";

    public SignUpDetails()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_sign_up_details, container, false);
        input_firstname=(EditText)v.findViewById(R.id.input_firstname);
        input_lastname=(EditText)v.findViewById(R.id.input_lastname);
        input_email=(EditText)v.findViewById(R.id.input_email);
        input_password=(EditText)v.findViewById(R.id.input_pass);
        confirm_password=(EditText)v.findViewById(R.id.input_repass);
        b1=(Button)v.findViewById(R.id.register);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String firstname=input_firstname.getText().toString();
                final String lastname=input_lastname.getText().toString();
                final String emailid=input_email.getText().toString();
                final String password=input_password.getText().toString();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("firstname", firstname);
                    params.put("lastname", lastname);
                    params.put("emailid", emailid);
                    params.put("password", password);
                    CustomRequest request = new CustomRequest(Request.Method.POST, url, params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Log.d(TAG, jsonObject.toString());

                                    try {
                                        String success = jsonObject.getString("success");
                                        String message = jsonObject.getString("message");
                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                        if (success.equals("yes")) {
                                            FragmentTransaction ft=getFragmentManager().beginTransaction();
                                            ft.replace(R.id.loginframes,new LoginDetails());
                                            ft.commit();
                                        } else {
                                            input_firstname.setText(firstname);
                                            input_lastname.setText(lastname);
                                            input_email.setText(emailid);
                                            input_password.setText("");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                                    Toast.makeText(getActivity(),
                                            volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    AppController.getInstance().addToRequestQueue(request);
            }
        });
        return v;
    }

}
