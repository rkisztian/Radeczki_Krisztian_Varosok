package com.example.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class InsertActivity extends AppCompatActivity {

    private Button buttonAdd, buttonVissza1;
    private EditText editTextNev,editTextOrszag,editTextLakossag;
    private String url = "https://retoolapi.dev/saorCc/varosok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();
        buttonVissza1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lak =  editTextLakossag.getText().toString();
                String varos = editTextNev.getText().toString();
                String orszag = editTextOrszag.getText().toString();
                if (varos.isEmpty()) {
                    Toast.makeText(InsertActivity.this,
                            "A Város mező nem lehet ures", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (orszag.isEmpty()) {
                    Toast.makeText(InsertActivity.this,
                            "Az Ország mező nem lehet ures", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lak.isEmpty()) {
                    Toast.makeText(InsertActivity.this,
                            "A lakosság mező nem lehet ures", Toast.LENGTH_SHORT).show();
                    return;
                }
                Gson conv = new Gson();
                Varos varoska = new Varos(0, varos, orszag, Integer.parseInt(lak));
                RequestTask task = new RequestTask(url, "POST", conv.toJson(varoska) );
                task.execute();
            }
        });
    }

    private void init(){
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonVissza1 = findViewById(R.id.buttonVissza1);
        editTextNev = findViewById(R.id.editTextNev);
        editTextOrszag = findViewById(R.id.editTextOrszag);
        editTextLakossag = findViewById(R.id.editTextLakossag);
        editTextLakossag.setText("0");

    }

    public void urlapAlaphelyzetbe() {
        editTextLakossag.setText("");
        editTextOrszag.setText("");
        editTextNev.setText("");
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
                Toast.makeText(InsertActivity.this,
                        e.toString(), Toast.LENGTH_SHORT).show();
            }
            return response;
        }



        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            Gson converter = new Gson();

            switch (requestType) {
                case "POST":
                    Toast.makeText(InsertActivity.this, "Sikeres felvétel", Toast.LENGTH_SHORT).show();
                    urlapAlaphelyzetbe();
                    break;

            }
        }
    }

}
