 package LuseDma.ussd.models;
 
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import LuseDma.ussd.common.AppConfigHelper;
 import com.mongodb.client.MongoClient;

 import com.mongodb.client.MongoClients;
 import com.mongodb.client.MongoDatabase;
 public class MongoDB
 {
   MongoClient mongoClient;
   MongoDatabase database;
   public MongoDB() {
     Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
     try {
        //mongodb://myDBReader:D1fficultP%40ssw0rd@mongodb0.example.com:27017,mongodb1.example.com:27017,mongodb2.example.com:27017/admin?replicaSet=myRepl
        String uri = "mongodb://"+AppConfigHelper.MONGO_USER+":"+AppConfigHelper.MONGO_PWD+"@"+AppConfigHelper.MONGO_HOST+":"+AppConfigHelper.MONGO_PORT+"/"+AppConfigHelper.MONGO_DATABASE+"";
        this.mongoClient = MongoClients.create(uri);
        this.database = this.mongoClient.getDatabase(AppConfigHelper.MONGO_DATABASE);
     } catch (Throwable t) {
        if (t instanceof Exception) {
          AppConfigHelper.logger.error("MongoDB: failed to connect to MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, t);
        } else if (t instanceof Error) {
          AppConfigHelper.logger.error("MongoDB: failed to connect to MongoDB server /" + AppConfigHelper.MONGO_HOST + ":" + AppConfigHelper.MONGO_PORT + "/" + AppConfigHelper.MONGO_DATABASE, t);
        } 
     } 
   }
   
   public MongoDatabase getDatabase () { return this.database; }
    
   public MongoClient getMongoClient() { return this.mongoClient; }
   
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

