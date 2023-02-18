package com.example.glassfishproj.Servlets;

import com.example.glassfishproj.Interfaces.VehicleService;
import com.example.glassfishproj.Interfaces.CustomerService;
import com.example.glassfishproj.Models.Vehicle;
import com.example.glassfishproj.Models.Customer;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="customers", value = "/customers")
public class CustomerServlet extends HttpServlet {

    @EJB
    private VehicleService beanVehicle;
    @EJB
    private CustomerService beanCustomer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Customer customer = beanCustomer.find(Long.valueOf(req.getParameter("id")));

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>\n" +
                "<head>\n" +
                "    <title>EJBJPA</title>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                "</head>\n" +
                "<body>");

        out.println("<br><br>First name: " + customer.getFirst_name() + "<br><br>Last name:" + customer.getLast_name() + "<br>");

        out.println("<br><br>");
        out.println("Rented books<br><table>");
        out.println("<form action=\"customers\" method=\"post\">");
        out.println("<tr><th>Title</th> <th>Author</th> <th>Year</th></tr>");

        try {
            Vehicle assignedVehicle = customer.getVehicle();
            if (assignedVehicle == null)
                throw new NullPointerException();
            out.println("<tr> <td><a href=\"" + req.getContextPath() + "/customers?vehicle_id=" + assignedVehicle.getId_vehicle() + "\">" + assignedVehicle.getMake() + "</a></td>" +
                    "<td>" + assignedVehicle.getModel() + "</td>" +
                    "<td>" + assignedVehicle.getPrice() + "</td>" +
                    "<td> <button type=\"submit\" name=\"book_id\" value=\"" + assignedVehicle.getId_vehicle() + "\" >Collect</button> </td>" +
                    "</tr>");
        } catch (NullPointerException e) {
            out.println("<tr><td> No vehicle rented! </td></tr>");
        }
        out.println("</table><br><br>");
        out.println("</form>");

        out.println("Rent a new vehicle");
        out.println("<br>");
        out.println("<form action=\"customers\" method=\"post\">");
        out.println("<table>");
        out.println("<tr><th>Make</th> <th>Model</th> <th>Price</th></tr>");
        try {
            List<Vehicle> vehiclesList = beanVehicle.findAll();
            if (vehiclesList.isEmpty())
                throw new NullPointerException();
            for (Vehicle vehicle : vehiclesList) {
                if (vehicle.getCustomer() == null)
                    out.println("<tr> <td><a href=\"" + req.getContextPath() + "/vehicles?id=" + vehicle.getId_vehicle() + "\">" + vehicle.getMake() + "</a></td>" +
                            "<td>" + vehicle.getModel() + "</td>" +
                            "<td>" + vehicle.getPrice() + "</td>" +
                            "<td> <button type=\"submit\" name=\"customer_vehicle\" value=\"" + customer.getId_customer() + '_' + vehicle.getId_vehicle() + "\" >Rent</button> </td>" +
                            "</tr>");
            }
        } catch (NullPointerException e) {
            out.println("<tr><td> No vehicles found! </td></tr>");
        }
        out.println("</table>");
        out.println("</form>");

        out.println("<form action=\"main\" method=\"get\"><input type=\"submit\" value=\"Back\"></form>");
        out.println("</body>\n" +
                "</html>\n");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("customer_vehicle") != null) {
            String customer_vehicle = req.getParameter("customer_vehicle");
            beanCustomer.addVehicleToCustomer(Long.parseLong(customer_vehicle.split("_")[0]), Long.parseLong(customer_vehicle.split("_")[1]));
            resp.sendRedirect(req.getContextPath() + "/main");
            return;
        }

        if (req.getParameter("vehicle_id") != null) {
            String vehicle_id = req.getParameter("vehicle_id");
            beanCustomer.removeVehicleFromCustomer(Long.valueOf(vehicle_id));
            resp.sendRedirect(req.getContextPath() + "/main");
            return;
        }

        if (req.getParameter("customer_id") != null) {
            long id = Long.valueOf(req.getParameter("customer_id"));
            beanCustomer.deleteCustomer(id);
            resp.sendRedirect(req.getContextPath() + "/main");
            return;
        }

        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        if (first_name != null && !first_name.isEmpty() &&
                last_name != null && !last_name.isEmpty()) {
            beanCustomer.addCustomer(first_name, last_name);
            resp.sendRedirect(req.getContextPath() + "/main");
        } else {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("<html>\n" +
                    "<head>\n" +
                    "    <title>EJBJPA</title>\n" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                    "</head>\n");
            out.println("<body>First name and last name fields can't be empty!<br><br>");
            out.println("<form action=\"main\" method=\"get\"><input type=\"submit\" value=\"Back\"></form>");
            out.println("</body></html>");

            out.close();
        }
    }

}
