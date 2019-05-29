package edu.byu.cs.autism.Time_stamp;

import edu.byu.cs.autism.CommunicationQuiz.Communication_Quiz;
import edu.byu.cs.autism.minigame.Minigames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;

import static edu.byu.cs.autism.Time_stamp.IncermentPlayTime.*;
import static org.bukkit.Bukkit.getServer;

public class PlayTimeRecord {
    public static void handleCommand(JavaPlugin plugin, CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage("[Count] Usage: /count <game> <status> <player>");
            return;
        }
        if (plugin.getServer().getPlayer(args[2]) == null) {
            sender.sendMessage("Unable to find player with name `" + args[2] + "`.");
            return;
        }
        //get game name
        String game = args[0];
        // Get player name.
        String pp = plugin.getServer().getPlayer(args[2]).getName();
        String p = Register.users.get(pp);
        //time
        Instant start = Instant.now();

        // Enter a new game
        if (args[1].equalsIgnoreCase("enter")) {

//            for(Game i : playersInGame){
//                //if the sender didn't leave from other games, kick him out
//                if (i.getPlayerName().equals(p)){
//                    Leave(p);
//                }
//                //if other players in the current game, kick them out
//                if(i.getGameName().equals(game)){
//                    Player otherPlayerInGame = getServer().getPlayer(i.getPlayerName());
//                    otherPlayerInGame.sendMessage("Other players want to play " + i.getGameName() + ", try some other games");
//                    getServer().dispatchCommand(otherPlayerInGame,  "spawn");
//                    Game g = getGameWithPlayer(i.getPlayerName());
//                    Leave(g.getPlayerName());
//                }
//            }
            Game g = getGameWithPlayer(p);
           if( g != null){
               Leave(p);
           }

//            Game g1 = getGameWithGameName(game);
//           if(g1 != null){
//               Player otherPlayerInGame = getServer().getPlayer(g1.getPlayerName());
//               otherPlayerInGame.sendMessage("Other players want to play " + g1.getGameName() + ", try some other games");
//               getServer().dispatchCommand(otherPlayerInGame,  "spawn");
//               Leave(g1.getPlayerName());
//
//           }

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
    public static void Leave(String p) {
        Instant end = Instant.now();
        Game game = getGameWithPlayer(p);
        if (game != null) {
            long timeElapsed = Duration.between(game.getEnterTime(), end).toMinutes();
            game.setTimeElapsed(timeElapsed);
            SwatchCaseForLeave(game);
        }
    }

    private static Game getGameWithPlayer(String p) {
        for (Game i : playersInGame) {
            if (i.getPlayerName().equals(p)) {
                return i;
            }
        }
        return null;
    }
    private static Game getGameWithGameName(String g) {
        for (Game i : playersInGame) {
            if (i.getGameName().equals(g)) {
                return i;
            }
        }
        return null;
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
