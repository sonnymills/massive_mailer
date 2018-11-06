package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.Question;
import com.odde.massivemailer.model.onlinetest.Quiz;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/onlinetest/question")
public class QuestionController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/onlinetest/question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        int correctlyAnsweredCount = (int) session.getAttribute("correctlyAnsweredCount");
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        String answeredOptionId = req.getParameter("optionId");

        Question currentQuestion = quiz.getCurrentQuestion();
        boolean correctAnswer = currentQuestion.verifyAnswer(answeredOptionId);

        if(correctAnswer){
            correctlyAnsweredCount++;
            session.setAttribute("correctlyAnsweredCount", correctlyAnsweredCount);
            resp.sendRedirect(getRedirectPageName(quiz.hasNextQuestion()));
            return;
        }

        req.setAttribute("selectedOption",answeredOptionId);
        RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/advice.jsp");
        dispatch.forward(req, resp);
    }

    private String getRedirectPageName(boolean moreQuestionsExist) {
        String redirectPageName = "/onlinetest/end_of_test.jsp";
        if (moreQuestionsExist) {
            redirectPageName = "/onlinetest/question.jsp";
        }
        return redirectPageName;
    }
}