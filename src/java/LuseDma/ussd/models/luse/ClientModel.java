package LuseDma.ussd.models.luse;

import LuseDma.ussd.common.AppConfigHelper;
import LuseDma.ussd.models.MongoDBOld;
import LuseDma.ussd.models.MongoModelOld;
import LuseDma.ussd.pojos.luse.Client;
import org.jongo.MongoCollection;

public class ClientModel extends MongoModelOld {

    private final MongoDBOld conn;
    private MongoCollection collection;
    private final String collectionName = "clients";

    public ClientModel(MongoDBOld conn) {
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

    public Boolean save(Client client) {
        try {
            String query = "{\"msisdn\": \"" + client.getMsisdn() + "\"}";
            this.collection.remove(query);
            this.collection.save(client);
            return true;
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("ClientModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return false;
        } catch (Exception e) {
            AppConfigHelper.logger.error("ClientModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
            return true;
        }
    }

    public Client findOne(String msisdn) {
        Client session = null;
        String query = "{\"msisdn\": \"" + msisdn + "\"}";
        try {
            session = this.collection.findOne(query).as(Client.class);
            System.out.println("client obj ==> " + session);
        } catch (OutOfMemoryError e) {
            AppConfigHelper.logger.error("ClientModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        } catch (Exception e) {
            AppConfigHelper.logger.error("ClientModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
        }
        return session;
    }
}

