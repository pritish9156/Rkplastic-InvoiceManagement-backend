package controller;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/api/health")
public class HealthServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        resp.setContentType(
                "application/json");

        resp.getWriter().write(
                "{"+"'status':'UP'"+"}");
    }
}