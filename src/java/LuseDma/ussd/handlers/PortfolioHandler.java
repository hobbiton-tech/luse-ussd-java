package LuseDma.ussd.handlers;

import LuseDma.ussd.helpers.DetailPaginationHelper;
import LuseDma.ussd.helpers.PaginationHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.models.luse.ClientModel;
import LuseDma.ussd.models.luse.SecurityModel;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.pojos.luse.Client;
import LuseDma.ussd.views.PorfolioView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class PortfolioHandler {

    private final int handlersessionlevel = 0;
    private USSDSession ussdsession = null;
    private MobileSession mobilesession = null;
    private FormSession formsession = null;
    private SecurityModel securityModel = null;
    private ArrayList<ListItem> listitems = null;
    private String header = null;
    private String msisdn;
    private Map<String, Object> portfolioForm;
    private String formName;
    private JSONObject selected;

    PorfolioView view = new PorfolioView();

    public PortfolioHandler(USSDSession ussdsession) {
        this.ussdsession = ussdsession;
        this.formName = "portfolio_form";
        this.mobilesession = ussdsession.getMobileSession();
        this.msisdn = ussdsession.getMobileSession().getMSISDN();
        this.formsession = ussdsession.getMobileSession().getFormSession();
        this.portfolioForm = this.mobilesession.getFormSession().getForm(this.formName);
    }

    public USSDResponse runSession() {
        System.out.println("run method session level ==> " + this.ussdsession.getSessionLevel());
        if (this.ussdsession.getSessionLevel() < (handlersessionlevel + 2)) {
            Client client = new ClientModel(this.ussdsession.getUSSDSessionHelper().getMongoDB()).findOne(this.msisdn);
            JSONArray holdingsList = LuseServiceCenter.getClientPorfolio(client.getId(), client.getCsdId(), this.ussdsession.getMSISDN(), this.ussdsession.getUSSDSessionHelper().getMongoDB());
            this.header = this.view.holdingMenuHeader();
            this.listitems = getHoldingsList(holdingsList);
            PaginationHelper paginationHelper = null;

            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel:
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                    this.ussdsession.saveSessionMode(1);
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no security holdings found.");
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 1:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no security holdings found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                        case 1:
                            this.selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, holdingsList);
                            System.out.println("selected ===> " + this.selected);

                            this.portfolioForm.put("subscriberId", client.getId());
                            System.out.println("passed parameter...1");
                            this.portfolioForm.put("csdId", this.selected.getOrDefault("csdId", "").toString());
                            System.out.println("passed parameter...2");
                            this.portfolioForm.put("securityCode", this.selected.getOrDefault("securityCode", "").toString());
                            System.out.println("passed parameter...3");
//                            this.portfolioForm.put("securityName", String.valueOf(this.selected.getOrDefault("securityName", "").toString()));
                            System.out.println("passed parameter...4");
                            this.portfolioForm.put("shortLongIndicator", this.selected.getOrDefault("shortLongIndicator", "").toString());
                            System.out.println("passed parameter...5");
                            this.portfolioForm.put("holdingsBalance", this.selected.getOrDefault("holdingsBalance", "").toString());
                            System.out.println("passed parameter...6");
                            this.portfolioForm.put("availableBalance", this.selected.getOrDefault("availableBalance", "").toString());
                            System.out.println("passed parameter...1");

                            System.out.println("saving form...");
                            this.formsession.getFormData().put(this.formName, this.portfolioForm);
                            this.ussdsession.saveFormSession(this.formsession);

                            this.ussdsession.resetList();
                            this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
                            this.ussdsession.saveSessionMode(1);

                            return this.holdingsDetail(this.portfolioForm.getOrDefault("csdId", "").toString());
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
            return this.holdingsDetail(this.portfolioForm.getOrDefault("csdId", "").toString());
        }
        return this.holdingsDetail(this.portfolioForm.getOrDefault("csdId", "").toString());
    }

    public USSDResponse holdingsDetail(String holdingsCsdId) {

        this.header = this.view.holdingsDetailHeader();
        JSONObject holding = new JSONObject();
        holding.put("subscriberId", this.portfolioForm.getOrDefault("subscriberId", "").toString());
        holding.put("csdId", this.portfolioForm.getOrDefault("csdId", "").toString());
        holding.put("securityCode", this.portfolioForm.getOrDefault("securityCode", "").toString());
        holding.put("securityName", this.portfolioForm.getOrDefault("securityName", "").toString());
        holding.put("shortLongIndicator", this.portfolioForm.getOrDefault("shortLongIndicator", "").toString());
        holding.put("holdingsBalance", this.portfolioForm.getOrDefault("holdingsBalance", "").toString());
        holding.put("availableBalance", this.portfolioForm.getOrDefault("availableBalance", "").toString());

        this.listitems = this.view.holdingsDetail(holding);

        DetailPaginationHelper paginationHelper = null;
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel + 2:
                this.ussdsession.resetList();
                this.ussdsession.saveUSSDSession(handlersessionlevel + 3);
                this.ussdsession.saveSessionMode(1);
                paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, this.header, "", "Sorry no information found.");
                return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            case handlersessionlevel + 3:
                paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, this.header, "", "Sorry no information found.");
                switch (paginationHelper.getListPageActionHandle()) {
                }
                this.ussdsession.saveSessionMode(1);
                return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
        }
        return runSession();
    }

    public ArrayList<ListItem> getHoldingsList(JSONArray items) {
        ArrayList<ListItem> list = new ArrayList<>();
        try {
            for (int i = 0; i < items.size(); i++) {
                JSONObject o = (JSONObject) items.get(i);
                String id = (String) o.getOrDefault("csdId", "");
                String securityCode = (String) o.getOrDefault("securityCode", "");
                ListItem listitem = new ListItem(id, securityCode);
                list.add(listitem);
            }
        } catch (Exception exception) {
        }
        return list;
    }

    public JSONObject getSelectedItem(int arrayIndex, JSONArray item) {
        JSONObject o = (JSONObject) item.get(arrayIndex);
        return o;
    }
}
