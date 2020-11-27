 package LuseDma.ussd.models;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
 import dev.morphia.Morphia;
 import org.jongo.Jongo;
 import org.jongo.MongoCollection;

 public class MongoModel
 {
   MongoDatabase db;
   Jongo jongo;
   MongoClient client;
   public MongoModel(MongoDB client) {
     try {
       this.client = client.getMongoClient();
       this.db = client.getDatabase();
       //this.jongo = client.getJongo();
       
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        ///Datastore datastore = morphia.createDatastore(client, "library");
        //datastore.ensureIndexes();
     } catch (Exception e) {
       AppConfigHelper.logger.error("MongoModel - constructor():Failed to get Mongo Client/DB/Jongo from MongoDB", e);
       throw new RuntimeException("MongoModel - constructor():Failed to get Mongo Client/DB/Jongo from MongoDB");
     } 
   }
   public MongoCollection getCollection(String table) {
     MongoCollection collection = null;
     try {
       collection = this.jongo.getCollection(table);
     } catch (NullPointerException e) {
       AppConfigHelper.logger.error("MongoModel - getCollection():Failed to get collection, unable to get collection " + table + " from MongoDB", e);
       throw new RuntimeException("MongoModel - getCollection():Failed to get collection, unable to get collection " + table + " from MongoDB");
     } 
     return collection;
   }
  /* public Boolean exists(String table, BasicDBObject readquery) {
     try {
       DBCollection collection = this.db.getCollection(table);
       DBCursor cursor = collection.find((DBObject)readquery);
       if (cursor.count() >= 1) {
         return Boolean.valueOf(true);
       }
       return Boolean.valueOf(false);
     }
     catch (NullPointerException e) {
       AppConfigHelper.logger.error("MongoModel - exists(): unable to get collection " + table + " from MongoDB", e);
       return Boolean.valueOf(false);
     } 
   }
   public Boolean create(String table, BasicDBObject document) {
     if (table == null) {
       AppConfigHelper.logger.error("MongoModel - create(): mongo collection must not be null");
       return Boolean.valueOf(false);
     } 
     if (document == null) {
       AppConfigHelper.logger.error("MongoModel - create(): mongo document must not be null");
       return Boolean.valueOf(false);
     } 
     try {
       DBCollection collection = this.db.getCollection(table);
       collection.insert((DBObject)document, WriteConcern.SAFE);
       return Boolean.valueOf(true);
     } catch (NullPointerException e) {
       AppConfigHelper.logger.error("MongoModel - create(): unable to get collection " + table + " from MongoDB", e);
       return Boolean.valueOf(false);
     } 
   }
   public List<DBObject> read(String table, BasicDBObject readquery, BasicDBObject projections, int offset, int perpage) {
     JSONArray array = new JSONArray();
     List<DBObject> list = null;
     try {
       DBCollection collection = this.db.getCollection(table);
       DBCursor cursor = collection.find((DBObject)readquery, (DBObject)projections, offset, perpage);
       list = cursor.toArray();
     } catch (NullPointerException e) {
       AppConfigHelper.logger.error("MongoModel - read(): unable to get collection " + table + " from MongoDB", e);
     } 
     return list;
   }
   public Boolean update(String table, BasicDBObject updatequery, BasicDBObject updatedocument, boolean multi) {
     if (table == null) {
       AppConfigHelper.logger.error("MongoModel - update(): collection must not be null");
       return Boolean.valueOf(false);
     } 
     if (updatedocument == null) {
       AppConfigHelper.logger.error("MongoModel - update(): document must not be null");
       return Boolean.valueOf(false);
     } 
     try {
       DBCollection collection = this.db.getCollection(table);
       collection.update((DBObject)updatequery, (DBObject)updatedocument, false, multi, WriteConcern.SAFE);
       return Boolean.valueOf(true);
     } catch (NullPointerException e) {
       AppConfigHelper.logger.error("MongoModel - update(): unable to get collection " + table + " from MongoDB", e);
       return Boolean.valueOf(false);
     } 
   }*/
 }
