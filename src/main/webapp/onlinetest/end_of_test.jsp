<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:with_side_menu title="End Of Test">
    <jsp:body>

        <div class="container-fluid">
            <h1 id="title">End Of Test</h1>
            <p id="correct-count">${correctlyAnsweredCount}</p>
            <p id="total-count">${onlineTest.getNumberOfQuestions()}</p>
            <p id="correct-percentage">${onlineTest.getCorrectPercentage(correctlyAnsweredCount)}</p>%
            <p id="message">基本を学びなおしましょう</p>
            <p>
                Thank you for your hard work.
                It will be about two hours soon. Why do not you take a break?:)
            </p>
        </div>

    </jsp:body>
</t:with_side_menu>
