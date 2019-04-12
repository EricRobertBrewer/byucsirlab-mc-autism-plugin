package edu.byu.cs.autism.Time_stamp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveData {
    public SaveData(){

    }
    public static void saveBeforeShutDown(){
          ///////////////////////////////////////////////////////////////////
         //REMEBER to change the file path when change the server location//
        ///////////////////////////////////////////////////////////////////
        File file = new File("C:\\Users\\sophi\\Documents\\byucsirlab-mc-autism-plugin\\src\\FileWriter.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);

            fr.write("Total Games Have Been Payed: " + IncermentPlayTime.totalGamePlayed + "\n");
            fr.write("Roleplay Game: (" + IncermentPlayTime.roleplay_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.roleplay_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("Communication Quiz: (" + IncermentPlayTime.communication_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.communication_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("T bridge Game: (" + IncermentPlayTime.T_bridge_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.T_bridge_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("Team Tunnel Game: (" + IncermentPlayTime.team_tunnel_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.team_tunnel_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("Multiplayer Maze Game: (" + IncermentPlayTime.multiplayer_maze_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.multiplayer_maze_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("Make Friends Game: (" + IncermentPlayTime.makefriend_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.makefriend_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("Zombie Maze Game: (" + IncermentPlayTime.zombie_maze_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.zombie_maze_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("Voice Game: (" + IncermentPlayTime.voice_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.voice_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("Jim's World Game: (" + IncermentPlayTime.Jim_world_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.Jim_world_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("Creative World Game: (" + IncermentPlayTime.creative_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.creative_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

             fr.write("Survival World Game: (" + IncermentPlayTime.survival_analyse.size()+ " times)\n");
            for(Game i : IncermentPlayTime.survival_analyse){
                fr.write("Player: " + i.getPlayerName() + "\tTime Elapsed: " + i.getTimeElapsed()+ "\n");
            }

            fr.write("Other Games: (" + IncermentPlayTime.other_games.size() + " times)\n");
            for(Game i : IncermentPlayTime.other_games){
                fr.write("Player: " + i.getPlayerName() + "\tGame: " + i.getGameName() + "\tTime Elapsed: " + i.getTimeElapsed() + "\n");
            }

            fr.write("Still in the game: (" + IncermentPlayTime.playersInGame.size() + " times)\n");
            for(Game i : IncermentPlayTime.playersInGame){
                fr.write("Player: " + i.getPlayerName() + "\tGame: " + i.getGameName() + "\tEntered time: " + i.getEnterTime() + "\n");
            }
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
