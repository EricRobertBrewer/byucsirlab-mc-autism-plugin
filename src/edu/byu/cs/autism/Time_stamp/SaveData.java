package edu.byu.cs.autism.Time_stamp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class SaveData {
    public SaveData(){

    }
    public static void saveBeforeShutDown(File dataFolder){
          ///////////////////////////////////////////////////////////////////
         //REMEBER to change the file path when change the server location//
        ///////////////////////////////////////////////////////////////////
        File file = new File(dataFolder,"PlayLog");
        FileWriter fr = null;
        Instant t = Instant.now();
        try {
            fr = new FileWriter(file, true);
            fr.write("Data saved at " + t + "\r\n");
            fr.write("Total Games That Have Been Played: " + IncermentPlayTime.totalGamePlayed + "\r\n");
            fr.write("Roleplay Game: (" + IncermentPlayTime.roleplay_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.roleplay_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime() + "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("Communication Quiz: (" + IncermentPlayTime.communication_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.communication_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime() + "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("T bridge Game: (" + IncermentPlayTime.T_bridge_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.T_bridge_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime() +  "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("Team Tunnel Game: (" + IncermentPlayTime.team_tunnel_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.team_tunnel_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime() + "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("Multiplayer Maze Game: (" + IncermentPlayTime.multiplayer_maze_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.multiplayer_maze_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime() + "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("Make Friends Game: (" + IncermentPlayTime.makefriend_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.makefriend_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime() + "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("Zombie Maze Game: (" + IncermentPlayTime.zombie_maze_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.zombie_maze_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime() + "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("Voice Game: (" + IncermentPlayTime.voice_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.voice_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime() + "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("Jim's World Game: (" + IncermentPlayTime.Jim_world_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.Jim_world_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime() + "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("Creative World Game: (" + IncermentPlayTime.creative_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.creative_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime()+  "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

             fr.write("Survival World Game: (" + IncermentPlayTime.survival_analyse.size()+ " times)\r\n");
            for(Game i : IncermentPlayTime.survival_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime()+ "\tTime Elapsed: " + i.getTimeElapsed()+ " minutes\r\n");
            }

            fr.write("Other Games: (" + IncermentPlayTime.other_games.size() + " times)\r\n");
            for(Game i : IncermentPlayTime.other_games){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime()+ "\tGame: " + i.getGameName() + "\tTime Elapsed: " + i.getTimeElapsed() + " minutes\r\n");
            }

            fr.write("Still in the game: (" + IncermentPlayTime.playersInGame.size() + " times)\r\n");
            for(Game i : IncermentPlayTime.playersInGame){
                fr.write("Player: " + i.getPlayerName() + "\tTime Started: " + i.getEnterTime()+ "\tGame: " + i.getGameName() + "\tEntered time: " + i.getEnterTime() + " minutes\r\n");
            }
            fr.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
