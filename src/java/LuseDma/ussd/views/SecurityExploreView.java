package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.luse.Security;
import LuseDma.ussd.pojos.luse.SecurityOrder;
import org.json.JSONObject;

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
        String view = security + " Market:Press" + this.newLine;
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
        DecimalFormat df = new DecimalFormat(".##");
        LocalDate date = null;
        if (isError) {
            view = view + "Invalid option." + this.newLine;
        }
        if (currency.equals("ZMK")) {
            currency = "ZMW";
        }
        switch (securityType) {
            case "CS":
//                view = view + "Stock Market Details:" + this.newLine;
                view = view + "Name:" + AppConfigHelper.parseNull(security.getName()) + this.newLine;
                view = view + "code:" + AppConfigHelper.parseNull(security.getSecurityCode()) + this.newLine;
                view = view + "Price:" + currency + df.format(Double.parseDouble(AppConfigHelper.parseNull(security.getClosingPrice()))) + this.newLine;
                view = view + "Issue Date:"+date.parse(AppConfigHelper.parseNull(security.getIssueDate()), DateTimeFormatter.BASIC_ISO_DATE)+this.newLine;
                break;
            case "CORP":
//                view = view + "Bond Market Details:" + this.newLine;
                view = view + "ISIN:" + AppConfigHelper.parseNull(security.getCsdId()) + this.newLine;
//                view = view + "Symbol:" + AppConfigHelper.parseNull(security.getSymbol()) + this.newLine;
                view = view + "Price:" + currency + df.format(Double.parseDouble(AppConfigHelper.parseNull(security.getClosingPrice()))) + this.newLine;
                view = view + "Coupon Rate:%"+AppConfigHelper.parseNull(security.getCouponRate())+this.newLine;
//                view = view + "Issue Date:"+date.parse(AppConfigHelper.parseNull(security.getIssueDate()), DateTimeFormatter.BASIC_ISO_DATE)+this.newLine;
                view = view + "Maturity Date:"+date.parse(AppConfigHelper.parseNull(security.getMaturityDate()), DateTimeFormatter.BASIC_ISO_DATE)+this.newLine;
                break;
            case "CASH":
//                view = view + "Commodity Market Details:" + this.newLine;
                view = view + "code:" + AppConfigHelper.parseNull(security.getSecurityCode()) + this.newLine;
                view = view + "Symbol:" + AppConfigHelper.parseNull(security.getSymbol()) + this.newLine;
                view = view + "Price:" + currency + df.format(Double.parseDouble(AppConfigHelper.parseNull(security.getClosingPrice()))) + this.newLine;
                view = view + "Issue Date:"+date.parse(AppConfigHelper.parseNull(security.getIssueDate()), DateTimeFormatter.BASIC_ISO_DATE)+this.newLine;
                break;
        }
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
                view = view + "Bond:"+security.getCsdId()+this.newLine;
                view = view + "Enter bond volume" + this.newLine;
                break;
            case "CASH":
                view = view + "Commodity:"+security.getSecurityCode()+this.newLine;
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
            view = view + "Invalid input."+criteriaMsg+this.newLine;
        }
        switch (securityType) {
            case "CS":
                view = view + "Stock:" + security.getName() + this.newLine;
                view = view + "Enter price per share" + this.newLine;
                break;
            case "CORP":
                view = view + "Bond:"+security.getCsdId()+this.newLine;
                view = view + "Enter price per volume" + this.newLine;
                break;
            case "CASH":
                view = view + "Commodity:"+security.getSecurityCode()+this.newLine;
                view = view + "Enter price per tonnage" + this.newLine;
                break;
        }
        return view;
    }

    public String confirmOrder(String securityType, Security security, SecurityOrder order, boolean isError) {
        String view = "";
        Double priceEntered = Double.parseDouble(AppConfigHelper.parseNull(order.getPrice()));
        Double brokerFess = priceEntered*0.01;
        Double luseFess = priceEntered*0.00375;
        Double secFess = priceEntered*0.00125;
//        System.out.println("feess => "+brokerFess+luseFess+secFess);
        Double totalCost =priceEntered+brokerFess+luseFess+secFess;
        String currency = AppConfigHelper.parseNull(security.getCurrency());
        DecimalFormat df = new DecimalFormat(".##");
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
                view = view + "Price per share: " + df.format(Double.parseDouble(order.getPrice()))+ this.newLine;
                view = view + "Total Cost:"+ currency + df.format(totalCost * Double.parseDouble(order.getVolume()))+this.newLine;
                break;
            case "CORP":
                view = view + "Bond:"+security.getCsdId()+ this.newLine;
                view = view + "Volume:"+order.getVolume()+this.newLine;
                view = view + "Price per volume:"+df.format(Double.parseDouble(order.getPrice()))+this.newLine;
                view = view + "Total Cost:"+currency  + df.format(totalCost)+this.newLine;
                break;
            case "CASH":
                view = view + "Commodity:"+security.getSecurityCode()+ this.newLine;
                view = view + "Tonnage:"+order.getVolume()+this.newLine;
                view = view + "Price per tonnage:"+df.format(Double.parseDouble(order.getPrice()))+this.newLine;
                view = view + "Total Cost:"+currency  + df.format(totalCost)+this.newLine;
                break;
        }
        return view;
    }

    public String securityOrderResponse() {
        String view ="";
        view = view + "Your order is being processed you will receive an sms response soon." + this.newLine;
        return view;
    }


}
