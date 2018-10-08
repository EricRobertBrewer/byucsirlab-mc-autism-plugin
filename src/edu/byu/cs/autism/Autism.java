package edu.byu.cs.autism;

import edu.byu.cs.autism.friend.FriendMiniGameHistory;
import edu.byu.cs.autism.maze.Maze;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Autism extends JavaPlugin implements Listener {

    private final FriendMiniGameHistory friendMiniGameHistory = new FriendMiniGameHistory();

    //todo: load friendhistory from file
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        friendMiniGameHistory.load(getDataFolder());
    }


    //todo: save friendhistory to file
    @Override
    public void onDisable() {
        friendMiniGameHistory.save(getDataFolder());
    }

    public void sendPromptToPlayersInLocation(Location location, double radius) {
        Player[] players = getServer().getOnlinePlayers().toArray(new Player[0]);
        double radiusSquared = radius*radius;
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a( "{\"text\":\"Welcome to Chat Room\"}"));
        PacketPlayOutTitle packetPlayOutSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a( "{\"text\":\"Use one minute to answer the question in chat.\"}"));
        String prompt = Prompt.getRandomPrompt();
        for (int i = 0; i < players.length; i++) {
            if(players[i].getLocation().distanceSquared(location) <= radiusSquared){
                ((CraftPlayer) players[i]).getHandle().playerConnection.sendPacket(packetPlayOutTitle);
                ((CraftPlayer) players[i]).getHandle().playerConnection.sendPacket(packetPlayOutSubtitle);
                players[i].sendMessage(prompt);
            }
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
            sendPromptToPlayersInLocation(new Location(getServer().getWorld("autism"), 0, 56, 0), 10);
            return true;
        } else  if ("wonGame".equalsIgnoreCase(command.getName())) {
            // TODO Maybe we need to actually raise an event when players finish a mini-game, so other modules can respond to them.
            friendMiniGameHistory.handleCommand(this, sender, command, label, args);
        } else if ("gamesPlayed".equalsIgnoreCase(command.getName())){
            int games = friendMiniGameHistory.totalGamesPlayed(args[0], args[1]);
            this.getServer().dispatchCommand(this.getServer().getConsoleSender(),"They played " + games + " games");

        } else if ("dm".equalsIgnoreCase(command.getName())){
            //sends message to player , arg[0] is playing, following args are the message
            String cmd = "msg ";
            for(int i = 0; i < args.length; i++){
                cmd += args[i];
            }
            getServer().dispatchCommand(getServer().getConsoleSender(),cmd );


        }

        return super.onCommand(sender, command, label, args);
    }
}
