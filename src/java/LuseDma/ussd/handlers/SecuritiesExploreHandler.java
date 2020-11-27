package LuseDma.ussd.handlers;

import LuseDma.ussd.handlers.demo.AccountViewerHandler;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.views.SecurityExploreView;

import java.io.FileNotFoundException;

public class SecuritiesExploreHandler {

    private final int handlersessionlevel = 1;
    private USSDSession ussdsession = null;
    private String security;
    private SecurityExploreView view = new  SecurityExploreView();
    public SecuritiesExploreHandler(USSDSession ussdsession, String security) {
        this.ussdsession = ussdsession;
        this.security = security;
    }
    public USSDResponse runSession() throws FileNotFoundException {
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel:
                this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                this.ussdsession.saveSessionMode(0);
                return this.ussdsession.buildUSSDResponse(this.view.getExploreOptionsMenu(false, this.security), 2);
        }
        return runOptionSelectedHandler();
    }

    public USSDResponse runOptionSelectedHandler() throws FileNotFoundException {
        AccountViewerHandler accountViewerHandler;
        SecuritiesListHandler securitiesListHandler;
        switch (this.ussdsession.getSessionLevelOption(handlersessionlevel + 1)) {
            case 1:
                this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
                return this.ussdsession.buildUSSDResponse(this.view.getSearchMenu(false, this.security),2);
            case 2:
                securitiesListHandler = new SecuritiesListHandler(this.ussdsession, this.security);
                return securitiesListHandler.runSession();
        }
        return this.ussdsession.buildUSSDResponse(this.view.getExploreOptionsMenu(true, this.security), 2);
    }
}
