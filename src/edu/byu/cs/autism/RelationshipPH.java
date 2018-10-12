package edu.byu.cs.autism;

import edu.byu.cs.autism.friend.RelationshipLevel;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;


public class RelationshipPH extends PlaceholderExpansion  {

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
    public String getPlugin() {
        return null;
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    public String onPlaceholderRequest(Player p, String identifier) {

        //will probably have to rework
        return RelationshipLevel.getRelationship(p.getUniqueId().toString(), identifier).toString();

        return null;
    }

}
