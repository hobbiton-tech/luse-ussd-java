package LuseDma.ussd.handlers;


import LuseDma.ussd.helpers.DetailPaginationHelper;
import LuseDma.ussd.helpers.PaginationHelper;
import LuseDma.ussd.models.SecurityModel;
import LuseDma.ussd.pojos.*;
import LuseDma.ussd.pojos.luse.Security;
import LuseDma.ussd.views.SecurityExploreView;
import LuseDma.ussd.helpers.USSDSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import static LuseDma.ussd.views.SecurityExploreView.FORM_NAME;


public class SecuritiesListHandler {
    private final int handlersessionlevel = 2;
    private USSDSession ussdsession = null;
    private MobileSession mobilesession = null;
    private FormSession formsession = null;
    private SecurityModel securityModel = null;
    private ArrayList<ListItem> listitems = null;
    private String header = null;
    private Map<String, Object> form;
    private String securityType;
    private Security securityPojo;
    private Security securitySelected;

    private SecurityExploreView view = new SecurityExploreView();

    public SecuritiesListHandler(USSDSession ussdsession, String securityType) {
        this.securityType = securityType;
        this.ussdsession = ussdsession;
        this.mobilesession = ussdsession.getMobileSession();
        this.formsession = ussdsession.getMobileSession().getFormSession();
        this.form = this.mobilesession.getFormSession().getForm(FORM_NAME);
        this.securityModel = new SecurityModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
    }

    public USSDResponse runSession() throws FileNotFoundException {
        this.listitems = null;
        if (this.ussdsession.getSessionLevel() < (handlersessionlevel + 4)) {
            SecuritiesExploreHandler securitiesExploreHandler;
            JSONObject selected;

            JSONArray securityList = LuseServiceCenter.getSecurityList(this.securityType);
            this.header = this.view.getSecuritiesListHeader(this.securityType);
            this.listitems = getListItems(securityList);

            PaginationHelper paginationHelper = null;
            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel:
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 3);
                    this.ussdsession.saveSessionMode(1);
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no stocks found.");
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 3:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no stocks found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                        case 1:
                            selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, securityList);

                            this.form.put("id", selected.get("id"));
                            this.form.put("csdId", selected.get("csdId"));
                            this.form.put("atsId", selected.get("atsId"));
                            this.form.put("securityType", selected.get("securityType"));
                            this.form.put("securityCode", selected.get("securityCode"));
                            this.form.put("marketCap", selected.get("marketCap"));
                            this.form.put("changeInPrice", selected.get("changeInPrice"));
                            this.form.put("settlementPrice", selected.get("settlementPrice"));
                            this.form.put("openInterest", selected.get("openInterest"));
                            this.form.put("closingPrice", selected.get("closingPrice"));
                            this.form.put("name", selected.get("name"));
                            this.form.put("description", selected.get("description"));
                            this.form.put("symbol", selected.get("symbol"));
                            this.form.put("issueDate", selected.get("issueDate"));
                            this.form.put("maturityDate", selected.get("maturityDate"));
                            this.form.put("couponRate", selected.get("couponRate"));
                            this.form.put("currency", selected.get("currency"));

