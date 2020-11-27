 package LuseDma.ussd.http;
 
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.concurrent.TimeUnit;

 import LuseDma.ussd.common.AppConfigHelper;
 import okhttp3.FormBody;
 import okhttp3.HttpUrl;
 import okhttp3.MediaType;
 import okhttp3.OkHttpClient;
 import okhttp3.Request;
 import okhttp3.RequestBody;
 import okhttp3.Response;

 public class HTTPClientByOkHttp
 {
   private static final int MAX_ATTEMPTS = 5;
   private static final int SOCKET_TIMEOUT = 30;
   private static final int WRITE_TIMEOUT = 30;
   private static final int READ_TIMEOUT = 40;
   private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
 
   public static String doGetRequest(String endpoint, HashMap<String, String> params) {
     try {
       OkHttpClient client = (new OkHttpClient.Builder()).connectTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS).readTimeout(40L, TimeUnit.SECONDS).build();
       
       String url = getHttpUrl(endpoint, params).toString();

       Request request = (new Request.Builder()).header("User-Agent", "USSD App").addHeader("X-API-KEY", AppConfigHelper.API_KEY).url(url).build();
       
       Response response = client.newCall(request).execute();
       
       String resp = response.body().string();
       AppConfigHelper.logger.error("doGetRequest:" + resp);
       return resp;
     } catch (Exception e) {
       AppConfigHelper.logger.error("HTTPClientByOkHttp:doGetRequest:", e);
       return e.getMessage();
     } 
   }

   
   public static String doLazyGetRequest(String endpoint, HashMap<String, String> params) {
     try {
       OkHttpClient client = (new OkHttpClient.Builder()).connectTimeout(300L, TimeUnit.MILLISECONDS).writeTimeout(300L, TimeUnit.MILLISECONDS).readTimeout(300L, TimeUnit.MILLISECONDS).build();
       
       String url = getHttpUrl(endpoint, params).toString();

       Request request = (new Request.Builder()).header("User-Agent", "USSD App").addHeader("X-API-KEY", AppConfigHelper.API_KEY).url(url).build();
       
       Response response = client.newCall(request).execute();
       
       String resp = response.body().string();
       AppConfigHelper.logger.error("doGetRequest:" + resp);
       return resp;
     } catch (Exception e) {
       AppConfigHelper.logger.error("HTTPClientByOkHttp:doGetRequest:", e);
       return e.getMessage();
     } 
   }

   
   public static String doRawJSONPostRequest(String url, String json) throws IOException {
     try {
       OkHttpClient client = (new OkHttpClient.Builder()).connectTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS).readTimeout(40L, TimeUnit.SECONDS).build();
       
       RequestBody body = RequestBody.create(JSON, json);

       Request request = (new Request.Builder()).url(url).header("User-Agent", "USSD App").addHeader("Content-Type", "application/json").addHeader("Accept", "application/json").addHeader("X-API-KEY", AppConfigHelper.API_KEY).post(body).build();
       
       Response response = client.newCall(request).execute();

       String resp = response.body().string();
       AppConfigHelper.logger.error("doRawJSONPostRequest:" + resp);
       return resp;
     } catch (Exception e) {
       AppConfigHelper.logger.error("HTTPClientByOkHttp:doRawJSONPostRequest:", e);
       return e.getMessage();
     } 
   }
 
 
 
   
   public static String doPostRequest(String endpoint, HashMap<String, String> params) {
     try {
       OkHttpClient client = (new OkHttpClient.Builder()).connectTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS).readTimeout(40L, TimeUnit.SECONDS).build();
       
       RequestBody formBody = getRequestBody(params);

       Request request = (new Request.Builder()).url(endpoint).header("User-Agent", "USSD App").addHeader("Content-Type", "application/json").addHeader("X-API-KEY", AppConfigHelper.API_KEY).addHeader("Accept", "application/json").post(formBody).build();
       
       Response response = client.newCall(request).execute();

       String resp = response.body().string();
       AppConfigHelper.logger.error("doPostRequest:" + resp);
       return resp;
     } catch (Exception e) {
       AppConfigHelper.logger.error("HTTPClientByOkHttp:doPostRequest:", e);
       return e.getMessage();
     } 
   }
   public static RequestBody getRequestBody(HashMap<String, String> params) {
     FormBody.Builder builder = new FormBody.Builder();
     Iterator<String> keySetIterator = params.keySet().iterator();
     while (keySetIterator.hasNext()) {
       String key = keySetIterator.next();
       String value = params.get(key);
       builder.add(key, value);
     } 
     return (RequestBody)builder.build();
   }
   public static HttpUrl getHttpUrl(String endpoint, HashMap<String, String> params) {
     HttpUrl.Builder builder = HttpUrl.parse(endpoint).newBuilder();
     Iterator<String> keySetIterator = params.keySet().iterator();
     while (keySetIterator.hasNext()) {
       String key = keySetIterator.next();
       String value = params.get(key);
       builder.addQueryParameter(key, value);
     } 
     return builder.build();
   }
 }

