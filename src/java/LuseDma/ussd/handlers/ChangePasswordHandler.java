package LuseDma.ussd.handlers;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.views.AccountManagerView;
import LuseDma.ussd.views.FundMenuView;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import static LuseDma.ussd.views.SecurityExploreView.FORM_NAME;


public class ChangePasswordHandler {

    private final int handlersessionlevel = 1;
    private USSDSession ussdsession = null;
    private MobileSession mobilesession = null;
    private FormSession formsession = null;
    private Map<String, Object> changePasswordForm;
    private String msisdn;
    private String header = null;
    private ArrayList<ListItem> listitems = null;
    private JSONObject selected;
    private final String FORM_NAME = "change-password-form";

    private AccountManagerView view = new AccountManagerView();

    public ChangePasswordHandler(USSDSession ussdsession) {
        this.ussdsession = ussdsession;
        this.mobilesession = ussdsession.getMobileSession();
        this.msisdn = ussdsession.getMobileSession().getMSISDN();
        this.formsession = ussdsession.getMobileSession().getFormSession();
        this.changePasswordForm = this.mobilesession.getFormSession().getForm(FORM_NAME);
    }

    public USSDResponse runSession(Boolean isPasswordInvalid, Boolean isPasswordMismatch) throws FileNotFoundException {
        System.out.println("runSession session level ==> " + this.ussdsession.getSessionLevel());
        if (this.ussdsession.getSessionLevel() < handlersessionlevel + 2) {
            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel:
                    this.ussdsession.saveSessionMode(1);
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                    return this.ussdsession.buildUSSDResponse(this.view.changePasswordInput(isPasswordInvalid, "1", isPasswordMismatch), 2);
                case handlersessionlevel + 1:
                    this.changePasswordForm.put("oldPassword", this.ussdsession.getUserInput());
                    this.formsession.getFormData().put(this.FORM_NAME, this.changePasswordForm);
                    this.ussdsession.saveFormSession(this.formsession);

                    this.ussdsession.saveSessionMode(1);
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
                    return this.newPasswordInput();
            }
        }
        return this.newPasswordInput();
    }

    public USSDResponse newPasswordInput() throws FileNotFoundException {
        if (this.ussdsession.getSessionLevel() < handlersessionlevel + 4) {
            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel + 2:
                    this.ussdsession.saveSessionMode(1);
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 3);
                    return this.ussdsession.buildUSSDResponse(this.view.changePasswordInput(false, "2", false), 2);
                case handlersessionlevel + 3:
                    this.changePasswordForm.put("newPassword", this.ussdsession.getUserInput());
                    this.formsession.getFormData().put(this.FORM_NAME, this.changePasswordForm);
                    this.ussdsession.saveFormSession(this.formsession);

                    this.ussdsession.saveSessionMode(1);
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 4);
                    return this.confirmPasswordInput();
            }
        }
        return this.confirmPasswordInput();
    }

    public USSDResponse confirmPasswordInput() throws FileNotFoundException {
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel + 4:
                this.ussdsession.saveSessionMode(1);
                this.ussdsession.saveUSSDSession(handlersessionlevel + 5);
                return this.ussdsession.buildUSSDResponse(this.view.changePasswordInput(false, "3", false), 2);
            case handlersessionlevel + 5:
                this.changePasswordForm.put("confirmPassword", this.ussdsession.getUserInput());
                this.formsession.getFormData().put(this.FORM_NAME, this.changePasswordForm);
                this.ussdsession.saveFormSession(this.formsession);

                String oldPassword = String.valueOf(this.changePasswordForm.getOrDefault("oldPassword", ""));
                String newPassword = String.valueOf(this.changePasswordForm.getOrDefault("newPassword", ""));
                String passwordConfirm = String.valueOf(this.changePasswordForm.getOrDefault("confirmPassword", ""));

                if (newPassword.equals(passwordConfirm)) {
                    if (LuseServiceCenter.changePassword(oldPassword, newPassword, this.ussdsession.getMSISDN(), this.ussdsession.getUSSDSessionHelper().getMongoDB())) {
                        this.ussdsession.saveUSSDSession(-1);
                        this.formsession.clearFormData(FORM_NAME);
                        LuseServiceCenter.deleteUserStoredInfo(this.ussdsession.getMSISDN(), this.ussdsession.getUSSDSessionHelper().getMongoDB());
                        return this.ussdsession.buildUSSDResponse(this.view.changePasswordResponse(), 2);
                    }
                    this.ussdsession.saveSessionMode(1);
                    this.ussdsession.saveUSSDSession(handlersessionlevel);
                    return this.runSession(true, false);
                }

                this.ussdsession.saveSessionMode(1);
                this.ussdsession.saveUSSDSession(handlersessionlevel);
                return this.runSession(false, true);
        }
        return null;
    }
}
