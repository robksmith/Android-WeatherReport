package com.zengo.weatherreport.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zengo.weatherreport.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rob on 16/04/2017.
 * An adapter used to populate our recyclerview
 */
public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    ArrayList<DateTimeEntry> data;

    public WeatherAdapter(Context context, ArrayList<DateTimeEntry> data)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.weather_item, parent, false);
        WeatherItemHolder holder = new WeatherItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        // Get current position of item in recyclerview to bind data and assign values from list
        WeatherItemHolder weatherItemHolder = (WeatherItemHolder) holder;

        // Get the data
        DateTimeEntry current = data.get(position);

        // If date is for today, make background red
        if ( DatesHelper.isToday(current.date))
        {
            weatherItemHolder.weatherLayout.setBackground(context.getResources().getDrawable(R.drawable.background_day_1));
        }
        else
        {
            weatherItemHolder.weatherLayout.setBackground(context.getResources().getDrawable(R.drawable.background_day_2));
        }

        // could colour code weather items differently depending on day, weather wind wpeed etc....


        // Get our weather date in Date format
        Date date = DatesHelper.getDate(current.date);

        // convert the weather date to the format we want displayed without the seconds
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm");
        String displayDate = format.format(date);

        // put our values in the holder
        weatherItemHolder.textDateTime.setText(displayDate);
        weatherItemHolder.textMain.setText(current.mainHeadline);
        weatherItemHolder.textDescription.setText(current.description);

        // Including the weather image created from the icon - use glide for this
        Glide.with(context).load("http://openweathermap.org/img/w/" + current.icon + ".png")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(weatherItemHolder.imageIcon);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    /**
     * A view holder for each of our weather items
     */
    class WeatherItemHolder extends RecyclerView.ViewHolder
    {
        LinearLayout weatherLayout;
        TextView textDateTime;
        TextView textDescription;
        ImageView imageIcon;
        TextView textMain;

        public WeatherItemHolder(View itemView)
        {
            super(itemView);

            weatherLayout = (LinearLayout) itemView.findViewById(R.id.weatherLayout);
            textDateTime = (TextView) itemView.findViewById(R.id.textDateTime);
            textMain = (TextView) itemView.findViewById(R.id.textViewMain);
            textDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            imageIcon = (ImageView) itemView.findViewById(R.id.imageView1);
        }
    }

}




