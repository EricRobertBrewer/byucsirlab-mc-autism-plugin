package edu.byu.cs.autism.minigame;

import edu.byu.cs.autism.WorldNames;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Random;
import java.util.logging.Level;

public class Maze {

    public static final int SIZE_SMALL = 0;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_LARGE = 2;
    private static final String[] LABELS = {"SMALL", "MEDIUM", "LARGE"};
    private static final int[] DIMENSIONS = {25, 33, 49};
    private static final int[][] NORTH_WEST_BOTTOM_CORNERS = {
            {32, 56, -141},
            {32, 56, -182},
            {32, 56, -239}
    };

    public static void handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("[Maze] Usage: /maze <size> [seed]");
            return;
        }
        final String size = args[0];
        final Random random;
        if (args.length > 1) {
            final String seed = args[1];
            try {
                random = new Random(Long.parseLong(seed));
            } catch (NumberFormatException e) {
                sender.sendMessage("[Maze] Invalid `seed`: `" + seed + "`.");
                return;
            }
        } else {
            random = new Random();
        }
        if ("0".equals(size) || "small".equalsIgnoreCase(size)) {
            generate(random, SIZE_SMALL);
        } else if ("1".equals(size) || "medium".equalsIgnoreCase(size)) {
            generate(random, SIZE_MEDIUM);
        } else if ("2".equals(size) || "large".equalsIgnoreCase(size)) {
            generate(random, SIZE_LARGE);
        } else {
            sender.sendMessage("[Maze] `size` must be `small` (0), `medium` (1), or `large` (2).");
        }
    }

    private static void generate(Random random, int size) {
        final int[][] maze = getRepresentation(random, size);
        final int[] northWestBottomCorner = NORTH_WEST_BOTTOM_CORNERS[size];
        final int xStart = northWestBottomCorner[0];
        final int yStart = northWestBottomCorner[1];
        final int zStart = northWestBottomCorner[2];
        final World world = Bukkit.getWorld(WorldNames.AUTISM);
        for (int x = xStart; x < xStart + maze.length; x++) {
            for (int z = zStart; z < zStart + maze[0].length; z++) {
                world.getBlockAt(x, yStart, z).setType(Material.GRASS);
                if (maze[x-xStart][z-zStart] == 0) {
                    for (int y = yStart + 1; y < yStart + 4; y++) {
                        world.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                } else {
                    for (int y = yStart + 1; y < yStart + 4; y++) {
                        world.getBlockAt(x, y, z).setType(Material.HAY_BLOCK);
                    }
                }
            }
        }
        Bukkit.getLogger().log(Level.INFO, "Generated a " + LABELS[size] + " maze starting at (" + xStart + ", " + yStart + ", " + zStart + ").");
    }

    private static int[][] getRepresentation(Random random, int size) {
        final int d = DIMENSIONS[size];
        final int[][] maze = new int[d][d];
        for (int x = 0; x < maze.length; x++) {
            maze[x][0] = 1;
            maze[x][maze[x].length - 1] = 1;
        }
        for (int z = 1; z < maze[0].length - 1; z++) {
            maze[0][z] = 1;
            maze[maze.length - 1][z] = 1;
        }
        for (int x = 1; x < maze.length - 1; x++) {
            for (int z = 1; z < maze[x].length - 1; z++) {
                maze[x][z] = 0;
            }
        }
        divide(random, maze, 0, 0, maze.length - 1, maze[0].length - 1);
        return maze;
    }

    private static void divide(Random random, int[][] maze, int x1, int z1, int x2, int z2) {
        if (x2 - x1 < 4 || z2 - z1 < 4) {
            return;
        }
        // Create one vertical dividing wall.
        final int xWall = random.nextInt((x2 - x1) / 2 - 1) * 2 + x1 + 2;
        for (int z = z1 + 1; z < z2; z++) {
            maze[xWall][z] = 1;
        }
        // Create one horizontal dividing wall.
        final int zWall = random.nextInt((z2 - z1) / 2 - 1) * 2 + z1 + 2;
        for (int x = x1 + 1; x < x2; x++) {
            maze[x][zWall] = 1;
        }
        // Punch holes (doors) in three of the four walls extending from the intersection of `xWall` and `zWall`.
        final int cardinal = random.nextInt(4);
        if (cardinal != 0) {
            final int north = random.nextInt((z2 - zWall) / 2) * 2 + zWall + 1;
            maze[xWall][north] = 0;
        }
        if (cardinal != 1) {
            final int east = random.nextInt((x2 - xWall) / 2) * 2 + xWall + 1;
            maze[east][zWall] = 0;
        }
        if (cardinal != 2) {
            final int south = random.nextInt((zWall - z1) / 2) * 2 + z1 + 1;
            maze[xWall][south] = 0;
        }
        if (cardinal != 3) {
            final int west = random.nextInt((xWall - x1) / 2) * 2 + x1 + 1;
            maze[west][zWall] = 0;
        }
        // Subdivide the remaining four quadrants.
        divide(random, maze, x1, z1, xWall, zWall);
        divide(random, maze, x1, zWall, xWall, z2);
        divide(random, maze, xWall, z1, x2, zWall);
        divide(random, maze, xWall, zWall, x2, z2);
    }

    /**
     * Do not instantiate.
     */
    private Maze() {
    }
}
