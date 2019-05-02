package com.example.post;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private List<Post> getListPost(){
        List<Post> list = new ArrayList<Post>();
//        String im_url = "http://sohanews.sohacdn.com/thumb_w/660/2018/8/28/photo1535416480861-15354164808651145995032.png";
//        String im_url = "https://kenh14cdn.com/2017/2017-05-11-1512101608333593537-1496656855992.jpg";
        String im_url = "https://codiemgicoxuikhong.com/wp-content/uploads/2018/01/3851395811607223840785177179385096254783488n-1-1543141992498321668986.jpg";

        Post post = new Post("Hoàng Văn", "13/4/2019", im_url, "Yêu nhầm bạn thân", 109);
        Post post1 = new Post("Hoàng Văn", "13/4/2019", im_url, im_url, "Yêu nhầm bạn thân", 109);
        Post post2 = new Post("Quang Trung", "12/3/20", im_url, im_url, "Em trai sau khi chuyển giới", 200);
        Post post3 = new Post("Văn Hoàng", "29/2/2030", im_url, im_url, "Hồ điệp", 23098);

        list.add(post);
        list.add(post1);
        list.add(post2);
        list.add(post3);
        list.add(post1);
        list.add(post2);
        list.add(post3);
        list.add(post1);
        list.add(post2);
        list.add(post3);

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_post_actionbar);

//        imageView = findViewById(R.id.avatar_comment);
//        ListView imageView2 = findViewById(R.id.listPost);
//        String im_url = "http://sohanews.sohacdn.com/thumb_w/660/2018/8/28/photo1535416480861-15354164808651145995032.png";
//        Picasso.get().load(im_url).into(imageView);
//        Picasso.get().load(im_url).into(imageView2);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = Math.round(displayMetrics.widthPixels - 10 * displayMetrics.density);
        Toast.makeText(this,""+width,Toast.LENGTH_LONG).show();

        List<Post> list = getListPost();
        ListView listPost = (ListView) findViewById(R.id.listPost);
        listPost.setAdapter(new CustomPostAdapter(list,this, width));

        Button add_post = findViewById(R.id.add_post);
        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddPostActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

}
