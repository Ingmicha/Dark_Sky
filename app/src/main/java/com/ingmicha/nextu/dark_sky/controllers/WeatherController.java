package com.ingmicha.nextu.dark_sky.controllers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ingmicha.nextu.dark_sky.views.IViewWeather;

/**
 * Created by Michael Alejandro Ura A DesarrolloApps on 5/4/18.
 */

public class WeatherController {

    public void getWeatherSource(final IViewWeather iViewWeather, Context context, String longitude,String latitude) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url =
                "https://api.darksky.net/forecast/893d253b611c04b67741a475d395f78e/"+
                        latitude+","+longitude+"?lang=es&exclude=hourly,flags,minutely&units=si";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iViewWeather.showWeatherInfo(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iViewWeather.errorMessage();
                    }
                });
        queue.add(stringRequest);
    }

}
