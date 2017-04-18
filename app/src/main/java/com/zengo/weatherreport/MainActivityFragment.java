package com.zengo.weatherreport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zengo.weatherreport.Helpers.WeatherAdapter;
import com.zengo.weatherreport.networking.NetworkListener;
import com.zengo.weatherreport.networking.NetworkManager;

import java.util.ArrayList;

/**
 * A fragment containing our recycler view to be populated with weather data
 */
public class MainActivityFragment extends Fragment
{
    private RecyclerView recyclerView;

    public MainActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Get our singleton network manager
        NetworkManager networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());

        // inflate the view
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // find the debug feedback view
        final TextView debugFeedback = (TextView) view.findViewById(R.id.textViewFeedback);

        // find the recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewForWeather);

        // set the required layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        // make the GET call to the API passing in our success/failure listener
        networkManager.GETWeather(new NetworkListener<ArrayList>()
        {
            @Override
            public void onResult(ArrayList object)
            {
                debugFeedback.setText("ok");

                // Create an adapter using our result set
                WeatherAdapter weatherAdapter = new WeatherAdapter(getActivity(), object);

                // And then give it to the recycler view
                recyclerView.setAdapter(weatherAdapter);
            }
        }, new NetworkListener()
        {
            @Override
            public void onResult(Object object)
            {
                debugFeedback.setText("failed - did you put your key in NetworkManager.java?");
            }
        });

        return view;
    }

}
