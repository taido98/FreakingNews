package com.example.naviapplication;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.naviapplication.object.User;
import com.example.naviapplication.service.ip;
import com.example.naviapplication.util.RealPathUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    private ImageView imageView;
    private LinearLayout imagePick;
    private ArrayList<String> imgName, imgCode ;
    private String idTopic;
    private int idUser;
    private ArrayList<Uri> uri;
    private ip ip = new ip();
    private Spinner category_post;
    private EditText input_post;
    private int REQUEST_GALLERY_IMAGE = 100;
    private TextView name;
    String urlAddPost = "http://"+ip.getIp()+"/FreakingNews/newPost.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_addpost_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();

        imageView = findViewById(R.id.avatar_post);
        imagePick = (LinearLayout) findViewById(R.id.imagePick);
        category_post = (Spinner) findViewById(R.id.category_post);
        input_post = (EditText) findViewById(R.id.input_post);
        name = (TextView) findViewById(R.id.name_post);

        User user = new User(this);
        idUser = user.getId();

        category_post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(category_post.getSelectedItem().toString().equals("Kinh doanh"))
                    idTopic = "1";
                else if(category_post.getSelectedItem().toString().equals("Thể thao"))
                    idTopic = "2";
                else idTopic = "3";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Glide.with(this).load(user.getUrl_avatar()).into(imageView);
        name.setText(user.getName());

        Button picker = (Button) findViewById(R.id.picker);
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGallerySelected();
            }
        });

        Button post = (Button) findViewById(R.id.add_post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPostActivity.this.startActivity(new Intent(AddPostActivity.this,PostActivity.class));
                upload(urlAddPost);
            }
        });
    }

    protected void onGallerySelected(){
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent,"Select Picture"), REQUEST_GALLERY_IMAGE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            // When an Image is picked
            if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                imgName = new ArrayList<>();
                uri = new ArrayList<>();
                if(data.getData()!=null){
                    String PATH = RealPathUtil.getRealPath(AddPostActivity.this, data.getData());
                    Uri mImageUri= Uri.fromFile(new File(PATH));
                    uri.add(mImageUri);
                    //Get name
                    imgName.add(PATH.substring(PATH.lastIndexOf("/")+1));
                    ImageView image = new ImageView(this);
                    Glide.with(this).load(mImageUri).override(300,300).centerCrop().into(image);
                    imagePick.addView(image);
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            String PATH = RealPathUtil.getRealPath(AddPostActivity.this, item.getUri());
                            Uri mImageUri = Uri.fromFile(new File(PATH));
                            uri.add(mImageUri);
//                            bitmap.add(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
                            //Get name
                            imgName.add(PATH.substring(PATH.lastIndexOf("/")+1));
                            ImageView image = new ImageView(this);
                            Glide.with(this).load(mImageUri).override(300,300).centerCrop().into(image);
                            imagePick.addView(image);
                        }
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong"+e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
//        Toast.makeText(this,imgName)
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Encode bitmap to String
    public String getBitMap(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //Function upload post to server
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
                imgCode = new ArrayList<>();
                params.put("size",""+imgName.size());
                for(int i = 0; i < imgName.size(); i++){
                    try {
                        imgCode.add(getBitMap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri.get(i))));
                        params.put("imgName"+i,imgName.get(i));
                        params.put("imgCode"+i,imgCode.get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                params.put("idUser",""+idUser);
                params.put("idTopic",idTopic);
                params.put("content",input_post.getText().toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);
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

}
