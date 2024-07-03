package com.example.practica_puzzle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGato#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGato extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View vista;
    private static final int PLAYER_X = 1;
    private static final int PLAYER_0 = 2;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    private int currentPlayer;
    private Button[][] buttons;
    private boolean gameEnded;


    public FragmentGato() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGato.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGato newInstance(String param1, String param2) {
        FragmentGato fragment = new FragmentGato();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_gato, container, false);

        btn1 = (Button) vista.findViewById(R.id.btn1);
        btn2 = (Button) vista.findViewById(R.id.btn2);
        btn3 = (Button) vista.findViewById(R.id.btn3);
        btn4 = (Button) vista.findViewById(R.id.btn4);
        btn5 = (Button) vista.findViewById(R.id.btn5);
        btn6 = (Button) vista.findViewById(R.id.btn6);
        btn7 = (Button) vista.findViewById(R.id.btn7);
        btn8 = (Button) vista.findViewById(R.id.btn8);
        btn9 = (Button) vista.findViewById(R.id.btn9);

        buttons = new Button[][]{
                {btn1, btn2, btn3},
                {btn4, btn5, btn6},
                {btn7, btn8, btn9}
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClick((Button) v);
                    }
                });
            }
        }

        currentPlayer = PLAYER_X; // Empezar con el jugador X
        gameEnded = false;

        return vista;
    }

    private void onButtonClick(Button button){
        if (button.isEnabled()&& !gameEnded){
            button.setText(currentPlayer == PLAYER_X ? "X" : "0");

            if (checkForWin()) {
                gameEnded = true;
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn3.setEnabled(false);
                btn4.setEnabled(false);
                btn5.setEnabled(false);
                btn6.setEnabled(false);
                btn7.setEnabled(false);
                btn8.setEnabled(false);
                btn9.setEnabled(false);
                Toast.makeText(getActivity(), "Jugador " + (currentPlayer == PLAYER_X ? "X" : "O") + " ha ganado!", Toast.LENGTH_SHORT).show();
            }else {
                // Cambiar al siguiente jugador
                button.setEnabled(false);
                currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_0 : PLAYER_X;
            }
        }
    }
    private boolean checkForWin() {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][0].getText().equals(buttons[i][2].getText()) &&
                    !buttons[i][0].getText().toString().isEmpty()) {
                return true;
            }
        }
        // Verificar columnas
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[0][i].getText().equals(buttons[2][i].getText()) &&
                    !buttons[0][i].getText().toString().isEmpty()) {
                return true;
            }
        }

        // Verificar diagonales
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().toString().isEmpty()) {
            return true;
        }

        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[0][2].getText().equals(buttons[2][0].getText()) &&
                !buttons[0][2].getText().toString().isEmpty()) {
            return true;
        }

        // Si no hay ganador
        return false;
    }

}