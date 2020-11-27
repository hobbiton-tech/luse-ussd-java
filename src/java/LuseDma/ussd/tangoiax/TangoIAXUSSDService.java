 package LuseDma.ussd.tangoiax;
 
 import java.io.IOException;
 import java.io.InputStream;
 import javax.servlet.ServletException;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

 import org.apache.xmlrpc.XmlRpcServer;
 
 
 public class TangoIAXUSSDService
   extends HttpServlet
 {
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     XmlRpcServer xmlrpc = new XmlRpcServer();
     xmlrpc.addHandler("$default", new TangoIAXUSSDHandler());
     byte[] result = xmlrpc.execute((InputStream)request.getInputStream());
     response.setContentType("text/xml");
     response.setContentLength(result.length);
     ServletOutputStream servletOutputStream = response.getOutputStream();
     servletOutputStream.write(result);
     servletOutputStream.flush();
   }
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { processRequest(request, response); }
   public String getServletInfo() { return "Short description"; }
 }

