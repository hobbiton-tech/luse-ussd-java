 package LuseDma.ussd.common;
 
 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonParser;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.StringReader;
 import java.io.StringWriter;
 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.text.DateFormat;
 import java.text.DecimalFormat;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.Properties;
 import java.util.concurrent.TimeUnit;
 import javax.servlet.http.HttpServletRequest;
 import javax.xml.bind.JAXBContext;
 import javax.xml.bind.JAXBException;
 import javax.xml.bind.Marshaller;
 import javax.xml.bind.Unmarshaller;

 import org.apache.commons.lang.WordUtils;
 import org.apache.log4j.Appender;
 import org.apache.log4j.Layout;
 import org.apache.log4j.Level;
 import org.apache.log4j.Logger;
 import org.apache.log4j.PatternLayout;
 import org.apache.log4j.RollingFileAppender;
 import org.joda.time.DateTime;
 import org.joda.time.Duration;
 import org.joda.time.Minutes;
 import org.joda.time.ReadableInstant;
 
 public class AppConfigHelper
 {
   public static Logger logger;
   public static RollingFileAppender appender;
   public static AppConfigHelper instance = null;
   
   public static int SYSTEM_ENV = 2;
   public static int SYSTEM_DEBUG = 0;
   
   public static String CONFIG_PATH;
   
   public static String LOG_PATH;
   public static String APP_NAME = "LUSE";
   public static String SHORT_CODE = "*444#";
   public static String WEBSITE = "www.company.com";
   
   public static String CURRENCY_SYMBOL = "ZMK";
   public static String CURRENCY = "Kwacha";
   public static Integer MAX_EMPLOYEES = Integer.valueOf(5);
   public static String MOMO_PAYMENT_DELAY = "4000";
   public static String API_KEY = "";
   
   public static String MONGO_HOST = "localhost";
   public static int MONGO_PORT = 27017;
   public static String MONGO_USER = "root";
   public static String MONGO_PWD = "root123";
   public static String MONGO_DATABASE = "luse_dma_ussd";

   public static String LUSEDMA_BASE_URL = "http://41.175.23.42:8484/api/v1";
   public static  String LUSEDMA_WEBSOCKET_URL = "ws://41.175.23.42:8484/api/v1/websocket";
   public static String SMS_GATEWAY_URL = "http://41.175.8.69:8282/mtnSMSC/index.php";
   public static String LUSEDMA_ACCESS_TOKEN = "ZsTGOrFWmF0qyJ8Ehz85BtuUyTumXNehRcXbHPUda7HeBlKz0sEdbR4LypxNyOMTzYp74JC-vo8lqCVg";
   public static String LUSE_CLIENT_ID = "5f7495f348678628a6b4d2c0";

   public static String USSD_SESSIONS_COLLECTION = "ussd_sessions";
   public static String SUBSCRIBERS_COLLECTION = "subscribers";
   public static String LOAN_PRODUCTS_COLLECTION = "loan_products";
   public static String LOANS_LEDGER_COLLECTION = "loans_ledger";
   public static String TRANSACTIONS_COLLECTION = "payment_transactions";
   
   public static String USSD_MSG_BASE = "*677*";
   public static int USSD_MSG_INDEX = 5;
   public static int TIME_TO_LIVE = 20;
   public static int TEMPORAL_BLOCK_TIME = 10;
   public static int TEMPORAL_BLOCK_THRESHOLD = 60;
   public static int AUTHENTICATION_ATTEMPTS = 3;
   public static int MIN_PIN_SCHEMA_LENGTH = 4;
   public static int MAX_PIN_SCHEMA_LENGTH = 8;
   
   public static String[] NAGIVATION_CHARACTER = null;
   public static String CONFIRM_ACTION_CHARACTER = "1";
   public static String NEXT_PAGE_CHARACTER = "*";
   public static String PRIOR_PAGE_CHARACTER = "#";
   public static String RETURN_TO_LIST_CHARACTER = "#";
   public static String ADD_TO_LIST_CHARACTER = "+";
   public static String SKIP_CHARACTER = "99";
   public static String PINRESET_CHARACTER = "2";
   public static String ONE_STEP_BACK_CHARACTER = "#";
   public static String GO_BACK_CHARACTER = "##";
   public static String RESTART_CHARACTER = "999";
   
   public static String confirmActionText = "Press " + CONFIRM_ACTION_CHARACTER + " to confirm";
   public static String nextPageText = "Press " + NEXT_PAGE_CHARACTER + " for next";
   public static String priorPageText = "Press " + PRIOR_PAGE_CHARACTER + " for prior";
   public static String returnToListText = "Press " + RETURN_TO_LIST_CHARACTER + " for list";
   public static String addToListText = "Press " + ADD_TO_LIST_CHARACTER + " to add";
   public static String skipText = "Press " + SKIP_CHARACTER + " to skip";
   public static String pinResetText = "Press " + PINRESET_CHARACTER + " to reset pin";
   public static String oneStepBackText = "Press " + ONE_STEP_BACK_CHARACTER + " to return";
   public static String goBackText = "Press " + GO_BACK_CHARACTER + " to return";
   public static String restartText = "Press " + RESTART_CHARACTER + " to restart";
   
   public static Double DURATION = Double.valueOf(700.0D);
   public static String API_BASE_URL = "";
 
   public AppConfigHelper() {
     if (checkIfFileExists("/etc/luse-dma/ussd.conf").booleanValue()) {
       CONFIG_PATH = "/etc/luse-dma/ussd.conf";
      LOG_PATH = "/var/log/luse-dma/ussd.log";
     } else {
       CONFIG_PATH = "C:\\etc\\luse-dma\\ussd.conf";
       LOG_PATH = "C:\\etc\\luse-dma\\ussd.log";
     } 
     
     try {
       logger = Logger.getRootLogger();
       logger.setLevel(Level.INFO);
       logger.removeAllAppenders();
       appender = new RollingFileAppender((Layout)new PatternLayout("%t]:[%d{ISO8601}] %m%n"), LOG_PATH);
       appender.setMaxFileSize("50MB");
       appender.setMaxBackupIndex(10);
       logger.addAppender((Appender)appender);
     } catch (IOException e) {
       e.printStackTrace();
       System.out.println("Error starting main logger");
     } 
 
     
     Properties prop = new Properties();
     FileInputStream input = null;
     
     try {
       input = new FileInputStream(CONFIG_PATH);
       prop.load(input);
       
       SYSTEM_ENV = Integer.valueOf(prop.getProperty("system-env")).intValue();
       SYSTEM_DEBUG = Integer.valueOf(prop.getProperty("system-debug")).intValue();
       LOG_PATH = prop.getProperty("log-path");
       
       APP_NAME = prop.getProperty("app-name");
       SHORT_CODE = prop.getProperty("app-shortcode");
       WEBSITE = prop.getProperty("app-website");
       
       CURRENCY_SYMBOL = prop.getProperty("currency-symbol");
       CURRENCY = prop.getProperty("currency");

       MOMO_PAYMENT_DELAY = prop.getProperty("momo-payment-delay");
     
       MONGO_HOST = prop.getProperty("mongo-host");
       MONGO_PORT = Integer.valueOf(prop.getProperty("mongo-port")).intValue();
       MONGO_USER = prop.getProperty("mongo-user");
       MONGO_PWD = prop.getProperty("mongo-pwd");
       MONGO_DATABASE = prop.getProperty("mongo-database");
       
       USSD_MSG_BASE = prop.getProperty("ussd-message-base");
       USSD_MSG_INDEX = Integer.valueOf(prop.getProperty("ussd-message-index")).intValue();
       TIME_TO_LIVE = Integer.valueOf(prop.getProperty("cache-time-to-live")).intValue();
       TEMPORAL_BLOCK_TIME = Integer.valueOf(prop.getProperty("temporal-block-time")).intValue();
       TEMPORAL_BLOCK_THRESHOLD = Integer.valueOf(prop.getProperty("temporal-block-threshold")).intValue();
       AUTHENTICATION_ATTEMPTS = Integer.valueOf(prop.getProperty("authentication-attempts")).intValue();
       
       MIN_PIN_SCHEMA_LENGTH = Integer.valueOf(prop.getProperty("min-pin-schema-length")).intValue();
       MAX_PIN_SCHEMA_LENGTH = Integer.valueOf(prop.getProperty("max-pin-schema-length")).intValue();
 
       
       CONFIRM_ACTION_CHARACTER = prop.getProperty("confirm-action-character");
       NEXT_PAGE_CHARACTER = prop.getProperty("next-page-character");
       PRIOR_PAGE_CHARACTER = prop.getProperty("prior-page-character");
       RETURN_TO_LIST_CHARACTER = prop.getProperty("return-to-list-character");
       ADD_TO_LIST_CHARACTER = prop.getProperty("add-to-list-character");
       SKIP_CHARACTER = prop.getProperty("skip-character");
       PINRESET_CHARACTER = prop.getProperty("pin-reset-character");
       ONE_STEP_BACK_CHARACTER = prop.getProperty("one-step-back-character");
       GO_BACK_CHARACTER = prop.getProperty("go-back-character");
       RESTART_CHARACTER = prop.getProperty("restart-character");
       
       String[] characters = { CONFIRM_ACTION_CHARACTER, NEXT_PAGE_CHARACTER, PRIOR_PAGE_CHARACTER, RETURN_TO_LIST_CHARACTER, ADD_TO_LIST_CHARACTER, ONE_STEP_BACK_CHARACTER, GO_BACK_CHARACTER, RESTART_CHARACTER };
       NAGIVATION_CHARACTER = characters;
       
       confirmActionText = "Press " + CONFIRM_ACTION_CHARACTER + " to confirm";
       nextPageText = "Press " + NEXT_PAGE_CHARACTER + " for next";
       priorPageText = "Press " + PRIOR_PAGE_CHARACTER + " for prior";
       returnToListText = "Press " + RETURN_TO_LIST_CHARACTER + " for list";
       addToListText = "Press " + ADD_TO_LIST_CHARACTER + " to add";
       skipText = "Press " + SKIP_CHARACTER + " to skip";
       pinResetText = "Press " + PINRESET_CHARACTER + " to reset pin";
       oneStepBackText = "Press " + ONE_STEP_BACK_CHARACTER + " to return";
       goBackText = "Press " + GO_BACK_CHARACTER + " to return";
       restartText = "Press " + RESTART_CHARACTER + " to restart";

       API_BASE_URL = prop.getProperty("api-base-url");
       API_KEY = prop.getProperty("api-key");
     }
     catch (IOException e) {
       System.out.println("Error reading from property file");
       logger.info("Error reading from property file");
       logger.info(e.toString());
     } 
     
     try {
       logger = Logger.getRootLogger();
       logger.setLevel(Level.INFO);
       logger.removeAllAppenders();
       appender = new RollingFileAppender((Layout)new PatternLayout("%t]:[%d{ISO8601}] %m%n"), LOG_PATH);
       appender.setMaxFileSize("50MB");
       appender.setMaxBackupIndex(10);
       logger.addAppender((Appender)appender);
     } catch (IOException e) {
       e.printStackTrace();
       System.out.println("Error starting main logger");
     } 
   }
   public static synchronized AppConfigHelper getInstance() {
     if (instance == null) {
       instance = new AppConfigHelper();
     }
     return instance;
   }
   
   public static String getAppName() { return APP_NAME; }
   public static String getUSSDMessageBase() { return USSD_MSG_BASE; }
   public static int getAuthAttempts() { return AUTHENTICATION_ATTEMPTS; }
   public static int getMinPinLength() { return MIN_PIN_SCHEMA_LENGTH; }
   public static int getMaxPinLength() { return MAX_PIN_SCHEMA_LENGTH; }
 
   
   public static String[] getNavigationCharacters() { return NAGIVATION_CHARACTER; }
   
   public static String getConfirmActionCharacter() { return CONFIRM_ACTION_CHARACTER; }
   public static String getNextPageCharacter() { return NEXT_PAGE_CHARACTER; }
   public static String getPriorPageCharacter() { return PRIOR_PAGE_CHARACTER; }
   public static String getReturnToListCharacter() { return RETURN_TO_LIST_CHARACTER; }
   public static String getAddToListCharacter() { return ADD_TO_LIST_CHARACTER; }
   public static String getSkipCharacter() { return SKIP_CHARACTER; }
   public static String getPinResetCharacter() { return PINRESET_CHARACTER; }
   
   public static String getOneStepBackCharacter() { return ONE_STEP_BACK_CHARACTER; }
   public static String getGoBackCharacter() { return GO_BACK_CHARACTER; }
   public static String getRestartCharacter() { return RESTART_CHARACTER; }
   
   public static String getConfirmActionText() { return confirmActionText; }
   public static String getNextPageText() { return nextPageText; }
   public static String getPriorPageText() { return priorPageText; }
   public static String getReturnToListText() { return returnToListText; }
   public static String getAddToListText() { return addToListText; }
   public static String getSkipText() { return skipText; }
   public static String getPinResetText() { return pinResetText; }
   
   public static String getOneStepBackText() { return oneStepBackText; }
   public static String getGoBackText() { return goBackText; }
   public static String getRestartText() { return restartText; }
 
   
   public boolean isNavigationCharacter(String character) {
     if (character.length() == 1) {
       for (int i = 0; i < NAGIVATION_CHARACTER.length; i++) {
         String navchar = NAGIVATION_CHARACTER[i];
         if (navchar.equalsIgnoreCase(character)) {
           return true;
         }
       } 
       return false;
     } 
     return false;
   }
     public static HashMap<String,Object> getMobileProvider(int number)
    {
        HashMap<String,Object> mobileProvider = new HashMap<String,Object>();
        mobileProvider.put("id", 0);
        switch (number) {
            case 1: 
                mobileProvider.put("id", 1);
                mobileProvider.put("name", "AIRTEL");
                break;
            case 2:
                mobileProvider.put("id", 2);
                mobileProvider.put("name", "MTN");
                break;
            case 3:
                mobileProvider.put("id", 3);
                mobileProvider.put("name", "ZAMTEL");
                break;
        }
        return mobileProvider;
    }
   public static int getMaxPrefixLength(String[] prefixes) {
     int max = 0;
     int len = 0;
     for (String i : prefixes) {
       len = i.length();
       for (String j : prefixes) {
         if (j.length() > len) {
           len = j.length();
         }
       } 
     } 
     
     return len;
   }
   
   public static Integer getCurrentYear() {
     Integer year = Integer.valueOf(Calendar.getInstance().get(1));
     return year;
   }
   
   public static boolean isOverTimeToLive(Date previous) {
     boolean result = Minutes.minutesBetween((ReadableInstant)new DateTime(previous), (ReadableInstant)new DateTime()).isGreaterThan(Minutes.minutes(TIME_TO_LIVE));
     return result;
   }
   
   public static boolean isOverBlockedTime(Date previous) {
     boolean result = Minutes.minutesBetween((ReadableInstant)new DateTime(previous), (ReadableInstant)new DateTime()).isGreaterThan(Minutes.minutes(TEMPORAL_BLOCK_TIME));
     return result;
   }
   public static String getTemporalBlockTimeLeft(Date blockdate) {
     DateTime now = new DateTime();
     DateTime dateblocked = new DateTime(blockdate);
     DateTime unblockedtime = dateblocked.plusMinutes(TEMPORAL_BLOCK_TIME);
     Duration duration = new Duration((ReadableInstant)now, (ReadableInstant)unblockedtime);
     long dd = duration.getStandardDays();
     long hd = duration.getStandardHours();
     long nd = duration.getStandardMinutes();
     long sd = duration.getStandardSeconds();
     String timeleft = "";
     if (dd > 0L) {
       timeleft = dd + " day"; if (dd == 1L) { timeleft = timeleft + ""; } else { timeleft = timeleft + "s"; } 
     }  if (hd > 0L && dd <= 0L) {
       timeleft = hd + " hour"; if (hd == 1L) { timeleft = timeleft + ""; } else { timeleft = timeleft + "s"; } 
     }  if (nd > 0L && hd <= 0L && dd <= 0L) {
       timeleft = nd + " minute"; if (nd == 1L) { timeleft = timeleft + ""; } else { timeleft = timeleft + "s"; } 
     }  if (sd > 0L && nd <= 0L && hd <= 0L && dd <= 0L) {
       timeleft = sd + " second"; if (sd == 1L) { timeleft = timeleft + ""; } else { timeleft = timeleft + "s"; } 
     }  if (timeleft.equalsIgnoreCase("")) timeleft = "0 seconds"; 
     return timeleft;
   }
   public static Boolean isSecurityThreat(Date lasttempblockdate) {
     DateTime now = new DateTime();
     Duration duration = new Duration((ReadableInstant)now, (ReadableInstant)new DateTime(lasttempblockdate));
     long minutesSinceUnblock = duration.getStandardMinutes() * -1L;
     if (minutesSinceUnblock < TEMPORAL_BLOCK_THRESHOLD) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
   
   public static Boolean isHistory(Date date) {
     Calendar now = Calendar.getInstance();
     if (now.getTime().after(date)) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
   
   public static Date parseDate(String date, String format) {
     Date javadate = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat(format);
     try {
       sdf.setLenient(false);
       javadate = sdf.parse(date);
     }
     catch (ParseException e) {
       logger.error("Exception", e);
     }
     catch (IllegalArgumentException e) {
       logger.error("Exception", e);
     } 
     return javadate;
   }
 
   public static String formatDate(String date, String format) {
     Date javadate = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat(format);
     try {
       sdf.setLenient(false);
       javadate = sdf.parse(date);
     }
     catch (ParseException e) {
       logger.error("Exception", e);
     }
     catch (IllegalArgumentException e) {
       logger.error("Exception", e);
     } 
     return sdf.format(javadate);
   }
      
   public static String getInstanceDate(String format) {
     SimpleDateFormat sdf = new SimpleDateFormat(format);
     Calendar c1 = Calendar.getInstance();
     return sdf.format(c1.getTime());
   }
 
   
   public static String unwrapDate(Date date, String format) {
       try {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(date);
       } catch (Exception e){
           return "";
       }
   }
   public static Date addDaysToDate(Date date,int days){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // manipulate date
        //c.add(Calendar.YEAR, 1);
        //c.add(Calendar.MONTH, 1);
        //c.add(Calendar.DATE, 1);//same with c.add(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.HOUR, days);
        //c.add(Calendar.MINUTE, days);
        return  c.getTime();
   }
   
   
   public static Date addMinutesToDate(Date date,int minutes){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minutes);
        return  c.getTime();
   }
   public static Date getLastDateOfTheMonth(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
   }

   
   public static String securityHash(String stringInput, String algorithmName) {
     String hexMessageEncode = "";
     byte[] buffer = stringInput.getBytes();
     MessageDigest messageDigest = null;
     try {
       messageDigest = MessageDigest.getInstance(algorithmName);
     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
 
     
     messageDigest.update(buffer);
     byte[] messageDigestBytes = messageDigest.digest();
     for (int index = 0; index < messageDigestBytes.length; index++) {
       int countEncode = messageDigestBytes[index] & 0xFF;
       if (Integer.toHexString(countEncode).length() == 1) {
         hexMessageEncode = hexMessageEncode + "0";
       }
       hexMessageEncode = hexMessageEncode + Integer.toHexString(countEncode);
     } 
     return hexMessageEncode;
   }
 
 
   
   public static Boolean isPassedTimeToLive(Date previous) {
     long MAX_DURATION = TimeUnit.MILLISECONDS.convert(TIME_TO_LIVE, TimeUnit.MINUTES);
     Date now = new Date();
     long duration = now.getTime() - previous.getTime();
     if (duration >= MAX_DURATION) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
 
 
   
   public static String formatAmount(String amt) {
     try {
       DecimalFormat formatter = new DecimalFormat("#,###.00");
       amt = formatter.format(Double.parseDouble(amt));
       return CURRENCY_SYMBOL + amt;
     } catch (Exception e) {
       return CURRENCY_SYMBOL + "0.00";
     } 
   }
 
 
   
   public static String parseNull(String s) {
     try {
       if (s == null || s.equalsIgnoreCase("null")) {
         s = "";
       }
     } catch (NullPointerException e) {
       s = "";
     } 
     return s;
   }
   public static String capitalizeFirstLetter(String str) {
     try {
       if (str == null || str.equalsIgnoreCase("null")) {
         str = "";
       } else {
         str = WordUtils.capitalizeFully(str);
       } 
     } catch (NullPointerException e) {
       str = "";
     } 
     
     return str;
   }
 
 
   
   public static Boolean isValidName(String s) { return Boolean.valueOf(true); }
   
   public static Boolean isValidDate(String date) {
     try {
       Date dt = parseDate(date, "yyyy-MM-dd");
       return Boolean.valueOf(true);
     } catch (Exception e) {
       return Boolean.valueOf(false);
     } 
   }
   public static Boolean isValidInteger(String v) {
     try {
       int number = Integer.parseInt(v);
       return Boolean.valueOf(true);
     } catch (NumberFormatException e) {
       
       return Boolean.valueOf(false);
     } 
   }
   public static Integer getIntegerValue(String v) {
     int number = 0;
     try {
       number = Integer.parseInt(v);
     } catch (NumberFormatException numberFormatException) {}
 
     
     return Integer.valueOf(number);
   }
 
   
   public static Object unmarshXML(String xml, JAXBContext jaxbContext) {
     Object obj = null;
     try {
       Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
       obj = jaxbUnmarshaller.unmarshal(new StringReader(xml));
     } catch (JAXBException e) {
       logger.error("Exception", e);
     } 
     return obj;
   }
   
   public static String getXMLResponse(Object obj, JAXBContext jaxbContext) {
     StringWriter stringWriter = new StringWriter();
     try {
       Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
       jaxbMarshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
       jaxbMarshaller.marshal(obj, stringWriter);
     }
     catch (JAXBException e) {
       logger.error("Exception", e);
     } 
     return stringWriter.toString();
   }
   public static String parseUSSDHashChar(String v) {
     String result = "";
     if (v.length() > 0) {
       StringBuilder strb = new StringBuilder(v);
       if (strb.charAt(v.length() - 1) == '#') {
         strb.deleteCharAt(v.length() - 1);
       }
       result = strb.toString();
     } 
     return result;
   }
   public static Boolean checkIfFileExists(String path) {
     try {
       File f = new File(path);
       if (f.exists() && !f.isDirectory()) {
         return Boolean.valueOf(true);
       }
       return Boolean.valueOf(false);
     }
     catch (Exception e) {
       logger.error("Exception", e);
       return Boolean.valueOf(false);
     } 
   }
   public static HashMap getEmployerAPIParams(String accno) {
     HashMap<String, String> params = new HashMap<>();
     params.put("accno", accno);
     params.put("offset", "0");
     params.put("perpage", "1000000");
     params.put("sort", "desc");
     return params;
   }
   public static HashMap getMemberAPIParams(String ssn) {
     HashMap<String, String> params = new HashMap<>();
     params.put("ssn", ssn);
     params.put("offset", "0");
     params.put("perpage", "1000000");
     params.put("sort", "desc");
     return params;
   }
   public static HashMap getClaimsAPIParams(String ssn, String nrc) {
     HashMap<String, String> params = new HashMap<>();
     params.put("ssn", ssn);
     params.put("nrc", nrc);
     return params;
   }
   public static String getGender(String code) {
     switch (code) { case "F":
         return "Female";
       case "M": return "Male"; }
      return "Unknown";
   }
 
   
   public static JsonElement loadJson(String path) {
     JsonElement jsonData = null;
     Gson gson = (new GsonBuilder()).enableComplexMapKeySerialization().setPrettyPrinting().create();
     String filePath = path;
     try {
       String fileData = new String(Files.readAllBytes(Paths.get(filePath, new String[0])));
       JsonParser jsonParser = new JsonParser();
       jsonData = jsonParser.parse(fileData);
     } catch (IOException e) {
       e.printStackTrace();
     } 
     return jsonData;
   }
 
   
   public static Boolean saveJson(String data, String path) {
     String filePath = path;
     try {
       File file = new File(filePath);
       file.createNewFile();
       FileOutputStream fileStream = new FileOutputStream(file, false);
       byte[] myBytes = data.getBytes();
       fileStream.write(myBytes);
       fileStream.close();
       return Boolean.valueOf(true);
     } catch (IOException e) {
       e.printStackTrace();
       return Boolean.valueOf(false);
     } 
   }
  
    public static HashMap getMapFromObject(Object obj){
        HashMap<String,String> map = new HashMap<String,String>();
        return map;
    }

   public static String ellipsize(String input, int maxCharacters, int charactersAfterEllipsis) {
     if (maxCharacters < 3) {
       throw new IllegalArgumentException("maxCharacters must be at least 3 because the ellipsis already take up 3 characters");
     }
     if (maxCharacters - 3 > charactersAfterEllipsis) {
       throw new IllegalArgumentException("charactersAfterEllipsis must be less than maxCharacters");
     }
     if (input == null || input.length() < maxCharacters) {
       return input;
     }
     return input.substring(0, maxCharacters - 3 - charactersAfterEllipsis) + "..." + input.substring(input.length() - charactersAfterEllipsis);
   }
   
   public static String getRequestValue(HttpServletRequest request, String key, String defaultValue) {
     try {
       String value = request.getParameter(key);
       return value.isEmpty() ? defaultValue : value;
     } catch (Exception e) {
       return defaultValue;
     } 
   }
   
   public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
     Map<String, String> map = new HashMap<>();
     
     Enumeration<String> headerNames = request.getHeaderNames();
     while (headerNames.hasMoreElements()) {
       String key = headerNames.nextElement();
       String value = request.getHeader(key);
       map.put(key, value);
     } 
     
     return map;
   }
   
    public static HashMap<String, String> FormatPhoneNumber(String number)
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();
        String value = "";
        String value2 = "";
        number = number.replace("+", "");
        switch (number.length()) {
            case 9:
                value = "0" + number;
                value2 = "260" + number;
                break;
            case 10:
                value = number;
                value2 = "26" + number;
                break;
            case 12: 
                value = number.replaceAll("[26+]","");
                value2 = number;
                break;
            default:
               break;
        }
        dictionary.put("msisdn", value2);
        dictionary.put("national_msisdn", value);
        return dictionary;
    }
    public static String GetQualifiedPhoneNumber(String number)
    {
        String result = number;
        number = number.replace("+", "");
        switch (number.length()){
            case 9:
                result = "260" + number;
                break;
            case 10:
                result = "26" + number;
                break;
            case 12:
                result = number;
                break;
            default:
                return result;

        }
        return result;
    }

    public static boolean IsValidMobile(String data)
    {
        boolean result = false;
        try
        {
            String text = data.replace("+", "");
            if (isValidInteger(text)){
                switch (text.length()){
                    case 9:
                        return true;
                    case 10:
                        return true;
                    case 12:
                        return true;
                    default:
                        return result;
                    case 11:
                        return result;
                }
            }
            return result;
        } catch(Exception e) {
           return false;
        }
    }
    public static HashMap<String, Object> getPhoneNumber(String number)
    {
        HashMap<String, Object> dictionary = new HashMap<String, Object>();
        String value = "";
        Boolean isValid = false;
        number = number.replace("+", "");
        System.out.println("number:"+ number+" ,number length:"+ number.length());
        switch (number.length()) {
            case 9:
                value = "260" + number;
                isValid = true;
                break;
            case 10:
                value = "26" + number;
                isValid = true;
                break;
            case 12: 
                value = number;
                isValid = true;
                break;
            default:
                isValid = false;
               break;
        }
        dictionary.put("msisdn", value);
        dictionary.put("is_valid",isValid);
        return dictionary;
    }
    public static String getMSISDNProvider(String number)
    {
        String mno = "";
        Boolean isValid = false;
        char code = number.charAt(4);
        switch (code) {
            case '5':
                mno = "ZAMTEL";
                break;
            case '6':
                mno = "MTN";
                break;
            case '7': 
                mno = "AIRTEL";
                break;
        }
        return mno;
    }
 }

