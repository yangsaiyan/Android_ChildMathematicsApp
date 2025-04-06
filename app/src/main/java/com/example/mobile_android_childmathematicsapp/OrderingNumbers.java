package com.example.mobile_android_childmathematicsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

public class OrderingNumbers extends Fragment {

    private boolean isAsc = true;
    private int round = 1, clickCount = 0;
    private int[] randomNumbers, playerNumbers;
    private TableRow[] tableRows;
    private TextView[] textViews;
    private Button[] buttons;
    private TextView difficulty, order;
    private FloatingActionButton backButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ordering_numbers, container, false);

        tableRows = new TableRow[]{
                view.findViewById(R.id.tableRow1),
                view.findViewById(R.id.tableRow2),
                view.findViewById(R.id.tableRow3),
                view.findViewById(R.id.tableRow4),
                view.findViewById(R.id.tableRow5),
                view.findViewById(R.id.tableRow6),
        };

        textViews = new TextView[]{
                view.findViewById(R.id.textView1),
                view.findViewById(R.id.textView2),
                view.findViewById(R.id.textView3),
                view.findViewById(R.id.textView4),
                view.findViewById(R.id.textView5),
                view.findViewById(R.id.textView6),
                view.findViewById(R.id.textView7),
                view.findViewById(R.id.textView8),
                view.findViewById(R.id.textView9),
        };

        buttons = new Button[]{
                view.findViewById(R.id.numberButton1),
                view.findViewById(R.id.numberButton2),
                view.findViewById(R.id.numberButton3),
                view.findViewById(R.id.numberButton4),
                view.findViewById(R.id.numberButton5),
                view.findViewById(R.id.numberButton6),
                view.findViewById(R.id.numberButton7),
                view.findViewById(R.id.numberButton8),
                view.findViewById(R.id.numberButton9),
        };

        backButton = view.findViewById(R.id.backButton);
        difficulty = view.findViewById(R.id.textViewDifficulty);
        order = view.findViewById(R.id.textViewOrder);

        generateRandomNumbers();

        for(Button button: buttons){
            button.setOnClickListener(v -> {
                clickAndDisplay((String) button.getText(), view, button);
            });
        }

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

    //Generate a group of random numbers according to current difficulty
    public void generateRandomNumbers() {

        generateRandomOrder();
        clickCount = 0;
        if(round <= 3){
            difficulty.setText("Difficulty: Easy");
            playerNumbers = new int[3]; //declare new int array
            randomNumbers = new int[3]; //declare new int array
            for (int i = 0; i < 3; i++){ //generate one row three numbers from 1 - 9
                int randomNumber;
                do {
                    randomNumber = (int) (Math.random() * 10);
                } while (randomNumber > 9 || randomNumber == 0);

                if (i < buttons.length) {
                    buttons[i].setText(String.valueOf(randomNumber));
                }
                randomNumbers[i] = randomNumber;
            }
        } else if(round <= 9){ //generate two rows six numbers from 1 - 99
            difficulty.setText("Difficulty: Medium");
            tableRows[1].setVisibility(View.VISIBLE);
            tableRows[4].setVisibility(View.VISIBLE);
            playerNumbers = new int[6];
            randomNumbers = new int[6];
            for (int i = 0; i < 6; i++){
                int randomNumber;
                do {
                    randomNumber = (int) (Math.random() * 100);
                } while (randomNumber > 99  || randomNumber == 0);

                if (i < buttons.length) {
                    buttons[i].setText(String.valueOf(randomNumber));
                }
                randomNumbers[i] = randomNumber;
            }
        } else { //generate threes rows nine numbers from 1 - 999
            difficulty.setText("Difficulty: Hard");
            tableRows[2].setVisibility(View.VISIBLE);
            tableRows[5].setVisibility(View.VISIBLE);
            playerNumbers = new int[9];
            randomNumbers = new int[9];
            for (int i = 0; i < 9; i++){
                int randomNumber;
                do {
                    randomNumber = (int) (Math.random() * 1000);
                } while (randomNumber > 999  || randomNumber == 0);

                if (i < buttons.length) {
                    buttons[i].setText(String.valueOf(randomNumber));
                }
                randomNumbers[i] = randomNumber;
            }
        }
        checkDuplicate();
    }

    //To check if there are any duplicate numbers
    public void checkDuplicate() {
        for (int randomNumber : randomNumbers) {
            int checkDup = 0;
            for (int number : randomNumbers) {
                if (randomNumber == number) {
                    checkDup++;
                }
            }
            if (checkDup > 1) generateRandomNumbers();
        }
    }

    //This function will change ? => (selected number) to display selected number
    public void clickAndDisplay(String buttonText, View view, Button button) {
        if(checkClicked(Integer.parseInt(buttonText))){ //if clicked a selected button, then uncheck it.
            removeClicked(Integer.parseInt(buttonText));
            clickCount--;
            redisplayTextview();
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.main));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary));
        } else { //if clicked a not selected button, then check it.
            playerNumbers[clickCount] = Integer.parseInt(buttonText);
            setTextViewDisplay(buttonText);
            clickCount++;
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondary));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.main));
        }
        if(randomNumbers.length == clickCount){ //if array length is equal to click count then will trigger check answer function to check answer
            checkAnswer(view);
        }
    }

    //This function make TextView to display selected button (Content which is a random generated number), so that players can see what they selected
    public void setTextViewDisplay(String buttonText) {
        textViews[clickCount].setText(buttonText);
    }

    //To check if selected button is clicked (checked - which means selected previously) or not
    public boolean checkClicked(int clickedNumber) {
        boolean isClicked = false;

        for(int i = 0; i < playerNumbers.length; i++){
            if(playerNumbers[i] == clickedNumber){
                isClicked = true;
            }
        }

        return isClicked;
    }

    //To remove selected button
    public void removeClicked(int clickedNumber) {
        int[] temp = new int[randomNumbers.length];
        int tempCount = 0;
        int tempCount2 = 0;
        while (tempCount2 < clickCount){
            if(playerNumbers[tempCount2] != clickedNumber) {
                temp[tempCount++] = playerNumbers[tempCount2++];
            } else {
                tempCount2++;
            }
        }
        playerNumbers = temp.clone();
    }

    //Redisplay the value again when there are changes
    public void redisplayTextview() {
        for (int i = 0; i < playerNumbers.length; i++){
            if(i < clickCount) {
                textViews[i].setText(String.valueOf(playerNumbers[i]));
            } else {
                textViews[i].setText("?");
            }
        }
    }

    //This functions checks answer according to current order
    public void checkAnswer(View view) {

        boolean isCorrect = true;

        if(isAsc) {
            Arrays.sort(randomNumbers);

            for (int i = 0; i < randomNumbers.length; i++){
                if(playerNumbers[i] != randomNumbers[i]){
                    isCorrect = false;
                    break;
                }
            }
        } else {
            Arrays.sort(randomNumbers);

            for (int i = randomNumbers.length, j = 0; i > 0; i--, j++){
                if(playerNumbers[i -1] != randomNumbers[j]) {
                    isCorrect = false;
                    break;
                }
            }
        }

        if(isCorrect){
            round++;
            clearDisplayTextview();
            clearButtonColor();
            generateRandomNumbers();
        } else {
            Snackbar.make(view, "Inorrect! Please rearrange!",
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    //This function clears all textview by changing their value to "?"
    public void clearDisplayTextview() {
        for(TextView tv : textViews){
            tv.setText("?");
        }
    }

    //This function clears all selected button color (Reset to default)
    public void clearButtonColor() {
        for(Button button: buttons){
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.main));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary));
        }
    }

    //This function will generate current order (ASC/DSC) randomly
    public void generateRandomOrder() {
        int a = (int)(Math.random()*10);
        if(a < 5) {
            isAsc = true;
            order.setText("Order: Ascending");
        } else {
            isAsc = false;
            order.setText("Order: Descending");
        }
    }
}