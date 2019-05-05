package com.example.naviapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.naviapplication.util.RealPathUtil;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    private ImageView imageView, imagePin;
    private String PATH, imgName ,  im_url;
    private String imgCode;
    private Bitmap bitmap;
    ip ip = new ip();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_addpost_actionbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        im_url = "http://"+ip.getIp()+"/FreakingNews/upload/34689401_2147325555551794_7654281988110548992_n.jpg";
        im_url = "http://sohanews.sohacdn.com/thumb_w/660/2018/8/28/photo1535416480861-15354164808651145995032.png";
//        im_url = "https://scontent.fsgn2-3.fna.fbcdn.net/v/t1.0-9/58595586_2335381520025428_7214766066176622592_n.jpg?_nc_cat=108&_nc_oc=AQmkxqamW6GnIkygV8Hd9CHkvPMyTiTnrTJG_nXym340BAOyvm6cM1Y6w7nmnYGfz3U&_nc_ht=scontent.fsgn2-3.fna&oh=86463bbafdd68f9cda8a50e0da8f7370&oe=5D662368";
        imageView = findViewById(R.id.avatar_post);
        imagePin = (ImageView) findViewById(R.id.image_pin);

        Picasso.with(this).load(im_url).into(imageView);

        Button picker = (Button) findViewById(R.id.picker);
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), 100);

                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,100);
//                startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), 100);
            }
        });

        Button post = (Button) findViewById(R.id.add_post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload("http://"+ip.getIp()+"/FreakingNews/upload_image.php");
//                Toast.makeText(AddPostActivity.this,"posted",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK ) {
            PATH = RealPathUtil.getRealPath(this, data.getData());
            Uri uri = Uri.fromFile(new File(PATH));

            // Get name
            imgName = PATH.substring(PATH.lastIndexOf("/")+1);

            try {
                //Lấy dữ liệu dạng bitmap
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgCode = getBitMap(bitmap);
                Toast.makeText(this,imgCode,Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }


            //Trả về độ rộng của màn hình
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            //Load ảnh vào imageView
            Picasso.with(this).load(data.getData()).resize(width,0).into(imagePin);
//            Toast.makeText(this, data.getData().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Encode bitmap to String
    public String getBitMap(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void upload(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(AddPostActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddPostActivity.this, response,Toast.LENGTH_LONG).show();
                        Log.d("imageCode", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddPostActivity.this, "Lỗi\n"+error,Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("imageName",imgName);
                params.put("imageCode",imgCode);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
