package LuseDma.ussd.handlers;

import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.views.SecuritiesView;
import LuseDma.ussd.helpers.USSDSession;

import java.io.FileNotFoundException;


public class SecuritiesMenuHandler {

    private final int handlersessionlevel = 0;
    private USSDSession ussdsession = null;
    private String buyOrSell;
    private SecuritiesView view = new  SecuritiesView();

    public SecuritiesMenuHandler(USSDSession ussdsession, String buyOrSell) {
        this.ussdsession = ussdsession;
        this.buyOrSell = buyOrSell;
    }
    public USSDResponse runSession() throws FileNotFoundException {
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel:
                this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                this.ussdsession.saveSessionMode(0);
                return this.ussdsession.buildUSSDResponse(this.view.getSecuritiesMenu(false), 2);
        }
        return runOptionSelectedHandler();
    }

    public USSDResponse runOptionSelectedHandler() throws FileNotFoundException {
        SecuritiesExploreHandler securitiesExploreHandler;
        switch (this.ussdsession.getSessionLevelOption(handlersessionlevel + 1)) {
            case 1:
                securitiesExploreHandler = new SecuritiesExploreHandler(this.ussdsession, "CS", this.buyOrSell);
                return securitiesExploreHandler.runSession();
            case 2:
                securitiesExploreHandler = new SecuritiesExploreHandler(this.ussdsession, "CORP", this.buyOrSell);
                return securitiesExploreHandler.runSession();
            case 3:
                securitiesExploreHandler = new SecuritiesExploreHandler(this.ussdsession, "CASH", this.buyOrSell);
                return securitiesExploreHandler.runSession();

        }
        return this.ussdsession.buildUSSDResponse(this.view.getSecuritiesMenu(true), 2);
    }
}
