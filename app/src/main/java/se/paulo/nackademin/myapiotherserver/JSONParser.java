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

    public static List<Country> parseFeed(String content){  //************************************** DON´T WORK

        countryList = new ArrayList<>();
        Country country;

        try {
            JSONObject jsonObject = new JSONObject(content);
            //JSONArray jsonArray = jsonObject.getJSONArray("");
            JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("name"));

            for (int i = 0; i < jsonArray.length() ; i++) {

                JSONObject actor = jsonArray.getJSONObject(i);
                country = new Country();
                country.setCountry(actor.getString("name"));
                country.setCapital(actor.getString("capital"));
                country.setPopulation(actor.getLong("population"));

                countryList.add(country);

            }

            return countryList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Other solution..
    public static List<Country> parseFeed2(String content){ // *************************************SHOW INFORMATIONS IN EXCEPTIONS...
        countryList = new ArrayList<>();
        Country country;

        try {
            JSONArray jsonArray = new JSONArray(content.toString());

            for (int i = 0; i < jsonArray.length() ; i++) {

                JSONObject actor = jsonArray.getJSONObject(i);
                country = new Country();
                country.setCountry(actor.getString("name"));
                country.setCapital(actor.getString("capital"));
                country.setPopulation(actor.getLong("population"));

                countryList.add(country);
            }

            return countryList;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    // Other solution..
    public static List<Country> parseFeed3(String content){
        countryList = new ArrayList<>();
        Country country;


        return null;
    }
}
