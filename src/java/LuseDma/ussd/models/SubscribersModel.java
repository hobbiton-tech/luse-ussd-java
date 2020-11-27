 package LuseDma.ussd.models;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.kyc.Subscriber;
 import org.jongo.MongoCollection;

 public class SubscribersModel extends MongoModelOld
 {
   private final MongoDBOld conn;
   private MongoCollection collection;
   private final String collectionName = AppConfigHelper.SUBSCRIBERS_COLLECTION;
   
   public SubscribersModel(MongoDBOld conn) {
     super(conn);
     this.conn = conn;
     try {
       this.collection = this.jongo.getCollection(this.collectionName);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("SubscriberModel - constructor(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("SubscriberModel - constructor(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
   }
   public Boolean exists(String query) {
     try {
       long count = this.collection.count(query);
       if (count >= 1L) {
         return Boolean.valueOf(true);
       }
       return Boolean.valueOf(false);
     }
     catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("SubscriberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } catch (Exception e) {
       AppConfigHelper.logger.error("SubscriberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } 
   }
   public Subscriber findOne(String msisdn) {
     Subscriber session = null;
     try {
       session = (Subscriber)this.collection.findOne("{'msisdn':'"+msisdn+"'}").as(Subscriber.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("SubscriberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("SubscriberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
   public Subscriber find(String query) {
     Subscriber session = null;
     try {
       session = (Subscriber)this.collection.findOne(query).as(Subscriber.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("SubscriberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("SubscriberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
   public Subscriber find(String query, String projections) {
     Subscriber session = null;
     try {
       session = (Subscriber)this.collection.findOne(query).projection(projections).as(Subscriber.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("SubscriberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("SubscriberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }

}

