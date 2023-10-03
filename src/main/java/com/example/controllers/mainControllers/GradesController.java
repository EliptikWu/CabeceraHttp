package com.example.controllers.mainControllers;

import com.example.domain.mapping.dto.GradesDto;
import com.example.domain.mapping.mappers.GradesMapper;
import com.example.domain.mapping.mappers.StudentMapper;
import com.example.domain.mapping.mappers.SubjectMapper;
import com.example.domain.model.Grades;
import com.example.reposistories.impl.GradesRepositoryLogicImpl;
import com.example.services.GradesService;
import com.example.services.impl.GradesServiceImpl;
import com.example.services.impl.StudentServiceImpl;
import com.example.services.impl.SubjectServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Private Access**/
@WebServlet(name = "GradesController", value = "/grades-form")
public class GradesController extends HttpServlet {

    private GradesRepositoryLogicImpl gradesRepository;
    private GradesService service;

    private String message;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        Connection conn = (Connection) request.getAttribute("conn");
        gradesRepository = new GradesRepositoryLogicImpl(conn);
        service = new GradesServiceImpl(conn);

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Grades</h1>");
        out.println(service.list());
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Connection conn = (Connection) req.getAttribute("conn");
        StudentServiceImpl studentService = new StudentServiceImpl(conn);
        SubjectServiceImpl subjectService = new SubjectServiceImpl(conn);
        service = new GradesServiceImpl(conn);
        Long studentid = Long.valueOf(req.getParameter("student"));
        Long subject = Long.valueOf(req.getParameter("subject"));
        Double grade = Double.parseDouble(req.getParameter("grade"));
        Grades grades = Grades.builder()
                .student(StudentMapper.mapFrom(studentService.byId(studentid)))
                .subject(SubjectMapper.mapFrom(subjectService.byId(subject)))
                .grade(grade).build();
        GradesDto gradesDto = GradesMapper.mapFrom(grades);
        service.update(gradesDto);
        System.out.println(service.list());
        //List<String> errores = getErrors( studentid, subject, grade);
        //Map<String, String> errorsmap = getErrors2(studentid, subject,grade);
        //if (errorsmap.isEmpty()) {
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
            out.println("            <li>Name: " + studentid + "</li>");
            out.println("            <li>email: " + subject + "</li>");
            out.println("            <li>email: " + grade + "</li>");
            out.println("        </ul>");
            out.println("    </body>");
            out.println("</html>");
        }
    }/**
     else {
            req.setAttribute("errors", errores);
            req.setAttribute("errorsmap", errorsmap);

            getServletContext().getRequestDispatcher("/grades.jsp").forward(req, resp);
        }
    private Map<String,String> getErrors2(Long studentid, Long subject, Double grade) {
        Map<String,String> errors = new HashMap<>();
        if(studentid==null ||studentid.isBlank()){
            errors.put("studentid","El studentid es requerido");
        }
        if(subject==null ||subject.isBlank()){
            errors.put("subject","El subject es requerido");
        }
        if(grade==null ||grade.isBlank()){
            errors.put("grade","El grade es requerido");
        }
        return errors;
    }
    private List<String> getErrors(Long studentid, Long email, Double semester)
    {
        List<Long> errors = new ArrayList<>();
        if(studentid==null ||studentid.isBlank()){
            errors.add(Long.valueOf("El studentid es requerido"));
        }
        if(email==null ||email.isBlank()){
            errors.add("El email es requerido");
        }
        if(semester==null ||semester.isBlank()){
            errors.add("El semester es requerido");
        }
        return errors;
    }**/
}

