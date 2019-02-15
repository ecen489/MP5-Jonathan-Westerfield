package com.machineproblem5part2.jonathanwesterfield.machineproblem5part2;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class QuizQuestion implements Serializable
{
    private String topic;
    private String question;
    private String correctAnswer;
    private ArrayList<String> answerChoices;
    private boolean hasBeenUsed;

    public QuizQuestion(ArrayList<String> questionInfo)
    {
        this.topic = questionInfo.get(0);
        this.question = questionInfo.get(1);

        this.answerChoices = new ArrayList<String>();
        this.answerChoices.add(questionInfo.get(2));
        this.answerChoices.add(questionInfo.get(3));
        this.answerChoices.add(questionInfo.get(4));
        this.answerChoices.add(questionInfo.get(5));

        this.correctAnswer = questionInfo.get(6);
        this.hasBeenUsed = false;
    }

    public String getTopic()
    {
        return this.topic;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public String getCorrectAnswer()
    {
        return this.correctAnswer;
    }

    public ArrayList<String> getAnswerChoices()
    {
        return this.answerChoices;
    }

    public boolean hasBeenUsed()
    {
        return this.hasBeenUsed;
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        str.append("\nTopic: " + this.topic + "\n");
        str.append("Question: " + this.question + "\n");

        str.append("Answer Choices: ");
        for (String choice : this.answerChoices)
        {
            str.append(choice + " \\ ");
        }

        str.append("\nAnswer: " + this.correctAnswer + "\n");

        return str.toString();
    }

    public void setHasBeenUsed()
    {
        this.hasBeenUsed = true;
    }

    public void reset()
    {
        this.hasBeenUsed = false;
    }
}