package com.example.controllers;

import com.example.services.LoginService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
/**Private Access**/
@WebServlet("/loginSession")
class LoginSessionServlet extends HttpServlet{

    @Inject
    @Named("loginSession")
    LoginService auth;

    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            String mensajeRequest = (String) req.getAttribute("mensaje");
            String mensajeApp = (String) getServletContext().getAttribute("mensaje");
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/loginSession.jsp");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no " +
                    "esta autorizado para ingresar a esta página!");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        Optional<String> usernameOptional = auth.getUsername(req);
        if (usernameOptional.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Hola " + usernameOptional.get() +
                        "</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Hola " + usernameOptional.get() + " has " +
                        "iniciado sesión con éxito!</h1>");
                out.println("<p><a href='" + req.getContextPath() +
                        "/index.html'>volver</a></p>");
                out.println("<p><a href='" + req.getContextPath() +
                        "/logout'>cerrar sesión</a></p>");
                out.println(" </body>");
                out.println("</html>");
            }
        } else {
            req.getServletContext().getRequestDispatcher("/login.jsp").forward(req,
                    resp);
        }
    }



}
