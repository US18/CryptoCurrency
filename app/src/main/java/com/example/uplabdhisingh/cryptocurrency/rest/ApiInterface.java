package com.example.uplabdhisingh.cryptocurrency.rest;

import com.example.uplabdhisingh.cryptocurrency.model.CryptoDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by uplabdhisingh on 15/03/18.
 */

public interface ApiInterface
{
    @GET("ticker/")
    Call<List<CryptoDetails>> getCryptoDetails();
}
