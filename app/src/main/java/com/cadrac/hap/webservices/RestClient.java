package com.cadrac.hap.webservices;

import retrofit2.Retrofit;

public class RestClient {

    public static final String baseUrl = "http://toobworks.com/hap/";

    public static Retrofit client = new Retrofit.Builder().baseUrl(baseUrl).build();

}
