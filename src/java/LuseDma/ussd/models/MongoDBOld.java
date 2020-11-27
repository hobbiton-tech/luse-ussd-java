 package LuseDma.ussd.models;
 
 import LuseDma.ussd.common.AppConfigHelper;
 import com.mongodb.DB;
 import com.mongodb.MongoClient;
 import com.mongodb.MongoCredential;
 import com.mongodb.MongoException;
 import com.mongodb.ServerAddress;

 import java.util.logging.Level;
 import java.util.logging.Logger;

 import org.jongo.Jongo;

 public class MongoDBOld
 {
   MongoClient mongoClient;
   DB db;
   Jongo jongo;
   
   public MongoDBOld() {
     Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
     try {
       MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(AppConfigHelper.MONGO_USER, AppConfigHelper.MONGO_DATABASE, AppConfigHelper.MONGO_PWD.toCharArray());
       this.mongoClient = new MongoClient(new ServerAddress(AppConfigHelper.MONGO_HOST, AppConfigHelper.MONGO_PORT));
       this.db = this.mongoClient.getDB(AppConfigHelper.MONGO_DATABASE);
       this.jongo = new Jongo(this.db);
     } catch (MongoException e) {
       AppConfigHelper.logger.error("MongoDB: failed to connect to MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, (Throwable)e);
     } catch (IllegalStateException e) {
       AppConfigHelper.logger.error("MongoDB: failed to connect to MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, e);
     } catch (Throwable t) {
       if (t instanceof Exception) {
         AppConfigHelper.logger.error("MongoDB: failed to connect to MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, t);
       } else if (t instanceof Error) {
         AppConfigHelper.logger.error("MongoDB: failed to connect to MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, t);
       } 
     } 
   }
   
   public DB getDb() { return this.db; }
 
   
   public Jongo getJongo() { return this.jongo; }
   
   public void closeDb() {
     try {
       this.mongoClient.close();
     } catch (NullPointerException e) {
       AppConfigHelper.logger.error("MongoDB: Failed to close mongo connection", e);
     } catch (Exception e) {
       AppConfigHelper.logger.error("MongoDB: Failed to close mongo connection", e);
     } 
   }
 }

