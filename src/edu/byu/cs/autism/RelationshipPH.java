package edu.byu.cs.autism;

import edu.byu.cs.autism.friend.FriendMiniGameHistory;
import edu.byu.cs.autism.friend.RelationshipLevel;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class RelationshipPH extends PlaceholderExpansion  {

    private final FriendMiniGameHistory friendMiniGameHistory;
    private final Map<String, String> activeOthers = new HashMap<>();

    public RelationshipPH(FriendMiniGameHistory friendMiniGameHistory) {
        super();
        this.friendMiniGameHistory = friendMiniGameHistory;
    }

    public void setActiveOther(String player, String other){
        activeOthers.put(player, other);
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Joseph Bills, BYU IR Team";
    }

    @Override
    public String getIdentifier() {
        return "RelationLevel";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        final String playerId = p.getUniqueId().toString();
        final String other = activeOthers.get(playerId);
        final RelationshipLevel relation = RelationshipLevel.getRelationship(playerId, other);
        if ("active_other_level".equalsIgnoreCase(identifier)) {
            if (relation == null) {
                return "";
            }
            return String.valueOf(relation.getLevel(friendMiniGameHistory));
        } else if ("active_other_name".equalsIgnoreCase(identifier)) {
            if (other == null) {
                return "";
            }
            return Bukkit.getServer().getPlayer(UUID.fromString(other)).getDisplayName() + "&0:";
        } else if ("active_other_xp_in_current_level".equalsIgnoreCase(identifier)) {
            if (relation == null) {
                return "0";
            }
            return String.valueOf(relation.getXpInCurrentLevel(friendMiniGameHistory));
        } else if ("active_other_xp_to_next_level".equalsIgnoreCase(identifier)) {
            if (relation == null) {
                return "1";
            }
            return String.valueOf(relation.getXpToNextLevel(friendMiniGameHistory));
        } else if ("active_other_title".equalsIgnoreCase(identifier)) {
            if (relation == null) {
                return "Interact with other players!";
            }
            return "Level";
        }
        return super.onPlaceholderRequest(p, identifier);
    }

}
