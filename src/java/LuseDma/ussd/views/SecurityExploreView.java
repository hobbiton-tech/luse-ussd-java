package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.luse.Security;
import LuseDma.ussd.pojos.luse.SecurityOrder;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

public class SecurityExploreView {

    private String newLine = String.format("%n", new Object[0]);
    public static final String FORM_NAME = "security_form";
    private static final double brokeragePercentageFee = 1/100;
    private static final double lusePercentageFee = 0.375/100;
    private static final double secPercentageFee = 0.125/100;


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

    public String getSecurityDetailsHeader(String securityType, Security security, boolean isError) {
        String view = "";
        String currency = AppConfigHelper.parseNull(security.getCurrency());
        if (isError) {
            view = view + "Invalid option." + this.newLine;
        }
        if (currency.equals("ZMK")) {
            currency = "ZMW";
        }
        switch (securityType) {
            case "CS":
                view = view + "Stock Market Details:" + this.newLine;
                view = view + "Name:" + AppConfigHelper.parseNull(security.getName()) + this.newLine;
                view = view + "code:" + AppConfigHelper.parseNull(security.getSecurityCode()) + this.newLine;
                view = view + "Price:" + currency + AppConfigHelper.parseNull(security.getClosingPrice()) + this.newLine;
                break;
            case "CORP":
                view = view + "Bond Market Details:" + this.newLine;
                view = view + "ISIN:" + AppConfigHelper.parseNull(security.getCsdId()) + this.newLine;
                view = view + "Symbol:" + AppConfigHelper.parseNull(security.getSymbol()) + this.newLine;
                view = view + "Price:" + currency + AppConfigHelper.parseNull(security.getClosingPrice()) + this.newLine;
                break;
            case "CASH":
                view = view + "Commodity Market Details:" + this.newLine;
                view = view + "code:" + AppConfigHelper.parseNull(security.getSecurityCode()) + this.newLine;
                view = view + "Symbol:" + AppConfigHelper.parseNull(security.getSymbol()) + this.newLine;
                view = view + "Price:" + currency + AppConfigHelper.parseNull(security.getClosingPrice()) + this.newLine;
                break;
        }
        return view;
    }

    public ArrayList<ListItem> getSecurityDetails(Security security) {
        ListItem item;
        ArrayList<ListItem> view = new ArrayList<>();

        var dateString = AppConfigHelper.parseNull(security.getIssueDate());
        var issueDate = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6, 8);
        var dateString2 = AppConfigHelper.parseNull(security.getMaturityDate());
        var maturityDate = dateString2.substring(0, 4) + "-" + dateString2.substring(4, 6) + "-" + dateString2.substring(6, 8);

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

        item = new ListItem("price", AppConfigHelper.parseNull(security.getCurrency()) + AppConfigHelper.parseNull(security.getClosingPrice()));
        view.add(item);
        item = new ListItem("symbol", AppConfigHelper.parseNull(security.getSymbol()));
        view.add(item);
        item = new ListItem("issueDate", issueDate);
        view.add(item);
        item = new ListItem("maturityDate", maturityDate);
        view.add(item);
        return view;
    }

    public String stockBuyProcess(Security security, String field, boolean isError) {
        String view = "";
        view = view + "Stock:" + security.getName() + this.newLine;
        if (isError) {
            view = view + "Invalid input entered" + this.newLine;
        }
        switch (field) {
            case "broker":
                view = view + "Select your broker" + this.newLine;
                break;
            case "shares":
                view = view + "Enter number of shares" + this.newLine;
                break;
            case "price":
                view = view + "Enter price per share" + this.newLine;
                break;
        }
        return view;
    }

    private boolean isStockPriceEnteredValid(double orgPrice, double enteredPrice) {
        double percentRate = 0.25 * orgPrice;
        if ((-1 * percentRate) <= enteredPrice && (enteredPrice <= orgPrice)) {
            return true;
        }
        return false;
    }

    public String getSecurityVolume(String securityType, Security security, boolean isError) {
        String view = "";
        if (isError) {
            view = view + "Invalid input entered" + this.newLine;
        }
        switch (securityType) {
            case "CS":
                view = view + "Stock:" + security.getName() + this.newLine;
                view = view + "Enter number of shares" + this.newLine;
                break;
            case "CORP":
                view = view + "Enter bond tonnage" + this.newLine;
                break;
            case "CASH":
                view = view + "Enter commodity volume" + this.newLine;
                break;
        }
        return view;
    }

    public String securityPrice(String securityType, Security security, boolean isError, boolean isCriteria, String criteriaMsg) {
        String view = "";
        if (isError) {
            view = view + "Invalid input entered" + this.newLine;
        }
        if (isCriteria) {
            view = view + "Invalid input."+criteriaMsg+this.newLine;
        }
        switch (securityType) {
            case "CS":
                view = view + "Stock:" + security.getName() + this.newLine;
                view = view + "Enter price per share" + this.newLine;
                break;
            case "CORP":
                view = view + "Enter price per tonnage" + this.newLine;
                break;
            case "CASH":
                view = view + "Enter price per volume" + this.newLine;
                break;
        }
        return view;
    }

    public String confirmOrder(String securityType, Security security, SecurityOrder order, boolean isError) {
        String view = "";
        String totalCost = String.valueOf(Double.parseDouble(AppConfigHelper.parseNull(security.getClosingPrice())) + lusePercentageFee+brokeragePercentageFee+secPercentageFee);
        String currency = AppConfigHelper.parseNull(security.getCurrency());
        DecimalFormat df = new DecimalFormat("###.##");
        df.setRoundingMode(RoundingMode.CEILING);
        if (isError) {
            view = view + "Invalid option." + this.newLine;
        }
        if (currency.equals("ZMK")) {
            currency = "ZMW";
        }
        switch (securityType) {
            case "CS":
                view = view + "Stock:" + security.getName() + this.newLine;
                view = view + "Shares: " + order.getVolume() + this.newLine;
                view = view + "Price per share: " + order.getPrice() + this.newLine;
                view = view + "Total Cost:"+currency  + totalCost+this.newLine;
                break;
            case "CORP":
                view = view + "Bond:"+security.getCsdId()+ this.newLine;
                view = view + "Tonnage:"+order.getVolume()+this.newLine;
                view = view + "Price per tonnage:"+order.getPrice()+this.newLine;
                view = view + "Total Cost:"+currency  + totalCost+this.newLine;
                break;
            case "CASH":
                view = view + "Commodity:"+security.getSecurityCode()+ this.newLine;
                view = view + "Volume:"+order.getVolume()+this.newLine;
                view = view + "Price per volume:"+order.getPrice()+this.newLine;
                view = view + "Total Cost:"+currency  + totalCost+this.newLine;
                break;
        }
        return view;
    }

    public String securityOrderResponse() {
        String view ="";
        view = view + "Your order is being processed you will receive an sms response soon." + this.newLine;
        return view;
    }

    public String bondCalculator(){
        String view ="";
        view = view + "Calculate bond price by:"+this.newLine;
        return view;
    }

    public String bondsOptionInputView(Security security, String action, boolean isError){
        String view = "";
        view = view + "Bond:"+security.getCsdId()+this.newLine;
        if (isError) {
            view = view + "Invalid input entered" + this.newLine;
        }
        switch(action){
            case "yield":
                view = view + "Enter yield value"+this.newLine;
                break;
            case "price":
                view = view + "Enter price"+this.newLine;
                break;
        }
        return view;
    }

}
