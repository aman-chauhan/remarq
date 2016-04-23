package com.mobile.remarq;

import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity
{

    private String url="http://remarq-central.890m.com/get_pre.php";
    private static String TAG = Login.class.getSimpleName();
    HashMap<String,String> courseids=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction f=getFragmentManager().beginTransaction();
        f.replace(R.id.loginframes, new Splash());
        f.commit();

        /*new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

            }
        },3000);*/
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            CustomRequest request=new CustomRequest(Request.Method.GET,url,null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject jsonObject)
                        {
                            try{
                                Log.d(TAG,jsonObject.toString());

                                String success = jsonObject.getString("success");
                                if (success.equals("yes"))
                                {
                                    JSONArray array = jsonObject.getJSONArray("courses");
                                    for (int i = 0; i < array.length(); ++i)
                                    {
                                        JSONObject obj = (JSONObject) array.get(i);
                                        courseids.put(obj.getString("coursecode"),obj.getString("courseid"));
                                    }
                                    Log.d(TAG,courseids.toString());
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
                            Toast.makeText(getApplicationContext(),
                                    "Could not fetch data. Note uploading will be unsuccessful.", Toast.LENGTH_SHORT).show();
                        }
                    });
            AppController.getInstance().addToRequestQueue(request);
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            Bundle bundle=new Bundle();
            bundle.putSerializable("courseids",courseids);
            LoginDetails obj=new LoginDetails();
            obj.setArguments(bundle);
            FragmentTransaction ft=getFragmentManager().beginTransaction();
            ft.replace(R.id.loginframes, obj);
            ft.commit();
        }
    }
}

