package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.luse.Security;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class SecurityExploreView {

    private String newLine = String.format("%n", new Object[0]);
    public static final String FORM_NAME = "security_form";

    public String getExploreOptionsMenu(boolean action, String security) {

        switch (security) {
            case "CS":
                security = "Stocks";
                break;
            case "CORP":
                security = "Bonds";
                break;
            case "CASH":
                security = "Commodities";
                break;
        }
        String view = security + " Market:Press;" + this.newLine;
        if (action) {
            view = view + "Invalid option." + this.newLine;
        }
        view = view + "1. Search" + this.newLine;
        view = view + "2. Explore" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }

    public String getSearchMenu(boolean action, String security) {
        String view = security + " Market:Press;" + this.newLine;
        if (action) {
            view = view + "Invalid option." + this.newLine;
        }
        view = view + "Enter first letter of security" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }

    public String getSecuritiesListHeader(String securityType) {
        String view = "";
        switch (securityType) {
            case "CS":
                view = view + "Stocks Market:Press" + this.newLine;
                break;
            case "CORP":
                view = view + "Bonds Market:Press" + this.newLine;
                break;
            case "CASH":
                view = view + "Commodities Market:Press" + this.newLine;
                break;
        }
        return view;
    }

    public String getSecurityDetailsHeader(String securityType, Security security) {
        String view = "";
        String currency = AppConfigHelper.parseNull(security.getCurrency());
        if(currency.equals("ZMK")){
            currency = "ZMW";
        }
        switch (securityType) {
            case "CS":
                view = view + "Stock Details" + this.newLine;
                view = view + "Name:" + AppConfigHelper.parseNull(security.getName())+this.newLine;
                view = view + "Security code:" + AppConfigHelper.parseNull(security.getSecurityCode())+this.newLine;
                view = view + "Price:" +currency+AppConfigHelper.parseNull(security.getClosingPrice())+this.newLine;
                break;
            case "CORP":
                view = view + "Bond Details:" + this.newLine;
                break;
            case "CASH":
                view = view + "Commodity Details:" + this.newLine;
                break;
        }
        return view;
    }

    public ArrayList<ListItem> getSecurityDetails(Security security) {
        ListItem item;
        ArrayList<ListItem> view = new ArrayList<>();

        var dateString = AppConfigHelper.parseNull(security.getIssueDate());
        var issueDate = dateString.substring(0,4)+"-"+dateString.substring(4,6)+"-"+dateString.substring(6,8);
        var dateString2 = AppConfigHelper.parseNull(security.getMaturityDate());
        var maturityDate = dateString2.substring(0,4)+"-"+dateString2.substring(4,6)+"-"+dateString2.substring(6,8);

        if (security.getSecurityType().equals("CS")) {
            item = new ListItem("name", AppConfigHelper.parseNull(security.getName()));
            view.add(item);
        }
        if (!(security.getSecurityType().equals("CORP"))) {
            item = new ListItem("securityCode", AppConfigHelper.parseNull(security.getSecurityCode()));
            view.add(item);
            item = new ListItem("csdId", AppConfigHelper.parseNull(security.getCsdId()));
            view.add(item);
        }
        if (security.getSecurityType().equals("CORP")) {
            item = new ListItem("ISIN", AppConfigHelper.parseNull(security.getCsdId()));
            view.add(item);
            item = new ListItem("couponRate", AppConfigHelper.parseNull(security.getCouponRate()));
            view.add(item);
        }
        if (!security.getSecurityType().equals("CS")) {
            item = new ListItem("marketCap", AppConfigHelper.parseNull(security.getMarketCap()));
            view.add(item);
            item = new ListItem("settlementPrice", AppConfigHelper.parseNull(security.getSettlementPrice()));
            view.add(item);
            item = new ListItem("openInterest", AppConfigHelper.parseNull(security.getOpenInterest()));
            view.add(item);
        }

        item = new ListItem("price", AppConfigHelper.parseNull(security.getCurrency())+AppConfigHelper.parseNull(security.getClosingPrice()));
        view.add(item);
        item = new ListItem("symbol", AppConfigHelper.parseNull(security.getSymbol()));
        view.add(item);
        item = new ListItem("issueDate", issueDate);
        view.add(item);
        item = new ListItem("maturityDate", maturityDate);
        view.add(item);
        return view;
    }

    public String stockBuyProcess(Security security,String field,boolean isError){
        String view = "";

        view = view + "Stock:"+security.getName()+this.newLine;
        if(isError){
            view = view + "Invalid input entered" + this.newLine;
        }
        switch(field){
            case "broker":
                view = view + "Select your broker"+this.newLine;
                break;
            case "shares":
                view = view+ "Enter number of shares"+this.newLine;
                break;
            case "price":
                view = view + "Enter price per share"+this.newLine;
                break;
        }
        view = view + "Enter price ";

        return view;
    }

    private boolean isStockPriceEnteredValid(double orgPrice, double enteredPrice){
        double percentRate = 0.25 * orgPrice;
        if((-1*percentRate)<=enteredPrice && (enteredPrice<=orgPrice)){
            return true;
        }
        return false;
    }

}
