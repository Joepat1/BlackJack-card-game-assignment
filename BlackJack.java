// Joe Giblin
// October 31st 2025
// Professor Darell Criss

import java.util.Scanner;
import java.security.SecureRandom;

public class BlackJack {

    // creates a Scanner object to take user input
    private static Scanner userInput = new Scanner(System.in);
    // creates a SecureRandom object to shuffle the deck securely
    private static SecureRandom randomNumbers = new SecureRandom();
    // creates a deck of cards for the player and computer to draw from
    private static Card[] deck = new Card[52];
    // variable to take user input
    private static String input = "";
    // stats about the player's games
    private static int gamesWon = 0;
    private static int gamesDrawn = 0;
    private static int gamesLost = 0;
    private static int gamesPlayed = 0;
    // checks whether the user wants to play again after a game
    private static boolean playAgain = true;

    // variable to use for deal() method
    private static int dealN = 51;

    public static void main(String[] args) {
        // defines the faces and suits of the card objects
        String[] faces = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

        // creates each card in the deck
        for (int i = 0; i < 52; i++) {
            deck[i] = new Card(faces[i % 13], suits[i / 13]);
        }

        //shuffle();

        /*Card test;
        test = deal();
        System.out.println(test);

        String check = test.toString();

        check = check.split(" ")[0];*/

        // welcomes the user to the game
        System.out.println("Welcome to Black Jack! the goal of the game is to get as close to 21 as possible without going over.\nYou can either choose to hit, (draw a new card) or stand (end turn).\nIf you have an ace in your hand, your hand score will increase by 11 without busting you if the additional value is over 21.\n(Press enter to continue.)");
        // blank input line to break up the text and make it more readable in the output
        input = userInput.nextLine();

        // variable to keep track of whether the player won that game of Black Jack
        int gameWinCheck;

        // calls the playGame method while the user is interested in playing Black Jack
        while(playAgain) {
            gameWinCheck = playGame();

            if (gameWinCheck == 1) {
                System.out.println("You won this game of Black Jack!");
                System.out.println("(Press enter to continue.)");
                input = userInput.nextLine();
                gamesWon++;
                gamesPlayed++;
            } else if (gameWinCheck == 2) {
                System.out.println("You drew this game of Black Jack.");
                System.out.println("(Press enter to continue.)");
                input = userInput.nextLine();
                gamesDrawn++;
                gamesPlayed++;
            } else {
                System.out.println("You lost this game of Black Jack :(");
                System.out.println("(Press enter to continue.)");
                input = userInput.nextLine();
                gamesLost++;
                gamesPlayed++;
            } // end of if else statement

            System.out.println("Would you like to play Black Jack again? (y/n)");
            input = userInput.nextLine();
            
            // switch case to detect if the user wants to play Black Jack again
            switch(input) {
                case "y":
                case "yes":
                case "Y":
                case "Yes":
                case "YES":
                    playAgain = true;
                    break;
                default:
                    playAgain = false;
                    break;
            } // end of switch case 

        } // end of while statement

        System.out.println("Thanks for playing! You won " + gamesWon + " of the games you played, drew " + gamesDrawn + " of the games you played, and lost " + gamesLost + " of the games you played.\nYou played a total of " + gamesPlayed + " games.");

        /*Card test2;
        test2 = deal();
        System.out.println(test2);

        Card test3;
        test3 = deal();
        System.out.println(test3);*/

        /*for (int i = 0; i < 52; i++) {
            System.out.printf("%-20s", deck[i].toString());
            if ((i + 1) % 4 == 0) {
                System.out.println();
            }
        }*/
    } // end of main method

