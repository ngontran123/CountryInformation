package com.example.countryinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.os.*;
import android.app.*;
import android.os.Bundle;
import android.net.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    Handler messageHandler=new Handler();
    ProgressDialog progressDialog;
    countryList countryListAdapter;
    List<Country> countryListCopy;
    List<Country> countries11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=(ListView) this.findViewById(R.id.myCountryList);
        countries11=new ArrayList<>();
        countryListCopy=new ArrayList<>();
        countryListAdapter=new countryList(MainActivity.this,countries11,countryListCopy);
        listView.setAdapter(countryListAdapter);
        listView.setFocusable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View v,int position,long id)
            {
                Country t1=countryListAdapter.getItem(position);
                Toast.makeText(MainActivity.this,"You have clicked "+t1.getCountryName(),Toast.LENGTH_LONG).show();
                Intent t=new Intent(MainActivity.this,CountrydetailActivity.class);
                t.putExtra("Country",t1);
                startActivity(t);
            }
        });
                new fetchData().start();
    }

    class fetchData extends Thread{
        @Override
        public void run()
        {   messageHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Fetching data");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });
            String data="";
            Log.i("Thread","is running");
            try {
                URL url = new URL("http://api.geonames.org/countryInfoJSON?username=caoth");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    data += line;
                }
                if(!data.isEmpty())
                {
                    JSONObject object=new JSONObject(data);
                    JSONArray countries=object.getJSONArray("geonames");
                    countries11.clear();
                    for(int i=0;i<countries.length();i++)
                    {
                        JSONObject country=countries.getJSONObject(i);
                        String countryName=country.getString("countryName");
                        String countryImage=country.getString(("countryCode")).toLowerCase();
                        String Url=String.format("http://img.geonames.org/flags/x/%s.gif",countryImage);
                        Country country1=new Country();
                        String capital=country.getString("capital");
                        String population=country.getString("population");
                        String area=country.getString("areaInSqKm");
                        String map=String.format("http://img.geonames.org/img/country/250/%s.png",countryImage.toUpperCase());
                        country1.setPopulation(population);
                        country1.setCapital(capital);
                        country1.setAreaIntSqrKm(area);
                        country1.setMapUrl(map);
                        Log.i("population:",population);
                        country1.setCountryName(countryName);
                        country1.setImgUrl(Url);
                        countries11.add(country1);
                        countryListCopy.add(country1);
                    }
                }

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            messageHandler.post(new Runnable() {
                @Override
                public void run() {

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                        countryListAdapter.notifyDataSetChanged();
                    }
                }
            });


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item=menu.findItem(R.id.nav_search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setQueryHint("Type country name here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}