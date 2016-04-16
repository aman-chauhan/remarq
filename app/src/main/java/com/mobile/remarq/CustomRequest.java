package com.mobile.remarq;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Aman Chauhan on 16-04-2016.
 */
public class CustomRequest extends Request<JSONObject>
{

    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;

    public CustomRequest(int method, String url, Map<String,String> params, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener)
    {
        super(method,url,errorListener);
        this.listener=responseListener;
        this.params=params;
    }

    protected Map<String,String> getParams() throws com.android.volley.AuthFailureError
    {
        return params;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse)
    {
        try
        {
            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(networkResponse));
        }
        catch(UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch(JSONException je)
        {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject jsonObject)
    {
        listener.onResponse(jsonObject);
    }
}
