package edu.byu.cs.autism;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class PlayerMenu implements Listener {
    private static final String title_option1 = "Info";
    private static final String title_option2 = "Talk";
    private static final String title_option3 = "Add friend";
    private static final String title_option4 = "Invite";


    private static Map<Player, Player> openedMenu = new HashMap<>();

    //private Player me;
    //private Player you;

    public void openMenu(Player player, Player other) {



        if (player == null || other == null) {
            return;
        }


        openedMenu.put(player, other);

        Player me = player;

        Player you = openedMenu.get(me);

        Bukkit.getLogger().log(Level.INFO, "PRE: " + me.getDisplayName() );
        Bukkit.getLogger().log(Level.INFO, "PRE" + you.getDisplayName() );



        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.RED + other.getDisplayName() + "'s menu");

        ItemStack option1 = initOption(Material.BOOK, title_option1);
        ItemStack option2 = initOption(Material.WATCH, title_option2);
        ItemStack option3 = initOption(Material.BREAD, title_option3);
        ItemStack option4 = initOption(Material.ICE, title_option4);
        setInventoryItems(inv, option1, option2, option3, option4);

        player.openInventory(inv);
    }

    @EventHandler
    private void onMenuOptionClicked(InventoryClickEvent e) {
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(title_option1)) {
//            e.getWhoClicked().sendMessage("I clicked " + title_option1);
            e.getWhoClicked().closeInventory();
        }
        else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(title_option2)) {

            Player me = (Player) e.getWhoClicked();

            Player you = openedMenu.get(me);

            Bukkit.getLogger().log(Level.INFO, "PRE: " + me.getDisplayName() );
            Bukkit.getLogger().log(Level.INFO, "PRE" + you.getDisplayName() );

            Bukkit.getLogger().log(Level.INFO, "POST: " + me.getDisplayName() );
            Bukkit.getLogger().log(Level.INFO, "POST: "  + you.getDisplayName() );

//            Conversation.add(me, you);
//            e.getWhoClicked().sendMessage("I clicked " + title_option2);
            e.getWhoClicked().closeInventory();
        }
        else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(title_option3)) {
//            e.getWhoClicked().sendMessage(title_option3);
            e.getWhoClicked().closeInventory();
        }
        else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(title_option4)) {
//            e.getWhoClicked().sendMessage(title_option4);
            e.getWhoClicked().closeInventory();
        }
    }

    public PlayerMenu() {
    }

    private ItemStack initOption(Material icon, String title) {
        ItemStack option = new ItemStack(icon);

        ItemMeta optionMeta = option.getItemMeta();
        optionMeta.setDisplayName(title);

        option.setItemMeta(optionMeta);

        return option;
    }

    private ItemStack initOption(Material icon, String title, List<String> lore) {
        ItemStack option = new ItemStack(icon);

        ItemMeta optionMeta = option.getItemMeta();
        optionMeta.setDisplayName(title);
        optionMeta.setLore(lore);

        option.setItemMeta(optionMeta);

        return option;
    }

    private void setInventoryItems(Inventory inv, ItemStack... options) {
        for (int i = 0; i < options.length; i++) {
            inv.setItem(i, options[i]);
        }
    }
}
