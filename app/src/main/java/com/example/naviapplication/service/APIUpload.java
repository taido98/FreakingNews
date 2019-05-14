package com.example.naviapplication.service;

import com.example.naviapplication.model.Message;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIUpload {
    @FormUrlEncoded
    @POST("newPost.php")
    Call<Message> uploadImage(@Field("imageCode")String imgCode,
                              @Field("imageName")String imgName);
}
