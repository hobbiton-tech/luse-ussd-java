package LuseDma.ussd.handlers;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.models.luse.ClientModel;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.pojos.luse.Client;
import LuseDma.ussd.views.LuseMianViews;

import java.io.FileNotFoundException;

public class MainMenuHandler {
    private final int handlersessionlevel = -1;
    private USSDSession ussdsession = null;
    private MobileSession mobilesession = null;
    private ClientModel clientModel;
    private LuseMianViews view = new LuseMianViews();

    public MainMenuHandler(USSDSession ussdsession) {
        this.ussdsession = ussdsession;
        this.clientModel = new ClientModel(ussdsession.getUSSDSessionHelper().getMongoDB());
        this.mobilesession = ussdsession.getMobileSession();
    }

    public USSDResponse runSession() throws FileNotFoundException {

        if (this.clientModel.findOne(this.ussdsession.getMSISDN()) != null) {
            Client client = this.clientModel.findOne(this.ussdsession.getMSISDN());
            AppConfigHelper.setClientId(client.getId());
            AppConfigHelper.setAccessToken(client.getAccessToken());
            AppConfigHelper.setClientCsdId(client.getCsdId());
        }

        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel:
                this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                this.ussdsession.saveSessionMode(0);
                if (AppConfigHelper.LUSE_CLIENT_ID == null && AppConfigHelper.LUSEDMA_ACCESS_TOKEN == null) {
                    return this.ussdsession.buildUSSDResponse(this.view.LoginMenuOPtions(false), 2);
                } else {
                    this.ussdsession.setSessionLevelOption(handlersessionlevel + 1, 0);
                    return this.ussdsession.buildUSSDResponse(this.view.getMainMenuView(this.ussdsession, false), 2);
                }
            default:
                return runOptionSelectedHandler();
        }


    }

    public USSDResponse runOptionSelectedHandler() throws FileNotFoundException {
        AuthHandler authHandler;
        SecuritiesMenuHandler securitiesMenuHandler;
        FundMenuHandler fundMenuHandler;
        PortfolioHandler portfolioHandler;
        AccountManagerOPtionsHandler accountManagerOPtionsHandler;
        System.out.println("client id => " + AppConfigHelper.LUSE_CLIENT_ID + " Access token ==> " + AppConfigHelper.LUSEDMA_ACCESS_TOKEN);
        System.out.println("session level option ==> " + this.ussdsession.getSessionLevelOption(handlersessionlevel + 1));
        if (AppConfigHelper.LUSE_CLIENT_ID == null && AppConfigHelper.LUSEDMA_ACCESS_TOKEN == null) {
            switch (this.ussdsession.getSessionLevelOption(handlersessionlevel + 1)) {
                case 1:
                    authHandler = new AuthHandler(this.ussdsession, this.mobilesession);
                    return authHandler.runSession();
            }
            return this.ussdsession.buildUSSDResponse(this.view.LoginMenuOPtions(true), 2);
        } else {
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
                case 4:
                    portfolioHandler = new PortfolioHandler(this.ussdsession);
                    return portfolioHandler.runSession();
                case 5:
                    accountManagerOPtionsHandler = new AccountManagerOPtionsHandler(this.ussdsession);
                    return accountManagerOPtionsHandler.runSession();

            }
            return this.ussdsession.buildUSSDResponse(this.view.getMainMenuView(this.ussdsession, true), 2);
        }
    }
}
