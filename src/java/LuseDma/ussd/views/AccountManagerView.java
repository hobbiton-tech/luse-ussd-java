package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.luse.Client;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AccountManagerView {

    private String newLine = String.format("%n", new Object[0]);

    public String accountManagerOPtionsMenu(boolean action) {

        String view = "Manage Account Menu:Press;" + this.newLine;
        if (action) {
            view = view + "Invalid option." + this.newLine;
        }
        view = view + "1. View Balance" + this.newLine;
        view = view + "2. Change Password" + this.newLine;
        view = view + "3. Manage Brokers" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }

    public ArrayList<ListItem> accountBalanceDetails(Client client) {
        ListItem item;
        DecimalFormat df = new DecimalFormat(".##");

        ArrayList<ListItem> view = new ArrayList<>();
        item = new ListItem("Full Name", AppConfigHelper.parseNull(client.getFullname()));
        view.add(item);
        item = new ListItem("Current Balance", "ZMW" + df.format(Double.parseDouble(AppConfigHelper.parseNull(client.getWalletBalance()))));
        view.add(item);

        return view;
    }

    public String changePasswordInput(boolean isError, String inputType, boolean isPasswordMatch) {
        String view = "";
        view = view + "Change Password" + this.newLine;
        if (isError) {
            view = view + "Invalid credentials, try again." + this.newLine;
        }

        if (isPasswordMatch) {
            view = view + "Passwords mismatch. Try again" + this.newLine;
        }

        switch (inputType) {
            case "1":
                view = view + "Enter current password" + this.newLine;
                break;
            case "2":
                view = view + "Enter new password" + this.newLine;
                break;
            case "3":
                view = view + "Re-enter new password to confirm" + this.newLine;
                break;
        }

        return view;
    }

    public String changePasswordResponse() {
        String view = "";
        view = view + "request process complete. You will receive an SMS confirmation soon" + this.newLine;

        return view;
    }

    public String manageBrokersOptionsMenu(boolean action) {

        String view = "Manage Brokers:Press;" + this.newLine;
        if (action) {
            view = view + "Invalid option." + this.newLine;
        }
        view = view + "1. Add Broker" + this.newLine;
        view = view + "2. View Account Brokers" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }

    public ArrayList<ListItem> accountBrokerDetails(JSONArray brokersArray) {
        ListItem item;
        DecimalFormat df = new DecimalFormat(".##");
        System.out.println(brokersArray);

        ArrayList<ListItem> view = new ArrayList<>();
        for (int i = 0; i < brokersArray.size(); i++) {
            JSONObject o = (JSONObject) brokersArray.get(i);
            item = new ListItem("BrokerId", AppConfigHelper.parseNull((String) o.getOrDefault("brokerId", "")));
            view.add(item);
        }
        return view;
    }

    public String addBrokerResponse() {
        String view = "";
        view = view + "request processing. You will receive an SMS confirmation soon." + this.newLine;
        return view;
    }


}
