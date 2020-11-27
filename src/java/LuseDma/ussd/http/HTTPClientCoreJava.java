 package LuseDma.ussd.http;
 
 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.OutputStream;
 import java.io.UnsupportedEncodingException;
 import java.net.HttpURLConnection;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.net.URLEncoder;
 import java.util.HashMap;
 import java.util.Iterator;

 import LuseDma.ussd.common.AppConfigHelper;

 public class HTTPClientCoreJava
 {
   private static int HTTP_GET = 1;
   private static int HTTP_POST = 2;
   private static int CONNECT_TIMEOUT = 150000;
   private static int SOCKET_TIMEOUT = 180000;

   public static String get(String endpoint, String authcode, HashMap<String, String> params) {
     endpoint = endpoint + setHTTPParams(params, 1);
     AppConfigHelper.logger.error("URL:" + endpoint);
     String response = null;
     HttpURLConnection conn = null;
     try {
       URL aPortUrl = new URL(endpoint);
       conn = (HttpURLConnection)aPortUrl.openConnection();
       conn.setConnectTimeout(CONNECT_TIMEOUT);
       conn.setReadTimeout(SOCKET_TIMEOUT);
       conn.setRequestProperty("Authorization", authcode);
       response = parseInputStream(conn.getInputStream());
     } catch (Throwable t) {
       AppConfigHelper.logger.error("HTTPClient: failed to connect to " + endpoint, t);
     } finally {
       if (conn != null) {
         try {
           conn.disconnect();
         } catch (Exception e) {
           AppConfigHelper.logger.error("HTTPClient: failed to close conn " + endpoint, e);
         } 
       }
     } 
     return response;
   }

   public static String post(String endpoint, HashMap<String, String> params) {
     URL url;
     String response = null;
     String body = setHTTPParams(params, 2);
     try {
       url = new URL(endpoint);
     } catch (MalformedURLException e) {
       AppConfigHelper.logger.error("invalid url: " + endpoint, e);
       throw new IllegalArgumentException("invalid url: " + endpoint);
     } 
     
     byte[] bytes = body.getBytes();
     HttpURLConnection conn = null;
     
     try {
       conn = (HttpURLConnection)url.openConnection();
       conn.setConnectTimeout(CONNECT_TIMEOUT);
       conn.setReadTimeout(SOCKET_TIMEOUT);
       conn.setDoOutput(true);
       conn.setUseCaches(false);
       conn.setFixedLengthStreamingMode(bytes.length);
       conn.setRequestMethod("POST");
       conn.setRequestProperty("Content-Type", "application/json");
       
       OutputStream out = conn.getOutputStream();
       out.write(bytes);
       out.close();
       
       int status = conn.getResponseCode();
       if (status != 200) {
         throw new IOException("Post failed with error code " + status);
       }
       response = parseInputStream(conn.getInputStream());
     } catch (Throwable t) {
       AppConfigHelper.logger.error("HTTPClient: failed to connect to " + endpoint, t);
     } finally {
       if (conn != null) {
         try {
           conn.disconnect();
         } catch (Exception e) {
           AppConfigHelper.logger.error("HTTPClient: failed to close conn " + endpoint, e);
         } 
       }
     } 
     return response;
   }
   public static String lazy_post(String endpoint, HashMap<String, String> params) {
     URL url;
     String response = null;
     String body = setHTTPParams(params, 2);
 
 
     
     try {
       url = new URL(endpoint);
     } catch (MalformedURLException e) {
       AppConfigHelper.logger.error("invalid url: " + endpoint, e);
       throw new IllegalArgumentException("invalid url: " + endpoint);
     } 
     
     byte[] bytes = body.getBytes();
     HttpURLConnection conn = null;
     
     try {
       conn = (HttpURLConnection)url.openConnection();
       conn.setConnectTimeout(CONNECT_TIMEOUT);
       conn.setReadTimeout(SOCKET_TIMEOUT);
       conn.setDoOutput(true);
       conn.setUseCaches(false);
       conn.setFixedLengthStreamingMode(bytes.length);
       conn.setRequestMethod("POST");
       conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
       conn.setRequestProperty("charset", "utf-8");
       conn.setRequestProperty("Content-Length", Integer.toString(bytes.length));
       
       OutputStream out = conn.getOutputStream();
       out.write(bytes);
       out.close();
       
       int status = conn.getResponseCode();
       if (status != 200) {
         throw new IOException("Post failed with error code " + status);
       }
       response = parseInputStream(conn.getInputStream());
     } catch (Throwable t) {
       AppConfigHelper.logger.error("HTTPClient: failed to connect to " + endpoint, t);
     } finally {
       if (conn != null) {
         try {
           conn.disconnect();
         } catch (Exception e) {
           AppConfigHelper.logger.error("HTTPClient: failed to close conn " + endpoint, e);
         } 
       }
     } 
     return response;
   }
   public static String rawpost(String endpoint, String body) {
     URL url;
     String response = "";
     AppConfigHelper.logger.info("URL:" + endpoint);
     AppConfigHelper.logger.info("Client Request Body:" + body);
     try {
       url = new URL(endpoint);
     } catch (MalformedURLException e) {
       AppConfigHelper.logger.error("invalid url: " + endpoint, e);
       throw new IllegalArgumentException("invalid url: " + endpoint);
     } 
     
     byte[] bytes = body.getBytes();
     HttpURLConnection conn = null;
     
     try {
       conn = (HttpURLConnection)url.openConnection();
       conn.setConnectTimeout(CONNECT_TIMEOUT);
       conn.setReadTimeout(SOCKET_TIMEOUT);
       conn.setDoOutput(true);
       conn.setUseCaches(false);
       conn.setFixedLengthStreamingMode(bytes.length);
       conn.setRequestMethod("POST");
       conn.setRequestProperty("Content-Type", "application/json");
       conn.setRequestProperty("Accept", "application/json");
       
       OutputStream out = conn.getOutputStream();
       out.write(bytes);
       out.close();
 
       
       int status = conn.getResponseCode();
       if (status != 200) {
         AppConfigHelper.logger.error("Post failed with error code " + status);
         throw new IOException("Post failed with error code " + status);
       } 
       response = parseInputStream(conn.getInputStream());
     } catch (Throwable t) {
       AppConfigHelper.logger.error("HTTPClient: failed to close conn: /" + endpoint, t);
     } finally {
       if (conn != null) {
         try {
           conn.disconnect();
         } catch (Exception e) {
           AppConfigHelper.logger.error("HTTPClient: failed to close conn: /" + endpoint, e);
         } 
       }
     } 
     return response;
   }
   
   public static String setHTTPParams(HashMap<String, String> params, int method) {
     String str = "";
     if (params != null && params.size() > 0) {
       if (method == HTTP_POST) {
         str = str + "&";
       } else {
         str = str + "?";
       } 
       int index = 0;
       Iterator<String> keySetIterator = params.keySet().iterator();
       while (keySetIterator.hasNext()) {
         String key = keySetIterator.next();
         String value = params.get(key);
         str = str + setvar(index, key, value);
         index++;
       } 
     } 
     return str;
   }
   public static String setvar(int index, String argname, String argvalue) {
     String url_str = "";
     if (argname != null && 
       argvalue != null) {
       if (index == 0) {
         url_str = url_str + argname + "=";
       } else {
         url_str = url_str + "&" + argname + "=";
       } 
       try {
         String encoded = URLEncoder.encode(argvalue, "UTF-8");
         url_str = url_str + encoded;
       }
       catch (UnsupportedEncodingException e) {
         url_str = url_str + argvalue;
         AppConfigHelper.logger.error("Failed to encode GET request URL", e);
       } 
     } 
     
     return url_str;
   }
   
   public static String parseInputStream(InputStream is) {
     BufferedReader reader = new BufferedReader(new InputStreamReader(is));
     StringBuilder sb = new StringBuilder();
     String line = null;
     try {
       while ((line = reader.readLine()) != null) {
         sb.append(line + "\n");
       }
     } catch (IOException e) {
       AppConfigHelper.logger.error("HTTPClient: failed to parse InputStream", e);
     } finally {
       try {
         is.close();
       } catch (IOException e) {
         AppConfigHelper.logger.error("HTTPClient: failed to parse InputStream", e);
       } catch (NullPointerException e) {
         AppConfigHelper.logger.error("HTTPClient: failed to parse InputStream", e);
       } 
     } 
     return sb.toString();
   }
   
   public static String encodedCredentials(String username, String password) { return "Basic " + base64Encode(username + ":" + password); }
   
   private static final char[] base64Array = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

   private static String base64Encode(String string) {
     String encodedString = "";
     byte[] bytes = string.getBytes();
     int i = 0;
     int pad = 0;
     while (i < bytes.length) {
       byte b3, b2, b1 = bytes[i++];
 
       
       if (i >= bytes.length) {
         b2 = 0;
         b3 = 0;
         pad = 2;
       } else {
         
         b2 = bytes[i++];
         if (i >= bytes.length) {
           b3 = 0;
           pad = 1;
         } else {
           
           b3 = bytes[i++];
         } 
       }  byte c1 = (byte)(b1 >> 2);
       byte c2 = (byte)((b1 & 0x3) << 4 | b2 >> 4);
       byte c3 = (byte)((b2 & 0xF) << 2 | b3 >> 6);
       byte c4 = (byte)(b3 & 0x3F);
       encodedString = encodedString + base64Array[c1];
       encodedString = encodedString + base64Array[c2];
       switch (pad) {
         case 0:
           encodedString = encodedString + base64Array[c3];
           encodedString = encodedString + base64Array[c4];
         
         case 1:
           encodedString = encodedString + base64Array[c3];
           encodedString = encodedString + "=";
         
         case 2:
           encodedString = encodedString + "==";
       } 
     
     } 
     return encodedString;
   }
 }

