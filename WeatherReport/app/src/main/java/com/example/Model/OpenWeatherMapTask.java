package com.example.Model;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by intgawade20 on 7/4/2016.
 */
public class OpenWeatherMapTask extends AsyncTask<Void, Void, String> {
    String cityName;
   weatherinfo weather_info;

    String dummyAppid = "7d04bf41ec7dd47495f52d1648c2ae58";
    String queryWeather = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    String queryDummyKey = "&appid=" + dummyAppid;
    String cnt="&cnt="+14;
    String unit="&units=metric";

   public OpenWeatherMapTask(String cityName,weatherinfo inweather_info){
        this.cityName = cityName;
        this.weather_info=inweather_info;
    }

    @Override
    protected String doInBackground(Void... params) {
        String result = "";
        String queryReturn;

        String query = null;
        try {
            query = queryWeather + URLEncoder.encode(cityName, "UTF-8") + cnt + unit + queryDummyKey;
            Log.d("-----City--------------",query.toString());
            queryReturn = sendQuery(query);

            result += ParseJSON(queryReturn);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            queryReturn = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            queryReturn = e.getMessage();
        }

        return result;
    }
    @Override
    protected void onPostExecute(String s) {
        weather_info.setName(this.cityName);
        weather_info.setcInfo(s);
        weather_info.setLoading(false);
    }
    private String sendQuery(String query) throws IOException {
        String result = "";

        URL searchURL = new URL(query);

        HttpURLConnection httpURLConnection = (HttpURLConnection)searchURL.openConnection();
        if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader,
                    8192);

            String line = null;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            bufferedReader.close();
        }

        return result;
    }

    private String ParseJSON(String json){
        String jsonResult = "";

        try {
            JSONObject JsonObject = new JSONObject(json);
            String cod = jsonHelperGetString(JsonObject, "cod");
            if(cod != null){
                if(cod.equals("200")){

                    // Display City Information
                    JSONObject city = jsonHelperGetJSONObject(JsonObject, "city");
                    if(city != null){
                        String id = jsonHelperGetString(city, "id");
                        String name = jsonHelperGetString(city, "name");
                        String country=jsonHelperGetString(city, "country");
                        jsonResult += "id: " + id + "\n";
                        jsonResult += "city: " + name + "\n";
                        jsonResult += "country: " + country + "\n";
                    }
                    jsonResult += "\n";
                    //Display 14 Days weather information
                    JSONArray weatherof14Days = jsonHelperGetJSONArray(JsonObject, "list");
                    if(weatherof14Days != null){
                        for(int i=0; i<weatherof14Days.length(); i++){
                            JSONObject thisWeather = weatherof14Days.getJSONObject(i);
                            jsonResult += "weather for Day " + (i+1) + ":\n";
                            JSONArray weather = jsonHelperGetJSONArray(thisWeather, "weather");
                            if(weather !=null)
                            {
                                for(int j=0; j<weather.length(); j++){
                                    JSONObject TodaysWeather = weather.getJSONObject(j);
                                    jsonResult += "main: " + jsonHelperGetString(TodaysWeather, "main") + "\n";
                                    jsonResult += "description:" + jsonHelperGetString(TodaysWeather, "description") + "\n";
                                }
                            }
                            jsonResult += "pressure: " + jsonHelperGetString(thisWeather, "pressure") + "\n";
                            jsonResult += "humidity: " + jsonHelperGetString(thisWeather, "humidity") + "\n";
                            jsonResult += "speed: " + jsonHelperGetString(thisWeather, "speed") + "\n";
                            jsonResult += "deg: " + jsonHelperGetString(thisWeather, "deg") + "\n";
                            jsonResult += "clouds: " + jsonHelperGetString(thisWeather, "clouds") + "\n";
                            jsonResult += "\n";
                        }
                    }

                }else if(cod.equals("404")){
                    String message = jsonHelperGetString(JsonObject, "message");
                    jsonResult += "cod 404: " + message;
                }
            }else{
                jsonResult += "cod == null\n";
            }

        } catch (JSONException e) {
            e.printStackTrace();
            jsonResult += e.getMessage();
        }

        return jsonResult;
    }
    private String jsonHelperGetString(JSONObject obj, String k){
        String v = null;
        try {
            v = obj.getString(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
    private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k){
        JSONObject o = null;

        try {
            o = obj.getJSONObject(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return o;
    }
    private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k){
        JSONArray a = null;

        try {
            a = obj.getJSONArray(k);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return a;
    }


}
