package LuseDma.ussd.models.luse;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.models.MongoDBOld;
import LuseDma.ussd.models.MongoModelOld;
import LuseDma.ussd.pojos.luse.SecurityHodling;
import org.jongo.MongoCollection;

public class SecurityHoldingsModel extends MongoModelOld {

    private final MongoDBOld conn;
    private MongoCollection collection;
    private final String collectionName = "securitySelected";

    public SecurityHoldingsModel(MongoDBOld conn) {
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

    public Boolean save(SecurityHodling holding) {
        try {
            String query = "{\"subscriberId\": \"" + holding.getSubscriberId() + "\"}";
            System.out.println("holding save query ==> "+query);
            this.collection.remove(query);
            this.collection.save(holding);
            System.out.println("holding object saved");
            return true;
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return false;
        } catch (Exception e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return true;
        }
    }

    public SecurityHodling findOne(String csdId) {
        SecurityHodling session = null;
        String query = "{\"csdId\": \"" + csdId + "\"}";
        try {
            ;
            session = this.collection.findOne(query).as(SecurityHodling.class);
//            System.out.println("session obj ==> "+session);
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        } catch (Exception e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        }
        return session;
    }
}

