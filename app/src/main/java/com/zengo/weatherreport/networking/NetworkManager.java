package com.zengo.weatherreport.networking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zengo.weatherreport.Helpers.DateTimeEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rob on 16/04/2017.
 * Google recommended singleton to look after the volley request queue
 * I put the GET and json parse in here
 */
public class NetworkManager
{
    // The Open weather API key
    private static String OPEN_WEATHER_API = "put yours here";

    // Hardcoded 5 day weather forecast GET call set to Liverpool
    private static String OPEN_WEATHER_CALL = "http://api.openweathermap.org/data/2.5/forecast?q=Liverpool,gb&mode=json&appid=" + OPEN_WEATHER_API;

    private static NetworkManager instance;

    private RequestQueue requestQueue;

    private static Context context;


    private NetworkManager(Context context)
    {
        NetworkManager.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized NetworkManager getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new NetworkManager(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
        {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }

    /**
     * Make a GET call to the OpenWeather 5 day forecast
     * @param okListener
     * @param errorListener
     */
    public void GETWeather(final NetworkListener<ArrayList> okListener, final NetworkListener errorListener)
    {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, OPEN_WEATHER_CALL, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    // Parse our json response and put it into a data structure of our choosing - at the moment just an arrayList
                    ArrayList result = parseWeatherObject(response);

                    okListener.onResult(result);
                }
                catch (JSONException e)
                {
                    // For now, if something goes wrong just send nothing back - although we should send something sensible back
                    errorListener.onResult(null);

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // For now, if something goes wrong just send nothing back - although we should send something sensible back
                errorListener.onResult(null);
            }
        }
        );

        addToRequestQueue(jsObjRequest);
    }

    /**
     * Given the json response, build an arraylist of our own object
     * @param json
     * @return
     * @throws JSONException
     */
    private ArrayList parseWeatherObject(JSONObject json)
            throws JSONException
    {
        ArrayList arrayList = new ArrayList();

        //getting the list node from the json
        JSONArray list=json.getJSONArray("list");

        // Now iterate through each one creating our data structure and grabbing the info we need
        for(int i=0;i<list.length();i++)
        {
            // Create a new instance of our
            DateTimeEntry dtEntry = new DateTimeEntry();

            // Get the dateTime object
            JSONObject dtItem = list.getJSONObject(i);

            // pull out the date and put it in our own data
            dtEntry.date = dtItem.getString("dt_txt");

            // Now go for the weather object
            JSONArray weatherArray = dtItem.getJSONArray("weather");
            JSONObject ob = (JSONObject) weatherArray.get(0);

            // Grab what we are interested in
            dtEntry.mainHeadline = ob.getString("main");
            dtEntry.description = ob.getString("description");
            dtEntry.icon = ob.getString("icon");

            arrayList.add(dtEntry);
        }

        return arrayList;
    }
}
