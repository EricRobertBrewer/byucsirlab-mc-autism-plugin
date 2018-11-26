package edu.byu.cs.autism;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogEntry {


    enum Type{START_CONVERSATION, END_CONVERSATION, MADE_EYECONTACT, BROKE_EYECONTACT};

    Type type;

    Date timestamp;

     public LogEntry(String logline){


         if(logline.contains("start")){
             type = Type.START_CONVERSATION;
         } else if(logline.contains("end")) {
             type = Type.END_CONVERSATION;
         } else if(logline.contains("made")){
             type = Type.MADE_EYECONTACT;
         } else if(logline.contains("broke")){
             type = Type.BROKE_EYECONTACT;
         }

         try{

             timestamp =  new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(logline.split("at ")[1]);

         } catch (Exception e){
             
         }





    }







}
