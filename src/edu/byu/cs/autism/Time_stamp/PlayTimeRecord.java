package edu.byu.cs.autism.Time_stamp;

import edu.byu.cs.autism.minigame.Minigames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;

import static edu.byu.cs.autism.Time_stamp.IncermentPlayTime.*;

public class PlayTimeRecord {
    public static void handleCommand(JavaPlugin plugin, CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 3) {
            sender.sendMessage("[Count] Usage: /count <game> <status> <player>");
        }
        if (plugin.getServer().getPlayer(args[2]) == null) {
            sender.sendMessage("Unable to find player with name `" + args[2] + "`.");
            return;
        }
        //get game name
        String game = args[0];
        // Get player name.
        String p = args[2];
        //time
        Instant start = Instant.now();

        // Enter a new game
        if (args[1].equalsIgnoreCase("enter")) {
            Game enterGame = new Game(game, p, start);
            playersInGame.add(enterGame);
            totalGamePlayed++;
        }
        // left the game
        else if(args[1].equalsIgnoreCase("leave")){
            Leave(p);
        }
        else{
            sender.sendMessage("Wrong status, [usage]: enter/leave");
        }
    }
    public static void Leave(String p){
        Instant end = Instant.now();
        for (Game i : playersInGame) {
            if (i.getPlayerName().equals(p)) {
                Long timeElapsed = Duration.between(i.getEnterTime(), end).toMinutes();
                i.setTimeElapsed(timeElapsed);
                SwatchCaseForLeave(i);
            }
        }
    }
    private static void SwatchCaseForLeave(Game i){
        switch (i.getGameName()){
            case "roleplay":
                roleplay_analyse.add(i);
                break;
            case "communication":
                communication_analyse.add(i);
                break;
            case "T-bridge":
                T_bridge_analyse.add(i);
                break;
            case "Team-tunnel":
                team_tunnel_analyse.add(i);
                break;
            case "multiplayer-maze":
                multiplayer_maze_analyse.add(i);
                break;
            case "makefriend":
                makefriend_analyse.add(i);
                break;
            case "zombie-maze":
                zombie_maze_analyse.add(i);
                break;
            case "voice":
                voice_analyse.add(i);
                break;
            case "Jim-world":
                Jim_world_analyse.add(i);
                break;
            case "creative":
                creative_analyse.add(i);
                break;
            case "survival":
                survival_analyse.add(i);
                break;
            default:
                other_games.add(i);
                break;
        }
        playersInGame.remove(i);

    }
}
