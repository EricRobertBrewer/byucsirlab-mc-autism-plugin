package tutorial;

//import me.blackvein.quests.CustomObjective;
//import me.blackvein.quests.Quest;
//import me.blackvein.quests.Quests;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Pig;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerInteractEntityEvent;
//import org.bukkit.inventory.EquipmentSlot;
//import org.bukkit.plugin.java.JavaPlugin;

//
//public class GetToKnowFriendObjective extends CustomObjective implements Listener {
//    private static final String GET_TO_KNOW_FRIEND_QUEST_NAME = "Get To Know Friend";
//
//    public GetToKnowFriendObjective() {
//        this.setName("Get To Know Friend Objective");
//        this.setAuthor("Autism");
//        this.setEnableCount(true);
//        this.setShowCount(true);
//        this.setCountPrompt("Number of friends to get to know: ");
//        this.setDisplay("Acquire friends: %count%");
//    }
//
//    @EventHandler
//    public void onPlayerGetToKnowFriend(PlayerInteractEntityEvent e) {
//        if (e.getHand() == EquipmentSlot.OFF_HAND) {
//            return;
//        }
//        final Player player = e.getPlayer();
//        final Entity entity = e.getRightClicked();
//        final Quest quest = ((Quests) JavaPlugin.getPlugin(Quests.class)).getQuest(GET_TO_KNOW_FRIEND_QUEST_NAME);
//
//        if (entity instanceof Pig) {
//            incrementObjective(player, this, 1, quest);
//        }
//    }
//}
