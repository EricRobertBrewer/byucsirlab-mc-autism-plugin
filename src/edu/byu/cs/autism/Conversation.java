package edu.byu.cs.autism;

import org.bukkit.entity.Player;

import java.util.List;

public class Conversation {

    Player one;
    Player two;

    static  List<Conversation> conversations;

    static public void testConversations(){
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
    }

    boolean active = true;

    final double GRACE_SPAN = 100; //five seconds

    double gracePoints = GRACE_SPAN;



    void update(){
        if(EyeContact.eyeContact(one,two)){ //later also check for distance, if speaking
            gracePoints = GRACE_SPAN;
        } else {
            gracePoints--;
        }

        if(gracePoints <= 0){
            active = false;
            one.sendMessage("Your conversation with " + two.getDisplayName() + " has ended.");
            two.sendMessage( "Your conversation with " + one.getDisplayName() + " has ended.");
        }

    }



}
