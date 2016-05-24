package se.paulo.nackademin.myapiotherserver;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulo Vila Nova on 2016-05-17.
 */
public class JSONParser {

    public static List<Country> countryList;

    public static List<Country> parseFeed(String content){

        Log.e("TESTING CONTENT:","" + content);
        countryList = new ArrayList<>();
        Country country;

            //Starting with JSONArray..
            try {
                JSONArray parentArray = new JSONArray(content);
                for (int i = 0; i < parentArray.length() ; i++) {
                    country = new Country();
                    JSONObject jsonObject = parentArray.getJSONObject(i);
                    country.setCountry(jsonObject.getString("name"));
                    country.setCapital(jsonObject.getString("capital"));
                    country.setPopulation(jsonObject.getLong("population"));
                    countryList.add(country);
                }
                return countryList;

            } catch (JSONException e) {
                Log.i("TESTING 1:","Not starting with JSONArray");
                e.printStackTrace();
            }


            //Starting with JSONObject..
            try {
                country = new Country();
                JSONObject parentObject = new JSONObject(content);
                country.setCountry(parentObject.getString("name"));
                country.setCapital(parentObject.getString("capital"));
                country.setPopulation(parentObject.getLong("population"));

                countryList.add(country);
                return countryList;

            } catch (JSONException e) {
                Log.i("TESTING 2:","Not starting with JSONObject");
                e.printStackTrace();
            }


        return null;
    }


}
