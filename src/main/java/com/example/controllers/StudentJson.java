package com.example.controllers;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.model.Student;
import com.example.reposistories.impl.StudentRepositoryLogicImpl;
import com.example.services.StudentService;
import com.example.services.impl.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
/**Public Access**/
@WebServlet({"/student.json"})
public class StudentJson extends HttpServlet {


    public StudentRepositoryLogicImpl student;
    public StudentService service;


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        ServletInputStream jsonStream = req.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        StudentDto student = mapper.readValue(jsonStream, StudentDto.class);
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println(" <head>");
            out.println(" <meta charset=\"UTF-8\">");
            out.println(" <title>Detalle de producto desde json</title>");
            out.println(" </head>");
            out.println(" <body>");
            out.println(" <h1>Detalle de producto desde json!</h1>");
            out.println("<ul>");
            out.println("<li>Id: " + student.idStu() + "</li>");
            out.println("<li>Nombre: " + student.name() + "</li>");
            out.println("<li>Semestre: " + student.semester() + "</li>");
            out.println("</ul>");
            out.println(" </body>");
            out.println("</html>");




        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        List<StudentDto> students = service.list();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(students);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}

