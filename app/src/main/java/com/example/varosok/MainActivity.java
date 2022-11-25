package com.example.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonListazas, buttonUjFelvetel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init(){
        buttonListazas = findViewById(R.id.buttonListazas);
        buttonUjFelvetel = findViewById(R.id.buttonUjFelvetel);
    }
}