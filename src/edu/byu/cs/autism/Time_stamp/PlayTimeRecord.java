package edu.byu.cs.autism.Time_stamp;

import edu.byu.cs.autism.minigame.Minigames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalTime;

import static edu.byu.cs.autism.Time_stamp.IncermentPlayTime.*;

public class PlayTimeRecord {
    public void handleCommand(JavaPlugin plugin, CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 3) {
            sender.sendMessage("[Count] Usage: /count <game> <status> <player>");
        }
        if (plugin.getServer().getPlayer(args[1]) == null) {
            sender.sendMessage("Unable to find player with name `" + args[1] + "`.");
            return;
        }
        //get game name
        final String game = args[0];
        // Get UUIDs.
        final String p1 = plugin.getServer().getPlayer(args[2]).getUniqueId().toString();
        final LocalTime time = java.time.LocalTime.now();

        // Enter a new game
        if (args[1].equalsIgnoreCase("enter")) {
            if (game.equalsIgnoreCase("roleplay")) {
                IncermentPlayTime.roleplay++;
//            Game enterGame = new Game(game, p1, time);
//            roleplay_players.add(enterGame);
            } else if (game.equalsIgnoreCase("communication")) {
                IncermentPlayTime.communication++;
//            Game enterGame = new Game(game, p1, time);
//            communication_players.add(enterGame);
            } else if (game.equalsIgnoreCase("T-bridge")) {
                IncermentPlayTime.T_bridge++;
//            Game enterGame = new Game(game, p1, time);
//            T_bridge_players.add(enterGame);
            } else if (game.equalsIgnoreCase("Team-tunnel")) {
                IncermentPlayTime.team_tunnel++;
            } else if (game.equalsIgnoreCase("multiplayer-maze")) {
                IncermentPlayTime.multiplayer_maze++;
            } else if (game.equalsIgnoreCase("makefriend")) {
                IncermentPlayTime.makefriend++;
            } else if (game.equalsIgnoreCase("zombie-maze")) {
                IncermentPlayTime.zombie_maze++;
            } else if (game.equalsIgnoreCase("voice")) {
                IncermentPlayTime.voice++;
            } else if (game.equalsIgnoreCase("Jim-world")) {
                IncermentPlayTime.Jim_world++;
            } else if (game.equalsIgnoreCase("creative")) {
                IncermentPlayTime.creative++;
            } else if (game.equalsIgnoreCase("survival")) {
                IncermentPlayTime.survival++;
            } else {
                sender.sendMessage("Invalid game name: `" + game + "`.");
                return;
            }
            Game enterGame = new Game(game, p1, time);
            playersInGame.add(enterGame);
        }
        // left the game
        else if(args[1].equalsIgnoreCase("leave")){
            for (Game i : playersInGame){
                if (i.getPlayerName().equals(p1)){
                    // TODO save the data
//                    count how long the user played
//                    LocalTime playedHowLong = java.time.LocalTime.now() - time;
                    playersInGame.remove(i);
                }
            }
        }
    }
}
