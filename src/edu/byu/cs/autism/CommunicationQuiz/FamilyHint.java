package edu.byu.cs.autism.CommunicationQuiz;

import org.bukkit.Bukkit;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FamilyHint {
    Timer timer;


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

    class Hint extends TimerTask{
        public void run(){
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),  "msg @a[x=-49,y=56,z=27,dx=10,dy=8,dz=-10] \n\2474Hint:\nTalk about: " + RandomHint());
        }
    }
}
