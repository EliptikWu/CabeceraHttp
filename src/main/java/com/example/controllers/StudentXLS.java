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
@WebServlet({"/students.xls", "/students.html", "/students"})
public class StudentXLS extends HttpServlet {
    private StudentRepositoryLogicImpl student;
    private StudentService service;
    public StudentXLS() {
        student = new StudentRepositoryLogicImpl();
        service = new StudentServiceImpl(student);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        List<Student> students = service.listar();
        resp.setContentType("text/html;charset=UTF-8");
        String servletPath = req.getServletPath();
        boolean esXls = servletPath.endsWith(".xls");
        if (esXls) {
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment;filename=students.xls");
        }
        try (PrintWriter out = resp.getWriter()) {
            if(!esXls) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Listado de Estudiantes</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Listado de Estudiantes!</h1>");
                out.println("<p><a href=\"" + req.getContextPath() + "/students.xls" + "\">exportar a xls</a></p>");
            }
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>id</th>");
            out.println("<th>nombre</th>");
            out.println("<th>tipo</th>");
            out.println("<th>precio</th>");
            out.println("</tr>");
            students.forEach(p ->{
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td>" + p.getName() + "</td>");
                out.println("<td>" + p.getEmail() + "</td>");
                out.println("<td>" + p.getSemester() + "</td>");
                out.println("</tr>");
            });
            out.println("</table>");
            if(!esXls) {
                out.println(" </body>");
                out.println("</html>");
            }
        }
    }
}