package com.example.practica_puzzle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentAhorcado extends Fragment {

    private EditText editPalabraSecreta;
    private EditText editLetra;
    private TextView textPalabra;
    private GridLayout gridAhorcado;
    private Button btnVida1, btnVida2, btnVida3, btnVida4, btnVida5, btnVida6;
    private String palabraSecreta;
    private List<Character> letrasAdivinadas;
    private int vidasRestantes = 6;
    private View view;

    public FragmentAhorcado() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ahorcado, container, false);

        editPalabraSecreta = view.findViewById(R.id.editPalabraSecreta);
        textPalabra = view.findViewById(R.id.textPalabra);
        editLetra = view.findViewById(R.id.editLetra);
        gridAhorcado = view.findViewById(R.id.gridAhorcado);

        // Inicialización de los botones de vida
        btnVida1 = view.findViewById(R.id.btnVida1);
        btnVida2 = view.findViewById(R.id.btnVida2);
        btnVida3 = view.findViewById(R.id.btnVida3);
        btnVida4 = view.findViewById(R.id.btnVida4);
        btnVida5 = view.findViewById(R.id.btnVida5);
        btnVida6 = view.findViewById(R.id.btnVida6);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnIniciarJuego).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIniciarJuego(v);
            }
        });

        view.findViewById(R.id.btnEnviarLetra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBotonAdivinar(v);
            }
        });
    }

    public void onClickIniciarJuego(View view) {
        palabraSecreta = editPalabraSecreta.getText().toString().toUpperCase();
        if (palabraSecreta.isEmpty()) {
            Toast.makeText(getContext(), "Ingresa una palabra secreta", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aquí ocultamos el EditText de la palabra secreta
        editPalabraSecreta.setVisibility(View.GONE);

        iniciarJuego();
        editPalabraSecreta.setEnabled(false);
        view.setEnabled(false);
    }

    private void iniciarJuego() {
        letrasAdivinadas = new ArrayList<>();
        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        StringBuilder palabraMostrada = new StringBuilder();

        for (int i = 0; i < palabraSecreta.length(); i++) {
            char letra = palabraSecreta.charAt(i);
            if (letrasAdivinadas.contains(letra)) {
                palabraMostrada.append(letra);
            } else {
                palabraMostrada.append("_ ");
            }
        }

        textPalabra.setText(palabraMostrada.toString().trim());
    }

    public void onClickBotonAdivinar(View view) {
        if (palabraSecreta == null) {
            Toast.makeText(getContext(), "Primero inicia el juego con una palabra secreta", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editLetra.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Ingrese una letra", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir la letra ingresada a mayúscula
        char letraIngresada = Character.toUpperCase(editLetra.getText().charAt(0));
        editLetra.setText("");

        if (letrasAdivinadas.contains(letraIngresada)) {
            Toast.makeText(getContext(), "Ya has ingresado esa letra", Toast.LENGTH_SHORT).show();
            return;
        }

        if (palabraSecreta.indexOf(letraIngresada) >= 0) {
            letrasAdivinadas.add(letraIngresada);
        } else {
            vidasRestantes--;
            actualizarGraficoAhorcado();
        }

        actualizarInterfaz();

        if (haGanado()) {
            Toast.makeText(getContext(), "¡Has ganado!", Toast.LENGTH_SHORT).show();
            reiniciarJuego();
        } else if (haPerdido()) {
            Toast.makeText(getContext(), "¡Has perdido! La palabra era: " + palabraSecreta, Toast.LENGTH_SHORT).show();
            reiniciarJuego();
        }
    }

    private void actualizarGraficoAhorcado() {
        switch (vidasRestantes) {
            case 5:
                btnVida6.setEnabled(false);
                break;
            case 4:
                btnVida5.setEnabled(false);
                break;
            case 3:
                btnVida4.setEnabled(false);
                break;
            case 2:
                btnVida3.setEnabled(false);
                break;
            case 1:
                btnVida2.setEnabled(false);
                break;
            case 0:
                btnVida1.setEnabled(false);
                break;
        }
    }

    private boolean haGanado() {
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (!letrasAdivinadas.contains(palabraSecreta.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean haPerdido() {
        return vidasRestantes <= 0;
    }

    private void reiniciarJuego() {
        letrasAdivinadas.clear();
        vidasRestantes = 6;
        palabraSecreta = null;
        textPalabra.setText("");
        editPalabraSecreta.setText("");
        editPalabraSecreta.setVisibility(View.VISIBLE); // Mostrar de nuevo el EditText de la palabra secreta
        editPalabraSecreta.setEnabled(true);
        getView().findViewById(R.id.btnIniciarJuego).setEnabled(true);
        actualizarGraficoAhorcado();
    }
}
