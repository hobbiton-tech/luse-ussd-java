package LuseDma.ussd.handlers;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.helpers.PaginationHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.models.luse.ClientModel;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.pojos.luse.Client;
import LuseDma.ussd.pojos.luse.SecurityOrder;
import LuseDma.ussd.views.FundMenuView;
import LuseDma.ussd.views.SecurityExploreView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import static LuseDma.ussd.views.FundMenuView.FORM_NAME;

public class FundMenuHandler {

    private final int handlersessionlevel = 0;
    private USSDSession ussdsession = null;
    private MobileSession mobilesession = null;
    private FormSession formsession = null;
    private String msisdn;
    private Map<String, Object> fundForm;
    private String header = null;
    private ArrayList<ListItem> listitems = null;
    private JSONObject selected;

    private FundMenuView view = new FundMenuView();

    public FundMenuHandler(USSDSession ussdsession) {
        this.ussdsession = ussdsession;
        this.mobilesession = ussdsession.getMobileSession();
        this.msisdn = ussdsession.getMobileSession().getMSISDN();
        this.formsession = ussdsession.getMobileSession().getFormSession();
        this.fundForm = this.mobilesession.getFormSession().getForm(FORM_NAME);
    }

    public USSDResponse runSession() throws FileNotFoundException {
        System.out.println("runSession session level ==> " + this.ussdsession.getSessionLevel());
        if (this.ussdsession.getSessionLevel() < handlersessionlevel + 2) {
            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel:
                    this.ussdsession.saveSessionMode(1);
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                    return this.ussdsession.buildUSSDResponse(this.view.amountView(false), 2);
                case handlersessionlevel + 1:
                    if (isValidInput(this.ussdsession.getUserInput())) {

                        this.fundForm.put("subscriberId", AppConfigHelper.LUSE_CLIENT_ID);
                        this.fundForm.put("amount", this.ussdsession.getUserInput());
                        this.fundForm.put("msisdn", this.msisdn);
                        this.formsession.getFormData().put(FundMenuView.FORM_NAME, this.fundForm);
                        this.ussdsession.saveFormSession(this.formsession);

                        this.ussdsession.saveSessionMode(1);
                        this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
                        return this.fundConfirm();
                    }
                    return this.ussdsession.buildUSSDResponse(this.view.amountView(true), 2);
            }
        }
        return this.fundConfirm();
    }

    public USSDResponse fundConfirm() throws FileNotFoundException {
        System.out.println("fundConfirm session level ==> " + this.ussdsession.getSessionLevel());
        if (this.ussdsession.getSessionLevel() < handlersessionlevel + 4) {
            this.header = this.view.fundDetailsView(this.fundForm.getOrDefault("amount", "").toString());
            JSONArray confirmOption = LuseServiceCenter.confirmOption();
            this.listitems = this.getOptions(confirmOption);
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
                            this.selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, confirmOption);
                            System.out.println("selected ==> "+this.selected);
                            switch ((String) this.selected.get("id")) {
                                case "1":
                                    this.ussdsession.resetList();
                                    this.ussdsession.saveSessionMode(1);
                                    this.ussdsession.saveUSSDSession(handlersessionlevel + 4);
                                    return this.processFund();
                            }
                            break;
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
        }
        this.ussdsession.saveSessionMode(1);
        return this.processFund();
    }

    public USSDResponse processFund() throws FileNotFoundException {
        System.out.println("processFund session level ==> " + this.ussdsession.getSessionLevel());
        MainMenuHandler mainMenuHandler = null;
        switch (this.ussdsession.getSessionLevel()){
            case handlersessionlevel+4:
                System.out.println("funding account...");
                ClientModel clientModel = new ClientModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
                Client client = clientModel.findOne(this.msisdn);

                JSONObject payload = new JSONObject();
                payload.put("amount", this.fundForm.getOrDefault("amount", "").toString());
                payload.put("msisdn", this.fundForm.getOrDefault("msisdn", "").toString());
                payload.put("paymentMethod", "MOMO");
                payload.put("paymentChannel", "MTN");
                payload.put("brokerId", client.getAtsAccountsBrokerId());
                payload.put("subscriberId", client.getId());
                payload.put("currency", "ZMW");

                LuseServiceCenter.fundWallet(payload, this.ussdsession.getUSSDSessionHelper().getMongoDB(), this.msisdn);
                this.ussdsession.saveSessionMode(0);
                this.ussdsession.saveUSSDSession(-1);
                return this.ussdsession.buildUSSDResponse(this.view.fundResponse(), 2);
        }
        this.ussdsession.saveSessionMode(0);
        this.ussdsession.saveUSSDSession(-1);
        return mainMenuHandler.runSession();
    }

    public boolean isValidInput(String input) {
        return input.matches("[0-9]+");
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

    public JSONObject getSelectedItem(int arrayIndex, JSONArray item) {
//        System.out.println("selected item ==> " + item);
        JSONObject o = (JSONObject) item.get(arrayIndex);
        return o;
    }

}
