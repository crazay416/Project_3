package com.example.project_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Car_Data extends AppCompatActivity {

    private static String urlCarDetail = "https://thawing-beach-68207.herokuapp.com/cars/";

    static ArrayList<HashMap<String, String>> carDetails;

    TextView price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_data);

        carDetails = new ArrayList<>();

        Intent intent = getIntent();
        HashMap<String, String> carInfo = (HashMap<String, String>)intent.getSerializableExtra("car");



        new GetCarDetails(carInfo).execute();

    }


    private class GetCarDetails extends AsyncTask<Void, Void, Void>{

        private HashMap<String, String> carInfo = new HashMap<String, String>();
        private String currency;

        public GetCarDetails(HashMap carinfo){
            this.carInfo = carinfo;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(urlCarDetail + carInfo.get("id"));

            if(jsonStr != null){
                try{

                    JSONObject jsonObject;

                    JSONArray jsonArray = new JSONArray(jsonStr);

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject d = jsonArray.getJSONObject(i);
                        currency = d.getString("price");


                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            price = findViewById(R.id.price);
            price.setText(currency);



        }


        }
}