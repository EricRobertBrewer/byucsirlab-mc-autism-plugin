package edu.byu.cs.autism.friend;

import edu.byu.cs.autism.minigame.Minigames;
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

    private static final String MINI_GAMES_FOLDER_NAME = File.separator + "friend-mini-game-history" + File.separator;

    /**
     * A data structure to store who won which games with which other players.
     * The first key is the game name.
     * The second key is the player UUID.
     * The third key is the friend with whom the player played's UUID.
     * The result of using these keys is the number of times the two players have played the game.
     */
    private final Map<String, Map<String, Map<String, Integer>>> gamePlayerFriendMap = new HashMap<>();

    public FriendMiniGameHistory() {
    }

    /**
     * Load the game history from disk.
     * @param dataFolder `JavaPlugin#getDataFolder()`.
     */
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
     * @param gameFile The file containing player-to-friend data for this mini-game.
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

    /**
     * Save the game history to disk.
     * @param dataFolder `JavaPlugin#getDataFolder()`.
     */
    public void save(File dataFolder) {
        for (final String game : gamePlayerFriendMap.keySet()) {
            saveGame(dataFolder, game);
        }
    }

    private void saveGame(File dataFolder, String game) {
        try {
            final File gameFile = new File(dataFolder, game);
            if (!gameFile.exists() && !gameFile.createNewFile()) {
                Bukkit.getLogger().log(Level.INFO, "Can't create mini-game file: `" + gameFile.getName() + "`.");
                return;
            }
            if (!gameFile.canWrite() && !gameFile.setWritable(true)) {
                Bukkit.getLogger().log(Level.INFO, "Can't write to mini-game file: `" + gameFile.getName() + "`.");
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
        //awards money to players based on which mini-game they just won, what games they've won in the past, and who they played with
        //takes three parameters, two players and the mini-game
        if (args.length < 2 || args.length > 2) {
            sender.sendMessage("[Friend] Usage: /wongame <game> <player1> <player2>");
        }
        final String game = args[0];
        if (!Minigames.isValid(game)) {
            sender.sendMessage("Invalid game name: `" + game + "`.");
        }
        if (plugin.getServer().getPlayer(args[1]) == null) {
            sender.sendMessage("Unable to find player with name `" + args[1] + "`.");
            return;
        }
        if (plugin.getServer().getPlayer(args[2]) == null) {
            sender.sendMessage("Unable to find player with name `" + args[2] + "`.");
            return;
        }
        // Get UUIDs.
        final String p1 = plugin.getServer().getPlayer(args[1]).getUniqueId().toString();
        final String p2 = plugin.getServer().getPlayer(args[2]).getUniqueId().toString();
        //calculate points earned
        int p1s = Minigames.getScore(this, game, p1, p2);
        int p2s = Minigames.getScore(this, game, p2, p1);
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

    private void incrementTuple(String game, String p1, String p2) {
        if (!gamePlayerFriendMap.containsKey(game)) {
            gamePlayerFriendMap.put(game, new HashMap<>());
        }
        final Map<String, Map<String, Integer>> playerFriendMap = gamePlayerFriendMap.get(game);
        //check p1 has played mini-game
        if (!playerFriendMap.containsKey(p1)) {
            playerFriendMap.put(p1, new HashMap<>());
        }
        //retrieve map
        final Map<String, Integer> friendMap = playerFriendMap.get(p1);
        //see if they've played together before
        if (!friendMap.containsKey(p2)) {
            //create new pair
            friendMap.put(p2, 1);
        } else {
            //increment count
            final int count = friendMap.get(p2);
            friendMap.put(p2, count + 1);
        }
    }

    /**
     * Get the total number of games played by this player.
     * @param p1 Player UUID.
     * @return Total number of games played (non-unique).
     */
    public int getTotalGamesPlayed(String p1) {
        return getTotalGamesPlayed(p1, null);
    }

    /**
     * Get the total number of games that these two players have played together.
     * @param p1 UUID of player 1.
     * @param p2 UUID of player 2.
     * @return The total number of games that the players have played together.
     */
    public int getTotalGamesPlayed(String p1, String p2) {
        return getTotalGamesPlayed(p1, p2, false);
    }

    /**
     * Get the number of unique games which this player has played.
     * @param p1 Player UUID.
     * @param unique If `true`, returns the number of unique games this player has played.
     *               If `false`, returns the total number of games this player has played (including repeats).
     * @return The number of [unique] games this player has played.
     */
    public int getTotalGamesPlayed(String p1, boolean unique) {
        return getTotalGamesPlayed(p1, null, unique);
    }

    /**
     * Get the total number of [unique] games that these players have played together.
     * @param p1 UUID of player 1.
     * @param p2 UUID of player 2.
     * @param unique If `true`, will only return the number of unique games these players have played together.
     *               if `false`, will return the total number of games they have played together.
     * @return The number of games played together.
     */
    public int getTotalGamesPlayed(String p1, String p2, boolean unique) {
        int sum = 0;
        for (final String game : gamePlayerFriendMap.keySet()) {
            sum += getGamesPlayed(game, p1, p2, unique);
        }
        return sum;
    }

    public int getGamesPlayed(String game, String p1) {
        return getGamesPlayed(game, p1, null);
    }

    public int getGamesPlayed(String game, String p1, String p2) {
        return getGamesPlayed(game, p1, p2, false);
    }

    public int getGamesPlayed(String game, String p1, boolean unique) {
        return getGamesPlayed(game, p1, null, unique);
    }

    /**
     * Get the number of [unique] times that player 1 has played this game with player 2.
     * @param game Name.
     * @param p1 UUID of player 1.
     * @param p2 UUID of player 2.
     * @param unique If `true`, will return 1 if the players have played the game at all, otherwise 0.
     *               If `false`, will return the number of times that the two players have played the game together.
     * @return The number of times the players have played the game together.
     */
    public int getGamesPlayed(String game, String p1, String p2, boolean unique) {
        // Check for existence of game.
        if (!gamePlayerFriendMap.containsKey(game)) {
            return 0;
        }
        final Map<String, Map<String, Integer>> playerFriendMap = gamePlayerFriendMap.get(game);
        // Check for existence of player in this game.
        if (!playerFriendMap.containsKey(p1)) {
            return 0;
        }
        final Map<String, Integer> friendMap = playerFriendMap.get(p1);
        // Check if the TOTAL number of games should be returned, i.e. games played with any other player.
        if (p2 == null) {
            int sum = 0;
            for (final String friendId : friendMap.keySet()) {
                if (unique) {
                    sum += 1;
                } else {
                    sum += friendMap.get(friendId);
                }
            }
            return sum;
        } else if (!friendMap.containsKey(p2)) {
            return 0;
        }
        if (unique) {
            return 1;
        }
        return friendMap.get(p2);
    }
}
