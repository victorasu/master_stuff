import java.io.*;
import org.apache.catalina.*;
import org.apache.catalina.startup.*;

public class TomcatServer {
    public static void main(String[] args) throws Exception {
        Tomcat server = new Tomcat();
        server.setPort(8080);
        Context ctx = server.addContext("", (new File(".")).getAbsolutePath());
        Tomcat.addServlet(ctx, "client", new p.ClientHtml());
        Tomcat.addServlet(ctx, "urlChecker", new p.UrlChecker());
        Tomcat.addServlet(ctx, "urlPinger", new p.UrlPinger());
        ctx.addServletMapping("", "client");
        ctx.addServletMapping("/client.html", "client");
        ctx.addServletMapping("/UrlChecker", "urlChecker");
        ctx.addServletMapping("/UrlPinger", "urlPinger");
        server.start();
        System.out.println("Start server Tomcat embedded");
        server.getServer().await();
    }
}
