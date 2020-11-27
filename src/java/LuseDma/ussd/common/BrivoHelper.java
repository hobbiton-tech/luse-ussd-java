 package LuseDma.ussd.common;
 
 import java.math.BigDecimal;
 import java.math.RoundingMode;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.security.SecureRandom;
 import java.text.DateFormat;
 import java.text.DecimalFormat;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.Random;
 import java.util.UUID;
 import java.util.concurrent.atomic.AtomicLong;
 import java.util.logging.Level;
 import java.util.logging.Logger;

 import org.apache.commons.codec.binary.Base32;

 public class BrivoHelper
 {
   private static final char[] symbols = new char[36];
   private static final Random random = new Random();
   private static final Random securerandom = new SecureRandom();
   private static final AtomicLong LAST_TIME_MS = new AtomicLong();
   
   static  {
     for (int idx = 0; idx < 10; idx++)
       symbols[idx] = (char)(48 + idx); 
     for (int idx = 10; idx < 36; idx++)
       symbols[idx] = (char)(65 + idx - 10); 
   }
   
   private static char[] buffer;
   
   public static UUID generateUUID() { return UUID.randomUUID(); }
 
   
   public static String generateAtomicLongCode(AtomicLong sequence) {
     byte[] id = new byte[10];
     longTo5ByteArray(sequence.incrementAndGet(), id);
     byte[] rnd = new byte[5];
     securerandom.nextBytes(rnd);
     System.arraycopy(rnd, 0, id, 5, 5);
     return (new Base32()).encodeAsString(id);
   }
   private static void longTo5ByteArray(long l, byte[] b) {
     b[0] = (byte)(int)(l >>> 32L);
     b[1] = (byte)(int)(l >>> 24L);
     b[2] = (byte)(int)(l >>> 16L);
     b[3] = (byte)(int)(l >>> 8L);
     b[4] = (byte)(int)(l >>> 0L);
   }
 
   
   public static String generateAlphaNumericCode(int length) {
     buffer = new char[length];
     for (int idx = 0; idx < buffer.length; idx++) {
       buffer[idx] = symbols[random.nextInt(symbols.length)];
     }
     return new String(buffer);
   }
 
   
   public static int generateRandomNumber(int Max, int Min) { return Min + (int)(Math.random() * (Max - Min + 1)); }
 
   
   public static Long uniqueCurrentTimeMs() {
     long lastTime, now = System.currentTimeMillis();
     do {
       lastTime = LAST_TIME_MS.get();
       if (lastTime < now)
         continue;  now = lastTime + 1L;
     } while (!LAST_TIME_MS.compareAndSet(lastTime, now));
     return Long.valueOf(now);
   }
    public static Long uniqueCurrentTime() {
     long lastTime, now = System.currentTimeMillis();
     do {
       lastTime = LAST_TIME_MS.get();
       if (lastTime < now)
         continue;  now = lastTime + 1L;
     } while (!LAST_TIME_MS.compareAndSet(lastTime, now));
     return now/1000L;
   }
   
   public static long unixTimeStamp(Date date) { return date.getTime() / 1000L; }
   
   public static double generateRandomDouble(double min, double max) {
     double range = max - min;
     double scaled = random.nextDouble() * range;
     double shifted = scaled + min;
     return shifted;
   }
 
   
   public static Double round(double value, int places) {
     if (places < 0) throw new IllegalArgumentException(); 
     BigDecimal bd = new BigDecimal(value);
     bd = bd.setScale(places, RoundingMode.HALF_UP);
     return Double.valueOf(bd.doubleValue());
   }
   
   public static String round(double value) {
     DecimalFormat formatter;
     if (value - (int)value > 0.0D) {
       formatter = new DecimalFormat("0.00");
     } else {
       formatter = new DecimalFormat("0");
     }  return formatter.format(value);
   }
   public static long generateRequestID(int charLength) {
     return (charLength < 1) ? 0L : ((new Random())
       .nextInt(9 * (int)Math.pow(10.0D, (charLength - 1)) - 1) + 
       (int)Math.pow(10.0D, (charLength - 1)));
   }
 
   
   public static int generateAutoNumber(int High, int Low) { return random.nextInt(High - Low) + Low; }
 
   
   public static String cryptoHash(String stringInput, String algorithmName) {
     String hexMessageEncode = "";
     byte[] buffer = stringInput.getBytes();
     MessageDigest messageDigest = null;
     try {
       messageDigest = MessageDigest.getInstance(algorithmName);
     } catch (NoSuchAlgorithmException e) {
       Logger.getLogger(BrivoHelper.class.getName()).log(Level.SEVERE, null, e);
     } 
     messageDigest.update(buffer);
     byte[] messageDigestBytes = messageDigest.digest();
     for (int index = 0; index < messageDigestBytes.length; index++) {
       int countEncode = messageDigestBytes[index] & 0xFF;
       if (Integer.toHexString(countEncode).length() == 1) hexMessageEncode = hexMessageEncode + "0"; 
       hexMessageEncode = hexMessageEncode + Integer.toHexString(countEncode);
     } 
     return hexMessageEncode;
   }
   
   public static Date parseDate(String date, String format) {
     Date javadate = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat(format);
     
     try { sdf.setLenient(false);
       javadate = sdf.parse(date); }
     catch (ParseException e)
     { Logger.getLogger(BrivoHelper.class.getName()).log(Level.SEVERE, null, e); }
     catch (IllegalArgumentException e) { Logger.getLogger(BrivoHelper.class.getName()).log(Level.SEVERE, null, e); }
      return javadate;
   }
   
   public static String getInstanceDate(String format) {
     SimpleDateFormat sdf = new SimpleDateFormat(format);
     Calendar c1 = Calendar.getInstance();
     return sdf.format(c1.getTime());
   }
 
   
   public static String unwrapDate(Date date, String format) {
     DateFormat df = new SimpleDateFormat(format);
 
     
     return df.format(date);
   }
 }
   /*Thread thread = new Thread(runnable);
            thread.start();
  public static void postDelayed(Runnable runnable, long delayMillis) {
     long requested = System.currentTimeMillis();
    (new Thread((Runnable)new Object(requested, delayMillis, runnable))).start();

 }*/

