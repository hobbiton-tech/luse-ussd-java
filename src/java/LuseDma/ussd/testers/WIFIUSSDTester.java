package LuseDma.ussd.testers;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


import LuseDma.ussd.handlers.USSDEngineCoreHandler;

import LuseDma.ussd.helpers.USSDSessionHelper;
import LuseDma.ussd.pojos.USSDResponse;
import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.models.MongoDBOld;



public class WIFIUSSDTester {
    private static String SessionID = "115";
    private static String AppID = "677";
    private static String Shortcode = "677";
    private static String SessionStatus = "1";
    private static String MSISDN = "260975000000";//260977533618
    private static String USSDString = "*677#";


    public static void main(String[] args) {
        AppConfigHelper config = new AppConfigHelper();
        MongoDBOld mongodb = new MongoDBOld();
        try {
            USSDSessionHelper ussdsessionhelper = new USSDSessionHelper(MSISDN, mongodb);
            ussdsessionhelper.setSessionID(SessionID);
            ussdsessionhelper.setAppID(AppID);
            ussdsessionhelper.setShortCode(Shortcode);
            ussdsessionhelper.setSessionStatus(Integer.parseInt(SessionStatus));
            ussdsessionhelper.setNetworkID(3);
            ussdsessionhelper.setUserInput(parseUSSDBody(Integer.parseInt(SessionStatus), USSDString));
            USSDEngineCoreHandler mUSSDEngineCoreHandler = new USSDEngineCoreHandler(ussdsessionhelper);
            USSDResponse ussdresponse = mUSSDEngineCoreHandler.runUSSDSessionHandler(Integer.valueOf(-1), Integer.valueOf(1));
            System.out.println(getUSSDResponse(ussdresponse));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mongodb != null) mongodb.closeDb();
        }
    }

    public static String parseUSSDBody(int sessionstatus, String value) {
        if (sessionstatus == 1) {
            String v = value.substring(AppConfigHelper.USSD_MSG_INDEX);
            v = AppConfigHelper.parseUSSDHashChar(v);
            return v;
        }
        return value.substring(AppConfigHelper.USSD_MSG_INDEX);
    }

    public static String getUSSDResponse(USSDResponse ussdresponse) {
        String sessionstatus = "1";
        switch (ussdresponse.getSessionStatus()) {
            case 1:
                sessionstatus = "1";
                break;
            case 2:
                sessionstatus = "2";
                break;
            case 3:
                sessionstatus = "1";
                break;
        }
        try {
            String message = ussdresponse.getResponseMessage();
            System.out.println("USSD Response  => Bytes:" + (message.getBytes("ASCII")).length + ", UTF Characters:" + message.length() + ", GSM Characters:" + ((message.getBytes()).length * 8 / 7));
        } catch (Exception ex) {
            Logger.getLogger(WIFIUSSDTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "&TransId=" + ussdresponse.getUSSDSessionHelper().getSessionID() + "&RequestType=" + sessionstatus + "&MSISDN=" + ussdresponse.getUSSDSessionHelper().getMSISDN() + "&AppId=" + ussdresponse.getUSSDSessionHelper().getAppID() + "&USSDString=\n" + ussdresponse.getResponseMessage();
    }
}
