package p;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class ClientHtml extends HttpServlet {
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
            out.println("<title>URL Check and Ping</title>");
        out.println("</head>");
        out.println("<body>");
            out.println("<form method=\"POST\" action=\"UrlChecker\">");
                out.println("URL:<input type=\"text\" name=\"url\">");
                out.println("<input type=\"submit\" value=\"Check URL\" />");
            out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
