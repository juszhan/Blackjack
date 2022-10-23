import java.util.Scanner;

public class Main {
    public static Scanner reader;

    public static void main(String[] args) {
        reader = new Scanner(System.in);

        try {
            internalMain();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }

    private static void internalMain() {
        System.out.println("Welcome to Blackjack");
        System.out.println("Menu:");
        System.out.println("\"1\" - hit");
        System.out.println("\"2\" - stand");
        System.out.println("Any other key - quit");
        System.out.println();

        Deck deck = new Deck();
        deck.initialize();

        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        int winCount = 0;
        int lossCount = 0;

        while (true) {
            // Step 1: print current game status
            float winRate = 0f;
            if (winCount > 0 || lossCount > 0) {
                winRate = (float) winCount / (winCount + lossCount);
            }

            System.out.println("-----");
            System.out.println(String.format(
                    "Wins|Loss: %d|%d, Win rate: %.2f%%",
                    winCount,
                    lossCount,
                    winRate * 100));
            System.out.println();

            // Step 2: prepare for next game
            playerHand.clear();
            dealerHand.clear();
            if (deck.shouldShuffle()) {
                deck.shuffle();
            }

            // Step 3: deal 2 card for the player and dealer
            playerHand.add(deck.dealNextCard());
            playerHand.add(deck.dealNextCard());

            dealerHand.add(deck.dealNextCard());
            dealerHand.add(deck.dealNextCard());

            System.out.println(String.format(
                    "Player: %s",
                    playerHand.toString()));
            System.out.println(String.format(
                    "Dealer: %s",
                    dealerHand.toString()));

            // Step 4: check for 21
            boolean playerWin = playerHand.getHandState() == HandState.TWENTYONE;
            boolean dealerWin = dealerHand.getHandState() == HandState.TWENTYONE;

            if (playerWin && dealerWin) {
                System.out.println("It's a tie!");
                continue;
            } else if (dealerWin) {
                System.out.println("Loss - dealer has 21");
                lossCount++;
                continue;
            } else if (playerWin) {
                System.out.println("Win - player has 21");
                winCount++;
                continue;
            }

            // Step 5: player turn
            while (playerHand.getHandState() == HandState.VALID) {
                System.out.print("Player action: ");
                final InputSelection nextAction = getNextAction();
                if (nextAction == InputSelection.HIT) {
                    final Card nextCard = deck.dealNextCard();
                    System.out.println("* Hit " + nextCard.toString());
                    playerHand.add(nextCard);
                } else if (nextAction == InputSelection.STAND) {
                    System.out.println("* Stand");
                    break;
                } else {
                    doQuit();
                    return;
                }

                System.out.println(String.format(
                        "Player: %s",
                        playerHand.toString()));
            }

            final HandState playerState = playerHand.getHandState();
            if (playerState == HandState.BUST) {
                System.out.println("Loss - player bust");
                lossCount++;
                continue;
            } else if (playerState == HandState.TWENTYONE) {
                System.out.println("Win - player has 21");
                winCount++;
                continue;
            }

            // Step 6: dealer turn, must draw until bust or beats player
            while (dealerHand.getHandState() == HandState.VALID) {
                if (dealerHand.getBestValue() <= playerHand.getBestValue()) {
                    final Card nextCard = deck.dealNextCard();
                    dealerHand.add(nextCard);
                    System.out.println("* Dealer Hits " + nextCard.toString());
                    System.out.println(String.format(
                            "Dealer: %s",
                            dealerHand.toString()));
                } else {
                    System.out.println("* Dealer Stand");
                    System.out.println("Loss - dealer has higher value than player");
                    lossCount++;
                    break;
                }
            }

            final HandState dealerState = dealerHand.getHandState();
            if (dealerState == HandState.BUST) {
                System.out.println("Win - dealer busts");
                winCount++;
            } else if (dealerState == HandState.TWENTYONE) {
                System.out.println("Loss - dealer has 21");
                lossCount++;
            } else {
                throw new RuntimeException("Unexpected dealerState");
            }
        }
    }

    private static InputSelection getNextAction() {
        if (reader.hasNextInt())
        {
            final int in = reader.nextInt();
            if (in == 1) {
                return InputSelection.HIT;
            } else if (in == 2) {
                return InputSelection.STAND;
            }
        }

        return InputSelection.QUIT;
    }

    private static void doQuit() {
        System.out.println("Thanks for playing! Exiting...");
        reader.close();
    }
}