package com.machineproblem5part2.jonathanwesterfield.machineproblem5part2;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.view.View.*;
import android.content.Context.*;
import java.util.*;
import android.util.Log;
import java.io.*;
import android.app.*;

import org.w3c.dom.Text;

public class QuestionActivity extends AppCompatActivity
{

    TextView questionView;
    TextView choiceAView;
    TextView choiceBView;
    TextView choiceCView;
    TextView choiceDView;
    EditText answerBox;

    ArrayList<String> answerChoices;
    String correctAnswer;

    Button submitBtn;
    QuizQuestion questionObj;
    int userAnswer;
    int currScore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent fromMain = getIntent();
        this.questionObj = (QuizQuestion)fromMain.getSerializableExtra("question_object");
        this.currScore = fromMain.getIntExtra("score", 0);

        this.answerChoices = new ArrayList<String>();
        pullAnswerChoices();

        this.correctAnswer = this.questionObj.getCorrectAnswer();

        initializeInterfaces();
        populateViews();

    }

    public void initializeInterfaces()
    {
        this.questionView = (TextView) findViewById(R.id.questionView);
        this.choiceAView = (TextView) findViewById(R.id.choiceAView);
        this.choiceBView = (TextView) findViewById(R.id.choiceBView);
        this.choiceCView = (TextView) findViewById(R.id.choiceCView);
        this.choiceDView = (TextView) findViewById(R.id.choiceDView);
        this.answerBox = (EditText) findViewById(R.id.answerBox);
        this.submitBtn = (Button) findViewById(R.id.submitBtn);
    }

    public void pullAnswerChoices()
    {
        for(String choice : this.questionObj.getAnswerChoices())
            this.answerChoices.add(choice);
    }

    public void populateViews()
    {
        this.questionView.setText(questionObj.getQuestion());
        this.choiceAView.setText("A. " + this.answerChoices.get(0));
        this.choiceBView.setText("B. " + this.answerChoices.get(1));
        this.choiceCView.setText("C. " + this.answerChoices.get(2));
        this.choiceDView.setText("D. " + this.answerChoices.get(3));
    }

    public void checkAnswer(View view)
    {
        String answer = this.answerBox.getText().toString();

        // Sanitize the inputs
        answer = answer.trim();
        answer = answer.replaceAll("[-+.^:,\n\t\r]","");
        System.out.println("User Answer: " + answer);

        translateAnswer(view, answer);

        if(this.userAnswer == -1)
            return;

        if (this.answerChoices.get(this.userAnswer).equalsIgnoreCase(this.correctAnswer))
        {
            this.currScore++;
            showQuestionResultAlert(view, true);
        }
        else
            showQuestionResultAlert(view, false);

        System.out.println("Updated Score: " + this.currScore);

    }

    // Translate A, B, C, & D to 0,1,2,3 (indexs of the answer arraylist)
    public void translateAnswer(View view, String answer)
    {
        // If user doesn't enter A, B, C or D, send them a message for not being able
        // to follow directions
        if(!answer.equalsIgnoreCase("A") && !answer.equalsIgnoreCase("B")
                && !answer.equalsIgnoreCase("C") && !answer.equalsIgnoreCase("D"))
        {
            showBadInputAlert(view);
            this.userAnswer = -1;
            return;
        }

        if(answer.equalsIgnoreCase("A"))
            this.userAnswer = 0;
        else if(answer.equalsIgnoreCase("B"))
            this.userAnswer = 1;
        else if(answer.equalsIgnoreCase("C"))
            this.userAnswer = 2;
        else if(answer.equalsIgnoreCase("D"))
            this.userAnswer = 3;
    }

    public void showQuestionResultAlert(View view, boolean correct)
    {
        this.answerBox.getText().clear();

        String title;
        String message;

        if(correct)
        {
            title = "CORRECT!!!";
            message = "You got the correct answer! You've gained a point!";
        }
        else
        {
            title = "WRONG!!!";
            message = "Do generally better. You gain zero points.";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).
                setMessage(message)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        returnToPrevActivity();
                    }
                });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void returnToPrevActivity()
    {
        Intent mainIntent = new Intent();
        mainIntent.putExtra("updated_score", this.currScore);
        setResult(RESULT_OK, mainIntent);
        finish();
    }

    public void showBadInputAlert(View view)
    {
        this.answerBox.getText().clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stop It. Get Some Help.").
                setMessage("Enter the letters A, B, C or D as your answer choices!")
                .setNeutralButton("OK", null);;

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
