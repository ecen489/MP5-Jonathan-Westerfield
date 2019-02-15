package com.machineproblem5part2.jonathanwesterfield.machineproblem5part2;

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

public class MainActivity extends AppCompatActivity
{
    int score;

    ArrayList<QuizQuestion> questions;
    ArrayList<String> topicsList;
    TextView scoreView;
    Button resetBtn;
    ListView topicsView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.questions = new ArrayList<QuizQuestion>();
        this.score = 0;

        initializeInterfaces();
        parseQuizQuestions();
        printQuizQuestions();
        populateListView();


    }

    public void initializeInterfaces()
    {
        this.scoreView = (TextView) findViewById(R.id.scoreView);
        this.resetBtn = (Button) findViewById(R.id.resetBtn);
        this.topicsView = (ListView) findViewById(R.id.topicListView);
        setListViewClickListener();

    }

    /**
     * Goes through the file of quiz questions and puts it into a question object. Then
     * stores in the questions object arraylist
     */
    public void parseQuizQuestions()
    {
        ArrayList<String> tempStorage;

        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.quiz_questions));
        String placeHolder;

        for(int i = 0; scanner.hasNextLine(); i++)
        {
            tempStorage = new ArrayList<>();

            for(int j = 0; j < 7; j++)
            {
                // Read in all lines and get rid of weird characters
                placeHolder = scanner.nextLine();
                placeHolder = placeHolder.replaceAll("[-+.^:,\n\t\r]","");
                tempStorage.add(placeHolder);
            }

            for(int j = 0; i < tempStorage.size(); i++)
            {
                // Log.d("LISTING", "Temp Storage[" + i + "]: " + tempStorage.get(i) + "\n");
                System.out.println("Temp Storage[" + i + "]: " + tempStorage.get(i));
            }

            questions.add(new QuizQuestion(tempStorage));
        }
    }

    public void printQuizQuestions()
    {
        System.out.println("Number of Questions Created: " + this.questions.size());

        for(QuizQuestion question : this.questions)
        {
            System.out.println(question.toString());
        }
    }

    // gets all of the topics from questions and lists them only once
    // does not store duplicate topics since we don't want to display the
    // same topic multiple times on the screen
    public void populateListView()
    {
        // populate the array of topics to trim
        ArrayList<String> tempTopicsList = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++)
            tempTopicsList.add(questions.get(i).getTopic());

        this.topicsList = removeListDuplicates(tempTopicsList);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, topicsList);

        this.topicsView.setAdapter(adapter);
    }

    // Removes duplicate topics from the topics display list
    public ArrayList<String> removeListDuplicates(ArrayList<String> list)
    {
        // Create a new ArrayList
        ArrayList<String> newList = new ArrayList<String>();

        // Traverse through the first list
        for (String element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element))
            {
                newList.add(element);
            }
        }

        return newList;
    }

    public void onResetClick(View view)
    {
        this.score = 0;
        this.scoreView.setText("Score: " + this.score);

        for(QuizQuestion questionObj : this.questions)
            questionObj.reset();
    }

    public void setListViewClickListener()
    {
        this.topicsView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                String chosenTopic = topicsList.get(i);
                int questionIndex = chooseQuestion(chosenTopic);
                System.out.println("TOPIC CHOICE: " + i + " : " + chosenTopic);

                if(questionIndex == -1)
                    showNoMoreQuestionsAlert(view);
                else
                    switchToQuestionActivity(questionIndex);
            }
        });
    }

    public void switchToQuestionActivity(int index)
    {
        Intent showQuestion = new Intent(this, QuestionActivity.class);
        showQuestion.putExtra("question_object", this.questions.get(index));
        showQuestion.putExtra("score", this.score);
        startActivityForResult(showQuestion, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        int returnedScore = 0;

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                returnedScore = data.getIntExtra("updated_score", 0);
                updateScore(returnedScore);
            }
        }
    }

    public void updateScore(int updatedScore)
    {
        this.score = updatedScore;
        this.scoreView.setText("Score: " + this.score);
    }

    public void showNoMoreQuestionsAlert(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning").
                setMessage("There are no more questions for this topic! " +
                "Please choose another topic or reset the game.")
                .setNeutralButton("OK", null);;

        // Add the buttons


        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public int chooseQuestion(String chosenTopic)
    {
        for(int i = 0; i < this.questions.size(); i++)
        {
            // if the question has the same topic as the chosen topic and hasn't
            // been used yet return the index of that question object
            if(chosenTopic.equalsIgnoreCase(this.questions.get(i).getTopic())
                    && !this.questions.get(i).hasBeenUsed())
            {
                // set the question to used so it doesn't get chosen again
                // and return the index to display the question
                this.questions.get(i).setHasBeenUsed();
                return i;
            }
        }
        // return -1 if all of the questions for that topic have been used
        return -1;
    }
}
