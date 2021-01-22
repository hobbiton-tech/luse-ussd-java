package LuseDma.ussd.handlers;


import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.models.luse.ClientModel;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.pojos.luse.Client;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;

public class AuthHandler {
    private final int handlersessionlevel = 0;
    private USSDSession ussdsession = null;
    private MobileSession mobilesession = null;
    private String responsemessage = "";
    private MainMenuHandler MainMenuHandler = null;
    private String newLine = String.format("%n", new Object[0]);

    public AuthHandler(USSDSession ussdsession, MobileSession mobileSession) {
        this.ussdsession = ussdsession;
        this.mobilesession = mobileSession;

    }

    public USSDResponse runSession() throws FileNotFoundException {
        System.out.println("auth handler session level ==> " + this.ussdsession.getSecurity().getAuthSessionLevel());
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel:
                this.ussdsession.saveUSSDSession(handlersessionlevel +1);
                this.ussdsession.saveSessionMode(1);
                this.responsemessage = "Enter your password:" + this.newLine;
                return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
            case handlersessionlevel + 1:
                String password = this.ussdsession.getUserInput();
                System.out.println("tyring to login user");
                if (LuseServiceCenter.loginClient(this.ussdsession.getUSSDSessionHelper().getMongoDB(), this.mobilesession.getMSISDN(), password)) {

                    MainMenuHandler mainMenuHandler;
                    this.ussdsession.saveUSSDSession(-1);
                    mainMenuHandler = new MainMenuHandler(this.ussdsession);
                    return mainMenuHandler.runSession();
                }
                this.responsemessage += "Login failed. Bad credentials" + this.newLine;
                this.responsemessage += "Enter your password:" + this.newLine;
                return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
        }
        this.responsemessage += "Login failed. Bad credentials" + this.newLine;
        this.responsemessage += "Enter your password:" + this.newLine;
        return this.ussdsession.buildUSSDResponse(this.responsemessage, 2);
    }


}
