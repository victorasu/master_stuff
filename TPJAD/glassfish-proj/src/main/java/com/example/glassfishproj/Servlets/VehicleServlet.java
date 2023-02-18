package com.example.glassfishproj.Servlets;

import com.example.glassfishproj.Interfaces.VehicleService;
import com.example.glassfishproj.Models.Customer;
import com.example.glassfishproj.Models.Vehicle;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="vehicles", value = "/vehicles")
public class VehicleServlet extends HttpServlet {

    @EJB
    private VehicleService vehicleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Vehicle vehicle = vehicleService.find(Long.parseLong(req.getParameter("id")));

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>\n" +
                "<head>\n" +
                "    <title>EJBJPA</title>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                "</head>\n" +
                "<body>");

        out.println("<br><br>Make: " + vehicle.getMake() +
                "<br><br>Model: " + vehicle.getModel() +
                "<br><br>Price: " + vehicle.getPrice() +
                "<br><br>Registration: " + vehicle.getRegistration()
        );

        Customer customer = vehicle.getCustomer();

        if (customer == null)
        {
            out.println("<br>This vehicle is available.");
            out.println("<form action=\"vehicles\" method=\"post\">");
            out.println("<button type=\"submit\" name=\"vehicle_id\" value=\"" + vehicle.getId_vehicle() + "\" >Delete</button>");
            out.println("</form>");
        }
        else
            out.println("<br>Owner: " + customer.getFirst_name() + ' ' + customer.getLast_name());

        out.println("<form action=\"main\" method=\"get\"><input type=\"submit\" value=\"Back\"></form>");
        out.println("</body>\n" +
                "</html>\n");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String make = req.getParameter("make");
        String model = req.getParameter("model");
        String price = req.getParameter("price");
        String registration = req.getParameter("registration");

        if (make != null && !make.isEmpty() &&
                model != null && !model.isEmpty() &&
                price != null && !price.isEmpty() &&
                registration != null && !registration.isEmpty()) {

            vehicleService.addVehicle(make, model, Double.parseDouble(price), registration);
            resp.sendRedirect(req.getContextPath() + "/main");
        } else {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("<html>\n" +
                    "<head>\n" +
                    "    <title>EJBJPA</title>\n" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                    "</head>\n");
            out.println("<body>Please submit fields with correct input and not empty.<br><br>");
            out.println("<form action=\"main\" method=\"get\"><input type=\"submit\" value=\"Back\"></form>");
            out.println("</body></html>");

            out.close();
        }
    }
}
