package com.example.vechiceserviceapp.Bikes;



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vechiceserviceapp.HomeActivity;
import com.example.vechiceserviceapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BikeDetailedActivity extends AppCompatActivity {

    private static String JSON_URL = "https://run.mocky.io/v3/5563ee77-5890-483c-8476-a386bb8c6239";


    List<Bikemodelclass> bikeList;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikedetailed);

        bikeList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Service station near by you");


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        GetData getData = new GetData();
        getData.execute();

        View buttonLayout = getLayoutInflater().inflate(R.layout.car_detaileditem, null);


        Button btnBookService = buttonLayout.findViewById(R.id.id_btnBookService);

        btnBookService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button Click", "Button clicked!");
                showBookingToast();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showBookingToast();
            }
        });

    }

    private void showBookingToast() {
        Toast toast = Toast.makeText(
                BikeDetailedActivity.this,
                "Please Click on Book A Service. You will get a call from the BIKE service center.",
                Toast.LENGTH_SHORT
        );
        toast.show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 240000);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private class GetData extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("bikes");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Bikemodelclass model = new  Bikemodelclass();
                    model.setId(jsonObject1.getString("id"));
                    model.setServicecentername(jsonObject1.getString("servicecentername"));
                    model.setDescription(jsonObject1.getString("description"));
                    model.setLocation(jsonObject1.getString("location"));
                    model.setContactno(jsonObject1.getString("contactno"));
                    model.setImage(jsonObject1.getString("image"));
                    model.setRatings(jsonObject1.getString("ratings"));
                    model.setRecommended(jsonObject1.getString("recommended"));
                    model.setTime(jsonObject1.getString("time"));
                    model.setSpeciality(jsonObject1.getString("speciality"));
                    model.setPickup(jsonObject1.getString("pickup"));
                    model.setCost(jsonObject1.getString("cost"));


                    bikeList.add(model);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(bikeList);

        }
    }

    private void PutDataIntoRecyclerView(List< Bikemodelclass> bikeList) {

        Adaptery adaptery = new Adaptery(this, bikeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptery);

    }
}



/////

