package p;
import java.io.*;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
public class UrlChecker extends HttpServlet {
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        RequestDispatcher rd;
        rd = sc.getRequestDispatcher("/UrlPinger");
        rd.forward(request, response);
    }
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        String checkResult = "";

        if (url == null || url.equals(""))
            checkResult = "URL Invalid. Please try again.";
        else {
            try {
                URL check = new URL(url);
                check.toURI();
            }
            catch (Exception ex) {
                checkResult = "URL Invalid. Please try again.";
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<HTML><HEAD><TITLE>Url Checker</TITLE></HEAD>");
        if(checkResult.equals("")){
            out.println("<BODY><H3>URL Valid. Want us to check if the service is up and working?</h3><br/>");
            out.println("<form method=\"GET\" action=\"UrlPinger\">");
            out.println("URL:<input type=\"text\" name=\"url\" value=\""+url+"\">");
            out.println("<input type=\"submit\" value=\"Ping website\">");
        }
        else {
            out.println("<BODY><H3>"+ checkResult +"</h3><br/>");
            out.println("<form method=\"POST\" action=\"UrlChecker\">");
            out.println("URL:<input type=\"text\" name=\"url\" value=\""+url+"\">");
            out.println("<input type=\"submit\" value=\"Check URL\">");
        }
        out.println("</BODY></HTML>");
        out.close();
    }
}
