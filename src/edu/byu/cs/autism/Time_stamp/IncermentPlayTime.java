package edu.byu.cs.autism.Time_stamp;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.List;

public class IncermentPlayTime {
    public static List<Game> playersInGame = new ArrayList<>();

    public static List<Game> roleplay_analyse = new ArrayList<>();
    public static List<Game> communication_analyse = new ArrayList<>();
    public static List<Game> T_bridge_analyse = new ArrayList<>();
    public static List<Game> team_tunnel_analyse = new ArrayList<>();
    public static List<Game> multiplayer_maze_analyse = new ArrayList<>();
    public static List<Game> makefriend_analyse = new ArrayList<>();
    public static List<Game> zombie_maze_analyse = new ArrayList<>();
    public static List<Game> voice_analyse = new ArrayList<>();
    public static List<Game> Jim_world_analyse = new ArrayList<>();
    public static List<Game> creative_analyse = new ArrayList<>();
    public static List<Game> survival_analyse = new ArrayList<>();

    public static List<Game> other_games = new ArrayList<>();

    public static Long totalGamePlayed = Long.valueOf(0);

    public IncermentPlayTime(){
//        playersInGame = new ArrayList<>();
//        roleplay_analyse = new ArrayList<>();
//        communication_analyse = new ArrayList<>();
//        T_bridge_analyse = new ArrayList<>();
//        team_tunnel_analyse = new ArrayList<>();
//        multiplayer_maze_analyse = new ArrayList<>();
//        makefriend_analyse = new ArrayList<>();
//        zombie_maze_analyse = new ArrayList<>();
//        voice_analyse = new ArrayList<>();
//        Jim_world_analyse = new ArrayList<>();
//        creative_analyse = new ArrayList<>();
//        survival_analyse = new ArrayList<>();
//        other_games = new ArrayList<>();
    }

    public static void Clear(){
        roleplay_analyse.clear();
        communication_analyse.clear();
        T_bridge_analyse.clear();
        team_tunnel_analyse.clear();
        multiplayer_maze_analyse.clear();
        makefriend_analyse.clear();
        zombie_maze_analyse.clear();
        voice_analyse.clear();
        Jim_world_analyse.clear();
        creative_analyse.clear();
        survival_analyse.clear();

        other_games.clear();

        totalGamePlayed = Long.valueOf(0);
    }

}
