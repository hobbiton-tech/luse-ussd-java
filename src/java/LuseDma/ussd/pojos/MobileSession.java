 package LuseDma.ussd.pojos;
 
import LuseDma.ussd.pojos.demo.TermsAcceptance;
import LuseDma.ussd.pojos.kyc.Name;
import LuseDma.ussd.pojos.kyc.Gender;
 import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.util.Map;
 import org.bson.types.ObjectId;
 
 @JsonIgnoreProperties(ignoreUnknown = true)
 public class MobileSession
 {
   private ObjectId id;
   private String sessionid = null;
   private String msisdn = null; 
   private Boolean is_new_subscriber;
   private MSISDNProvider msisdnprovider = new MSISDNProvider();
   private Name name = new Name();
   private Gender gender = Gender.UNKNOWN;
   private Account account = new Account();
   private Security security = new Security();
   private ArrayList<Integer> options = new ArrayList<>();
   private int offset = 0;
   private int priorperpage = 0;
   private int perpage = 0;
   private int currentpage = 0;
   private int islastpage = 0;
   private int sessionlevel = -1;
   private int sessionmode = 0;
   private TermsAcceptance terms_acceptance = new TermsAcceptance();
   private SelectedOption selectedoption = new SelectedOption();
   private SelectionSession selectionsession = new SelectionSession();
   private FormSession formsession = new FormSession();
   private List<Map<String, Object>> employees = new ArrayList<>();
   private String ussdmessage = null;
   private int sessionstatus = 1; 
   private Date dateblocked;
   private Date lastseen = new Date(); 
   private Date dateunsubscribed;
   private Date datesubscribed = new Date();
   public MobileSession() {}
   public MobileSession(String sessionid, String msisdn, MSISDNProvider msisdnprovider, int sessionlevel, int sessionmode, String ussdmessage) {
     this();
     this.sessionid = sessionid;
     this.msisdn = msisdn;
     this.msisdnprovider = msisdnprovider;
     this.sessionlevel = sessionlevel;
     this.sessionmode = sessionmode;
     this.ussdmessage = ussdmessage;
   }
   public MobileSession(String sessionid, String msisdn, MSISDNProvider msisdnprovider, int sessionlevel, int sessionmode, Boolean is_new_subscriber) {
     this();
     this.sessionid = sessionid;
     this.msisdn = msisdn;
     this.msisdnprovider = msisdnprovider;
     this.sessionlevel = sessionlevel;
     this.sessionmode = sessionmode;
     this.is_new_subscriber = is_new_subscriber;
   }
   
   public ObjectId getId() { return this.id; }
   public String getSessionID() { return this.sessionid; }
   public String getMSISDN() { return this.msisdn; }
   public Boolean getIsNewSubscriber() { return this.is_new_subscriber; }
   public MSISDNProvider getMSISDNProvider() { return this.msisdnprovider; }
   public Name getName() { return this.name; }
   public Gender getGender() { return this.gender; }
   public Account getAccount() { return this.account; }
   public Security getSecurity() { return this.security; }
   public ArrayList<Integer> getOptions() { return this.options; }
   public int getOffSet() { return this.offset; }
   public int getPiorPerPage() { return this.priorperpage; }
   public int getPerPage() { return this.perpage; }
   public int getCurrentPage() { return this.currentpage; }
   public int getIsLastPage() { return this.islastpage; }
   public int getSessionLevel() { return this.sessionlevel; }
   public int getSessionMode() { return this.sessionmode; }
   public int getSessionStatus() { return this.sessionstatus; }
   public TermsAcceptance getTermsAcceptance() { return this.terms_acceptance; }
   public SelectedOption getSelectedOption() { return this.selectedoption; }
   public SelectionSession getSelectionSession() { return this.selectionsession; }
   public FormSession getFormSession() { return this.formsession; }
   public List<Map<String, Object>> getEmployees() { return this.employees; }
   public String getUSSDMessage() { return this.ussdmessage; }
   public Date getDateBlocked() { return this.dateblocked; }
   public Date getLastSeen() { return this.lastseen; }
   public Date getSubscribed() { return this.datesubscribed; }
   public Date getUnSubscribed() { return this.dateunsubscribed; }
   
   public void setId(ObjectId v) { this.id = v; }
   public void setSessionID(String v) { this.sessionid = v; }
   public void setMSISDN(String v) { this.msisdn = v; }
   public void setMSISDNProvider(MSISDNProvider v) { this.msisdnprovider = v; }
   public void setName(Name v) { this.name = v; }
   public void setGender(Gender v) { this.gender = v; }
   public void setAccount(Account v) { this.account = v; }
   public void setSecurity(Security v) { this.security = v; }
   public void setOptions(ArrayList<Integer> v) { this.options = v; }
   public void setOffSet(int v) { this.offset = v; }
   public void setPiorPerPage(int v) { this.priorperpage = v; }
   public void setPerPage(int v) { this.perpage = v; }
   public void setCurrentPage(int v) { this.currentpage = v; }
   public void setIsLastPage(int v) { this.islastpage = v; }
   public void setSessionLevel(int v) { this.sessionlevel = v; }
   public void setSessionMode(int v) { this.sessionmode = v; }
   public void setSessionStatus(int v) { this.sessionstatus = v; }
   public void setTermsAcceptance(TermsAcceptance v) { this.terms_acceptance = v; }
   public void setSelectedOption(SelectedOption v) { this.selectedoption = v; }
   public void setSelectionSession(SelectionSession v) { this.selectionsession = v; }
   public void setFormSession(FormSession v) { this.formsession = v; }
   public void setEmployees(List<Map<String, Object>> v) { this.employees = v; }
   public void setUSSDMessage(String v) { this.ussdmessage = v; }
   public void setDateBlocked(Date v) { this.dateblocked = v; }
   public void setLastSeen(Date v) { this.lastseen = v; }
   public void setSubscribed(Date v) { this.datesubscribed = v; }
   public void setUnSubscribed(Date v) { this.dateunsubscribed = v; }
 }

