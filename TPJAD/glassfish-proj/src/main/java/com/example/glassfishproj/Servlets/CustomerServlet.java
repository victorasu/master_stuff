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

        out.println("Rent a new vehicle");
        out.println("<br>");
        out.println("<form action=\"customers\" method=\"post\">");
        out.println("Vehicle ID: <input type=\"text\" name=\"vehicle_id\">");
        out.println("<button type=\"submit\" name=\"customer_vehicle\" value=\"" + customer.getId_customer() + '_' + "\" >Rent</button> </td>");
        out.println("</form>");

        out.println("Delete customer");
        out.println("<br>");
        out.println("<form action=\"customers\" method=\"post\">");
        out.println("<button type=\"submit\" name=\"customer_id\" value=\"" + customer.getId_customer() + "\" >Delete</button>");
        out.println("</form>");

        out.println("<form action=\"main\" method=\"get\"><input type=\"submit\" value=\"Back\"></form>");
        out.println("</body>\n" +
                "</html>\n");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("customer_vehicle") != null) {
            String customerId = req.getParameter("customer_vehicle");
            String vehicleId = req.getParameter("vehicle_id");
            beanCustomer.addVehicleToCustomer(Long.parseLong(customerId), Long.parseLong(vehicleId));
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
