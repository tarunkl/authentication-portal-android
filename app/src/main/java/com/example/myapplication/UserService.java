package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class UserService {
    @POST("auth/users/")
    Call<UserResponse> saveUser(@Body UserRequest userRequest){return null;}

}
