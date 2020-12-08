package LuseDma.ussd.handlers;

import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.views.SecurityExploreView;

import java.io.FileNotFoundException;

public class SecuritiesExploreHandler {

    private final int handlersessionlevel = 1;
    private USSDSession ussdsession = null;
    private String security;
    private String buyOrSell;
    private SecurityExploreView view = new SecurityExploreView();

    public SecuritiesExploreHandler(USSDSession ussdsession, String security, String buyOrSell) {
        this.ussdsession = ussdsession;
        this.security = security;
        this.buyOrSell = buyOrSell;
    }

    public USSDResponse runSession() throws FileNotFoundException {
        System.out.println("security explore runSession handlersessionlevel ==> "+this.ussdsession.getSessionLevel());
        System.out.println("security explore run session level ==> "+this.ussdsession.getSessionLevelOption(handlersessionlevel + 1));
        if(this.ussdsession.getSessionLevelOption(handlersessionlevel + 1) != 3){
            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel:
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                    this.ussdsession.saveSessionMode(0);
                    return this.ussdsession.buildUSSDResponse(this.view.getExploreOptionsMenu(false, this.security), 2);
            }
            return runOptionSelectedHandler();
        }
        return runOptionSelectedHandler();
    }

    public USSDResponse runOptionSelectedHandler() throws FileNotFoundException {
        SecuritiesListHandler securitiesListHandler;

        System.out.println("session Level option ==> " + this.ussdsession.getSessionLevelOption(handlersessionlevel + 1));
        switch (this.ussdsession.getSessionLevelOption(handlersessionlevel + 1)) {
            case 1:
                System.out.println("case 1 Level option selected==> " + this.ussdsession.getSessionLevelOption(handlersessionlevel + 1));
                this.ussdsession.setSessionLevelOption(handlersessionlevel + 1, 3);
                this.ussdsession.saveSessionOptions();
                return this.ussdsession.buildUSSDResponse(this.view.getSearchMenu(false, this.security), 2);
            case 2:
                securitiesListHandler = new SecuritiesListHandler(this.ussdsession, this.security, "", this.buyOrSell);
                return securitiesListHandler.runSession();
            case 3:
                System.out.println("case 3 Level option selected==> " + this.ussdsession.getSessionLevelOption(handlersessionlevel + 1));
                if (this.ussdsession.getUserInput().matches("[a-zA-z]+")) {
                    System.out.println("search value entered ==> " + this.ussdsession.getUserInput());
                    securitiesListHandler = new SecuritiesListHandler(this.ussdsession, this.security, String.valueOf(this.ussdsession.getUserInput()).toUpperCase(), this.buyOrSell);
                    return securitiesListHandler.runSession();
                }
                this.ussdsession.setSessionLevelOption(handlersessionlevel + 1, 1);
                this.ussdsession.saveSessionOptions();
                return this.ussdsession.buildUSSDResponse(this.view.getSearchMenu(true, this.security), 2);
        }

        return this.ussdsession.buildUSSDResponse(this.view.getExploreOptionsMenu(true, this.security), 2);
    }
}