                            this.formsession.getFormData().put(FORM_NAME, this.form);
                            this.ussdsession.saveFormSession(this.formsession);
                            this.ussdsession.resetList();
                            this.ussdsession.saveUSSDSession(handlersessionlevel + 4);
                            this.ussdsession.saveSessionMode(1);
                            return this.saveSecurity();
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
            return this.saveSecurity();
        }
        return this.saveSecurity();
    }

    public USSDResponse saveSecurity() throws FileNotFoundException {
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel + 4:
                Security security = new Security();
                security.setId((String) this.form.getOrDefault("id", ""));
                security.setCsdId((String) this.form.getOrDefault("csdId", ""));
                security.setAtsId((String) this.form.getOrDefault("atsId", ""));
                security.setSecurityType((String) this.form.getOrDefault("securityType", ""));
                security.setSecurityCode((String) this.form.getOrDefault("securityCode", ""));
                security.setName((String) this.form.getOrDefault("name", ""));
                security.setDescription((String) this.form.getOrDefault("description", ""));
                security.setMarketCap((String) this.form.getOrDefault("marketCap", ""));
                security.setClosingPrice((String) this.form.getOrDefault("closingPrice", ""));
                security.setChangeInPrice((String) this.form.getOrDefault("changeInPrice", ""));
                security.setSettlementPrice((String) this.form.getOrDefault("settlementPrice", ""));
                security.setOpenInterest((String) this.form.getOrDefault("openInterest", ""));
                security.setSymbol((String) this.form.getOrDefault("symbol", ""));
                security.setIssueDate((String) this.form.getOrDefault("issueDate", ""));
                security.setMaturityDate((String) this.form.getOrDefault("maturityDate", ""));
                security.setCouponRate((String) this.form.getOrDefault("couponRate", ""));
                security.setCurrency((String) this.form.getOrDefault("currency", ""));
                this.securityModel.save(security);

                this.ussdsession.saveUSSDSession(handlersessionlevel + 5);
                this.ussdsession.saveSessionMode(1);
                return this.getSecurityDetails((String) this.form.getOrDefault("csdId", ""));
        }
        this.ussdsession.saveSessionMode(1);
        return this.getSecurityDetails((String) this.form.getOrDefault("csdId", ""));
    }

    public USSDResponse getSecurityDetails(String csdId) throws FileNotFoundException {
        if (this.ussdsession.getSessionLevel() < (handlersessionlevel + 7)) {
            this.securitySelected = this.securityModel.findOne(csdId);
            this.header = this.view.getSecurityDetailsHeader(this.securityType, this.securitySelected);
            JSONArray buyOPtions = LuseServiceCenter.buyConfirmation();
            this.listitems = this.getBuyOptionsList(buyOPtions);
            PaginationHelper paginationHelper = null;
            JSONObject selectedOption;
            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel + 5:
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 6);
                    this.ussdsession.saveSessionMode(1);
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no information found.");
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 6:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no information found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                        case 1:
                            selectedOption = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, buyOPtions);
                            System.out.println("selected option ==> "+selectedOption);
                            JSONObject optionData = (JSONObject) selectedOption;
                            String optionId = (String)optionData.getOrDefault("id", "");
                            switch (optionId){
                                case "1":
                                    System.out.println("in option 1");
                                    this.ussdsession.saveSessionMode(1);
                                    this.ussdsession.saveUSSDSession(handlersessionlevel+7);
                                    return this.getClientBrokers();
                            }
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
            this.ussdsession.saveSessionMode(1);
            this.ussdsession.saveSessionMode(handlersessionlevel);
            return this.runSession();
        }
        this.ussdsession.saveSessionMode(1);
        this.ussdsession.saveSessionMode(handlersessionlevel);
        return this.runSession();
    }

    public USSDResponse getClientBrokers() throws FileNotFoundException {
        System.out.println("get client brokers function ==> ");
//        this.header = this.view.stockBuyProcess(this.securitySelected, "broker", false);
        JSONArray clientInformation = LuseServiceCenter.clientInformation();
        System.out.println("client info ==> "+clientInformation);
//        this.listitems = this.getClientBrokerList(clientInformation);
        this.ussdsession.saveSessionMode(1);
        this.ussdsession.saveSessionMode(handlersessionlevel);
        return this.runSession();
    }

    public USSDResponse startBuyProcess(){
        return null;
    }

    public ArrayList<ListItem> getListItems(JSONArray items) {
        ArrayList<ListItem> list = new ArrayList<>();
        try {
            for (int i = 0; i < items.size(); i++) {
                JSONObject o = (JSONObject) items.get(i);
                String id = (String) o.getOrDefault("id", "");
                String securityType = (String) o.getOrDefault("securityType", "");
                String securityName = "";
                switch (securityType) {
                    case "CORP":
                        securityName = (String) o.getOrDefault("csdId", "");
                        break;
                    default:
                        securityName = (String) o.getOrDefault("securityCode", "");
                        break;
                }
                ListItem listitem = new ListItem(id, securityName);
                list.add(listitem);
            }
        } catch (Exception exception) {
        }
        return list;
    }


    public JSONObject getSelectedItem(int arrayIndex, JSONArray items) {
        JSONObject o = null;
        try {
            o = (JSONObject) items.get(arrayIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public ArrayList<ListItem> getBuyOptionsList(JSONArray items) {
        ArrayList<ListItem> list = new ArrayList<>();
        try {
            for (int i = 0; i < items.size(); i++) {
                JSONObject o = (JSONObject) items.get(i);
                String id = (String) o.getOrDefault("id", "");
                String option = (String) o.getOrDefault("option", "");
                ListItem listitem = new ListItem(id, option);
                list.add(listitem);
            }
        } catch (Exception exception) {
        }
        return list;
    }

    public ArrayList<ListItem> getClientBrokerList(JSONArray items) {
        ArrayList<ListItem> list = new ArrayList<>();
        try {
            for (int i = 0; i < items.size(); i++) {
                JSONObject o = (JSONObject) items.get(i);
                String id = (String) o.getOrDefault("id", "");
                String option = (String) o.getOrDefault("option", "");
                ListItem listitem = new ListItem(id, option);
                list.add(listitem);
            }
        } catch (Exception exception) {
        }
        return list;
    }
}
