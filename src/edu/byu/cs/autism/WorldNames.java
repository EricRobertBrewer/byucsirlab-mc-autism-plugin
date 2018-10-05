package edu.byu.cs.autism;

public class WorldNames {

    /**
     * This is the world in which players spawn - the "main" world on our server.
     * It contains mini-games, merchants, a communication room, etc.
     */
    public static final String AUTISM = "autism";
    /**
     * The first survival world which we've created.
     * Currently connected to a button in the Game Room.
     */
    public static final String SURVIVAL_1 = "survival";
    /**
     * Also currently connected to a button in the Game Room.
     */
    public static final String CREATIVE_FLAT_1 = "creative_flat1";
    /**
     * The End world, containing the Ender Dragon boss fight.
     * Connected from the Game Room.
     * When two players enter, one will be equipped with a sword - the other with a bow and arrows.
     */
    public static final String END_BOSS_1 = "boss2_w";

    private WorldNames() {
    }
}
