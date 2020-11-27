package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.helpers.USSDSession;

public class LuseMianViews
{
    private String newLine = String.format("%n", new Object[0]);

    public String getMainMenuView(USSDSession ussdsession, boolean action) {
        String view = "LUSE Main Menu:press"+this.newLine;
        if (action) {
            view = view + "Invalid option." + this.newLine;
        }
        view = view + "1. Buy" + this.newLine;
        view = view + "2. Sell" + this.newLine;
        view = view + "3. Fund" + this.newLine;
        view = view + "4. Portfolio" + this.newLine;
        view = view + "5. Manage Account" + this.newLine;
        return view;
    }

    public String getBalanceMenuView(int action) {
        String view = "Balance: Press " + this.newLine;
        if (action == 0) {
            view = view + "Invalid option." + this.newLine;
        }
        view = view + "1. For Payments" + this.newLine;
        view = view + "2. To repay loan" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }

    public String getTermAndConditionsView() {
        String view = "";
        view = view + "Terms And Conditions:" + this.newLine;
        view = view + "To view the " + AppConfigHelper.APP_NAME + " terms and conditions, please our website at  " + AppConfigHelper.WEBSITE + "" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }

    public String getGenericListHeadersView(String action) {
        String view = "";
        switch (action) {
            case "account":
                view = view + "Account:" + this.newLine;
                return view;
            case "summary":
                view = view + "Summary:" + this.newLine;
                return view;
            case "mini_statement":
                view = view + "Statement:" + this.newLine;
                return view;
            case "beneficiaries":
                view = view + "Beneficiaries:" + this.newLine;
                return view;
            case "beneficiaries_select":
                view = view + "Select to Edit:" + this.newLine;
                return view;
        }
        return view;
    }

    public String getFeedbackFormView(String action, Boolean err) {
        String view = "Feedback:" + this.newLine;
        switch (action) {
            case "feedback_form":
                if (err.booleanValue()) {
                    view = view + "Sorry feedback too short,try again." + this.newLine;
                }
                view = view + "What would you like us to help you with?" + this.newLine;
                view = view + AppConfigHelper.getGoBackText();
                return view;
            case "transaction_success":
                view = view + "Your feedback has been submitted successfully" + this.newLine;
                view = view + AppConfigHelper.getGoBackText();
                return view;
        }
        view = view + "Sorry failed to save feedback, please try again later" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }



    public String getUnavailableView() { return "This service is temporarily unavailable. Please try again later."; }


    public String getUnsubscribedView() { return "You have successfully disabled notifications."; }


    public String getSubscribedView() { return "You have successfully enabled notifications."; }


    public String getUnSuccessfulView() { return "Your request was not successful. Please try again later."; }
}


