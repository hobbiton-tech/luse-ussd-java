package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.pojos.FormSession;


public class LoginView
{
    private String newLine = String.format("%n", new Object[0]);

    public  static final String FORM_NAME = "login_form";

    public String getLoginView(FormSession session, String action, boolean err) {
        String view = "";
        switch (action) {
            case "email":
                view = view + "Enter email." + this.newLine;
                AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
                return view;
            case "password":
                view = view + "Enter password." + this.newLine;
                AppConfigHelper.getInstance(); view = view + AppConfigHelper.getGoBackText();
                return view;
        }
        view = view + "Login Failed. Bad credentials, please try again later" + this.newLine;
        view = view + "Press;" + this.newLine;
        view = view + "1. To try this again" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }



}


