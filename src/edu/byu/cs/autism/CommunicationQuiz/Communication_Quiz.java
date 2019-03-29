package edu.byu.cs.autism.CommunicationQuiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Communication_Quiz {
    private static String[] player_one_questions;
    private static String[] player_two_questions;
    private static String player_one;
    private static String player_two;
    private static String questionA ;
    private static String questionB ;
    private static String answer;
    private static Stack<String> playerA_question;
    private static Stack<String> playerB_question;
    private static Stack<String> playerA_answer;
    private static Stack<String> playerB_answer;
    //    private Stack<String> wrongAnswer = new Stack<>();
    private static List<Integer> questionNum;

    public Communication_Quiz(){
        player_one_questions = new String[] {
                // Partner
                "Which city do your partner's parents live in?",
                "Which city does your partner live in?",
                "What is your partner's name?",
                "How many siblings does your partner have?",
                "Does your partner live with his/her family?",
                "Does your partner have any children?",
                "Does your partner have pets?",
                "Is your partner married?",

                "Do you know what your partner's parents do for work?",
                "Does your partner have any cousins?",
                "Do your partner's grandparents live near you?",
                "What does your partner's family like to do for fun? (List one)",
                "Is your partner the oldest child in your family?",
                "Is your partner the youngest child in your family?",

                // You
                "Which city do your parents live in?",
                "Which city do you live in?",
                "What is your name?",
                "How many siblings do you have?",
                "Do you live with your family?",
                "Do you have children?",
                "Do you have pets?",
                "Are you married?",

                "Do you know what your parents do for work?",
                "Do you have any cousins?",
                "Do your grandparents live near you?",
                "What does your family like to do for fun? (List one)",
                "Are you the oldest child in your family?",
                "Are you the youngest child in your family?"
        };
        player_two_questions = new String[] {
                // You
                "Which city do your parents live in?",
                "Which city do you live in?",
                "What is your name?",
                "How many siblings do you have?",
                "Do you live with your family?",
                "Do you have children?",
                "Do you have pets?",
                "Are you married?" ,

                "Do you know what your parents do for work?",
                "Do you have any cousins?",
                "Do your grandparents live near you?",
                "What does your family like to do for fun? (List one)",
                "Are you the oldest child in your family?",
                "Are you the youngest child in your family?",

                // Partner
                "Which city do your partner's parents live?",
                "Which city does your partner live in?",
                "What is your partner's name?",
                "How many siblings does your partner have?",
                "Does your partner live with his/her family?",
                "Does your partner have children?",
                "Does your partner have pets?",
                "Is your partner married?",

                "Do you know what your partner's parents do for work?",
                "Does your partner have any cousins?",
                "Do your partner's grandparents live near you?",
                "What does your partner's family like to do for fun? (List one)",
                "Is your partner the oldest child in your family?",
                "Is your partner the youngest child in your family?"
        };
        player_one = "";
        player_two = "";
        questionA = "";
        questionB = "";
        answer = "";
        playerA_answer = new Stack<>();
        playerB_answer = new Stack<>();
        playerA_question = new Stack<>();
        playerB_question = new Stack<>();
        questionNum = new ArrayList<>();
    }

    public static void clearEverything() {
        setPlayer_one("");
        setPlayer_two("");
        playerA_question.clear();
        playerB_question.clear();
        playerB_answer.clear();
        playerA_answer.clear();
        questionNum.clear();

    }
    public static int pick_questions_number(){
        Random rand = new Random();
        return rand.nextInt(player_one_questions.length);
    }

    public static String pick_question(int num, int randQuestion){
        if(num==1){
            return player_one_questions[randQuestion];
        }
        else return player_two_questions[randQuestion];
    }

    public static String getPlayer_one() {
        return player_one;
    }

    public static void setPlayer_one(String one) {
        player_one = one;
    }

    public static String getPlayer_two() {
        return player_two;
    }

    public static void setPlayer_two(String two) {
        player_two = two;
    }

    public static String getQuestionA() {
        return questionA;
    }

    public static void setQuestionA(String questionA) {
        questionA = questionA;
    }

    public static String getQuestionB() {
        return questionB;
    }

    public static void setQuestionB(String questionB) {
        questionB = questionB;
    }

    public static String getAnswer() {
        return answer;
    }

    public static void setAnswer(String answer) {
        answer = answer;
    }

    public static Stack<String> getPlayerA_question() {
        return playerA_question;
    }

    public static void setPlayerA_question(String playerA_q) {
        playerA_question.push(playerA_q);
    }

    public static Stack<String> getPlayerB_question() {
        return playerB_question;
    }

    public static void setPlayerB_question(String playerB_q) {
        playerB_question.push(playerB_q);
    }

    public static Stack<String> getPlayerA_answer() {
        return playerA_answer;
    }

    public static void setPlayerA_answer(String playerA_a) {
        playerA_answer.push(playerA_a);
    }
    public static String popPlayerA_answer(){
        return playerA_answer.pop();
    }

    public static Stack<String> getPlayerB_answer() {
        return playerB_answer;
    }

    public static void setPlayerB_answer(String playerB_a) {
        playerB_answer.push(playerB_a);
    }

    public static String popPlayerB_answer(){
        return playerB_answer.pop();
    }
    public static List<Integer> getQuestionNum() {
        return questionNum;
    }

    public static void setQuestionNum(List<Integer> question) {
        questionNum = question;
    }
}
