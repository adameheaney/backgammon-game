package src;

import java.util.Scanner;

import src.bot.BackgammonBot;

/**
 * @author Adam Heaney
 * This class is the runner class, and creates and plays a game of Backgammon.  
 */
public class BackgammonRunner {
    public static void main(String[] args) {
        Backgammon game = new Backgammon();
        Scanner scan = new Scanner(System.in);
        System.out.println("What game would you like to play? ");
        System.out.println("\"BB\" for a bot vs. bot game, \"PB\" for a player vs. bot game, or \"PP\" for a player vs. player game.");
        String mode = scan.nextLine();
        if(mode.equals("BB")) {
            System.out.println("Bot1: ");
            BackgammonBot bot = BackgammonBot.instantiateBot(scan.nextLine());
            System.out.println("Bot2: ");
            BackgammonBot bot2 = BackgammonBot.instantiateBot(scan.nextLine());
            if(bot == null || bot2 == null) {
                System.out.println("INVALID BOTS");
                return;
            }
            game.botAgainstBot(bot, bot2);
        }
        else if(mode.equals("PB")) {
            System.out.println("Bot: ");
            BackgammonBot bot = BackgammonBot.instantiateBot(scan.nextLine());
            game.playAgainstBot(bot);
        }
        else if(mode.equals("PP")) {
            game.play();
        }
        else {
            System.out.println("Invalid input. Ending program.");
        }
    }

}