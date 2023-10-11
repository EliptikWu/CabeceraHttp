package com.example.controllers;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.mapping.dto.TeacherDto;
import com.example.services.LoginService;
import com.example.services.StudentService;
import com.example.services.SubjectService;
import com.example.services.TeacherService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

/**Private Access**/
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";
    @Inject
    @Named("login")
    LoginService auth;
    @Inject
    TeacherService tService;
    @Inject
    SubjectService subService;
    @Inject
    StudentService stuService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            List<TeacherDto> teacherDtoList = tService.list();
            getServletContext().setAttribute("teacherDtoList", teacherDtoList);
            List<SubjectDto> subjectDtoList = subService.list();
            getServletContext().setAttribute("subjectDtoList", subjectDtoList);
            List<StudentDto> studentDtoList = stuService.list();
            getServletContext().setAttribute("studentDtoList", studentDtoList);
            Cookie usernameCookie = new Cookie("username", username);
            resp.addCookie(usernameCookie);
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
                out.println(" <h3>Hi " + username + " you have logged in successfully!</h3>");
                out.println(" </body>");
                out.println("</html>");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, not authorized " +
                    "to enter this page!");
        }
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            resp.sendRedirect(req.getContextPath() + "/login.html");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry, not authorized " +
                    "to enter this page!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {

        Optional<String> usernameOptional = auth.getUsername(req);
        if (usernameOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Hola " + usernameOptional.get() +
                        "</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Hola " + usernameOptional.get() + " has " +
                        "iniciado sesión con éxito!</h1>");
                out.println("<p><a href='" + req.getContextPath() +
                        "/index.html'>volver</a></p>");
                out.println("<p><a href='" + req.getContextPath() +
                        "/logout'>cerrar sesión</a></p>");
                out.println(" </body>");
                out.println("</html>");
            }
        } else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req,
                    resp);
        }
    }
}