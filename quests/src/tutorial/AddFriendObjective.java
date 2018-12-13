package tutorial;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class AddFriendObjective extends CustomObjective implements Listener {
    private static final String TITLE_OPTION_ADD_FRIEND = "Add friend";
    private static final String ADD_FRIEND_QUEST_NAME = "Add a Friend";

    public AddFriendObjective() {
        this.setName("Add Friend Objective");
        this.setAuthor("Autism");
        this.setEnableCount(true);
        this.setShowCount(true);
        this.setCountPrompt("Number of friends to add: ");
        this.setDisplay("Add friends: %count%");
    }

    @EventHandler
    public void onAddFriend(InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();
        final Quest quest = ((Quests) JavaPlugin.getPlugin(Quests.class)).getQuest(ADD_FRIEND_QUEST_NAME);

        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(TITLE_OPTION_ADD_FRIEND)) {
            player.sendMessage("I should have completed the quest");
            incrementObjective(player, this, 999, quest);
        }
    }
}
