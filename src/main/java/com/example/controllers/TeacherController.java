package com.example.controllers;

import com.example.domain.mapping.dto.TeacherDto;
import com.example.domain.model.Teacher;
import com.example.reposistories.impl.TeacherRepositoryLogicImpl;
import com.example.services.TeacherService;
import com.example.services.impl.TeacherServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**Public Access**/
@WebServlet(name = "teacherController", value = "/teacher-form")
public class TeacherController extends HttpServlet {

    private TeacherRepositoryLogicImpl teacherRepository;
    private TeacherService service;

    public TeacherController() {
        teacherRepository = new TeacherRepositoryLogicImpl();
        service = new TeacherServiceImpl((Connection) teacherRepository);
    }

    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Teachers</h1>");
        out.println(service.list());
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        TeacherDto teacher = new TeacherDto(4L, name,email);
        service.update(teacher);
        System.out.println(service.list());

        try (PrintWriter out = resp.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <title>Resultado form</title>");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <h1>Resultado form!</h1>");
            out.println("        <ul>");
            out.println("            <li>Name: " + name + "</li>");
            out.println("            <li>email: " + email + "</li>");
            out.println("        </ul>");
            out.println("    </body>");
            out.println("</html>");
        }
    }
    public void destroy() {
    }
}