 package LuseDma.ussd.handlers.demo;

 import org.json.JSONArray;
// import org.json.JSONObject;
 import org.json.simple.parser.JSONParser;
 import org.json.simple.JSONObject;



 import java.io.FileNotFoundException;
 import java.net.URI;
 import java.net.http.HttpClient;
 import java.net.http.HttpRequest;
 import java.time.Duration;
 import java.net.http.HttpResponse;

 public class DataServiceHandler
 {
   public static JSONArray getSectors() {
     JSONArray data = new JSONArray();
     try {
       JSONObject o = new JSONObject();
       o.put("id", "1");
       o.put("name", "Marketeer");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "2");
       o.put("name", "Bus Driver/Conductor");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "3");
       o.put("name", "Saw Miller");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "4");
       o.put("name", "Welder");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "5");
       o.put("name", "Carpenter");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "6");
       o.put("name", "Farmer");
       data.put(o);
     } catch (Exception exception) {}
     return data;
   }
 
   public static JSONArray getProvinces() {
     JSONArray data = new JSONArray();
 
     
     try {
       JSONObject o = new JSONObject();
       o.put("id", "1");
       o.put("name", "Lusaka");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "2");
       o.put("name", "Central");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "3");
       o.put("name", "Copperbelt");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "4");
       o.put("name", "Northern");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "5");
       o.put("name", "Luapula");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "6");
       o.put("name", "Eastern");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "7");
       o.put("name", "North-Western");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "8");
       o.put("name", "Western");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "9");
       o.put("name", "Southern");
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "10");
       o.put("name", "Muchinga");
       data.put(o);
     } catch (Exception exception) {}
     return data;
   }
 
   
   public static JSONArray getRelations() {
     JSONArray data = new JSONArray();
     try {
       JSONObject o = new JSONObject();
       o.put("id", "16");
       o.put("name", "DAUGHTER");
       data.put(o);
       o = new JSONObject();
       o.put("id", "17");
       o.put("name", "SON");
       data.put(o);
       o = new JSONObject();
       o.put("id", "18");
       o.put("name", "WIFE");
       data.put(o);
       o = new JSONObject();
       o.put("id", "19");
       o.put("name", "HUSBAND");
       data.put(o);
 
 
 
 
 
 
 
 
 
 
 
 
 
 
     
     }
     catch (Exception exception) {}
     return data;
   }
   
   public static JSONArray getStatusDescriptors() {
     JSONArray data = new JSONArray();
 
     
     try {
       JSONObject o = new JSONObject();
       o.put("id", "1");
       o.put("name", "Accurate Claim (Bank Payment)");
       o.put("condition", "Amount greater than K101");
       JSONArray statuses = new JSONArray();
       JSONObject status = new JSONObject();
       status.put("id", "1");
       status.put("name", "Status 1");
       status.put("description", "Your application has been received, check progress after (Date logged + 10 working days).");
       statuses.put(status);
       
       status = new JSONObject();
       status.put("id", "2");
       status.put("name", "Status 2");
       status.put("description", "[amount with 2 decimal points] has been approved for bank authorization, check again on the system  within 10 working days.");
       statuses.put(status);
       
       status = new JSONObject();
       status.put("id", "3");
       status.put("name", "Status 3");
       status.put("description", "Your Claim is paid. Please check your bank account.");
       statuses.put(status);
       o.put("statuses", statuses);
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "2");
       o.put("name", "Accurate Claim(Cash Payment)");
       o.put("condition", "[amount less than K101]");
       statuses = new JSONArray();
       status = new JSONObject();
       status.put("id", "1");
       status.put("name", "Status 1");
       status.put("description", "Your application has been received, check progress after (Date logged + 10 working days).");
       statuses.put(status);
       
       status = new JSONObject();
       status.put("id", "2");
       status.put("name", "Status 2");
       status.put("description", "[amount with 2 decimal points] has been approved for collection, please visit your nearest NAPSA office.");
       statuses.put(status);
       
       status = new JSONObject();
       status.put("id", "3");
       status.put("name", "Status 3");
       status.put("description", "[amount with 2 decimal points] has been collected from NAPSA.");
       status.put("note", "Note: Lump Sum payment needs to include Payment details such as Bank Name,Account Name, Sort Code.");
       statuses.put(status);
       o.put("statuses", statuses);
       data.put(o);
 
       
       o = new JSONObject();
       o.put("id", "3");
       o.put("name", "Rejected Claim");
       o.put("condition", "Rejected");
       statuses = new JSONArray();
       status = new JSONObject();
       status.put("id", "1");
       status.put("name", "Status");
       status.put("description", "We are unable to process your claim, kindly contact customer services where the claim was received for further info.");
       statuses.put(status);
       o.put("statuses", statuses);
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "4");
       o.put("name", "Bounced at Bank");
       o.put("condition", "Bounced");
       statuses = new JSONArray();
       status = new JSONObject();
       status.put("id", "1");
       status.put("name", "Status 1");
       status.put("description", "Final authorisation for your payment was not granted, kindly visit the nearest Napsa office, call using a toll free line 677 or Leave message on Feedback form.");
       statuses.put(status);
       
       status = new JSONObject();
       status.put("id", "1");
       status.put("name", "Status 2");
       status.put("description", "Final authorisation for your payment was not granted, kindly visit the nearest Napsa office, call using a toll free line 677 or Leave message on Feedback form.");
       statuses.put(status);
       o.put("statuses", statuses);
       data.put(o);
       
       o = new JSONObject();
       o.put("id", "4");
       o.put("name", "On Payroll");
       o.put("condition", "Bounced");
       statuses = new JSONArray();
       status = new JSONObject();
       status.put("id", "1");
       status.put("name", "Status");
       status.put("description", "Current beneficiary is on the payroll. Kindly visit the nearest Napsa office for more details.");
       status.put("note", "Note: This status will be provided on condition that the member has a valid life certificate.");
       statuses.put(status);
       o.put("statuses", statuses);
       data.put(o);
 
     
     }
     catch (Exception exception) {}
     return data;
   }

   public static JSONArray getStocks() {
     JSONArray data = new JSONArray();


     try {
       JSONObject o = new JSONObject();
       o.put("id", "1");
       o.put("security", "AIRTEL");
       data.put(o);

       o = new JSONObject();
       o.put("id", "2");
       o.put("security", "CEC");
       data.put(o);

       o = new JSONObject();
       o.put("id", "3");
       o.put("security", "TEST3");
       data.put(o);

       o = new JSONObject();
       o.put("id", "4");
       o.put("security", "TEST4");
       data.put(o);

       o = new JSONObject();
       o.put("id", "5");
       o.put("security", "TEST5");
       data.put(o);

       o = new JSONObject();
       o.put("id", "6");
       o.put("security", "TEST6");
       data.put(o);

       o = new JSONObject();
       o.put("id", "7");
       o.put("security", "TEST7");
       data.put(o);

       o = new JSONObject();
       o.put("id", "8");
       o.put("security", "TEST8");
       data.put(o);

       o = new JSONObject();
       o.put("id", "9");
       o.put("security", "TEST9");
       data.put(o);

       o = new JSONObject();
       o.put("id", "10");
       o.put("security", "TEST10");
       data.put(o);

     } catch (Exception exception) {}
     return data;
   }

   public static JSONArray getAllTestUsers(){
     JSONArray data = new JSONArray();
     try{
       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create("http://localhost:3000/user/all"))
               .timeout(Duration.ofMinutes(1))
               .header("Content-Type", "application/json")
               .POST(HttpRequest.BodyPublishers.noBody())
               .build();
       var client = HttpClient.newHttpClient();
       var response = client.send(request, HttpResponse.BodyHandlers.ofString());
       System.out.println("result ==>"+response.body());
       JSONParser parse = new JSONParser();
       JSONObject resObj = (JSONObject) parse.parse(response.body().toString());

       JSONObject o = new JSONObject();
       o.put("id", "1");
       o.put("email", "malingasimz@gmail.com");
       data.put(o);
     }
     catch(Exception e){System.out.println("error==>"+e.getMessage());}
     System.out.println("json data ==>"+data);
     return data;
   }

 }

