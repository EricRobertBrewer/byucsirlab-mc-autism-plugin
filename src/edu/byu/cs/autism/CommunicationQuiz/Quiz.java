package edu.byu.cs.autism.CommunicationQuiz;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

import static org.bukkit.Bukkit.getServer;

public class Quiz {

    public static void handleCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 ||
                (!"begin".equalsIgnoreCase(args[0]) &&
                        !"answer".equalsIgnoreCase(args[0]) &&
                        !"quit".equalsIgnoreCase(args[0]))) {
            String usage = "/quiz begin -- Join the game\n/quiz answer [YOUR ANSWER] -- Answer the question\n/quiz quit -- Give up";
            sender.sendMessage(ChatColor.DARK_GREEN+usage);
            return;
        }

        if ("quit".equalsIgnoreCase(args[0])) {
            if(!Communication_Quiz.getPlayer_one().isEmpty()){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),  "tp " + Communication_Quiz.getPlayer_one() + " 0 56 -17");
            }
            if(!Communication_Quiz.getPlayer_two().isEmpty()){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),  "tp " + Communication_Quiz.getPlayer_two() + " 0 56 -17");
            }
            Communication_Quiz.clearEverything();
            // Minecraft.getMinecraft().world.getPlayerEntityByName(sender.getName()).setPositionAndUpdate(-3, 56, -19);
