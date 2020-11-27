 package LuseDma.ussd.handlers;
 
import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.common.BrivoHelper;
import LuseDma.ussd.handlers.demo.DataServiceHandler;
import LuseDma.ussd.helpers.PaginationHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.models.MembersModel;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.demo.MaritalStatus;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.demo.NameTitle;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.pojos.kyc.Gender;
import LuseDma.ussd.pojos.kyc.Member;
import LuseDma.ussd.views.RegistrationViews;
import static LuseDma.ussd.views.RegistrationViews.FORM_NAME;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;

 public class RegistrationHandler
 {
   private final int handlersessionlevel = 0;
   private USSDSession ussdsession = null;
   private MobileSession mobilesession = null;
   private FormSession formsession = null;
   private MembersModel membersModel = null;
   private ArrayList<ListItem> listitems = null;
   private String header = null;
   private Map<String, Object> form;
   private RegistrationViews view = new RegistrationViews();
   public RegistrationHandler(USSDSession ussdsession) {
     this.ussdsession = ussdsession;
     this.mobilesession = ussdsession.getMobileSession();
     this.formsession = ussdsession.getMobileSession().getFormSession();
     this.form = this.mobilesession.getFormSession().getForm(FORM_NAME);
     this.membersModel = new MembersModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
   }
   public USSDResponse runSession() throws FileNotFoundException {
     String input = "";
     switch (this.ussdsession.getSessionLevel()) {
       case handlersessionlevel:
         this.formsession.getFormData().put(FORM_NAME, this.form);
         this.ussdsession.saveFormSession(this.formsession);
         
         this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
         this.ussdsession.saveSessionMode(1);
         return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "title", false), 2);
       case handlersessionlevel + 1:
         input = this.ussdsession.getUserInput();
         if (validateTitle(this.ussdsession).booleanValue()) {
           this.ussdsession.saveFormSession(this.formsession);
           
           this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "firstname", false), 2);
         } 
         this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
         this.ussdsession.saveSessionMode(1);
         return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "title", true), 2);
       case handlersessionlevel + 2:
         input = this.ussdsession.getUserInput();
         if (this.ussdsession.isValidName().booleanValue()) {
           this.form.put("firstname", input);
           this.formsession.getFormData().put(FORM_NAME, this.form);
           this.ussdsession.saveFormSession(this.formsession);
           
           this.ussdsession.saveUSSDSession(handlersessionlevel + 3);
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "middlename", false), 2);
         } 
         this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
         this.ussdsession.saveSessionMode(1);
         this.ussdsession.saveFormSession(this.formsession);
         return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "firstname", true), 2);
       
       case handlersessionlevel + 3:
        input = this.ussdsession.getUserInput();
        this.form.put("middlename", input);
        this.formsession.getFormData().put(FORM_NAME, this.form);
        this.ussdsession.saveFormSession(this.formsession);

        this.ussdsession.saveUSSDSession(handlersessionlevel + 4);
        this.ussdsession.saveSessionMode(1);
        return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "lastname", false), 2);
       case handlersessionlevel + 4:
         input = this.ussdsession.getUserInput();
         if (this.ussdsession.isValidName().booleanValue()) {
           this.form.put("lastname", input);
           this.formsession.getFormData().put(FORM_NAME, this.form);
           this.ussdsession.saveFormSession(this.formsession);
           
           this.ussdsession.saveUSSDSession(handlersessionlevel + 5);
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "idnumber", false), 2);
         } 
         this.ussdsession.saveUSSDSession(handlersessionlevel + 4);
         this.ussdsession.saveSessionMode(1);
         this.ussdsession.saveFormSession(this.formsession);
         return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "lastname", true), 2);
       
       case handlersessionlevel + 5:
         input = this.ussdsession.getUserInput();
         if (validateIdNumber(input)){
         //if (this.ussdsession.isValidAlphaNumeric().booleanValue()) {
           this.form.put("idnumber", input);
           this.formsession.getFormData().put(FORM_NAME, this.form);
           this.ussdsession.saveFormSession(this.formsession);
           
           this.ussdsession.saveUSSDSession(handlersessionlevel + 6);
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "gender", false), 2);
         } 
         this.ussdsession.saveUSSDSession(handlersessionlevel + 5);
         this.ussdsession.saveSessionMode(1);
         this.ussdsession.saveFormSession(this.formsession);
         return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "idnumber", true), 2);
       case handlersessionlevel + 6:
         input = this.ussdsession.getUserInput();
         if (validateGender(this.ussdsession).booleanValue()) {
           this.ussdsession.saveFormSession(this.formsession);
           
           this.ussdsession.saveUSSDSession(handlersessionlevel + 7);
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "address", false), 2);
         } 
         this.ussdsession.saveUSSDSession(handlersessionlevel + 6);
         this.ussdsession.saveSessionMode(1);
         this.ussdsession.saveFormSession(this.formsession);
         return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "gender", true), 2);
      case handlersessionlevel + 7:
         input = this.ussdsession.getUserInput();
         if (this.ussdsession.isValidText().booleanValue()) {
            this.form.put("address", input);
            this.formsession.getFormData().put(FORM_NAME, this.form);
            this.ussdsession.saveFormSession(this.formsession);
          
            this.ussdsession.saveUSSDSession(handlersessionlevel + 8);
            this.ussdsession.saveSessionMode(1);
            return runProvinceSelection();
         } 
         this.ussdsession.saveUSSDSession(handlersessionlevel + 7);
         this.ussdsession.saveSessionMode(1);
         this.ussdsession.saveFormSession(this.formsession);
         return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "address", true), 2);
     } 
     return runProvinceSelection();
   }
   public USSDResponse runProvinceSelection() throws FileNotFoundException {
     if (this.ussdsession.getSessionLevel() < handlersessionlevel + 10) {
       JSONObject selected; this.header = this.view.getRegistrationView(this.formsession, "province", false);
       JSONArray provinces = DataServiceHandler.getProvinces();
       
       this.listitems = getListItems(provinces);
       
       PaginationHelper paginationHelper = null;
       switch (this.ussdsession.getSessionLevel()) {
         case handlersessionlevel + 8:
           this.ussdsession.resetList();
           this.ussdsession.saveUSSDSession(handlersessionlevel + 9);
           this.ussdsession.saveSessionMode(1);
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, Boolean.valueOf(false), Boolean.valueOf(false), "Sorry no provinces found.");
           return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
         case handlersessionlevel + 9:
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no provinces found.");
           switch (paginationHelper.getListPageActionHandle()) {
             case 1:
               selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, provinces);
               this.form.put("provinceid", selected.optString("id"));
               this.form.put("province", selected.optString("name"));
               this.formsession.getFormData().put(FORM_NAME, this.form);
               this.ussdsession.saveFormSession(this.formsession);
               
               this.ussdsession.resetList();
               this.ussdsession.saveUSSDSession(handlersessionlevel + 10);
               return runSectorSelection();
           } 
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
       } 
       return runSectorSelection();
     } 
     return runSectorSelection();
   }
 
     public USSDResponse runSectorSelection() throws FileNotFoundException {
     if (this.ussdsession.getSessionLevel() < handlersessionlevel + 12) {
       JSONObject selected; this.header = this.view.getRegistrationView(this.formsession, "sector", false);
       JSONArray sectors = DataServiceHandler.getSectors();
       this.listitems = getListItems(sectors);
       PaginationHelper paginationHelper = null;
       switch (this.ussdsession.getSessionLevel()) {
         case handlersessionlevel + 10:
           this.ussdsession.resetList();
           this.ussdsession.saveUSSDSession(handlersessionlevel + 11);
           this.ussdsession.saveSessionMode(1);
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, Boolean.valueOf(false), Boolean.valueOf(false), "Sorry no provinces found.");
           return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
         case handlersessionlevel + 11:
           paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, Boolean.valueOf(false), Boolean.valueOf(false), "Sorry no provinces found.");
           switch (paginationHelper.getListPageActionHandle()) {
             case 1:
               selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, sectors);
               this.form.put("sector", selected.optString("name"));
               this.formsession.getFormData().put(FORM_NAME, this.form);
               this.ussdsession.saveFormSession(this.formsession);
               
               this.ussdsession.resetList();
               this.ussdsession.saveUSSDSession(handlersessionlevel + 12);
               return runRegistrationSession();
           } 
           this.ussdsession.saveSessionMode(1);
           return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
       } 
       return runRegistrationSession();
     } 
     return runRegistrationSession();
   }
 
   public USSDResponse runRegistrationSession() throws FileNotFoundException {
     String input;
     switch (this.ussdsession.getSessionLevel()) {
       case handlersessionlevel + 12:
            this.form.put("ssn", BrivoHelper.uniqueCurrentTime().toString());
            Member member = new Member();
            member.setMsisdn(this.ussdsession.getMSISDN());
            member.setSSN((String)this.form.getOrDefault("ssn", ""));
            member.setSector((String)this.form.getOrDefault("sector", ""));
            member.setTitle((String)this.form.getOrDefault("title", ""));
            member.setFirstname((String)this.form.getOrDefault("firstname", ""));
            member.setMiddlename((String)this.form.getOrDefault("middlename", ""));
            member.setLastname((String)this.form.getOrDefault("lastname", ""));
            member.setIdnumber((String)this.form.getOrDefault("idnumber", ""));
            member.setGender((String)this.form.getOrDefault("gender", ""));
            member.setProvince((String)this.form.getOrDefault("province", ""));
            member.setAddress((String)this.form.getOrDefault("address", ""));
            member.setTitle((String)this.form.getOrDefault("title", ""));
            this.membersModel.save(member);
            this.ussdsession.saveUSSDSession(-1);
            this.ussdsession.saveSessionMode(1);
            return this.ussdsession.buildUSSDResponse(this.view.getRegistrationView(this.formsession, "transaction_success", true), 2);
     } 
     
     this.ussdsession.saveUSSDSession(-1);
     MainMenuHandler mainMenuHandler = new MainMenuHandler(this.ussdsession);
     return mainMenuHandler.runSession();
   }
 
   
   public JSONObject getSelectedItem(int id, JSONArray items) {
     JSONObject o = null;
     try {
       o = items.getJSONObject(id);
     } catch (JSONException e) {
       e.printStackTrace();
     } 
     return o;
   }
   public ArrayList<ListItem> getListItems(JSONArray items) {
     ArrayList<ListItem> list = new ArrayList<>();
     try {
       for (int i = 0; i < items.length(); i++) {
         JSONObject o = items.getJSONObject(i);
         String id = o.optString("id", "");
         String name = o.optString("name", "");
         ListItem listitem = new ListItem(id, name);
         list.add(listitem);
       } 
     } catch (Exception exception) {}
     return list;
   }
   public Boolean validateGender(USSDSession v) {
     try {
       int choice = Integer.parseInt(v.getUserInput());
       switch (choice) {
         case 1:
           this.form.put("gender", Gender.Male);
           return Boolean.valueOf(true);
         case 2:
           this.form.put("gender", Gender.Female);
           return Boolean.valueOf(true);
       } 
       return Boolean.valueOf(false);
     }
     catch (Exception e) {
       return Boolean.valueOf(false);
     } 
   }
   public Boolean validateNationality(USSDSession v) {
     try {
       int choice = Integer.parseInt(v.getUserInput());
       switch (choice) {
         case 1:
           this.form.put("nationality", "Zambian");
           return Boolean.valueOf(true);
         case 2:
           this.form.put("nationality", "Foreign");
           return Boolean.valueOf(true);
       } 
       return Boolean.valueOf(false);
     }
     catch (Exception e) {
       return Boolean.valueOf(false);
     } 
   }
   public Boolean validateMaritalStatus(USSDSession v) {
     try {
       int choice = Integer.parseInt(v.getUserInput());
       switch (choice) {
         case 1:
           this.form.put("marital_status", MaritalStatus.Married);
           return Boolean.valueOf(true);
         case 2:
           this.form.put("marital_status", MaritalStatus.Widowed);
           return Boolean.valueOf(true);
         case 3:
           this.form.put("marital_status", MaritalStatus.Separated);
           return Boolean.valueOf(true);
         case 4:
           this.form.put("marital_status", MaritalStatus.Divorced);
           return Boolean.valueOf(true);
         case 5:
           this.form.put("marital_status", MaritalStatus.Single);
           return Boolean.valueOf(true);
       } 
       return Boolean.valueOf(false);
     }
     catch (Exception e) {
       return Boolean.valueOf(false);
     } 
   }
   public Boolean validateTitle(USSDSession v) {
     if (v.getUserInput().equalsIgnoreCase(AppConfigHelper.SKIP_CHARACTER)) {
       this.form.put("title", AppConfigHelper.SKIP_CHARACTER);
       return Boolean.valueOf(true);
     } 
     try {
       int choice = Integer.parseInt(v.getUserInput());
       switch (choice) {
         case 1:
           this.form.put("title", NameTitle.Mr);
           return Boolean.valueOf(true);
         case 2:
           this.form.put("title", NameTitle.Mrs);
           return Boolean.valueOf(true);
         case 3:
           this.form.put("title", NameTitle.Miss);
           return Boolean.valueOf(true);
         case 4:
           this.form.put("title", NameTitle.Ms);
           return Boolean.valueOf(true);
         case 5:
           this.form.put("title", NameTitle.Dr);
           return Boolean.valueOf(true);
       } 
       return Boolean.valueOf(false);
     }
     catch (Exception e) {
       return Boolean.valueOf(false);
     } 
   }
   public Boolean validateIdNumber(String v) {
       return !this.membersModel.exists(v);
    }
 }

