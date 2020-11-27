 package LuseDma.ussd.models;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.demo.LoanItem;

 import java.util.ArrayList;
import java.util.List;
 import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

 public class LoansLedgerModel
   extends MongoModelOld
 {
   private final MongoDBOld conn;
   private MongoCollection collection;
   private final String collectionName = AppConfigHelper.LOANS_LEDGER_COLLECTION;
   
   public LoansLedgerModel(MongoDBOld conn) {
     super(conn);
     this.conn = conn;
     try {
       this.collection = this.jongo.getCollection(this.collectionName);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoansLedgerModel - constructor(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoansLedgerModel - constructor(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
   }
   
   public Boolean save(LoanItem item) {
     try {
        this.collection.insert(item);
        return Boolean.valueOf(true);
     }
     catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } 
   }
   public LoanItem findByReference(String reference) {
     LoanItem session = null;
     try {
       session = (LoanItem)this.collection.findOne("{'reference':'"+reference+"'}").as(LoanItem.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
   public LoanItem find(String query, String projections) {
     LoanItem session = null;
     try {
       session = (LoanItem)this.collection.findOne(query).projection(projections).as(LoanItem.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
      public List<LoanItem> findAll(String msisdn) {
     List<LoanItem> list = new ArrayList<>();
     try {
       MongoCursor<LoanItem> result = this.collection.find().as(LoanItem.class);
       if (result != null) {
            for (LoanItem entry : result) {
                LoanItem document = entry;
                list.add(document);
            }
       }
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return list;
   }

      
      
      
      
      
   public void update(LoanItem loan) {
     Object object = null;
     try {
        this.collection.update("{'reference':'"+loan.getReference()+"'}").with(loan);
        //friends.update("{name: 'Joe'}").with(new Friend(..));
        //friends.update("{name: 'Joe'}").with("{$set: {address: #}}", new Address(..));
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoansLedgerModel - update(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoansLedgerModel - update(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
   }

 }

