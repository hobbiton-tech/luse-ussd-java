package LuseDma.ussd.handlers;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.helpers.PaginationHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.pojos.FormSession;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.MobileSession;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.pojos.luse.SecurityOrder;
import LuseDma.ussd.views.AccountManagerView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import static LuseDma.ussd.views.SecurityExploreView.FORM_NAME;

public class AddBrokerHandler {

    private final int handlersessionlevel = 2;
    private USSDSession ussdsession = null;
    private ArrayList<ListItem> listitems = null;
    private MobileSession mobilesession = null;
    private FormSession formsession = null;
    private JSONObject selected;
    private final String FORM_NAME = "addBrokerForm";
    private final String header = "Select Broker\n";
    private Map<String, Object> brokerForm;
    private AccountManagerView view = new  AccountManagerView();

    public AddBrokerHandler(USSDSession ussdsession){
        this.ussdsession = ussdsession;
        this.mobilesession = ussdsession.getMobileSession();
        this.formsession = ussdsession.getMobileSession().getFormSession();
        this.brokerForm = this.mobilesession.getFormSession().getForm(this.FORM_NAME);
    }

    public USSDResponse runSession() throws FileNotFoundException {
        if (this.ussdsession.getSessionLevel() < (handlersessionlevel + 2)) {

            JSONArray brokersList = LuseServiceCenter.getAllBrokers( this.ussdsession.getMSISDN(), this.ussdsession.getUSSDSessionHelper().getMongoDB());
            this.listitems = this.getAllBrokers(brokersList);

            PaginationHelper paginationHelper = null;

            switch (this.ussdsession.getSessionLevel()) {
                case handlersessionlevel:
                    this.ussdsession.resetList();
                    this.ussdsession.saveUSSDSession(handlersessionlevel + 1);
                    this.ussdsession.saveSessionMode(1);
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no brokers found.");
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
                case handlersessionlevel + 1:
                    paginationHelper = new PaginationHelper(this.ussdsession, this.listitems, this.header, false, false, "Sorry no brokers found.");
                    switch (paginationHelper.getListPageActionHandle()) {
                        case 1:
                            this.selected = getSelectedItem(Integer.parseInt(this.ussdsession.getUserInput()) - 1, brokersList);

                            this.addFieldtoBrokerFormAndSave("brokerId", (String) this.selected.get("atsId"));

                            this.ussdsession.resetList();
                            this.ussdsession.saveSessionMode(1);
                            this.ussdsession.saveUSSDSession(handlersessionlevel + 2);
                            return this.addBroker();
                    }
                    this.ussdsession.saveSessionMode(1);
                    return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            }
            return this.addBroker();
        }
        return this.addBroker();
    }

    public USSDResponse addBroker() throws FileNotFoundException {
        System.out.println("proccessOrder session level ==> " + this.ussdsession.getSessionLevel());
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel + 2:
                JSONObject payload = new JSONObject();
                payload.put("brokerId", this.brokerForm.getOrDefault("brokerId", ""));
                payload.put("csdId", AppConfigHelper.LUSE_CLIENT_CSDID);
                payload.put("subscriberId", AppConfigHelper.LUSE_CLIENT_ID);


                this.ussdsession.saveUSSDSession(-1);
                this.ussdsession.saveSessionMode(0);
                this.ussdsession.clearOptions();
                this.formsession.clearFormData(this.FORM_NAME);

                LuseServiceCenter.addBroker(payload, this.ussdsession.getUSSDSessionHelper().getMongoDB(), this.mobilesession.getMSISDN());
                return this.ussdsession.buildUSSDResponse(this.view.addBrokerResponse(), 2);
            default:
                this.ussdsession.saveUSSDSession(-1);
                this.ussdsession.saveSessionMode(0);
                this.ussdsession.clearOptions();
                this.formsession.clearFormData(this.FORM_NAME);
                LuseServiceCenter.deleteUserStoredInfo(this.ussdsession.getMSISDN(), this.ussdsession.getUSSDSessionHelper().getMongoDB());
                return this.ussdsession.buildUSSDResponse(this.view.addBrokerResponse(), 2);
        }
    }


    public void addFieldtoBrokerFormAndSave(String field, String data) {
        this.brokerForm.put(field, data);
        this.formsession.getFormData().put(this.FORM_NAME, this.brokerForm);
        this.ussdsession.saveFormSession(this.formsession);
    }

    public ArrayList<ListItem> getAllBrokers(JSONArray items) {
        ArrayList<ListItem> list = new ArrayList<>();
        try {
            for (int i = 0; i < items.size(); i++) {
                JSONObject o = (JSONObject) items.get(i);
                String id = (String) o.getOrDefault("atsId", "");
                String option = (String) o.getOrDefault("name", "");
                ListItem listitem = new ListItem(id, option);
                list.add(i, listitem);
            }
        } catch (Exception e) {
            System.out.println("error ==> " + e.getMessage());
        }
        return list;
    }

    public JSONObject getSelectedItem(int arrayIndex, JSONArray item) {
//        System.out.println("selected item ==> " + item);
        JSONObject o = (JSONObject) item.get(arrayIndex);
        return o;
    }

}
