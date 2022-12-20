package p;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
public class UrlPinger extends HttpServlet {
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        int timeout = 5000;
        String requestFailed = "";

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("HEAD");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);

        int responseCode = 0;
        String responseMessage = "";
        try {
            responseCode = conn.getResponseCode();
            responseMessage = conn.getResponseMessage();
        }
        catch (Exception ex) {
            requestFailed = "Something went wrong when connecting to the URL.";
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<HTML><HEAD><TITLE>Url Checker</TITLE></HEAD>");
        if(requestFailed.equals("")){
            out.println("<BODY><H3>Status code: " + responseCode + " - " + responseMessage + "</h3><br/>");
        }
        else{
            out.println("<BODY><H3>Something went wrong... " + requestFailed + "</h3><br/>");
        }
        out.println("</BODY></HTML>");
        out.close();
    }
}
