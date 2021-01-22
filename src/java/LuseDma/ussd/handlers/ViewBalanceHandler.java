package LuseDma.ussd.handlers;

import LuseDma.ussd.helpers.DetailPaginationHelper;
import LuseDma.ussd.helpers.USSDSession;
import LuseDma.ussd.models.luse.ClientModel;
import LuseDma.ussd.pojos.ListItem;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.pojos.luse.Client;
import LuseDma.ussd.views.AccountManagerView;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ViewBalanceHandler {

    private final int handlersessionlevel = 1;
    private USSDSession ussdsession = null;
    private ArrayList<ListItem> listitems = null;
    private AccountManagerView view = new  AccountManagerView();

    public ViewBalanceHandler(USSDSession ussdsession){
        this.ussdsession = ussdsession;
    }

    public USSDResponse runSession(){
        String header = "Account Balance\n";

        ClientModel clientModel = new ClientModel(this.ussdsession.getUSSDSessionHelper().getMongoDB());
        Client client = clientModel.findOne(this.ussdsession.getMSISDN());

        this.listitems = this.view.accountBalanceDetails(client);

        DetailPaginationHelper paginationHelper = null;
        switch (this.ussdsession.getSessionLevel()) {
            case handlersessionlevel:
                this.ussdsession.resetList();
                this.ussdsession.saveUSSDSession(handlersessionlevel +1);
                this.ussdsession.saveSessionMode(1);
                paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, header, "", "Sorry no information found.");
                return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
            case handlersessionlevel + 1:
                paginationHelper = new DetailPaginationHelper(this.ussdsession, this.listitems, header, "", "Sorry no information found.");
                switch (paginationHelper.getListPageActionHandle()) {
                }
                this.ussdsession.saveSessionMode(1);
                return this.ussdsession.buildUSSDResponse(paginationHelper.getListPage(), 2);
        }
        return runSession();
    }

}
