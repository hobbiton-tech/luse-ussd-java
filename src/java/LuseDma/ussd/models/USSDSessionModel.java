 package LuseDma.ussd.models;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.MobileSession;
 import org.jongo.MongoCollection;

 public class USSDSessionModel extends MongoModelOld
 {
   private final MongoDBOld conn;
   private MongoCollection collection;
   private final String collectionName = AppConfigHelper.USSD_SESSIONS_COLLECTION;

   @Override
   public String toString() {
     return "USSDSessionModel{" +
             "collectionName='" + collectionName + '\'' +
             '}';
   }

   public USSDSessionModel(MongoDBOld conn) {
     super(conn);
     this.conn = conn;
     try {
       this.collection = this.jongo.getCollection(this.collectionName);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("USSDMongoModel - constructor(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("USSDMongoModel - constructor(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
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
       AppConfigHelper.logger.error("USSDMongoModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } catch (Exception e) {
       AppConfigHelper.logger.error("USSDMongoModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } 
   }
   public MobileSession find(String query) {
     MobileSession session = null;
     try {
       session = (MobileSession)this.collection.findOne(query).as(MobileSession.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("USSDMongoModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("USSDMongoModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
   public MobileSession find(String query, String projections) {
     MobileSession session = null;
     try {
       session = (MobileSession)this.collection.findOne(query).projection(projections).as(MobileSession.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("USSDMongoModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("USSDMongoModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }

 }

