package com.example.post;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private List<Comment> getListComment(){
        List<Comment> list = new ArrayList<Comment>();
        String im_url = "http://sohanews.sohacdn.com/thumb_w/660/2018/8/28/photo1535416480861-15354164808651145995032.png";

        Comment cmt1 = new Comment("Hoàng Văn A", "Thích\nOneshot", im_url);
        Comment cmt2 = new Comment("Hoàng Văn B", "Đẹp thế", im_url);
        Comment cmt3 = new Comment("Hoàng Văn C", "Đẹp trai thế", im_url);

        list.add(cmt1);
        list.add(cmt2);
        list.add(cmt3);
        list.add(cmt1);
        list.add(cmt2);
        list.add(cmt3);
        list.add(cmt1);
        list.add(cmt2);
        list.add(cmt3);

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = this.getIntent();
        Toast.makeText(this,"Da chuyen",Toast.LENGTH_LONG).show();
        List<Comment> listCmt = getListComment();
        ListView listComment = (ListView) findViewById(R.id.listComment);
        listComment.setAdapter(new CustomCommentAdapter(listCmt,this));

        Button submit = findViewById(R.id.submit_cmt);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentActivity.this,"Da submit",Toast.LENGTH_LONG).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
