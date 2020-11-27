 package LuseDma.ussd.helpers;
 
 import java.text.DecimalFormat;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;

 import LuseDma.ussd.models.USSDSessionModel;
 import LuseDma.ussd.pojos.USSDResponse;
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.models.SubscribersModel;

 import LuseDma.ussd.pojos.Account;
 import LuseDma.ussd.pojos.demo.LoanItem;
 import LuseDma.ussd.pojos.FormSession;
 import LuseDma.ussd.pojos.MobileSession;
 import LuseDma.ussd.pojos.Security;
 import LuseDma.ussd.pojos.SelectedOption;
 import LuseDma.ussd.pojos.SelectionSession;
 import LuseDma.ussd.pojos.demo.TermsAcceptance;


 public class USSDSession
 {
   private USSDSessionModel model;
   private USSDSessionHelper ussdsessionhelper = null;
   private MobileSession mobilesession;
   private Security security;
   private DecimalFormat df = new DecimalFormat("#.##"); private int sessionlevel;
   private int sessionmode;
   private int currentpage;
   int islastpage;
   private int offset;
   private int priorperpage;
   private int perpage;
   private USSDResponse response;
   private final String newLine = String.format("%n", new Object[0]); private Pattern pattern;
   public USSDSession(USSDSessionHelper object) {
     this.ussdsessionhelper = object;
     try {
       this.model = this.ussdsessionhelper.getUSSDMongoModel();
       this.mobilesession = this.model.find("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}");
       this.security = this.mobilesession.getSecurity();
     } catch (Exception e) {
       AppConfigHelper.logger.error("USSDSession - constructor():Failed to get database connections", e);
       throw new RuntimeException("USSDSession - constructor():Failed to get database connections");
     } 
   }
   private Matcher matcher; private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
   public String getMSISDN() { return this.ussdsessionhelper.getMSISDN(); }
   public int getSessionLevel() { return this.sessionlevel; }
   public int getSessionMode() { return this.sessionmode; }
   public int getSessionLevelOption(int index) { return ((Integer)this.mobilesession.getOptions().get(index)).intValue(); }
   public int getLastSessionLevelOption() { return ((Integer)this.mobilesession.getOptions().get(this.mobilesession.getOptions().size() - 1)).intValue(); }
   public ArrayList<Integer> getSessionLevelOptions() { return this.mobilesession.getOptions(); }
   public int getCurrentPage() { return this.currentpage; }
   public int getIsLastPage() { return this.islastpage; }
   public int getPriorPerPage() { return this.priorperpage; }
   public int getPerPage() { return this.perpage; }
   public int getOffSet() { return this.offset; }
   public String getUserInput() { return this.ussdsessionhelper.getUserInput(); }
   public USSDSessionHelper getUSSDSessionHelper() { return this.ussdsessionhelper; }
   public MobileSession getMobileSession() { return this.mobilesession; }
   public Security getSecurity() { return this.security; }
   public USSDSessionModel getUSSDMongoModel() { return this.model; }
   public USSDResponse buildUSSDResponse(String r, int s) {
     this.response = new USSDResponse(this.ussdsessionhelper);
     this.response.setSessionStatus(s);
     this.response.setResponseMessage(r);
     return this.response;
   }
   
   public void setUSSDSession() {
     this.offset = this.mobilesession.getOffSet();
     this.priorperpage = this.mobilesession.getPiorPerPage();
     this.perpage = this.mobilesession.getPerPage();
     this.currentpage = this.mobilesession.getCurrentPage();
     this.islastpage = this.mobilesession.getIsLastPage();
     this.sessionlevel = this.mobilesession.getSessionLevel();
     this.sessionmode = this.mobilesession.getSessionMode();
   }
   
   public void setUserInput(String value) { this.ussdsessionhelper.setUserInput(value); }
   
   public void setCurrentPage(int value) {
     this.currentpage = value;
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {currentpage:" + this.currentpage + "}}");
   }
   public void setPerPage(int value) {
     this.perpage = value;
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {perpage:" + this.perpage + "}}");
   }
   public void setPriorPerPage(int value) {
     this.priorperpage = value;
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {priorperpage:" + this.priorperpage + "}}");
   }
   public void setOffSet(int value) {
     this.offset = value;
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {offset:" + this.offset + "}}");
   }
   
   public void saveIsNewSubscriber(Boolean v) { this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {is_new_subscriber:" + v + "}}"); }
   
   public void setIsLastPage(int value) {
     this.islastpage = value;
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {islastpage:" + this.islastpage + "}}");
   }
   public void setSessionLevel(int value) { this.sessionlevel = value; }
   
   public void setSessionLevelOption() {
     int level = this.sessionlevel;
     if(level < 0)
         level = 0;
     
     if (this.sessionmode == 0) {
       int option; try {
         option = Integer.parseInt(getUserInput());
       } catch (NumberFormatException e) {
         AppConfigHelper.logger.error("Exception:", e);
         option = 1004;
       } 
       if (this.mobilesession.getOptions().size() > level) {
         System.out.println("options:" + this.mobilesession.getOptions().size());
         this.mobilesession.getOptions().set(level, Integer.valueOf(option));
       } else {
         for (int i = this.mobilesession.getOptions().size(); i < level + 1; i++)
         {
           this.mobilesession.getOptions().add(Integer.valueOf(0));
         }
         
         this.mobilesession.getOptions().set(level, Integer.valueOf(option));
       } 
     } 
   }
   public void setSessionLevelOption(int level, int option) {
     if (this.mobilesession.getOptions().size() > level) {
       this.mobilesession.getOptions().set(level, Integer.valueOf(option));
     } else {
       for (int i = this.mobilesession.getOptions().size(); i < level + 1; i++)
       {
         this.mobilesession.getOptions().add(Integer.valueOf(0));
       }
       this.mobilesession.getOptions().set(level, Integer.valueOf(option));
     } 
     saveSessionOptions();
   }
   
   public void setOptions(int option, int level) {
     if (this.mobilesession.getOptions().size() > level) {
       this.mobilesession.getOptions().set(level, Integer.valueOf(option));
     } else {
       this.mobilesession.getOptions().add(Integer.valueOf(option));
     } 
   }
   
   public void setQuickAccessOptions(ArrayList<String> options) {
     int level = 0;
     for (int i = 0; i < options.size(); i++) {
       LoanItem account; 
       ArrayList<Object> ssnaccountitems; String pin; level = i;
       switch (i) {
         case 0:
           try {
             int option = Integer.parseInt(options.get(i));
             
             setOptions(option, i);
           }
           catch (NumberFormatException e) {
             AppConfigHelper.logger.error("Exception:", e);
             int option = 1004;
             setOptions(option, i);
           } 
           break;
         case 1:
           try {
             int option = Integer.parseInt(options.get(i));
             
             setOptions(option, i);
           }
           catch (NumberFormatException e) {
             AppConfigHelper.logger.error("Exception:", e);
             int option = 1004;
             setOptions(option, i);
           } 
           break;
         case 2:
           pin = options.get(i);
           
           if (isValidCurrentPin(pin).booleanValue())
           {
             getSecurity().setIsLoggedIn(1);
           }
           break;
         case 3:
           ssnaccountitems = null;
           account = null;
           try {

             int option = Integer.parseInt(options.get(i));
             
             setOptions(option, i - 1);
           
           }
           catch (NumberFormatException e) {
             AppConfigHelper.logger.error("Exception:", e);
             int option = 1004;
             setOptions(option, i - 1);
           } 
           break;
         case 4:
           try {
             int option = Integer.parseInt(options.get(i));
             
             setOptions(option, i - 1);
           }
           catch (NumberFormatException e) {
             AppConfigHelper.logger.error("Exception:", e);
             int option = 1004;
             setOptions(option, i - 1);
           } 
           break;
       } 
     } 
     saveUSSDSession(level);
     saveAuthSession(-1);
   }
 
 
 
 
 
   public void saveAuthMode(int mode) {
     this.security.setIsAuthenticationMode(mode);
     saveSecurityInfo();
   }
   public void resetAuthSession() {
     getSecurity().setAuthSessionLevel(-1);
     saveSecurityInfo();
   }
   public void saveUSSDSession(int level) {
     if (level == -1) {
       this.sessionlevel = level;
       this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {sessionlevel:" + level + ",sessionmode:0}}");
     } else if (level > -1) {
       this.sessionlevel = level;
       this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {sessionlevel:" + level + ",sessionstatus:" + this.mobilesession.getSessionStatus() + "}}");
       this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {options: #}}", new Object[] { this.mobilesession.getOptions() });
     } 
     System.out.println("SessionLevel():" + this.sessionlevel);
   }
 
 
 
   
   public void saveBlockedSessionStatus(int status) {
     Date date = new Date();
     this.mobilesession.setSessionStatus(status);
     this.mobilesession.setDateBlocked(date);
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {dateblocked: #}}", new Object[] { date });
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {sessionstatus:" + status + "}}");
   }
   public void saveNotificationsUnsubscribe() {
     Date date = new Date();
     this.mobilesession.setSessionStatus(0);
     this.mobilesession.setUnSubscribed(date);
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {sessionstatus:0}}");
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {dateunsubscribed: #}}", new Object[] { date });
   }
   
   public void saveNotificationsSubscribe() {
     Date date = new Date();
     this.mobilesession.setSessionStatus(1);
     this.mobilesession.setSubscribed(date);
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {datesubscribed: #}}", new Object[] { date });
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {sessionstatus:1}}");
   }
 
   
   public void saveSessionOptions() { this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {options: #}}", new Object[] { this.mobilesession.getOptions() }); }
 
   
   public void saveUSSDSessionValue(String field, String value) { this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {" + field + ":" + value + "}}"); }
 
   
   public void saveSessionMode(int mode) {
     this.sessionmode = mode;
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {sessionmode:" + mode + "}}");
   }
   public void saveSelectedOption(SelectedOption option) {
     this.mobilesession.setSelectedOption(option);
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {selectedoption: #}}", new Object[] { option });
   }
   
   public void saveLastSeen() { this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {lastseen: #}}", new Object[] { new Date() }); }
 
   
   public void saveTermsAcceptance(TermsAcceptance v) { this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {terms_acceptance: #}}", new Object[] { v }); }
   
   public void saveSelectionSession(SelectionSession v) {
     this.mobilesession.setSelectionSession(v);
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {selectionsession: #}}", new Object[] { v });
   }
   public void saveFormSession(FormSession v) {
     this.mobilesession.setFormSession(v);
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {formsession: #}}", new Object[] { v });
   }
   public void saveAccount(Account v) {
     this.mobilesession.setAccount(v);
     this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {account: #}}", new Object[] { v });
   }
 
 
 
 
   
   public void saveAuthSession(int level) {
     this.security.setAuthSessionLevel(level);
     saveSecurityInfo();
   }
   public void saveIsPinSetupMode(int status) {
     this.security.setIsPinSetupMode(status);
     saveSecurityInfo();
   }
   
   public void saveIsPinResetMode(int status) {
     this.security.setIsPinResetMode(status);
     saveSecurityInfo();
   }
   public void saveSecurityInfo() { this.model.getCollection(AppConfigHelper.USSD_SESSIONS_COLLECTION).update("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}").with("{$set: {security: #}}", new Object[] { this.security }); }
 
 
   
   public Boolean isAuthorizedSubscriber() {
     return new SubscribersModel(this.ussdsessionhelper.getMongoDB())
             .exists("{ msisdn : '" + this.ussdsessionhelper.getMSISDN() + "'}");
   }
   
   public Boolean isValidCurrentPin(String pin) {
     if (!AppConfigHelper.securityHash(pin, "MD5").equals(this.mobilesession.getSecurity().getPin())) {
       return Boolean.valueOf(false);
     }
     return Boolean.valueOf(true);
   }
   
   public Boolean isValidRepeatPin() {
     String pin = getUserInput();
     if (!AppConfigHelper.securityHash(pin, "MD5").equals(this.mobilesession.getSecurity().getPin())) {
       return Boolean.valueOf(false);
     }
     return Boolean.valueOf(true);
   }
   
   public Boolean isValidPINSchema() {
     int len = getUserInput().length();
     AppConfigHelper.getInstance(); int minlen = AppConfigHelper.getMinPinLength();
     AppConfigHelper.getInstance(); int maxlen = AppConfigHelper.getMaxPinLength();
     return Boolean.valueOf((maxlen > minlen) ? ((len >= minlen && len <= maxlen)) : ((len > maxlen && len < minlen)));
   }
   
   public Boolean isExpiredUSSDSession() {
     if (AppConfigHelper.isOverTimeToLive(this.mobilesession.getLastSeen())) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
   
   public Boolean isExpiredTemporalBlock() {
     if (AppConfigHelper.isOverBlockedTime(this.mobilesession.getDateBlocked())) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
   
   public Boolean isSecurityThreat() {
     if (AppConfigHelper.isSecurityThreat(this.mobilesession.getDateBlocked()).booleanValue()) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
   
   public Boolean isBlocked() {
     if (this.mobilesession.getSessionStatus() == 3) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }

   public String getTemporalBlockTimeLeft(Date date) { return AppConfigHelper.getTemporalBlockTimeLeft(date); }
 
 
 
   
   public void clearOptions() { this.mobilesession.getOptions().clear(); }
   
   public void resetList() {
     setCurrentPage(0);
     setPerPage(0);
     setPriorPerPage(0);
     setOffSet(0);
     setIsLastPage(0);
   }
   public void resetSelectionOptions() {
     SelectionSession v = new SelectionSession();
     this.mobilesession.setSelectionSession(v);
     saveSelectionSession(v);
   }
   public Boolean isConfirmedAction(String value) {
     if (getUserInput().equalsIgnoreCase(value)) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
   
   public Boolean isValidInteger() {
     if (getUserInput().equalsIgnoreCase(AppConfigHelper.SKIP_CHARACTER)) {
       return Boolean.valueOf(true);
     }
     try {
       int number = Integer.parseInt(getUserInput());
       return Boolean.valueOf(true);
     } catch (NumberFormatException e) {
       
       return Boolean.valueOf(false);
     } 
   }
   
   public Boolean isValidName() {
     if (getUserInput().equalsIgnoreCase(AppConfigHelper.SKIP_CHARACTER)) {
       return Boolean.valueOf(true);
     }
     try {
       int name = Integer.parseInt(getUserInput());
       return Boolean.valueOf(false);
     } catch (NumberFormatException e) {
       return Boolean.valueOf(true);
     } 
   }
   
   public Boolean isValidText() {
     if (getUserInput().equalsIgnoreCase(AppConfigHelper.SKIP_CHARACTER)) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(true);
   }
   
   public Boolean isValidAlphaNumeric() {
     if (getUserInput().equalsIgnoreCase(AppConfigHelper.SKIP_CHARACTER)) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(true);
   }
   
   public Boolean isValidDate() {
     if (getUserInput().equalsIgnoreCase(AppConfigHelper.SKIP_CHARACTER)) {
       return Boolean.valueOf(true);
     }
     Date javadate = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
     try {
       sdf.setLenient(false);
       javadate = sdf.parse(getUserInput());
       return Boolean.valueOf(true);
     } catch (ParseException e) {
       
       return Boolean.valueOf(false);
     } 
   }
 
 
 
 
 
 
 
   
   public Boolean isValidEmail() {
     if (getUserInput().equalsIgnoreCase("99")) {
       return Boolean.valueOf(true);
     }
     this.pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
     this.matcher = this.pattern.matcher(getUserInput());
     return Boolean.valueOf(this.matcher.matches());
   }
   
   public Boolean isValidPhoneNo() {
     if (getUserInput().equalsIgnoreCase("99")) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(true);
   }
 }

