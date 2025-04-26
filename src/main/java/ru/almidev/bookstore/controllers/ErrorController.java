package ru.almidev.bookstore.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.almidev.bookstore.attributes.AttributeNames.*;
import static ru.almidev.bookstore.views.ViewPaths.*;

@WebServlet("/error")
public class ErrorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer code = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String message = (String) request.getAttribute("jakarta.servlet.error.message");

        if (code == null) {
            code = 500;
            message = "Неизвестная ошибка сервера.";
        }

        request.setAttribute(ERROR_CODE, code);
        request.setAttribute(ERROR_MESSAGE, message);
        request.getRequestDispatcher(ERROR_JSP).forward(request, response);
    }
}
