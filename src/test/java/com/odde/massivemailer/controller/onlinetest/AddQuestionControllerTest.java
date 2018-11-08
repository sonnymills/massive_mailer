package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.controller.onlinetest.AddQuestionController;
import com.odde.massivemailer.model.onlinetest.AnswerOption;
import com.odde.massivemailer.model.onlinetest.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(TestWithDB.class)
public class AddQuestionControllerTest {
    private AddQuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new AddQuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void doPostAddQuestion() throws Exception {
        request.setParameter("description", "aaaaaaaaaaaaaaaa");
        request.setParameter("option1", "option1");
        request.setParameter("option2", "option2");
        request.setParameter("option3", "option3");
        request.setParameter("option4", "option4");
        request.setParameter("option5", "option5");
        request.setParameter("option6", "option6");

        controller.doPost(request, response);

        String description = request.getParameter("description");
        assertEquals("/onlinetest/question_list.jsp", response.getRedirectedUrl());

        Question question = Question.findFirst("");
        assertEquals(description, question.getDescription());

        for (int i = 0; i < 6; i++) {
            String option = request.getParameter("option" + (i + 1));
            boolean hasOption = question.getOptions().stream().anyMatch(opt -> opt.getDescription().equals(option));
            assertTrue(hasOption);
        }
    }

}
