package com.odde.massivemailer.model.onlinetest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class OnlineTest {

    private final List<Long> questionIds;
    private int numberOfAnsweredQuestions;
    private int correctAnswerCount;

    public OnlineTest(int questionCount) {
        questionIds = Question.getNRandomIds(questionCount).collect(Collectors.toList());
        numberOfAnsweredQuestions = 0;
    }

    public Question getPreviousQuestion() {
        return Question.getById(questionIds.get(numberOfAnsweredQuestions-1));
    }

    public Question getCurrentQuestion() {
        return Question.getById(questionIds.get(numberOfAnsweredQuestions));
    }

    public Question getNextQuestion() {
        if (!hasNextQuestion()) {
            throw new NoSuchElementException("OnlineTest not started");
        }
        Question question = Question.getById(questionIds.get(numberOfAnsweredQuestions + 1));
        return question;
    }

    public boolean hasNextQuestion() {
        return questionIds.size() > this.getNumberOfAnsweredQuestions();
    }

    public int getNumberOfAnsweredQuestions() {
        return this.numberOfAnsweredQuestions;
    }

    public int getCurrentQuestionIndex() {
        return this.numberOfAnsweredQuestions + 1;
    }

    public int getNumberOfQuestions() {
        return (questionIds!=null) ? questionIds.size() : 0;
    }

    public void moveToNextQuestion() {
        numberOfAnsweredQuestions++;
    }

    public int getCorrectPercentage(double correctNumber) {
        return (int) (correctNumber/getNumberOfQuestions() * 100);
    }

    public String showFinalMessage() {
        if(getCorrectPercentage(this.correctAnswerCount) < 85) {
            return "基本を学びなおしましょう";
        } else if (getCorrectPercentage(this.correctAnswerCount) == 100) {
            return "あなたはスクラムマスター！";
        }
        return "あともう少し";
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public void setCorrectAnswerCount(int correctlyAnsweredCount) {
        this.correctAnswerCount = correctlyAnsweredCount;
    }
}