package dev.andi.filmania.Controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by macos on 11/26/16.
 */

public class CloudConnecting {
    public void getData(Context context, String URL, final CloudResult result) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result.resultData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.errorResultData(error.toString());
            }
        }
        );
        requestQueue.add(stringRequest);
    }
}
