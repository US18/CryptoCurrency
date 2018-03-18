package com.example.uplabdhisingh.cryptocurrency.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by uplabdhisingh on 15/03/18.
 */

public class ApiClient
{

    public static final String BASE_URL = "https://api.coinmarketcap.com/v1/";
    private static Retrofit retrofit=null;

    public static Retrofit getClient()
    {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
