package edu.byu.cs.autism;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LogFileParser {



    LogEntry getEntry(File log, int line){

        try {
            Scanner scanner = new Scanner(log);

            for (int i = 1; i < line; i++) {
                scanner.nextLine();
            }


            new LogEntry(scanner.nextLine());

        } catch (Exception e){

        }
        return  null;
    }

    List<LogEntry> getClassOfEntry(File log, LogEntry.Type type){
        List<LogEntry> entries = new LinkedList<>();
        int i = 0;
        LogEntry entry = null;
        do{
            entry = getEntry(log,i);
            i++;
            if(entry.type == type){
                entries.add(entry);
            }
        } while (entry!= null);
        return  entries;
    }

}
