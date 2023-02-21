package com.example.wildflyproj.Servlets;

import com.example.wildflyproj.Interfaces.CustomerService;
import com.example.wildflyproj.Interfaces.VehicleService;
import com.example.wildflyproj.Models.Customer;

import com.example.wildflyproj.Models.Vehicle;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="main", value = "/")
public class MainServlet extends HttpServlet {

    @EJB
    private VehicleService beanVehicle;
    @EJB
    private CustomerService beanCustomer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>\n" +
                "<head>\n" +
                "    <title>EJBJPA</title>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"> "+
                "</head>\n" +
                "<body>\n" +
                "<h1>Buy a car</h1>" +
                "    <table>\n" +
                "        <tr>\n" +
                "            <th>Add new vehicle</th>\n" +
                "            <th>Add new customer</th>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "                <form action=\"vehicles\" method=\"post\">\n" +
                "                    Make <input type = \"text\" name = \"make\">\n" +
                "                    <br />\n" +
                "                    <br />\n" +
                "                    Model <input type = \"text\" name = \"model\">\n" +
                "                    <br />\n" +
                "                    <br />\n" +
                "                    Price <input type = \"text\" name = \"price\">\n" +
                "                    <br />\n" +
                "                    <br />\n" +
                "                    Registration <input type = \"text\" name = \"registration\">\n" +
                "                    <br />\n" +
                "                    <br />\n" +
                "                    <br />\n" +
                "                    <input type=\"submit\" value=\"Submit\">\n" +
                "                </form>\n" +
                "            </td>\n" +
                "            <td>\n" +
                "                <form action=\"customers\" method=\"post\">\n" +
                "                    First Name <input type = \"text\" name = \"first_name\">\n" +
                "                    <br />\n" +
                "                    <br />\n" +
                "                    <br />\n" +
                "                    Last Name <input type = \"text\" name = \"last_name\">\n" +
                "                    <br />\n" +
                "                    <br />\n" +
                "                    <br />\n" +
                "                    <input type=\"submit\" value=\"Submit\">\n" +
                "                </form>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "\n" +
                "\n" +
                "    </table>\n" +
                "\n");

        out.println("<form action=\"vehicles\" method=\"post\">");
        out.println("<br><br>");
        out.println("<table>");
        out.println("<tr><th>Make</th> <th>Model</th> <th>Price</th> <th>Buyer</th></tr>");
        try {
            List<Vehicle> vehicleList = beanVehicle.findAll();
            if (vehicleList.isEmpty())
                throw new NullPointerException();
            for (Vehicle vehicle : vehicleList) {
                if(vehicle.getCustomer() != null) {
                    out.println(
                            "<tr><td><a href=\"" + req.getContextPath() + "/vehicles?id=" + vehicle.getId_vehicle() + "\">" + vehicle.getMake() + "</a></td>" +
                                    "<td>"
                                    + vehicle.getModel() +
                                    "</td>" +
                                    "<td>"
                                    + vehicle.getPrice() +
                                    "</td>" +
                                    "<td>"
                                    + vehicle.getCustomer().getFirst_name() + " " + vehicle.getCustomer().getLast_name() +
                                    "</td>" +
                                    "</tr>");
                }
                else {
                    out.println(
                            "<tr><td><a href=\"" + req.getContextPath() + "/vehicles?id=" + vehicle.getId_vehicle() + "\">" + vehicle.getMake() + "</a></td>" +
                            "<td>"
                            + vehicle.getModel() +
                            "</td>" +
                            "<td>"
                            + vehicle.getPrice() +
                            "</td>" +
                            "</tr>");
                }
            }
        } catch (NullPointerException e) {
            out.println("<tr><td> No vehicles found! </td></tr>");
        }
        out.println("</table>");
        out.println("</form>");

        out.println("<form action=\"customers\" method=\"post\">");
        out.println("<table>");
        out.println("<tr><th>Name</th></tr>");
        try {
            List<Customer> customerList = beanCustomer.findAll();
            if (customerList.isEmpty())
                throw new NullPointerException();
            for (Customer c : customerList) {
                out.println("<tr> <td><a href=\"" + req.getContextPath() + "/customers?id=" + c.getId_customer()+ "\">" + c.getFirst_name() + ' ' + c.getLast_name() + "</a></td>" +
                        "</tr>");
            }
        } catch (NullPointerException e) {
            out.println("<tr><td> No customers found! </td></tr>");
        }

        out.println("</form>");
        out.println("</table>");
        out.println("</body>\n" +
                "</html>\n");
        out.close();
    }
}
