package se.paulo.nackademin.myapiotherserver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView showCountries, showPopulation, showCapital;
    Button findRegion, findPopulation, findCapital;
    EditText wRegion, wCountry, wCapital;
    ProgressBar pb;

    List<MyTask> tasks;
    List<Country> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tasks = new ArrayList<>();

        //ProgressBar
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        //TextView
        showCountries = (TextView)findViewById(R.id.txtShowCountries);
        showPopulation = (TextView)findViewById(R.id.txtShowPopulation);
        showCapital = (TextView)findViewById(R.id.txtShowCountry);

        //Buttons
        findRegion = (Button)findViewById(R.id.btnFindCountries);
        findRegion.setOnClickListener(this);
        findPopulation = (Button)findViewById(R.id.btnFindPopulation);
        findPopulation.setOnClickListener(this);
        findCapital = (Button)findViewById(R.id.btnFindCapital);
        findCapital.setOnClickListener(this);

        //EditText
        wRegion = (EditText)findViewById(R.id.edtRegion);
        wCountry = (EditText)findViewById(R.id.edtCountry);
        wCapital = (EditText)findViewById(R.id.edtCapital);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean isOnLine(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        //Checking the Connection..
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    //Code to request the informations
    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
        //task.execute(uri);
    }

    protected void updateDisplay(){
//        output.append(message + "\n");


        if(countryList != null){

            //Getting just one random object
            Country country = countryList.get(0);
            showCountries.setText(country.getCountryName());
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnFindCountries:

                if(isOnLine()){
                    requestData("https://restcountries.eu/rest/v1/region/africa");
                }else {
                    Toast.makeText(getApplicationContext(), "Network isn´t available!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnFindPopulation:
                if(isOnLine()){
                    requestData("https://restcountries.eu/rest/v1/alpha/col");
                }else {
                    Toast.makeText(getApplicationContext(), "Network isn´t available!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnFindCapital:
                if(isOnLine()){
                    requestData("https://restcountries.eu/rest/v1/alpha/col");
                }else {
                    Toast.makeText(getApplicationContext(), "Network isn´t available!", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }


    // Params, Progress, Result need to be declared
    private class MyTask extends AsyncTask<String, String, String> {

        //Execute before doInBackground()
        @Override
        protected void onPreExecute() {

            if(tasks.size() == 0){
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);

        }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            //bookList = JSONParse.parseFeed(result);
            updateDisplay();

            tasks.remove(this);
            if(tasks.size() == 0){
                pb.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        protected void onProgressUpdate(String... values) {
//            updateDisplay(values[0]);
        }
    }
}
