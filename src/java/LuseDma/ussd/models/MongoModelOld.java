 package LuseDma.ussd.models;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import com.mongodb.BasicDBObject;
 import com.mongodb.DB;
 import com.mongodb.DBCollection;
 import com.mongodb.DBCursor;
 import com.mongodb.DBObject;
 import com.mongodb.WriteConcern;
 import java.util.List;

 import org.jongo.Jongo;
 import org.jongo.MongoCollection;
 import org.json.JSONArray;

 public class MongoModelOld
 {
   DB db;
   public Jongo jongo;
   
   public MongoModelOld(MongoDBOld conn) {
     try {
       this.db = conn.getDb();
       this.jongo = conn.getJongo();
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
   public Boolean exists(String table, BasicDBObject readquery) {
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
   public Boolean put(String table, BasicDBObject updatequery, BasicDBObject updatedocument, boolean multi) {
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
   }
 }
