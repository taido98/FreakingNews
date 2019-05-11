package com.example.naviapplication;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SaveTest {

//    private void addSave(final String url){
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response.trim().equals("success")){
////                            Toast.makeText(MainActivity.this, "them thanh cong", Toast.LENGTH_SHORT);
//                        }
//                        else {
////                            Toast.makeText(MainActivity.this, "Loi Dang nhap", Toast.LENGTH_SHORT);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(MainActivity.this, "Xay ra loi", Toast.LENGTH_SHORT);
//                        Log.d("AAA","Loi!\n"+error.toString());
//                    }
//                }
//        ){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
////                params.put("usernameApp", txtEmail.getText().toString().trim());
////                params.put("nameApp", txtName.getText().toString().trim());
//
////                params.put("id_User", id_User);
//                params.put("title_news", title_news);
//                params.put("link_news", link_news);
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
}
