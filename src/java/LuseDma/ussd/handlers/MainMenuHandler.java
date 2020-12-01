package LuseDma.ussd.handlers;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.views.LuseMianViews;

import java.io.FileNotFoundException;

public class MainMenuHandler {
    private final int handlersessionlevel = -1;
    private USSDSession ussdsession = null;
    private MobileSession mobilesession = null;
    private LuseMianViews view = new LuseMianViews();

    public MainMenuHandler(USSDSession ussdsession) {
        this.ussdsession = ussdsession;
        this.mobilesession = ussdsession.getMobileSession();
        LuseServiceCenter.saveClient(this.ussdsession.getUSSDSessionHelper().getMongoDB(), this.mobilesession.getMSISDN(), AppConfigHelper.LUSE_CLIENT_ID);
    }

    public USSDResponse runSession() throws FileNotFoundException {
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel:
                this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                this.ussdsession.saveSessionMode(0);
                return this.ussdsession.buildUSSDResponse(this.view.getMainMenuView(this.ussdsession, false), 2);
        }
        return runOptionSelectedHandler();
    }

    public USSDResponse runOptionSelectedHandler() throws FileNotFoundException {
        RegistrationHandler registrationHandler;
        SecuritiesMenuHandler securitiesMenuHandler;
        FundMenuHandler fundMenuHandler;
        switch (this.ussdsession.getSessionLevelOption(handlersessionlevel + 1)) {
            case 1:
                securitiesMenuHandler = new SecuritiesMenuHandler(this.ussdsession, "1");
                return securitiesMenuHandler.runSession();
            case 2:
                securitiesMenuHandler = new SecuritiesMenuHandler(this.ussdsession, "2");
                return securitiesMenuHandler.runSession();
            case 3:
                fundMenuHandler = new FundMenuHandler(this.ussdsession);
                return fundMenuHandler.runSession();
        }
        return this.ussdsession.buildUSSDResponse(this.view.getMainMenuView(this.ussdsession, true), 2);
    }
}
