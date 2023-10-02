package com.example.controllers;

import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.model.Student;
import com.example.reposistories.impl.StudentRepositoryLogicImpl;
import com.example.services.StudentService;
import jakarta.servlet.*;
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


    public StudentRepositoryLogicImpl student;
    public StudentService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        List<StudentDto> student = service.list();
        resp.setContentType("text/html;charset=UTF-8");
        String servletPath = req.getServletPath();
        boolean esXls = servletPath.endsWith(".xls");
        if (esXls) {
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment;filename=students.xls");
        }
        try (PrintWriter out = resp.getWriter()) {
            if (!esXls) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Student List</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Student List!</h1>");
                out.println("<p><a href=\"" + req.getContextPath() + "/students.xls" + "\">exportar a xls < / a ></p > ");
            }
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>id</th>");
            out.println("<th>name</th>");
            out.println("<th>email</th>");
            out.println("<th>semestre</th>");
            out.println("</tr>");

            student.forEach(p ->{
                out.println("<tr>");
                out.println("<td>" + p.idStudent() + "</td>");
                out.println("<td>" + p.name() + "</td>");
                out.println("<td>" + p.email() + "</td>");
                out.println("<td>" + p.semester() + "</td>");


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