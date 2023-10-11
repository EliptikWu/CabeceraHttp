package com.example.controllers.mainControllers;

import com.example.domain.mapping.dto.GradesDto;
import com.example.domain.mapping.dto.StudentDto;
import com.example.domain.mapping.dto.SubjectDto;
import com.example.domain.mapping.dto.TeacherDto;
import com.example.domain.mapping.mappers.GradesMapper;
import com.example.domain.mapping.mappers.StudentMapper;
import com.example.domain.mapping.mappers.SubjectMapper;
import com.example.domain.model.Grades;
import com.example.reposistories.Repository;
import com.example.reposistories.impl.GradesRepositoryLogicImpl;
import com.example.services.GradesService;
import com.example.services.StudentService;
import com.example.services.SubjectService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
    @Inject
    @Named("Grade")
    Repository<GradesDto> GradeRepository;
    @Inject
    GradesService service;

    @Inject
    StudentService studentService; ;
    @Inject
    SubjectService subjectService;

    private String message;

    private SubjectDto getSubjectByName(String name) {
        List<SubjectDto> subjects = subjectService.list();
        return subjects.stream()
                .filter(e->e.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(null);
    }

    private StudentDto getStudentByName(String name) {
        List<StudentDto> students = studentService.list();
        return students.stream()
                .filter(e->e.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(null);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        Connection conn = (Connection) request.getAttribute("conn");

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
        String studentName = req.getParameter("students");
        System.out.println(studentName);
        String subjectName = req.getParameter("subjects");
        String gradeName = req.getParameter("grade");
        Map<String, String> errorsmap = getErrors2(studentName, subjectName, gradeName);
        if (errorsmap.isEmpty()) {
            StudentDto studentDto = getStudentByName(studentName);
            System.out.println(studentDto);
            SubjectDto subjectDto = getSubjectByName(subjectName);
            Double grade = Double.parseDouble(req.getParameter("grade"));
            Grades grades = Grades.builder()
                    .student(StudentMapper.mapFrom(studentDto))
                    .subject(SubjectMapper.mapFrom(subjectDto))
                    .grade(grade).build();
            GradesDto gradesDto = GradesMapper.mapFrom(grades);
            service.update(gradesDto);
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
            out.println("            <li>Name: " + studentDto + "</li>");
            out.println("            <li>email: " + subjectDto + "</li>");
            out.println("            <li>email: " + grade + "</li>");
            out.println("        </ul>");
            out.println("    </body>");
            out.println("</html>");
        }
    }
     else {
            req.setAttribute("errorsmap", errorsmap);

            getServletContext().getRequestDispatcher("/grades.jsp").forward(req, resp);
        }
    }
    private Map<String,String> getErrors2(String studentid, String subject, String grade) {
        Map<String,String> errors = new HashMap<>();
        if(studentid==null ||studentid.isBlank()){
            errors.put("student","El student es requerido");
        }
        if(subject==null ||subject.isBlank()){
            errors.put("subject","El subject es requerido");
        }
        if(grade==null ||grade.isBlank()){
            errors.put("grade","El grade es requerido");
        }
        return errors;
    }
}

