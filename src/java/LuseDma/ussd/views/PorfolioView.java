package LuseDma.ussd.views;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.kyc.Subscriber;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PorfolioView {

    private String newLine = String.format("%n", new Object[0]);

    public String holdingMenuHeader(){
        String view = "";
        view = view + "Porfolio Menu"+this.newLine;
        view = view + "List of securtiy holdings:Press"+this.newLine;

        return view;
    }

    public String holdingsDetailHeader(){
        String view = "";
        view = view + "Holdings Details"+this.newLine;

        return view;
    }

    public ArrayList<ListItem> holdingsDetail(JSONObject holdings) {
        ListItem item;
        ArrayList<ListItem> view = new ArrayList<>();
        item = new ListItem("csdId", AppConfigHelper.parseNull(holdings.get("csdId").toString()));
        view.add(item);
        item = new ListItem("securityCode", AppConfigHelper.parseNull(holdings.get("securityCode").toString()));
        view.add(item);
        item = new ListItem("shortLongIndicator", AppConfigHelper.parseNull(holdings.get("shortLongIndicator").toString()));
        view.add(item);
        item = new ListItem("holdingsBalance", AppConfigHelper.parseNull(holdings.get("holdingsBalance").toString()));
        view.add(item);
        item = new ListItem("availableBalance", AppConfigHelper.parseNull(holdings.get("availableBalance").toString()));
        view.add(item);
        return view;
    }
}
