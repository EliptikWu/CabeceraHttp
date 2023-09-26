package com.example.controllers;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//works by giving the current time using the LocalTime class system and creates a DateTimeFormatter
// object to format the time in a specific format ("hh:mm:ss")
/**Public Access**/
@WebServlet({"/reloj.json"})
@WebFilter({"/public/reloj"})
public class Reloj extends HttpServlet implements Filter {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setHeader("refresh", "1");
        LocalTime hora = LocalTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm:ss");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println(" <head>");
            out.println(" <meta charset=\"UTF-8\">");
            out.println(" <title>The updated time</title>");
            out.println(" </head>");
            out.println(" <body>");
            out.println(" <h1>The updated time!</h1>");
            out.println("<h3>" + hora.format(df) + "</h3>");
            out.println(" </body>");
            out.println("</html>");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
