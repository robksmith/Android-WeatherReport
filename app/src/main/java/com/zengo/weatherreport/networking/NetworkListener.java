package com.zengo.weatherreport.networking;

/**
 * Created by Rob on 16/04/2017.
 */

public interface NetworkListener<T>
{
    void onResult(T object);

}
