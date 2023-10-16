package com.example.controllers.mainControllers;

import com.example.domain.mapping.dto.TeacherDto;
import com.example.domain.mapping.mappers.TeacherMapper;
import com.example.domain.model.Teacher;
import com.example.reposistories.Repository;
import com.example.services.TeacherService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Private Access**/
@WebServlet(name = "teacherController", value = "/teacher-form")
public class TeacherController extends HttpServlet {
    @Inject
    @Named("Teacher")
    Repository<TeacherDto> TeacherRepository;
    @Inject
    TeacherService service;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Teachers</h1>");
        out.println(service.list());
        out.println("</body></html>");
    }

    private TeacherDto getTeacherByName(String teacher) {
        List<TeacherDto> teachers = (List<TeacherDto>)getServletContext().getAttribute("teachers");
        return teachers.stream()
                .filter(e->e.name().equalsIgnoreCase(teacher))
                .findFirst()
                .orElseGet(null);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        Teacher teacher = Teacher.builder()
                .name(name)
                .email(email).build();
        TeacherDto teacherDto = TeacherMapper.mapFrom(teacher);
        service.update(teacherDto);
        System.out.println(service.list());
        List<String> errores = getErrors(name, email);
        Map<String, String> errorsmap = getErrors2(name, email);
        if (errorsmap.isEmpty()) {
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

    } else {
            req.setAttribute("errors", errores);
            req.setAttribute("errorsmap", errorsmap);

            getServletContext().getRequestDispatcher("/student.jsp").forward(req, resp);
        }
    }
    private Map<String,String> getErrors2(String name,String email) {
        Map<String,String> errors = new HashMap<>();
        if(name==null ||name.isBlank()){
            errors.put("name","El nombre es requerido");
        }
        if(email==null ||email.isBlank()){
            errors.put("email","El email es requerido");
        }
        return errors;
    }
    private List<String> getErrors(String name,String email)
    {
        List<String> errors = new ArrayList<String>();
        if(name==null ||name.isBlank()){
            errors.add("El nombre es requerido");
        }
        if(email==null ||email.isBlank()){
            errors.add("El email es requerido");
        }
        return errors;
    }
}