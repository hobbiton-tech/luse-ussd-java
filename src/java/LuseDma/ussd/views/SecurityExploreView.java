package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.luse.Security;
import LuseDma.ussd.pojos.luse.SecurityOrder;
import org.json.simple.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        String view = security + " Market:Press" + this.newLine;
        if (action) {
            view = view + "Invalid option." + this.newLine;
        }
//        view = view + "1. Search" + this.newLine;
        view = view + "1. Explore" + this.newLine;
        view = view + AppConfigHelper.getGoBackText();
        return view;
    }

    public String getSearchMenu(String securityType, boolean action) {
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
        if (action) {
            view = view + "Invalid input entered." + this.newLine;
        }
        view = view + "Enter first letter of security" + this.newLine;
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

    public String securityOptionsHeader(String securityType, Security security) {
        String view = "";

        switch (securityType) {
            case "CS":
                view = view + security.getName() + ":press" + this.newLine;
                break;
            case "CORP":
                view = view + security.getCsdId() + ":press" + this.newLine;
                break;
            case "CASH":
                view = view + security.getSecurityCode() + ":press" + this.newLine;
                break;
        }
        return view;
    }

    public String securityDetailsHeader(String securityType, Security security) {
        String view = "";

        switch (securityType) {
            case "CS":
                view = view + "Stock:"+security.getName() + this.newLine;
                break;
            case "CORP":
                view = view + "Bond:"+security.getCsdId() + this.newLine;
                break;
            case "CASH":
                view = view + "Commodity:"+security.getSecurityCode()+ this.newLine;
                break;
        }
        return view;
    }

    public ArrayList<ListItem> securityDetails(String securityType, Security security) {

        ListItem item;
        DecimalFormat df = new DecimalFormat(".##");
        String currency = AppConfigHelper.parseNull(security.getCurrency());
        if (currency.equals("ZMK")) {
            currency = "ZMW";
        }

        ArrayList<ListItem> view = new ArrayList<>();
        switch (securityType) {
            case "CS":
            case "CASH":
                item = new ListItem("securityCode", AppConfigHelper.parseNull(security.getSecurityCode()));
                view.add(item);
                item = new ListItem("Symbol", AppConfigHelper.parseNull(security.getSymbol()));
                view.add(item);
                break;
            case "CORP":
                item = new ListItem("ISIN", AppConfigHelper.parseNull(security.getCsdId()));
                view.add(item);
                item = new ListItem("Symbol", AppConfigHelper.parseNull(security.getSymbol()));
                view.add(item);
                item = new ListItem("Coupon Rate", "%"+AppConfigHelper.parseNull(security.getCouponRate()));
                view.add(item);
                break;
        }
        item = new ListItem("price", currency+AppConfigHelper.parseNull(df.format(Double.parseDouble(security.getClosingPrice()))));
        view.add(item);
        item = new ListItem("Issue Date", AppConfigHelper.parseNull(LocalDate.parse(security.getIssueDate(), DateTimeFormatter.BASIC_ISO_DATE).toString()));
        view.add(item);
        item = new ListItem("Maturity Date", AppConfigHelper.parseNull(LocalDate.parse(security.getMaturityDate(), DateTimeFormatter.BASIC_ISO_DATE).toString()));
        view.add(item);
        return view;
    }

    public String stockBuyProcess(Security security, String field, boolean isError) {
        String view = "";
//        view = view + "Stock:" + security.getName() + this.newLine;
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
                view = view + "Bond:" + security.getCsdId() + this.newLine;
                view = view + "Enter bond volume" + this.newLine;
                break;
            case "CASH":
                view = view + "Commodity:" + security.getSecurityCode() + this.newLine;
                view = view + "Enter commodity tonnage" + this.newLine;
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
            view = view + "Invalid input." + criteriaMsg + this.newLine;
        }
        switch (securityType) {
            case "CS":
                view = view + "Stock:" + security.getName() + this.newLine;
                view = view + "Enter price per share" + this.newLine;
                break;
            case "CORP":
                view = view + "Bond:" + security.getCsdId() + this.newLine;
                view = view + "Enter price per volume" + this.newLine;
                break;
            case "CASH":
                view = view + "Commodity:" + security.getSecurityCode() + this.newLine;
                view = view + "Enter price per tonnage" + this.newLine;
                break;
        }
        return view;
    }

    public ArrayList<ListItem> orderDetails(String securityType, Security security, SecurityOrder order) {

        ListItem item;
        Double priceEntered = Double.parseDouble(AppConfigHelper.parseNull(order.getPrice()));
        Double orderPrice = priceEntered * Double.parseDouble(order.getVolume());
        Double brokerFess = orderPrice * 0.01;
        Double luseFess = orderPrice * 0.00375;
        Double secFess = orderPrice * 0.00125;
        Double totalCost = orderPrice + brokerFess + luseFess + secFess;
        String currency = AppConfigHelper.parseNull(security.getCurrency());
        if (currency.equals("ZMK")) { currency = "ZMW"; }
        DecimalFormat df = new DecimalFormat(".##");
        String priceText = "";

        ArrayList<ListItem> view = new ArrayList<>();
        switch (securityType){
            case "CS":
                item = new ListItem("Shares", AppConfigHelper.parseNull(order.getVolume()));
                view.add(item);
                priceText = "Price per share";
                break;
            case "CORP":
                item = new ListItem("Volume", AppConfigHelper.parseNull(order.getVolume()));
                view.add(item);
                priceText = "Price per volume";
                break;
            case "CASH":
                item = new ListItem("Tonnage", AppConfigHelper.parseNull(order.getVolume()));
                view.add(item);
                priceText = "Price per tonnage";
                break;
        }
        item = new ListItem(priceText, currency+AppConfigHelper.parseNull(df.format(Double.parseDouble(order.getPrice()))));
        view.add(item);
        item = new ListItem("Luse Fees", currency+df.format(luseFess));
        view.add(item);
        item = new ListItem("Sec Fees", currency+df.format(secFess));
        view.add(item);
        item = new ListItem("Broker Fees", currency+df.format(brokerFess));
        view.add(item);
        item = new ListItem("Purchase total", currency+df.format(orderPrice));
        view.add(item);
        item = new ListItem("Transaction Fees", currency+df.format(totalCost));
        view.add(item);

        return view;
    }

    public String securityOrderResponse() {
        String view = "";
        view = view + "Your order is being processed you will receive an sms response soon." + this.newLine;
        return view;
    }


}
