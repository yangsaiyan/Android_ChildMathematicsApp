package com.example.mobile_android_childmathematicsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Random;

public class ComposingNumbers extends Fragment {

    private int randomNumber, round = 1;
    private int[] selectedNumber;
    private int[] buttonNumbers;
    private FloatingActionButton backButton;
    private Button[] buttons;
    private TextView displayText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_composing_numbers, container, false);

        displayText = view.findViewById(R.id.toComposeNumber);

        buttons = new Button[]{
                view.findViewById(R.id.numberComposeButton1),
                view.findViewById(R.id.numberComposeButton2),
                view.findViewById(R.id.numberComposeButton3),
                view.findViewById(R.id.numberComposeButton4),
                view.findViewById(R.id.numberComposeButton5),
                view.findViewById(R.id.numberComposeButton6),
                view.findViewById(R.id.numberComposeButton7),
                view.findViewById(R.id.numberComposeButton8),
                view.findViewById(R.id.numberComposeButton9),
        };

        generateRandomNumber();

        for (Button button: buttons){
            button.setOnClickListener(v -> {

                buttonOnclick(button, view);
            });
        }

        backButton = view.findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Do you want to return to Home screen?")
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        requireActivity().onBackPressed();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        return view;
    }

    public void buttonOnclick(Button button, View view) {

        if(checkSelected(button)){
            onClickRemove(button);
        } else {

            for (int i = 0; i < selectedNumber.length; i++) {
                if (selectedNumber[i] == 0) {
                    selectedNumber[i] = Integer.parseInt(button.getText().toString());
                    break;
                }
            }
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondary));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.main));
        }
        checkAnswer(view);
    }

    //Remove selected button (uncheck)
    public void onClickRemove(Button button) {

        for (int i = 0; i < selectedNumber.length; i++) {
            if (selectedNumber[i] == Integer.parseInt(button.getText().toString())) {
                selectedNumber[i] = 0;
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.main));
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary));
                break;
            }
        }
    }

    //Select a button (check)
    public boolean checkSelected(Button button) {

        for (int number: selectedNumber){
            if(number == Integer.parseInt(button.getText().toString())){
                return true;
            }
        }
        return false;
    }

    //Generate a target number (Question)
    public void generateRandomNumber() {
        selectedNumber = new int[2];
        buttonNumbers = new int[9];
        if(round < 4) { //Difficulty Easy
            randomNumber = (int) (Math.random() * 10);
            //Target number cannot be 0, 1, 2 because random generate numbers will not duplicate, so re generate again when condition met
            if(randomNumber > 9 || randomNumber == 0 || randomNumber == 1 || randomNumber == 2) generateRandomNumber();
        } else if(round < 7){ //Difficulty Medium
            randomNumber = (int) (Math.random() * 100);
            if(randomNumber > 99 || randomNumber == 0) generateRandomNumber();
        } else { //Difficulty Hard
            randomNumber = (int) (Math.random() * 1000);
            if(randomNumber > 999 || randomNumber == 0) generateRandomNumber();
        }
        displayText.setText(String.valueOf(randomNumber));
        generateButtonNumbers();
    }

    //Generate a group (9 numbers) of numbers for players to select
    public void generateButtonNumbers() {
        String[] c = calAnswer().split("/");
        int a = Integer.parseInt(c[0]);
        int b = Integer.parseInt(c[1]);
        if(a + b < 3) { //if = 0, 1, 2 regenerate to prevent negative number of lesser than 3
            generateButtonNumbers();
        }

        if (round < 4) { // Difficulty Easy 1 - 9

            for (int i = 0; i < buttonNumbers.length; i++) {
                buttonNumbers[i] = i + 1;
            }
        } else if (round < 7) {// Difficulty Medium 1 - 99

            for (int i = 0; i < buttonNumbers.length; i++) {
                if (i == 0) buttonNumbers[i] = a;
                else if (i == 1) buttonNumbers[i] = b;
                else buttonNumbers[i] = (int) (Math.random() * 100);
            }
            checkDupAndNeg();
        } else {// Difficulty Hard 1 - 999

            for (int i = 0; i < buttonNumbers.length; i++) {
                if (i == 0) buttonNumbers[i] = a;
                else if (i == 1) buttonNumbers[i] = b;
                else buttonNumbers[i] = (int) (Math.random() * 1000);
            }
            checkDupAndNeg();
        }
        shuffleArray(buttonNumbers);
        printButtonText(buttons);
    }

    //To make sure generated numbers won't duplicate and are negative
    public void checkDupAndNeg(){
        for(int i = 0; i < buttonNumbers.length; i++){
            for(int j = 0; j < i; j++){
                if(i != j && buttonNumbers[i] == buttonNumbers[j] || buttonNumbers[i] <= 0){
                    generateButtonNumbers();
                }
            }
        }
    }

    //Logic to calculate two answers sum up to target number randomly, to make sure there are correct answers everytime
    public String calAnswer() {
        int c, d;
        if (round < 4) {
            c = (int) (Math.random() * 10);
            d = randomNumber - c;
        } else if (round < 7) {
            c = (int) (Math.random() * 100);
            d = randomNumber - c;
        } else {
            c = (int) (Math.random() * 1000);
            d = randomNumber - c;
        }

        return (String.valueOf(c) + "/" + String.valueOf(d));
    }

    //To shuffle the order of the array because answers are index 0, 1 everytime random numbers generated
    private void shuffleArray(int[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    //To print the button text (Number)
    public void printButtonText(Button[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(String.valueOf(buttonNumbers[i]));
        }
    }

    //To check answer of two selected button
    public void checkAnswer(View view) {

        if(selectedNumber[0] == 0 || selectedNumber[1] == 0) {
            return;
        }

        if(selectedNumber[0] + selectedNumber[1] == randomNumber){
            generateRandomNumber();
            round++;
            clearButton();
        } else {
            Snackbar.make(view, "Inorrect! Please select two numbers sum up to " + randomNumber + ".",
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    //Reset selected button style to default
    public void clearButton() {
        for (Button button: buttons){
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.main));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary));
        }
    }
}