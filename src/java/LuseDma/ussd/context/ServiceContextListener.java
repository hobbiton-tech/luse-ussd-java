 package LuseDma.ussd.context;
 
 import javax.servlet.ServletContextEvent;
 import javax.servlet.ServletContextListener;
 
 public class ServiceContextListener
   implements ServletContextListener
 {
   public void contextInitialized(ServletContextEvent sce) { System.out.println("ServletContextListener started"); }
 
 
 
   
   public void contextDestroyed(ServletContextEvent sce) { System.out.println("ServletContextListener stopped"); }
 }

