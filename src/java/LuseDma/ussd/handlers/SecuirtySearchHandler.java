package LuseDma.ussd.handlers;

import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.pojos.luse.Security;
import LuseDma.ussd.views.SecurityExploreView;

import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Map;

public class SecuirtySearchHandler {

    private final int handlersessionlevel = 2;
    private final String formName;
    private final MobileSession mobilesession;
    private final String msisdn;
    private final FormSession formsession;
    private USSDSession ussdsession = null;
    private String security;
    private String buyOrSell;
    private Map<String, Object> searchForm;
    private SecurityExploreView view = new SecurityExploreView();

    public SecuirtySearchHandler(USSDSession ussdsession, String security, String buyOrSell){
        this.ussdsession = ussdsession;
        this.formName = "search_form";
        this.mobilesession = ussdsession.getMobileSession();
        this.msisdn = ussdsession.getMobileSession().getMSISDN();
        this.formsession = ussdsession.getMobileSession().getFormSession();
        this.security = security;
        this.buyOrSell = buyOrSell;
        this.searchForm = this.mobilesession.getFormSession().getForm(this.formName);
    }

    public USSDResponse runSession() throws FileNotFoundException {
        System.out.println("run method session level ==> " + this.ussdsession.getSessionLevel());
        SecuritiesListHandler securitiesListHandler;
        String searchValue = this.searchForm.getOrDefault("searchValue", "").toString();
        System.out.println("search value ==> " + searchValue);

        switch(this.ussdsession.getSessionLevel()){
            case handlersessionlevel:
                this.ussdsession.saveSessionMode(0);
                this.searchForm.put("searchValue", "");
                this.formsession.getFormData().put(this.formName, this.searchForm);
                this.ussdsession.saveFormSession(this.formsession);
                this.ussdsession.saveUSSDSession(handlersessionlevel+1);
                return this.ussdsession.buildUSSDResponse(this.view.getSearchMenu(this.security, false), 2);
            case handlersessionlevel+1:
                if(this.searchForm.getOrDefault("searchValue", "").toString().equals("")){
                    String userInput = this.ussdsession.getUserInput();
                    if(userInput.matches("[a-zA-z]+")){
                        searchValue = userInput.toUpperCase();
                        this.searchForm.put("searchValue", searchValue);
                        this.formsession.getFormData().put(this.formName, this.searchForm);
                        this.ussdsession.saveFormSession(this.formsession);
                    }
                    this.ussdsession.saveSessionMode(0);
                    return this.ussdsession.buildUSSDResponse(this.view.getSearchMenu(this.security, true), 2);
                }
                this.ussdsession.saveSessionMode(0);
                this.ussdsession.saveUSSDSession(handlersessionlevel);
                securitiesListHandler = new SecuritiesListHandler(this.ussdsession, this.security, searchValue, this.buyOrSell);
                return securitiesListHandler.runSession();

        }
        this.ussdsession.saveSessionMode(0);
        this.ussdsession.saveUSSDSession(handlersessionlevel);
        securitiesListHandler = new SecuritiesListHandler(this.ussdsession, this.security, searchValue, this.buyOrSell);
        return securitiesListHandler.runSession();
    }
}
