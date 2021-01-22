package LuseDma.ussd.handlers;

import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.views.AccountManagerView;

import java.io.FileNotFoundException;

public class ManageBrokersOPtionsHandler {

    private final int handlersessionlevel = 1;
    private USSDSession ussdsession = null;
    private AccountManagerView view = new  AccountManagerView();

    public ManageBrokersOPtionsHandler(USSDSession ussdsession){
        this.ussdsession = ussdsession;
    }

    public USSDResponse runSession() throws FileNotFoundException {
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel:
                this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                this.ussdsession.saveSessionMode(0);
                return this.ussdsession.buildUSSDResponse(this.view.manageBrokersOptionsMenu(false), 2);
        }
        return runOptionSelectedHandler();
    }

    public USSDResponse runOptionSelectedHandler() throws FileNotFoundException {
        ViewAccountBrokersHandler viewAccountBrokersHandler;
        AddBrokerHandler addBrokerHandler;
        ManageBrokersOPtionsHandler manageBrokersOPtionsHandler;
        switch (this.ussdsession.getSessionLevelOption(handlersessionlevel + 1)) {
            case 1:
                addBrokerHandler = new AddBrokerHandler(this.ussdsession);
                return addBrokerHandler.runSession();
            case 2:
                viewAccountBrokersHandler = new ViewAccountBrokersHandler(this.ussdsession);
                return viewAccountBrokersHandler.runSession();


        }
        return this.ussdsession.buildUSSDResponse(this.view.accountManagerOPtionsMenu(true), 2);
    }
}
