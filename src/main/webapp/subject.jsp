<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Map"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.domain.mapping.dto.SubjectDto" %>
<%@ page import="com.example.domain.mapping.dto.TeacherDto" %>
<%
    List<String> errores = (List<String>)request.getAttribute("errores");
%>
<%
    List<TeacherDto> teachers = (List<TeacherDto>) request.getServletContext().getAttribute("teacherDtoList");
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
<h3><%= "Formulario Materia" %>
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

<form action="subject-form" method="post">
    <div class="row mb-3">
        <label for="name" class="col-form-label col-sm-2">Name</label>
        <div class="col-sm-4"><input type="text" name="name" id="name" class="form-control" value="${param.name}"></div>
        <%
            if(errorsmap != null && errorsmap.containsKey("name")){
                out.println("<div class='row mb-3 alert alert-danger col-sm-4' " +
                        "style='color: red;'>"+ errorsmap.get("name") + "</div>");
            }
        %>
    </div>
    <div class="row mb-3">
        <%
            if(teachers != null && !teachers.isEmpty()){}
        %>
        <label for="teacher" class="col-form-label col-sm-2">Teacher</label>
        <div class="col-sm-4"><select name="teachers" id="teacher" class="form-control">
            <option value="">-- seleccionar --</option>
            <% for(TeacherDto teacher: teachers){%>
            <option><%=teacher.name()%></><option>
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
        <label for="habilitar" class="col-form-label
col-sm-2">Habilitar</label>
        <div class="form-check">
            <input type="checkbox" name="habilitar" id="habilitar" checked
                   class="form-check-input">
        </div>
    </div>
    <div class="row mb-3">
        <div>
            <input type="submit" value="Enviar" class="btn btn-primary">
        </div>
    </div>
</form>
<br/>
<a href="subject-form">Vamos a SubjectController</a>
</body>
</html>