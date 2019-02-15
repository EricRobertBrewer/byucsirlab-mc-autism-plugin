package edu.byu.cs.autism;

import edu.byu.cs.autism.friend.RelationshipLevel;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Conversation {

    Player one;
    Player two;

    boolean afk1;
    boolean afk2;

    private static  List<Conversation> conversations;

    static private void testConversations(){
        if(conversations == null) conversations = new ArrayList<>();

        //should be called each tick, somehow
        for(int i = conversations.size() - 1; i >= 0; i--){
            conversations.get(i).update();
            if(!conversations.get(i).active) {
                conversations.remove(i);
            }
        }
    }

    //getConversation(Player one, Player two)
   public static void handleCommand(String command){
        testConversations();
    }

    public Conversation(Player one, Player two) {
        this.one = one;
        this.two = two;

        //create thread
        //create new router
        //connect audio to user

       // new Router().connectTo(two.getAddress().getAddress().toString());

        Bukkit.getLogger().log(Level.INFO, "Making connection" );


        Router.addressPair.put(one.getAddress().toString(), two.getAddress().toString());
        Router.addressPair.put(two.getAddress().toString(), one. getAddress().toString());

        Bukkit.getLogger().log(Level.INFO, "Connection made" );


        RelationshipLevel.initiateConversation(one.getUniqueId().toString(), two.getUniqueId().toString());


    }

    boolean active = true;

    final int GRACE_SPAN = 100; //five seconds
    final static int CHECK_POINT = 1200;


    int gracePoints = GRACE_SPAN;



    int time = 0;

    boolean makingEyecontact = false;

    boolean checkPointReached = false;


    public static void add(Player a, Player b){


        //standardize order

        Player one;
        Player two;

        if(a.getUniqueId().toString().compareTo(b.getUniqueId().toString()) > 0){
            one = a;
            two = b;
        } else {
            one = b;
            two = a;
        }

        //check that conversation is not already in list

        boolean alreadyIn = false;

        if(conversations == null) conversations = new ArrayList<>();

        for(int i = 0; i < conversations.size() && !alreadyIn; i++){
            if(conversations.get(i).one.equals(one) && conversations.get(i).two.equals(two)){
                alreadyIn = true;
            }

        }
        if(!alreadyIn){
            conversations.add(new Conversation(one,two));
        }

        Bukkit.getLogger().log(Level.INFO, "Convo added" );

    }



    @EventHandler
    private  void onAFK(AfkStatusChangeEvent e){

        //note - how to handle if conversation initiated with AFK user?

        if(e.getAffected().getBase().equals(one)){

           afk1 = e.getAffected().isAfk();

        }

        if(e.getAffected().getBase().equals(two)){
            afk2 = e.getAffected().isAfk();
        }


    }

    void update(){






        if(time == 0){
            log(new File("LogFolder"),one.getUniqueId().toString() + "_" + two.getUniqueId().toString(),
                    "Conversation started at " + new Date().toString());
        }

        time++;

        if(EyeContact.eyeContact(one,two) && !makingEyecontact) {
            log(new File("LogFolder"),one.getUniqueId().toString() + "_" + two.getUniqueId().toString(),
                    "Eye Contact Made at " + new Date().toString());
            makingEyecontact = true;
        }

        if(!EyeContact.eyeContact(one,two) && makingEyecontact) {
            log(new File("LogFolder"),one.getUniqueId().toString() + "_" + two.getUniqueId().toString(),
                    "Eye Contact broken at " + new Date().toString());
            makingEyecontact = false;
        }



        if(time > CHECK_POINT && !checkPointReached){
            RelationshipLevel.checkpoint(one.getUniqueId().toString(), two.getUniqueId().toString());
            one.sendMessage("Your conversation with " + two.getDisplayName() + " has gone on for awhile.");
            checkPointReached = true;
        }

        if(EyeContact.eyeContact(one,two) && PersonalSpace.personalSpace(one,two) && !afk2 && !afk1){ //later also check if speaking
            gracePoints = GRACE_SPAN;
            RelationshipLevel.converse(one.getUniqueId().toString(), two.getUniqueId().toString());

        } else {
            gracePoints--;
        }

        if(gracePoints <= 0){
            active = false;
            one.sendMessage("Your conversation with " + two.getDisplayName() + " has ended.");
            two.sendMessage( "Your conversation with " + one.getDisplayName() + " has ended.");
            //log end of conversation
            log(new File("LogFolder"),one.getUniqueId().toString() + "_" + two.getUniqueId().toString(),
                    "Conversation ended at " + new Date().toString());
        }

    }


    public void log(File dataFolder, String log, String message ){

        try {

            if(!dataFolder.exists() && !dataFolder.mkdirs()){
                System.err.println("Folder failed");
                return;
            }

            final File logFile = new File(dataFolder, log  );
            if (!logFile.exists() && !logFile.createNewFile()) {
                Bukkit.getLogger().log(Level.INFO, "Can't create mini-game file: `" + logFile.getName() + "`.");
                return;
            }
            if (!logFile.canWrite() && !logFile.setWritable(true)) {
                Bukkit.getLogger().log(Level.INFO, "Can't write to mini-game file: `" + logFile.getName() + "`.");
                return;
            }
            final OutputStream outputStream = new FileOutputStream(logFile, true);
            final PrintStream out = new PrintStream(outputStream);

            out.println(message);
            out.close();
            outputStream.close();

        } catch (Exception e){

        }

    }


}
