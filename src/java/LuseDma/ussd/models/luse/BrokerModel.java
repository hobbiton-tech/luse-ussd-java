package LuseDma.ussd.models.luse;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.models.MongoDBOld;
import LuseDma.ussd.models.MongoModelOld;
import LuseDma.ussd.pojos.luse.Broker;
import org.jongo.MongoCollection;

public class BrokerModel extends MongoModelOld {

    private final MongoDBOld conn;
    private MongoCollection collection;
    private final String collectionName = "brokers";

    public BrokerModel(MongoDBOld conn) {
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

    public Boolean save(Broker broker) {
        try {
            Broker result = this.findOne(broker.getAtsId());
            if(result == null){
                this.collection.save(broker);
            }
            return true;
        }
        catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("BrokerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return false;
        } catch (Exception e) {
            AppConfigHelper.logger.error("BrokerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return true;
        }
    }

    public Broker findOne(String atsId) {
        Broker session = null;
        String query = "{\"atsId\": \""+atsId+"\"}";
        try {
            ;
            session = this.collection.findOne(query).as(Broker.class);
//            System.out.println("session obj ==> "+session);
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("BrokerModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        } catch (Exception e) {
            AppConfigHelper.logger.error("BrokerModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        }
        return session;
    }
}

