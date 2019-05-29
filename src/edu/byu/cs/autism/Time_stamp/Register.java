package edu.byu.cs.autism.Time_stamp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Register {

    public static Map<String,String> users = new HashMap<>();

    public static void handleCommand(JavaPlugin plugin, CommandSender sender, Command command, String label, String[] args) {

        //register user for session
        String key = sender.getName();
        String value = args[0];

        users.put(key, value);
        //teleport to spawn
        plugin.getServer().dispatchCommand(sender,  "spawn");
    }

}
