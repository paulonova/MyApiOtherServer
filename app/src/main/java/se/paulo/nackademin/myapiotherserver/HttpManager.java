package se.paulo.nackademin.myapiotherserver;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by paulo on 2016-05-16.
 */
public class HttpManager {

    Country country;

    public static String getData(String uri){

        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();

            //StringBuilder sb = new StringBuilder();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            return buffer.toString();

        }catch (Exception e){
            e.printStackTrace();
            return null;

        }finally {

            if(con != null){
                con.disconnect();
            }

            if(reader != null){
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }

}
