package edu.byu.cs.autism;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.xml.internal.bind.v2.TODO;
import edu.byu.cs.autism.CommunicationQuiz.FamilyHint;
import edu.byu.cs.autism.CommunicationQuiz.Quiz;
import edu.byu.cs.autism.Time_stamp.*;
import edu.byu.cs.autism.friend.FriendMiniGameHistory;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;

import static edu.byu.cs.autism.Time_stamp.IncermentPlayTime.playersInGame;
import static edu.byu.cs.autism.Time_stamp.SaveData.saveBeforeShutDown;
import static org.bukkit.Bukkit.getServer;

public class Autism extends JavaPlugin implements Listener {

    private final FriendMiniGameHistory friendMiniGameHistory = new FriendMiniGameHistory();
    private final FamilyHint family_hint = new FamilyHint();
    private final IncermentPlayTime incermentPlayTime = new IncermentPlayTime();
    private final SaveData saveData = new SaveData();

    RelationshipPH rph;
    private final PlayerMenu playerMenu = new PlayerMenu();

    @Override
    public void onEnable() {
        family_hint.popHint();
        family_hint.TimerForData(getDataFolder());

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(playerMenu, this);
        friendMiniGameHistory.load(getDataFolder());
//         rph =  new RelationshipPH(friendMiniGameHistory);
//         rph.register();


        try {

            HttpServer communicationServer = HttpServer.create(new InetSocketAddress(8000), 0);
            communicationServer.createContext("thing", (HttpHandler) new CommunicationHandler());


        } catch (Exception e){

        }
    }

    @Override
    public void onDisable() {
        // TODO Save Data
        SaveData.saveBeforeShutDown(getDataFolder());
        friendMiniGameHistory.save(getDataFolder());
    }

    @EventHandler
    public void onDeath (PlayerDeathEvent e){
        Player player = e.getEntity();
        String p = player.getName();
//        PlayTimeRecord.Leave(p);
        //getServer().dispatchCommand(player,  "spawn");
        System.out.println("DIE");
        //Note: Need to set respawn world on ScenicView server
    }

    @EventHandler
    public void onRespawn (PlayerRespawnEvent e){
        Player player = e.getPlayer();
        String p = player.getName();
//        PlayTimeRecord.Leave(p);
        //getServer().dispatchCommand(player,  "spawn");
        System.out.println("LIVE");

    }


    @EventHandler
    public void onLogout (PlayerQuitEvent e){
        Player player = e.getPlayer();
        if(Register.users.containsKey(player.getName())){
            getServer().dispatchCommand(player,  "spawn");
            Register.users.remove(player.getName());
        }

        System.out.println("LOGOUT");
    }

    @EventHandler
    public void onLogon (PlayerJoinEvent e){
        Player player = e.getPlayer();
        getServer().dispatchCommand(player,  "tp 10 58 39");
        System.out.println("LOGIN");
    }

    @EventHandler
    private void onPlayerEntityInteraction(PlayerInteractEntityEvent e) {
        final Player player = e.getPlayer();
        final Entity entity = e.getRightClicked();
        if (entity instanceof Player) {
            final Player other = (Player) entity;
            playerMenu.openMenu(player, other);
            //player.sendMessage("I click the player " + other.getDisplayName());
//            other.sendMessage("You were clicked on by " + e.getPlayer().getDisplayName());

            /*
            if(EyeContact.eyeContact(player, other)){
                player.sendMessage("Eye contact made");
            } else {
                player.sendMessage("Eye contact NOT made");
            }*/

            //player.sendMessage("Vector to other: " + EyeContact.eyeToeye(player,other).toString());
            //player.sendMessage( "Direction vector: " + EyeContact.direction(player.getEyeLocation().getPitch(), player.getEyeLocation().getYaw()).toString());
            //player.sendMessage("Cosine Sim: " + EyeContact.cossime(EyeContact.eyeToeye(player,other),EyeContact.direction(player.getEyeLocation().getPitch(), player.getEyeLocation().getYaw())));



//            rph.setActiveOther(player.getUniqueId().toString(), other.getUniqueId().toString());
//            rph.setActiveOther(other.getUniqueId().toString(), player.getUniqueId().toString());
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
            if(!Register.users.containsKey(sender.getName())){
                return false;
            }
            final Player player = (Player) sender;
            final Location originFacingNorth = Bukkit.getWorld(WorldNames.AUTISM).getSpawnLocation();
            originFacingNorth.setYaw(180.0f);
            player.teleport(originFacingNorth);
            if (!player.isOp()) {
                player.setGameMode(GameMode.ADVENTURE);
            }
//Time stamp
            PlayTimeRecord.Leave(sender.getName());

            return true;
        } else if("count".equalsIgnoreCase(command.getName())){
            PlayTimeRecord.handleCommand(this, sender, command, label, args);
            return true;
        } else if ("quiz".equalsIgnoreCase(command.getName())){
//            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),  "say trigger quiz");
            Quiz.handleCommand(sender, command, label, args);
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
        }  else if ("testConversations".equalsIgnoreCase(command.getName())){

            Conversation.handleCommand("testConversations");
        } else if ("tellServer".equalsIgnoreCase(command.getName())){

            for(String arg : args){
                System.out.print(args[0]);
            }
            System.out.println();

        }  else if ("register".equalsIgnoreCase(command.getName())){
            Register.handleCommand(this, sender, command, label, args);
        }


        String string = "horse";

        return super.onCommand(sender, command, label, args);
    }

}
