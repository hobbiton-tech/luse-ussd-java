 package LuseDma.ussd.models;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import LuseDma.ussd.pojos.kyc.Member;
 import org.jongo.MongoCollection;

 public class MembersModel extends MongoModelOld
 {
   private final MongoDBOld conn;
   private MongoCollection collection;
   private final String collectionName = "";
   
   public MembersModel(MongoDBOld conn) {
     super(conn);
     this.conn = conn;
     try {
       this.collection = this.jongo.getCollection(this.collectionName);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("MemberModel - constructor(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("MemberModel - constructor(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
   }
   public Boolean save(Member item) {
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
   public Member findByReference(String idnumber) {
     Member session = null;
     try {
       session = (Member)this.collection.findOne("{'idnumber':'"+idnumber+"'}").as(Member.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoansLedgerModel - find(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
   public void update(Member member) {
     Object object = null;
     try {
        this.collection.update("{'idnumber':'"+member.getIdnumber()+"'}").with(member);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("LoansLedgerModel - update(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("LoansLedgerModel - update(): failed to get collection " + AppConfigHelper.USSD_SESSIONS_COLLECTION + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
   }
   public Boolean exists(String idnumber) {
     try {
       long count = this.collection.count("{'idnumber':'"+idnumber+"'}");
       if (count >= 1L) {
         return Boolean.valueOf(true);
       }
       return Boolean.valueOf(false);
     }
     catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("MemberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } catch (Exception e) {
       AppConfigHelper.logger.error("MemberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
       return Boolean.valueOf(false);
     } 
   }
   public Member findOne(String msisdn) {
     Member session = null;
     try {
       ;
       session = (Member)this.collection.findOne("{'msisdn':'"+msisdn+"'}").orderBy("{date: -1}").as(Member.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("MemberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("MemberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
   public Member find(String query) {
     Member session = null;
     try {
       session = (Member)this.collection.findOne(query).as(Member.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("MemberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("MemberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }
   public Member find(String query, String projections) {
     Member session = null;
     try {
       session = (Member)this.collection.findOne(query).projection(projections).as(Member.class);
     } catch (OutOfMemoryError e) {
       AppConfigHelper.logger.error("MemberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("MemberModel - find(): failed to get collection " + collectionName + " from MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } 
     return session;
   }

}

