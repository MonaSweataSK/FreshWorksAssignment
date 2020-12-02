package Assignment;
import java.io.*;
import java.lang.String;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;



import java.lang.instrument.Instrumentation;
import java.time.LocalTime;
@SuppressWarnings({ "serial", "unused" })
class DuplicateKey extends Exception {

}
@SuppressWarnings("serial")
class InvalidKey extends Exception {

}
@SuppressWarnings("serial")
class TimeExceeded extends Exception {

}
@SuppressWarnings("serial")
class KeySizeExceeded extends Exception{
	
}
@SuppressWarnings("serial")
class ValueSizeExceeded extends Exception{
	
}
public class assignment
{
	private static final String Key = null;
	private static Instrumentation instrument;
 static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 public void Insert( ) throws IOException, JSONException
 {
   Scanner in = new Scanner (System.in);
   
  System.out.println("Enter id ");
	String  id=in.next();
	System.out.println("Enter name ");
	String name=in.next();
	System.out.println("Enter department ");
	String dept=in.next();
	
	System.out.println("Enter the TimetoLive ");
	String ttl=in.next();  
	 
  JSONObject obj=new JSONObject(); 
  try {
if (id.length()>32) // chheck if Key is more than 32 Char
 	 throw new KeySizeExceeded();
  else if((instrument.getObjectSize((Object)obj)/1024)>16) // Check if jsonObject is more than 16 KB
 	 throw new ValueSizeExceeded();
  }
  catch (KeySizeExceeded e) {
   	System.out.println(" Key size exceeds maximum size, Enter Valid Key");
   } catch (ValueSizeExceeded e) {
   	System.out.println(" Value size exceeds maximum size ");
   }
  try {
  if (obj.has(name)) //check if jsonObject has the given key value pair is duplicate keys
      throw new DuplicateKey();
  }
  catch (DuplicateKey e) {
      System.out.println("KEY already exists. Duplicate keys not allowed");
  obj.put("ID",id);    
  obj.put("Name",name);    
  obj.put("Department",dept);
  
  LocalTime time = LocalTime.now();
  int TimeStamp = time.toSecondOfDay();
  //obj.put(Key,TimeStamp);
  obj.put(Key,ttl);
  PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Student.json",true)));
  pw.print(obj);
  System.out.println("Details added successfully.");
  pw.close();
 }
  }
 public Object read() throws JSONException {

     try (FileReader reader = new FileReader("Student.json")) {
         //Read JSON file
         
     	JSONTokener tokener = new JSONTokener(reader);
         JSONObject temp = new JSONObject(tokener);
         if (temp.has(Key)) //Check if jsonObject has the given key value pair
         {
             JSONArray tempArray = new JSONArray();
             tempArray = temp.getJSONArray(Key);
             LocalTime time = LocalTime.now();
             int CurrentTime = time.toSecondOfDay();
             if ((CurrentTime - tempArray.getInt(2)) < tempArray.getInt(1)) //Checking ttl condition
            	 return tempArray.getJSONObject(0);
             else
                 throw new TimeExceeded();

         } else
             throw new InvalidKey();

     } catch (TimeExceeded e) {
         System.out.println("Key Exceeded Time To Live");
     } catch (InvalidKey e) {
         System.out.println("Invalid Key.Enter a valid key to continue");
     } catch (FileNotFoundException e) {
         System.out.println("File Not Found");
     } catch (IOException e) {
         System.out.println("Caught IO Exception");
     }
		return null;
 }
 public void delete() throws JSONException {
	 try (FileReader reader = new FileReader("Student.json")) //Read JSON file
     {
     	JSONTokener tokener = new JSONTokener(reader);
         JSONObject temp = new JSONObject(tokener);
         if (temp.has(Key)) //Check if JSONObject has the given key value pair
         {
             JSONArray tempArray = new JSONArray();
             tempArray = temp.getJSONArray(Key);
             LocalTime time = LocalTime.now();
             int CurrentTime = time.toSecondOfDay();
             if ((CurrentTime - tempArray.getInt(2)) < tempArray.getInt(1)) //Checking ttl condition , if satisfied removes < Key,Value > pair
                 temp.remove(Key);
             else
                 throw new TimeExceeded();

             try (FileWriter file = new FileWriter("Student.json",false)) //Writes back edited jsonObject back to the file
             {

                 file.write(temp.toString());
                 file.close();
             }
         }
             else
                 throw new InvalidKey();

         } 
         catch (InvalidKey e) {
             System.out.println("Invalid Key.Enter a valid key to continue");
         } catch (IOException e) {
             System.out.println("Caught IO Exception");
         }

      catch (TimeExceeded e) {
         System.out.println("Key Exceeded Time To Live");
     }
	 
 }
public static void main(String args[]) throws IOException, JSONExceptionassignment{
  Scanner s = new Scanner(System.in);
  System.out.println("Choose the operation to be performed");
  System.out.println("1. CREATE 2.READ 3.DELETE");
  int choice=s.nextInt();
  if(choice==1) {
	  in.Insert();
  }
  else if(choice ==2 ) {
	  in.read();
	  
  }
  else {
	  in.delete();
  }
 
}
 
}
