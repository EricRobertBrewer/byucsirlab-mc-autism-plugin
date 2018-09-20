package edu.byu.cs.autism;

import java.util.Random;

public class Prompt {
    public static String[] prompts = {
            "What activities do you enjoy? For example, you might say, I enjoy playing minecraft because...",
            "Tell your partner a little bit about your family. For example, how many people are in your family?",
            "What do you do for work? For example, Where do you work? Do you enjoy working there?",
            "What do you think about the last mini-game? For example, You can tell your partner what you like about the game.",
            "What is your favorite food?"
    };

    public static String getRandomPrompt() {
        Random rand = new Random();
        int randIndex = rand.nextInt(prompts.length);
        return prompts[randIndex];
    }
}
