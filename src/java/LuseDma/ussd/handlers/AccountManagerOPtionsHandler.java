package LuseDma.ussd.handlers;

import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.views.AccountManagerView;
import LuseDma.ussd.views.SecuritiesView;

import java.io.FileNotFoundException;

public class AccountManagerOPtionsHandler {

    private final int handlersessionlevel = 0;
    private USSDSession ussdsession = null;
    private AccountManagerView view = new  AccountManagerView();


    public AccountManagerOPtionsHandler(USSDSession ussdsession){
        this.ussdsession = ussdsession;
    }

    public USSDResponse runSession() throws FileNotFoundException {
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel:
                this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                this.ussdsession.saveSessionMode(0);
                return this.ussdsession.buildUSSDResponse(this.view.accountManagerOPtionsMenu(false), 2);
        }
        return runOptionSelectedHandler();
    }

    public USSDResponse runOptionSelectedHandler() throws FileNotFoundException {
        ViewBalanceHandler viewBalanceHandler;
        ChangePasswordHandler changePasswordHandler;
        ManageBrokersOPtionsHandler manageBrokersOPtionsHandler;
        switch (this.ussdsession.getSessionLevelOption(handlersessionlevel + 1)) {
            case 1:
                viewBalanceHandler = new ViewBalanceHandler(this.ussdsession);
                return viewBalanceHandler.runSession();
            case 2:
                changePasswordHandler = new ChangePasswordHandler(this.ussdsession);
                return changePasswordHandler.runSession(false, false);
            case 3:
                manageBrokersOPtionsHandler = new ManageBrokersOPtionsHandler(this.ussdsession);
                return manageBrokersOPtionsHandler.runSession();


        }
        return this.ussdsession.buildUSSDResponse(this.view.accountManagerOPtionsMenu(true), 2);
    }

}
