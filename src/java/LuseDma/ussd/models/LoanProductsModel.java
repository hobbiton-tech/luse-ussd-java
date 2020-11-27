 package LuseDma.ussd.models;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.demo.LoanProductsItem;

 import java.util.ArrayList;
import java.util.List;
 import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

 public class LoanProductsModel
   extends MongoModelOld
 {
   private final MongoDBOld conn;
   private MongoCollection collection;
   private final String collectionName = AppConfigHelper.LOAN_PRODUCTS_COLLECTION;
   
   public LoanProductsModel(MongoDBOld conn) {
     super(conn);
     this.conn = conn;
     try {
       this.collection = this.jongo.getCollection(this.collectionName);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoanProductsModel - constructor(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoanProductsModel - constructor(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
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
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } 
   }
   public LoanProductsItem find(String query) {
     LoanProductsItem session = null;
     try {
       session = (LoanProductsItem)this.collection.findOne(query).as(LoanProductsItem.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
   public LoanProductsItem find(String query, String projections) {
     LoanProductsItem session = null;
     try {
       session = (LoanProductsItem)this.collection.findOne(query).projection(projections).as(LoanProductsItem.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
   public List<LoanProductsItem> findAll(String msisdn) {
     List<LoanProductsItem> list = new ArrayList<>();
     try {
       MongoCursor<LoanProductsItem> result = this.collection.find().as(LoanProductsItem.class);
       if (result != null) {
            for (LoanProductsItem entry : result) {
                LoanProductsItem document = entry;
                list.add(document);
            }
       }
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return list;
   }
   
   
   
   
   
   public Iterable<LoanProductsItem> find(String query, String projections, String sort) {
     Object object = null;
     try {
       object = this.collection.find(query).projection(projections).sort(sort).as(LoanProductsItem.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return (Iterable<LoanProductsItem>)object;
   }
   public Iterable<LoanProductsItem> find(String query, String projections, String sort, int skip, int limit) {
     Object object = null;
     try {
       object = this.collection.find(query).projection(projections).sort(sort).skip(skip).limit(limit).as(LoanProductsItem.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoanProductsModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return (Iterable<LoanProductsItem>)object;
   }
 }

