package edu.byu.cs.autism.CommunicationQuiz;

import edu.byu.cs.autism.Autism;
import edu.byu.cs.autism.Time_stamp.IncermentPlayTime;
import edu.byu.cs.autism.Time_stamp.SaveData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static org.bukkit.Bukkit.getServer;

public class FamilyHint {
    Timer timer;
    Timer data_timer;

    private static String[] hintArray_family = {"NAME", "AGE", "Where is he/she from", "Family Memebers", "PETS", "SIBILINGS", "COUSINS", "Things to do with family"};

    public void popHint(){
        timer = new Timer();
        timer.schedule(new Hint(), 0, 30*1000);
    }

    public String RandomHint(){
        Random rand = new Random();
        int index = rand.nextInt(hintArray_family.length);
        return hintArray_family[index];

    }

    public void TimerForData(File file){
        data_timer = new Timer();
        data_timer.schedule(new AutoSaveData(file), 0, 3 * 60 * 60 * 1000 );//try half an hour
    }

    class AutoSaveData extends TimerTask {
        private final File file;

        AutoSaveData ( File file )
        {
            this.file = file;
        }

        public void run(){
            SaveData.saveBeforeShutDown(this.file);
            IncermentPlayTime.Clear();
        }
    }

    class Hint extends TimerTask {
        public void run(){
            ////
//            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),  "msg @a[x=-49,y=56,z=27,dx=10,dy=8,dz=-10] \n\2474Hint:\nTalk about: " + RandomHint());
            Player[] players =getServer().getOnlinePlayers().toArray(new Player[0]);
            Location l = new Location(getServer().getWorld("autism"), -44, 57, 22);
            for (int i = 0; i < players.length; i++) {
                if(players[i].getLocation().distanceSquared(l) <= 4*4){
                    players[i].sendMessage("\2474Hint:\nTalk about: " + RandomHint());
                }
            }
        }
    }
}
