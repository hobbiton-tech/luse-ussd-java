 package LuseDma.ussd.common;
 
 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import java.text.SimpleDateFormat;
 import java.util.Date;

 public class GsonUtil
 {
   public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXX";
   private static Gson gson;
   private static Gson gsonExpose;
   private static SimpleDateFormat sdf;
   
   public static Gson getInstance() {
     if (gson == null) {
       gson = new Gson();//getGsonBuilderInstance(false).create();
     }
     return gson;
   }
   
   public static Gson getExposeInstance() {
     if (gsonExpose == null) {
       gsonExpose = getGsonBuilderInstance(true).create();
     }
     return gsonExpose;
   }
   
   public static Gson getInstance(boolean onlyExpose) {
     if (!onlyExpose) {
       if (gson == null) {
         gson = getGsonBuilderInstance(false).create();
       }
       return gson;
     } 
     if (gsonExpose == null) {
       gsonExpose = getGsonBuilderInstance(true).create();
     }
     return gsonExpose;
   }
 
   
   public static SimpleDateFormat getSDFInstance() {
     if (sdf == null) {
       sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXX");
     }
     return sdf;
   }
   
   private static GsonBuilder getGsonBuilderInstance(boolean onlyExpose) {
     GsonBuilder gsonBuilder = new GsonBuilder();
     if (onlyExpose) {
       gsonBuilder.excludeFieldsWithoutExposeAnnotation();
     }
     gsonBuilder.registerTypeAdapter(Date.class, new Object());
     gsonBuilder.registerTypeAdapter(Date.class, new Object());
     gsonBuilder.setPrettyPrinting();
     gsonBuilder.serializeNulls();
     return gsonBuilder;
   }
 
   
   public static <T> T fromJson(String json, Class<T> classOfT, boolean onlyExpose) {
     try {
       return (T)getInstance(onlyExpose).fromJson(json, classOfT);
     } catch (Exception ex) {
       
       return null;
     } 
   }
 }
