package com.group6.donor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class ApiClient {
//change ip address before testing
    private static final String BASE_URL = "http://192.168.254.103/db_donation_inventory_system/";
    private static Retrofit retrofit;

    static Retrofit getApiClient(){

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
