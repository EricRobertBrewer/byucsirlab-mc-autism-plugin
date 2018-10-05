package edu.byu.cs.autism.friend;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

public class FriendMiniGameHistory {

    private static final String MINI_GAMES_FOLDER_NAME = File.separator + "mini-game-friend-history" + File.separator;

    /**
     * A data structure to store who won which games with which other players.
     * The first key is the game name.
     * The second key is the player UUID.
     * The third key is the friend with whom the player played's UUID.
     * The result of using these keys is the number of times the two players have played the game.
     */
    private Map<String, Map<String, Map<String, Integer>>> gamePlayerFriendMap = new HashMap<>();

    public FriendMiniGameHistory() {
    }

    public void load(File dataFolder) {
        final File miniGameFriendFolder = new File(dataFolder, MINI_GAMES_FOLDER_NAME);
        if (!miniGameFriendFolder.exists() && !miniGameFriendFolder.mkdirs()) {
            Bukkit.getLogger().log(Level.SEVERE, "Unable to create mini-game friends folder: `" + miniGameFriendFolder.getPath() + "`.");
            return;
        }
        final File[] gameFiles = miniGameFriendFolder.listFiles();
        if (gameFiles != null) {
            for (final File gameFile : gameFiles) {
                loadGame(gameFile);
            }
        }
    }

