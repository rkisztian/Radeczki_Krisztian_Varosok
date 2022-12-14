package com.example.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

public class ListActivity extends AppCompatActivity {
    private Button buttonVissza;
    private TextView textViewVaros;
    private String url = "https://retoolapi.dev/saorCc/varosok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    private void init() {
        buttonVissza = findViewById(R.id.buttonVissza);
        textViewVaros = findViewById(R.id.textViewVaros);
        RequestTask req = new RequestTask(url, "GET");
        req.execute();
    }

    private class RequestTask extends AsyncTask<Void, Void, Response> {
        String requestUrl;
        String requestType;
        String requestParams;

        public RequestTask(String requestUrl, String requestType, String requestParams) {
            this.requestUrl = requestUrl;
            this.requestType = requestType;
            this.requestParams = requestParams;
        }

        public RequestTask(String requestUrl, String requestType) {
            this.requestUrl = requestUrl;
            this.requestType = requestType;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                switch (requestType) {
                    case "GET":
                        response = RequestHandler.get(requestUrl);
                        break;
                    case "POST":
                        response = RequestHandler.post(requestUrl, requestParams);
                        break;
                    case "PUT":
                        response = RequestHandler.put(requestUrl, requestParams);
                        break;
                    case "DELETE":
                        response = RequestHandler.delete(requestUrl + "/" + requestParams);
                        break;
                }

            } catch (IOException e) {
                Toast.makeText(ListActivity.this,
                        e.toString(), Toast.LENGTH_SHORT).show();
            }
            return response;
        }


        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            Gson converter = new Gson();

            switch (requestType) {
                case "GET":
                    Varos[] varosArray = converter.fromJson(response.getContent(), Varos[].class);
                    textViewVaros.setText("");
                    StringBuilder sr = new StringBuilder();
                    for (Varos varos : varosArray) {
                        sr.append(varos.getNev() + " " + varos.getOrszag() + " " + varos.getLakossag() + System.lineSeparator());
                    }
                    textViewVaros.setText(sr.toString());
                    break;
                case "POST":
                    //Person person = converter.fromJson(response.getContent(), Person.class);
                    //people.add(0, person);
                    //urlapAlaphelyzetbe();
                    break;

            }
        }
    }
}