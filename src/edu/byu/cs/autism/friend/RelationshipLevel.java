package edu.byu.cs.autism.friend;

import java.util.HashMap;
import java.util.Map;

public class RelationshipLevel {
    static Map<String, Map<String, RelationshipLevel>> relationships = null;

    //constants
    static final int GAME_WEIGHT = 1200;
    static final int INITIATE_WEIGHT = 10;
    static final int CONVERSE_WEIGHT = 1;
    static final int CHECK_POINT_WEIGHT = 1000;


    //unique ids
    String p1;
    String p2;
    /**
     * Cumulative. It does NOT reset back down to 0.
     */
    int exp;


    public RelationshipLevel(String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;
        exp = 0;
    }

    public static RelationshipLevel getRelationship(String p1, String p2){

//        if(p2 == null) return new RelationshipLevel(p1, "(No Play Selected)");
        if(p1 == null || p2 == null) return null;

        //ensure relations has been initialized
        if(relationships == null){
            createRelation(p1, p2, 3);
        } else {
            //ensure p1 is in table
            if(relationships.containsKey(p1)){
                //ensure relation is in table
                if(!relationships.get(p1).containsKey(p2)){
                    createRelation(p1, p2, 1);
                }
            } else {
              createRelation(p1, p2, 2);
            }
        }



       return relationships.get(p1).get(p2);
    }

    private static void createRelation(String p1, String p2, int depth){
        switch (depth){
            case 3:
            relationships = new HashMap<>();
            case 2:
            relationships.put(p1, new HashMap<>());
            case 1:
            relationships.get(p1).put(p2, new RelationshipLevel(p1, p2));
        }
    }




    public int getLevel(FriendMiniGameHistory history){
         return expToLevel(exp + GAME_WEIGHT * history.getTotalGamesPlayed(p1, p2));
    }

    public int getXpToNextLevel(FriendMiniGameHistory history) {
        return getXpOfNextLevel(history) - exp;
    }

    public int getXpForNextLevel(FriendMiniGameHistory history) {
        return getXpOfNextLevel(history) - getXpOfCurrentLevel(history);
    }


    public int getXpOfNextLevel(FriendMiniGameHistory history){
        return levelToExp(getLevel(history) + 1);
    }

    public int getXpInCurrentLevel(FriendMiniGameHistory history) {
        return exp - getXpOfCurrentLevel(history);
    }

    public int getXpOfCurrentLevel(FriendMiniGameHistory history) {
        return levelToExp(getLevel(history));
    }

    private static int expToLevel(int exp){
        return exp/GAME_WEIGHT;
    }

    private static int levelToExp(int level){
        return level * GAME_WEIGHT;
    }



    public static void initiateConversation(String p1, String p2){
        getRelationship(p1, p2).exp += INITIATE_WEIGHT;
        getRelationship(p2, p1).exp += INITIATE_WEIGHT;
    }

    public static void converse(String p1, String p2){
        getRelationship(p1, p2).exp += CONVERSE_WEIGHT;
        getRelationship(p2, p1).exp += CONVERSE_WEIGHT;
    }

    public static void checkpoint(String p1, String p2){
        getRelationship(p1, p2).exp += CHECK_POINT_WEIGHT;
        getRelationship(p2, p1).exp += CHECK_POINT_WEIGHT;
    }





}
