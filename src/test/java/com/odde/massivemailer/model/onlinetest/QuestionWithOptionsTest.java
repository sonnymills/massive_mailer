package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class QuestionWithOptionsTest {

    private Question createWithOptions(List<QuestionOption> questionOptions) {
        Question question = new Question("des1", "adv1", new ObjectId(), false, false).saveIt();
        questionOptions.forEach(option -> option.addToQuestion(question.getId()));
        return question;
    }

    @Test
    public void shouldCreateQuestionHavingDescriptionAndAdviceAndAnswerOptions() {
        List<QuestionOption> expectedQuestionOptions = IntStream.range(0, 4).mapToObj(index -> QuestionOption.create("option desc"+index, index%4==0)).collect(Collectors.toList());
        Question expected  = createWithOptions(expectedQuestionOptions);

        Question actual = Question.getById(expected.getId());
        assertEquals(expected, actual);

        Collection<QuestionOption> actualQuestionOptions = actual.getOptions();

        assertEquals(expectedQuestionOptions.size(), actualQuestionOptions.size());
        assertTrue(expectedQuestionOptions.containsAll(actualQuestionOptions));
    }
}
