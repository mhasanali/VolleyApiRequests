package com.example.volleyapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;

public class MainActivity extends AppCompatActivity {
    String url="https://stage-operations.dawaai.pk/picker/login";
    RequestQueue requestQueue;
    JsonObjectRequest jsonreq;
    JSONObject object;
    private class Req extends AsyncTask<String,String,String>{
        String res="";
        ProgressDialog nDialog;

        @Override
        protected void onPreExecute() {

            nDialog = new ProgressDialog(MainActivity.this);
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Getting Data from the Internet");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                object=new JSONObject();
                object.put("username","picker1");
                object.put("password","picker123");
                jsonreq=new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        res=response.toString();
                 //       Log.d("hasan","response: " + response);

                        Log.d("hasan","res : " + res);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("hasan","Error from response : " +error.networkResponse.statusCode);

                    }

                });

                requestQueue.add(jsonreq);
            }
            catch (Exception e){
                Log.d("hasan","error from catch " + e);
                return e.toString();
            }
            return res;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                nDialog.dismiss();
               Log.d("hasan",s);
                Log.d("hasan","Done!");

            }
            catch (Exception e){
                Log.d("hasan","error : " + e);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Req req=new Req();
        req.execute();
     //   RequestQueue requestQueue= Volley.newRequestQueue(this);
        //        requestQueue.add(jsonReq);
       // requestQueue.start();
    }
}
