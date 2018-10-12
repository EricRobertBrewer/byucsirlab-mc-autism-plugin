package edu.byu.cs.autism.friend;

//import com.sun.org.apache.xpath.internal.operations.String;

import java.util.HashMap;
import java.util.Map;

public class RelationshipLevel {
    static Map<String, Map<String, RelationshipLevel>> relationships = null;

    static final int GAME_WEIGHT = 5;
    static final int INITIATE_WEIGHT = 1;

    public RelationshipLevel(String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;
        exp = 0;
    }

    public static RelationshipLevel  getRelationship(String p1, String p2){

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
        getRelationship(p2, p1).exp += INITIATE_WEIGHT;
    }


}
