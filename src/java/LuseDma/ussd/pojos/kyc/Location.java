/*    */ package LuseDma.ussd.pojos.kyc;

import LuseDma.ussd.pojos.Attribute;

/*    */ public class Location
/*    */ {
/* 22 */   private double latitude = 0.0D;
/* 23 */   private double longitude = 0.0D;
/* 24 */   private String address = "Unknown";
/* 25 */   private Attribute city = new Attribute(0, "Unknown");
/* 26 */   private Attribute province = new Attribute(0, "Unknown"); public Location() {}
/* 27 */   private Attribute country = new Attribute(0, "Unknown");
/*    */   
/*    */   public Location(double latitude, double longitude) {
/* 30 */     this();
/* 31 */     this.latitude = latitude;
/* 32 */     this.longitude = longitude;
/*    */   }
/*    */   public Location(double latitude, double longitude, String address) {
/* 35 */     this();
/* 36 */     this.latitude = latitude;
/* 37 */     this.longitude = longitude;
/* 38 */     this.address = address;
/*    */   }
/*    */   public Location(Attribute province) {
/* 41 */     this();
/* 42 */     this.province = province;
/*    */   }
/*    */   public Location(Attribute city, Attribute province) {
/* 45 */     this();
/* 46 */     this.city = city;
/* 47 */     this.province = province;
/*    */   }
/*    */   public Location(double latitude, double longitude, String address, Attribute city, Attribute province) {
/* 50 */     this();
/* 51 */     this.latitude = latitude;
/* 52 */     this.longitude = longitude;
/* 53 */     this.city = city;
/* 54 */     this.province = province;
/*    */   }
/*    */   public Location(double latitude, double longitude, String address, Attribute city, Attribute province, Attribute country) {
/* 57 */     this();
/* 58 */     this.latitude = latitude;
/* 59 */     this.longitude = longitude;
/* 60 */     this.address = address;
/* 61 */     this.city = city;
/* 62 */     this.province = province;
/* 63 */     this.country = country;
/*    */   }
/*    */   
/* 66 */   public double getLatitude() { return this.latitude; }
/* 67 */   public double getLongitude() { return this.longitude; }
/* 68 */   public String getAddress() { return this.address; }
/* 69 */   public Attribute getCity() { return this.city; }
/* 70 */   public Attribute getProvince() { return this.province; }
/* 71 */   public Attribute getCountry() { return this.country; }
/*    */   
/* 73 */   public void setLatitude(double v) { this.latitude = v; }
/* 74 */   public void setLongitude(double v) { this.longitude = v; }
/* 75 */   public void setAddress(String v) { this.address = v; }
/* 76 */   public void setCity(Attribute v) { this.city = v; }
/* 77 */   public void setProvince(Attribute v) { this.province = v; }
/* 78 */   public void setCountry(Attribute v) { this.country = v; }
/*    */ }
