<%--
  Created by IntelliJ IDEA.
  User: likun
  Date: 3/10/2023
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.Map"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.domain.mapping.dto.StudentDto" %>
<%@ page import="com.example.domain.mapping.dto.SubjectDto" %>
<%
  List<String> errores = (List<String>)request.getAttribute("errores");
%>
<%
  List<StudentDto> students = (List<StudentDto>)getServletContext().getAttribute("studentDtoList");
%>
<%
  List<SubjectDto> subjects = (List<SubjectDto>)getServletContext().getAttribute("subjectDtoList");
%>
<%
  Map<String,String> errorsmap =
          (Map<String,String>)request.getAttribute("errorsmap");
%>
  <!DOCTYPE html>
  <html>
  <head>
    <title>JSP - Hello World</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css
  " rel="stylesheet"
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
  </head>
  <body>
  <h3><%= "Formulario Notas" %>
  </h3>
  <%
    if(errorsmap != null && errorsmap.size()>0){
  %>
  <ul class="alert alert-danger mx-5">
    <% for(String error: errorsmap.values()){%>
    <li><%=error%></li>
    <%}%>
  </ul>
  <%}%>
  <form action="grades-form" method="post">
    <div class="row mb-3">
      <%
        if(students != null && !students.isEmpty()){}
      %>
      <label for="student" class="col-form-label col-sm-2">Student</label>
      <div class="col-sm-4"><select name="students" id="student" class="form-control">
        <option value="">-- seleccionar --</option>
        <% for(StudentDto student: students){%>
        <option><%=student.name()%></><option>
          <%}%>
      </select></div>
      <%
        if(errorsmap != null && errorsmap.containsKey("student")){
          out.println("<div class='row mb-3 alert alert-danger col-sm-4' " +
                  "style='color: red;'>"+ errorsmap.get("student") + "</div>");
        }
      %>
    </div>
    <div class="row mb-3">
      <label for="subject" class="col-form-label col-sm-2">Subject</label>
      <div class="col-sm-4"><select name="subjects" id="subject" class="form-control">
        <option value="">-- seleccionar --</option>
        <% for(SubjectDto subject: subjects){%>
        <option><%=subject.name()%></><option>
            <%}%>
      </select></div>
      <%
        if(errorsmap != null && errorsmap.containsKey("subject")){
          out.println("<div class='row mb-3 alert alert-danger col-sm-4' " +
                  "style='color: red;'>"+ errorsmap.get("subject") + "</div>");
        }
      %>
    </div>
    <div class="row mb-3">
      <label for="grade" class="col-form-label col-sm-2">Grade</label>
      <div class="col-sm-4"><input type="text" name="grade" id="grade" class="form-control" value="${param.grade}"></div>
      <%
        if(errorsmap != null && errorsmap.containsKey("grade")){
          out.println("<div class='row mb-3 alert alert-danger col-sm-4' " +
                  "style='color: red;'>"+ errorsmap.get("grade") + "</div>");
        }
      %>
    </div>
    <div class="row mb-3">
      <label for="habilitar" class="col-form-label
  col-sm-2">Habilitar</label>
      <div class="form-check">
        <input type="checkbox" name="habilitar" id="habilitar" checked
               class="form-check-input">
      </div>
    </div>
    </div>
    <div class="row mb-3">
      <div>
        <input type="submit" value="Enviar" class="btn btn-primary">
      </div>
    </div>
  </form>
  <br/>
  <a href="grades-form">Vamos a GradesController</a>
  </body>
  </html>
