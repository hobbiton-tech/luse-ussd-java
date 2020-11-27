package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;

public class SecuritiesView {

    private String newLine = String.format("%n", new Object[0]);
    public String getSecuritiesMenu(boolean action) {
        String view = "Securities Menu:Press;" + this.newLine;
        if (action) {
            view = view + "Invalid option." + this.newLine;
        }
        view = view + "1. Stocks" + this.newLine;
        view = view + "2. Bonds" + this.newLine;
        view = view + "3. Commodities" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }
}
