package com.example.practica_puzzle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    Button btnGato,btnPuzzle,btnAhorcado;
    FragmentGato FragmentoGato;
    FragmentPuzzle FragmentoPuzzle;
    FragmentAhorcado FragmentoAhorcado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGato = findViewById(R.id.btnGato);
        btnPuzzle = findViewById(R.id.btnPuzzle);
        btnAhorcado = findViewById(R.id.btnAhorcado);

        FragmentoGato = new FragmentGato();
        FragmentoPuzzle = new FragmentPuzzle();
        FragmentoAhorcado = new FragmentAhorcado();

        btnGato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trancision = getSupportFragmentManager().beginTransaction();
                trancision.replace(R.id.frgMain, FragmentoGato);
                trancision.commit();
            }
        });

        btnPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trancision = getSupportFragmentManager().beginTransaction();
                trancision.replace(R.id.frgMain, FragmentoPuzzle);
                trancision.commit();
            }
        });

        btnAhorcado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trancision = getSupportFragmentManager().beginTransaction();
                trancision.replace(R.id.frgMain, FragmentoAhorcado);
                trancision.commit();
            }
        });


    }
}