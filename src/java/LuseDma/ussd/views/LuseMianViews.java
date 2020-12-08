package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.helpers.USSDSession;

public class LuseMianViews
{
    private String newLine = String.format("%n", new Object[0]);

    public String getMainMenuView(USSDSession ussdsession, boolean action) {
        String view = "MyLuse Main Menu:press"+this.newLine;
        if (action) {
            view = view + "Invalid option." + this.newLine;
        }
        view = view + "1. Buy" + this.newLine;
        view = view + "2. Sell" + this.newLine;
        view = view + "3. Fund" + this.newLine;
        view = view + "4. Portfolio" + this.newLine;
//        view = view + "5. Manage Account" + this.newLine;
        return view;
    }

}


