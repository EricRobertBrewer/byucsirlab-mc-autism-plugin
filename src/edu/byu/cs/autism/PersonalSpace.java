package edu.byu.cs.autism;

import org.bukkit.entity.Player;

public class PersonalSpace {

    final static double INNER_THRESHOLD = 2;
    final static double OUTER_THRESHOLD = 10;

   public static boolean personalSpace(Player a, Player b){

        double distance = a.getLocation().distance(b.getLocation());
        return distance > INNER_THRESHOLD && distance < OUTER_THRESHOLD;
    }

}
