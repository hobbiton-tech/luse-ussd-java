package LuseDma.ussd.models.luse;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.models.MongoDBOld;
import LuseDma.ussd.models.MongoModelOld;
import LuseDma.ussd.pojos.luse.FundAccount;
import LuseDma.ussd.pojos.luse.Security;
import LuseDma.ussd.pojos.luse.SecurityOrder;
import org.jongo.MongoCollection;

public class FundAccountModel extends MongoModelOld {

    private final MongoDBOld conn;
    private MongoCollection collection;
    private final String collectionName = "securityOrder";

    public FundAccountModel(MongoDBOld conn) {
        super(conn);
        this.conn = conn;
        try {
            this.collection = this.jongo.getCollection(this.collectionName);
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("SecurityModel - constructor(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        } catch (Exception e) {
            AppConfigHelper.logger.error("SecurityModel - constructor(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        }
    }

    public Boolean save(FundAccount transaction) {
        try {
            String query = "{\"subscriberId\": \"" + transaction.getSubscriberId() + "\"}";
            this.collection.remove(query);
            this.collection.save(transaction);
            System.out.println("object saved");
            return true;
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return false;
        } catch (Exception e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return true;
        }
    }

    public FundAccount findOne(String subscriberId) {
        FundAccount session = null;
        String query = "{\"subscriberId\": \"" + subscriberId + "\"}";
        System.out.println("query ==> "+query);
        try {
            session = this.collection.findOne(query).as(FundAccount.class);
            System.out.println("session obj ==> "+session);
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        } catch (Exception e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        }
        return session;
    }
}

