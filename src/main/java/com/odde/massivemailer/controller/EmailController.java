package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ContactService contactService;

    public EmailController() {
        contactService = new SqliteContact();
    }

    public EmailController(ContactService contactService) {
        this.contactService = contactService;

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultMsg = "";

        ContactPerson contact = new ContactPerson("todo name", req.getParameter("email"), "todo last name", "todo company");
        if (contactService.addContact(contact))
            resultMsg = "status=success&msg=Add contact successfully";
        else
            resultMsg = "status=failed&msg=Email " + req.getParameter("email")
                    + " is already exist";

        resp.sendRedirect("contactlist.jsp?" + resultMsg);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String convertedContactToJSON = new Gson().toJson(contactService.getContactList());
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(convertedContactToJSON);
    }
}
