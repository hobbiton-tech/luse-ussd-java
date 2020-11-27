 package LuseDma.ussd.pojos;
 
 public class Security
 {
   private String pin = "";
   
   private int isauthmode = 0;
   private int authsessionlevel = -1;
   private int ispinsetupmode = 1;
   private int ispinresetmode = 0;
   private int isloggedin = 0;
    
   public String getPin() { return this.pin; }
   public int getIsAuthenticationMode() { return this.isauthmode; }
   public int getAuthSessionLevel() { return this.authsessionlevel; }
   public int getIsLoggedIn() { return this.isloggedin; }
   public int getIsPinSetupMode() { return this.ispinsetupmode; }
   public int getIsPinResetMode() { return this.ispinresetmode; }
 
   public void setPin(String v) { this.pin = v; }
   public void setIsAuthenticationMode(int v) { this.isauthmode = v; }
   public void setAuthSessionLevel(int v) { this.authsessionlevel = v; }
   public void setIsLoggedIn(int v) { this.isloggedin = v; }
   public void setIsPinSetupMode(int v) { this.ispinsetupmode = v; }
   public void setIsPinResetMode(int v) { this.ispinresetmode = v; }
   

 }
