package com.example.practica_puzzle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.fragment.app.Fragment;
import androidx.core.widget.TextViewCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPuzzle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPuzzle extends Fragment {

    private GridLayout gridLayout;
    private Button[][] buttons;
    private int emptyRow = 2;
    private int emptyCol = 2;

    public FragmentPuzzle() {
        // Required empty public constructor
    }

    public static FragmentPuzzle newInstance(String param1, String param2) {
        FragmentPuzzle fragment = new FragmentPuzzle();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get fragment arguments if any
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_puzzle, container, false);
        gridLayout = view.findViewById(R.id.gridLayout);
        buttons = new Button[3][3];

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            numbers.add(i);
        }
        numbers.add(0); // 0 represents the empty space
        Collections.shuffle(numbers);

        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(getContext());
                button.setBackgroundResource(R.drawable.button_style);
                TextViewCompat.setTextAppearance(button, R.style.PuzzleButtonText);

                if (numbers.get(index) == 0) {
                    button.setBackgroundResource(R.drawable.empty_button_style); // Color para el botón vacío
                    button.setText("");
                    emptyRow = i;
                    emptyCol = j;
                } else {
                    button.setText(String.valueOf(numbers.get(index)));
                }

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveButton((Button) v);
                    }
                });

                buttons[i][j] = button;
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(i, 1, 1.0f);
                params.columnSpec = GridLayout.spec(j, 1, 1.0f);
                button.setLayoutParams(params);
                gridLayout.addView(button);
                index++;
            }
        }

        return view;
    }

    private void moveButton(Button button) {
        int row = -1;
        int col = -1;

        // Find the position of the clicked button
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j] == button) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        // Check if the button can move (if it is adjacent to the empty space)
        if ((Math.abs(row - emptyRow) == 1 && col == emptyCol) || (Math.abs(col - emptyCol) == 1 && row == emptyRow)) {
            // Swap the button text and background with the empty space
            buttons[emptyRow][emptyCol].setText(button.getText());
            buttons[emptyRow][emptyCol].setBackgroundResource(R.drawable.button_style);
            button.setText("");
            button.setBackgroundResource(R.drawable.empty_button_style);
            emptyRow = row;
            emptyCol = col;
        }
    }
}
