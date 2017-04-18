## Introduction

This simple Android app uses the OpenWeatherMap 5 day weather forecast API to retrieve the current 5 day weather forecast and display a part of it in a RecyclerView.

## Running and Building

This app was built with Android Studio version 2.3.1.
Before building and running, please insert your own Open Weather Api key in NetworkManager.java by altering the OPEN_WEATHER_API String.  

## Testing

I tested the app on my LG G2 and Samsung Galaxy S6.

## Tidying up

4 hours is not a long time to come up with something good. Stuff was thrown together quickly here. The following is a list of things that need cleaning up:

* Strings are hardcoded
* Comments are lacking
* No error checking
* Exceptions not handled correctly
* Inconsistent variable naming
* Tidy up code
* Probably loads more...

## More features with more time

Time ran out just when lots more features could be added such as:

* Use a different View for each of the 5 days. At present all the days are just displayed in one big view - we really need a date selector or something like 5 scroll views which can be scrolled horizontally not vertically. This would mean we could fit all 5 days on screen.
* Implement pull to refresh possibly
* Display far more data from the API such as wind, temperatures etc
* Be able to dig down on a date to display even more info that won't fit on screen
* Formatting is a thrown together mess - get this designed properly
* Detect GPS and use that instead of hardcoding
* Implement Navigation drawer, Settings etc
* Implement nice error popups
* tons more...

## Third party libraries used

* Volley
* Glide

## Author

**Rob Smith** - (https://github.com/robksmith)