    /**
     * For example: `UberPrinny=rubinusp:1,cow47:2`, except with UUIDs instead of player names.
     * @param gameFile
     */
    private void loadGame(File gameFile) {
        final String fileName = gameFile.getName();
        final String name = fileName.substring(0, fileName.indexOf("."));
        gamePlayerFriendMap.put(name, new HashMap<>());
        try {
            final InputStream inputStream = new FileInputStream(gameFile);
            final Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                try {
                    final String line = scanner.nextLine().trim();
                    final String[] playerParts = line.split("=");
                    final String player = playerParts[0];
                    gamePlayerFriendMap.get(name).put(player, new HashMap<>());
                    final String[] friendParts = playerParts[1].split(",");
                    for (final String friendPart : friendParts) {
                        final String[] friendScoreParts = friendPart.split(":");
                        final String friend = friendScoreParts[0];
                        final int score = Integer.parseInt(friendScoreParts[1]);
                        gamePlayerFriendMap.get(name).get(player).put(friend, score);
                    }
                } catch (NumberFormatException e) {
                    Bukkit.getLogger().log(Level.INFO, "NumberFormatException occurred when loading:", e);
                }
            }
            scanner.close();
            inputStream.close();
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.INFO, "IOException occurred when loading:", e);
        }
    }

    public void save(File dataFolder) {
        for (final String game : gamePlayerFriendMap.keySet()) {
            saveGame(dataFolder, game);
        }
    }

    private void saveGame(File dataFolder, String game) {
        try {
            final File gameFile = new File(dataFolder, game);
            if (!gameFile.exists() && !gameFile.createNewFile()) {
                Bukkit.getLogger().log(Level.INFO, "Can't create game file: `" + gameFile.getName() + "`.");
                return;
            }
            if (!gameFile.canWrite() && !gameFile.setWritable(true)) {
                Bukkit.getLogger().log(Level.INFO, "Can't write to game file: `" + gameFile.getName() + "`.");
                return;
            }
            final OutputStream outputStream = new FileOutputStream(gameFile);
            final PrintStream out = new PrintStream(outputStream);
            for (final String player : gamePlayerFriendMap.get(game).keySet()) {
                out.print(player + "=");
                boolean hasPrintedFriend = false;
                for (final String friend : gamePlayerFriendMap.get(game).get(player).keySet()) {
                    final int score = gamePlayerFriendMap.get(game).get(player).get(friend);
                    if (hasPrintedFriend) {
                        out.print(",");
                    }
                    out.print(friend + ":" + score);
                    hasPrintedFriend = true;
                }
                out.println();
            }
            out.close();
            outputStream.close();
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.INFO, "IOException occurred when saving:", e);
        }
    }

    public void handleCommand(JavaPlugin plugin, CommandSender sender, Command command, String label, String[] args) {
        //awards money to players based on which game they just won, what games they've won in the past, and who they played with
        //takes three parameters, two players and the game
        String game = args[0];
        String p1 = args [1];
        String p2 = args [2];

        //calculate points earned
        int score = baseScore(game) + newPartnerBonus(game, p1, p2) + getContinuedPartnerBonus(p1, p2, game);
        int p1s = score + firstTimeBonus(game, p1);
        int p2s = score + firstTimeBonus(game, p2);

        //give money
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),"eco give " + p1 + " " + p1s);
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),"eco give " + p2 + " " + p2s);

        sender.sendMessage("Gave " + p1 + " " + p1s + " coins and " + p2 + " " + p2s +  " coins");

        //update tuples, single redundancy
        //left
        incrementTuple(game, p1, p2);
        //right
        incrementTuple(game, p2, p1);
    }

    private void incrementTuple(String game, String p1, String p2){
        Map<String, Map<String, Integer>> gameHistory = gamePlayerFriendMap.get(game);
        //check p1 has played game
        if (gameHistory.containsKey(p1)) {
            //retrieve map
            final Map<String, Integer> playerHistory = gameHistory.get(p1);
            //see if they've played together before
            if (playerHistory.containsKey(p2)) {
                //increment count
                int count = playerHistory.get(p2);
                playerHistory.put(p2, count + 1);
            } else {
                //create new pair
                playerHistory.put(p2, 1);
            }
        } else {
            //create new map
            final Map<String, Integer> friendHistory = new HashMap<>();
            //create new pair
            friendHistory.put(p2, 1);
            gameHistory.put(p1, friendHistory);
        }
    }

    private int getContinuedPartnerBonus(String p1, String p2, String game){
        //see if players have played this game before
        boolean newgame;
        Map<String, Map<String, Integer>> gameHistory = gamePlayerFriendMap.get(game);
        if (gameHistory.containsKey(p1)) {
            newgame = !gameHistory.get(p1).containsKey(p2);
        } else {
            newgame = true;
        }
        if (newgame) {
            int count = 0;
            //increase count for each tuple with both players that is found
            for (Map.Entry<String, Map<String, Map<String, Integer>>> entry : gamePlayerFriendMap.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                if (entry.getValue().containsKey(p1)) {
                    Map<String, Integer> playerHistory = entry.getValue().get(p1);
                    if (playerHistory.containsKey(p2)) {
                        count++;
                    }
                }
            }
            //calculate bonus from count
            switch (count) {
                case 1:
                    return 5;
                case 2:
                    return 10;
            }
        }
        return 0;
    }

    private int baseScore(String game){
        if (game.equals("lava")) {
            return 0;
        }
        if (game.equals("T")) {
            return 5;
        }
        if (game.equals("team")) {
            return 2;
        }
        return -1;
    }

    private int firstTimeBonus(String game, String player){
        //check if this game contains the player
        boolean firstTime = !gamePlayerFriendMap.get(game).containsKey(player);
        if (firstTime){
            //calculate bonus from game
            if (game.equals("lava")) {
                return 5;
            }
            if (game.equals("T")) {
                return  10;
            }
            if (game.equals("team")) {
                return 7;
            }
        }
        return 0;
    }

    private int newPartnerBonus(String game, String p1, String p2){
        //check if players have played this game before
        boolean newgame = false;
        Map<String, Map<String, Integer>> gameHistory = gamePlayerFriendMap.get(game);
        if (gameHistory.containsKey(p1)) {
            newgame = !gameHistory.get(p1).containsKey(p2);
        } else {
            newgame = true;
        }
        if (newgame) {
            //calculate bonus from game

            if(game.equals("lava")){
                return 1;
            }
            if(game.equals("T")){
                return  5;
            }
            if(game.equals("team")){
                return 3;
            }
        }
        return 0;
    }

    private int totalGamesPlayer(String p1, String p2) {
        int sum = 0;
        for (Map.Entry<String, Map<String, Map<String, Integer>>> entry : gamePlayerFriendMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (entry.getValue().containsKey(p1)) {
                Map<String, Integer> playerHistory = entry.getValue().get(p1);
                if (playerHistory.containsKey(p2)) {
                    sum += playerHistory.get(p2);
                }
            }
        }
        return sum;
    }
}
