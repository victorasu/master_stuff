import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(p.ClientHtml.class, "/client.html");
        handler.addServletWithMapping(p.UrlChecker.class, "/UrlChecker");
        handler.addServletWithMapping(p.UrlPinger.class, "/UrlPinger");
        System.out.println("Start server jetty embedded");
        server.start();
        server.join();
    }
}
