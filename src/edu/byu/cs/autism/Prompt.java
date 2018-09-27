package edu.byu.cs.autism;

import java.util.Random;

public class Prompt {
    public static String[] prompts = {
            "What activities do you enjoy? For example, you might say, I enjoy playing minecraft because...",
            "Tell your partner a little bit about your family. For example, how many people are in your family?",
            "What do you do for work? For example, Where do you work? Do you enjoy working there?",
            "What do you think about the last mini-game? For example, You can tell your partner what you like about the game.",
            "What is your favorite food?",
            "What is your favorite sport? How often do you play?",
            "Where would you like to go for vacation? Why?",
            "What would be your ideal way to spend the weekend?",
            "What’s the best single day on the calendar? Why?",
            "What’s your dream car? Why?",
            "What is your favorite movie? Why?",
            "What website do you visit most often?",
            "As the only human left on Earth, what would you do?",
            "What is something you will NEVER do again?",
            "What do you want to be remembered for?",
            "Among your friends or family, what are you famous for?",
            "What would be your spirit animal? Why?",
            "What’s the most immature thing that you do?"
    };

    public static String getRandomPrompt() {
        Random rand = new Random();
        int randIndex = rand.nextInt(prompts.length);
        return prompts[randIndex];
    }
}
