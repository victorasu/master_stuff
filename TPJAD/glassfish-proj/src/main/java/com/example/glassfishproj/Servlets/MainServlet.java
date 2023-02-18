package com.example.glassfishproj.Servlets;

import com.example.glassfishproj.Interfaces.CustomerService;
import com.example.glassfishproj.Interfaces.VehicleService;
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
                "<h1>Rent a car</h1>" +
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



        out.println("</body>\n" +
                "</html>\n");
        out.close();
    }
}
