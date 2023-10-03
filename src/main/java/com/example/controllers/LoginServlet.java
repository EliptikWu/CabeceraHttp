package com.example.controllers;

import com.example.domain.mapping.dto.TeacherDto;
import com.example.services.TeacherService;
import com.example.services.impl.TeacherServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

/**Private Access**/
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            Cookie usernameCookie = new Cookie("username", username);
            resp.addCookie(usernameCookie);
            Connection conn = (Connection) req.getAttribute("conn");
            TeacherService service = new TeacherServiceImpl(conn);
            List<TeacherDto> teacherDtoList = service.list();
            getServletContext().setAttribute("teacherDtoList", teacherDtoList);
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Correct login</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Login correcto!</h1>");
                out.println(" <h3>Hi "+ username + " you have logged in successfully!</h3>");
                out.println(" </body>");
                out.println("</html>");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, not authorized " +
                    "to enter this page!");
        }if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            resp.sendRedirect(req.getContextPath() + "/login.html");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, not authorized " +
                    "to enter this page!");
        }
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String languagePreference = "English";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("language")) {
                    languagePreference = cookie.getValue();
                    break;
                }
            }
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println(" <head>");
            out.println(" <meta charset=\"UTF-8\">");
            out.println(" <title>Welcome</title>");
            out.println(" </head>");
            out.println(" <body>");
            out.println(" <h1>Welcome/h1>");
            out.println(" <h3>Preferred language: " + languagePreference + "</h3>");
            out.println(" </body>");
            out.println("</html>");
        }
    }
}
