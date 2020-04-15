package com.example.volleyapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class UserData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        BgUserData bgClass=new BgUserData();
        bgClass.execute();
    }







    private class BgUserData extends AsyncTask<String,String,JSONObject>
    {
        ProgressDialog pDialog;
        JsonObjectRequest request;
        RequestQueue queue;
        TextView welcome;
        TextView name;
        ImageView image;
        TextView contact;
        TextView address;
        String result;
        String url="https://randomuser.me/api/";
        private String nameT;
        private String contactT;
        private String addressT;
        private String imageT;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(UserData.this);
            pDialog.setMessage("Fetching data from the internet");
            pDialog.setTitle("Please wait...");
            pDialog.show();
            Log.d("hasan","Instruction executed");
            name=findViewById(R.id.name);
            address=findViewById(R.id.address);
            contact=findViewById(R.id.contact);
            welcome=findViewById(R.id.textView2);



            image=findViewById(R.id.image);


        }



        @Override
        protected  JSONObject doInBackground(String... strings) {

            final JSONObject[] res = new JSONObject[1];
            Log.d("hasan","execution started");
                queue= Volley.newRequestQueue(UserData.this);
                request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            res[0] =response;
                            //accessing the object
                            JSONArray arr= response.getJSONArray("results");
                            //accessing json array inside object
                           // JSONObject obj2=obj.getJSONObject("0");
                            for(int i=0; i < arr.length(); i++){
                                JSONObject object=arr.getJSONObject(i);
                                //accessing name object inside object
                            //    Log.d("hasan","gender is : " + i + " is  " + object.getString("name"));
                                if(object.length() > 0){
                                  //  Log.d("hasan","this : " + object);

                                    //getting contact
                                    contactT=object.getString("phone");

                                    //getting image
                                    JSONObject imgObj=object.getJSONObject("picture");

                                    imageT=imgObj.getString("large");
                                    Picasso.get().load(imageT).into(image);



                                    //getting address
                                    JSONObject locObj=object.getJSONObject("location");
                                    JSONObject addObj=locObj.getJSONObject("street");
                                    addressT=addObj.getString("number") + " street, " + addObj.getString("name") + ", " +  locObj.getString("city") + ", " + locObj.getString("country");
                                   //getting name
                                    JSONObject nameObj=object.getJSONObject("name");
                                    nameT=nameObj.getString("title");
                                    nameT+=" " + nameObj.getString("first") + " " + nameObj.getString("last");
                                    name.setText(nameT);

                                    //     Log.d("hasan","Name : " + nameT);
                                }
                            }
                        //    Log.d("hasan","response : " + arr.getString(1));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("hasan","error : " + e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        result=error.toString();
                    }
                });
            queue.add(request);

            try {
                Thread.sleep(4500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return res[0];
        }




        @Override
        protected  void onPostExecute(JSONObject s) {
            super.onPostExecute(s);
            pDialog.dismiss();

            Log.d("hasan","execution finished!");
            address.setText(addressT);
            contact.setText(contactT);
            welcome.append(" "+nameT);

//                image.setImageBitmap(mIcon_val);



          //  Log.d("hasan","Response is : " + s);

        }
    }
}
