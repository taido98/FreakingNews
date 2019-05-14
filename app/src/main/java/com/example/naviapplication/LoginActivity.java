package com.example.naviapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.naviapplication.service.ip;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private CallbackManager callbackManager;
    private LoginButton fb_login;
    private SignInButton gg_login;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;
    private String name, email, url_avatar, id;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 001;
    public final String SHARED_PREFERENCES_NAME = "DoUSer";
    private Button sign_out;
    private ip ip = new ip();
    String urlInsert ="http://"+ip.getIp()+"/FreakingNews/insert.php";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        fb_login = findViewById(R.id.fb_login);
        gg_login = findViewById(R.id.gg_login);
        sign_out = findViewById(R.id.sign_out);
        findViewById(R.id.gg_login).setOnClickListener(this);
        findViewById(R.id.sign_out).setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();

        fb_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loadUserProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this,"Login Cancel",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this,"Login Error",Toast.LENGTH_LONG).show();
            }
        });




    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
//lay ket qua fb,gg
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(
                newAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {
                        name = object.optString("name");
                        id = object.optString("id");
                        if(object.optString("email").equals(""))
                            email = id+"@facebook.com";
                        else
                            email = object.optString("email");
                        url_avatar = "https://graph.facebook.com/"+id+"/picture?type=large";
                        addUser(urlInsert);

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields","id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        if(completedTask.isSuccessful()){

            GoogleSignInAccount acct = completedTask.getResult();
            name=acct.getDisplayName();
            email = acct.getEmail();
            url_avatar= acct.getPhotoUrl().toString();
            addUser(urlInsert);
        }
    }


    public void addUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("name",name);
                        intent.putExtra("url",url_avatar);
                        intent.putExtra("idUser",response);

                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.putString("name", name);
                        editor.putString("url", url_avatar);
                        editor.putString("idUser" , response);
                        editor.apply();

                        LoginActivity.this.startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("AAA","Loi!\n"+error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("name", name);
                params.put("url", url_avatar);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        name=null;
                        email=null;
                        url_avatar =null;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("name",name);
                        intent.putExtra("url",url_avatar);
                        LoginActivity.this.startActivity(intent);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out:
                signOut();
                break;
//            //
            case R.id.gg_login:
                signIn();
////                addUser(urlInsert);
                break;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);
        finish();
    }
}
