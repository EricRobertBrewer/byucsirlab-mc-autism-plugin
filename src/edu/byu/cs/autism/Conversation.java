package edu.byu.cs.autism;

import org.bukkit.entity.Player;

public class Conversation {

    Player one;
    Player two;

    public Conversation(Player one, Player two) {
        this.one = one;
        this.two = two;
    }

    boolean active = true;

    final double GRACE_SPAN = 100; //five seconds

    double gracePoints = GRACE_SPAN;



    void update(){
        if(EyeContact.eyeContact(one,two)){ //later also check for distance
            gracePoints = GRACE_SPAN;
        } else {
            gracePoints--;
        }

        if(gracePoints <= 0){
            active = false;
        }

    }

}
