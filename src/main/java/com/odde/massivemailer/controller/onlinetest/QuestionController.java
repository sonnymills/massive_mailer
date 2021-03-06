package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.OnlineTest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/onlinetest/question")
public class QuestionController extends AppController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        String[] answeredOptionIds = req.getParameterValues("optionId");

        if (req.getParameterValues("optionId") == null) {
            resp.sendRedirect(getRedirectPageName(true));
            return;
        }

        String lastDoneQuestionId = req.getParameter("lastDoneQuestionId");
        String alertMsg = onlineTest.getAlertMsg(lastDoneQuestionId);
        session.setAttribute("alertMsg", alertMsg);

        if (!lastDoneQuestionId.equals(String.valueOf(onlineTest.getNumberOfAnsweredQuestions()))) {
            resp.sendRedirect(getRedirectPageName(onlineTest.hasNextQuestion()));
            return;
        }


        if (onlineTest.answer(answeredOptionIds)) {
            resp.sendRedirect(getRedirectPageName(onlineTest.hasNextQuestion()));
            return;
        }

        req.setAttribute("selectedOption", new ArrayList(Arrays.asList(answeredOptionIds)));
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
