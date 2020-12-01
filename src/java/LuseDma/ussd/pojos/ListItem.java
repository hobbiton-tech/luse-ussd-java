 package LuseDma.ussd.pojos;
 
 import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 import com.fasterxml.jackson.annotation.JsonProperty;
 import org.bson.types.ObjectId;

 @JsonIgnoreProperties(ignoreUnknown = true)
 public class ListItem
 {
   private ObjectId id;
   @JsonProperty("itemid")
   private String _itemid = null; @JsonProperty("itemname")
   private String _itemname = null;
   
   public ListItem(String itemid, String itemname) {
     this();
     this._itemid = itemid;
     this._itemname = itemname;
   }
   public ListItem() {}
   public ObjectId getId() { return this.id; }
   @JsonProperty("itemid")
   public String getItemID() { return this._itemid; }
   @JsonProperty("itemname")
   public String getItemName() { return this._itemname; }
   
   public void getId(ObjectId value) { this.id = value; }

     @Override
     public String toString() {
         return "ListItem{" +
                 "id=" + id +
                 ", _itemid='" + _itemid + '\'' +
                 ", _itemname='" + _itemname + '\'' +
                 '}';
     }

     public void setItemID(String value) { this._itemid = value; }
   public void setItemName(String value) { this._itemname = value; }
 }
