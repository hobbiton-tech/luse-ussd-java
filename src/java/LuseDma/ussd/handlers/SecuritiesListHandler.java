package LuseDma.ussd.handlers;

import LuseDma.ussd.helpers.DetailPaginationHelper;
import LuseDma.ussd.helpers.PaginationHelper;
import LuseDma.ussd.models.luse.ClientModel;
import LuseDma.ussd.models.luse.SecurityModel;
import LuseDma.ussd.models.luse.SecurityOrderModel;
import LuseDma.ussd.pojos.*;
import LuseDma.ussd.pojos.luse.Client;
import LuseDma.ussd.pojos.luse.Security;
import LuseDma.ussd.pojos.luse.SecurityOrder;
import LuseDma.ussd.views.SecurityExploreView;
import LuseDma.ussd.helpers.USSDSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
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
    private Map<String, Object> securityForm, buyForm, optionSelectedForm, searchForm;
    private String securityType;
    private Security securitySelected;
    private JSONObject selected;
    private String msisdn;
    private String securityFilter;
    private String buyOrSell;
    private String optionSelectedFormName = "optionSelectedForm";
    private String searchFormName = "search_form";


    private SecurityExploreView view = new SecurityExploreView();

    public SecuritiesListHandler(USSDSession ussdsession, String securityType, String securityFilter, String buyOrSell) {
        this.securityType = securityType;
        this.ussdsession = ussdsession;
        this.mobilesession = ussdsession.getMobileSession();
        this.msisdn = ussdsession.getMobileSession().getMSISDN();
        this.formsession = ussdsession.getMobileSession().getFormSession();
        this.securityForm = this.mobilesession.getFormSession().getForm(FORM_NAME);
        this.buyForm = this.mobilesession.getFormSession().getForm(this.securityType);
        this.optionSelectedForm = this.mobilesession.getFormSession().getForm(this.optionSelectedFormName);
        this.securityModel = new SecurityModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
        this.searchForm = this.mobilesession.getFormSession().getForm(this.searchFormName);
        this.securityFilter = securityFilter;
        this.buyOrSell = buyOrSell;
    }


    public USSDResponse runSession() throws FileNotFoundException {
        System.out.println("run method session level ==> " + this.ussdsession.getSessionLevel());
        if (this.ussdsession.getSessionLevel() < (handlersessionlevel + 2)) {

            JSONArray securityList = LuseServiceCenter.getSecurityList(this.securityType, this.securityFilter);
            this.header = this.view.getSecuritiesListHeader(this.securityType);
            this.listitems = getSecuritiesList(securityList);
            PaginationHelper paginationHelper = null;

            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel:
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                    this.ussdsession.saveSessionMode(1);
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no stocks found.");
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 1:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no stocks found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                        case 1:
                            this.selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, securityList);

                            System.out.println("selected security ==> " + this.selected);

                            this.setSecurityFormParametersAndSave();

                            this.ussdsession.resetList();
                            this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
                            this.ussdsession.saveSessionMode(1);

                            return this.securityOptionSelector((String) this.securityForm.get("csdId"));
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
            return this.securityOptionSelector((String) this.securityForm.get("csdId"));
        }
        return this.securityOptionSelector((String) this.securityForm.get("csdId"));
    }

    public USSDResponse securityOptionSelector(String csdId) throws FileNotFoundException {
        System.out.println("securityOptionSelector method session level ==> " + this.ussdsession.getSessionLevel());
        this.securitySelected = this.securityModel.findOne(csdId);
        if (this.ussdsession.getSessionLevel() < handlersessionlevel + 4) {

            JSONArray optionSelectors = LuseServiceCenter.optionSelectors("securityDetails", this.buyOrSell);
            this.header = this.view.securityOptionsHeader(this.securityType, this.securitySelected);
            this.listitems = getOptions(optionSelectors);
            PaginationHelper paginationHelper = null;

            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel + 2:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no information found.");
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 3);
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 3:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no information found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                        case 1:
                            this.selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, optionSelectors);
                            switch ((String) this.selected.get("id")) {
                                case "1":
                                    this.ussdsession.resetList();
                                    this.ussdsession.saveSessionMode(1);
                                    this.ussdsession.saveUSSDSession(handlersessionlevel + 4);

                                    this.optionSelectedForm.put("action", "viewSecurityDetails");
                                    this.formsession.getFormData().put(this.optionSelectedFormName, optionSelectedForm);
                                    this.ussdsession.saveFormSession(this.formsession);

                                    return this.securityDetails(this.optionSelectedForm.get("action").toString());
                                case "2":
                                    this.ussdsession.resetList();
                                    this.ussdsession.saveSessionMode(1);
                                    this.ussdsession.saveUSSDSession(handlersessionlevel + 4);

                                    this.optionSelectedForm.put("action", "");
                                    this.formsession.getFormData().put(this.optionSelectedFormName, optionSelectedForm);
                                    this.ussdsession.saveFormSession(this.formsession);

                                    return this.securityDetails(this.optionSelectedForm.get("action").toString());
                            }
                            break;
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
        }
        return this.securityDetails(this.optionSelectedForm.get("action").toString());
    }

    public USSDResponse securityDetails(String action) throws FileNotFoundException {
        System.out.println("securityDetails method session level ==> " + this.ussdsession.getSessionLevel());
        if (action.equals("viewSecurityDetails")) {

            this.header = this.view.securityDetailsHeader(this.securityType, this.securitySelected);
            this.listitems = this.view.securityDetails(this.securityType, this.securitySelected);

            DetailPaginationHelper paginationHelper = null;
            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel + 4:
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 5);
                    this.ussdsession.saveSessionMode(1);
                    paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, this.header, "", "Sorry no information found.");
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 5:
                    paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, this.header, "", "Sorry no information found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
        }
        return this.clientBrokers();
    }

    public USSDResponse clientBrokers() throws FileNotFoundException {
        if (this.ussdsession.getSessionLevel() < (handlersessionlevel + 6)) {
            this.header = this.view.stockBuyProcess(this.securitySelected, "broker", false);

            JSONArray brokersList = LuseServiceCenter.clientInformation("brokers");
            this.listitems = getClientBrokerList(brokersList);

            System.out.println("clientBrokers session level ==> " + this.ussdsession.getSessionLevel());

            PaginationHelper paginationHelper = null;

            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel + 4:
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 5);
                    this.ussdsession.saveSessionMode(1);
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no brokers found.");
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 5:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no brokers found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                        case 1:
                            this.selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, brokersList);

                            this.addFieldtoBuyFormAndSave("brokerId", (String) this.selected.get("brokerId"));
                            this.addFieldtoBuyFormAndSave("brokerAtsId", this.selected.get("atsId").toString());

                            this.ussdsession.resetList();
                            this.ussdsession.saveSessionMode(1);
                            this.ussdsession.saveUSSDSession(handlersessionlevel + 6);
                            return this.securityVolume();
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
            return this.securityVolume();
        }
        return this.securityVolume();
    }

    public USSDResponse securityVolume() throws FileNotFoundException {
        System.out.println("security volume session level ==> " + this.ussdsession.getSessionLevel());
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel + 6:
                this.ussdsession.saveSessionMode(1);
                this.ussdsession.saveUSSDSession(handlersessionlevel + 7);
                return this.ussdsession.buildUSSDResponse(this.view.getSecurityVolume(this.securityType, this.securitySelected, false), 2);
            case handlersessionlevel + 7:
                String volumeEntered = this.ussdsession.getUserInput();
                Boolean isValidInput = this.validateInput("number", volumeEntered);
                if (isValidInput) {
                    this.addFieldtoBuyFormAndSave("volume", volumeEntered);
                    this.ussdsession.saveSessionMode(1);
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 8);
                    return this.securityPrice();
                }

                this.ussdsession.saveSessionMode(1);
                return this.ussdsession.buildUSSDResponse(this.view.getSecurityVolume(this.securityType, this.securitySelected, true), 2);
        }
        this.ussdsession.saveSessionMode(1);
        return this.securityPrice();
    }

    public USSDResponse securityPrice() throws FileNotFoundException {
        System.out.println("security price session level ==> " + this.ussdsession.getSessionLevel());
        if (this.ussdsession.getSessionLevel() < handlersessionlevel + 10) {
            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel + 8:
                    this.ussdsession.saveSessionMode(1);
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 9);
                    return this.ussdsession.buildUSSDResponse(this.view.securityPrice(this.securityType, this.securitySelected, false, false, ""), 2);
                case handlersessionlevel + 9:
                    this.ussdsession.saveSessionMode(1);
                    String priceEntered = this.ussdsession.getUserInput();
                    Boolean isValidInput = this.validateInput("number", priceEntered);
                    if (isValidInput) {
                        switch (this.securityType) {
                            case "CS":
                                double price = Double.parseDouble(priceEntered);
                                double securityPrice = Double.parseDouble(this.securityForm.get("closingPrice").toString());
                                double criteria = securityPrice * 0.25;
                                System.out.println("criteria ==> " + criteria);
                                double minPrice = (securityPrice - criteria);
                                double maxPrice = (securityPrice + criteria);
                                if (price < minPrice || price > maxPrice) {
                                    String criteriaMsg = "";
                                    DecimalFormat df = new DecimalFormat(".##");
                                    if (price < minPrice) {
                                        criteriaMsg = "Price cannot be lower than " + df.format(minPrice);
                                    }
                                    if (price > maxPrice) {
                                        criteriaMsg = "Price cannot be greater than " + df.format(maxPrice);
                                    }
                                    this.ussdsession.saveSessionMode(1);
                                    return this.ussdsession.buildUSSDResponse(this.view.securityPrice(this.securityType, this.securitySelected, false, true, criteriaMsg), 2);
                                }
                                break;
                        }
                        this.addFieldtoBuyFormAndSave("price", priceEntered);
                        this.ussdsession.saveSessionMode(1);
                        this.ussdsession.saveUSSDSession(handlersessionlevel + 10);
                        return this.orderOptionsSelector();
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(this.view.securityPrice(this.securityType, this.securitySelected, true, false, ""), 2);
            }
            return orderOptionsSelector();
        }
        return orderOptionsSelector();
    }

    public USSDResponse orderOptionsSelector() throws FileNotFoundException {
        System.out.println("orderOptionsSelector method session level ==> " + this.ussdsession.getSessionLevel());
        if (this.ussdsession.getSessionLevel() < handlersessionlevel + 12) {

            JSONArray optionSelectors = LuseServiceCenter.optionSelectors("orderDetails", this.buyOrSell);
            this.header = this.view.securityOptionsHeader(this.securityType, this.securitySelected);
            this.listitems = getOptions(optionSelectors);
            PaginationHelper paginationHelper = null;

            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel + 10:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no information found.");
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 11);
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 11:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no information found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                        case 1:
                            this.selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, optionSelectors);
                            switch ((String) this.selected.get("id")) {
                                case "1":
                                    this.ussdsession.resetList();
                                    this.ussdsession.saveSessionMode(1);
                                    this.ussdsession.saveUSSDSession(handlersessionlevel + 12);

                                    this.optionSelectedForm.put("action", "viewOrderDetails");
                                    this.formsession.getFormData().put(this.optionSelectedFormName, optionSelectedForm);
                                    this.ussdsession.saveFormSession(this.formsession);

                                    return this.orderDetails(this.optionSelectedForm.get("action").toString());
                                case "2":
                                    this.ussdsession.resetList();
                                    this.ussdsession.saveSessionMode(1);
                                    this.ussdsession.saveUSSDSession(handlersessionlevel + 12);

                                    this.optionSelectedForm.put("action", "");
                                    this.formsession.getFormData().put(this.optionSelectedFormName, optionSelectedForm);
                                    this.ussdsession.saveFormSession(this.formsession);

                                    return this.orderDetails(this.optionSelectedForm.get("action").toString());
                            }
                            break;
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
        }
        return this.orderDetails(this.optionSelectedForm.get("action").toString());
    }

    public USSDResponse orderDetails(String action) throws FileNotFoundException {
        System.out.println("orderDetails method session level ==> " + this.ussdsession.getSessionLevel());
        SecurityOrder order = this.saveSecurityOrder();
        if (action.equals("viewOrderDetails")) {

            this.header = this.view.securityDetailsHeader(this.securityType, this.securitySelected);
            this.listitems = this.view.orderDetails(this.securityType, this.securitySelected, order);

            DetailPaginationHelper paginationHelper = null;
            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel + 12:
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 13);
                    this.ussdsession.saveSessionMode(1);
                    paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, this.header, "", "Sorry no information found.");
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 13:
                    paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, this.header, "", "Sorry no information found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
        }
        return this.proccessOrder(order);
    }

    public USSDResponse proccessOrder(SecurityOrder order) throws FileNotFoundException {
        System.out.println("proccessOrder session level ==> " + this.ussdsession.getSessionLevel());
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel + 12:
                JSONObject payload = new JSONObject();
                payload.put("subscriberId", order.getSubscriberId());
                payload.put("securityType", order.getSecurityType());
                payload.put("securityAtsId", order.getSecurityAtsId());
                payload.put("securitySymbol", order.getSecuritySymbol());
                payload.put("maturityDate", order.getMaturityDate());
                payload.put("subscriberAtsId", order.getSubscriberAtsId());
                payload.put("brokerAtsId", order.getBrokerAtsId());
                payload.put("volume", order.getVolume());
                payload.put("price", order.getPrice());
                payload.put("buyOrSell", order.getBuyOrSell());
                payload.put("orderType", order.getOrderType());
                payload.put("orderCapacity", order.getOrderCapacity());
                payload.put("comment", order.getComment());

                this.ussdsession.saveUSSDSession(-1);
                this.ussdsession.saveSessionMode(0);
                this.ussdsession.clearOptions();
                this.formsession.clearFormData(this.optionSelectedFormName);
                this.formsession.clearFormData(FORM_NAME);
                this.formsession.clearFormData(this.securityType);
                LuseServiceCenter.orderSecurity(payload, this.ussdsession.getUSSDSessionHelper().getMongoDB(), this.mobilesession.getMSISDN());
                return this.ussdsession.buildUSSDResponse(this.view.securityOrderResponse(), 2);
            default:
                this.ussdsession.saveUSSDSession(-1);
                this.ussdsession.saveSessionMode(0);
                this.ussdsession.clearOptions();
                this.formsession.clearFormData(this.optionSelectedFormName);
                this.formsession.clearFormData(FORM_NAME);
                this.formsession.clearFormData(this.securityType);
                return this.ussdsession.buildUSSDResponse(this.view.securityOrderResponse(), 2);
        }
    }

    //    Miscellaneous functions
    public void setSecurityFormParametersAndSave() throws FileNotFoundException {
        this.securityForm.put("id", this.selected.get("id"));
        this.securityForm.put("csdId", this.selected.get("csdId"));
        this.securityForm.put("atsId", this.selected.get("atsId"));
        this.securityForm.put("securityType", this.selected.get("securityType"));
        this.securityForm.put("securityCode", this.selected.getOrDefault("securityCode", ""));
        this.securityForm.put("marketCap", this.selected.get("marketCap"));
        this.securityForm.put("changeInPrice", this.selected.get("changeInPrice"));
        this.securityForm.put("settlementPrice", this.selected.get("settlementPrice"));
        this.securityForm.put("openInterest", this.selected.get("openInterest"));
        this.securityForm.put("closingPrice", this.selected.get("closingPrice"));
        this.securityForm.put("name", this.selected.getOrDefault("name", ""));
        this.securityForm.put("description", this.selected.get("description"));
        this.securityForm.put("symbol", this.selected.get("symbol"));
        this.securityForm.put("issueDate", this.selected.get("issueDate"));
        this.securityForm.put("maturityDate", this.selected.get("maturityDate"));
        this.securityForm.put("couponRate", this.selected.getOrDefault("couponRate", ""));
        this.securityForm.put("currency", this.selected.get("currency"));

        this.formsession.getFormData().put(FORM_NAME, this.securityForm);
        this.ussdsession.saveFormSession(this.formsession);

        this.saveSecurity();
    }

    public void saveSecurity() throws FileNotFoundException {
        Security security = new Security();

        System.out.println("security form ==> " + this.securityForm);
        security.setId((String) this.securityForm.getOrDefault("id", ""));
        security.setMsisdn(this.mobilesession.getMSISDN());
        security.setCsdId((String) this.securityForm.getOrDefault("csdId", ""));
        security.setAtsId((String) this.securityForm.getOrDefault("atsId", ""));
        security.setSecurityType((String) this.securityForm.getOrDefault("securityType", ""));
        security.setSecurityCode(this.securityForm.getOrDefault("securityCode", "").toString());
        security.setName((String) this.securityForm.getOrDefault("name", ""));
        security.setDescription((String) this.securityForm.getOrDefault("description", ""));
        security.setMarketCap((String) this.securityForm.getOrDefault("marketCap", ""));
        security.setClosingPrice((String) this.securityForm.getOrDefault("closingPrice", ""));
        security.setChangeInPrice((String) this.securityForm.getOrDefault("changeInPrice", ""));
        security.setSettlementPrice((String) this.securityForm.getOrDefault("settlementPrice", ""));
        security.setOpenInterest((String) this.securityForm.getOrDefault("openInterest", ""));
        security.setSymbol((String) this.securityForm.getOrDefault("symbol", ""));
        security.setIssueDate((String) this.securityForm.getOrDefault("issueDate", ""));
        security.setMaturityDate((String) this.securityForm.getOrDefault("maturityDate", ""));
        security.setCouponRate((String) this.securityForm.getOrDefault("couponRate", ""));
        security.setCurrency((String) this.securityForm.getOrDefault("currency", ""));

        this.securityModel.save(security);
    }

    public void addFieldtoBuyFormAndSave(String field, String data) {
        this.buyForm.put(field, data);
        this.formsession.getFormData().put(this.securityType, this.buyForm);
        this.ussdsession.saveFormSession(this.formsession);
    }

    public Boolean validateInput(String type, String data) {
        boolean result = false;
        switch (type) {
            case "number":
                result = data.matches("[0-9]+");
                break;
        }
        return result;
    }

    public SecurityOrder saveSecurityOrder() {
        System.out.println("saveSecurityOrder processing...");

        ClientModel clientModel = new ClientModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
        Client client = clientModel.findOne(this.mobilesession.getMSISDN());

        SecurityOrderModel orderModelExists = new SecurityOrderModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
        SecurityOrder orderExists = orderModelExists.findOne(client.getId());
        System.out.println("order exists ==> " + orderExists);

        SecurityOrderModel orderModel = new SecurityOrderModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
        SecurityOrder order = new SecurityOrder();

        String subscriberId = client.getId();
        String securityType = this.securityType;
        String securityAtsId = this.securityForm.get("atsId").toString();
        String securitySymbol = this.securityForm.get("securityCode").toString();
        String maturityDate = this.securityForm.get("maturityDate").toString();
        String subscriberAtsId = this.buyForm.get("brokerAtsId").toString();
        String brokerAtsId = this.buyForm.get("brokerId").toString();
        String volume = this.buyForm.get("volume").toString();
        String price = this.buyForm.get("price").toString();
        String buyOrSell = this.buyOrSell;
        String orderType = "2";
        String orderCapacity = "A";
        String comment = "buy test";

        order.setSubscriberId(subscriberId);
        order.setSubscriberAtsId(subscriberAtsId);
        order.setSecurityType(securityType);
        order.setBrokerAtsId(brokerAtsId);
        order.setSecurityAtsId(securityAtsId);
        order.setSecuritySymbol(securitySymbol);
        order.setSecurityType(securityType);
        order.setMaturityDate(maturityDate);
        order.setVolume(volume);
        order.setPrice(price);
        order.setBuyOrSell(buyOrSell);
        order.setOrderCapacity(orderCapacity);
        order.setOrderType(orderType);
        order.setComment(comment);

        System.out.println("order ==> " + order);
        orderModel.save(order);

        return orderModel.findOne(client.getId());
    }

    public ArrayList<ListItem> getSecuritiesList(JSONArray items) {
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
//        System.out.println("list ==> "+list);
        return list;
    }

    public JSONObject getSelectedItem(int arrayIndex, JSONArray item) {
//        System.out.println("selected item ==> " + item);
        JSONObject o = (JSONObject) item.get(arrayIndex);
        return o;
    }

    public ArrayList<ListItem> getOptions(JSONArray items) {
        ArrayList<ListItem> list = new ArrayList<>();
        try {
            for (int i = 0; i < items.size(); i++) {
                JSONObject o = (JSONObject) items.get(i);
                String id = (String) o.get("id");
                String option = (String) o.get("option");
                ListItem listitem = new ListItem(id, option);
                list.add(i, listitem);
            }
        } catch (Exception exception) {
        }
        for (ListItem str : list) {
            System.out.println(str);
        }
        return list;
    }

    public ArrayList<ListItem> getClientBrokerList(JSONArray items) {
        ArrayList<ListItem> list = new ArrayList<>();
        try {
            for (int i = 0; i < items.size(); i++) {
                JSONObject o = (JSONObject) items.get(i);
                String id = (String) o.getOrDefault("brokerId", "");
                String option = (String) o.getOrDefault("brokerId", "");
                ListItem listitem = new ListItem(id, option);
                list.add(i, listitem);
            }
        } catch (Exception e) {
            System.out.println("error ==> " + e.getMessage());
        }
        return list;
    }
}
