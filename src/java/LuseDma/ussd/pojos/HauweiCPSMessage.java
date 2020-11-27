 package LuseDma.ussd.pojos;
 
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;

 @XmlRootElement(name = "cps-message")
 public class HauweiCPSMessage
 {
   private int sequence_number = 0;
   private int version = 0;
   private String service_type = null;
   private String source_addr = null;
   private String dest_addr = null;
   private String timestamp = null;
   private int command_status = 0;
   private int data_coding = 0;
   private int msg_len = 0; public HauweiCPSMessage() {}
   private String msg_content = null;
   
   public HauweiCPSMessage(int sequence_number, String service_type) {
     this();
     this.sequence_number = sequence_number;
     this.service_type = service_type;
   }
   public HauweiCPSMessage(int sequence_number, String service_type, String source_addr) {
     this();
     this.sequence_number = sequence_number;
     this.service_type = service_type;
     this.source_addr = source_addr;
   }
   
   @XmlElement
   public void setsequence_number(int v) { this.sequence_number = v; }
 
   
   @XmlElement
   public void setversion(int v) { this.version = v; }
 
   
   @XmlElement
   public void setservice_type(String v) { this.service_type = v; }
 
   
   @XmlElement
   public void setsource_addr(String v) { this.source_addr = v; }
 
   
   @XmlElement
   public void setdest_addr(String v) { this.dest_addr = v; }
 
   
   @XmlElement
   public void settimestamp(String v) { this.timestamp = v; }
 
   
   @XmlElement
   public void setcommand_status(int v) { this.command_status = v; }
 
   
   @XmlElement
   public void setdata_coding(int v) { this.data_coding = v; }
 
   
   @XmlElement
   public void setmsg_len(int v) { this.msg_len = v; }
 
   
   @XmlElement
   public void setmsg_content(String v) { this.msg_content = v; }
 
 
 
   
   public int getsequence_number() { return this.sequence_number; }
 
   
   public int getversion() { return this.version; }
 
   
   public String getservice_type() { return this.service_type; }
 
   
   public String getsource_addr() { return this.source_addr; }
 
   
   public String getdest_addr() { return this.dest_addr; }
 
   
   public String gettimestamp() { return this.timestamp; }
 
   
   public int getcommand_status() { return this.command_status; }
 
   
   public int getdata_coding() { return this.data_coding; }
 
   
   public int getmsg_len() { return this.msg_len; }
 
   
   public String getmsg_content() { return this.msg_content; }
 }
