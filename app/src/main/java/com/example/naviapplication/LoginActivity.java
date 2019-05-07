package com.example.naviapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton fb_login;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;
    private String name, email, url, id;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fb_login = findViewById(R.id.fb_login);

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

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if(accessToken == null){
//            Toast.makeText(LoginActivity.this,"Sign in, Please ...", Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(LoginActivity.this,"Ok Ok :))))", Toast.LENGTH_LONG).show();
//            loadUserProfile(accessToken);
//        }
//        new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                if(currentAccessToken == null){
//                    Toast.makeText(LoginActivity.this,"Signed out", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    Toast.makeText(LoginActivity.this,"Signed in", Toast.LENGTH_LONG).show();
//                    loadUserProfile(currentAccessToken);
//                }
//            }
//        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(
                newAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {
                        // Application code
                        name = object.optString("name");
                        id = object.optString("id");
                        if(object.optString("email").equals(""))
                            email = id+"@facebook.com";
                        else
                            email = object.optString("email");
                        url = "https://graph.facebook.com/"+id+"/picture?type=large";

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("id",id);
                        intent.putExtra("email",email);
                        intent.putExtra("url",url);
                        LoginActivity.this.startActivity(intent);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields","id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

}
