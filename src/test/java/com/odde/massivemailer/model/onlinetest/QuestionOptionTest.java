package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.base.ValidationException;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class QuestionOptionTest {
    private final ObjectId questionId1 = new ObjectId();
    private final ObjectId questionId2 = new ObjectId();

    @Test
    public void shouldBeAbleToPersist() {
        QuestionOption questionOption = QuestionOption.create("desc", false);
        questionOption.addToQuestion(questionId1);

        Optional<QuestionOption> actual = QuestionOption.getById(questionOption.getStringId());

        assertTrue(actual.isPresent());
        assertThat(actual.get(), is(equalTo(questionOption)));
    }

    @Test
    public void shouldSaveAndReadIsCorrectProperly() {
        QuestionOption wrongOption = QuestionOption.create("desc1", false);
        QuestionOption rightOption = QuestionOption.create("desc2", true);
        wrongOption.addToQuestion(questionId1);
        rightOption.addToQuestion(questionId1);

        Optional<QuestionOption> wrongFromDb = QuestionOption.getById(wrongOption.getStringId());
        assertTrue(wrongFromDb.isPresent());
        QuestionOption wrong = wrongFromDb.get();
        assertThat(wrong.isCorrect(), is(false));

        Optional<QuestionOption> rightFromDb = QuestionOption.getById(rightOption.getStringId());
        assertTrue(rightFromDb.isPresent());
        QuestionOption right = rightFromDb.get();
        assertThat(right.isCorrect(), is(true));
    }

    @Test
    public void shouldReturnOptionsForQuestion() {
        IntStream.range(0, 3).forEach(i -> {
            QuestionOption questionOption = QuestionOption.create("desc", false);
            questionOption.addToQuestion(i%2 ==0 ? questionId1 : questionId2);
        });

        Collection<QuestionOption> optionsForQuestion = QuestionOption.getForQuestion(questionId1);
        assertThat(optionsForQuestion.size(), is(2));
        optionsForQuestion.forEach(option -> assertEquals(option.getQuestionId(), questionId1));
    }

    @Test(expected = ValidationException.class)
    public void shouldFailWhenDescriptionIsEmpty() {
        QuestionOption.createIt(questionId1, "", false);
    }

    @Test(expected = ValidationException.class)
    public void shouldFailWhenQuestionIdIsEmpty() {
        QuestionOption.createIt(null, "desc", false);
    }
}
