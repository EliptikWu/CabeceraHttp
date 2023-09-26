package com.example.controllers;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.model.Student;
import com.example.reposistories.impl.StudentRepositoryLogicImpl;
import com.example.services.StudentService;
import com.example.services.impl.StudentServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**Private Access**/
@WebServlet("/loginId")
@WebFilter({"/private/login"})
public class LoginId extends HttpServlet implements Filter {

    private StudentRepositoryLogicImpl studentRepository;
    private StudentService service;

    public void StudentController() {
        studentRepository = new StudentRepositoryLogicImpl();
        service = new StudentServiceImpl(studentRepository);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Long id = null;
        String studentIdStr = request.getParameter("studentId");

        if (studentIdStr != null && !studentIdStr.isEmpty()) {
            try {
                idstudent = Long.parseLong(studentIdStr);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid student ID");
                return;
            }
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<h1>Please enter a student ID.</h1>");
            return;
        }

        Optional<Student> studentOptional = Optional.ofNullable(service.porId(id));

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            response.setContentType("text/html");
            response.getWriter().println("<p>ID: " + student.id() + "</p>");
            response.getWriter().println("<p>Nombre: " + student.name() + "</p>");
            response.getWriter().println("<p>Correo: " + student.email() + "</p>");
            response.getWriter().println("<p>Semestre: " + student.semester() + "</p>");

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Student not found");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Test");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}

