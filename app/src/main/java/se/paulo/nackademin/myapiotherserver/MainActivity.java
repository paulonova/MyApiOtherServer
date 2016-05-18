package se.paulo.nackademin.myapiotherserver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView showResults;
    Button findRegion, findPopulation, findCapital;
    EditText wRegion;
    ProgressBar pb;

    private String regionName;
    private String countryToCapital;
    private String countryToPopulation;


    List<MyTask> tasks;
    List<Country> countryList;
    Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //hiding the soft-keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tasks = new ArrayList<>();

        //ProgressBar
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        //pb.setVisibility(View.GONE);

        //TextView
        showResults = (TextView)findViewById(R.id.txtShowResults);
        //showResults.setMovementMethod(new ScrollingMovementMethod());


        //Buttons
        findRegion = (Button)findViewById(R.id.btnFindCountries);
        findRegion.setOnClickListener(this);
        findPopulation = (Button)findViewById(R.id.btnFindPopulation);
        findPopulation.setOnClickListener(this);
        findCapital = (Button)findViewById(R.id.btnFindCapital);
        findCapital.setOnClickListener(this);

        //EditText
        wRegion = (EditText)findViewById(R.id.edtRegion);


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
        if (id == R.id.action_clear) {
            showResults.setText(R.string.show_results);
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

    //protected void updateDisplay(String message){
    protected void updateDisplay(){

        if(countryList != null){

            for (int i = 0; i <countryList.size() ; i++) {
                country = new Country();
                country = countryList.get(i);
                showResults.append(i + ": " + country.getCountry() + "\n");
            }

        }else {
            showResults.setText("there is no result for such name!");

            //showResults.append(message + "\n");
        }

//        if(countryList != null){
//
//            //Getting just one random object
//            Country country = countryList.get(0);
//            //showResults.setText(country.getCountryName());
//            showResults.setText("TESTING...");
//        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnFindCountries:
                //Getting text from EditText..
                regionName = wRegion.getText().toString();
                showResults.setText("");
                if(isOnLine()){

                    if(!regionName.isEmpty() && regionName != null){
                        requestData("https://restcountries.eu/rest/v1/region/" + regionName.toLowerCase());
                    }else{
                        Toast.makeText(getApplicationContext(), "Empty field! try again..", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Network isn´t available!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnFindPopulation:
                //Getting text from EditText..
                countryToPopulation = wRegion.getText().toString();
                showResults.setText("");
                if(isOnLine()){
                    if(!countryToPopulation.isEmpty()){
                        requestData("https://restcountries.eu/rest/v1/alpha/" + countryToPopulation.toLowerCase());
                    }else {
                        Toast.makeText(getApplicationContext(), "Empty field! try again..", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Network isn´t available!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnFindCapital:
                //Getting text from EditText..
                countryToCapital = wRegion.getText().toString();
                showResults.setText("");
                if(isOnLine()){
                    if(!countryToCapital.isEmpty()){
                        requestData("https://restcountries.eu/rest/v1/alpha/" + countryToCapital.toLowerCase());
                    }else{
                        Toast.makeText(getApplicationContext(), "Empty field! try again..", Toast.LENGTH_LONG).show();
                    }

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
            updateDisplay();

            if(tasks.size() == 0){
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);

        }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);
            if(content != null){
                return content;
            }
             return "";
        }

        @Override
        protected void onPostExecute(String result) {
            countryList = JSONParser.parseFeed3(result);
            updateDisplay();

            tasks.remove(this);
            if(tasks.size() == 0){
                pb.setVisibility(View.GONE);
            }

        }

        @Override
        protected void onProgressUpdate(String... values) {
            //updateDisplay(values[0]);
        }
    }
}
