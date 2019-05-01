package com.example.naviapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.naviapplication.fagments.BusinessFragment;
import com.example.naviapplication.fagments.ErrorFragment;
import com.example.naviapplication.fagments.HomeFragment;
import com.example.naviapplication.fagments.SavedNewsFragment;
import com.example.naviapplication.fagments.SportFragment;
import com.example.naviapplication.fagments.TechFragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Article> articles;
    SwipeMenuListView listView;
    CustomAdapter customAdapter;
    private static String cate = "https://www.24h.com.vn/upload/rss/tintuctrongngay.rss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (SwipeMenuListView) findViewById(R.id.listView);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        articles = new ArrayList<Article>();

        //nếu có kết nối Internet
        if (check_internet()) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadData().execute(cate);
                }
            });

            //tạo menu trượt
            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    // tạo nút share
                    SwipeMenuItem shareItem = new SwipeMenuItem(
                            MainActivity.this);
                    // nút share background
                    shareItem.setBackground(new ColorDrawable(Color.rgb(66, 134, 244)));
                    // width của nút share
                    shareItem.setWidth(170);
                    // tạo icon
                    shareItem.setIcon(R.drawable.ic_share_white);
                    // thêm vào menu
                    menu.addMenuItem(shareItem);

                    // tạo nút save
                    SwipeMenuItem saveItem = new SwipeMenuItem(
                            MainActivity.this);
                    //background
                    saveItem.setBackground(new ColorDrawable(Color.rgb(66, 244, 83)));
                    // width
                    saveItem.setWidth(170);
                    // tạo icon
                    saveItem.setIcon(R.drawable.ic_menu_save);
                    // thêm vào menu
                    menu.addMenuItem(saveItem);
                }
            };
            listView.setMenuCreator(creator);


            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            //khi click vào nút share, gọi hàm shareIt() để share
                            Toast.makeText(MainActivity.this, articles.get(position).title, Toast.LENGTH_LONG).show();
                            shareIt(articles.get(position));
                            break;
                        case 1:
                            //TODO 1: sự kiện longclick để lưu tin vào data, thêm code ở đây
                            Toast.makeText(MainActivity.this, "Tin đã được lưu", Toast.LENGTH_LONG).show();
                            break;
                    }
                    // false : close the menu; true : not close the menu
                    return false;
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("link", articles.get(position).link);
                    startActivity(intent);
                }
            });

        } else {    //Nếu không có internet, hiển thị thông báo lỗi
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ErrorFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //điều hướng menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (!check_internet()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ErrorFragment()).commit();
        } else {
            switch (id) {
                case R.id.nav_home: {
                    articles.clear();
                    customAdapter.notifyDataSetChanged();
                    new ReadData().execute(cate);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new HomeFragment()).commit();
                    break;
                }
                case R.id.nav_business: {
                    cate = "https://24h.com.vn/upload/rss/taichinhbatdongsan.rss";
                    articles.clear();
                    customAdapter.notifyDataSetChanged();
                    new ReadData().execute(cate);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new BusinessFragment()).commit();
                    break;
                }
                case R.id.nav_sport: {
                    cate = "https://24h.com.vn/upload/rss/thethao.rss";
                    articles.clear();
                    customAdapter.notifyDataSetChanged();
                    new ReadData().execute(cate);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SportFragment()).commit();
                    break;
                }
                case R.id.nav_tech: {
                    cate = "https://24h.com.vn/upload/rss/congnghethongtin.rss";
                    articles.clear();
                    customAdapter.notifyDataSetChanged();
                    new ReadData().execute(cate);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new TechFragment()).commit();
                    break;
                }
                case R.id.nav_saved: {
                    articles.clear();
                    customAdapter.notifyDataSetChanged();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SavedNewsFragment()).commit();
                    break;
                }
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //function kiểm tra có internet đang kết nối không
    private boolean check_internet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    //class aynctask để xử lý rss được đọc về
    class ReadData extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {

            return docNoiDung_Tu_URL(strings[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String title = "";
            String link = "";
            String image = "";
            String pubDate = "";
            for (int i=0; i < nodeList.getLength(); i++) {
                String cdata = nodeListDescription.item(i+1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher m = p.matcher(cdata);
                if (m.find()) {
                    image = m.group(1);
                    Log.d("hinhanh", image);
                }
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");
                pubDate = parser.getValue(element, "pubDate");
                articles.add(new Article(title, link, image, pubDate));
            }

            customAdapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_list_item_1, articles);
            listView.setAdapter(customAdapter);
            listView.invalidateViews();

            super.onPostExecute(s);
        }
    }

    //function đọc nội dung từ URL
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    //function để chia sẻ tin trên các nền tảng
    public void shareIt(Article p) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String sharebody = p.link;
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
