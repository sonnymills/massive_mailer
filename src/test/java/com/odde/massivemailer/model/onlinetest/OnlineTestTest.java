package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class OnlineTestTest {

    @Test
    public void shouldNotGetANewOnlineTestWithNQuestionIdsIfEnoughQuestionsInDatabase() {
        OnlineTest newOnlineTest = new OnlineTest(5);
        assertEquals(0, newOnlineTest.getNumberOfQuestions());
    }

    @Test
    public void shouldGetNextQuestionWhenHaveMoreQuestionsAvailable() {
        mockQuestion(5);
        OnlineTest newOnlineTest = new OnlineTest(5);
        assertEquals(5, newOnlineTest.getNumberOfQuestions());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotGetNextQuestionWhenNoMoreQuestionsLeft() {
        OnlineTest newOnlineTest = new OnlineTest(5);
        newOnlineTest.getNextQuestion();
    }

    @Test
    public void shouldNotRepeatQuestions() {
        mockQuestion(6);
        OnlineTest newOnlineTest = new OnlineTest(5);
        Set<Question> questions = new HashSet<>();
        while(newOnlineTest.hasNextQuestion()) {
            questions.add(newOnlineTest.getCurrentQuestion());
            newOnlineTest.moveToNextQuestion();
        }
        assertEquals(5, questions.size());
    }

    @Test
    public void showMultiCategoryMessage(){
        mockQuestion(2, "1");
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(2);
        assertEquals("", onlineTest.getCategoryMessage());

    }

    @Test
    public void showWrongSingleCategoryMessage(){
        mockQuestion(1, "2");
        mockQuestion(1, "2");
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(0);
        assertEquals("Techをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void showWrongMultiCategoryMessage(){
        mockQuestion(1, "2");
        mockQuestion(1, "1");
        OnlineTest onlineTest = new OnlineTest(2);

        onlineTest.setCorrectAnswerCount(0);
        assertEquals("TechとScrumをもっと勉強して", onlineTest.getCategoryMessage());

    }

    @Test
    public void showChangeAdvice() {
        mockQuestion(5, "1");
        OnlineTest onlineTest = new OnlineTest(5);

        onlineTest.setCorrectAnswerCount(4);
        assertEquals("", onlineTest.getCategoryMessage());

        onlineTest.setCorrectAnswerCount(1);
        assertEquals("Scrumをもっと勉強して", onlineTest.getCategoryMessage());

    }

    private static void mockQuestion(int numberOfQuestion, String category) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index, "category", category));
    }

    private static void mockQuestion(int numberOfQuestion) {
        IntStream.range(0, numberOfQuestion).forEach(index -> Question.createIt("description", "desc" + index, "advice", "adv" + index));
    }
}
