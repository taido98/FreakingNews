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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.naviapplication.util.AddImageAdapter;
import com.example.naviapplication.util.PostAdapter;
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
    private Button post;
    private ArrayList<String> imgName, imgCode ;
    private String idTopic;
    private int idUser;
    private ArrayList<Uri> uri = new ArrayList<>();
    private ip ip = new ip();
    private Spinner category_post;
    private EditText input_post;
    private int REQUEST_GALLERY_IMAGE = 100;
    private TextView name;
    private RecyclerView recyclerView;
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
        category_post = (Spinner) findViewById(R.id.category_post);
        input_post = (EditText) findViewById(R.id.input_post);
        name = (TextView) findViewById(R.id.name_post);
        recyclerView = (RecyclerView) findViewById(R.id.imageAddPost);
        post = (Button) findViewById(R.id.add_post);

        User user = new User(this);
        idUser = user.getId();
        idTopic = "0";

        category_post.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(category_post.getSelectedItem().toString().equals("Kinh doanh"))
                    idTopic = "1";
                else if(category_post.getSelectedItem().toString().equals("Thể thao"))
                    idTopic = "2";
                else if(category_post.getSelectedItem().toString().equals("Công nghệ"))
                    idTopic = "3";
                else idTopic = "0";
                checkEmpty();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        input_post.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEmpty();
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

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty().equals("true")){
                    AddPostActivity.this.startActivity(new Intent(AddPostActivity.this,PostActivity.class));
                    upload(urlAddPost);
                    finish();
                }
                else {
                    Toast.makeText(AddPostActivity.this,checkEmpty(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected String checkEmpty(){
        if(idTopic.equals("0")){
            post.setBackgroundResource(R.drawable.add_post_button_fail);
            post.setTextColor(0xB4CACACA);
            return "Vui lòng chọn thể loại muốn đăng";
        }
        if(input_post.getText().toString().equals("")){
            post.setBackgroundResource(R.drawable.add_post_button_fail);
            post.setTextColor(0xB4CACACA);
            return "Vui lòng viết nội dung bài đăng";
        }if(uri.size() == 0){
            post.setBackgroundResource(R.drawable.add_post_button_fail);
            post.setTextColor(0xB4CACACA);
            return "Vui lòng chọn hình ảnh";
        }
        post.setBackgroundResource(R.drawable.custom_add_post);
        post.setTextColor(0xFFFFFFFF);
        return "true";
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
                if(data.getData()!=null){
                    String PATH = RealPathUtil.getRealPath(AddPostActivity.this, data.getData());
                    Uri mImageUri= Uri.fromFile(new File(PATH));
                    uri.add(mImageUri);
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            String PATH = RealPathUtil.getRealPath(AddPostActivity.this, item.getUri());
                            Uri mImageUri = Uri.fromFile(new File(PATH));
                            uri.add(mImageUri);
                        }
                    }
                }
                recyclerView.setAdapter(new AddImageAdapter(uri));
                recyclerView.setLayoutManager(new LinearLayoutManager(AddPostActivity.this, LinearLayoutManager.HORIZONTAL, false));
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong"+e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
//        Toast.makeText(this,imgName)
        checkEmpty();
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddPostActivity.this, "Lỗi\n"+error,Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                imgCode = new ArrayList<>();
                imgName = new ArrayList<>();
                params.put("size",""+uri.size());
                for(int i = 0; i < uri.size(); i++){
                    try {
                        imgCode.add(getBitMap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri.get(i))));
                        imgName.add(uri.get(i).toString().substring(uri.get(i).toString().lastIndexOf("/")+1));
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
