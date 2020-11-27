 package LuseDma.ussd.pojos;
 
 import java.util.ArrayList;

 public class GenericResponse<T>
 {
   private int responsecode = 0;
   private String responsemessage = null;
   private T item = null; public GenericResponse() {}
   private ArrayList<T> items = null;
   
   public GenericResponse(int responsecode, String responsemessage) {
     this();
     this.responsecode = responsecode;
     this.responsemessage = responsemessage;
   }
   public GenericResponse(int responsecode, String responsemessage, T item) {
     this();
     this.responsecode = responsecode;
     this.responsemessage = responsemessage;
     this.item = item;
   }
   public GenericResponse(int responsecode, String responsemessage, ArrayList<T> items) {
     this();
     this.responsecode = responsecode;
     this.responsemessage = responsemessage;
     this.items = items;
   }
   
   public void setResponseCode(int v) { this.responsecode = v; }
 
   
   public void setResponseMessage(String v) { this.responsemessage = v; }
 
   
   public void setItem(T v) { this.item = v; }
 
   
   public void setItems(ArrayList<T> v) { this.items = v; }
 
   
   public int getResponseCode() { return this.responsecode; }
 
   
   public String getResponseMessage() { return this.responsemessage; }
 
   
   public T getItem() { return this.item; }
 
   
   public ArrayList<T> getItems() { return this.items; }
 }