//            MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();
//            s.getCommandManager().executeCommand(s, "/tp " + sender.getName()+ " 755.5 1.0 -1538.5");
//            s.getCommandManager().executeCommand(s, "/tp " + sender.getName() + " -2.5 57.0 -21.5");
//            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),  "tp " + sender.getName() + " 0 56 -17");
            //sender.sendMessage(new TextComponentString("/tp " + sender.getName()+ " 755 1 -1539"));
        } else if ("begin".equalsIgnoreCase(args[0])) {

            //when someone who is not in the game type egin, there maybe a problem
            //sender is one of the player
            if (Communication_Quiz.getPlayer_two().equals(sender.getName()) || Communication_Quiz.getPlayer_one().equals(sender.getName())) {
                sender.sendMessage( ChatColor.DARK_GREEN+"You already in the game");
                return;
            }
            //payer one not assigned and the sender is not player two
            else if (Communication_Quiz.getPlayer_one().isEmpty()) {
                Communication_Quiz.setPlayer_one(sender.getName());
                getServer().dispatchCommand(Bukkit.getConsoleSender(),  "tp " + sender.getName() + " -47 57 12");
                sender.sendMessage(ChatColor.BLUE + "You joined the game successfully, waiting for your partner...");

            }
            //player two is not assigned and the sender is not player one
            else if (Communication_Quiz.getPlayer_two().isEmpty()) {
                Communication_Quiz.setPlayer_two(sender.getName());
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),  "tp " + sender.getName() + " -46 57 32");
                sender.sendMessage(ChatColor.BLUE +"You joined the game successfully, waiting for your partner...");
            }
            //both player one and two has been assigned and is not the sender
            else {
                sender.sendMessage(ChatColor.BLUE +"Someone else is taking the quiz, wait a second");
            }
            if (!Communication_Quiz.getPlayer_two().isEmpty() && !Communication_Quiz.getPlayer_one().isEmpty()) {
                Player p1 = getServer().getPlayer(Communication_Quiz.getPlayer_one());
                Player p2 = getServer().getPlayer(Communication_Quiz.getPlayer_two());
                execute_quiz(p1, p2);
            }

        } else if ("answer".equalsIgnoreCase(args[0])) {
            StringBuilder sb = new StringBuilder();
            sb.append(args[1]);
            for (int i = 2; i < args.length; i++) {
                sb.append(" ");
                sb.append(args[i]);
            }
            String answer = sb.toString();

            //not one of the player
            if (!Communication_Quiz.getPlayer_one().equals(sender.getName()) && !Communication_Quiz.getPlayer_two().equals(sender.getName())) {
                sender.sendMessage(ChatColor.RED +"You are not in the quiz now");
                return;
            }
            Player p1 = getServer().getPlayer(Communication_Quiz.getPlayer_one());
            Player p2 = getServer().getPlayer(Communication_Quiz.getPlayer_two());

            if (Communication_Quiz.getPlayer_one().equals(sender.getName())) {
                //if player one has answered all the question being asked
                if (Communication_Quiz.getPlayerA_answer().size() == Communication_Quiz.getPlayerA_question().size()) {
                    sender.sendMessage(ChatColor.RED +"Wait for a new question coming up");
                    return;
                }

                Communication_Quiz.setPlayerA_answer(answer);
            }
            if (Communication_Quiz.getPlayer_two().equals(sender.getName())) {
                //if player two has answered all the question being asked
                if (Communication_Quiz.getPlayerB_answer().size() == Communication_Quiz.getPlayerB_question().size()) {
                    sender.sendMessage(ChatColor.RED +"Wait for a new question coming up");
                    return;
                }

                Communication_Quiz.setPlayerB_answer(answer);
            }
            boolean playerA_isAnswered = (Communication_Quiz.getPlayerA_answer().size() == Communication_Quiz.getPlayerA_question().size());
            boolean playerB_isAnswered = (Communication_Quiz.getPlayerB_answer().size() == Communication_Quiz.getPlayerB_question().size());
            if (playerA_isAnswered && playerB_isAnswered) {
                execute_quiz(p1, p2);
            }
            else{
                sender.sendMessage(ChatColor.RED +"Waiting for your partner to answer the question");
            }
        }
    }

    private static void execute_quiz(Player p1, Player p2) {
        if (checkIfGameOver(p1, p2)) {
            //teleport
            return;
        }

        int randQuestion = Communication_Quiz.pick_questions_number();
        //check if the question has been asked;
        while (Communication_Quiz.getQuestionNum().contains(randQuestion)) {
            randQuestion = Communication_Quiz.pick_questions_number();
        }
        Communication_Quiz.getQuestionNum().add(randQuestion);

        Communication_Quiz.setQuestionA(Communication_Quiz.pick_question(1, randQuestion));
        Communication_Quiz.setPlayerA_question(Communication_Quiz.getQuestionA());

        Communication_Quiz.setQuestionB(Communication_Quiz.pick_question(2, randQuestion));
        Communication_Quiz.setPlayerB_question(Communication_Quiz.getQuestionB());

        if (p1 != null && p2 != null) {
            p1.sendMessage(ChatColor.BLUE +"QUESTION: " + ChatColor.RED + Communication_Quiz.getQuestionA() + ChatColor.BLUE+"\n type \'\\quiz answer [your answer]\' to answer the question");

            p2.sendMessage(ChatColor.BLUE +"QUESTION: " +ChatColor.RED +  Communication_Quiz.getQuestionB() + ChatColor.BLUE+"\n type \'\\quiz answer [your answer]\' to answer the question");
        }
    }

    private static boolean checkIfGameOver(Player p1, Player p2) {
        boolean over = false;
        if ( Communication_Quiz.getPlayerA_answer().size() == 5 && Communication_Quiz.getPlayerB_answer().size() == 5) {
            over = true;
            p1.sendMessage(ChatColor.BLUE + "The Quiz is over");
            p2.sendMessage(ChatColor.BLUE + "The Quiz is over");

//            MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();

            int size =  Communication_Quiz.getPlayerA_answer().size();
            int count_correction = printResult(p1, p2);
            if (count_correction >= (int) Math.ceil(size / 2.0)) {
                p1.sendMessage(ChatColor.BLUE + "Congratulation! You and your partner pass the test");
//                s.getCommandManager().executeCommand(s, "/tp " + p1.getName() + " -2.5 57.0 -21.5");
                getServer().dispatchCommand(Bukkit.getConsoleSender(),  "tp " + p1.getName() + " 0 56 -17");
                p2.sendMessage(ChatColor.BLUE + "Congratulation! You and your partner pass the test");
//                s.getCommandManager().executeCommand(s, "/tp " + p2.getName() + " -2.5 57.0 -21.5");
                getServer().dispatchCommand(Bukkit.getConsoleSender(),  "tp " + p2.getName() + " 0 56 -17");

            } else {
                p1.sendMessage(ChatColor.BLUE + "Sorry! You and your partner may need to talk more next time");
//                s.getCommandManager().executeCommand(s, "/tp " + p1.getName() + " -2.5 57.0 -21.5");
                getServer().dispatchCommand(Bukkit.getConsoleSender(),  "tp " + p1.getName() + " 0 56 -17");
                p2.sendMessage(ChatColor.BLUE + "Sorry! You and your partner may need to talk more next time");
//                s.getCommandManager().executeCommand(s, "/tp " + p2.getName() + " -2.5 57.0 -21.5");
                getServer().dispatchCommand(Bukkit.getConsoleSender(),  "tp " + p2.getName() + " 0 56 -17");


            }

            Communication_Quiz.clearEverything();
        }
        return over;
    }

    private static int printResult(Player p1, Player p2){
        int count = 0;
        StringBuilder playerA_result = new StringBuilder();
        StringBuilder playerB_result = new StringBuilder();
        playerA_result.append("Compare Answers: \n");
        playerB_result.append("Compare Answers: \n");
        while(!Communication_Quiz.getPlayerA_question().empty()){
            playerA_result.append(Communication_Quiz.getPlayerA_question().pop()).append("\n");
            playerB_result.append(Communication_Quiz.getPlayerB_question().pop()).append("\n");
            String pA = Communication_Quiz.popPlayerA_answer();
            String pB = Communication_Quiz.popPlayerB_answer();

            playerA_result.append("You Answered: ").append(pA).append("\n");
            playerA_result.append("Your Partner Answered: ").append(pB).append("\n");

            playerB_result.append("You Answered: ").append(pB).append("\n");
            playerB_result.append("Your Partner Answered: ").append(pA).append("\n");


            if (pA.equalsIgnoreCase(pB)) {
                count++;
            }
        }
        p1.sendMessage(ChatColor.BLUE + playerA_result.toString());
        p2.sendMessage(ChatColor.BLUE + playerB_result.toString());

        return count;
    }
}