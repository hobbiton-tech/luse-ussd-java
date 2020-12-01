package LuseDma.ussd.models.luse;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.models.MongoDBOld;
import LuseDma.ussd.models.MongoModelOld;
import LuseDma.ussd.pojos.luse.Security;
import org.jongo.MongoCollection;

public class SecurityModel extends MongoModelOld {

    private final MongoDBOld conn;
    private MongoCollection collection;
    private final String collectionName = "securitySelected";

    public SecurityModel(MongoDBOld conn) {
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

    public Boolean save(Security item) {
        try {
            String query = "{\"msisdn\": \"" + item.getMsisdn() + "\"}";
            System.out.println("security save query ==> "+query);
            this.collection.remove(query);
            this.collection.save(item);
            System.out.println("security object saved");
            return true;
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return false;
        } catch (Exception e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return true;
        }
    }

    public Security findOne(String csdId) {
        Security session = null;
        String query = "{\"csdId\": \"" + csdId + "\"}";
        try {
            ;
            session = this.collection.findOne(query).as(Security.class);
//            System.out.println("session obj ==> "+session);
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        } catch (Exception e) {
            AppConfigHelper.logger.error("SecurityModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        }
        return session;
    }
}

