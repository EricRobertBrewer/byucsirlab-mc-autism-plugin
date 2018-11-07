package edu.byu.cs.autism;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.byu.cs.autism.friend.FriendMiniGameHistory;
import edu.byu.cs.autism.friend.RelationshipLevel;
import edu.byu.cs.autism.minigame.Maze;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetSocketAddress;
import java.util.List;

public class Autism extends JavaPlugin implements Listener {


    List<Conversation> conversations;

    public void testConversations(){
        //should be called each tick, somehow
        for(int i = conversations.size() - 1; i >= 0; i--){
            conversations.get(i).update();
            if(!conversations.get(i).active) {
                conversations.remove(i);
            }
        }
    }

    private final FriendMiniGameHistory friendMiniGameHistory = new FriendMiniGameHistory();
    RelationshipPH rph;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        friendMiniGameHistory.load(getDataFolder());
         rph =  new RelationshipPH(friendMiniGameHistory);rph.register();


        try {

            HttpServer communicationServer = HttpServer.create(new InetSocketAddress(8000), 0);
            communicationServer.createContext("thing", (HttpHandler) new CommunicationHandler());


        } catch (Exception e){

        }
    }

    @Override
    public void onDisable() {
        friendMiniGameHistory.save(getDataFolder());
    }

    @EventHandler
    private void onPlayerEntityInteraction(PlayerInteractEntityEvent e) {
        final Player player = e.getPlayer();
        final Entity entity = e.getRightClicked();
        if (entity instanceof Player) {
            final Player other = (Player) entity;
            player.sendMessage("I click the player " + other.getDisplayName());
            other.sendMessage("You were clicked on by " + e.getPlayer().getDisplayName());

            if(EyeContact.eyeContact(player, other)){
                player.sendMessage("Eye contact made");
            } else {
                player.sendMessage("Eye contact NOT made");

            }

            player.sendMessage("Vector to other: " + EyeContact.eyeToeye(player,other).toString());
            player.sendMessage( "Direction vector: " + EyeContact.direction(player.getEyeLocation().getPitch(), player.getEyeLocation().getYaw()).toString());
            player.sendMessage("Cosine Sim: " + EyeContact.cossime(EyeContact.eyeToeye(player,other),EyeContact.direction(player.getEyeLocation().getPitch(), player.getEyeLocation().getYaw())));

            conversations.add(new Conversation(player, other));

            RelationshipLevel.initiateConversation(player.getUniqueId().toString(), other.getUniqueId().toString());
            rph.setActiveOther(player.getUniqueId().toString(), other.getUniqueId().toString());
            rph.setActiveOther(other.getUniqueId().toString(), player.getUniqueId().toString());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ubb reload");

            player.getLocation();
            player.getEyeLocation();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ("spawn".equalsIgnoreCase(command.getName())) {
            if (!(sender instanceof Player)) {
                return false;
            }
            final Player player = (Player) sender;
            final Location originFacingNorth = Bukkit.getWorld(WorldNames.AUTISM).getSpawnLocation();
            originFacingNorth.setYaw(180.0f);
            player.teleport(originFacingNorth);
            if (!player.isOp()) {
                player.setGameMode(GameMode.ADVENTURE);
            }
            return true;
        } else if ("maze".equalsIgnoreCase(command.getName())) {
            Maze.handleCommand(sender, command, label, args);
            return true;
        } else if ("prompt".equalsIgnoreCase(command.getName())) {
            Prompt.sendPromptToPlayersInLocation(this, new Location(getServer().getWorld("autism"), 0, 56, 0), 10);
            return true;
        } else  if ("wonGame".equalsIgnoreCase(command.getName())) {
            // TODO Maybe we need to actually raise an event when players finish a mini-game, so other modules can respond to them.
            friendMiniGameHistory.handleCommand(this, sender, command, label, args);
        } else if ("gamesPlayed".equalsIgnoreCase(command.getName())) {
            int games = friendMiniGameHistory.getTotalGamesPlayed(args[0], args[1]);
            this.getServer().dispatchCommand(this.getServer().getConsoleSender(),"They played " + games + " games");
        } else if ("dm".equalsIgnoreCase(command.getName())) {
            //sends message to player , arg[0] is playing, following args are the message
            StringBuilder cmd = new StringBuilder("msg ");
            for (String arg : args) {
                cmd.append(arg);
            }
            getServer().dispatchCommand(getServer().getConsoleSender(), cmd.toString());
        } else if ("eyeTest".equalsIgnoreCase(command.getName())){

            Player player = getServer().getPlayer(sender.getName());
            sender.sendMessage("Center: " + player.getLocation().toString() + " Eye: " + player.getEyeLocation().toString());
        }
        return super.onCommand(sender, command, label, args);
    }
}
