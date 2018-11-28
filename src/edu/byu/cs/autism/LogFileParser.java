package edu.byu.cs.autism;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LogFileParser {


    File log;


    List<LogEntry> conversationStarts = null;
    List<LogEntry> conversationEnds = null;
    List<LogEntry> eyeContactStarts = null;
    List<LogEntry> eyeContactEnds = null;


    public LogFileParser(Player a, Player b){

    }

    public LogFileParser(File log) {
        this.log = log;
    }

    LogEntry getEntry(int line){

        LogEntry entry = null;
        try {
            Scanner scanner = new Scanner(log);

            for (int i = 1; i < line; i++) {
                scanner.nextLine();
            }


             entry =  new LogEntry(scanner.nextLine());

        } catch (Exception e){

        }
        return  entry;
    }

    List<LogEntry> getClassOfEntry( LogEntry.Type type) {
        List<LogEntry> entries = null;
        switch (type) {
            case MADE_EYECONTACT:
                entries = eyeContactStarts;
                break;
            case BROKE_EYECONTACT:
                entries = eyeContactEnds;
                break;
            case START_CONVERSATION:
                entries = conversationStarts;
                break;
            case END_CONVERSATION:
                entries = conversationEnds;
                break;
        }
        if (entries != null) {
            return entries;
        } else {

            entries = new LinkedList<>();
            int i = 0;
            LogEntry entry = null;
            do {
                entry = getEntry( i);
                i++;
                if (entry != null && entry.type == type) {
                    entries.add(entry);
                }
            } while (entry != null);

            switch (type) {
                case MADE_EYECONTACT:
                    eyeContactStarts = entries;
                    break;
                case BROKE_EYECONTACT:
                    eyeContactEnds = entries;
                    break;
                case START_CONVERSATION:
                    conversationStarts = entries;
                    break;
                case END_CONVERSATION:
                    conversationEnds = entries;
                    break;
            }

            return entries;
        }
    }

    double getConversationLength( int num){

        Date start = getClassOfEntry(LogEntry.Type.START_CONVERSATION).get(num).timestamp;
        Date end = getClassOfEntry(LogEntry.Type.END_CONVERSATION).get(num).timestamp;

        return  end.getTime() - start.getTime();

    }



    double getTotalConversationLength(){

        int numCons = getClassOfEntry(LogEntry.Type.END_CONVERSATION).size();
        int sum = 0;
        for(int i = 0; i < numCons ; i++){
          sum += getConversationLength(i) ;
        }

        return sum/1000;
    }

}
