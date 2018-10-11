package edu.byu.cs.autism.friend;

//import com.sun.org.apache.xpath.internal.operations.String;

import java.util.Map;

public class RelationshipLevel {
    static Map<String, Map<String, RelationshipLevel>> relationships;

    static final int GAME_WEIGHT = 5;
    static final int INITIATE_WEIGHT = 1;


    private static RelationshipLevel  getRelationship(String p1, String p2){
       return relationships.get(p1).get(p2);
    }



    String p1;
    String p2;
    int exp;

    public int getLevel(String p1, String p2, FriendMiniGameHistory history){

         return expToLevel(exp + GAME_WEIGHT * history.getTotalGamesPlayed(p1, p2));

    }

    private static  int expToLevel(int exp){
        return exp/GAME_WEIGHT;
    }

    public static void initiateConversation(String p1, String p2){
        getRelationship(p1, p2).exp += INITIATE_WEIGHT;
    }


}
