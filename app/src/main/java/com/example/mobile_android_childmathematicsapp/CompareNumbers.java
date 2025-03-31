package com.example.mobile_android_childmathematicsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class CompareNumbers extends Fragment {

    private boolean isGreater;
    private int winStreak = 0;
    private int[] randomNumbers = new int[2];
    private TextView displaySelection;
    private TextView number1;
    private TextView number2;
    private MaterialButtonToggleGroup answer;
    private Button greaterButton, lesserButton, checkAnswer;
    private FloatingActionButton backButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_compare_numbers, container, false);

        displaySelection = view.findViewById(R.id.answerShow);
        number1 = view.findViewById(R.id.number1);
        number2 = view.findViewById(R.id.number2);
        greaterButton = view.findViewById(R.id.greaterButton);
        lesserButton = view.findViewById(R.id.lesserButton);
        answer = view.findViewById(R.id.answerButtonGroup);
        backButton = view.findViewById(R.id.backButton);

        generateRandomNumbers();

        checkAnswer = view.findViewById(R.id.checkAnswerButton);
        checkAnswer.setOnClickListener(v -> {
            checkAnswer(view);
        });

        backButton.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Do you want to return to Home screen?")
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        requireActivity().onBackPressed();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        answer.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.greaterButton) {

                    isGreater = true;
                    displaySelection.setText(greaterButton.getText());
                } else if (checkedId == R.id.lesserButton) {

                    isGreater = false;
                    displaySelection.setText(lesserButton.getText());
                }
            }
        });

        return view;
    }

    public void generateRandomNumbers() {

        for (int i = 0; i < randomNumbers.length; i++){

            randomNumbers[i] = (int) (Math.random() * 1000);

            if(randomNumbers[i] > 999){
                i--;
            }
        }

        number1.setText(String.valueOf(randomNumbers[0]));
        number2.setText(String.valueOf(randomNumbers[1]));
    }

    public void checkAnswer(View view) {

        boolean isCorrect;

        if(displaySelection.getText() == "?"){
            Snackbar.make(view, "Please select an answer!",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(isGreater){
            isCorrect = (randomNumbers[0] > randomNumbers[1]);
        }
        else {
            isCorrect = (randomNumbers[0] < randomNumbers[1]);
        }

        clearAll(isCorrect, view);
    }

    public void clearAll(boolean isCorrect, View view) {

        if(isCorrect){
            winStreak++;
            new android.os.Handler().postDelayed(() -> {
                generateRandomNumbers();
                answer.clearChecked();
                displaySelection.setText("?");
                isGreater = false;
            }, 1000);
            Snackbar.make(view, "Correct! Current Win Streak: " + winStreak + ".",
                    Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(view, "Inorrect! Resetting Win Streak...",
                    Snackbar.LENGTH_SHORT).show();
            winStreak = 0;
            return;
        }
    }
}