    public static int playGame() {
        // variable to return whether the game was a win, draw, or loss for the player
        int playerWin = 0;
        /// variable to check whether the player's turn or computer's turn is still active
        boolean play = true;

        // calls the shuffle method to randomize the game
        shuffle();

        // keeps track of what card number the player and computer are on, starts at 2 to fill the player hand array correctly
        int currentCardNumber = 2;
        // object to use for getting new player or computer cards
        Card currentCard;        

        boolean playerHandHasAce = false;
        boolean computerHandHasAce = false;
        
        // arrays with the highest possible number of cards in a hand allocated
        Card[] playerHand = new Card[9];
        Card[] computerHand = new Card[9];

        // checks whether the player/computer hits a card
        boolean hit = false;
        // variable to track how many times the player hit, and subtract that from card count for the computer's turn when it hits
        int hitCount = 0;
        boolean playerTurn = true;

        // first value keeps track of the player score without an ace, and second keeps track with an ace, set to 0 if no ace
        int[] playerSum = {0, 0};
        int[] computerSum = {0, 0};
        // variable to hold the information of the computer's first card and its value
        int revealedSum = 0;
        boolean revealedAce = false;
        // variable to check whether the computer will choose to hit/stand or if it must reveal its second card
        boolean computerTurnOne = true;

        // deals the first two cards to the player, and then the computer
        currentCard = deal();
        playerHand[0] = currentCard;
        currentCard = deal();
        playerHand[1] = currentCard;

        currentCard = deal();
        computerHand[0] = currentCard;
        currentCard = deal();
        computerHand[1] = currentCard;

        // variable used to parse the first word of the card object, which contains the value of the card
        String check;

        for (int i = 0; i < 2; i++) {

            check = playerHand[i].toString();
            check = check.split(" ")[0];

            // switch case to add values to the player's hand based off of the cards dealt to them
            switch(check) {
                case "Ace":
                    playerHandHasAce = true;
                    playerSum[0] += 1;
                    break;
                case "Two":
                    playerSum[0] += 2;
                    break;
                case "Three":
                    playerSum[0] += 3;
                    break;
                case "Four":
                    playerSum[0] += 4;
                    break;
                case "Five":
                    playerSum[0] += 5;
                    break;
                case "Six":
                    playerSum[0] += 6;
                    break;
                case "Seven":
                    playerSum[0] += 7;
                    break;
                case "Eight":
                    playerSum[0] += 8;
                    break;
                case "Nine":
                    playerSum[0] += 9;
                    break;
                case "Ten":
                    playerSum[0] += 10;
                    break;
                case "Jack":
                    playerSum[0] += 11;
                    break;
                case "Queen":
                    playerSum[0] += 12;
                    break;
                case "King":
                    playerSum[0] += 13;
                    break;
                default:
                    break;
            } // end of switch case
        } // end of for loop

        // same purpose as last for loop, but for assigning the computer's hand values instead
        for (int i = 0; i < 2; i++) {

            check = computerHand[i].toString();
            check = check.split(" ")[0];

            switch(check) {
                case "Ace":
                    computerHandHasAce = true;
                    computerSum[0] += 1;
                    break;
                case "Two":
                    computerSum[0] += 2;
                    break;
                case "Three":
                    computerSum[0] += 3;
                    break;
                case "Four":
                    computerSum[0] += 4;
                    break;
                case "Five":
                    computerSum[0] += 5;
                    break;
                case "Six":
                    computerSum[0] += 6;
                    break;
                case "Seven":
                    computerSum[0] += 7;
                    break;
                case "Eight":
                    computerSum[0] += 8;
                    break;
                case "Nine":
                    computerSum[0] += 9;
                    break;
                case "Ten":
                    computerSum[0] += 10;
                    break;
                case "Jack":
                    computerSum[0] += 11;
                    break;
                case "Queen":
                    computerSum[0] += 12;
                    break;
                case "King":
                    computerSum[0] += 13;
                    break;
                default:
                    break;
            } // end of switch case
            // checks whether this is the first or second card drawn by the computer, and sets the first card value to be the revealed sum val
            if (i == 0) {
                if (computerSum[1] > 0) {
                    revealedSum = computerSum[1];
                } else {
                    revealedSum = computerSum[0];
                } // end of if else statement
                if (computerHandHasAce) {
                    revealedAce = true;
                } // end of if statement
            } // end of if statement
        } // end of for loop
        
        // sets the value of the player's/computer's second sum if they have an ace in their first two card draws.
        if (playerHandHasAce) {
            playerSum[1] = playerSum[0] + 10;
        }
        
        if (computerHandHasAce) {
            computerSum[1] = computerSum[0] + 10;
        }

        /*System.out.println(playerHand[0].toString());
        System.out.println(playerHand[1].toString());
        System.out.println(playerSum[0] + " " + playerSum[1]);*/

        /*System.out.println(computerHand[0].toString());
        System.out.println(computerHand[1].toString());
        System.out.println(computerSum[0] + " " + computerSum[1]);*/

        // checks whether the player's hand has an ace, and what the value of their hand is, and then gives them a message of what they have.
        if (playerHandHasAce) {
            if (playerSum[1] < 21) {
                System.out.println("Your starting hand is: " + playerHand[0].toString() + " and " + playerHand[1].toString() + ".");
                System.out.println("Because you have an ace that would not bust you, your hand is worth both " + playerSum[0] + " and " + playerSum[1] + ".");
            } else if (playerSum[1] == 21){
                System.out.println("Your starting hand is: " + playerHand[0].toString() + " and " + playerHand[1].toString() + ".");
                System.out.println("Thanks to your ace, your hand is already perfect!");
                playerTurn = false;
            } else {
                System.out.println("Your starting hand is: " + playerHand[0].toString() + " and " + playerHand[1].toString() + ".");
                System.out.println("Unfortunately, your ace would bust, and therefore your hand is only worth " + playerSum[0] + ".");
            } // end of if else statement
        } else {
            if (playerSum[0] < 21) {
                System.out.println("Your starting hand is: " + playerHand[0].toString() + " and " + playerHand[1].toString() + ".");
                System.out.println("Your hand is worth " + playerSum[0] + ".");
            } else if (playerSum[0] == 21) {
                System.out.println("Your starting hand is: " + playerHand[0].toString() + " and " + playerHand[1].toString() + ".");
                System.out.println("You got lucky! Your hand is already perfect!");
                playerTurn = false;
            } else {
                System.out.println("Your starting hand is: " + playerHand[0].toString() + " and " + playerHand[1].toString() + ".");
                System.out.println("Unfortunately, your hand is already above 21. you lost the game right away :(");
                play = false;
            } // end of if else statement
        } // end of if else statement

        System.out.println("(Press enter to continue.)");
        input = userInput.nextLine();

        // checks whether or not the player already busted, and then reveals the computer's card regardless with slightly different text depending on whether the player busted.
        if (play) {
            if (revealedAce) {
                System.out.println("The computer's revealed card is: " + computerHand[0] + ".");
                System.out.println("The computer has an ace, so the computer's hand is worth both 1 and 11.");
            } else {
                System.out.println("The computer's revealed card is: " + computerHand[0] + ".");
                System.out.println("The computer's hand so far is worth: " + revealedSum + ".");
            } // end of if else statement
        } else {
            if (revealedAce) {
                System.out.println("The computer's revealed card was: " + computerHand[0] + ".");
                System.out.println("The computer had an ace, so the computer's hand was already worth both 1 and 11... super unlucky :(");
            } else {
                System.out.println("The computer's revealed card was: " + computerHand[0] + ".");
                System.out.println("The computer's hand was worth: " + revealedSum + ".");
            } // end of if else statement
        } // end of if else statement

        System.out.println("(Press enter to continue.)");
        input = userInput.nextLine();

        // while it's either the player's turn or computer's turn, it lets them pick between hitting or standing.
        while (play) {
            // checks to see if the player's hand is below 21, and if they have standed to see if they get another turn.
            if ((playerSum[1] > 0 && playerSum [1] < 21) || (playerSum[0] < 21) && (playerTurn)) {
                System.out.println("Would you like to hit or stand? (h/s)");
                input = userInput.nextLine();
                switch (input) {
                    case "h":
                    case "hit":
                    case "H":
                    case "Hit":
                    case "HIT":
                        hit = true;
                        break;
                    default:
                        hit = false;
                        break;
                } // end of switch case
                if (hit) {
                    hitCount++;
                    System.out.println("You have chosen to hit. Your new card is: ");
                    currentCard = deal();
                    playerHand[currentCardNumber] = currentCard;
                    System.out.println(playerHand[currentCardNumber].toString());
                    check = playerHand[currentCardNumber].toString();
                    check = check.split(" ")[0];
                    currentCardNumber++;

                    // reused code to check the value of the card dealt to the player
                    switch(check) {
                        case "Ace":
                            playerHandHasAce = true;
                            playerSum[0] += 1;
                            break;
                        case "Two":
                            playerSum[0] += 2;
                            break;
                        case "Three":
                            playerSum[0] += 3;
                            break;
                        case "Four":
                            playerSum[0] += 4;
                            break;
                        case "Five":
                            playerSum[0] += 5;
                            break;
                        case "Six":
                            playerSum[0] += 6;
                            break;
                        case "Seven":
                            playerSum[0] += 7;
                            break;
                        case "Eight":
                            playerSum[0] += 8;
                            break;
                        case "Nine":
                            playerSum[0] += 9;
                            break;
                        case "Ten":
                            playerSum[0] += 10;
                            break;
                        case "Jack":
                            playerSum[0] += 11;
                            break;
                        case "Queen":
                            playerSum[0] += 12;
                            break;
                        case "King":
                            playerSum[0] += 13;
                            break;
                        default:
                            break;
                    } // end of switch case
                    if (playerHandHasAce) {
                        playerSum[1] = playerSum[0] + 10;
                        if (playerSum[1] < 22) {
                            System.out.println("Your hand is worth both " + playerSum[0] + " and " + playerSum[1] + ".");
                        } else if (playerSum[0] < 22) {
                            System.out.println("Unfortunately, your ace would bust so your hand is only worth " + playerSum[0] + ".");
                        } else {
                            System.out.println("Unfortunately, you have now busted.");
                            System.out.println("(Press enter to continue.)");
                            input = userInput.nextLine();
                            playerTurn = false;
                            play = false;
                        } // end of if else statement
                    } else {
                        if (playerSum[0] < 22) {
                            System.out.println("Your hand is now worth " + playerSum[0] + ".");
                        } else {
                            System.out.println("Unfortunately, you have now busted.");
                            System.out.println("(Press enter to continue.)");
                            input = userInput.nextLine();
                            playerTurn = false;
                            play = false;
                        } // end of if else statement
                    } // end of if else statement
                } else {
                    System.out.println("You have chosen to stand. good luck!\n(Press enter to continue.)");
                    input = userInput.nextLine();
                    playerTurn = false;
                } // end of if else statement
            } // end of if statement (checks whether it's the player's turn or not)

            // checks if it's the computer's turn, and then has it reveal its card if it's the computer's first turn, or decide whether to hit or stand if it's the computer's 2nd+ turn
            if (computerTurnOne && !playerTurn) {
                if (playerSum[0] > 21) {
                    play = false;
                    break;
                }
                System.out.println("The computer will now reveal its second card: " + computerHand[1].toString());
                if (revealedAce) {
                    if (computerSum[1] < 21) {
                        System.out.println("The computer's hand is now worth both: " + computerSum[0] + " and " + computerSum[1]);
                    } else if (computerSum[1] == 21) {
                        System.out.println("The computer got a blackjack with its ace...");
                        play = false;
                    } else {
                        System.out.println("The ace would bust, so the computer's hand is only worth: " + computerSum[0] + ".");
                    }
                } else if (computerHandHasAce) {
                    System.out.println("The computer revealed its ace!");
                    if (computerSum[1] < 21) {
                        System.out.println("The computer's hand is now worth both: " + computerSum[0] + " and " + computerSum[1]);
                    } else if (computerSum[1] == 21) {
                        System.out.println("The computer got a blackjack with its unrevealed ace...");
                        play = false;
                    } else {
                        System.out.println("Thankfully, the ace would bust, so the computer's hand is only worth: " + computerSum[0] + ".");
                    } // end of if else statement
                } else if (computerSum[0] < 21) {
                    System.out.println("The computer's hand is now worth: " + computerSum[0] + ".");
                } else if (computerSum[0] == 21) {
                    System.out.println("The computer got a blackjack...");
                    play = false;
                } else {
                    System.out.println("The computer busted with its unrevealed card!");
                    play = false;
                } // end of if else statement
                System.out.println("(Press enter to continue.)");
                input = userInput.nextLine();

                computerTurnOne = false;
            } else if (!playerTurn && (computerSum[0] < 17 && !(computerSum[1] > 16 && computerSum[1] < 22))) {
                System.out.println("The computer has chosen to hit.");

                currentCard = deal();
                computerHand[currentCardNumber - hitCount] = currentCard;
                System.out.println("The computer has drawn: " + computerHand[currentCardNumber - hitCount].toString() + ".");
                check = computerHand[currentCardNumber - hitCount].toString();
                check = check.split(" ")[0];
                currentCardNumber++;

                switch(check) {
                    case "Ace":
                        computerHandHasAce = true;
                        computerSum[0] += 1;
                        break;
                    case "Two":
                        computerSum[0] += 2;
                        break;
                    case "Three":
                        computerSum[0] += 3;
                        break;
                    case "Four":
                        computerSum[0] += 4;
                        break;
                    case "Five":
                        computerSum[0] += 5;
                        break;
                    case "Six":
                        computerSum[0] += 6;
                        break;
                    case "Seven":
                        computerSum[0] += 7;
                        break;
                    case "Eight":
                        computerSum[0] += 8;
                        break;
                    case "Nine":
                        computerSum[0] += 9;
                        break;
                    case "Ten":
                        computerSum[0] += 10;
                        break;
                    case "Jack":
                        computerSum[0] += 11;
                        break;
                    case "Queen":
                        computerSum[0] += 12;
                        break;
                    case "King":
                        computerSum[0] += 13;
                        break;
                    default:
                        break;
                }

                // checks if the computer's hand has an ace, and if it does, sets the value of the computer's ace sum.
                // after checking, regardless of if the computer had an ace, it informs the player of the computer's new hand value.
                if (computerHandHasAce) {
                    computerSum[1] = computerSum[0] + 10;
                    if (computerSum[1] < 22) {
                        System.out.println("The computer's hand is now worth both: " + computerSum[0] + " and " + computerSum[1] + ".");
                    } else {
                        System.out.println("The ace would bust the computer's hand, so it is only worth: " + computerSum[0]);
                    } // end of if else statement
                } else {
                    System.out.println("The computer's hand is now worth: " + computerSum[0]);
                } // end of if else statement
                System.out.println("(Press enter to continue.)");
                input = userInput.nextLine();
            } else if (!playerTurn && computerSum[0] > 21) {
                System.out.println("The computer has busted!");
                play = false;
            } else if (!playerTurn){
                System.out.println("The computer has chosen to stand.");
                System.out.println("(Press enter to continue.)");
                input = userInput.nextLine();
                play = false;
            }
        } // end of playGame method

        if (playerSum[1] > 0 && playerSum[1] < 22) {
            if (computerSum[1] > 0 && computerSum[1] < 22) {
                if (playerSum[1] > computerSum[1]) {
                    playerWin = 1;
                } else if (playerSum[1] == computerSum[1]) {
                    playerWin = 2;
                }
            } else if (computerSum[0] < 22) {
                if (playerSum[1] > computerSum[0]) {
                    playerWin = 1;
                } else if (playerSum[1] == computerSum[0]) {
                    playerWin = 2;
                }
            } else {
                playerWin = 1;
            } // end of if else statement
        } else if (playerSum[0] < 22) {
            if (computerSum[1] > 0 && computerSum[1] < 22) {
                if (playerSum[0] > computerSum[1]) {
                    playerWin = 1;
                } else if (playerSum[0] == computerSum[1]) {
                    playerWin = 2;
                } // end of if else statement
            } else if (computerSum[0] < 22) {
                if (playerSum[0] > computerSum[0]) {
                    playerWin = 1;
                } else if (playerSum[0] == computerSum[0]) {
                    playerWin = 2;
                }
            } else {
                playerWin = 1;
            }
        }

        return playerWin;
    } // end of play method

    // deals a card from the next spot in the deck
    public static Card deal() {
        dealN++;
        if (dealN > 51) {
            dealN = 0;
        } // end of if statement
        return deck[dealN];
    } // end of deal method

    // shuffles the deck of cards
    public static void shuffle() {
        for (int first = 0; first < 52; first++) {
            int second = randomNumbers.nextInt(52);

            // creates a temporary card to swap two card object infos
            Card temp = deck[first];
            deck[first] = deck[second];
            deck[second] = temp;
        } // end of for loop
    } // end of method shuffle

} // end of class BlackJack