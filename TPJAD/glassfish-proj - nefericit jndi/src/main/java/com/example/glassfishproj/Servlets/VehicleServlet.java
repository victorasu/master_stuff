package com.example.glassfishproj.Servlets;

import com.example.glassfishproj.Interfaces.VehicleService;
import com.example.glassfishproj.Models.Customer;
import com.example.glassfishproj.Models.Vehicle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(name="vehicles", value = "/vehicles")
public class VehicleServlet extends HttpServlet {

    private Properties JNDIProps;
    private Context context;
    private VehicleService vehicleService;
    
    public VehicleServlet() throws NamingException {
        JNDIProps = new Properties();
        JNDIProps.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.impl.SerialInitContextFactory");
        JNDIProps.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        JNDIProps.setProperty("org.omg.CORBA.ORBInitialPort", "8080");
        context = new InitialContext(JNDIProps);
        Object object = context.lookup("java:global/glassfish-proj-1.0-SNAPSHOT/VehicleBean!com.example.glassfishproj.Interfaces.VehicleService");
        vehicleService = (VehicleService) object;
    }

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
            out.println("<br>This vehicle is available.");
        else
            out.println("<br>Customer: " + customer.getFirst_name() + ' ' + customer.getLast_name());

        out.println("<form action=\"main\" method=\"get\"><input type=\"submit\" value=\"Back\"></form>");
        out.println("</body>\n" +
                "</html>\n");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("vehicle_id") != null) {
            long id = Long.parseLong(req.getParameter("vehicle_id"));
            vehicleService.deleteVehicle(id);
            resp.sendRedirect(req.getContextPath() + "/main");
            return;
        }

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
