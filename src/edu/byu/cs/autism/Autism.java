package edu.byu.cs.autism;

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

    Map<String,Map<String,Map<String,Integer>>> friendHistory; //game-p1-p2

    //test

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        //initialize map
        friendHistory = new HashMap<String,Map<String, Map<String,Integer>>>();

        //all games
         friendHistory.put("T",new HashMap<>());
         friendHistory.put("team",new HashMap<>());
         friendHistory.put("lava",new HashMap<>());

    }

    @Override
    public void onDisable() {
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
            getServer().broadcastMessage(Prompt.getRandomPrompt());
            return true;
        }

        //todo: settle on data structure to store tuples
        if("wonGame".equalsIgnoreCase(command.getName())){
            //awards money to players based on which game they just won, what games they've won in the past, and who they played with
            //takes three parameters, two players and the game
            String game = args[0];
            String p1 = args [1];
            String p2 = args [2];

            //calculate points earned
            int score = baseScore(game) + newPartnerBonus(game, p1, p2) + getContinuedPartnerBonus(p1, p2, game);
            int p1s = score + firstTimeBonus(game, p1);
            int p2s = score + firstTimeBonus(game, p1);

            //give money
            getServer().dispatchCommand(getServer().getConsoleSender(),"eco give " + p1 + " " + p1s);
            getServer().dispatchCommand(getServer().getConsoleSender(),"eco give " + p2 + " " + p2s);

            //update tuples, single redundancy
            //left
            incrementTuple(game, p1, p2);
            //right
            incrementTuple(game, p2, p1);
        }
        return super.onCommand(sender, command, label, args);
    }

    private void incrementTuple(String game, String p1, String p2){
        Map<String, Map<String, Integer>> gameHistory = friendHistory.get(game);
        //check p1 has played game
        if(gameHistory.containsKey(p1)){
            //retrieve map
            Map<String, Integer> playerHistory = gameHistory.get(p1);

            //see if they've played together before
            if(playerHistory.containsKey(p2)){
                //increment count
                Integer count = playerHistory.get(p2);
                count++;
                playerHistory.put(p2, count);

            } else {
                //create new pair
                playerHistory.put(p2, 1);
            }


        } else {
            //create new map
            Map playerHistory = new HashMap();
            //create new pair
            playerHistory.put(p2, 1);
            gameHistory.put(p1,playerHistory);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a( "{\"text\":\"Welcome!\"}"));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutTitle);


    }




    private int getContinuedPartnerBonus(String p1, String p2, String game){
        //see if players have played this game before
        boolean newgame = false;
        Map<String, Map<String, Integer>> gameHistory = friendHistory.get(game);
        if(gameHistory.containsKey(p1)){
            newgame = !gameHistory.get(p1).containsKey(p2);
        } else newgame = true;

        if(newgame) {
            int count = 0;
            //increase count for each tuple with both players that is found
            for (Map.Entry<String,Map<String,Map<String,Integer>>> entry : friendHistory.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                if (entry.getValue().containsKey(p1)){
                    Map<String, Integer> playerHistory = entry.getValue().get(p1);
                    if(playerHistory.containsKey(p2)){
                        count++;
                    }

                }
            }

            //calculate bonus from count
            switch (count){
                case 1:
                    return 5;
                case 2:
                    return 10;
            }
        }
        return 0;
    }

    private int baseScore(String game){
        if(game.equals("lava")){
            return 0;
        }
        if(game.equals("T")){
            return 5;
        }
        if(game.equals("team")){
            return 2;
        }
        return -1;
    }

    private int firstTimeBonus(String game, String player){
        //check if any tuples contain the player and game
        boolean firstTime = false;
        for (Map.Entry<String,Map<String,Map<String,Integer>>> entry : friendHistory.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (entry.getValue().containsKey(player)){
                firstTime = true;
                break;
            }
        }

        if(firstTime){
            //calculate bonus from game
            if(game.equals("lava")){
                return 5;
            }
            if(game.equals("T")){
                return  10;
            }
            if(game.equals("team")){
                return 7;
            }
        }
        return 0;
    }

    private int newPartnerBonus(String game, String p1, String p2){
        //check if players have played this game before
        boolean newgame = false;
        Map<String, Map<String, Integer>> gameHistory = friendHistory.get(game);
        if(gameHistory.containsKey(p1)){
            newgame = !gameHistory.get(p1).containsKey(p2);
        } else newgame = true;
        if(newgame){
            //calculate bonus from game
            if(game.equals("lava")){
                return 1;
            }
            if(game.equals("T")){
                return  5;
            }
            if(game.equals("team")){
                return 3;
            }
        }
        return 0;
    }
}
