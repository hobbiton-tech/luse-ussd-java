 package LuseDma.ussd.common;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;

 import com.fasterxml.jackson.databind.ObjectMapper;
 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;

public class JsonHelper
{
 public static Object toJSON(Object object) throws JSONException {
        if (object instanceof Map) {
            JSONObject json = new JSONObject();
            Map map = (Map) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable)object)) {
                json.put(value);
            }
            return json;
        } else {
            return object;
        }
    }
 
 
   
   public static boolean isEmptyObject(JSONObject object) { return (object.names() == null); }
 
 
   
   public static Map<String, Object> getMap(JSONObject object, String key) throws JSONException { return toMap(object.getJSONObject(key)); }
 
   
   public static Map<String, Object> toMap(JSONObject object) {
     Map<String, Object> map = new HashMap<>();
     
     Iterator<String> keys = object.keys();
     while (keys.hasNext()) {
       String key = keys.next();
       try {
         map.put(key, fromJson(object.get(key)));
       } catch (JSONException jSONException) {}
     } 
     return map;
   }
   public static Map<String, Object> objectToMap(Object object) {
     Map<String, Object> map = new HashMap<>();
    try {
        ObjectMapper oMapper = new ObjectMapper();
        // object -> Map
        map = oMapper.convertValue(object, Map.class);
    } catch (Exception e) {
        e.printStackTrace();
    }
     return map;
   }
   public static Object mapToObjectt(Map<String, Object> map) {
    Object object = new Object();
    try {
        ObjectMapper oMapper = new ObjectMapper();
        // object -> Map
        object = oMapper.convertValue(map, Object.class);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return object;
   }
   public static <T> T mapToObject(Map<String, Object> map,Class<?> target) {
       ObjectMapper objectMapper = new ObjectMapper();
       return objectMapper.convertValue(map, (Class<T>) target);
   }
   public static List toList(JSONArray array) throws JSONException {
     List<Object> list = new ArrayList();
     for (int i = 0; i < array.length(); i++) {
       list.add(fromJson(array.get(i)));
     }
     return list;
   }
   
   private static Object fromJson(Object json) throws JSONException {
     if (json == JSONObject.NULL)
       return null; 
     if (json instanceof JSONObject)
       return toMap((JSONObject)json); 
     if (json instanceof JSONArray) {
       return toList((JSONArray)json);
     }
     return json;
   }
 }
